package com.test.common.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description:
 * @ClassName: DBTools
 * @Author: yanbobo
 * @CreateDate: 2020/3/9 14:46
 */
public class DBTools {

    private static DruidDataSource dataSource;

    static {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://127.0.0.1:3316/ssmtest?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
            String root = "root";
            String password = "root";

            dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setDriverClassName(driver);
            dataSource.setUsername(root);
            dataSource.setPassword(password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void coonClose(Connection coon) {
        if (coon != null) {
            try {
                coon.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
