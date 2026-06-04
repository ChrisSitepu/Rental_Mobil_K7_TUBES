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
        // Update these values to match your local SQL Server configuration
        String serverName = "localhost\\SQLEXPRESS01";
        String databaseName = "rental_mobil_kelompok7";
        String user = "kelompok7";
        String password = "12345678"; // Updated to match user preference or common defaults

        String connectionUrl = "jdbc:sqlserver://" + serverName + ";"
                + "databaseName=" + databaseName + ";"
                + "user=" + user + ";"
                + "password=" + password + ";"
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