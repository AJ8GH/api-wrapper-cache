# Weather API Wrapper

[![build](https://github.com/AJ8GH/api-wrapper-cache/actions/workflows/build.yaml/badge.svg)](https://github.com/AJ8GH/api-wrapper-cache/actions/workflows/build.yaml)
![coverage](https://img.shields.io/endpoint?url=https://gist.githubusercontent.com/aj8gh/123515634bcaab172200ad406ee83c06/raw/api-wrapper-cache-coverage-badge.json)

Rest API written in Kotlin.

Wrapper for existing weather API https://www.visualcrossing.com/weather-api/

Uses Redis to cache responses for each city. (TODO)

## Set up and run

### Clone the repo

```sh
git clone git@github.com:AJ8GH/api-wrapper-cache.git

cd api-wrapper-cache
```

### Local

```sh
./gradle bootRun
```

### Docker

```sh
docker compose up
```

### Running Tests

```sh
./gradlew test
```

## Dependencies

> Library and plugin versions defined in `gradle.properties`

- Kotlin `2.1.21`
- JDK `21`
- Gradle `8.14.1`
- Spring Boot `3.5.0`
  - spring-boot-starter-web
  - spring-boot-starter-actuator
  - spring-boot-starter-test
- kotlin-logging-jvm
- okhttp
- mockk
- kotest-runner-junit5
- kotest-extensions-spring
- kotest-assertions-core
- kotest-framework-datatest
- kotlin-wiremock
