package com.kgit2.test.flags

import com.kgit2.annotation.Bitmask
import flags.BinFlagCommon

@Bitmask
sealed class BinFlag(val value: Long) {
    object A : BinFlag(0b1)
    object B : BinFlag(0b10)
    object C : BinFlag(0b100)
    object D : BinFlag(0b1000)
    class Mask(value: Long) : BinFlag(value)
}

operator fun <T: BinFlagCommon> T.contains(other: BinFlagCommon): Boolean {
    return value and other.value != 0L
}

infix fun <T: BinFlagCommon> T.or(other: BinFlagCommon): BinFlagCommon {
    return BinFlagCommon.Mask(value or other.value)
}
