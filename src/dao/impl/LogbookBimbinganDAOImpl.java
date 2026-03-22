package dao.impl;

import config.DatabaseConfig;
import dao.LogbookBimbinganDAO;
import model.LogbookBimbingan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogbookBimbinganDAOImpl implements LogbookBimbinganDAO {

    private Connection connection = DatabaseConfig.getConnection();

    @Override
    public void insert(LogbookBimbingan entity) {
        String sql = "INSERT INTO logbook_bimbingan (mahasiswa_id, dosen_id, tanggal, kegiatan, catatan_dosen, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getMahasiswaId());
            statement.setInt(2, entity.getDosenId());
            statement.setDate(3, entity.getTanggal());
            statement.setString(4, entity.getKegiatan());
            statement.setString(5, entity.getCatatanDosen());
            statement.setString(6, entity.getStatus());
            
            statement.executeUpdate();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Logbook bimbingan berhasil disimpan!");
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan logbook: " + e.getMessage());
        }
    }

    @Override
    public LogbookBimbingan findById(int id) {
        String sql = "SELECT l.*, m.nama AS nama_mahasiswa, d.nama AS nama_dosen " +
                     "FROM logbook_bimbingan l " +
                     "JOIN mahasiswa m ON l.mahasiswa_id = m.id " +
                     "JOIN dosen d ON l.dosen_id = d.id " +
                     "WHERE l.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    LogbookBimbingan logbook = new LogbookBimbingan(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getInt("dosen_id"),
                        resultSet.getDate("tanggal"),
                        resultSet.getString("kegiatan"),
                        resultSet.getString("catatan_dosen"),
                        resultSet.getString("status")
                    );
                    logbook.setNamaMahasiswa(resultSet.getString("nama_mahasiswa"));
                    logbook.setNamaDosen(resultSet.getString("nama_dosen"));
                    return logbook;
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById Logbook: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<LogbookBimbingan> findAll() {
        List<LogbookBimbingan> list = new ArrayList<>();
        String sql = "SELECT l.*, m.nama AS nama_mahasiswa, d.nama AS nama_dosen " +
                     "FROM logbook_bimbingan l " +
                     "JOIN mahasiswa m ON l.mahasiswa_id = m.id " +
                     "JOIN dosen d ON l.dosen_id = d.id";
                     
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                LogbookBimbingan logbook = new LogbookBimbingan(
                    resultSet.getInt("id"),
                    resultSet.getInt("mahasiswa_id"),
                    resultSet.getInt("dosen_id"),
                    resultSet.getDate("tanggal"),
                    resultSet.getString("kegiatan"),
                    resultSet.getString("catatan_dosen"),
                    resultSet.getString("status")
                );
                logbook.setNamaMahasiswa(resultSet.getString("nama_mahasiswa"));
                logbook.setNamaDosen(resultSet.getString("nama_dosen"));
                list.add(logbook);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findAll Logbook: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void update(LogbookBimbingan entity) {
        String sql = "UPDATE logbook_bimbingan SET mahasiswa_id = ?, dosen_id = ?, tanggal = ?, kegiatan = ?, " +
                     "catatan_dosen = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getMahasiswaId());
            statement.setInt(2, entity.getDosenId());
            statement.setDate(3, entity.getTanggal());
            statement.setString(4, entity.getKegiatan());
            statement.setString(5, entity.getCatatanDosen());
            statement.setString(6, entity.getStatus());
            statement.setInt(7, entity.getId());
            
            statement.executeUpdate();
            System.out.println("Logbook bimbingan berhasil diupdate!");
        } catch (SQLException e) {
            System.err.println("Gagal update Logbook: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM logbook_bimbingan WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Data logbook berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Gagal delete Logbook: " + e.getMessage());
        }
    }

    @Override
    public List<LogbookBimbingan> findByMahasiswaId(int mahasiswaId) {
        List<LogbookBimbingan> list = new ArrayList<>();
        String sql = "SELECT l.*, m.nama AS nama_mahasiswa, d.nama AS nama_dosen " +
                     "FROM logbook_bimbingan l " +
                     "JOIN mahasiswa m ON l.mahasiswa_id = m.id " +
                     "JOIN dosen d ON l.dosen_id = d.id " +
                     "WHERE l.mahasiswa_id = ? ORDER BY l.tanggal DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mahasiswaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LogbookBimbingan logbook = new LogbookBimbingan(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getInt("dosen_id"),
                        resultSet.getDate("tanggal"),
                        resultSet.getString("kegiatan"),
                        resultSet.getString("catatan_dosen"),
                        resultSet.getString("status")
                    );
                    logbook.setNamaMahasiswa(resultSet.getString("nama_mahasiswa"));
                    logbook.setNamaDosen(resultSet.getString("nama_dosen"));
                    list.add(logbook);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByMahasiswaId Logbook: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<LogbookBimbingan> findByDosenId(int dosenId) {
        List<LogbookBimbingan> list = new ArrayList<>();
        String sql = "SELECT l.*, m.nama AS nama_mahasiswa, d.nama AS nama_dosen " +
                     "FROM logbook_bimbingan l " +
                     "JOIN mahasiswa m ON l.mahasiswa_id = m.id " +
                     "JOIN dosen d ON l.dosen_id = d.id " +
                     "WHERE l.dosen_id = ? ORDER BY l.tanggal DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dosenId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LogbookBimbingan logbook = new LogbookBimbingan(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getInt("dosen_id"),
                        resultSet.getDate("tanggal"),
                        resultSet.getString("kegiatan"),
                        resultSet.getString("catatan_dosen"),
                        resultSet.getString("status")
                    );
                    logbook.setNamaMahasiswa(resultSet.getString("nama_mahasiswa"));
                    logbook.setNamaDosen(resultSet.getString("nama_dosen"));
                    list.add(logbook);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByDosenId Logbook: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void updateCatatanDanStatus(int logbookId, String catatan, String status) {
        String sql = "UPDATE logbook_bimbingan SET catatan_dosen = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, catatan);
            statement.setString(2, status);
            statement.setInt(3, logbookId);
            
            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {
                 System.out.println("Catatan Logbook berhasil diberikan!");
            } else {
                 System.out.println("Gagal! ID Logbook tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.err.println("Error updateCatatanDanStatus: " + e.getMessage());
        }
    }
}
