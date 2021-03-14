package ${packageName};

<#if autoIncrement >
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
</#if>
<#if serializableFlag >
import ${serializablePackageName};
</#if>
<#list  packages as package>
import ${package};
</#list>

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since ${date?string("yyyy-MM-dd")}
 */
public class ${className} <#if serializableFlag >implements Serializable</#if> {
    <#list  fields as field>
    <#if autoIncrement && field.name == id >@TableId(value = "${field.name}", type = IdType.AUTO)</#if>
    private ${field.type} ${field.name};
    </#list>

 <#list  fields as field>

    public void set${field.name?cap_first}(${field.type} ${field.name}){
         this.${field.name} = ${field.name};
    }

    public ${field.type} get${field.name?cap_first}(){
         return this.${field.name};
    }

 </#list>

    @Override
    public String toString() {
         return "${className}{" +
<#list fields as field>
     <#if field_index = 0>
         "${field.name}=" + ${field.name} +
      <#else>
         ",${field.name}=" + ${field.name} +
     </#if>
</#list>
         "}";
    }
}