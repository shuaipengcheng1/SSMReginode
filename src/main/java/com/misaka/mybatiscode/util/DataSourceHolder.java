package com.misaka.mybatiscode.util;

public class DataSourceHolder {
    //    本地线程上下文 单线程线程安全
    private static final ThreadLocal<DataSourceType> type = new ThreadLocal<>();
    //默认为主库
    private static final DataSourceType DEFAULT_TYPE = DataSourceType.WRITE;

    public static DataSourceType get() {
        DataSourceType type1 = type.get();
        if (type1 != null) {
            return type1;
        } else {
            return DEFAULT_TYPE;
        }
    }
    public static void set(DataSourceType type1){
//        设置
        type.set(type1);
    }
}
