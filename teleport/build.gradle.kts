plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":core"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
