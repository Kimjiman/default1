# Default Project

Spring Boot 2.7 web application (Java 17, Gradle 7.6)

## Tech Stack

- Spring Boot 2.7.18 (Security, JPA, Thymeleaf, WebFlux)
- JPA + QueryDSL 5.0 / MapStruct 1.5.5
- Spring Security + JWT (jjwt 0.11.5)
- MySQL / Redis

## Architecture

```
Controller → Facade → Service → Repository (JPA + QueryDSL)
                ↕
            Converter (MapStruct)
```

- Entity / Model(DTO) separation
- Facade pattern for cross-service orchestration

## Profiles

| Profile | Port | Description |
|---------|------|-------------|
| `local` (default) | 8085 | DevTools enabled, show-sql on |
| `dev` | 8080 | File logging (`/web/jar/log/file.log`) |
| `prod` | 8080 | show-sql off, file logging |

## Build & Run

### Windows (CMD / PowerShell)

```bash
set JAVA_HOME=C:\java\jdk-17.0.18+8

# Build
gradlew.bat clean build

# Run - local (default, port 8085)
gradlew.bat bootRun

# Run - dev (port 8080)
gradlew.bat bootRun -PspringProfiles=dev

# Run - prod (port 8080)
gradlew.bat bootRun -PspringProfiles=prod
```

### Git Bash / Linux / macOS

```bash
export JAVA_HOME=/c/java/jdk-17.0.18+8  # Git Bash
# export JAVA_HOME=/usr/lib/jvm/java-17  # Linux/macOS

# Build
./gradlew clean build

# Run - local (default, port 8085)
./gradlew bootRun

# Run - dev (port 8080)
./gradlew bootRun -PspringProfiles=dev

# Run - prod (port 8080)
./gradlew bootRun -PspringProfiles=prod
```

## API Docs

- Swagger UI: `{base_url}/swagger-ui/index.html`
