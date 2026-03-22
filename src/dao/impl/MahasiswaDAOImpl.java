package dao.impl;

import config.DatabaseConfig;
import dao.MahasiswaDAO;
import model.Mahasiswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAOImpl implements MahasiswaDAO {

    private Connection connection = DatabaseConfig.getConnection();

    @Override
    public Mahasiswa findByNim(String nim) {
        // Menggunakan JOIN antara tabel `mahasiswa` dan `users`
        // dikarenakan inheritance User -> Mahasiswa membutuhkan data dari dua tabel 
        String sql = "SELECT m.*, u.username, u.password FROM mahasiswa m " +
                     "JOIN users u ON m.user_id = u.id WHERE m.nim = ?";
                     
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nim);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Mahasiswa(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("id"),
                        resultSet.getString("nim"),
                        resultSet.getString("nama"),
                        resultSet.getString("jurusan")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal menemukan Mahasiswa dengan NIM: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isNimExist(String nim) {
        String sql = "SELECT id FROM mahasiswa WHERE nim = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nim);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Gagal cek NIM: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void insert(Mahasiswa entity) {
        // Karena constraint Foreign Key as Cascade, User harus di-insert lebih dulu,        
        String sql = "INSERT INTO mahasiswa (user_id, nim, nama, jurusan) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getId()); // Referensi foreign key user_id dari kelas Parent User
            statement.setString(2, entity.getNim());
            statement.setString(3, entity.getNama());
            statement.setString(4, entity.getJurusan());
            
            statement.executeUpdate();
            
            // Mengambil ID Auto Increment yang baru dibuat
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setMahasiswaId(generatedKeys.getInt(1)); // Set ID Mahasiswa lokal
                }
            }
            System.out.println("Data mahasiswa berhasil ditambahkan!");
        } catch (SQLException e) {
            System.err.println("Gagal insert Mahasiswa: " + e.getMessage());
        }
    }

    @Override
    public Mahasiswa findById(int id) {
        String sql = "SELECT m.*, u.username, u.password FROM mahasiswa m " +
                     "JOIN users u ON m.user_id = u.id WHERE m.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Mahasiswa(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("id"),
                        resultSet.getString("nim"),
                        resultSet.getString("nama"),
                        resultSet.getString("jurusan")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal menemukan Mahasiswa by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Mahasiswa> findAll() {
        List<Mahasiswa> listMahasiswa = new ArrayList<>();
        String sql = "SELECT m.*, u.username, u.password FROM mahasiswa m " +
                     "JOIN users u ON m.user_id = u.id";
                     
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                listMahasiswa.add(new Mahasiswa(
                    resultSet.getInt("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getInt("id"),
                    resultSet.getString("nim"),
                    resultSet.getString("nama"),
                    resultSet.getString("jurusan")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Gagal menemukan semua data Mahasiswa: " + e.getMessage());
        }
        return listMahasiswa;
    }

    @Override
    public void update(Mahasiswa entity) {
        // update (username/password) Parent harus via UserDAO
        String sql = "UPDATE mahasiswa SET nim = ?, nama = ?, jurusan = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getNim());
            statement.setString(2, entity.getNama());
            statement.setString(3, entity.getJurusan());
            statement.setInt(4, entity.getMahasiswaId());
            
            statement.executeUpdate();
            System.out.println("Data mahasiswa berhasil diupdate!");
        } catch (SQLException e) {
            System.err.println("Gagal update Mahasiswa: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM mahasiswa WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Data mahasiswa berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Gagal delete Mahasiswa: " + e.getMessage());
        }
    }
}
