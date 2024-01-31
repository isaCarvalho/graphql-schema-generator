plugins {
    kotlin("jvm") version "1.9.0"
    application

    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

group = "org.isabela"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.0")

    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.9.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
