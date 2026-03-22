package dao.impl;

import config.DatabaseConfig;
import dao.UserDAO;
import model.Dosen;
import model.Mahasiswa;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    
    private Connection connection = DatabaseConfig.getConnection();

    @Override
    public User authenticate(String username, String password) {
        // Query menggunakan JOIN untuk langsung mengambil data Mahasiswa/Dosen
        String sql = "SELECT u.id AS user_id, u.username, u.password, u.role, " +
                     "m.id AS mhs_id, m.nim, m.nama AS mhs_nama, m.jurusan, " +
                     "d.id AS dsn_id, d.nidn, d.nama AS dsn_nama, d.departemen " +
                     "FROM users u " +
                     "LEFT JOIN mahasiswa m ON u.id = m.user_id " +
                     "LEFT JOIN dosen d ON u.id = d.user_id " +
                     "WHERE u.username = ? AND u.password = ?";
                     
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String role = resultSet.getString("role");
                    
                    // Polimorfisme: Mengembalikan instance sesuai Role dari database
                    if ("MAHASISWA".equals(role)) {
                        return new Mahasiswa(
                            userId, username, password,
                            resultSet.getInt("mhs_id"),
                            resultSet.getString("nim"),
                            resultSet.getString("mhs_nama"),
                            resultSet.getString("jurusan")
                        );
                    } else if ("DOSEN".equals(role)) {
                        return new Dosen(
                            userId, username, password,
                            resultSet.getInt("dsn_id"),
                            resultSet.getString("nidn"),
                            resultSet.getString("dsn_nama"),
                            resultSet.getString("departemen")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal saat autetikasi: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isUsernameExist(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error cek username: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void insert(User entity) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getRole());
            
            statement.executeUpdate();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("User baru berhasil ditambahkan!");
        } catch (SQLException e) {
            System.err.println("Gagal insert User: " + e.getMessage());
        }
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                   
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    
                    if("MAHASISWA".equals(role)) {
                       // placeholder find by ID
                       return new Mahasiswa(id, username, password, 0, "", "", "");
                    } else if("DOSEN".equals(role)){
                       return new Dosen(id, username, password, 0, "", "", "");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById User: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                
                 if("MAHASISWA".equals(role)) {
                       users.add(new Mahasiswa(id, username, password, 0, "...", "...", "..."));
                 } else if("DOSEN".equals(role)){
                       users.add(new Dosen(id, username, password, 0, "...", "...", "..."));
                 }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findAll User: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getRole());
            statement.setInt(4, entity.getId());
            
            statement.executeUpdate();
            System.out.println("Data user berhasil diupdate.");
        } catch (SQLException e) {
            System.err.println("Gagal update User: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Data user berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Gagal delete User: " + e.getMessage());
        }
    }
}
