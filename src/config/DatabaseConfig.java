package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
    
public class DatabaseConfig {
    // Detail koneksi database
    private static final String URL = "jdbc:mysql://localhost:3306/db_tugas_akhir";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    // Instance tunggal untuk Singleton
    private static Connection connection;

    // Private constructor mencegah instansiasi dari luar
    private DatabaseConfig() {}

    // Method untuk mendapatkan instance koneksi
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Mendaftarkan driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi database berhasil!");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Koneksi database gagal: " + e.getMessage());
            }
        }
        return connection;
    }
}
