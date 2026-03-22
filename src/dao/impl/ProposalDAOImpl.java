package dao.impl;

import config.DatabaseConfig;
import dao.ProposalDAO;
import model.Proposal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProposalDAOImpl implements ProposalDAO {

    private Connection connection = DatabaseConfig.getConnection();

    @Override
    public void insert(Proposal entity) {
        String sql = "INSERT INTO proposal (mahasiswa_id, judul, latar_belakang, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getMahasiswaId());
            statement.setString(2, entity.getJudul());
            statement.setString(3, entity.getLatarBelakang());
            statement.setString(4, entity.getStatus());
            
            statement.executeUpdate();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Proposal berhasil diajukan!");
        } catch (SQLException e) {
            System.err.println("Gagal mengajukan proposal: " + e.getMessage());
        }
    }

    @Override
    public Proposal findById(int id) {
        String sql = "SELECT * FROM proposal WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Proposal(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getString("judul"),
                        resultSet.getString("latar_belakang"),
                        resultSet.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findById Proposal: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Proposal> findAll() {
        List<Proposal> proposals = new ArrayList<>();
        String sql = "SELECT * FROM proposal";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                proposals.add(new Proposal(
                    resultSet.getInt("id"),
                    resultSet.getInt("mahasiswa_id"),
                    resultSet.getString("judul"),
                    resultSet.getString("latar_belakang"),
                    resultSet.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Gagal findAll Proposal: " + e.getMessage());
        }
        return proposals;
    }

    @Override
    public void update(Proposal entity) {
        String sql = "UPDATE proposal SET judul = ?, latar_belakang = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getJudul());
            statement.setString(2, entity.getLatarBelakang());
            statement.setString(3, entity.getStatus());
            statement.setInt(4, entity.getId());
            
            statement.executeUpdate();
            System.out.println("Proposal berhasil diupdate.");
        } catch (SQLException e) {
            System.err.println("Gagal update Proposal: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM proposal WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Proposal berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Gagal delete Proposal: " + e.getMessage());
        }
    }

    @Override
    public List<Proposal> findByMahasiswaId(int mahasiswaId) {
        List<Proposal> proposals = new ArrayList<>();
        String sql = "SELECT * FROM proposal WHERE mahasiswa_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mahasiswaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    proposals.add(new Proposal(
                        resultSet.getInt("id"),
                        resultSet.getInt("mahasiswa_id"),
                        resultSet.getString("judul"),
                        resultSet.getString("latar_belakang"),
                        resultSet.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal findByMahasiswaId Proposal: " + e.getMessage());
        }
        return proposals;
    }

    @Override
    public boolean hasProposal(int mahasiswaId) {
        String sql = "SELECT id FROM proposal WHERE mahasiswa_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mahasiswaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error cek status proposal mahasiswa: " + e.getMessage());
            return false;
        }
    }
}
