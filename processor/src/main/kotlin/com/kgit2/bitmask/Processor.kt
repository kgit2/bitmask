package com.kgit2.bitmask

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.kgit2.bitmask.processor.BitmaskAnnotationProcessor
import koin
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Single
import org.koin.dsl.koinApplication

@Single
@ComponentScan
class BitmaskProcessor(
    override val environment: SymbolProcessorEnvironment,
) : SymbolProcessor, ProcessorBase {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        koin.get<BitmaskAnnotationProcessor>().process(resolver)
        return emptyList()
    }
}

