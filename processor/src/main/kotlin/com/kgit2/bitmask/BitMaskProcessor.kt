package com.kgit2.bitmask

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.google.devtools.ksp.visitor.KSValidateVisitor

class BitMaskProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    val logger = environment.logger

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotationChecker = KSValidateVisitor { annotation, data ->
            logger.info(annotation.toString())
            logger.info(data.toString())
            true
        }
        resolver.getAllFiles().forEach { file ->
            logger.info(file.fileName)
            file.declarations.forEach { declaration ->
                logger.info(declaration.toString())
                declaration.validate { annotation, data ->
                    logger.info(annotation.toString())
                    logger.info(data.toString())
                    true
                }
            }
        }
        return emptyList()
    }
}
