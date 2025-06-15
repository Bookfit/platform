plugins {
	java
	id("org.springframework.boot") version "3.3.13-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bookfit"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springCloudVersion"] = "2023.0.5"
val querydslVersion = "5.1.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("org.postgresql:postgresql")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.hibernate:hibernate-spatial:6.4.4.Final")
	implementation("org.locationtech.jts:jts-core:1.18.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
	annotationProcessor("com.querydsl:querydsl-apt:$querydslVersion:jakarta")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
