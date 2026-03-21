package dao.impl;

import config.DatabaseConfig;
import dao.PengajuanPembimbingDAO;
import model.PengajuanPembimbing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PengajuanPembimbingDAOImpl implements PengajuanPembimbingDAO {

    private Connection connection = DatabaseConfig.getConnection();

    @Override
    public void insert(PengajuanPembimbing entity) {
        String sql = "INSERT INTO pengajuan_pembimbing (mahasiswa_id, dosen_id, status) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getMahasiswaId());
            statement.setInt(2, entity.getDosenId());
            statement.setString(3, "MENUNGGU"); // Default selalu menunggu saat pertama kali diajukan
            
            statement.executeUpdate();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("✅ Pengajuan pembimbing berhasil dikirim!");
        } catch (SQLException e) {
            System.err.println("❌ Gagal mengajukan pembimbing: " + e.getMessage());
        }
    }

    @Override
    public PengajuanPembimbing findById(int id) {
        String sql = "SELECT p.*, m.nama AS nama_mahasiswa, d.nama AS nama_dosen " +
                     "FROM pengajuan_pembimbing p " +
                     "JOIN mahasiswa m ON p.mahasiswa_id = m.id " +
                     "JOIN dosen d ON p.dosen_id = d.id " +
                     "WHERE p.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    PengajuanPembimbing pengajuan = new PengajuanPembimbing(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getInt("dosen_id"),
                        resultSet.getString("status")
                    );
                    pengajuan.setNamaMahasiswa(resultSet.getString("nama_mahasiswa"));
                    pengajuan.setNamaDosen(resultSet.getString("nama_dosen"));
                    return pengajuan;
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById PengajuanPembimbing: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<PengajuanPembimbing> findAll() {
        List<PengajuanPembimbing> list = new ArrayList<>();
        String sql = "SELECT p.*, m.nama AS nama_mahasiswa, d.nama AS nama_dosen " +
                     "FROM pengajuan_pembimbing p " +
                     "JOIN mahasiswa m ON p.mahasiswa_id = m.id " +
                     "JOIN dosen d ON p.dosen_id = d.id";
                     
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                PengajuanPembimbing pengajuan = new PengajuanPembimbing(
                    resultSet.getInt("id"),
                    resultSet.getInt("mahasiswa_id"),
                    resultSet.getInt("dosen_id"),
                    resultSet.getString("status")
                );
                pengajuan.setNamaMahasiswa(resultSet.getString("nama_mahasiswa"));
                pengajuan.setNamaDosen(resultSet.getString("nama_dosen"));
                list.add(pengajuan);
            }
        } catch (SQLException e) {
            System.err.println("Gagal findAll PengajuanPembimbing: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void update(PengajuanPembimbing entity) {
        // Method update generic jika perlu mengubah mahasiswa atau dosen nya
        String sql = "UPDATE pengajuan_pembimbing SET mahasiswa_id = ?, dosen_id = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getMahasiswaId());
            statement.setInt(2, entity.getDosenId());
            statement.setString(3, entity.getStatus());
            statement.setInt(4, entity.getId());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal update PengajuanPembimbing: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM pengajuan_pembimbing WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("✅ Data pengajuan berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("❌ Gagal delete PengajuanPembimbing: " + e.getMessage());
        }
    }

    @Override
    public boolean hasPengajuan(int mahasiswaId) {
        String sql = "SELECT id FROM pengajuan_pembimbing WHERE mahasiswa_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mahasiswaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error cek pengajuan: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<PengajuanPembimbing> findByDosenId(int dosenId) {
        List<PengajuanPembimbing> list = new ArrayList<>();
        String sql = "SELECT p.*, m.nama AS nama_mahasiswa, d.nama AS nama_dosen " +
                     "FROM pengajuan_pembimbing p " +
                     "JOIN mahasiswa m ON p.mahasiswa_id = m.id " +
                     "JOIN dosen d ON p.dosen_id = d.id " +
                     "WHERE p.dosen_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dosenId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PengajuanPembimbing pengajuan = new PengajuanPembimbing(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getInt("dosen_id"),
                        resultSet.getString("status")
                    );
                    pengajuan.setNamaMahasiswa(resultSet.getString("nama_mahasiswa"));
                    pengajuan.setNamaDosen(resultSet.getString("nama_dosen"));
                    list.add(pengajuan);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByDosenId: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<PengajuanPembimbing> findByMahasiswaId(int mahasiswaId) {
        List<PengajuanPembimbing> list = new ArrayList<>();
        String sql = "SELECT p.*, m.nama AS nama_mahasiswa, d.nama AS nama_dosen " +
                     "FROM pengajuan_pembimbing p " +
                     "JOIN mahasiswa m ON p.mahasiswa_id = m.id " +
                     "JOIN dosen d ON p.dosen_id = d.id " +
                     "WHERE p.mahasiswa_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mahasiswaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PengajuanPembimbing pengajuan = new PengajuanPembimbing(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getInt("dosen_id"),
                        resultSet.getString("status")
                    );
                    pengajuan.setNamaMahasiswa(resultSet.getString("nama_mahasiswa"));
                    pengajuan.setNamaDosen(resultSet.getString("nama_dosen"));
                    list.add(pengajuan);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByMahasiswaId: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void updateStatus(int idPengajuan, String statusBaru) {
        String sql = "UPDATE pengajuan_pembimbing SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, statusBaru);
            statement.setInt(2, idPengajuan);
            
            int rowsUpdated = statement.executeUpdate();
            if(rowsUpdated > 0) {
                 System.out.println("✅ Status pengajuan berhasil diubah menjadi: " + statusBaru);
            } else {
                 System.out.println("❌ Gagal! ID Pengajuan tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error updateStatus: " + e.getMessage());
        }
    }
}
