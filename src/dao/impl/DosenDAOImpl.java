package dao.impl;

import config.DatabaseConfig;
import dao.DosenDAO;
import model.Dosen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DosenDAOImpl implements DosenDAO {

    private Connection connection = DatabaseConfig.getConnection();

    @Override
    public Dosen findByNidn(String nidn) {
        String sql = "SELECT d.*, u.username, u.password FROM dosen d " +
                     "JOIN users u ON d.user_id = u.id WHERE d.nidn = ?";
                     
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nidn);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Dosen(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("id"),
                        resultSet.getString("nidn"),
                        resultSet.getString("nama"),
                        resultSet.getString("departemen")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal menemukan Dosen dengan NIDN: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isNidnExist(String nidn) {
        String sql = "SELECT id FROM dosen WHERE nidn = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nidn);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Gagal cek NIDN: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void insert(Dosen entity) {
        String sql = "INSERT INTO dosen (user_id, nidn, nama, departemen) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getId()); 
            statement.setString(2, entity.getNidn());
            statement.setString(3, entity.getNama());
            statement.setString(4, entity.getDepartemen());
            
            statement.executeUpdate();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setDosenId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Data dosen berhasil ditambahkan!");
        } catch (SQLException e) {
            System.err.println("Gagal insert Dosen: " + e.getMessage());
        }
    }

    @Override
    public Dosen findById(int id) {
        String sql = "SELECT d.*, u.username, u.password FROM dosen d " +
                     "JOIN users u ON d.user_id = u.id WHERE d.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Dosen(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("id"),
                        resultSet.getString("nidn"),
                        resultSet.getString("nama"),
                        resultSet.getString("departemen")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal menemukan Dosen by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Dosen> findAll() {
        List<Dosen> listDosen = new ArrayList<>();
        String sql = "SELECT d.*, u.username, u.password FROM dosen d " +
                     "JOIN users u ON d.user_id = u.id";
                     
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                listDosen.add(new Dosen(
                    resultSet.getInt("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getInt("id"),
                    resultSet.getString("nidn"),
                    resultSet.getString("nama"),
                    resultSet.getString("departemen")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Gagal menemukan semua data Dosen: " + e.getMessage());
        }
        return listDosen;
    }

    @Override
    public void update(Dosen entity) {
        String sql = "UPDATE dosen SET nidn = ?, nama = ?, departemen = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getNidn());
            statement.setString(2, entity.getNama());
            statement.setString(3, entity.getDepartemen());
            statement.setInt(4, entity.getDosenId());
            
            statement.executeUpdate();
            System.out.println("Data dosen berhasil diupdate!");
        } catch (SQLException e) {
            System.err.println("Gagal update Dosen: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM dosen WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Data dosen berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Gagal delete Dosen: " + e.getMessage());
        }
    }
}
