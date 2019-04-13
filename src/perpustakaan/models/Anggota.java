package perpustakaan.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.sql2o.Connection;
import perpustakaan.DB;

public class Anggota extends RecursiveTreeObject<Anggota>{
    private int id_anggota;
    private String nama_anggota;
    private String kelas;

    public Anggota(String nama_anggota, String kelas) {
        this.nama_anggota = nama_anggota;
        this.kelas = kelas;
    }

    public Anggota(int id_anggota, String nama_anggota, String kelas) {
        this.id_anggota = id_anggota;
        this.nama_anggota = nama_anggota;
        this.kelas = kelas;
    }
    
    public static List<Anggota> getAnggotaList() {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM anggota";
            return connection.createQuery(query).executeAndFetch(Anggota.class);
        }
    }
    
    public static Anggota anggota(Kunjungan kunjungan) {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM anggota WHERE id_anggota = :id_anggota";
            return connection.createQuery(query).bind(kunjungan).executeAndFetchFirst(Anggota.class);
        }
    }
    
    public static Anggota anggota(Peminjaman peminjaman) {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM anggota WHERE id_anggota = :id_anggota";
            return connection.createQuery(query).bind(peminjaman).executeAndFetchFirst(Anggota.class);
        }
    }

    public static Anggota anggota(int id_anggota) {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM anggota WHERE id_anggota = :id_anggota";
            return connection.createQuery(query).addParameter("id_anggota", id_anggota).executeAndFetchFirst(Anggota.class);
        }
    }
    
    public int createAnggota(){
        try (Connection connection = DB.sql2o.open()) {
            final String query = "INSERT INTO anggota (nama_anggota,kelas) VALUE (:nama_anggota, :kelas)";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult();
        }
    }
    
    public int updateAnggota(){
        try (Connection connection = DB.sql2o.open()) {
            final String query = "UPDATE anggota SET `nama_anggota` = :nama_anggota, `kelas` = :kelas WHERE `id_anggota` = :id_anggota";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult();
        }
    }
    
    public int deleteAnggota(){
        try (Connection connection = DB.sql2o.open()) {
            final String query = "DELETE FROM anggota where id_anggota=:id_anggota";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult();
        }
    }
    
    public ObjectProperty<Integer> id_anggotaProperty() {
        return new SimpleObjectProperty(id_anggota);
    }
    
    public StringProperty nama_anggotaProperty() {
        return new SimpleStringProperty(nama_anggota);
    }
    
    public StringProperty kelasProperty() {
        return new SimpleStringProperty(kelas);
    }

    public int getId_anggota() {
        return id_anggota;
    }

    public void setId_anggota(int id_anggota) {
        this.id_anggota = id_anggota;
    }

    public String getNama_anggota() {
        return nama_anggota;
    }

    public void setNama_anggota(String nama_anggota) {
        this.nama_anggota = nama_anggota;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
    
}
