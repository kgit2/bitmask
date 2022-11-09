package com.kgit2.bitmask.visitor

import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSTypeAlias
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import com.kgit2.bitmask.ProcessorBase
import com.kgit2.bitmask.model.PropertyDeclaration
import org.koin.core.annotation.Factory

@Factory
class PropertyVisitor(
    override val environment: SymbolProcessorEnvironment,
) : KSDefaultVisitor<Unit?, PropertyDeclaration?>(), ProcessorBase {
    override fun defaultHandler(node: KSNode, data: Unit?): PropertyDeclaration? {
        return when (node) {
            is KSPropertyDeclaration -> when (val nodeType = node.type.resolve().declaration) {
                is KSTypeAlias -> PropertyDeclaration(node.qualifiedName!!.getShortName(), nodeType.findActualType().qualifiedName!!.asString(), node)
                else -> PropertyDeclaration(node.qualifiedName!!.getShortName(), nodeType.qualifiedName!!.asString(), node)
            }
            else -> null
        }
    }
}

fun KSTypeAlias.findActualType(): KSClassDeclaration {
    val resolvedType = this.type.resolve().declaration
    return if (resolvedType is KSTypeAlias) {
        resolvedType.findActualType()
    } else {
        resolvedType as KSClassDeclaration
    }
}
