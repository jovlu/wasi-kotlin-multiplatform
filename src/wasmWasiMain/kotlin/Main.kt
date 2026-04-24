fun main() {
    while (true) {
        val line = readWasiLine() ?: break
        println("Wasm received: $line")
    }
}