# basic-arch — Spring Boot 스타터 프로젝트

Spring Boot 2.7 기반의 백엔드 스타터 프로젝트입니다.
새로운 프로젝트를 시작할 때 공통 인프라를 처음부터 구축하는 시간을 줄이기 위해 만들어졌습니다.
JWT 인증, RBAC 권한 체계, Redis 캐시, 파일 업로드, 공통 코드 관리 등 실무에서 자주 필요한 기능을 미리 갖추고 있습니다.

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| 언어 / 플랫폼 | Java 17, Spring Boot 2.7.18, Gradle 7.6 |
| 데이터 접근 | Spring Data JPA, QueryDSL 5.0, Flyway |
| 매핑 | MapStruct 1.5.5, Lombok |
| 인증 / 보안 | Spring Security, JWT (jjwt 0.11.5) |
| 캐시 / 세션 | Redis 7 (토큰 저장소 + 캐시) |
| 데이터베이스 | PostgreSQL 15 |
| API 문서 | SpringDoc OpenAPI (Swagger UI) |
| 외부 통신 | Spring WebFlux (WebClient) |
| 뷰 템플릿 | Thymeleaf |
| 로컬 인프라 | Docker Compose (PostgreSQL + Redis 자동 기동) |
| 배포 서버 | Railway 자동 배포 이용(진짜 넘편함) |

---

## 아키텍처

### 레이어드 + 파사드 패턴

```
HTTP 요청
    ↓
Controller          — HTTP 입출력 처리만 담당 (@RestController)
    ↓
Facade              — 여러 Service 조합, 입력값 검증, 예외 발생 (@Facade)
    ↓
Service             — 단일 도메인 비즈니스 로직
    ↓
Repository          — 데이터 접근 (JPA + QueryDSL)
    ↕
Converter           — Entity ↔ Model 변환 (MapStruct)
```

**Facade가 하는 일**
- 여러 Service를 조합해 하나의 유스케이스를 완성
- 입력 유효성 검사 (`ToyAssert`)
- 비즈니스 예외 발생 (`CustomException`)
- Entity ↔ Model 변환 위임

**의도적인 설계 결정**
- `@Valid` Bean Validation 대신 Facade에서 `ToyAssert`로 명시적 검증 → 검증 로직이 한 곳에 집중되어 추적이 쉬움
- 예외는 Facade에서만 발생 → Controller · Service는 순수한 역할에 집중

---

### 인증 흐름 (JWT)

```
로그인 요청
    ↓
AuthFacade → UserService (자격증명 확인)
           → JwtTokenService
                ↓
           JwtTokenProvider   — JWT 서명/검증 (순수 기능, Redis 의존 없음)
           RefreshTokenStore  — 리프레시 토큰 저장소 인터페이스
                ↑
           RedisRefreshTokenStore — Redis 구현체 (키: jwt:refresh:{loginId})

이후 API 요청
    ↓
JwtAuthenticationFilter → JwtTokenProvider (토큰 검증)
                        → AuthUserDetailsService (DB에서 사용자 로드)
                        → SecurityContext 설정
```

| 클래스 | 역할 |
|--------|------|
| `JwtTokenProvider` | JWT 생성·파싱·검증 (Redis 비의존) |
| `JwtTokenService` | 토큰 생명주기 관리 (발급·재발급·삭제·중복 로그인 처리) |
| `RefreshTokenStore` | 리프레시 토큰 저장소 추상 인터페이스 |
| `RedisRefreshTokenStore` | Redis 기반 구현체 |
| `AuthUserDetails` | `UserDetails`를 구현한 record (User 엔티티 래핑) |

---

## 프로젝트 구조

```
src/main/java/com/example/basicarch/
│
├── BasicArchApplication.java
│
├── base/                          # 공통 인프라 (프로젝트 전역 공유)
│   ├── annotation/                # @Facade 커스텀 어노테이션
│   ├── component/
│   │   ├── GlobalLogAop.java      # 전역 요청/응답 로깅 AOP
│   │   ├── ShellExecute.java      # 셸 명령 실행 유틸
│   │   └── webclient/             # WebClient 래퍼 (외부 API 호출)
│   ├── constants/
│   │   ├── CacheType.java         # Redis 캐시 키/TTL 정의
│   │   ├── RegexPattern.java      # 정규식 상수
│   │   ├── UrlConstants.java      # URL 경로 상수
│   │   └── YN.java                # Y/N 열거형 (JSON ↔ DB 변환)
│   ├── converter/
│   │   ├── BaseMapperConfig.java  # MapStruct 공통 설정
│   │   └── TypeConverter.java     # LocalDateTime ↔ String, String ↔ YN
│   ├── exception/
│   │   ├── ErrorCode.java         # 에러 코드 전략 인터페이스
│   │   ├── SystemErrorCode.java   # 공통 에러 코드 열거형
│   │   ├── CustomException.java   # 비즈니스 예외 클래스
│   │   └── ToyAssert.java         # 검증 유틸 (notBlank, notNull, isTrue …)
│   ├── model/
│   │   ├── BaseObject.java        # 최상위 공통 객체
│   │   ├── BaseEntity.java        # JPA 기반 클래스 (id, 감사 필드, @PrePersist)
│   │   ├── BaseModel.java         # DTO 기반 클래스
│   │   ├── BaseSearchParam.java   # 검색 조건 기반 클래스
│   │   ├── Response.java          # API 응답 봉투 (success/fail)
│   │   ├── pager/PageInfo.java    # 페이지 메타데이터
│   │   └── pager/PageResponse.java # 페이지 응답 (PageInfo + List)
│   ├── redis/
│   │   ├── RedisRepository.java   # Redis CRUD 추상 클래스
│   │   ├── CacheEventPublishable.java  # 캐시 무효화 이벤트 발행 인터페이스
│   │   └── CacheEventListener.java    # Redis Pub/Sub 수신 → 캐시 제거
│   ├── security/                  # Spring Security + JWT 구현
│   └── utils/
│       ├── SessionUtils.java      # SecurityContext에서 현재 사용자 조회
│       ├── DateUtils.java         # 날짜/시간 유틸 (40+ 메서드)
│       ├── StringUtils.java       # 문자열 유틸 (마스킹, 패딩, 정규식)
│       ├── CollectionUtils.java   # 컬렉션 유틸 (safeStream, merge, toMap …)
│       ├── JsonUtils.java         # GSON 기반 JSON 유틸 (LocalDateTime 지원)
│       └── …                      # CookieUtils, CryptoUtils, NetworkUtils 등
│
├── config/                        # Spring 설정 클래스
│   ├── SecurityConfig.java        # Security 필터 체인, 인가 규칙
│   ├── RedisConfig.java           # RedisTemplate, Pub/Sub 설정
│   ├── QueryDslConfig.java        # JPAQueryFactory 빈
│   ├── SwaggerConfig.java         # OpenAPI 문서 설정
│   ├── CorsConfig.java            # CORS 허용 설정
│   ├── WebConfig.java             # MVC 설정 (인터셉터 등록 등)
│   ├── LocalDockerConfig.java     # local 프로필 시 docker-compose 자동 실행
│   ├── advice/
│   │   ├── ResponseAdvice.java    # 전역 응답 자동 래핑 (Response<T>)
│   │   └── ExceptionAdvice.java   # 전역 예외 처리 → Response.fail(…)
│   ├── interceptor/
│   │   └── RoleInterceptor.java   # URI별 역할 권한 체크 (Menu 캐시 활용)
│   └── scheduler/
│       └── CacheScheduler.java    # 캐시 주기적 갱신 (cron.yml 설정)
│
└── module/                        # 기능 모듈 (각 모듈: controller/facade/service/repository/converter/model/entity)
    ├── user/                      # 사용자, 역할, 인증 (로그인/로그아웃/토큰 재발급)
    ├── code/                      # 공통 코드 · 코드그룹 관리
    ├── menu/                      # 메뉴 관리 + URI별 역할 캐시
    ├── file/                      # 파일 업로드/다운로드
    └── main/                      # 메인 페이지
```

---

## 주요 모듈

### user — 사용자 & 인증
- JWT 기반 로그인 / 로그아웃 / 액세스 토큰 재발급
- RBAC: `Role` ↔ `UserRole` ↔ `User` 구조
- 중복 로그인 감지 (Redis에 로그인 시 기존 리프레시 토큰 삭제)
- API: `POST /auth/login`, `POST /auth/logout`, `POST /auth/issueAccessToken`

### code — 공통 코드
- `CodeGroup` (그룹) ↔ `Code` (코드) 1:N 관계
- Redis 캐시: `codeGroup:code → name` 형태로 1시간 캐시
- Redis Pub/Sub으로 캐시 무효화 이벤트 전파

### menu — 메뉴
- 트리 구조 메뉴 관리 (self-join `parent_id`)
- URI별 접근 가능 역할 캐시 → `RoleInterceptor`에서 권한 체크
- 캐시 TTL: 30분

### file — 파일
- 멀티파트 업로드 / 다운로드 / 삭제
- `ref_path` + `ref_id` 조합으로 어느 엔티티에도 파일 첨부 가능
- 저장 경로: `local` 프로필 기준 `c:/tmp/basic-arch`

---

## Redis 캐시 전략

| 캐시 | 키 구조 | TTL | 무효화 방식 |
|------|--------|-----|------------|
| Code 캐시 | `codeGroup:code → name` | 1시간 | Redis Pub/Sub 이벤트 |
| Menu 역할 캐시 | `uri → roleList` | 30분 | Redis Pub/Sub 이벤트 |
| Refresh Token | `jwt:refresh:{loginId}` | JWT 만료 시간 | 로그아웃/재로그인 시 삭제 |

캐시 갱신 주기는 `src/main/resources/cron.yml`에서 설정합니다.

---

## 공통 패턴

### API 응답 형식

모든 응답은 `Response<T>`로 자동 래핑됩니다 (`ResponseAdvice`가 처리).

```json
// 성공
{ "data": { ... } }

// 실패
{ "status": 400, "message": "에러 메시지" }
```

### 페이지 응답 형식

```json
{
  "pageInfo": { "page": 1, "totalRow": 100, "totalPage": 10, "limit": 10 },
  "list": [ ... ]
}
```

### Entity / Model 분리

- **Entity** (`extends BaseEntity<Long>`): JPA 영속성 객체, DB와 직접 매핑
- **Model** (`extends BaseModel<Long>`): Facade/Controller가 주고받는 DTO
- **Converter** (MapStruct): 양방향 변환 (`toModel` / `toEntity`)

### 감사 필드 자동 설정

`BaseEntity`의 `@PrePersist` / `@PreUpdate`가 자동으로 처리합니다.

| 필드 | 처리 방식 |
|------|----------|
| `createTime` / `updateTime` | `LocalDateTime.now()` 자동 설정 |
| `createId` / `updateId` | `SessionUtils.getId()` (현재 로그인 사용자 ID) |

---

## 빌드 및 실행

### 사전 요구사항

- **JDK 17** (`C:\java\jdk-17.0.18+8`)
- **WSL2 + Docker Desktop** (Windows 기준, local 프로필 자동 실행에 필요)

### Git Bash 기준

```bash
# 빌드
JAVA_HOME=/c/java/jdk-17.0.18+8 ./gradlew clean build

# 실행 — local 프로필 (기본값, 포트 8085)
JAVA_HOME=/c/java/jdk-17.0.18+8 ./gradlew bootRun

# 실행 — dev 프로필 (포트 8080)
JAVA_HOME=/c/java/jdk-17.0.18+8 ./gradlew bootRun -PspringProfiles=dev

# 전체 테스트
JAVA_HOME=/c/java/jdk-17.0.18+8 ./gradlew test

# 특정 테스트 클래스
JAVA_HOME=/c/java/jdk-17.0.18+8 ./gradlew test --tests "com.example.basicarch.module.user.UserServiceTest"
```

### Windows CMD / PowerShell 기준

```cmd
set JAVA_HOME=C:\java\jdk-17.0.18+8

gradlew.bat clean build
gradlew.bat bootRun
gradlew.bat bootRun -PspringProfiles=dev
```

### Docker (인프라만 별도 실행)

`local` 프로필로 실행하면 `LocalDockerConfig`가 `docker-compose up -d`를 자동 호출합니다.
수동으로 실행하려면:

```bash
docker-compose up -d    # PostgreSQL + Redis 기동
docker-compose down     # 중지
```

---

## 실행 환경 (프로필)

| 프로필 | 포트 | DB | 특이사항 |
|--------|------|----|---------|
| `local` (기본값) | 8085 | PostgreSQL localhost:5432 | DevTools·LiveReload 활성, SQL 로깅, Docker 자동 기동 |
| `dev` | 8080 | PostgreSQL localhost:5432 | 파일 로깅 (`/web/jar/log/file.log`) |
| `prod` | 8080 | PostgreSQL localhost:5432 | SQL 로깅 비활성, 파일 로깅 |

---

## API 문서

애플리케이션 실행 후 Swagger UI에서 모든 API를 확인하고 테스트할 수 있습니다.

```
http://localhost:8085/swagger-ui/index.html   (local)
http://localhost:8080/swagger-ui/index.html   (dev/prod)
```

---

## 데이터베이스 초기화

Flyway가 앱 시작 시 자동으로 스키마와 기본 데이터를 생성합니다.

```bash
# 볼륨까지 초기화 후 재시작 (DB를 완전히 리셋할 때)
docker-compose down -v
docker-compose up -d
```

기본 계정: `admin` / `admin`

---

## 학습 로드맵

이직 준비 학습 순서 (CKA 목표)

| 순서 | 기술 | 학습 내용 | 상태 |
|------|------|-----------|------|
| 1 | **Kubernetes** | minikube로 현재 프로젝트 배포, Deployment / Service / ConfigMap / Secret / HPA 실습 | 🔜 진행 예정 |
| 2 | **Prometheus + Grafana** | K8s 클러스터 메트릭 수집, 대시보드 구성, 알림 설정 | ⏳ 대기 |
| 3 | **Kafka** | docker-compose에 Kafka 추가, 이벤트 드리븐 아키텍처 실습, DLT 처리 | ⏳ 대기 |
