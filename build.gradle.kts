plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.isabela"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    implementation("org.reflections:reflections:0.9.12")

    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.9.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}