package com.kgit2.test.flags

import com.kgit2.annotation.Bitmask
import com.kgit2.annotation.Value
import com.kgit2.test.types.MyLong

interface Test

data class User(val value: Int)

@Bitmask
open class SealedClass constructor(val value1: User, val value2: Test? = null, val value3: User? = null) {
    var value4: Int = value1.value
    object A : SealedClass(0b1)
    object B : SealedClass(0b10)
    object C : SealedClass(0b100)
    object D : SealedClass(0b1000)
}

// @Bitmask
// sealed class SealedClassFlag(val value: Long) {
//     object A : SealedClassFlag(0b1)
//     object B : SealedClassFlag(0b10)
//     object C : SealedClassFlag(0b100)
//     object D : SealedClassFlag(0b1000)
// }
