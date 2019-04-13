package perpustakaan.models;

import org.sql2o.Connection;
import perpustakaan.DB;

public class Pustakawan {
    private int id_pustakawan;
    private String nama_pustakawan;
    private String username;
    private String password;
    
    public static Pustakawan pustakawan(String username) {
        try (Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM pustakawan WHERE username = :username";
            return connection.createQuery(query).addParameter("username", username).executeAndFetchFirst(Pustakawan.class);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
