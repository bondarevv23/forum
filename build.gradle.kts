plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.github.bondarevv23"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// starters
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// db
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("org.postgresql:postgresql")
	// lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// docker
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")

	// tests
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
}

buildscript {
	dependencies {
		classpath("org.openapitools:openapi-generator-gradle-plugin:7.4.0")
	}
}

apply(plugin = "org.openapi.generator")
apply(from="gradle/swagger.gradle")

tasks.withType<Test> {
	useJUnitPlatform()
}
