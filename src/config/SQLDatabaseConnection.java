package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabaseConnection {

    // private static final String connectionUrl =
    // "jdbc:sqlserver://localhost\\SQLEXPRESS01;"
    // + "databaseName=rental_mobil_kelompok7;"
    // + "encrypt=true;"
    // + "trustServerCertificate=true;"
    // + "integratedSecurity=true;";

    // public static Connection getConnection() throws SQLException {
    // return DriverManager.getConnection(connectionUrl);
    // }
    public static Connection getConnection() throws SQLException {
    String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;"
                + "databaseName=rental_mobil_kelompok7;"
                + "user=kelompok7;"
                + "password=12345678;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        return DriverManager.getConnection(connectionUrl);
        
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection connection = getConnection();

            System.out.println("Koneksi berhasil!");

            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC tidak ditemukan!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Koneksi gagal!");
            e.printStackTrace();
        }
        
    }
}