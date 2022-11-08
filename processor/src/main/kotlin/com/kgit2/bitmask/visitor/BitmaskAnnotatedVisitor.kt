package com.kgit2.bitmask.visitor

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import com.kgit2.bitmask.ProcessorBase
import com.kgit2.bitmask.model.BitmaskModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory

@Factory
@ComponentScan
class BitmaskAnnotatedVisitor(
    override val environment: SymbolProcessorEnvironment
) : KSDefaultVisitor<Unit?, BitmaskModel?>(), ProcessorBase {
    override fun defaultHandler(node: KSNode, data: Unit?): BitmaskModel? {
        val result = when (node) {
            is KSClassDeclaration -> {
                logger.warn("${node.classKind.toString()} ${node.packageName.asString()}", node)
                val fileName = if (node.parent is KSFile) {
                    (node.parent as KSFile).fileName
                } else {
                    null
                }
                BitmaskModel(
                    packageName = node.packageName.asString(),
                    fileName = fileName?.split(".")?.first() ?: node.simpleName.asString(),
                    fileExt = fileName?.split(".")?.last() ?: "kt",
                    classDeclare = node.simpleName.asString(),
                    value = "value",
                    literalSuffix = "L",
                )
            }

            else -> {
                logger.warn("Unknown Type", node)
                null
            }
        }
        return result
    }
}
