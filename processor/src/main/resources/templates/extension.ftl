<#if packageName?has_content>
package ${packageName}
</#if>

sealed class ${className}${mask}<#if constructorFields?has_content>(${constructorFields?join(", ")})</#if> : ${className}(value) {
    class Mask(value: Long) : ${className}(value)
}

operator fun <T: ${className}> T.contains(other: ${className}): Boolean {
    return (${value} and other.${value}) != 0${literalSuffix}
}

infix fun <T: ${className}> T.or(other: ${className}): ${className} {
    return ${className}${mask}.${mask}(value or other.value)
}
