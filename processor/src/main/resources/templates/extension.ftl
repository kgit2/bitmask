<#if packageName?has_content>
package ${packageName}
</#if>

<#if constructorFields?has_content>
    <#assign subClassConstructorFields = "(${constructorFields?join(', ')})">
    <#assign inheritParameters = "${constructorFields?map(field -> field.name)?join(', ')}">
</#if>

class ${className}${mask}<#if subClassConstructorFields?has_content>${subClassConstructorFields}</#if> : ${className}(<#if inheritParameters?has_content>${inheritParameters}</#if>) {
    class Mask<#if subClassConstructorFields?has_content>${subClassConstructorFields}</#if> : ${className}(<#if inheritParameters?has_content>${inheritParameters}</#if>)
}

operator fun <T: ${className}> T.contains(other: ${className}): Boolean {
    return (${value.name} and other.${value.name}) != 0${literalSuffix}
}

infix fun <T: ${className}> T.or(other: ${className}): ${className} {
    return ${className}${mask}(<#if inheritParameters?has_content>${inheritParameters}</#if>)
}
