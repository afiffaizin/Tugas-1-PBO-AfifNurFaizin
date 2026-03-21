CREATE DATABASE IF NOT EXISTS db_tugas_akhir;
USE db_tugas_akhir;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('MAHASISWA', 'DOSEN') NOT NULL
);

CREATE TABLE mahasiswa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    nim VARCHAR(20) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL,
    jurusan VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE    
);

CREATE TABLE dosen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    nidn VARCHAR(20) NOT NULL UNIQUE,
    nama VARCHAR(100) NOT NULL,
    departemen VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE proposal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mahasiswa_id INT NOT NULL,
    judul VARCHAR(255) NOT NULL,
    latar_belakang TEXT,
    status ENUM('DIAJUKAN', 'DITERIMA', 'DITOLAK') DEFAULT 'DIAJUKAN',
    FOREIGN KEY (mahasiswa_id) REFERENCES mahasiswa(id) ON DELETE CASCADE
);

CREATE TABLE pengajuan_pembimbing (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mahasiswa_id INT NOT NULL,
    dosen_id INT NOT NULL,
    status ENUM('MENUNGGU', 'DITERIMA', 'DITOLAK') DEFAULT 'MENUNGGU',
    FOREIGN KEY (mahasiswa_id) REFERENCES mahasiswa(id) ON DELETE CASCADE,
    FOREIGN KEY (dosen_id) REFERENCES dosen(id) ON DELETE CASCADE
);

CREATE TABLE logbook_bimbingan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mahasiswa_id INT NOT NULL,
    dosen_id INT NOT NULL,
    tanggal DATE NOT NULL,
    kegiatan TEXT NOT NULL,
    catatan_dosen TEXT,
    status ENUM('DIAJUKAN', 'DISETUJUI') DEFAULT 'DIAJUKAN',
    FOREIGN KEY (mahasiswa_id) REFERENCES mahasiswa(id) ON DELETE CASCADE,
    FOREIGN KEY (dosen_id) REFERENCES dosen(id) ON DELETE CASCADE
);
