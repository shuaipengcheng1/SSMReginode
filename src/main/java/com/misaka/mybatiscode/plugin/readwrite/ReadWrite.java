package com.misaka.mybatiscode.plugin.readwrite;


import com.misaka.mybatiscode.util.DataSourceHolder;
import com.misaka.mybatiscode.util.DataSourceType;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import javax.sql.DataSource;
import java.util.Properties;

/*
 *
 * 读写分离插件 即 查询的时候 查数据库A 修改的时候查数据库 B
 * */
//这里底层会将该类 放入职责链中 每一个Executor都会进入职责链
/*
* //    将执行器加入到责任链中 责任链模式 就是将一个请求对象 传入责任链 责任链中会包含很多实现接口 通过next()方法连接 类似于链表数据结构 如果当前责任链实现类无法处理该请求 那么会调用next方法 获取下一个责任链 直到可以处理该请求
    executor = (Executor) interceptorChain.pluginAll(executor);
    * TODO 所以只要是使用了interceptorChain的对象 都可以进行拦截处理 因为都会在pluginAll中进行循环拦截
    *    parameterHandler = (ParameterHandler) interceptorChain.pluginAll(parameterHandler);
    *    resultSetHandler = (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
         statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);
         executor = (Executor) interceptorChain.pluginAll(executor);
         *  拦截的具体实现  [target是传入的要被拦截的类例如Executor] 也就是会调用Interceptor实现类的plugin方法
         *  for (Interceptor interceptor : interceptors) {
                  target = interceptor.plugin(target);
               }

    * 然后interceptorChain 里面的值是通过Configuration的parseConfiguration方法来初始化的 pluginElement(root.evalNode("plugins"));
    * 所以要在XML文件中 通过<plugin> 来进行注册
    *
*
* */
@Intercepts({
        @Signature(
                type = Executor.class, //要拦截的接口
                method = "update", // 拦截的方法
                args = {MappedStatement.class, Object.class} //方法参数
        ),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
})
public class ReadWrite implements Interceptor {
    /*
    *         return interceptor.intercept(new Invocation(target, method, args));
Invocation参数 包装了目标方法的调用信息
*
*   private final Object target;
  private final Method method;
  private final Object[] args;
     * */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

//Before
//       获取目标类方法参数列表
        Object[] args = invocation.getArgs();
//        获取第一个参数 MappedStatement 就是SQL的封装信息 存储在Configuration中的MappedStatements中的
//        key为 包名+接口名+方法名 value就是MappedStatement
//        在使用SqlSession.getMapper(xxDao.class)返回的动态代理对象执行方法时也会调用到
        /*
        *
        * //      通过传入的command.getName()[也就是SQL id] 来获取对应的SQL对象[MappedStatement]
      MappedStatement ms = configuration.getMappedStatement(statement);
        * */
        MappedStatement mappedStatement = (MappedStatement) args[0];
        DataSourceType type = null;

//        如果是查询
        if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
//            如果数据查询为!select 为自增id查询主键 SELECT LAST_INSERT_ID()
            if (mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
//    使用主库/写库
                type = DataSourceType.WRITE;

            } else {
//                使用从库/读库
                type = DataSourceType.READ;
            }
        } else {
//类型为增删改
//            使用写库
            type = DataSourceType.WRITE;

        }
//        修改当前线程要使用的数据原的key
        DataSourceHolder.set(type);

//        调用目标类方法
        return invocation.proceed();
//        after
    }

    @Override
    public Object plugin(Object target) {
//        这里底层会进行一个动态代理 并且使用Method.invoke(target,args) 来调用target对象的方法
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
