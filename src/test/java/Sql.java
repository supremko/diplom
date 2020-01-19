import lombok.val;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Sql {
    private static String user = "app";
    private static String password = "pass";
    private static String mysql = "jdbc:mysql://localhost:3306/app";
    private static String postgres = "jdbc:postgresql://localhost:5432/app";
    private static String database = System.getProperty("db_url");

    static int countApprovedPayment() throws SQLException {
        val count = "select count(1) as rownum from order_entity o, payment_entity p where o.payment_id=p.transaction_id and p.status='APPROVED';";
        int countApprovedPayment = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    countApprovedPayment = rs.getInt("rownum");
                }
            }
        }
        return countApprovedPayment;
    }

    static int countDeclinedPayment() throws SQLException {
        val count = "select count(1) as rownum from order_entity o, payment_entity p where o.payment_id=p.transaction_id and p.status='DECLINED';";
        int countDeclinedPayment = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    countDeclinedPayment = rs.getInt("rownum");
                }
            }
        }
        return countDeclinedPayment;
    }

    static int countPayment() throws SQLException {
        val count = "select count(1) as rownum from order_entity o, payment_entity p where o.payment_id=p.transaction_id;";
        int countPayment = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    countPayment = rs.getInt("rownum");
                }
            }
        }
        return countPayment;
    }

    static int countApprovedCredit() throws SQLException {
        val count = "select count(1) as rownum from order_entity o, credit_request_entity c where o.payment_id=c.bank_id and c.status='APPROVED';";
        int countApprovedCredit = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    countApprovedCredit = rs.getInt("rownum");
                }
            }
        }
        return countApprovedCredit;
    }

    static int countDeclinedCredit() throws SQLException {
        val count = "select count(1) as rownum from order_entity o, credit_request_entity c where o.payment_id=c.bank_id and c.status='DECLINED';";
        int countDeclinedCredit = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    countDeclinedCredit = rs.getInt("rownum");
                }
            }
        }
        return countDeclinedCredit;
    }

    static int countCredit() throws SQLException {
        val count = "select count(1) as rownum from order_entity o, credit_request_entity c where o.payment_id=c.bank_id;";
        int countCredit = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    countCredit = rs.getInt("rownum");
                }
            }
        }
        return countCredit;
    }
}
