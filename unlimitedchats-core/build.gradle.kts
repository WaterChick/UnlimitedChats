plugins {
    id("java")
}

group = "dev.waterchick"
version = rootProject.findProperty("versionName") ?: "default-version"

repositories {
    mavenCentral()
    maven {url = uri("https://jitpack.io")}
}

dependencies {
    //testImplementation(platform("org.junit:junit-bom:5.9.1"))
    //testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.github.WaterChick:configwrapper:v1.0.0")
}

tasks.withType<JavaCompile> {
    doFirst {
        val props = mapOf("versionName" to project.version.toString())
        val file = file("src/main/java/dev/waterchick/Version.java") // Zkontroluj cestu k souboru

        // Nahrazení ${versionName} v souboru
        val content = file.readText()
        val newContent = content.replace("\${versionName}", props["versionName"].toString())
        file.writeText(newContent)
    }
}


tasks.test {
    useJUnitPlatform()
}