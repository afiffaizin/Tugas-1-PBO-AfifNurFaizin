package dao;

import model.Mahasiswa;

/**
 * Interface khusus untuk entitas Mahasiswa.
 * Meng-extend BaseDAO dengan parameter <Mahasiswa>.
 */
public interface MahasiswaDAO extends BaseDAO<Mahasiswa> {
    
    /**
     * Mengambil data Mahasiswa spesifik berdasarkan NIM
     */
    Mahasiswa findByNim(String nim);
    
    /**
     * Memeriksa apakah NIM sudah terdaftar
     */
    boolean isNimExist(String nim);
}
