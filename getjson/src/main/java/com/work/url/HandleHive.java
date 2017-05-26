package com.work.url;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by wang on 2017/5/24.
 */
public class HandleHive {
    private String driverName;
    private String connectionString;
    private Connection connection;

    private static Logger logger = Logger.getLogger(HandleHive.class);
    public HandleHive(String driverName,String connectionString,String userName,String password){
        this.driverName = driverName;
        this.connectionString = connectionString;

        try{
            Class.forName(driverName);
            connection = DriverManager.getConnection(connectionString,userName,password);
        }catch (ClassNotFoundException e){
            logger.error("driver not found");
            e.printStackTrace();
        }catch (SQLException e){
            logger.error("getConnection error");
            e.printStackTrace();
        }
    }

    public void executeSQL(String sql){
        if(sql == null){
            logger.error("SQL:"+sql+" is null!");
            return;
        }

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
        }catch (SQLException e){
            closeConnection();
            logger.error("executeSQL:"+sql);
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            if (connection == null)
                return;

            connection.close();
        }catch (SQLException e){
            logger.error("close connection");
            e.printStackTrace();
        }
    }
}
