# ─────────────────────────────────────────
# Stage 1: Build
# ─────────────────────────────────────────
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# 의존성 캐싱을 위해 gradle 파일 먼저 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# gradlew 실행 권한 부여
RUN chmod +x gradlew

# 의존성 다운로드 (소스 변경 시 이 레이어는 캐시 재사용)
RUN ./gradlew dependencies --no-daemon

# 소스 복사 후 빌드
COPY src src
RUN ./gradlew clean bootJar --no-daemon -x test

# ─────────────────────────────────────────
# Stage 2: Run
# ─────────────────────────────────────────
FROM eclipse-temurin:17-jre

WORKDIR /app

# 빌드 결과물만 복사 (JDK 불필요, JRE만 사용 → 이미지 경량화)
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 프로필은 환경변수 SPRING_PROFILES_ACTIVE 로 주입 (dev/prod 환경변수로 구분)
ENTRYPOINT ["java", "-jar", "app.jar"]
