package com.kgit2.bitmask.visitor

import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.isOpen
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import com.kgit2.bitmask.ProcessorBase
import com.kgit2.bitmask.model.BitmaskModel
import com.kgit2.bitmask.model.ConstructorField
import koin
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import java.util.*

internal val priorityType: PriorityQueue<String> = PriorityQueue(listOf(
    "kotlin.Int",
    "kotlin.UInt",
    "kotlin.Long",
    "kotlin.ULong",
    "kotlin.Short",
    "kotlin.UShort",
))

@Factory
@ComponentScan
class BitmaskAnnotatedVisitor(
    override val environment: SymbolProcessorEnvironment
) : KSDefaultVisitor<Unit?, BitmaskModel?>(), ProcessorBase {
    override fun defaultHandler(node: KSNode, data: Unit?): BitmaskModel? {
        val result = when (node) {
            is KSClassDeclaration -> {
                if (!validate(node)) return null
                val fileName = if (node.parent is KSFile) {
                    (node.parent as KSFile).fileName
                } else {
                    null
                }
                val fields =  if (node.primaryConstructor == null) {
                    null
                } else {
                    node.primaryConstructor!!.parameters.map {
                        ConstructorField(
                            name = it.name!!.asString(),
                            type = it.type.resolve().declaration.qualifiedName!!.asString()
                        )
                    }
                }
                val propertyVisitor = koin.get<PropertyVisitor>()
                node.getAllProperties().forEach { property ->
                    val propertyDeclaration = property.accept(propertyVisitor, null)
                }
                BitmaskModel(
                    packageName = node.packageName.asString(),
                    fileName = fileName?.split(".")?.first() ?: node.simpleName.asString(),
                    fileExt = fileName?.split(".")?.last() ?: "kt",
                    className = node.simpleName.asString(),
                    constructorFields = fields,
                    literalSuffix = "L",
                    visible = "val"
                )
            }

            else -> {
                logger.warn("Unknown Type", node)
                null
            }
        }
        return result
    }

    private fun validate(node: KSClassDeclaration): Boolean {
        return when {
            node.isCompanionObject -> false
            node.typeParameters.isNotEmpty() -> false
            node.isAbstract() -> false
            !node.isOpen() -> false
            else -> true
        }
    }

    private fun findValue(node: KSClassDeclaration): Boolean {
        node.getAllProperties().forEach { property ->
            val resolvedType = property.type.resolve().declaration
            // Long::class.
        }
        return false
    }

}
