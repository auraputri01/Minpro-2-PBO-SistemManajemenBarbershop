Sistem Manajemen Barbershop

Mini Project 1
Praktikum Pemrograman Berorientasi Objek (PBO)

Nama: AURA PUTRI ANANDITA SYARIF
NIM: 2509116094
Program Studi: Sistem Informasi

==============================================================================

Deskripsi Singkat Program
Sistem Manajemen Barbershop merupakan program berbasis Java yang dibuat untuk membantu mengelola proses pelayanan pada barbershop. Program ini mencakup pengelolaan data pelanggan dan barber, pemilihan layanan, antrean pelanggan, proses pelayanan, hingga pembayaran.

Pada data program disimpan sementara menggunakan ArrayList. Lalu Program juga menerapkan beberapa konsep dasar seperti class, object, constructor, encapsulation, getter, dan setter.

==============================================================================

Alur Program
Saat program dijalankan, pengguna akan masuk ke menu utama yang menyediakan beberapa pilihan, yaitu pengelolaan pelanggan, pengelolaan barber, daftar layanan, pelayanan pelanggan, pengecekan status pelanggan dan barber, serta ringkasan barbershop.

Pada bagian pelanggan dan barber terdapat proses CRUD (Create, Read, Update, Delete) untuk menambah, menampilkan, mengubah, dan menghapus data.

Proses pelayanan dimulai ketika pelanggan yang sudah terdaftar memilih barber dan layanan yang tersedia. Sistem kemudian memberikan ID pelayanan dan nomor antrean secara otomatis. Status awal pelayanan adalah Menunggu, kemudian berubah menjadi Diproses saat barber mulai melayani dan menjadi Selesai setelah proses pelayanan selesai.

Setelah pelayanan selesai, pelanggan dapat melakukan pembayaran dengan metode Tunai atau QRIS. Jika pembayaran berhasil dilakukan, status pembayaran akan berubah dari Belum Bayar menjadi Lunas.

==============================================================================

Penerapan Nilai Tambah

Ada encapsulation diterapkan dengan membuat atribut pada class menjadi private serta menggunakan getter dan setter untuk mengakses atau mengubah data.

Lalu ada juga validasi input digunakan untuk mencegah beberapa kesalahan, seperti ID kosong, ID yang sudah digunakan, data yang tidak ditemukan, dan pilihan menu yang tidak tersedia.

Status barber otomatis ditentukan berdasarkan jumlah pelanggan aktif. Barber dengan 0 pelanggan berstatus Tersedia, 1 sampai 4 pelanggan berstatus Melayani, sedangkan 5 pelanggan berstatus Penuh. Barber juga dapat diubah menjadi Tidak Tersedia apabila sedang tidak bekerja.

Setiap barber hanya dapat menerima maksimal 5 pelanggan aktif. Jika kapasitas barber sudah penuh, pelanggan harus memilih barber lainnya.

Program juga membuat ID pelayanan dan nomor antrean secara otomatis, sehingga pengguna tidak perlu memasukkannya secara manual.