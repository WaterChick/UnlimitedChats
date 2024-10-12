plugins {
    id("java")
    id ("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.0"
}


group = "dev.waterchick"
version = "1.0.5"
val versionName: String by extra // Definice versionName

extra["versionName"] = version

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.named<Jar>("shadowJar").get()) // Používáme shadowJar jako artefakt

            // Základní metadata
            groupId = group.toString()
            artifactId = project.name // změň na tvůj artifact id
            version = extra["versionName"] as String // změň na požadovanou verzi
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/WaterChick/unlimitedchats") // Změň na skutečnou URL repozitáře
            credentials {
                username = System.getenv("USERNAME") // Automaticky se nastaví při běhu workflow
                password = System.getenv("TOKEN") // Automaticky se nastaví při běhu workflow
            }
        }
    }
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

