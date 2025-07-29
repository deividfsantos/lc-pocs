plugins {
    java
    application
}

group = "com.dsantos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


application {
    mainClass.set("com.dsantos.logger.demo.GradleDemo")
}

tasks.test {
    useJUnitPlatform()
}