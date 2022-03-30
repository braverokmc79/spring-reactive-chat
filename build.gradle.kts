plugins {
    java
    id("org.springframework.boot") version "2.2.13.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.adarshr.test-logger") version "3.2.0"
    id("org.unbroken-dome.test-sets") version "4.0.0"
}

group = "de.htwsaar.vs"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.auth0:java-jwt:3.12.0")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.bootRun {
    if (project.hasProperty("args")) {
        args = (project.properties["args"] as String).split(",")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

testSets {
    create("integrationTest") {
        dirName = "it"
    }
}

tasks {
    check {
        dependsOn("integrationTest")
    }
}

tasks.named("integrationTest") {
    shouldRunAfter("test")
}
