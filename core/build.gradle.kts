plugins {
    kotlin("jvm")
}

dependencies {
    api(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation("com.zaxxer:HikariCP:6.3.0")
    testImplementation(kotlin("test"))
    runtimeOnly("com.mysql:mysql-connector-j:9.2.0")
}

tasks.test {
    useJUnitPlatform()
}
