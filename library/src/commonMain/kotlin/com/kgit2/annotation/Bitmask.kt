package com.kgit2.annotation

@Target(
    AnnotationTarget.CLASS,
)
annotation class Bitmask()

@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.FIELD
)
annotation class Value()
