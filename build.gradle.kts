plugins {
    id("java")
}


group = "dev.waterchick"
version = "1.0.5"
val versionName: String by extra // Definice versionName

extra["versionName"] = version

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

allprojects {
    version = findProperty("versionName") ?: "default-version"
}

