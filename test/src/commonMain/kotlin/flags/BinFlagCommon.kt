package flags

// import com.kgit2.annotation.BitMask

// @BitMask
sealed class BinFlagCommon(val value: Long) {
    object A : BinFlagCommon(0b1)
    object B : BinFlagCommon(0b10)
    object C : BinFlagCommon(0b100)
    object D : BinFlagCommon(0b1000)
    class Mask(value: Long) : BinFlagCommon(value)
}

operator fun <T: BinFlagCommon> T.contains(other: BinFlagCommon): Boolean {
    return value and other.value != 0L
}

infix fun <T: BinFlagCommon> T.or(other: BinFlagCommon): BinFlagCommon {
    return BinFlagCommon.Mask(value or other.value)
}
