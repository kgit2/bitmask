package com.kgit2.bitmask.model

data class BitmaskModel(
    val packageName: String,
    val fileName: String,
    val fileExt: String,
    val visible: String,
    val className: String,
    val constructorFields: List<ConstructorField>? = null,
    val literalSuffix: String,
    val mask: String = "Mask",
)

interface Type {
    val name: String
    val type: String
}

data class ConstructorField(
    override val name: String,
    override val type: String,
) : Type {
    override fun toString(): String {
        return "$name: $type"
    }
}

data class PropertyDeclaration(
    override val name: String,
    override val type: String,
) : Type {
    override fun toString(): String {
        return "$name: $type"
    }
}
