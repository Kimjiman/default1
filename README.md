# basic-arch

---

## 소개

실무 프로젝트를 수행하면서 아키텍처 규격의 부재로 고생한 적이 많습니다. 비슷한 CRUD를 작업하는데도 로직이 제각각이거나, 서로 만든 서비스를 서로 갖다 쓰다가 순환참조 에러도 발생하는 건 기본이고요. 규격화된 환경에서 비즈니스 로직만 작업할 수 있으면 신규 개발하기도 훨씬 쉬울 거라고 생각해서 시작하게 된 프로젝트입니다.

| 분류       | 기술                                        |
|----------|-------------------------------------------|
| 언어 / 플랫폼 | Java 21, Spring Boot 3.5.9, Gradle 8.14   |
| 데이터 접근   | Spring Data JPA, QueryDSL 5.0, Flyway     |
| 매핑       | MapStruct 1.5.5, Lombok                   |
| 인증 / 보안  | Spring Security, JWT (jjwt 0.11.5)        |
| 캐시 / 세션  | Redis 7 (토큰 저장소 + 캐시)                     |
| 데이터베이스   | PostgreSQL 15                             |
| API 문서   | SpringDoc OpenAPI (Swagger UI)            |
| 외부 통신    | Spring WebFlux (WebClient)                |
| 로컬 인프라   | Docker Compose (PostgreSQL + Redis 자동 기동) |
| 모니터링     | Prometheus, Grafana, Actuator             |

---

## 설계

### 레이어드 + Facade 패턴

[파사드 패턴이란?](https://medium.com/@harshitha.khandelwal/facade-design-pattern-explained-f8c8be035086)

```
Controller   — HTTP 입출력 처리만 담당
Facade       — 여러 Service 조합, 입력값 검증, 예외 발생
Service      — 단일 도메인 비즈니스 로직
Repository   — 데이터 접근
```

**Facade 패턴을 선택한 이유**
- 서비스 간 순환 참조를 구조적으로 차단하기 위함
- Bean Validation 대신 [ToyAssert](src/main/java/com/example/basicarch/base/exception/ToyAssert.java)를 Facade에서 직접 처리 → 검증 로직이 한 곳에 집중되어 추적이 쉬움
- 예외는 Facade에서만 발생시키는 원칙, Service는 비즈니스 로직에만 집중

프로젝트 전반의 공통 구조는 아래 Base 클래스로 규격화했습니다.

| 클래스 | 역할 |
|--------|------|
| [BaseService](src/main/java/com/example/basicarch/base/service/BaseService.java) | 모든 Service가 구현하는 인터페이스. 동일한 메서드 시그니처 강제 |
| [BaseEntity](src/main/java/com/example/basicarch/base/model/BaseEntity.java) | PK 체계 단일화, 생성일/수정일 감사 필드 자동화 |
| [BaseModel](src/main/java/com/example/basicarch/base/model/BaseModel.java) | Facade/Controller 계층에서 사용하는 DTO 기반 클래스 |
| [BaseSearchParam](src/main/java/com/example/basicarch/base/model/BaseSearchParam.java) | 검색 조건 공통 파라미터 규격화 |

### Entity / Model 분리

- **Entity** (`extends BaseEntity<Long>`): JPA 영속성 객체, DB와 직접 매핑
- **Model** (`extends BaseModel<Long>`): Facade/Controller가 주고받는 DTO
- **Converter** (MapStruct): 양방향 변환 (`toModel` / `toEntity`)

### Redis 캐시 전략

| 캐시            | 키 구조                    | TTL       | 무효화 방식            |
|---------------|-------------------------|-----------|-------------------|
| Code 캐시       | `codeGroup:code → name` | 1시간       | Redis Pub/Sub 이벤트 |
| Menu 역할 캐시    | `uri → roleList`        | 30분       | Redis Pub/Sub 이벤트 |
| Refresh Token | `jwt:refresh:{loginId}` | JWT 만료 시간 | 로그아웃/재로그인 시 삭제    |

### API 응답 형식

모든 응답은 [ResponseAdvice](src/main/java/com/example/basicarch/config/advice/ResponseAdvice.java)가 [Response<T>](src/main/java/com/example/basicarch/base/model/Response.java)로 자동 래핑합니다.

```java
public static <T> Response<T> fail(int status, String error, String message) {
    return Response.<T>builder()
        .status(status)
        .error(error)
        .message(message)
    .build();
}

public static <T> Response<T> fail(int status, String message) {
    return Response.<T>builder()
        .status(status)
        .message(message)
    .build();
}
```

---

## 기능

### 프로젝트 구조

```
src/main/java/com/example/basicarch/
├── base/        공통 인프라 (annotation, exception, model, redis, security, utils)
├── config/      Spring 설정 (Security, Redis, Swagger, advice, interceptor, scheduler)
└── module/
    ├── user/    사용자, 역할, 인증
    ├── code/    공통 코드 · 코드그룹
    ├── menu/    메뉴 관리
    └── file/    파일 업로드/다운로드
```

각 모듈은 `controller / facade / service / repository / converter / entity / model` 구조를 따릅니다.

### 주요 모듈

**user — 사용자 & 인증**
- JWT 기반 로그인 / 로그아웃 / 액세스 토큰 재발급
- RBAC: `Role` ↔ `UserRole` ↔ `User` 구조
- 중복 로그인 감지 (로그인 시 기존 리프레시 토큰 삭제)
- `POST /auth/login`, `POST /auth/logout`, `POST /auth/issueAccessToken`

**code — 공통 코드**

**menu — 메뉴**

**file — 파일**

---

## 실행 가이드

요구 사항: JDK 21, Docker Desktop (Windows는 WSL2 필요)

```bash
./gradlew clean build
./gradlew bootRun
./gradlew test
```

| 프로필           | 포트   | 특이사항                                    |
|---------------|------|-----------------------------------------|
| `local` (기본값) | 8085 | DevTools 활성, SQL 로깅, Docker 자동 기동      |
| `dev`         | 8080 | 파일 로깅 (`/web/jar/log/file.log`)        |
| `prod`        | 8080 | SQL 로깅 비활성, 파일 로깅                      |

- Swagger UI: `http://localhost:8085/swagger-ui/index.html`
- Prometheus: `http://localhost:19090`
- Grafana: `http://localhost:13000`
기본 계정: `admin` / `admin`

---

## 로드맵

이직 준비 학습 순서

| 순서 | 기술             | 학습 내용                                                                  | 상태   |
|----|----------------|------------------------------------------------------------------------|------|
| 1  | **Kubernetes** | minikube로 현재 프로젝트 배포, Deployment / Service / ConfigMap / Secret / HPA 실습 | 진행예정 |
| 2  | **Kafka**      | docker-compose에 Kafka 추가                        | 대기   |
