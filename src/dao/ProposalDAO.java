package dao;

import model.Proposal;
import java.util.List;

public interface ProposalDAO extends BaseDAO<Proposal> {
    
    // Method spesifik untuk mengambil daftar proposal per mahasiswa
    List<Proposal> findByMahasiswaId(int mahasiswaId);
    
    // Method untuk mengecek apakah mahasiswa sudah pernah mengajukan proposal agar tidak ganda
    boolean hasProposal(int mahasiswaId);
}
