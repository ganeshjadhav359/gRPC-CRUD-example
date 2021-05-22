package com.lms.configs;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceManager {

    private static final BasicDataSource dataSource = new BasicDataSource();

    private static final String MYSQL_DB_DRIVER_CLASS="com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_DB_URL="jdbc:mysql://localhost:3306/LMS";
    private static final String MYSQL_DB_USERNAME="root";
    private static final String MYSQL_DB_PASSWORD="root";

    static {
        dataSource.setDriverClassName(MYSQL_DB_DRIVER_CLASS);
        dataSource.setUrl(MYSQL_DB_URL);
        dataSource.setUsername(MYSQL_DB_USERNAME);
        dataSource.setPassword(MYSQL_DB_PASSWORD);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private DataSourceManager(){ }



}
