package perpustakaan.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.sql2o.Connection;
import perpustakaan.DB;

public class Buku extends RecursiveTreeObject<Buku>{
    private String nomor_buku;
    private String judul_buku;
    private String penerbit;
    private String pengarang;
    private int jml_buku;

    public Buku(String nomor_buku,String judul_buku, String penerbit, String pengarang, int jml_buku) {
        this.nomor_buku = nomor_buku;
        this.judul_buku = judul_buku;
        this.penerbit = penerbit;
        this.pengarang = pengarang;
        this.jml_buku = jml_buku;
    } 
    
    public static List<Buku> getBukuList() {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM buku";
            return connection.createQuery(query).executeAndFetch(Buku.class);
        }
    }
    
    public static Buku buku(Peminjaman peminjaman) {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM buku WHERE nomor_buku = :nomor_buku";
            return connection.createQuery(query).bind(peminjaman).executeAndFetchFirst(Buku.class);
        }
    }

    public int createBukuList() {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "INSERT INTO `buku`(`nomor_buku`, `judul_buku`, `penerbit`, `pengarang`, `jml_buku`) VALUES (:nomor_buku, :judul_buku, :penerbit, :pengarang, :jml_buku)";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult();
        }
    }
    
    public int updateBukuList() {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "UPDATE `buku` SET `judul_buku`=:judul_buku,`penerbit`=:penerbit,`pengarang`=:pengarang,`jml_buku`=:jml_buku WHERE `nomor_buku` = :nomor_buku";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult();
        }
    }
    
    public int deleteBukuList() {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "DELETE FROM `buku` WHERE nomor_buku = :nomor_buku";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult();
        }
    }
    
    public StringProperty nomor_bukuProperty() {
        return new SimpleStringProperty(nomor_buku);
    }
    
    public StringProperty judul_bukuProperty() {
        return new SimpleStringProperty(judul_buku);
    }
    
    public StringProperty penerbitProperty() {
        return new SimpleStringProperty(penerbit);
    }
    
    public StringProperty pengarangProperty() {
        return new SimpleStringProperty(pengarang);
    }
    
    public ObjectProperty<Integer> jml_bukuProperty() {
        return new SimpleObjectProperty(jml_buku);
    }
    
    public String getJudul_buku() {
        return judul_buku;
    }

    public String getNomor_buku() {
        return nomor_buku;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public String getPengarang() {
        return pengarang;
    }

    public int getJml_buku() {
        return jml_buku;
    }

    public void setNomor_buku(String nomor_buku) {
        this.nomor_buku = nomor_buku;
    }

    public void setJudul_buku(String judul_buku) {
        this.judul_buku = judul_buku;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public void setJml_buku(int jml_buku) {
        this.jml_buku = jml_buku;
    }
}
