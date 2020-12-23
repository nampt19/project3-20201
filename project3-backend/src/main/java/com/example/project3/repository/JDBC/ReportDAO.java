package com.example.project3.repository.JDBC;

import com.example.project3.model.Report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportDAO extends AbstractDAO {
    public Report getReportByAllTime() {
        Report report;
        int numberCustomer = 0;
        int numberTransaction = 0;
        int numberHistoryCare = 0;

        int numberStatusPaid = 0;
        int numberStatusPaying = 0;
        int numberStatusDestroy = 0;

        int moneyStatusPaid = 0;
        int moneyStatusPaying = 0;
        int moneyStatusDestroy = 0;
        try {
            // connnect to database 'testdb'
            Connection conn = AbstractDAO.getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmt = conn.createStatement();
            // get data from table 'student'
            ResultSet rs = stmt.executeQuery("select count(*) as number_customer from customer");
            while (rs.next()){
                numberCustomer = rs.getInt(1);
            }
            rs = stmt.executeQuery("select count(*) as number_transaction from transaction");
            while (rs.next()){
                numberTransaction = rs.getInt(1);
            }
            rs = stmt.executeQuery("select count(*) as number_history_care from history_care");
            while (rs.next()){
                numberHistoryCare = rs.getInt(1);
            }
            rs = stmt.executeQuery("select count(*),status from transaction group by status");
            while (rs.next()) {
                //  System.out.println(rs.getInt(1));
                if (rs.getString(2).equals("Đã thanh toán"))
                    numberStatusPaid = rs.getInt(1);
                else if (rs.getString(2).equals("Đang nợ"))
                    numberStatusPaying = rs.getInt(1);
                else if (rs.getString(2).equals("Đã hủy"))
                    numberStatusDestroy = rs.getInt(1);
            }
            rs = stmt.executeQuery("select SUM(product.price) as total,transaction.status \n" +
                    "from transaction,product where transaction.id_product = product.id \n" +
                    "group by status");
            while (rs.next()) {
                //  System.out.println(rs.getInt(1));
                if (rs.getString(2).equals("Đã thanh toán"))
                    moneyStatusPaid = rs.getInt(1);
                else if (rs.getString(2).equals("Đang nợ"))
                    moneyStatusPaying = rs.getInt(1);
                else if (rs.getString(2).equals("Đã hủy"))
                    moneyStatusDestroy = rs.getInt(1);
            }

            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        report = new Report(numberCustomer,
                numberTransaction,
                numberHistoryCare,
                numberStatusPaid,
                numberStatusPaying,
                numberStatusDestroy,
                moneyStatusPaid,
                moneyStatusPaying,
                moneyStatusDestroy);

        return report;
    }

    public Report getReportByTime(int seconds) {
        Report report;
        int numberCustomer = 0;
        int numberTransaction = 0;
        int numberHistoryCare = 0;

        int numberStatusPaid = 0;
        int numberStatusPaying = 0;
        int numberStatusDestroy = 0;

        int moneyStatusPaid = 0;
        int moneyStatusPaying = 0;
        int moneyStatusDestroy = 0;
        try {
            // connnect to database 'testdb'
            Connection conn = AbstractDAO.getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmt = conn.createStatement();
            // get data from table 'student'
            ResultSet rs = stmt.executeQuery("select count(*) as number_customer from customer \n" +
                    "where UNIX_TIMESTAMP(create_time) between UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 00:00:00\")) -"+seconds+" \n" +
                    "and  UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 23:59:59\"))-"+seconds+"");
            while (rs.next()){
                numberCustomer = rs.getInt(1);
            }
            rs = stmt.executeQuery("select count(*) as number_transaction from transaction \n" +
                    "where UNIX_TIMESTAMP(time) between UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 00:00:00\")) -"+seconds+" \n" +
                    "and  UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 23:59:59\"))-"+seconds+"");
            while (rs.next()){
                numberTransaction = rs.getInt(1);
            }
            rs = stmt.executeQuery("select count(*) as number_history_care from history_care \n" +
                    "where UNIX_TIMESTAMP(time) between UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 00:00:00\"))-"+seconds+"  \n" +
                    "and  UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 23:59:59\"))-"+seconds+"");
            while (rs.next()){
                numberHistoryCare = rs.getInt(1);
            }
            rs = stmt.executeQuery("select count(*),status from transaction\n" +
                    "where UNIX_TIMESTAMP(time) between UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 00:00:00\"))-"+seconds+"  \n" +
                    "and  UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 23:59:59\"))-"+seconds+"\n" +
                    "group by status");
            while (rs.next()) {
                //  System.out.println(rs.getInt(1));
                if (rs.getString(2).equals("Đã thanh toán"))
                    numberStatusPaid = rs.getInt(1);
                else if (rs.getString(2).equals("Đang nợ"))
                    numberStatusPaying = rs.getInt(1);
                else if (rs.getString(2).equals("Đã hủy"))
                    numberStatusDestroy = rs.getInt(1);
            }
            rs = stmt.executeQuery("select sum(product.price) as total,transaction.status\n" +
                    "from transaction,product where transaction.id_product = product.id \n" +
                    "and UNIX_TIMESTAMP(time) between UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 00:00:00\"))-"+seconds+"  \n" +
                    "and  UNIX_TIMESTAMP(DATE_FORMAT(now(), \"%Y-%m-%d 23:59:59\"))-"+seconds+"\n" +
                    "group by status;");
            while (rs.next()) {
                //  System.out.println(rs.getInt(1));
                if (rs.getString(2).equals("Đã thanh toán"))
                    moneyStatusPaid = rs.getInt(1);
                else if (rs.getString(2).equals("Đang nợ"))
                    moneyStatusPaying = rs.getInt(1);
                else if (rs.getString(2).equals("Đã hủy"))
                    moneyStatusDestroy = rs.getInt(1);
            }

            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        report = new Report(numberCustomer,
                numberTransaction,
                numberHistoryCare,
                numberStatusPaid,
                numberStatusPaying,
                numberStatusDestroy,
                moneyStatusPaid,
                moneyStatusPaying,
                moneyStatusDestroy);

        return report;
    }
}
