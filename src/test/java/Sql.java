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
        val count = "select count(1) from order_entity o, payment_entity p where o.payment_id=p.transaction_id and p.status='APPROVED';";
        int countApprovedPayment = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countApprovedPayment = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countApprovedPayment = rs.getInt("count");
                    }
                }
            }
        }
        return countApprovedPayment;
    }

    static int countDeclinedPayment() throws SQLException {
        val count = "select count(1) from order_entity o, payment_entity p where o.payment_id=p.transaction_id and p.status='DECLINED';";
        int countDeclinedPayment = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countDeclinedPayment = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countDeclinedPayment = rs.getInt("count");
                    }
                }
            }
        }
        return countDeclinedPayment;
    }

    static int countPayment() throws SQLException {
        val count = "select count(1) from order_entity o, payment_entity p where o.payment_id=p.transaction_id;";
        int countPayment = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countPayment = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countPayment = rs.getInt("count");
                    }
                }
            }
        }
        return countPayment;
    }

    static int countApprovedCredit() throws SQLException {
        val count = "select count(1) from order_entity o, credit_request_entity c where o.payment_id=c.bank_id and c.status='APPROVED';";
        int countApprovedCredit = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countApprovedCredit = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countApprovedCredit = rs.getInt("count");
                    }
                }
            }
        }
        return countApprovedCredit;
    }

    static int countDeclinedCredit() throws SQLException {
        val count = "select count(1) from order_entity o, credit_request_entity c where o.payment_id=c.bank_id and c.status='DECLINED';";
        int countDeclinedCredit = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countDeclinedCredit = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countDeclinedCredit = rs.getInt("count");
                    }
                }
            }
        }
        return countDeclinedCredit;
    }

    static int countCredit() throws SQLException {
        val count = "select count(1) from order_entity o, credit_request_entity c where o.payment_id=c.bank_id;";
        int countCredit = 0;
        try (
                val conn = DriverManager.getConnection(database, user, password);
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countCredit = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countCredit = rs.getInt("count");
                    }
                }
            }
        }
        return countCredit;
    }
}
