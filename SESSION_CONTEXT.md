# 프로젝트: 메뉴 기반 동적 권한 관리 시스템 (Dynamic RBAC) 구현

## 1. 개요

Spring Boot 환경에서 `JwtAuthenticationFilter`를 통한 **인증(Authentication)**과 `RoleInterceptor`를 통한 **인가(Authorization)** 체계를 구축한다. 특히, 코드 수정이나 서버 재시작 없이 관리자 UI에서 메뉴별 접근 권한을 즉시 변경할 수 있도록 **DB 기반 동적 인가 및 캐싱 전략**을 적용한다.

## 2. 시스템 아키텍처 및 흐름

1. **Request**: 유저 요청 발생
2. **JwtAuthenticationFilter**: JWT 검증 및 SecurityContext에 유저 정보(Roles 포함) 설정
3. **RoleInterceptor**: 요청 URI를 기반으로 `Menu` 테이블/캐시 데이터와 매칭
4. **Authorization**: `menu.roles`와 유저의 `Role` 목록을 비교
* **일치**: 핸들러(Controller) 통과
* **불일치**: `403 Forbidden` 반환 (SystemErrorCode 1301 사용)

---

## 3. 상세 구현 Phase별 태스크

### Phase 1: Role 데이터 모델 구축

| 파일명 | 주요 내용 |
| --- | --- |
| **Role Entity** | `id`, `roleName`, `description`, `useYn` |
| **UserRole Entity** | `id`, `userId`, `roleId` (N:M 관계 해소 테이블) |
| **RoleRepository** | 기본 CRUD 인터페이스 |
| **UserRoleRepository** | `findByUserId(Long userId)`를 통한 유저 권한 조회 |

### Phase 2: 인증 객체(SecurityContext)에 Role 연결

* **AuthUserService**: 기존 하드코딩된 권한 부여(`List.of("USR")`) 로직을 제거하고, 로그인 시 `UserRoleRepository`를 통해 DB에서 조회한 실제 Role 목록을 `UserDetails`에 담도록 수정한다.

### Phase 3: 메뉴 기반 동적 인가 및 캐싱

* **MenuRepository**: `findByUseYn("Y")` 등을 통해 활성 메뉴 전체 조회 기능 추가.
* **MenuService**:
* `Map<String, List<String>>` (Key: URI, Value: Roles) 형태의 메모리 캐시 관리.
* `findByUri(String uri)`: 요청된 URI와 매칭되는 메뉴의 권한 정보를 캐시에서 반환.


* **RoleInterceptor**: `HandlerInterceptor`를 구현하여 요청 URI를 가로채고, `MenuService`를 통해 접근 권한 여부 체크.
* **CacheScheduler**: 기존 스케줄러를 활용하여 주기적으로(매일 23:55) 캐시를 최신화.

### Phase 4: 관리 API 및 데이터 변환 (Facade/Model)

* **MenuModel / MenuConverter**: Entity와 DTO 간의 변환 로직 구현.
* **MenuFacade / Controller**: 메뉴 CRUD API 및 특정 메뉴에 대한 Role 할당/해제 API 구현. **(수정 시 캐시 즉시 갱신 로직 포함 필수)**
* **RoleFacade / Controller**: Role 정의 관리 및 유저별 Role 할당 API.

### Phase 5: 공통 기능 보강

* **SessionUtils**: `hasRole(String roleName)` 메서드를 추가하여 코드 내 권한 체크 편의성 증대.
* **SystemErrorCode**: `FORBIDDEN(1301, "접근 권한이 없습니다.")` 에러 코드 정의.

---

## 4. 캐싱 및 즉시 반영 전략

* **Initialization**: 애플리케이션 기동 시 `MenuService`에서 전용 캐시(Map)를 초기화한다.
* **Access**: `RoleInterceptor`는 DB 조회를 생략하고 오직 캐시된 데이터를 참조하여 응답 속도를 극대화한다.
* **Update**: 관리자가 메뉴 권한을 수정할 경우, `MenuFacade`에서 DB 업데이트 후 `MenuService.refreshCache()`를 호출하여 **즉시 반영**되도록 처리한다.

---

## 5. 구현 순서 권고

1. **Data**: Role + UserRole Entity/Repository 구현
2. **Security**: AuthUserService 수정 (DB 연동)
3. **Model**: MenuModel + MenuConverter 생성
4. **Core**: MenuService(캐싱/매칭) -> RoleInterceptor 구현
5. **API**: MenuFacade/Controller (CRUD + 권한 설정 API)
6. **Error**: SystemErrorCode.FORBIDDEN 정의