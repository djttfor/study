package com.ex.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectUtil {
    interface ConnectConstant{
        String DEFAULT_HOST = "47.112.181.157";
        int DEFAULT_PORT = 5672;
        String DEFAULT_USERNAME = "admin";
        String DEFAULT_PASSWORD = "admin";
    }
    public static Connection buildConnection(String host,int port,String username,String password,String virtualPath,String connectionName) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualPath);
        if(connectionName==null){
            return connectionFactory.newConnection();
        }
        return connectionFactory.newConnection(connectionName);
    }
    public static Connection buildDefaultConnection(String connectionName) throws IOException, TimeoutException {
        if (connectionName == null){
            return buildConnection(ConnectConstant.DEFAULT_HOST,ConnectConstant.DEFAULT_PORT,
                    ConnectConstant.DEFAULT_USERNAME,ConnectConstant.DEFAULT_PASSWORD,"/",null);
        }
        return buildConnection(ConnectConstant.DEFAULT_HOST,ConnectConstant.DEFAULT_PORT,
                ConnectConstant.DEFAULT_USERNAME,ConnectConstant.DEFAULT_PASSWORD,"/",connectionName);
    }
    public static void close(Connection connection)  {
       if(connection !=null && connection.isOpen()){
           try {
               connection.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

}
