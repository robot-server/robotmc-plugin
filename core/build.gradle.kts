plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    api(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.websockets)
    api(libs.ktor.serialization.kotlinx.json)
    api("io.github.oshai:kotlin-logging-jvm:7.0.7")
    api(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    api(libs.exposed.kotlin.datetime)
    implementation("com.zaxxer:HikariCP:6.3.0")
    testImplementation(kotlin("test"))
    runtimeOnly("com.mysql:mysql-connector-j:9.2.0")
}

tasks.test {
    useJUnitPlatform()
}
