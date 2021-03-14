package ${packageName};

<#if mybatisPlusFlag >
import ${entityPackageName};
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
</#if>

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since ${date?string("yyyy-MM-dd")}
 */
public interface ${mapperName} <#if mybatisPlusFlag >extends BaseMapper<${entityName}></#if> {
}