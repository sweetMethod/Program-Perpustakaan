package perpustakaan.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomeController {

    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXButton butkun;

    @FXML
    private JFXButton butpem;

    @FXML
    private JFXButton butpeng;

    @FXML
    private JFXButton buttambah;

    @FXML
    private JFXButton butdata;

    @FXML
    private JFXButton butlap;

    @FXML
    private JFXButton butlogout;

    @FXML
    private JFXButton butinfo;

    @FXML
    void tekanData(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/databuku.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

    @FXML
    void tekanInfo(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

    @FXML
    void tekanKunjungan(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kunjungan.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

    @FXML
    void tekanLaporan(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/laporan.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

    @FXML
    void tekanLogout(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

    @FXML
    void tekanPeminjaman(ActionEvent event)  throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/peminjaman.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

    @FXML
    void tekanPengembalian(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pengembalian.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

    @FXML
    void tekanTambah(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/anggota.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

}
