package com.kgit2.test

import com.kgit2.test.flags.BinFlag
import com.kgit2.test.flags.contains

fun main() {
    val flag = BinFlag.Mask(0b11111)
    when {
        BinFlag.A in flag -> println("A")
        BinFlag.B in flag -> println("B")
        BinFlag.C in flag -> println("C")
        BinFlag.D in flag -> println("D")
    }
}
