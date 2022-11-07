package com.kgit2.test

import flags.BinFlagCommon
import flags.contains

fun main() {
    val flag = BinFlagCommon.Mask(0b11111)
    when {
        BinFlagCommon.A in flag -> println("A")
        BinFlagCommon.B in flag -> println("B")
        BinFlagCommon.C in flag -> println("C")
        BinFlagCommon.D in flag -> println("D")
    }
}
