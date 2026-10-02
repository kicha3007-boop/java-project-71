plugins {
    application
    jacoco
    id("com.github.ben-manes.versions") version "0.52.0"
    id("com.diffplug.spotless") version "7.2.1"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "hexlet.code.App"
}

dependencies {
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.19.2")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat().aosp()
        formatAnnotations()
        endWithNewline()
    }
}

// Точка входа (CLI-обёртка) из покрытия исключена: её проверяет ручной запуск.
val coverageExcludes = listOf("hexlet/code/App.class")

fun JacocoReportBase.excludeEntryPoint() {
    classDirectories.setFrom(
        files(classDirectories.files.map { fileTree(it) { exclude(coverageExcludes) } }),
    )
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    excludeEntryPoint()
    reports {
        xml.required = true
        html.required = true
    }
}

tasks.test { finalizedBy(tasks.jacocoTestReport) }

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
    excludeEntryPoint()
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

tasks.check { dependsOn(tasks.jacocoTestCoverageVerification) }
