package com.ex.plugins;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
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
        Object o = boundSql.getParameterObject();
        String message = buildSqlStr(boundSql, o);
        logger.info(message);
        return invocation.proceed();
    }
    public String buildSqlStr(BoundSql boundSql,Object parameterObject){
        MapperMethod.ParamMap paramMap = parameterObject instanceof MapperMethod.ParamMap ?
                (MapperMethod.ParamMap) parameterObject: null;
        StringBuilder logStr = new StringBuilder();
        logStr.append(boundSql.getSql().replaceAll("\n|\\s+"," "));
        StringBuilder sqlRecord ;
        if(null!=paramMap){
            sqlRecord = getParameterStr(boundSql, logStr, paramMap);
        }else{
            sqlRecord = logStr;
        }
        return sqlRecord.toString();
    }
    public StringBuilder getParameterStr(BoundSql boundSql,StringBuilder sb,MapperMethod.ParamMap paramMap){
        sb.append("\n").append("parameters:");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        int i = 0;
        for (ParameterMapping parameterMapping : parameterMappings) {
            String property = parameterMapping.getProperty();

            //测试-------------------------------------
//            Object o = paramMap.get(property);
//            if(o instanceof Integer){
//                Integer a = (Integer) o;
//                if(3==a){
//                    throw new RuntimeException(a+"这个账户被限制消费了");
//                }
//            }

            if((i++)==parameterMappings.size()-1){
                sb.append(paramMap.get(property)).append(".");
            }else{
                sb.append(paramMap.get(property)).append(",");
            }
        }
        return sb;
    }
}
