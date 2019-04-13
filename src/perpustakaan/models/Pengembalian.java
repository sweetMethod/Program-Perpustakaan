/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perpustakaan.models;

import java.util.Date;
import org.sql2o.Connection;
import perpustakaan.DB;

/**
 *
 * @author asus
 */
public class Pengembalian {
    private final int id_peminjaman;
    private final Date tgl_pengembalian;

    public Pengembalian(int id_peminjaman, Date tgl_pengembalian) {
        this.id_peminjaman = id_peminjaman;
        this.tgl_pengembalian = tgl_pengembalian;
    }
    
    public static Pengembalian pengembalian(Peminjaman peminjaman) {
        try(Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM pengembalian WHERE id_peminjaman = :id_peminjaman";
            return connection.createQuery(query).bind(peminjaman).executeAndFetchFirst(Pengembalian.class);
        }
    }
    
    public int insertPengembalian() {
        try (Connection connection = DB.sql2o.open()) {
            final String query = "INSERT INTO pengembalian VALUES (:id_peminjaman, :tgl_pengembalian)";
            connection.createQuery(query).bind(this).executeUpdate();
            return connection.getResult();
        }
    }

    public int getId_peminjaman() {
        return id_peminjaman;
    }

    public Date getTgl_pengembalian() {
        return tgl_pengembalian;
    }

    @Override
    public String toString() {
        return "Pengembalian{" + "id_peminjaman=" + id_peminjaman + ", tgl_pengembalian=" + tgl_pengembalian + '}';
    }
    
}
