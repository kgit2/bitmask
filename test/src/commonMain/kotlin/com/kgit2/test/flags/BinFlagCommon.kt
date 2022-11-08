package com.kgit2.test.flags

import com.kgit2.annotation.Bitmask
import com.kgit2.test.types.MyLong

interface Test

@Bitmask
sealed class SealedClass(val value: MyLong, val value2: Test? = null, val value3: Long? = null) {
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
