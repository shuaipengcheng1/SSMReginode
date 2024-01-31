package com.misaka.mybatiscode;

import com.misaka.mybatiscode.doman.User;
import com.misaka.mybatiscode.mapper.UserDao;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

/*
* mybatis 源码分析
* */
@Slf4j
public class Test {
    public static void main(String[] args) throws IOException {
////        第一步 读取mybatis文件
////        通过classLoader来读取文件 并且返回一个输入流
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
////        其实就是 ClassLoaderWrapper类的getResourceAsStream()方法的下面一行代码
////        InputStream returnValue = cl.getResourceAsStream(resource);
////       就是通过classLoader来加载一个流 并且返回一个输入流
////        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
//
////        第二步 构建SqlSessionFactory 框架初始化 并且传入xml Builder建造者模式
//        /*
//        * new DefaultSqlSessionFactory(config); 其实最后调用的是
//        * */
//
//        /*
//        * 这一步主要的对象有 XpathParser对象 里面包含了Xml的解析信息  Configuration对象包含了xml信息的java对象
//        * XmlParser通过在XMLConfigBuilder的构造函数中 聚合到了XMLConfigBuilder对象中 而Configuration对象是XMLConfigBuilder对象通过其构造函数调用super(new Configuration())在父类的构造函数中生成了configuration实例对象 并且赋值到父类的成员变量中 以此来实现依赖关系
//        * */
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
//
////        第三步 打开sqlsession
        SqlSession sqlSession = factory.openSession();
//
////        第四步 获取Mapper接口[Dao接口]  底层是动态代理
////        这个UserDao是一个 return mapperProxyFactory.newInstance(sqlSession);这方法返回的是DAO动态代理对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);
////        第五步 调用Mapper接口对象的方法 [返回Domain]
////        动态代理对象调用方法时 一定会先触发创建代理对象时 声明的invoke方法
//        /*
//        * 通过代理对象 来进行查询
//        * 底层
//        * 1 因为是动态代理 所以通过调用MapperProxy的invoke方法 来执行cachedInvoker(method).invoke(proxy, method, args, sqlSession);
//        * 2 在 cachedInvoker 内部 通过new MapperMethod(mapperInterface, method, sqlSession.getConfiguration()) 创建了一个MapperMethod对象
//        * 3 在 MapperMethod的构造函数中 具体有两件事 一个是初始化SQL操作 第二初始化方法签名
//        *   - 1  通过configuration.getMappedStatement(statementId) 获取MapperStatement对象这个对象包含了Mapper在xml声明的所有信息 并且这个也是在XMLConfigBuilder中 通过parseConfiguration方法来初始化的 【TODO statementId:包名+接口名+方法名组成的字符串】
//        *         然后创建通过MapperStatement的属性 获取到了SQL操作的类型[SELECT or ..] 和 SQL的id [包名+接口名+方法名]
//        *     2  方法签名主要是通过接口方法的信息 将一个方法作为一个对象的形式存储 MethodSignature对象中主要包含返回值信息 参数列表信息
//        * 4 在获取到了MapperMethod对象后 里面就包含了 command[SQL操作信息] 和方法签名信息
//        * 5 通过 new PlainMethodInvoker(MapperMethod mapperMethod) 创建了一个PlainMethodInvoker对象 并且传入了MapperMethod
//        * 6 调用PlainMethodInvoker的invoke方法 里面执行了  return mapperMethod.execute(sqlSession, args);
//        * 7 mapperMethod.execute(sqlSession, args)函数内部主要是通过
//        *   - 判读操作类型
//        *   - 判断返回类型
//        *   - 通过 Object param = method.convertArgsToSqlCommandParam(args); 绑定参数 将参数转换为 SQL 命令参数
//        *   - 执行查询 result = sqlSession.selectOne(command.getName(), param);
//        *      参数  command.getName()
//                         *传入SQL的id[其实在configuration中 有一个属性叫做MapperStatements[底层是一个哈希表 key是包名+接口名+方法名组成的字符串] 然后SqlSession[DefalutSqlSession]中聚合了Configuration 我们就可以通过SQL id 就是这里command属性的name属性
//                          来获取MapperStatement对象 然后通过这个对象 获取对应的SqlSource属性 也就是SQL语句 还有其他所有关于Mapper的信息
//                    param
//                          * 外部传入参数的列表 或者是一个参数 可以是HashMap 也可以是一个值 刚才通过convertArgsToSqlCommandParam函数获取的返回值
//        * */
       User user= userDao.selectById(1);
//        System.out.println(user);

        log.info("user is {}",user);

//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationConext.xml");
////        获取Mapper
//        UserDao userDao = context.getBean(UserDao.class);
//        User user  = userDao.selectById(1);
//        int r = userDao.insertId(new User(0,"1","21",33));
//
//        System.out.println(user);
//        System.out.println(r);




    }
}
