package com.misaka.mybatiscode.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/*
* 动态数据源实现
* */
public class DynamicDataSource  extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
//        返回一个key
        System.out.println(DataSourceHolder.get().name());
        return DataSourceHolder.get().name()  ;
    }
}
