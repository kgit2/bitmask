package flags

// import com.kgit2.annotation.BitMask

// @BitMask
sealed class BinFlagNative(val value: Long) {
    object A : BinFlagNative(0b1)
    object B : BinFlagNative(0b10)
    object C : BinFlagNative(0b100)
    object D : BinFlagNative(0b1000)
    class Mask(value: Long) : BinFlagNative(value)
}

operator fun <T: BinFlagNative> T.contains(other: BinFlagNative): Boolean {
    return value and other.value != 0L
}

infix fun <T: BinFlagNative> T.or(other: BinFlagNative): BinFlagNative {
    return BinFlagNative.Mask(value or other.value)
}
