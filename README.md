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
