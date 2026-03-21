package dao;

import model.LogbookBimbingan;
import java.util.List;

public interface LogbookBimbinganDAO extends BaseDAO<LogbookBimbingan> {
    
    // Untuk Mahasiswa melihat semua logbook bimbingan mereka
    List<LogbookBimbingan> findByMahasiswaId(int mahasiswaId);
    
    // Untuk Dosen melihat semua logbook yang diajukan oleh mahasiswa bimbingan mereka
    List<LogbookBimbingan> findByDosenId(int dosenId);
    
    // Untuk Dosen menambahkan catatan atau mengubah status.
    void updateCatatanDanStatus(int logbookId, String catatan, String status);
}
