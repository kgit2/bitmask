package com.kgit2.test.flags

import com.kgit2.annotation.Bitmask
import flags.BinFlagCommon

@Bitmask
sealed class SealedClassFlag(val value: Long) {
    object A : SealedClassFlag(0b1)
    object B : SealedClassFlag(0b10)
    object C : SealedClassFlag(0b100)
    object D : SealedClassFlag(0b1000)
}

operator fun <T: BinFlagCommon> T.contains(other: BinFlagCommon): Boolean {
    return value and other.value != 0L
}

infix fun <T: BinFlagCommon> T.or(other: BinFlagCommon): BinFlagCommon {
    return BinFlagCommon.Mask(value or other.value)
}

// @Bitmask
// enum class EnumClassFlag(val value: Long) {
//     A(0b1),
//     B(0b10),
//     C(0b100),
//     D(0b1000),
//     Mask(0b1111)
// }
