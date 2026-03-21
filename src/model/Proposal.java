package model;

public class Proposal {
    private int id;
    private int mahasiswaId;
    private String judul;
    private String latarBelakang;
    private String status;

    public Proposal(int id, int mahasiswaId, String judul, String latarBelakang, String status) {
        this.id = id;
        this.mahasiswaId = mahasiswaId;
        this.judul = judul;
        this.latarBelakang = latarBelakang;
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

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLatarBelakang() {
        return latarBelakang;
    }

    public void setLatarBelakang(String latarBelakang) {
        this.latarBelakang = latarBelakang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
