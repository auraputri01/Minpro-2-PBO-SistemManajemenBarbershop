#**Sistem Manajemen Barbershop**
#**Mini Project 2 Praktikum Pemrograman Berorientasi Objek (PBO)**

Nama: Aura Putri Anandita Syarif NIM: 2509116094 Program Studi: Sistem Informasi (C)

============================================================================

##**Deskripsi Singkat Program**

Sistem Manajemen Barbershop adalah program berbasis Java yang dibuat untuk membantu mengelola operasional sebuah barbershop, mulai dari data pelanggan dan barber, pemilihan layanan, antrean pelanggan, proses pelayanan, hingga pembayaran.

Program ini merupakan pengembangan lanjutan dari Mini Project 1. Kalau sebelumnya semua logika masih bercampur dalam satu file, sekarang program sudah dirapikan menggunakan arsitektur MVC (Model-View-Controller), sehingga tampilan, logika bisnis, dan data masing-masing berdiri di tempatnya sendiri dan lebih mudah dikembangkan lebih lanjut.

Data pada program ini masih disimpan sementara menggunakan ArrayList selama program berjalan (belum tersambung ke database). Program juga menerapkan konsep-konsep dasar OOP seperti class, object, constructor, encapsulation, inheritance, getter/setter, dan polymorphism.

============================================================================

##**Alur Program**

Saat program dijalankan, pengguna akan masuk ke menu utama yang menyediakan beberapa pilihan: pengelolaan pelanggan, pengelolaan barber, daftar layanan, pelayanan pelanggan, pengecekan status pelanggan dan barber, serta ringkasan barbershop.

Pada bagian pelanggan dan barber terdapat proses CRUD (Create, Read, Update, Delete) untuk menambah, menampilkan, mengubah, dan menghapus data. Setiap input yang dimasukkan pengguna akan divalidasi terlebih dahulu. Kalau formatnya salah, program akan meminta pengguna mengisi ulang, bukan langsung berhenti.

Proses pelayanan dimulai ketika pelanggan yang sudah terdaftar memilih barber dan layanan yang tersedia. Sistem kemudian membuatkan ID pelayanan dan nomor antrean secara otomatis. Status awal pelayanan adalah Menunggu, kemudian berubah menjadi Diproses saat barber mulai melayani, dan menjadi Selesai setelah proses pelayanan selesai.

Setelah pelayanan selesai, pelanggan dapat melakukan pembayaran dengan metode Tunai atau QRIS. Jika pembayaran berhasil dilakukan, status pembayaran akan berubah dari Belum Bayar menjadi Lunas.

Program juga sudah diisi beberapa data contoh sejak awal dijalankan (layanan, pelanggan, barber, dan pelayanan dengan status berbeda-beda), sehingga fitur "Tampilkan" bisa langsung dicoba tanpa harus input satu per satu dari awal.

============================================================================

##**Contoh Dokumentasi Program**
- Kelola Pelanggan
Tambah Pelanggan
<img width="678" height="622" alt="image" src="https://github.com/user-attachments/assets/018c8752-c871-4960-9ef9-3eb557a77b35" />

Tampilkan Pelanggan
<img width="651" height="786" alt="image" src="https://github.com/user-attachments/assets/03d595d5-1a35-4dfd-a70e-c6341ec63337" />

Ubah Pelanggan
<img width="520" height="266" alt="image" src="https://github.com/user-attachments/assets/bad973e6-9348-4520-ad06-c9c612018290" />

Hapus Pelanggan
<img width="492" height="241" alt="image" src="https://github.com/user-attachments/assets/2aab6e40-e62d-4dc6-865d-f8fabd865b7f" />

- Kelola Barber
Tambah Barber
<img width="726" height="590" alt="image" src="https://github.com/user-attachments/assets/04f89b1c-a026-4ee0-a880-e81ecc4d9618" />

Tampilkan Barber
<img width="462" height="753" alt="image" src="https://github.com/user-attachments/assets/5fb6bbdd-7547-4646-a662-6598f2fe6508" />

Ubah Barber
<img width="497" height="231" alt="image" src="https://github.com/user-attachments/assets/acd02a7d-f495-43b9-8ec4-71c086866f2b" />

Hapus Barber
<img width="385" height="217" alt="image" src="https://github.com/user-attachments/assets/13168409-f89a-4bf1-a996-4cce17a97085" />

Ubah Status Kehadiran
<img width="481" height="240" alt="image" src="https://github.com/user-attachments/assets/fe0ce257-9c22-42e0-868a-bbdf1987a752" />

- Lihat Daftar Layanan
<img width="422" height="260" alt="image" src="https://github.com/user-attachments/assets/364ff716-a909-4d6d-bca4-6c7483829e44" />

- Pelayanan Pelanggan
Daftarkan Pelayanan
<img width="570" height="567" alt="image" src="https://github.com/user-attachments/assets/c86a6b38-eaab-4f94-bfb1-d3677076930d" />

Tampilkan Semua Pelayanan
<img width="718" height="767" alt="image" src="https://github.com/user-attachments/assets/3ab597c7-5ad2-4a44-bdd6-c7eafa839339" />

Mulai Pelayanan
<img width="370" height="192" alt="image" src="https://github.com/user-attachments/assets/0132b3a6-e97f-4742-a6d5-0b1edcd10071" />

Selesaikan Pelayanan
<img width="397" height="202" alt="image" src="https://github.com/user-attachments/assets/5a7633f0-28e3-4bcd-98e1-5c185571d3c8" />

Pembayaran
<img width="365" height="252" alt="image" src="https://github.com/user-attachments/assets/8d5c6d67-383f-45cb-a1d5-ecddcc64518f" />

Batalkan Pelayanan
<img width="392" height="196" alt="image" src="https://github.com/user-attachments/assets/6d98253d-e22a-4de0-afd1-bc81822e00ec" />

- Cek Status Pelanggan
<img width="586" height="517" alt="image" src="https://github.com/user-attachments/assets/615e1c3d-94cf-4c84-9fdc-a9dadf23bcd0" />

- Lihat Status Barber
<img width="565" height="633" alt="image" src="https://github.com/user-attachments/assets/06f15505-157d-4885-b039-09e3c6fd5d94" />

- Ringkasan Barber
<img width="638" height="536" alt="image" src="https://github.com/user-attachments/assets/fca1e89b-c87e-4d19-8ff8-b911eb1802de" />

============================================================================

##**Struktur Package (MVC)**
<img width="442" height="377" alt="image" src="https://github.com/user-attachments/assets/964ccdd1-d1af-4ec9-a912-b554de1af705" />

Struktur ini dipisah menjadi tiga bagian besar supaya masing-masing hanya mengurus satu tanggung jawab:

- Model hanya peduli soal apakah datanya valid dan masuk akal.
- View (Main.java) hanya peduli soal bagaimana menampilkan sesuatu dan membaca input dari pengguna. Inilah satu-satunya file yang memakai Scanner dan System.out.
- Controller (BarbershopController.java) hanya peduli soal aturan main: mencari data, menghitung total, dan mengubah status. Tidak ada satu baris pun System.out di sini, sehingga logikanya bisa diuji atau dipakai ulang tanpa terikat pada tampilan konsol.
- Util adalah kotak perkakas kecil yang boleh dipinjam siapa saja.

============================================================================

##**Penerapan Encapsulation dan Inheritance**

###***Encapsulation***
Encapsulation diterapkan dengan membuat atribut pada tiap class bersifat private, serta menggunakan getter dan setter untuk mengakses atau mengubah data. Bedanya dengan getter/setter biasa, setter di program ini juga memvalidasi nilai sebelum disimpan bukan sekadar menyimpan apa pun yang dimasukkan. Contohnya:
- Barber.setPengalaman() menolak nilai pengalaman yang negatif atau lebih dari 50 tahun.
- Pelanggan.setNoHp() menolak nomor HP yang formatnya tidak sesuai nomor Indonesia yang wajar.

Seluruh ArrayList data (pelanggan, barber, layanan, pelayanan) di BarbershopController juga bersifat private final, sehingga tidak ada bagian program lain yang bisa mengubah isinya secara langsung semua harus lewat method publik yang memang disediakan.

###***Inheritance***
Terdapat satu superclass abstract, Orang, yang diturunkan menjadi dua cabang, dan masing-masing cabang diturunkan sekali lagi:
- Orang menyimpan atribut yang dimiliki semua orang di sistem ini: ID dan nama. Method getPeran() dibuat abstract, sehingga setiap turunannya wajib mendefinisikan perannya sendiri.
- Pelanggan adalah pelanggan biasa, tidak mendapat diskon.
- PelangganMember meng-override method diskon menjadi 10%.
- Barber adalah barber standar dengan kapasitas 5 antrean dan tanpa biaya tambahan.
- BarberSenior meng-override kapasitasnya menjadi 7 antrean, tapi menambahkan biaya jasa Rp10.000.

Perbedaan perilaku antar-subclass ini benar-benar dipakai dalam perhitungan total pembayaran pelayanan dihitung dari harga layanan ditambah biaya tambahan si barber, dikurangi diskon si pelanggan. Hasilnya otomatis berbeda tergantung kombinasi barber dan pelanggan yang dipilih, tanpa program perlu mengecek satu per satu dengan percabangan if/else.

============================================================================

##**Penerapan Nilai Tambah**
###***Struktur MVC***
Sudah dijelaskan lengkap pada bagian Struktur Package (MVC) di atas.

###***Polymorphism***
Diterapkan dalam dua bentuk:

- Method overriding 
getPeran(), getPersenDiskon(), getKapasitas(), dan getBiayaTambahan() didefinisikan ulang di tiap subclass dengan hasil yang berbeda-beda (lihat Model/Pelanggan.java, Model/PelangganMember.java, Model/Barber.java, dan Model/BarberSenior.java).
-Method overloading
Di Controller/BarbershopController.java, method tambahPelanggan() dan tambahBarber() masing-masing punya dua versi: versi singkat yang otomatis membuat data reguler/biasa, dan versi lengkap dengan parameter boolean tambahan untuk memilih jenis member/senior.

============================================================================

##**Aturan Bisnis Tambahan**
Selain empat ketentuan wajib, program ini juga menambahkan beberapa aturan supaya datanya tetap konsisten dan masuk akal:

- Validasi input mencegah berbagai kesalahan, seperti ID kosong, ID yang sudah digunakan, data yang tidak ditemukan, dan pilihan menu yang tidak tersedia.
- Status barber ditentukan otomatis berdasarkan jumlah pelanggan aktif yang sedang ditanganinya. Barber dengan 0 pelanggan berstatus tersedia, 1 sampai beberapa pelanggan berstatus melayani, dan yang sudah mencapai batas kapasitasnya berstatus penuh. Barber juga dapat diubah menjadi tidak tersedia apabila sedang tidak bekerja.
- Batas kapasitas barber dijaga otomatis, barber biasa maksimal 5 pelanggan aktif, barber senior maksimal 7. Jika kapasitasnya sudah penuh, pelanggan diarahkan untuk memilih barber lain.
- ID pelayanan dan nomor antrean dibuat otomatis oleh sistem, jadi pengguna tidak perlu memasukkannya secara manual.










