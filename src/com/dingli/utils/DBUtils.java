package com.dingli.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtils {
    //数据库工具类：注册驱动、获取连接、关闭资源
    //数据库连接池
    private static DataSource dataSource;
    //工具类被调用时代码块会先运行，加载配置文件druid.properties
    static {
        Properties properties = new Properties();
        //从配置文件中读取数据库信息
        InputStream resourceAsStream = DBUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        //加载配置文件
        try {
            //读取信息之后通过load加载，将字节流resourceAsStream加载进来
            properties.load(resourceAsStream);
            //通过数据库工厂来创建数据源
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException{
        //通过数据源获取连接
        return dataSource.getConnection();
    }
    public static void close(Statement statement,Connection connection){
        close(null,statement,connection);
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
    public static void close(ResultSet resultSet,Statement statement,Connection connection){
        if (resultSet != null){
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (statement != null){
            try{
                statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
