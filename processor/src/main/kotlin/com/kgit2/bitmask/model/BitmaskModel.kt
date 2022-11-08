package com.kgit2.bitmask.model

data class BitmaskModel(
    val packageName: String,
    val fileName: String,
    val fileExt: String,
    val classDeclare: String,
    val value: String,
    val literalSuffix: String,
    val mask: String = "Mask",
)
