# NEXT_SESSION — 다음 세션에서 해야 할 일

## 1. 테스트 작성

이번 변경에 대한 단위 테스트 미작성 상태.

- `CacheEventPublishable.Publisher` — publish 호출 시 Redis convertAndSend 검증
- `CacheEventListener` — 메시지 수신 시 올바른 서비스의 refreshCache() 호출 검증
- `CodeFacade` — CUD 메서드 호출 시 publishCacheEvent() 호출 검증
- `MenuFacade` — CUD 메서드 호출 시 publishCacheEvent() 호출 검증
- `CodeService.refreshCache()` — JSON 직렬화/역직렬화 검증

## 2. Redis/Kafka 학습 로드맵

### Redis (다음 단계)

| 순서 | 주제 | 구현 아이디어 |
|------|------|-------------|
| 1 | **Rate Limiting** | API 호출 제한 — `INCR` + `EXPIRE`로 시간당 요청 수 제한 |
| 2 | **멱등성 키** | 중복 요청 방어 — `SETNX` + TTL로 같은 요청 2번 처리 방지 |
| 3 | **Sorted Set** | API 호출 통계, 접속 랭킹 |

### Kafka (신규 도입)

| 순서 | 주제 | 구현 아이디어 |
|------|------|-------------|
| 1 | **기본 Producer/Consumer** | 코드 생성 순차 처리 — 파티션 키(`codeGroupId`)로 순서 보장 |
| 2 | **이벤트 소싱** | 코드/메뉴 변경 이력을 Kafka 토픽에 기록 |
| 3 | **비동기 처리** | 파일 업로드 후 후처리를 Kafka로 비동기화 |
| 4 | **Dead Letter Queue** | 실패한 메시지 재처리 패턴 |

### 핵심 개념 메모

- **트랜잭션은 단일 DB 내 원자성만 보장** — 서버 간 동시성은 별도 해결 필요
- **Redis Lock**: 짧은 중복 방어 (멱등성, Rate Limiting) — 유실 가능성 있음
- **Kafka**: 유실 없는 순차 처리, 이벤트 기반 — 데이터 정합성이 중요한 경우
- **분산 환경에서 순차 처리 문제**: DB 프로시저, SELECT FOR UPDATE, Redis Lock, Kafka 파티션 등 선택지 존재

## 3. 아키텍처 현황

```
CacheEventPublishable (interface)
├── CHANNEL = "cache:invalidate"
├── getCacheEventPublisher()   → 구현체가 @Getter로 제공
├── getCacheType()             → 구현체가 전략 선언
├── default publishCacheEvent() → publisher.publish(getCacheType())
└── @Component Publisher       → StringRedisTemplate.convertAndSend() 래핑

CodeFacade implements CacheEventPublishable → getCacheType() = CODE
MenuFacade implements CacheEventPublishable → getCacheType() = MENU

CacheEventListener (MessageListener)
  → Redis "cache:invalidate" 구독
  → CacheType.fromValue() → codeService.refreshCache() / menuService.refreshCache()

CacheScheduler (기존 유지 — Pub/Sub 유실 시 안전망)
```
