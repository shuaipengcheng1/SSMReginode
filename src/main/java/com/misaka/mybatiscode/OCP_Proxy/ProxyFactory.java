package com.misaka.mybatiscode.OCP_Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

//泛型类 指定泛型
public class ProxyFactory<T> {
    //    维护一个目标对象 为了适配各种数据类型 引用最好是Object 或者是用泛型类
    private T userDao;
    private final Handler<T> handler = new Handler<>();

    public void setUserDao(T userDao) {
        this.userDao = userDao;
//        传入目标对象
        handler.setTarget(userDao);
    }

    //    返回一个代理对象
    public T getInstance() {

        return (T) Proxy.newProxyInstance(userDao.getClass().getClassLoader(), userDao.getClass().getInterfaces(), handler);
    }
}

class Handler<T> implements InvocationHandler {
    //    目标对象
    private T target;

    public void setTarget(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        动态代理 运行时增强
        System.out.println("动态代理");
        System.out.println("改写 比如这里引入JDBC代码");


//        获取形参数量
        for (int i = 0; i < method.getParameterCount(); i++) {
            System.out.println(args[i]);
        }

//        AOP
        System.out.println("前置增强");
//        触发目标对象的函数
        Object result = method.invoke(target, args);
//        AOP
        System.out.println("后置通知");
        return result;

    }
}