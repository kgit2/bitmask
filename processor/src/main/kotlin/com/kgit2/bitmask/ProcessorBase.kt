package com.kgit2.bitmask

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import org.koin.core.annotation.InjectedParam

interface ProcessorBase {
    val environment: SymbolProcessorEnvironment
    val options: Map<String, String>
        get() = environment.options
    val codeGenerator
        get() = environment.codeGenerator
    val logger
        get() = environment.logger
}
