package model;

import java.sql.Date;

public class LogbookBimbingan {
    private int id;
    private int mahasiswaId;
    private int dosenId;
    private Date tanggal;
    private String kegiatan;
    private String catatanDosen;
    private String status;

    // Properti untuk JOIN query
    private String namaMahasiswa;
    private String namaDosen;

    public LogbookBimbingan(int id, int mahasiswaId, int dosenId, Date tanggal, String kegiatan, String catatanDosen, String status) {
        this.id = id;
        this.mahasiswaId = mahasiswaId;
        this.dosenId = dosenId;
        this.tanggal = tanggal;
        this.kegiatan = kegiatan;
        this.catatanDosen = catatanDosen;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMahasiswaId() {
        return mahasiswaId;
    }

    public void setMahasiswaId(int mahasiswaId) {
        this.mahasiswaId = mahasiswaId;
    }

    public int getDosenId() {
        return dosenId;
    }

    public void setDosenId(int dosenId) {
        this.dosenId = dosenId;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getCatatanDosen() {
        return catatanDosen;
    }

    public void setCatatanDosen(String catatanDosen) {
        this.catatanDosen = catatanDosen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public void setNamaMahasiswa(String namaMahasiswa) {
        this.namaMahasiswa = namaMahasiswa;
    }

    public String getNamaDosen() {
        return namaDosen;
    }

    public void setNamaDosen(String namaDosen) {
        this.namaDosen = namaDosen;
    }
}
