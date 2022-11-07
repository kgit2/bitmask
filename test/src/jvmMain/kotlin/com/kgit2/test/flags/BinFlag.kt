package com.kgit2.test.flags

// import com.kgit2.annotation.BitMask

// @BitMask
sealed class BinFlag(val value: Long) {
    object A : BinFlag(0b1)
    object B : BinFlag(0b10)
    object C : BinFlag(0b100)
    object D : BinFlag(0b1000)
    class Mask(value: Long) : BinFlag(value)
}

operator fun <T: BinFlag> T.contains(other: BinFlag): Boolean {
    return value and other.value != 0L
}

infix fun <T:BinFlag> T.or(other: BinFlag): BinFlag {
    return BinFlag.Mask(value or other.value)
}
