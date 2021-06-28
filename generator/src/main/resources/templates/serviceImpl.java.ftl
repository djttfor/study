package ${packageName};

<#if mybatisPlusFlag >
import ${entityPackageName};
import ${mapperPackageName};
import ${servicePackageName};
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
</#if>
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since ${date?string("yyyy-MM-dd")}
 */
@Service
public class ${serviceName}Impl <#if mybatisPlusFlag >extends ServiceImpl<${mapperName}, ${entityName}></#if> implements ${serviceName} {
}