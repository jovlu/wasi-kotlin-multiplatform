plugins {
    kotlin("multiplatform") version "2.3.21"
}
repositories {
    mavenCentral()
}
kotlin {
    wasmWasi {
        binaries.executable()
        nodejs()
    }
}