package ${packageName};

${imports}

public record ${entity.name}(
<#list fields as field>
    ${field.type().toJavaType()} ${field.name()}<#sep>,</#sep>
</#list>
) {
<#list fields as field>
    <#if field.required()>
    public static ${entity.name} requireNonNull${field.capitalizedName()}(${field.type().toJavaType()} ${field.name()}) {
        if (${field.name()} == null) throw new IllegalArgumentException("${field.name()} is required");
        return new ${entity.name}(<#list fields as f>${f.name()}<#sep>, </#sep></#list>);
    }
    </#if>
</#list>
}

