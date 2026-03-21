# Tugas-1-PBO-AfifNurFaizin

# Bagian 1: ANALASIS SISTEM

## 1. Identifikasi Class Utama

| Class                                    | Atribut                                                                                                       | Method                                                                                                                                                     |
| ---------------------------------------- | ------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **User** _(Abstract Class)_              | `id`, `username`, `password`, `role`                                                                          | `tampilkanMenu()`                                                                                                                                          |
| **Mahasiswa** _(extends User)_           | `mahasiswaId`, `nim`, `nama`, `jurusan`                                                                       | `tampilkanMenu()`, `getMahasiswaId()`, `getNim()`, `getNama()`, `getJurusan()`                                                                             |
| **Dosen** _(extends User)_               | `dosenId`, `nidn`, `nama`, `departemen`                                                                       | `tampilkanMenu()`, `getDosenId()`, `getNidn()`, `getNama()`, `getDepartemen()`                                                                             |
| **Proposal**                             | `id`, `mahasiswaId`, `judul`, `latarBelakang`, `status`                                                       | `getId()`, `getMahasiswaId()`, `getJudul()`, `getLatarBelakang()`, `getStatus()`                                                                           |
| **PengajuanPembimbing**                  | `id`, `mahasiswaId`, `dosenId`, `status`, `namaMahasiswa`, `namaDosen`                                        | `getId()`, `getMahasiswaId()`, `getDosenId()`, `getStatus()`, `getNamaMahasiswa()`, `getNamaDosen()`                                                       |
| **LogbookBimbingan**                     | `id`, `mahasiswaId`, `dosenId`, `tanggal`, `kegiatan`, `catatanDosen`, `status`, `namaMahasiswa`, `namaDosen` | `getId()`, `getMahasiswaId()`, `getDosenId()`, `getTanggal()`, `getKegiatan()`, `getCatatanDosen()`, `getStatus()`, `getNamaMahasiswa()`, `getNamaDosen()` |
| **DatabaseConfig**                       | `URL`, `USER`, `PASSWORD`, `connection`                                                                       | `getConnection()`                                                                                                                                          |
| **BaseDAO\<T>** _(Interface)_            | -                                                                                                             | `insert()`, `findById()`, `findAll()`, `update()`, `delete()`                                                                                              |
| **UserDAO** _(Interface)_                | -                                                                                                             | `authenticate()`, `isUsernameExist()`                                                                                                                      |
| **MahasiswaDAO** _(Interface)_           | -                                                                                                             | `findByNim()`, `isNimExist()`                                                                                                                              |
| **DosenDAO** _(Interface)_               | -                                                                                                             | `findByNidn()`, `isNidnExist()`                                                                                                                            |
| **ProposalDAO** _(Interface)_            | -                                                                                                             | `findByMahasiswaId()`, `hasProposal()`                                                                                                                     |
| **PengajuanPembimbingDAO** _(Interface)_ | -                                                                                                             | `hasPengajuan()`, `findByDosenId()`, `findByMahasiswaId()`, `updateStatus()`                                                                               |
| **LogbookBimbinganDAO** _(Interface)_    | -                                                                                                             | `findByMahasiswaId()`, `findByDosenId()`, `updateCatatanDanStatus()`                                                                                       |
| **UserDAOImpl**                          | `connection`                                                                                                  | `authenticate()`, `isUsernameExist()`, `insert()`, `findById()`, `findAll()`, `update()`, `delete()`                                                       |
| **MahasiswaDAOImpl**                     | `connection`                                                                                                  | `findByNim()`, `isNimExist()`, `insert()`, `findById()`, `findAll()`, `update()`                                                                           |

### Sistem yang sudah dibuat ini menerapkan arsitektur perangkat lunak terstruktur, dan berpusat pada dua konsep utama:

1. Data Access Object (DAO) Design Pattern: Ini adalah pola desain struktural yang bertujuan memisahkan logika akses data (database) dari antarmuka pengguna atau logika bisnis.
2. Pemrograman Berorientasi Objek (OOP) :adalah cara membuat program dengan menggunakan objek sebagai dasar utama.

## 2. Hubungan Antar Class

Semua atribut di dalam tiap _Class_ dideklarasikan dengan Modifikasi Akses `private`. Ini merupakan implementasi enkapulasi agar data tidak rusak oleh modifikasi instan dari entitas luar.

**a) INHERITANCE (Pewarisan)**

- Mahasiswa extends User
  -> Mahasiswa mewarisi seluruh atribut dan method dari User.
- Dosen extends User
  -> Dosen mewarisi seluruh atribut dan method dari User.
- Semua DAO Interface (UserDAO, MahasiswaDAO, DosenDAO, ProposalDAO,
  PengajuanPembimbingDAO, LogbookBimbinganDAO) extends BaseDAO<T>
  -> Mewarisi operasi CRUD dasar (insert, findById, findAll, update, delete).

**b) ABSTRACTION (Abstraksi)**

- User adalah abstract class dengan abstract method tampilkanMenu().
  Class anak (Mahasiswa & Dosen) WAJIB mengimplementasikan method tersebut.
- BaseDAO<T> adalah interface generik yang mendefinisikan kontrak operasi
  CRUD untuk semua entitas tanpa menentukan implementasi spesifik.

**c) IMPLEMENTS (Implementasi Interface)**

- UserDAOImpl implements UserDAO
- MahasiswaDAOImpl implements MahasiswaDAO
- DosenDAOImpl implements DosenDAO
- ProposalDAOImpl implements ProposalDAO
- PengajuanPembimbingDAOImpl implements PengajuanPembimbingDAO
- LogbookBimbinganDAOImpl implements LogbookBimbinganDAO

**d) DEPENDENCY (Ketergantungan/Asosiasi)**

- Proposal bergantung pada Mahasiswa (melalui atribut mahasiswaId)  
  -> Setiap proposal dimiliki oleh satu mahasiswa.
- PengajuanPembimbing menghubungkan Mahasiswa dan Dosen
  (melalui atribut mahasiswaId dan dosenId)
  -> Menggambarkan relasi many-to-many antara mahasiswa dan dosen.
- LogbookBimbingan menghubungkan Mahasiswa dan Dosen
  (melalui atribut mahasiswaId dan dosenId)
  -> Setiap logbook mencatat bimbingan antara satu mahasiswa dengan
  satu dosen pembimbing.
- Semua class DAOImpl bergantung pada DatabaseConfig
  (melalui DatabaseConfig.getConnection())
  -> Menggunakan Singleton Pattern untuk koneksi database.
- Main bergantung pada semua DAO interface dan model class
  -> Sebagai pusat kontrol alur program.

## 3. Alasan pemilihan Atribut dan Method

**a) Class User (Abstract)**

- Atribut id, username, password, role dipilih karena setiap pengguna
  sistem membutuhkan identitas unik (id), kredensial login (username &
  password), dan pembedaan hak akses (role: MAHASISWA/DOSEN).
- Method tampilkanMenu() dijadikan abstract karena setiap role memiliki
  menu yang berbeda, sehingga implementasinya diserahkan ke subclass.

**b) Class Mahasiswa**

- Atribut mahasiswaId, nim, nama, jurusan merupakan data spesifik
  mahasiswa yang dibutuhkan sistem. NIM bersifat unik sebagai identitas
  akademik, sedangkan jurusan diperlukan untuk konteks administrasi.
- Method tampilkanMenu() di-override untuk menampilkan menu khusus
  mahasiswa (input proposal, pilih pembimbing, isi logbook, lihat riwayat).

**c) Class Dosen**

- Atribut dosenId, nidn, nama, departemen dipilih karena NIDN adalah
  identitas unik dosen secara nasional, dan departemen menunjukkan
  bidang keahlian yang relevan untuk pembimbingan tugas akhir.
- Method tampilkanMenu() di-override untuk menampilkan menu khusus
  dosen (terima/tolak bimbingan, verifikasi logbook, lihat riwayat).

**d) Class Proposal**

- Atribut judul dan latarBelakang merupakan komponen inti sebuah
  proposal tugas akhir. Atribut status (DIAJUKAN/DISETUJUI/DITOLAK)
  diperlukan untuk melacak progres pengajuan.
- mahasiswaId sebagai foreign key menghubungkan proposal ke pemiliknya.

**e) Class PengajuanPembimbing**

- Atribut mahasiswaId dan dosenId menghubungkan dua entitas utama.
  Status (MENUNGGU/DITERIMA/DITOLAK) melacak proses persetujuan.
- namaMahasiswa dan namaDosen ditambahkan untuk mempermudah tampilan
  data hasil JOIN query tanpa harus melakukan query tambahan.

**f) Class LogbookBimbingan**

- Atribut tanggal, kegiatan, catatanDosen, dan status dipilih karena
  logbook bimbingan perlu mencatat kapan bimbingan dilakukan, apa
  kegiatannya, catatan/feedback dari dosen, dan status verifikasi.
- mahasiswaId dan dosenId menghubungkan logbook ke mahasiswa dan
  dosen pembimbing yang bersangkutan.

**g) Class DatabaseConfig**

- Menggunakan Singleton Pattern agar hanya ada satu koneksi database
  yang dibagikan ke seluruh DAO, sehingga menghemat resource.
- Atribut URL, USER, PASSWORD bersifat final karena konfigurasi database
  tidak berubah selama aplikasi berjalan.

**h) Interface BaseDAO<T>**

- Menggunakan Generics <T> agar satu interface dapat digunakan untuk
  berbagai tipe entitas (User, Mahasiswa, Dosen, dll) tanpa duplikasi.
- 5 method CRUD (insert, findById, findAll, update, delete) merupakan
  operasi standar yang dibutuhkan untuk setiap entitas dalam database.

# Bagian 2: DESAIN CLASS DIAGRAM

## 1. Class Diagram

![Class Diagram](assets/Diagram.webp)

## 2. ERD

![ERD](assets/ERD.png)

# BAGIAN 3 — IMPLEMENTASI PROGRAM JAVA

Sistem Informasi Tugas Akhir adalah aplikasi berbasis Command Line Interface (CLI) yang dibangun menggunakan bahasa pemrograman Java dan database MySQL. Sistem ini dirancang untuk mengelola seluruh alur administrasi Tugas Akhir mahasiswa.

Sistem ini melibatkan dua peran (role) utama yang masing-masing memiliki hak akses dan menu operasi yang berbeda:

| Role          | Deskripsi                                                                                                                                                   |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Mahasiswa** | Dapat mengajukan proposal, memilih dosen pembimbing, mengisi logbook bimbingan, dan melihat riwayat catatan.                                                |
| **Dosen**     | Dapat menerima/menolak pengajuan bimbingan, memverifikasi logbook, memberikan catatan evaluasi, dan melihat riwayat seluruh logbook mahasiswa bimbingannya. |

---

## Fitur-Fitur Utama Sistem

### 1. Menambahkan Data

- **Input Proposal** — Mahasiswa mengisi judul dan latar belakang proposal, kemudian data tersebut disimpan ke database melalui `ProposalDAO.insert()` dengan status awal `"DIAJUKAN"`.
- **Pengajuan Pembimbing** — Mahasiswa memilih dosen dari daftar yang tersedia, sistem menyimpan pengajuan melalui `PengajuanPembimbingDAO.insert()` dengan status awal `"MENUNGGU"`.
- **Isi Logbook** — Mahasiswa mengisi tanggal dan kegiatan bimbingan, data tersimpan melalui `LogbookBimbinganDAO.insert()` dengan status `"DIAJUKAN"`.

### 2. Menampilkan Data

- **Daftar Dosen** — Menampilkan seluruh dosen yang terdaftar di sistem menggunakan `DosenDAO.findAll()`.
- **Riwayat Pengajuan** — Menampilkan riwayat pengajuan pembimbing beserta statusnya menggunakan `PengajuanPembimbingDAO.findByMahasiswaId()`.
- **Riwayat Logbook** — Menampilkan daftar progres bimbingan beserta catatan dosen menggunakan `LogbookBimbinganDAO.findByMahasiswaId()` atau `findByDosenId()`.
- **Daftar Pengajuan Masuk** — Dosen melihat seluruh mahasiswa yang mengajukan bimbingan kepadanya menggunakan `PengajuanPembimbingDAO.findByDosenId()`.

### 3. Mengubah Status Data

- **Terima/Tolak Bimbingan** — Dosen mengubah status pengajuan dari `"MENUNGGU"` menjadi `"DITERIMA"` atau `"DITOLAK"` melalui `PengajuanPembimbingDAO.updateStatus()`.
- **Verifikasi Logbook** — Dosen menyetujui logbook dan memberikan catatan evaluasi, mengubah status logbook dari `"DIAJUKAN"` menjadi `"DISETUJUI"` melalui `LogbookBimbinganDAO.updateCatatanDanStatus()`.

---

## Alur Kerja Sistem (Workflow)

Berikut adalah alur kerja sistem secara **end-to-end** dari awal hingga akhir:

![WorkFLow](assets/workflow.png)

### Detail Alur:

**1. Autentikasi (Login):**

1. Program menampilkan menu awal dengan opsi Login atau Keluar (`while(true)` loop).
2. Pengguna memasukkan `username` dan `password`.
3. Sistem memverifikasi kredensial melalui `UserDAO.authenticate()`.
4. Jika berhasil, sistem mendeteksi role menggunakan `instanceof` dan mengarahkan ke menu yang sesuai.

**2. Pengajuan Proposal (Mahasiswa):**

1. Mahasiswa memilih menu "Input Proposal".
2. Sistem memeriksa apakah mahasiswa sudah memiliki proposal (`hasProposal()`).
3. Jika belum, mahasiswa mengisi judul dan latar belakang.
4. Proposal disimpan ke database dengan status `"DIAJUKAN"`.

**3. Pemilihan Dosen Pembimbing (Mahasiswa):**

1. Mahasiswa melihat riwayat pengajuan pembimbing sebelumnya.
2. Sistem memeriksa status: apakah sudah `DITERIMA`, `MENUNGGU`, atau `DITOLAK`.
3. Jika belum ada atau sebelumnya ditolak, mahasiswa dapat memilih dosen baru dari daftar.
4. Pengajuan disimpan dengan status `"MENUNGGU"`.

**4. Persetujuan Bimbingan (Dosen):**

1. Dosen melihat daftar pengajuan masuk dari mahasiswa.
2. Dosen memilih ID pengajuan yang ingin diproses.
3. Dosen memutuskan `"DITERIMA"` atau `"DITOLAK"`.
4. Status di database diperbarui melalui `updateStatus()`.

**5. Pencatatan & Verifikasi Logbook (Mahasiswa & Dosen):**

1. **Mahasiswa:** Mengisi tanggal dan kegiatan bimbingan (status awal: `"DIAJUKAN"`).
2. **Dosen:** Melihat logbook yang menunggu verifikasi, memberikan catatan evaluasi, dan mengubah status menjadi `"DISETUJUI"`.

## Teknologi dan Tools yang Digunakan

| Komponen           | Teknologi                        |
| ------------------ | -------------------------------- |
| Bahasa Pemrograman | **Java** (JDK)                   |
| Database           | **MySQL**                        |
| Driver Koneksi     | **MySQL Connector/J** (JDBC)     |
| Antarmuka          | **Command Line Interface (CLI)** |
| IDE                | Visual Studio Code               |

---

## Cara Menjalankan Program

### Persyaratan:

- Pastikan Java JDK terinstal
- MySQL Server aktif
- File driver `mysql-connector-j-*.jar` tersedia di folder `/lib`

### Langkah-langkah:

**1. Buat Database:**

```sql
-- Jalankan file database.sql di MySQL
SOURCE database.sql;
```

**2. Kompilasi Program:**

```shell
javac -cp ".;lib/*" -d bin src\config\*.java src\model\*.java src\dao\*.java src\dao\impl\*.java src\Main.java
```

**3. Jalankan Program:**

```shell
java -cp "bin;lib/*" Main
```

---

# BAGIAN 4 — ANALISIS KONSEP PBO

---

## 1. Penerapan Class dan Object

**Class** adalah blueprint yang mendefinisikan atribut dan method. **Object** adalah instance nyata dari class tersebut saat program berjalan.

**Penerapan dalam program:**

- Class `User`, `Mahasiswa`, `Dosen`, `Proposal`, `PengajuanPembimbing`, dan `LogbookBimbingan` mendefinisikan struktur data setiap entitas.
- Object dibuat saat program berjalan, contoh:

```java
// Object Mahasiswa dibuat saat login berhasil
Mahasiswa mhs = (Mahasiswa) user;

// Object Proposal dibuat saat mahasiswa mengajukan judul
Proposal pBaru = new Proposal(0, mhs.getMahasiswaId(), judul, latar, "DIAJUKAN");

// Object DAO dibuat untuk mengakses database
ProposalDAO proposalDao = new ProposalDAOImpl();
proposalDao.insert(pBaru); // Object digunakan untuk menyimpan data
```

Class adalah "blueprint"-nya, Object adalah wujud nyatanya yang menyimpan data dan menjalankan operasi.

---

## 2. Penerapan Enkapsulasi

Enkapsulasi adalah menyembunyikan data internal dan menyediakan akses terkendali melalui getter/setter.

**Penerapan dalam program:**

Semua atribut di setiap class dideklarasikan `private`, sehingga tidak bisa diakses langsung dari luar:

```java
// Di class Mahasiswa
private String nim;    // Tidak bisa diakses langsung: mhs.nim = "123" → ERROR

// Akses hanya melalui getter dan setter
public String getNim() { return nim; }       // Membaca data
public void setNim(String nim) { this.nim = nim; } // Mengubah data
```

**Manfaatnya:** Data sensitif seperti, password, NIM, NIDN terlindungi dari modifikasi sembarangan.

---

## 3. Mengapa PBO Lebih Baik dari Prosedural?

| Aspek            | PBO                                                                                                    | Prosedural                                                                    |
| ---------------- | ------------------------------------------------------------------------------------------------------ | ----------------------------------------------------------------------------- |
| **Organisasi**   | Kode terpisah rapi per class (`User`, `Proposal`, `ProposalDAOImpl`, dll.)                             | Semua kode tercampur dalam satu alur panjang                                  |
| **Reusability**  | `Mahasiswa` dan `Dosen` mewarisi `User` — tidak perlu tulis ulang atribut `id`, `username`, `password` | Setiap fungsi harus ditulis ulang untuk tiap entitas                          |
| **Skalabilitas** | JIka ingin tambah role baru? Cukup buat class baru `extends User` tanpa ubah kode lama                 | Harus modifikasi banyak fungsi dan tambah `if-else` di mana-mana              |
| **Keamanan**     | Atribut `private` + getter/setter melindungi data dari akses sembarangan                               | Variabel global bisa diubah dari mana saja                                    |
| **Polimorfisme** | `user.tampilkanMenu()` otomatis tampil sesuai role tanpa percabangan manual                            | Harus tulis `if (role == "MHS") {...} else if (role == "DSN") {...}` berulang |

**Kesimpulan:** Dengan PBO, sistem ini memberikan struktur yang lebih terorganisir, aman, dan mudah dikembangkan dibandingkan prosedural.

---

# BAGIAN 5 — REFLEKSI

---

## 1. Bagian yang Paling Sulit

Bagian yang paling sulit dalam mengerjakan tugas ini adalah **merancang dan mengimplementasikan arsitektur DAO (Data Access Object) Pattern secara konsisten untuk seluruh entitas**. Tantangannya bukan hanya menulis query SQL, tetapi memastikan setiap layer (Interface → Implementation) saling terhubung dengan benar dan konsisten.

## 2. Hal Baru yang Dipelajari tentang Konsep PBO

- **Abstract Class sebagai Kontrak yang Dipaksakan** — Sebelumnya saya memahami `abstract` hanya sebagai "class yang tidak bisa di-instantiate". Namun dalam implementasi `User` dengan method `tampilkanMenu()`, saya memahami bahwa abstract class berfungsi sebagai **kontrak** yang _memaksa_ setiap subclass untuk menyediakan implementasi spesifiknya sendiri. Ini menjamin setiap role pasti memiliki menu, tanpa ada yang terlewat.

- **Polimorfisme dalam Praktik Nyata** — Menulis `user.tampilkanMenu()` yang secara otomatis menampilkan menu berbeda tergantung apakah `user` tersebut instance dari `Mahasiswa` atau `Dosen` memberikan pemahaman konkret tentang bagaimana polimorfisme menghilangkan kebutuhan percabangan `if-else` yang berulang.

- **Singleton Pattern untuk Manajemen Resource** — Implementasi `DatabaseConfig` dengan Lazy Initialization mengajarkan bahwa pattern ini bukan hanya soal membatasi jumlah instance, tetapi juga soal **efisiensi pengelolaan resource** yang mahal seperti koneksi database.

- **Separation of Concerns** — Memisahkan kode ke dalam package `model`, `dao`, `dao.impl`, dan `config` memberikan pengalaman langsung tentang bagaimana arsitektur modular membuat kode lebih mudah dibaca, di-debug, dan dikembangkan.

---

## 3. Fitur yang Akan Ditambahkan untuk Pengembangan Lebih Lanjut

Jika sistem ini dikembangkan lebih lanjut, berikut fitur-fitur yang akan ditambahkan:

1. **Sistem Registrasi Akun** — Menambahkan fitur registrasi mandiri untuk Mahasiswa dan Dosen, sehingga tidak perlu input data langsung ke database. Termasuk validasi duplikasi username, NIM, dan NIDN saat pendaftaran.

2. **Penjadwalan Sidang Tugas Akhir** — Menambahkan modul penjadwalan sidang yang mencakup penentuan tanggal, ruang sidang, dan dosen penguji.

3. **Penilaian dan Revisi Tugas Akhir** — Fitur yang memungkinkan dosen penguji memberikan nilai dan catatan revisi setelah sidang, serta mahasiswa dapat melacak status revisi yang harus diselesaikan.

4. **Dashboard Statistik** — Menampilkan ringkasan data seperti jumlah proposal yang diajukan, jumlah mahasiswa yang sudah memiliki pembimbing, progres logbook per mahasiswa, dan statistik keseluruhan sistem.

5. **Notifikasi dan Riwayat Aktivitas** — Sistem pencatatan aktivitas ang merekam setiap aksi penting (login, pengajuan, persetujuan, verifikasi), sehingga pengguna dapat melihat timeline aktivitas mereka.

6. **Migrasi ke GUI** — Mengembangkan antarmuka dari CLI ke GUI berbasis **JavaFX** atau **web-based** agar sistem lebih user-friendly dan dapat diakses secara visual oleh pengguna yang kurang familiar dengan command line.
