package com.misaka.mybatiscode;

import com.misaka.mybatiscode.doman.User;
import com.misaka.mybatiscode.mapper.UserDao;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/*
* mybatis 源码分析
* */
@Slf4j
public class Test {
    public static void main(String[] args) throws IOException {
//        第一步 读取mybatis文件
//        通过classLoader来读取文件 并且返回一个输入流
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
//        其实就是 ClassLoaderWrapper类的getResourceAsStream()方法的下面一行代码
//        InputStream returnValue = cl.getResourceAsStream(resource);
//       就是通过classLoader来加载一个流 并且返回一个输入流
//        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");

//        第二步 构建SqlSessionFactory 框架初始化 并且传入xml Builder建造者模式
        /*
        * new DefaultSqlSessionFactory(config); 其实最后调用的是
        * */

        /*
        * 这一步主要的对象有 XpathParser对象 里面包含了Xml的解析信息  Configuration对象包含了xml信息的java对象
        * XmlParser通过在XMLConfigBuilder的构造函数中 聚合到了XMLConfigBuilder对象中 而Configuration对象是XMLConfigBuilder对象通过其构造函数调用super(new Configuration())在父类的构造函数中生成了configuration实例对象 并且赋值到父类的成员变量中 以此来实现依赖关系
        * */
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

//        第三步 打开sqlsession
        SqlSession sqlSession = factory.openSession();

//        第四步 获取Mapper接口[Dao接口]
        UserDao userDao = sqlSession.getMapper(UserDao.class);
//        第五步 调用Mapper接口对象的方法 [返回Domain]
       User user= userDao.selectById(1);
        System.out.println(user);
    }
}
