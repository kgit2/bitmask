package com.kgit2.bitmask.visitor

import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.symbol.Visibility
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import com.kgit2.bitmask.ProcessorBase
import com.kgit2.bitmask.model.ConstructorField
import org.koin.core.annotation.Factory

@Factory
class ConstructorVisitor(
    override val environment: SymbolProcessorEnvironment
) : KSDefaultVisitor<Unit?, List<ConstructorField>?>(), ProcessorBase {
    private fun defaultHandler(node: KSFunctionDeclaration, data: Unit?): List<ConstructorField>? {
        if (!validateVisibility(node)) return null
        return node.parameters.map {
            ConstructorField(it.name!!.asString(), it.type.resolve().declaration.qualifiedName!!.asString(), it.type.resolve().isMarkedNullable, it)
        }
    }

    override fun defaultHandler(node: KSNode, data: Unit?): List<ConstructorField>? {
        if (node !is KSFunctionDeclaration) return null
        return defaultHandler(node, data)
    }

    private fun validateVisibility(node: KSDeclaration): Boolean {
        return when (node.getVisibility()) {
            Visibility.PUBLIC -> true
            Visibility.INTERNAL -> true
            else -> false
        }
    }

    companion object {
        val NotAllowModifiers = setOf(
            Modifier.PROTECTED,
            Modifier.PRIVATE,
        )
    }
}
