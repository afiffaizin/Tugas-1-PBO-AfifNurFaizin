package model;

// Inheritance: Dosen mewarisi User
public class Dosen extends User {
    private int dosenId;
    private String nidn;
    private String nama;
    private String departemen;

    public Dosen(int id, String username, String password, int dosenId, String nidn, String nama, String departemen) {
        super(id, username, password, "DOSEN");
        this.dosenId = dosenId;
        this.nidn = nidn;
        this.nama = nama;
        this.departemen = departemen;
    }

    // Enkapsulasi Getter & Setter
    public int getDosenId() {
        return dosenId;
    }

    public void setDosenId(int dosenId) {
        this.dosenId = dosenId;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    // Polimorfisme: Override abstract method dari class User
    @Override
    public void tampilkanMenu() {
        System.out.println("=== Menu Dosen ===");
        System.out.println("1. Terima/Tolak Bimbingan");
        System.out.println("2. Verifikasi & Isi Catatan Logbook Mahasiswa");
        System.out.println("3. Lihat Riwayat Keseluruhan Logbook Mahasiswa");
        System.out.println("4. Logout");
        System.out.println("==================");
    }
}
