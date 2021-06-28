package ${packageName};

<#if mybatisPlusFlag >
import ${entityPackageName};
import com.baomidou.mybatisplus.extension.service.IService;
</#if>

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since ${date?string("yyyy-MM-dd")}
 */
public interface ${serviceName} <#if mybatisPlusFlag >extends IService<${entityName}></#if> {
}