package perpustakaan.models;

import org.sql2o.Connection;
import perpustakaan.DB;

public class Denda {
    private int id_denda;
    private String tipe_denda;
    private int tarif_denda;
    
    public static Denda denda(int id_denda) {
        try (Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM denda WHERE id_denda = :id_denda";
            return connection.createQuery(query).addParameter("id_denda", id_denda).executeAndFetchFirst(Denda.class);
        }
    }
    
    public int getId_denda() {
        return id_denda;
    }

    public String getTipe_denda() {
        return tipe_denda;
    }

    public int getTarif_denda() {
        return tarif_denda;
    }
    
}
