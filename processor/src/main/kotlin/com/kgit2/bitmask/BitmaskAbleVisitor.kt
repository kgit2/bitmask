package com.kgit2.bitmask

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory

@Factory
@ComponentScan
class BitmaskAbleVisitor(
    override val environment: SymbolProcessorEnvironment
) : KSDefaultVisitor<Unit, KSAnnotated>(), ProcessorBase {
    override fun defaultHandler(node: KSNode, data: Unit): KSAnnotated {
        TODO("Not yet implemented")
    }
}
