plugins {
    kotlin("jvm") version "2.2.20"
    signing
}

group = "com.github.rei0925.kotlincli"
version = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    api("org.reflections:reflections:0.10.2")
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jline:jline:3.26.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}