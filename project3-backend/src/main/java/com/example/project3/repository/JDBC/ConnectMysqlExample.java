package com.example.project3.repository.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectMysqlExample {
//    private static String DB_URL = "jdbc:mysql://localhost:3306/project3";
//    private static String USER_NAME = "root";
//    private static String PASSWORD = "1234";
//
//    public static void main(String args[]) {
//        try {
//            // connnect to database 'testdb'
//            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
//            // crate statement
//            Statement stmt = conn.createStatement();
//            // get data from table 'student'
//            ResultSet rs = stmt.executeQuery("select count(*),status from transaction group by status;");
//            // show data
//            while (rs.next()) {
//                System.out.println(rs.getInt(1)+"-"+rs.getString(2));
//            }
//            // close connection
//            conn.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public static Connection getConnection(String dbURL, String userName,
//                                           String password) {
//        Connection conn = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection(dbURL, userName, password);
//            System.out.println("connect successfully!");
//        } catch (Exception ex) {
//            System.out.println("connect failure!");
//            ex.printStackTrace();
//        }
//        return conn;
//    }
}
