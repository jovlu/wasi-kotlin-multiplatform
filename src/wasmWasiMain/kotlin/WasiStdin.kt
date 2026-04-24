import kotlin.wasm.ExperimentalWasmInterop
import kotlin.wasm.WasmImport
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

@OptIn(ExperimentalWasmInterop::class)
@WasmImport("wasi_snapshot_preview1", "fd_read")
external fun fdRead(fd: Int, iovs: Int, iovsLen: Int, nread: Int): Int

@OptIn(UnsafeWasmMemoryApi::class)
fun readWasiLine(): String? {
    withScopedMemoryAllocator { a ->
        val line = StringBuilder()
        val buffer = a.allocate(1)
        val iovs = a.allocate(8)
        val nread = a.allocate(4)

        iovs.storeInt(buffer.address.toInt())
        (iovs + 4).storeInt(1)

        while (true) {
            if (fdRead(0, iovs.address.toInt(), 1, nread.address.toInt()) != 0 || nread.loadInt() == 0) {
                return if (line.isEmpty()) null else line.toString()
            }

            when (val c = buffer.loadByte().toInt().toChar()) {
                '\n' -> return line.toString()
                else -> line.append(c)
            }
        }
    }
}