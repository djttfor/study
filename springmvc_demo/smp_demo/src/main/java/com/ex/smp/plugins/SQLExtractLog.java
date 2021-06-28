package com.ex.smp.plugins;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Intercepts({@Signature(type = Executor.class,
        method = "query",args = {MappedStatement.class, Object.class, RowBounds.class,
        ResultHandler.class, CacheKey.class,BoundSql.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class}),
        @Signature(type = Executor.class,method = "update",args = {MappedStatement.class, Object.class})}
)
public class SQLExtractLog implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(SQLExtractLog.class);
    @Override
    public  Object  intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object params = args[1];
        BoundSql boundSql = ms.getBoundSql(params);
        String message = getSqlStr(boundSql);
        logger.info("Sql语句:"+message);
        return invocation.proceed();
    }
    public String getSqlStr(BoundSql boundSql){
        StringBuilder logStr = new StringBuilder();
        logStr.append(boundSql.getSql().replaceAll("\n|\\s+"," "));
        Object parameterObject = boundSql.getParameterObject();
        if(parameterObject!=null){
            logStr.append("\n").append("Parameters:");
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            MetaObject metaObject = new Configuration().newMetaObject(parameterObject);
            for(int i=0;i<parameterMappings.size();i++){
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String property = parameterMapping.getProperty();
                Object value = metaObject.getValue(property);
                String simpleName = value.getClass().getSimpleName();
                if(i==parameterMappings.size()-1){
                    logStr.append(value).append("(").append(simpleName).append(")");
                }else{
                    logStr.append(value).append("(").append(simpleName).append(")").append(",");
                }
            }
        }
        return logStr.toString();
    }
}
