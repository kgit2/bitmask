<#if packageName?has_content>
package ${packageName}
</#if>

sealed class ${classDeclare}${mask}(value: Long) : ${classDeclare}(value) {
    class Mask(value: Long) : ${classDeclare}(value)
}

operator fun <T: ${classDeclare}> T.contains(other: ${classDeclare}): Boolean {
    return (${value} and other.${value}) != 0${literalSuffix}
}

infix fun <T: ${classDeclare}> T.or(other: ${classDeclare}): ${classDeclare} {
    return ${classDeclare}${mask}.${mask}(value or other.value)
}
