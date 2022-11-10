package com.kgit2.bitmask.model

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Visibility
import com.kgit2.annotation.Value

data class BitmaskModel(
    val packageName: String,
    val fileName: String,
    val fileExt: String,
    val visible: String,
    val className: String,
    val constructorFields: Collection<ConstructorField>? = null,
    val value: PropertyDeclaration,
    val literalSuffix: String,
    val mask: String = "Mask",
)

// abstract class Type(
//     open val name: String,
//     open val type: String,
// ) {
//     override fun toString(): String {
//         return "$name: $type${if (optional) "?" else ""}"
//     }
// }

data class ConstructorField(
    val name: String,
    val type: String,
    val nullable: Boolean,
    val node: KSNode,
) {
    override fun toString(): String {
        return "$name: $type${if (nullable) "?" else ""}"
    }
}

class PropertyDeclaration(
    val name: String,
    val type: String,
    val node: KSPropertyDeclaration
) {
    val nullable = node.type.resolve().isMarkedNullable
    val mutable = node.isMutable
    val visibility = node.getVisibility().toString()

    @OptIn(KspExperimental::class)
    val valueAnnotation = node.isAnnotationPresent(Value::class)

    override fun toString(): String {
        return "${if (mutable) "val" else "var"} $name: $type${if (nullable) "?" else ""}"
    }
}
