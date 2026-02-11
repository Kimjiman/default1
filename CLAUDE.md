# CLAUDE.md

## Project Overview

Spring Boot 2.7 기반 웹 애플리케이션 (Java 17, Gradle 7.6)
- 패키지 루트: `com.example.default1`
- IDE: IntelliJ IDEA

## Build & Run

```bash
# 빌드
gradlew clean build

# 실행 (프로필 지정)
gradlew bootRun -PspringProfiles=local
```

- JDK 17 필요 (IntelliJ 기준: `File > Project Structure > SDK`)
- Gradle Wrapper 사용 (`gradlew` / `gradlew.bat`)

## Tech Stack

- **Spring Boot 2.7.18** (spring-security, spring-data-jpa, thymeleaf, webflux)
- **JPA + QueryDSL 5.0** (동적 쿼리)
- **MapStruct 1.5.5** (Entity <-> Model 변환)
- **Lombok** (@SuperBuilder, @Getter/@Setter 등)
- **Spring Security + JWT** (jjwt 0.11.5)
- **MySQL** (런타임)
- **Redis** (캐시/세션)
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
- `TypeConverter` — `LocalDateTime ↔ String` 변환 (`uses = {TypeConverter.class}`)
- Entity→Model: `qualifiedByName = "localDateTimeToString"`
- Model→Entity: `qualifiedByName = "stringToLocalDateTime"`, `@Mapping(target = "rowNum", ignore = true)`

### Service 규칙

- `implements BaseService<T, P, ID>` 인터페이스
- 기본 메서드: `existsById`, `findById`, `countAllBy`, `findAllBy`, `save`, `update`, `deleteById`

### Repository 규칙

- `{Name}Repository extends JpaRepository<T, ID>, {Name}RepositoryCustom`
- `{Name}RepositoryCustom` — QueryDSL 커스텀 쿼리 인터페이스
- `{Name}RepositoryImpl` — QueryDSL 구현체 (`JPAQueryFactory` 사용)

### JPA 관계

- `@OneToMany` + `@JoinColumn(insertable=false, updatable=false)` — 읽기 전용 관계
- LAZY 필드에 `@ToString.Exclude` 필수
- 목록 조회: QueryDSL에서 `.leftJoin().fetchJoin().distinct()` 사용
- 단건 조회: `@EntityGraph(attributePaths = {...})` 사용

## Project Structure

```
src/main/java/com/example/default1/
├── base/
│   ├── annotation/        — @Facade 등 커스텀 어노테이션
│   ├── component/         — ShellExecute, HttpRequestClient, GlobalLogAop
│   ├── constants/         — YN, RegexConstants, UrlConstants
│   ├── converter/         — TypeConverter, BaseMapperConfig
│   ├── exception/         — CustomException, BaseException, ToyAssert
│   ├── model/             — BaseObject, BaseEntity, BaseModel, BaseSearchParam, Response, pager/
│   ├── redis/             — RedisRepository, RedisObject
│   ├── security/          — AuthUserDetails, AuthUserService, JWT 관련
│   ├── service/           — BaseService 인터페이스
│   ├── typeHandler/       — YnAttributeConverter
│   └── utils/             — StringUtils, DateUtils, JsonUtils, CryptoUtils 등
├── config/                — Security, CORS, Redis, Swagger, QueryDSL, WebConfig
│   ├── advice/            — ResponseAdvice, ExceptionAdvice
│   ├── interceptor/       — RoleInterceptor
│   └── scheduler/         — CacheScheduler
└── module/
    ├── code/              — controller, converter, facade, model, repository, service
    ├── user/              — controller, converter, facade, model, repository, service
    ├── menu/              — controller, facade, model, repository, service
    ├── file/              — controller, model, repository, service
    ├── main/              — controller (MainController)
    └── test/              — controller, model, repository
```
