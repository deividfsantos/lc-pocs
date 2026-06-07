plugins {
    id("java")
    id("application")
}

group = "com.dsantos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass = "com.dsantos.Main"
}

