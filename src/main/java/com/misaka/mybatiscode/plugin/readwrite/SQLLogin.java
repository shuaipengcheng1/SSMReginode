package com.misaka.mybatiscode.plugin.readwrite;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class,method = "prepare",args ={Connection.class,Integer.class} )
})
public class SQLLogin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        获取目标对象
       StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
       Connection connection = (Connection) invocation.getArgs()[0];
//       获取Sql语句
        System.out.println("Log------------->"+statementHandler.getBoundSql().getSql());
        System.out.println("开始执行");
        Long start = System.currentTimeMillis();
        Object result= invocation.proceed();
        System.out.println("TIME Log------------->"+(System.currentTimeMillis()-start));


        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
