plugins {
    kotlin("jvm") version "1.4.31"
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation("com.beust:klaxon:5.5")
    testImplementation("io.kotest:kotest-runner-junit5:4.3.1")
}