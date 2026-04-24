
import { WASI } from 'wasi';
import { argv, env } from 'node:process';
import "./custom-formatters.js"

const wasi = new WASI({ version: 'preview1', args: argv, env, });

const fs = await import('node:fs');
const url = await import('node:url');
const wasmBuffer = fs.readFileSync(url.fileURLToPath(import.meta.resolve('./wasm-echo.wasm')));
const wasmModule = new WebAssembly.Module(wasmBuffer);
const wasmInstance = new WebAssembly.Instance(wasmModule, wasi.getImportObject());

wasi.start(wasmInstance);

const exports = wasmInstance.exports

export {
    exports as __ALL_EXPORTS,

}

export const {
    memory,
    _start
} = exports

