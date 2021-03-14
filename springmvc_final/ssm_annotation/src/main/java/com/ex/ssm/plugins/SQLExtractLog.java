package com.ex.ssm.plugins;

import com.ex.ssm.config.MyException;
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
import org.apache.ibatis.type.TypeHandlerRegistry;
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
        BoundSql boundSql;
        if(args.length==4){
           boundSql =  ms.getBoundSql(params);
        }else{
            boundSql = (BoundSql) args[5];//获取分页插件的sql
        }
        String message = getSqlStr(boundSql,ms);
        logger.info("Sql语句:"+message);
        return invocation.proceed();
    }
    public String getSqlStr(BoundSql boundSql,MappedStatement ms){
        StringBuilder logStr = new StringBuilder();
        logStr.append(boundSql.getSql().replaceAll("\n|\\s+"," "));
        Object parameterObject = null;
//        if(true){
//            throw new MyException("这是测试异常");
//        }
        try{
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            if (parameterMappings.size()>0 &&
                    (null != ( parameterObject = boundSql.getParameterObject()))) {
                logStr.append("\n").append(" Parameters:");

                TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();

                MetaObject metaObject = new Configuration().newMetaObject(parameterObject);
                for(int i=0;i<parameterMappings.size();i++){
                    Object value ;
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    String property = parameterMapping.getProperty();
                    //sql执行只需要一个参数的时候
                    if (boundSql.hasAdditionalParameter(property)) {
                        value = boundSql.getAdditionalParameter(property);
                    } else if(typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())){
                        value = parameterObject;
                    }else{
                        //多个参数
                        value = metaObject.getValue(property);
                    }
                    String simpleName = value.getClass().getSimpleName();
                    if(i==parameterMappings.size()-1){
                        logStr.append(value).append("(").append(simpleName).append(")");
                    }else{
                        logStr.append(value).append("(").append(simpleName).append(")").append(",");
                    }
                }
            }
        }catch (Exception e){
            logger.info("SQL记录插件出错了,当前参数为{},异常信息:{}",parameterObject,e);
        }
        return logStr.toString();
    }
}
