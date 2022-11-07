package com.kgit2.bitmask

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.visitor.KSValidateVisitor

class BitMaskProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    val logger = environment.logger

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotationChecker = KSValidateVisitor { a, b ->
            false
        }
        resolver.getAllFiles().forEach { file ->
            annotationChecker.
            logger.info(file.fileName)

        }
        return emptyList()
    }
}
