package dao;

import model.Dosen;

/**
 * Interface khusus untuk entitas Dosen.
 * Meng-extend BaseDAO dengan parameter <Dosen>.
 */
public interface DosenDAO extends BaseDAO<Dosen> {
    
    /**
     * Mengambil data Dosen spesifik berdasarkan NIDN-nya
     */
    Dosen findByNidn(String nidn);
    
    /**
     * Memeriksa apakah NIDN sudah terdaftar
     */
    boolean isNidnExist(String nidn);
}
