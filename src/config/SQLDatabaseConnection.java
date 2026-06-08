package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabaseConnection {

    private static final String connectionUrl =
            "jdbc:sqlserver://localhost:1433;"
            + "databaseName=rental_mobil_kelompok7;"
            + "user=kelompok7;"
            + "password=RentalMobil#2026;"
            + "encrypt=true;"
            + "trustServerCertificate=true;"
            + "loginTimeout=30;";

    public static Connection getConnection() throws SQLException {
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