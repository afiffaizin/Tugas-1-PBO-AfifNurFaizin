package dao;

import java.util.List;

/**
 * Antarmuka dasar (Base Interface) untuk semua operasi Data Access Object (DAO).
 * Menggunakan konsep Generics <T> dalam Object-Oriented Programming untuk 
 * menangani berbagai entitas (User, Mahasiswa, Dosen, dll) secara dinamis.
 */
public interface BaseDAO<T> {
    
    // Create: Menyimpan objek baru ke database
    void insert(T entity);

    // Read: Mengambil satu objek berdasarkan ID
    T findById(int id);

    // Read: Mengambil semua data dalam bentuk List
    List<T> findAll();

    // Update: Memperbarui data objek di database
    void update(T entity);

    // Delete: Menghapus data dari database berdasarkan ID
    void delete(int id);
}
