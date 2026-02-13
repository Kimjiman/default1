# CLAUDE.md

## Project Overview

Spring Boot 2.7 기반 웹 애플리케이션 (Java 17, Gradle 7.6)
- 패키지 루트: `com.example.default1`
- IDE: IntelliJ IDEA

## Build & Run

```bash
# 빌드 (Git Bash / Claude Code 환경)
JAVA_HOME=/c/java/temurin-17.0.16 ./gradlew clean build

# 실행 (프로필 지정)
JAVA_HOME=/c/java/temurin-17.0.16 ./gradlew bootRun -PspringProfiles=local
```

- JDK 17 필요 (`C:\java\temurin-17.0.16`)
- Gradle Wrapper 사용 (`gradlew` / `gradlew.bat`)
- Claude Code(Git Bash) 환경에서는 반드시 `./gradlew` (Unix wrapper) 사용 (`gradlew.bat`은 인식 불가)
- Git Bash 경로 표기: `C:\java\...` → `/c/java/...`

## Tech Stack

- **Spring Boot 2.7.18** (spring-security, spring-data-jpa, thymeleaf, webflux)
- **JPA + QueryDSL 5.0** (동적 쿼리)
- **MapStruct 1.5.5** (Entity <-> Model 변환)
- **Lombok** (@SuperBuilder, @Getter/@Setter 등)
- **Spring Security + JWT** (jjwt 0.11.5)
- **MySQL** (런타임)
- **Redis** (토큰 저장소, 캐시)
- **Springdoc OpenAPI** (Swagger UI)
- **JUnit 5 + Mockito + Instancio** (테스트)

## Architecture — Layered + Facade Pattern

```
Controller → Facade → Service → Repository (JPA + QueryDSL)
                ↕
            Converter (MapStruct)
```

### Layer 역할

| Layer | 역할 | 위치 |
|---|---|---|
| **Controller** | HTTP 요청/응답, `@RestController` | `module/{name}/controller/` |
| **Facade** | 서비스 간 조합, Model 변환 (`@Facade`) | `module/{name}/facade/` |
| **Service** | 단일 도메인 비즈니스 로직 | `module/{name}/service/` |
| **Repository** | JPA + QueryDSL 데이터 접근 | `module/{name}/repository/` |
| **Converter** | MapStruct 기반 Entity↔Model 변환 | `module/{name}/converter/` |

### 인증/인가 아키텍처

```
JwtAuthenticationFilter → JwtTokenProvider (JWT 검증)
                        → AuthUserDetailsService (DB에서 유저 로드 → AuthUserDetails)

AuthFacade  → UserService (로그인)
            → JwtTokenService  → JwtTokenProvider (JWT 생성)
                               → RefreshTokenStore (인터페이스)
                                     ↑
                               RedisRefreshTokenStore (구현체)
```

| 클래스 | 역할 |
|---|---|
| **AuthUserDetailsService** | `UserDetailsService` 구현체, DB에서 유저 조회 후 `AuthUserDetails` 반환 |
| **AuthUserDetails** | `UserDetails` 구현 record, `User` 엔티티를 래핑 |
| **JwtAuthenticationFilter** | JWT 토큰 검증 후 `AuthUserDetailsService`로 유저를 로드하여 SecurityContext 설정 |
| **JwtProperties** | `jwt.yml` 설정값 바인딩 (`@ConfigurationProperties`) |
| **JwtTokenProvider** | 순수 JWT 암호화/복호화/검증 (Redis 의존 없음) |
| **JwtTokenService** | 토큰 생명주기 관리 (생성, 재발급, 삭제, 중복로그인 체크) |
| **RefreshTokenStore** | 리프레시 토큰 저장소 추상화 인터페이스 |
| **RedisRefreshTokenStore** | Redis 구현체 (key: `jwt:refresh:{loginId}`) |
| **AuthFacade** | 인증 관련 Facade (로그인, 로그아웃, 토큰 재발급) |
| **AuthController** | 인증 API (`/auth/login`, `/auth/logout`, `/auth/issueAccessToken`) |

- JWT 설정은 `src/main/resources/jwt.yml`에서 관리 (`spring.config.import`로 로드)
- 저장소 교체 시 `RefreshTokenStore` 구현체만 추가하면 됨

### 모듈 목록

- `code` — 코드/코드그룹 관리 (CodeGroup ↔ Code: @OneToMany)
- `user` — 사용자/인증 (JWT 기반)
- `menu` — 메뉴 관리
- `file` — 파일 업로드/다운로드
- `test` — 테스트용

## Key Conventions

### Model 구조 (Entity / Model 분리)

```
BaseObject
├── BaseEntity<ID>   — JPA Entity 기반 클래스 (@MappedSuperclass)
│   ├── id, rowNum(@Transient), createTime, createId, updateTime, updateId
│   └── @PrePersist, @PreUpdate 자동 감사
└── BaseModel<ID>    — API 응답/요청용 DTO
    └── id, createId, createTime(String), updateId, updateTime(String)
```

- **Entity**: `module/{name}/model/{Name}.java` — JPA 엔티티, `extends BaseEntity<Long>`
- **Model**: `module/{name}/model/{Name}Model.java` — Facade/Controller에서 사용하는 DTO, `extends BaseModel<Long>`
- **SearchParam**: `module/{name}/model/{Name}SearchParam.java` — 검색 조건 파라미터

### Converter 규칙 (MapStruct)

- `toModel(Entity)` / `toEntity(Model)` — 단건 변환
- `toModelList(List<Entity>)` / `toEntityList(List<Model>)` — 목록 변환
- `TypeConverter` — 타입 변환 (`uses = {TypeConverter.class}`)
  - `LocalDateTime ↔ String`: `qualifiedByName = "localDateTimeToString"` / `"stringToLocalDateTime"`
  - `String ↔ YN`: `qualifiedByName = "stringToYn"` / `"ynToString"`
- Model→Entity: `@Mapping(target = "rowNum", ignore = true)`
- Entity의 `YN` 컬럼은 `String`으로 저장, Model에서 `YN` enum 사용 → MapStruct에서 변환

### Service 규칙

- `implements BaseService<T, P, ID>` 인터페이스
- 기본 메서드: `existsById`, `findById`, `countAllBy`, `findAllBy`, `save`, `update`, `deleteById`

### Repository 규칙

- `{Name}Repository extends JpaRepository<T, ID>, {Name}RepositoryCustom`
- `{Name}RepositoryCustom` — QueryDSL 커스텀 쿼리 인터페이스
- `{Name}RepositoryImpl` — QueryDSL 구현체 (`JPAQueryFactory` 사용)

### Exception 규칙

```
ErrorCode (전략 인터페이스)
    └── SystemErrorCode (공통 에러 enum)
            ↓
    CustomException (ErrorCode를 위임받아 처리)
            ↓
    ExceptionAdvice (응답 조립)
```

- **예외는 Facade에서만 발생**시키는 것이 원칙
- **검증은 `ToyAssert`** 사용 (단순 검증), 복합 조건은 직접 `throw`
- 에러 추가 시 `SystemErrorCode`에 enum 추가, 모듈 고유 에러가 많아지면 별도 enum 분리
- 사용 예시:
  ```java
  ToyAssert.notBlank(loginId, SystemErrorCode.REQUIRED, "아이디를 입력해주세요.");
  ToyAssert.notNull(id, SystemErrorCode.REQUIRED);
  ToyAssert.isTrue(condition, SystemErrorCode.DUPLICATE_LOGIN);
  throw new CustomException(SystemErrorCode.FILE_ERROR, "파일 다운로드 중 오류가 발생했습니다.");
  ```

### JPA 관계

- `@OneToMany` + `@JoinColumn(insertable=false, updatable=false)` — 읽기 전용 관계
- LAZY 필드에 `@ToString.Exclude` 필수
- 목록 조회: QueryDSL에서 `.leftJoin().fetchJoin().distinct()` 사용
- 단건 조회: `@EntityGraph(attributePaths = {...})` 사용

## Commit Convention

```
[상태] 구현내역 - 구현내역상세
```

| 상태 | 용도 |
|---|---|
| **Feature** | 신규 기능 추가 |
| **Fix** | 버그 수정 |
| **Well** | 기능 개선, 리팩토링 |
| **Docs** | 문서 수정 |
| **Chore** | 빌드 설정, 의존성 변경 등 기능 외 작업 |
| **Remove** | 기능/코드 삭제 |

예시:
- `[Feature] 사용자 인증 - JWT 토큰 발급 기능 추가`
- `[Fix] 로그아웃 - Redis key 불일치 버그 수정`
- `[Well] JWT 아키텍처 - 책임 분리 및 인터페이스 추상화`
- `[Docs] CLAUDE.md - 커밋 컨벤션 추가`
- `[Chore] Gradle - QueryDSL 의존성 버전 업데이트`
- `[Remove] 레거시 API - 미사용 엔드포인트 삭제`

## Project Structure

```
src/main/java/com/example/default1/
├── base/
│   ├── annotation/        — @Facade 등 커스텀 어노테이션
│   ├── component/         — ShellExecute, HttpRequestClient, GlobalLogAop
│   ├── constants/         — YN, RegexConstants, UrlConstants
│   ├── converter/         — TypeConverter, BaseMapperConfig
│   ├── exception/         — ErrorCode, SystemErrorCode, CustomException, BaseException, ToyAssert
│   ├── model/             — BaseObject, BaseEntity, BaseModel, BaseSearchParam, Response, pager/
│   ├── redis/             — RedisRepository, RedisObject
│   ├── security/          — AuthUserDetails, AuthUserDetailsService
│   │   └── jwt/           — JwtProperties, JwtTokenProvider, JwtTokenService, JwtAuthenticationFilter
│   │       └── token/     — RefreshTokenStore, RedisRefreshTokenStore
│   ├── service/           — BaseService 인터페이스
│   └── utils/             — StringUtils, DateUtils, JsonUtils, CryptoUtils 등
├── config/                — Security, CORS, Redis, Swagger, QueryDSL, WebConfig
│   ├── advice/            — ResponseAdvice, ExceptionAdvice
│   ├── interceptor/       — RoleInterceptor
│   └── scheduler/         — CacheScheduler
└── module/
    ├── code/              — controller, converter, facade, model, repository, service
    ├── user/              — controller(AuthController, UserController), converter, facade(AuthFacade, UserFacade), model, repository, service
    ├── menu/              — controller, facade, model, repository, service
    ├── file/              — controller, model, repository, service
    ├── main/              — controller (MainController)
    └── test/              — controller, model, repository
```
