plugins {
    kotlin("jvm") version "2.2.20"
    `maven-publish`
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = group.toString()
            artifactId = "kotlincli"
            version = version.toString()

            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())

            pom {
                name.set("KotlinCLI")
                description.set("A Kotlin command line interface library")
                url.set("https://github.com/rei0925/KotlinCLI")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("rei0925")
                        name.set("rei0925")
                        email.set("rei_0926@outlook.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/rei0925/KotlinCLI.git")
                    developerConnection.set("scm:git:ssh://github.com/rei0925/KotlinCLI.git")
                    url.set("https://github.com/rei0925/KotlinCLI")
                }
            }
        }
    }
}