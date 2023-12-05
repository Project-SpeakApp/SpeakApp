# Backend services of Project SpeakApp

## Build with docker compose
```
docker compose build
```

## Run with docker compose
```
docker compose up
```

## Run locally (with h2 database)

### From IntelliJ 
Edit spring run configuration and set active profiles to 'local'

### From command line (windows)
```
set SPRING_PROFILES_ACTIVE=local && .\mvnw spring-boot:run
```

### From command line (unix)
```
SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run
```
