import dao.DosenDAO;
import dao.LogbookBimbinganDAO;
import dao.PengajuanPembimbingDAO;
import dao.ProposalDAO;
import dao.UserDAO;
import dao.impl.DosenDAOImpl;
import dao.impl.LogbookBimbinganDAOImpl;
import dao.impl.PengajuanPembimbingDAOImpl;
import dao.impl.ProposalDAOImpl;
import dao.impl.UserDAOImpl;
import model.Dosen;
import model.LogbookBimbingan;
import model.Mahasiswa;
import model.PengajuanPembimbing;
import model.Proposal;
import model.User;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Inisialisasi Seluruh DAO Interfaces
        UserDAO userDao = new UserDAOImpl();
        ProposalDAO proposalDao = new ProposalDAOImpl();
        DosenDAO dosenDao = new DosenDAOImpl();
        PengajuanPembimbingDAO pengajuanDao = new PengajuanPembimbingDAOImpl();
        LogbookBimbinganDAO logbookDao = new LogbookBimbinganDAOImpl();

        while (true) {
            System.out.println("       SISTEM INFORMASI TUGAS AKHIR        ");
            System.out.println("============================================");
            System.out.println("Menu Awal:");
            System.out.println("1. Login");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilih menu (1/0): ");
            
            String pilihan = scanner.nextLine();
            
            if (pilihan.equals("0")) {
                System.out.println("Terima kasih telah menggunakan sistem ini. Sampai jumpa!");
                break;
            } else if (pilihan.equals("1")) {
                System.out.println("\nSILAKAN LOGIN");
                System.out.print("Username : ");
                String username = scanner.nextLine();
                
                System.out.print("Password : ");
                String password = scanner.nextLine();

                System.out.println("\nMemeriksa kredensial...");
                User user = userDao.authenticate(username, password);

                if (user != null) {
                    System.out.println("LOGIN BERHASIL!\n");
                    
                    // MENU UNTUK ROLE MAHASISWA
                    if (user instanceof Mahasiswa) {
                        Mahasiswa mhs = (Mahasiswa) user;
                        boolean isMhsLoggedIn = true;
                        
                        while(isMhsLoggedIn) {
                            System.out.println("\nSelamat Datang: " + mhs.getNama() + " (" + mhs.getNim() + ")");
                            mhs.tampilkanMenu();
                            System.out.print("Pilih aksi (1-5): ");
                            String aksiMhs = scanner.nextLine();
                            
                            switch (aksiMhs) {
                                case "1":
                                    System.out.println("\nMENGINPUT PROPOSAL BARU");
                                    if(proposalDao.hasProposal(mhs.getMahasiswaId())) {
                                        System.out.println("Anda sudah pernah mengajukan proposal sebelumnya!");
                                    } else {
                                        System.out.print("Masukkan Judul Proposal: ");
                                        String judul = scanner.nextLine();
                                        System.out.print("Masukkan Latar Belakang Singkat: ");
                                        String latar = scanner.nextLine();
                                        
                                        Proposal pBaru = new Proposal(0, mhs.getMahasiswaId(), judul, latar, "DIAJUKAN");
                                        proposalDao.insert(pBaru);
                                    }
                                    break;
                                    
                                case "2":
                                    System.out.println("\nFITUR PILIH PEMBIMBING");
                                    // Ambil semua daftar riwayat pengajuan pembimbing mahasiswa ini
                                    List<PengajuanPembimbing> riwayat = pengajuanDao.findByMahasiswaId(mhs.getMahasiswaId());
                                    
                                    boolean bolehMengajukan = true;
                                    boolean sudahDiterima = false;
                                    String namaDpn = "";
                                    
                                    System.out.println("Riwayat pengajuan pembimbing Anda:");
                                    
                                    if(riwayat.isEmpty()) {
                                         System.out.println("- Belum ada riwayat pengajuan.");
                                    } else {
                                        for(PengajuanPembimbing p : riwayat) {
                                            System.out.println("- Dosen: " + p.getNamaDosen() + " | Status: " + p.getStatus());
                                            
                                            if("DITERIMA".equals(p.getStatus())) {
                                                sudahDiterima = true;
                                                namaDpn = p.getNamaDosen();
                                                bolehMengajukan = false; 
                                            } else if("MENUNGGU".equals(p.getStatus())) {
                                                bolehMengajukan = false;
                                            }
                                        }
                                    }
                                    
                                    if(sudahDiterima) {
                                        System.out.println("\nAnda sudah mendapatkan pembimbing: " + namaDpn);
                                    } else if(!bolehMengajukan) {
                                        System.out.println("\nAnda tidak bisa mengajukan ulang karena status pengajuan Anda masih MENUNGGU.");
                                    } else {
                                        System.out.println("\nMENGISI FORM PENGAJUAN DOSEN PEMBIMBING");
                                        System.out.println("Daftar Dosen Tersedia:");
                                        List<Dosen> daftarDosen = dosenDao.findAll();
                                        for(Dosen dsnItem : daftarDosen) {
                                            System.out.println("[" + dsnItem.getDosenId() + "] " + dsnItem.getNama() + " - " + dsnItem.getDepartemen());
                                        }
                                        
                                        System.out.print("\nMasukkan Angka [ID] Dosen yang ingin Anda pilih: ");
                                        try {
                                            int idPilihan = Integer.parseInt(scanner.nextLine());
                                            
                                            Dosen dsnDipilih = dosenDao.findById(idPilihan);
                                            if (dsnDipilih != null) {
                                                PengajuanPembimbing ajukan = new PengajuanPembimbing(
                                                    0, 
                                                    mhs.getMahasiswaId(), 
                                                    idPilihan, 
                                                    "MENUNGGU"
                                                );
                                                pengajuanDao.insert(ajukan);
                                            } else {
                                                System.out.println("ID dosen tidak ditemukan!");
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Mohon masukkan angka ID yang benar!");
                                        }
                                    }
                                    break;
                                    
                                case "3":
                                    System.out.println("\nISI PROGRES LOGBOOK");
                                    List<PengajuanPembimbing> cekPembimbing = pengajuanDao.findByMahasiswaId(mhs.getMahasiswaId());
                                    int dosenFixedId = -1;
                                    String namaDosenFixed = "";
                                    
                                    for(PengajuanPembimbing p : cekPembimbing) {
                                        if ("DITERIMA".equals(p.getStatus())) {
                                            dosenFixedId = p.getDosenId();
                                            namaDosenFixed = p.getNamaDosen();
                                            break;
                                        }
                                    }
                                    
                                    if(dosenFixedId == -1) {
                                         System.out.println("Anda belum memiliki dosen pembimbing yang disetujui.");
                                         System.out.println("Silakan ajukan melalui Menu 2, atau tunggu persetujuan dosen terlebih dahulu.");
                                    } else {
                                         System.out.println("Dosen Pembimbing Anda: " + namaDosenFixed);
                                         System.out.println("\nTambah Logbook Baru:");
                                         System.out.print("Masukkan Tanggal (YYYY-MM-DD): ");
                                         try {
                                             String tglStr = scanner.nextLine();
                                             Date tanggalVal = Date.valueOf(tglStr);
                                             
                                             System.out.print("Kegiatan Bimbingan yang dilakukan: ");
                                             String kegiatan = scanner.nextLine();
                                             
                                             LogbookBimbingan lb = new LogbookBimbingan(0, mhs.getMahasiswaId(), dosenFixedId, tanggalVal, kegiatan, null, "DIAJUKAN");
                                             logbookDao.insert(lb);
                                         } catch (IllegalArgumentException e) {
                                             System.out.println("Format tanggal salah. Pastikan format YYYY-MM-DD (contoh: 2026-03-16)");
                                         }
                                    }
                                    break;
                                    
                                case "4":
                                    System.out.println("\nLIHAT RIWAYAT LOGBOOK & CATATAN");
                                    List<PengajuanPembimbing> curPembimbing = pengajuanDao.findByMahasiswaId(mhs.getMahasiswaId());
                                    boolean hasDosen = false;
                                    for(PengajuanPembimbing p : curPembimbing) {
                                        if ("DITERIMA".equals(p.getStatus())) {
                                            hasDosen = true;
                                            System.out.println("Dosen Pembimbing Anda: " + p.getNamaDosen());
                                            break;
                                        }
                                    }
                                    
                                    if(!hasDosen) {
                                         System.out.println("Anda belum memiliki dosen pembimbing yang disetujui.");
                                    } else {
                                         List<LogbookBimbingan> historis = logbookDao.findByMahasiswaId(mhs.getMahasiswaId());
                                         if (historis.isEmpty()) {
                                              System.out.println("Belum ada riwayat logbook yang diisi.");
                                         } else {
                                              System.out.println("Daftar Progres Anda:");
                                              for (LogbookBimbingan l : historis) {
                                                  System.out.println("Tanggal: " + l.getTanggal() + " | Kegiatan: " + l.getKegiatan());
                                                  System.out.println("Status: " + l.getStatus() + " | Catatan Dosen: " + (l.getCatatanDosen() != null ? l.getCatatanDosen() : "(Menunggu Diperiksa)"));
                                                  System.out.println("-------------------------------------------------------------");
                                              }
                                         }
                                    }
                                    break;
                                    
                                case "5":
                                    isMhsLoggedIn = false;
                                    System.out.println("Berhasil Logout.\n");
                                    break;
                                    
                                default:
                                    System.out.println("Pilihan tidak valid!");
                            }
                        }
                        
                    // MENU UNTUK ROLE DOSEN
                    } else if (user instanceof Dosen) {
                        Dosen dsn = (Dosen) user;
                        boolean isDsnLoggedIn = true;
                        
                        while(isDsnLoggedIn) {
                            System.out.println("\nSelamat Datang Dosen: " + dsn.getNama() + " (" + dsn.getNidn() + ")");
                            dsn.tampilkanMenu();
                            System.out.print("Pilih aksi (1-4): ");
                            String aksiDsn = scanner.nextLine();
                            
                            switch (aksiDsn) {
                                case "1":
                                    System.out.println("\nTERIMA/TOLAK PENDAMPINGAN");
                                    List<PengajuanPembimbing> daftarPengajuan = pengajuanDao.findByDosenId(dsn.getDosenId());
                                    
                                    if(daftarPengajuan.isEmpty()) {
                                         System.out.println("Belum ada mahasiswa yang mengajukan diri ke Anda.");
                                    } else {
                                        System.out.println("Daftar Pengajuan Masuk:");
                                        for(PengajuanPembimbing pb : daftarPengajuan) {
                                            System.out.println("ID Pengajuan: [" + pb.getId() + "] | Mahasiswa: " + pb.getNamaMahasiswa() + " | Status: " + pb.getStatus());
                                        }
                                        System.out.print("\nKetik ID Pengajuan yang ingin Anda proses (Atau ketik 0 untuk batal): ");
                                        try {
                                            int idProses = Integer.parseInt(scanner.nextLine());
                                            if (idProses != 0) {
                                                PengajuanPembimbing pengajuanTerpilih = null;
                                                for (PengajuanPembimbing pb : daftarPengajuan) {
                                                    if (pb.getId() == idProses) {
                                                        pengajuanTerpilih = pb;
                                                        break;
                                                    }
                                                }

                                                if (pengajuanTerpilih == null) {
                                                    System.out.println("Data tidak ditemukan. Pastikan Anda memasukkan ID Pengajuan yang benar.");
                                                } else if ("DITERIMA".equals(pengajuanTerpilih.getStatus())) {
                                                    System.out.println("Pengajuan ini telah berstatus DITERIMA.");
                                                    System.out.println("Sesuai kebijakan akademik, dosen tidak diperkenankan membatalkan kesediaan pembimbingan");
                                                    System.out.println("yang telah disetujui sebelumnya secara sepihak.");
                                                } else {
                                                    System.out.println("Ubah Status Menjadi:");
                                                    System.out.println("1. DITERIMA");
                                                    System.out.println("2. DITOLAK");
                                                    System.out.print("Pilih (1/2): ");
                                                    String mPilih = scanner.nextLine();
                                                    
                                                    if (mPilih.equals("1")) {
                                                         pengajuanDao.updateStatus(idProses, "DITERIMA");
                                                         System.out.println("Status pengajuan berhasil diubah menjadi: DITERIMA");
                                                    } else if (mPilih.equals("2")) {
                                                         pengajuanDao.updateStatus(idProses, "DITOLAK");
                                                         System.out.println("Status pengajuan berhasil diubah menjadi: DITOLAK");
                                                    } else {
                                                         System.out.println("Batal diproses (Input tidak valid)");
                                                    }
                                                }
                                            }
                                        } catch (NumberFormatException ex) {
                                            System.out.println("Mohon masukkan angka ID yang benar!");
                                        }
                                    }
                                    break;
                                    
                                case "2":
                                    System.out.println("\nVERIFIKASI & ISI CATATAN LOGBOOK MAHASISWA");
                                    List<LogbookBimbingan> mhsLogbooks = logbookDao.findByDosenId(dsn.getDosenId());
                                    boolean hasPending = false;
                                    
                                    System.out.println("Daftar Logbook Mahasiswa (Menunggu Verifikasi):");
                                    for (LogbookBimbingan lb : mhsLogbooks) {
                                        if ("DIAJUKAN".equals(lb.getStatus())) {
                                            hasPending = true;
                                            System.out.println("ID Logbook: [" + lb.getId() + "] | Mhs: " + lb.getNamaMahasiswa() + " | Tgl: " + lb.getTanggal());
                                            System.out.println("  Kegiatan: " + lb.getKegiatan());
                                            System.out.println("---------------------------------------------------------");
                                        }
                                    }
                                    
                                    if (!hasPending) {
                                         System.out.println("Tidak ada logbook baru yang menunggu verifikasi dari Anda.");
                                    } else {
                                         System.out.print("\nKetik ID Logbook yang ingin disetujui & diberi catatan (Atau 0 untuk batal): ");
                                         try {
                                             int idLogbook = Integer.parseInt(scanner.nextLine());
                                             if (idLogbook != 0) {
                                                 System.out.print("Tulis catatan untuk mahasiswa: ");
                                                 String catat = scanner.nextLine();
                                                 
                                                 logbookDao.updateCatatanDanStatus(idLogbook, catat, "DISETUJUI");
                                                 System.out.println("Catatan berhasil disimpan dan status logbook menjadi DISETUJUI.");
                                             }
                                         } catch (NumberFormatException e) {
                                             System.out.println("Mohon masukkan angka ID yang benar!");
                                         }
                                    }
                                    break;
                                    
                                case "3":
                                    System.out.println("\n MALIHAT RIWAYAT KESELURUHAN LOGBOOK MAHASISWA");
                                    List<LogbookBimbingan> allLogbooks = logbookDao.findByDosenId(dsn.getDosenId());
                                    
                                    if (allLogbooks.isEmpty()) {
                                         System.out.println(" - Belum ada mahasiswa bimbingan yang menyetorkan logbook.");
                                    } else {
                                         System.out.println("Riwayat Seluruh Catatan Logbook:");
                                         for (LogbookBimbingan lb : allLogbooks) {
                                             System.out.println("ID Logbook: [" + lb.getId() + "] | Mhs: " + lb.getNamaMahasiswa() + " | Tgl: " + lb.getTanggal());
                                             System.out.println("  Kegiatan: " + lb.getKegiatan());
                                             System.out.println("  Status: " + lb.getStatus() + " | Catatan Anda: " + (lb.getCatatanDosen() != null ? lb.getCatatanDosen() : "(Belum Diverifikasi)"));
                                             System.out.println("---------------------------------------------------------");
                                         }
                                    }
                                    break;
                                    
                                case "4":
                                    isDsnLoggedIn = false;
                                    System.out.println("Berhasil Logout.\n");
                                    break;
                                    
                                default:
                                    System.out.println("Pilihan tidak valid!");
                            }
                        }
                    }
                } else {
                    System.out.println("LOGIN GAGAL! Username atau Password Salah.\n");
                }
            } else {
                System.out.println("Pilihan tidak valid, silakan ketik 1 atau 0.\n");
            }
        }
        
        scanner.close();
    }
}
