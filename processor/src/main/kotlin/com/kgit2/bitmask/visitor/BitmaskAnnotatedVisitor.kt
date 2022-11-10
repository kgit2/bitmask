package com.kgit2.bitmask.visitor

import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.isOpen
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.symbol.Visibility
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import com.kgit2.bitmask.ProcessorBase
import com.kgit2.bitmask.model.BitmaskModel
import com.kgit2.bitmask.model.ConstructorField
import com.kgit2.bitmask.model.PropertyDeclaration
import koin
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory

@Factory
@ComponentScan
class BitmaskAnnotatedVisitor(
    override val environment: SymbolProcessorEnvironment
) : KSDefaultVisitor<Unit?, BitmaskModel?>(), ProcessorBase {
    override fun defaultHandler(node: KSNode, data: Unit?): BitmaskModel? {
        if (node !is KSClassDeclaration) return null
        return defaultHandler(node, data)
    }

    private fun defaultHandler(node: KSClassDeclaration, data: Unit?): BitmaskModel? {
        if (!validate(node)) return null
        val fileName = if (node.parent is KSFile) {
            (node.parent as KSFile).fileName
        } else {
            null
        }

        val constructorVisitor = koin.get<ConstructorVisitor>()
        val fieldsMap = node.primaryConstructor?.accept(constructorVisitor, null)
            ?.associateBy(ConstructorField::name)

        val propertyVisitor = koin.get<PropertyVisitor>()
        val propertyDeclarations = node.getAllProperties()
            .mapNotNull { it.accept(propertyVisitor, null) }

        if (!checkConstructorFieldVisibility(propertyDeclarations, fieldsMap)) {
            return null
        }

        val value: PropertyDeclaration? = kotlin.runCatching {
            propertyDeclarations.first { it.valueAnnotation && AllowTypes.contains(it.type) }
        }.recoverCatching {
            propertyDeclarations.first { it.name == "value" && AllowTypes.contains(it.type) }
        }.recoverCatching {
            propertyDeclarations.first { AllowTypes.contains(it.type) }
        }.getOrNull()

        if (value == null) {
            logger.error("""
                |No suitable property found for mask.
                |@Bitmask will look for suitable property according to the following priorities
                |1. annotate a property with @Value
                |2. named `value`
                |3. any property with type of Int, UInt, Long, ULong, Short, UShort, Byte or UByte.
                |note: Any attribute must be with type of Int, UInt, Long, ULong, Short, UShort, Byte or UByte.
            """.trimMargin(), node)
            return null
        }
        logger.info("Found value property: $value", node)
        if (fieldsMap?.contains(value.name) != true) {
            when {
                value.visibility != Visibility.PUBLIC.toString() -> {
                    logger.error("Property named `${value.name}` of type: `${value.type}` could not visit.\nChange it to `${Visibility.PUBLIC}`", value.node)
                    return null
                }
                !value.mutable -> {
                    logger.error("Property named `${value.name}` of type: `${value.type}` could not be mutable.\nChange it to `Var`", value.node)
                    return null
                }
                else -> {
                    logger.error("Property named `${value.name}` of type: `${value.type}` could not be mutable.\nPut it into primary constructor.", value.node)
                    return null
                }
            }
        }

        return BitmaskModel(
            packageName = node.packageName.asString(),
            fileName = fileName?.split(".")?.first() ?: node.simpleName.asString(),
            fileExt = fileName?.split(".")?.last() ?: "kt",
            className = node.simpleName.asString(),
            constructorFields = fieldsMap.values,
            value = PropertyDeclaration(
                name = "value",
                type = "kotlin.Int",
                node = value.node,
            ),
            literalSuffix = "L",
            visible = "val"
        )
    }

    private fun validate(node: KSClassDeclaration): Boolean {
        val intersect = node.modifiers.intersect(NotAllowModifiers)
        return when {
            !node.isOpen() -> {
                logger.warn("Class must be open", node)
                false
            }

            node.isCompanionObject -> {
                logger.warn("Class must not be companion object", node)
                false
            }

            node.typeParameters.isNotEmpty() -> {
                logger.warn("Class must not have generic type", node)
                false
            }

            node.isAbstract() -> {
                logger.warn("Class must not be abstract", node)
                false
            }

            intersect.isNotEmpty() -> {
                logger.error("Class must not be: {${intersect.joinToString(", ")}}", node)
                false
            }

            else -> true
        }
    }

    private fun checkConstructorFieldVisibility(
        propertyDeclarations: Sequence<PropertyDeclaration>,
        fieldsMap: Map<String, ConstructorField>?
    ): Boolean {
        propertyDeclarations.associateBy(PropertyDeclaration::name).apply {
            for ((name, field) in fieldsMap ?: emptyMap()) {
                if (name in this && this[name]?.visibility != Visibility.PUBLIC.toString()) {
                    logger.error("Property `${this[name]}` cannot be accessed.", this[name]!!.node)
                    return false
                }
            }
        }
        return true
    }

    companion object {
        val AllowTypes = setOf(
            "kotlin.Int",
            "kotlin.UInt",
            "kotlin.Long",
            "kotlin.ULong",
            "kotlin.Short",
            "kotlin.UShort",
            "kotlin.Byte",
            "kotlin.UByte",
        )

        val NotAllowModifiers = setOf(
            Modifier.DATA,
            Modifier.ENUM,
            Modifier.SEALED,
            Modifier.ANNOTATION,
            Modifier.INNER,
            Modifier.ACTUAL,
            Modifier.PRIVATE,
        )
    }
}
