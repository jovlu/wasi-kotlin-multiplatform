plugins {
    kotlin("multiplatform") version "2.3.21"
}
repositories {
    mavenCentral()
}
kotlin {
    wasmWasi {
        binaries.executable()
    }
}
tasks.register<Exec>("runWasm") {
    dependsOn("compileDevelopmentExecutableKotlinWasmWasi")
    outputs.upToDateWhen { false }

    commandLine(
        "cmd", "/c",
        "start", "\"wasm\"", "/wait",
        "wasmtime",
        "-W", "gc",
        "-W", "function-references",
        "-W", "exceptions",
        "build\\compileSync\\wasmWasi\\main\\developmentExecutable\\kotlin\\wasm-echo.wasm"
    )
}
