package com.neoteric.fullstackdemo_31._8._4.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCennection {
    public  static  Connection connection;

    public static Connection getConnection(){
        try {
            if (connection==null){
                Class.forName("com.mysql.cj.jdbc.Driver");
              connection=  DriverManager.getConnection("jdbc:mysql://@localhost:3306/user","root","root");
            }else {
                System.out.println("Returning existing connection");
            }

        } catch (Exception e){
            System.out.println("Exception occurred in getConnection"+e);
        }
return connection;
    }
}
