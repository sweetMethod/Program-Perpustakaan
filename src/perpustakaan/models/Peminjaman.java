package perpustakaan.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Date;
import java.util.List;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.sql2o.Connection;
import perpustakaan.DB;
import static perpustakaan.models.Pengembalian.pengembalian;

public class Peminjaman extends RecursiveTreeObject<Peminjaman> {
    private int id_peminjaman;
    private int id_anggota;
    private String nomor_buku;
    private Date tgl_peminjaman;
    private Date expired;
    
    public Peminjaman(int anggotaid, String bukuid, Date tanggal, Date tglexpired){
        this.id_anggota = anggotaid;
        this.nomor_buku = bukuid;
        this.tgl_peminjaman = tanggal;
        this.expired = tglexpired;
    }
            
    public int insertpeminjaman(){
        try (Connection connection = DB.sql2o.open()){
            final String query ="INSERT INTO peminjaman (id_peminjaman, id_anggota, nomor_buku, tgl_peminjaman, expired) VALUES (:id_peminjaman, :id_anggota, :nomor_buku, :tgl_peminjaman, :expired)";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult();
        }
    }
    
    public static List<Peminjaman> peminjamanList(Anggota anggota) {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM peminjaman WHERE id_anggota = :id_anggota";
            return connection.createQuery(query).bind(anggota).executeAndFetch(Peminjaman.class);
        }
    }
    
    public static List<Peminjaman> peminjamanList() {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM peminjaman";
            return connection.createQuery(query).executeAndFetch(Peminjaman.class);
        }
    }
    
    public static List<Peminjaman> peminjamanList(java.time.LocalDate dari) {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM peminjaman WHERE `tgl_peminjaman` >= :dari";
            return connection.createQuery(query)
                    .addParameter("dari", dari)
                    .executeAndFetch(Peminjaman.class);
        }
    }
    
    public static List<Peminjaman> peminjamanNotReturnList(Anggota anggota) {
        List<Peminjaman> peminjamanList = peminjamanList(anggota);
        peminjamanList.removeIf(peminjaman -> pengembalian(peminjaman) != null);
        return peminjamanList;
    }
    
    public int keterlambatan() {
        return Days.daysBetween(new LocalDate(expired), new LocalDate(new Date())).getDays();
    }
    
    public int keterlambatan(Pengembalian pengembalian) {
       return Days.daysBetween(new LocalDate(expired), new LocalDate(pengembalian.getTgl_pengembalian())).getDays();
    }

    public int getId_peminjaman() {
        return id_peminjaman;
    }
    
    public int getId_anggota() {
        return id_anggota;
    }

    public String getNomor_buku() {
        return nomor_buku;
    }
    
    public Date getTgl_peminjaman() {
        return tgl_peminjaman;
    }
    
    public Date getExpired() {
        return expired;
    }
}


