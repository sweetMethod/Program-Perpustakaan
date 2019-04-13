package perpustakaan.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import static perpustakaan.Format.DateFormat;
import static perpustakaan.models.Anggota.anggota;
import static perpustakaan.models.Buku.buku;
import perpustakaan.models.Peminjaman;

public class PeminjamanController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXButton back;
	
    @FXML
    private JFXTextField text_idanggota;

    @FXML
    private JFXTextField text_idbuku;

    @FXML
    private JFXButton buttonProses;

    @FXML
    void back(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }
    
    @FXML
    void anggotaKeypressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            text_idbuku.requestFocus();
        }
    }

    @FXML
    void bukuKeypressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Calendar expired = Calendar.getInstance();
            expired.setTime(new Date());
            expired.add(Calendar.DATE, 7);
            
            Peminjaman ak = new Peminjaman(
                    Integer.valueOf(text_idanggota.getText()),
                    text_idbuku.getText(),
                    new Date(),
                    expired.getTime()
            );
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Peminjaman");
            alert.setHeaderText("Error");
            
            if (anggota(ak) != null) {
                if (buku(ak) != null) {
                    if (ak.insertpeminjaman() > 0) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Peminjaman");
                        alert.setHeaderText("Peminjaman berhasil");
                        alert.setContentText(String.format(
                                "Nama Anggota\t\t\t : %s\nJudul Buku\t\t\t\t : %s\nTerakhir Pengembalian\t\t : %s",
                                anggota(ak).getNama_anggota(),
                                buku(ak).getJudul_buku(),
                                DateFormat(ak.getExpired())
                        ));
                        alert.show();
                        text_idanggota.requestFocus();
                        text_idanggota.setText("");
                        text_idbuku.setText("");
                    }
                } else {
                    alert.setContentText("Nomor buku tidak ditemukan");
                    alert.show();
                }
            } else {
                alert.setContentText("Id anggota tidak ditemukan");
                alert.show();
            }
            
        }
    }
}