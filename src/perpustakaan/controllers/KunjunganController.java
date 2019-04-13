package perpustakaan.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.io.IOException;
import java.util.Date;
import javafx.scene.control.Alert;
import perpustakaan.models.Anggota;
import static perpustakaan.models.Anggota.anggota;
import perpustakaan.models.Kunjungan;

public class KunjunganController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXButton back;
	
    @FXML
    private JFXTextField text_idanggota;

    @FXML
    private JFXButton buttonProsesKunjungan;

    @FXML
    void back(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

     @FXML
    void Scankode(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Kunjungan ak=new Kunjungan(new Date(), Integer.valueOf(text_idanggota.getText()));
            Anggota anggota = anggota(ak);
            if (anggota != null) {
                if (ak.insertkunjungan() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Kunjungan");
                    alert.setContentText(anggota.getNama_anggota()+" berhasil melakukan kunjungan");
                    alert.show();
                    
                    text_idanggota.setText("");
                } 
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kunjungan");
                alert.setContentText("Anggota tidak ditemukan");
                alert.show();
            }
            
        }
        
    }
    
}
