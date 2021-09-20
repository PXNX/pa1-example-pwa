plugins {
    id("org.jetbrains.kotlin.js") version "1.5.30" //apply false
}

group = "nyx"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.244-kotlin-1.5.30")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.244-kotlin-1.5.30")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:5.2.0-pre.246-kotlin-1.5.30")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.1-pre.244-kotlin-1.5.30")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled-next:0.1-pre.244-kotlin-1.5.30")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.5.2-native-mt")
    implementation(npm("react-loader-spinner", "3.1.14"))
    implementation(npm("styled-components", "~5.2.1"))

    implementation("io.ktor:ktor-client-core:1.6.3")
    implementation("io.ktor:ktor-client-js:1.6.3")
    implementation("io.ktor:ktor-client-serialization:1.6.3")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }
}

afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
    }
}

tasks.register("stage") {
    dependsOn("build")
}