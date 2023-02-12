package com.misaka.mybatiscode.OCP_Proxy;

public class Client {
    //        创建源对象 空实现
    static UserDao dao = new UserDao() {
        @Override
        public void selectById(int i) {

        }
    };
    public static void main(String[] args) {

//        指定泛型类
        ProxyFactory<UserDao> factory = new ProxyFactory<>();
        factory.setUserDao(dao);
//这里返回的是 一个Proxy对象
       UserDao userDao= factory.getInstance();

       userDao.selectById(1);




    }
}
