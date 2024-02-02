import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    id("com.google.devtools.ksp") version "1.9.21-1.0.16"

    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.21"
    kotlin("kapt") version "1.9.21"
}

group = "org.ryan.app"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.21")

    // kotlin-logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    // Arrow
    implementation("io.arrow-kt:arrow-core:1.2.0")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.2.0")
    implementation("io.arrow-kt:arrow-optics:1.2.0")
    ksp("io.arrow-kt:arrow-optics-ksp-plugin:1.2.0")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    //	Database
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    // Transaction
    // implementation("com.atomikos:transactions-spring-boot3-starter:6.0.0")

    // Mariadb / mySQL
    implementation("mysql:mysql-connector-java:8.0.33")
    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.named("compileKotlin") {
    dependsOn(":kaptGenerateStubsQuerydslKotlin")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
