package dao;

import model.PengajuanPembimbing;
import java.util.List;

public interface PengajuanPembimbingDAO extends BaseDAO<PengajuanPembimbing> {
    
    // Mengecek apakah mahasiswa sudah pernah mengajukan pembimbing
    boolean hasPengajuan(int mahasiswaId);
    
    // Dosen melihat daftar mahasiswa yang mengajukan bimbingan kepadanya
    List<PengajuanPembimbing> findByDosenId(int dosenId);
    
    // Mahasiswa melihat status pengajuannya sendiri
    List<PengajuanPembimbing> findByMahasiswaId(int mahasiswaId);
    
    // Dosen mengubah status menjadi DITERIMA atau DITOLAK
    void updateStatus(int idPengajuan, String statusBaru);
}
