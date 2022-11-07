package com.kgit2.bitmask

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

@Single
@ComponentScan
class BitmaskProcessor(
    val environment: SymbolProcessorEnvironment,
    private val annotationProcessor: BitmaskAnnotationProcessor,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        annotationProcessor.process(resolver)
        return emptyList()
    }
}

@Single
@ComponentScan
class Test(@InjectedParam var abc: String): SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        return emptyList()
    }
}
