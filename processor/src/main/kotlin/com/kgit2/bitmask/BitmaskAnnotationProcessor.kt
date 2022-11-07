package com.kgit2.bitmask

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.kgit2.annotation.Bitmask
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory

@Factory
@ComponentScan
class BitmaskAnnotationProcessor(
    override val environment: SymbolProcessorEnvironment,
    private val visitor: BitmaskAbleVisitor,
) : SymbolProcessor, ProcessorBase {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = Bitmask::class.qualifiedName?.let { resolver.getSymbolsWithAnnotation(it) }
        symbols?.forEach { symbol ->
            logger.warn("Processing annotation", symbol)
        }
        return emptyList()
    }

}
