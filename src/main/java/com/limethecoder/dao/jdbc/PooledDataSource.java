package com.limethecoder.dao.jdbc;


import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class PooledDataSource {
    private static final String DB_BUNDLE = "db";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";
    private static final String DB_DRIVER = "driver";
    private static final String DB_URL = "url";

    private static class InstanceHolder {
        private static final DataSource INSTANCE = initDataSource();
    }

    public static DataSource getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static DataSource initDataSource() {
        ResourceBundle properties = ResourceBundle.getBundle(DB_BUNDLE);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getString(DB_DRIVER));
        dataSource.setUsername(properties.getString(DB_USER));
        dataSource.setPassword(properties.getString(DB_PASSWORD));
        dataSource.setUrl(properties.getString(DB_URL));
        return dataSource;
    }
}
