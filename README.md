# spring-reactive-chat

Spring WebFlux로 구축된 채팅 애플리케이션


[Vue.js로 구축된 프론트엔드](https://github.com/nireinhard/VueChat)

## 요구 사항

- Java 17
- MongoDB 3.6+

## 시작하기

### 소스에서 빌드

이 프로젝트는 [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html)와 [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl)을 사용합니다. HTML).

프로젝트를 빌드하려면 다음을 사용하십시오.

```Shell
gradlew clean build
```

### 애플리케이션 실행


애플리케이션을 실행하려면 Spring Boot Gradle 플러그인에서 제공하는 작업 중 하나를 사용할 수 있습니다.

```Shell
gradlew bootRun
```

또한 특정 애플리케이션 프로필을 설정할 수도 있습니다. `dev`의 경우:

```Shell
gradlew bootRun --args='--spring.profiles.active=dev'
```

## 몽고DB 사용하기

이 프로젝트는 [MongoDB Change Streams](https://www.mongodb.com/docs/manual/changeStreams/)를 사용하여 `text/ event-stream` 미디어 유형.

> 변경 스트림 지원은 복제본 세트 또는 sharded  클러스터에 대해서만 가능합니다.
>
> &mdash; [Spring Data MongoDB Reference Documentation](https://docs.spring.io/spring-data/mongodb/docs/3.3.3/reference/html/#change-streams)

독립 실행형 MongoDB 인스턴스를 실행하고 앞서 언급한 엔드포인트를 호출하면 예외가 발생합니다.

> com.mongodb.MongoCommandException: 명령이 오류 40573으로 실패했습니다: 서버 localhost:27017의 '$changeStream 단계는 복제본 세트에서만 지원됩니다.'

이 [튜토리얼]을 참조하십시오.(https://www.mongodb.com/docs/manual/tutorial/deploy-replica-set/) 복제 세트를 배포합니다.

포함된 [Docker Compose 파일](docker-compose.yml)을 사용하여 로컬에서 시작하세요.
