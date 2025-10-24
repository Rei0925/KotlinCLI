plugins {
    kotlin("jvm") version "2.2.20"
}

group = "com.github.rei0925.kotlincli"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.reflections:reflections:0.10.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}