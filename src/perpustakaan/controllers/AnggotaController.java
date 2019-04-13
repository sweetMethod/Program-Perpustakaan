package perpustakaan.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import perpustakaan.models.Anggota;
import static perpustakaan.models.Anggota.getAnggotaList;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import perpustakaan.MemberCard;

public class AnggotaController implements Initializable{

    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXButton back;
    
    @FXML
    private JFXButton buttonBatal;

    @FXML
    private JFXButton buttonPrint;
    
    @FXML
    void back(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }
    
    @FXML
    private JFXTreeTableView<Anggota> table_anggota;

    @FXML
    private JFXTextField text_Namasiswa;

    @FXML
    private JFXTextField text_Kelas;

    @FXML
    private JFXButton buttonSimpan;

    @FXML
    private JFXButton buttonEdit;

    @FXML
    private JFXButton buttonHapus;

    private final ObservableList<Anggota> anggotaList = FXCollections.observableArrayList();
    
    @FXML
    void editAnggota(ActionEvent event) {
        if(validasi()){
            int index = table_anggota.getSelectionModel().getSelectedIndex();
            Anggota angg = anggotaList.get(index);
            angg.setNama_anggota(text_Namasiswa.getText());
            angg.setKelas(text_Kelas.getText());
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Edit Data Anggota");
            alert.setHeaderText(null);
            alert.setContentText("Apakah anda yakin untuk mengedit data anggota?");
            Optional result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            if(angg.updateAnggota()>0){
            table_anggota.refresh();
            resetForm();
            }
            else {
            alert.close();
            }     
        }
    }
            else {
            Alert alert2 = new Alert(AlertType.ERROR);
            alert2.setTitle("Anggota");
            alert2.setContentText("Nama Siswa atau Kelas kosong");
            alert2.show();
        }
    }
     
    @FXML
    void hapusAnggota(ActionEvent event) {
        int index = table_anggota.getSelectionModel().getSelectedIndex();
        Anggota angg = anggotaList.get(index);
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Anggota");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data anggota?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(angg.deleteAnggota()>0){
                Alert keluar = new Alert(AlertType.INFORMATION);
                keluar.setTitle("Data Anggota");
                keluar.setHeaderText(null);
                anggotaList.remove(angg);
                keluar.setContentText("data berhasil di hapus");
                keluar.showAndWait();
                resetForm();
            } else {
                alert.close();
            }
        }
    }

    private boolean validasi(){
        return !text_Namasiswa.getText().isEmpty() && !text_Kelas.getText().isEmpty();
    }

    @FXML
    void simpanAnggota(ActionEvent event) {
        if(validasi()){
            Anggota angg = new Anggota(
                text_Namasiswa.getText(),
                text_Kelas.getText()
                );
            if(angg.createAnggota()>0){
                anggotaList.setAll(getAnggotaList());
                resetForm();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Anggota");
            alert.setContentText("Nama Siswa atau Kelas kosong");
            alert.show();
        }
    }
    
    private void ambilData() {
        int index = table_anggota.getSelectionModel().getSelectedIndex();
        Anggota angg = anggotaList.get(index);
        text_Namasiswa.setText(angg.getNama_anggota());
        text_Kelas.setText(angg.getKelas());
    }
    
    
    private void resetForm() {
        text_Namasiswa.setText("");
        text_Kelas.setText("");
    }
    
    @FXML
    void batalAnggota(ActionEvent event) {
        resetForm();
        buttonSimpan.setDisable(false);
        buttonEdit.setDisable(true);
        buttonHapus.setDisable(true);
    }
    
    @FXML
    @SuppressWarnings("CallToPrintStackTrace")
    void printAnggota(ActionEvent event) {
        ObservableList<Integer> indexs = table_anggota.getSelectionModel().getSelectedIndices();
        List<Anggota> list = new ArrayList();
        
        indexs.stream().forEach((index) -> {
            list.add(anggotaList.get(index));
        });
        
        Task <MemberCard> labeltask = new Task <MemberCard>() {
            @Override
            protected MemberCard call() throws Exception {
                return new MemberCard(list);
            }
        };
        
        labeltask.setOnSucceeded(evt->{
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("out/membercard.pdf"));
            } catch (FileNotFoundException | DocumentException ex) {
                Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            document.setMargins(12f, 12f, 12f, 12f);
            document.open();
                
            try {
                document.add(labeltask.get().getLayout());
            } catch (InterruptedException | ExecutionException | DocumentException ex) {
                Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            document.close();
            
            File file = new File ("out/membercard.pdf");
            
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException ex) {
                Logger.getLogger(AnggotaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        labeltask.setOnFailed(evt -> labeltask.getException().printStackTrace());
        Thread thread = new Thread(labeltask);
        thread.setDaemon(true);
        thread.start();      
    }
    
    
    @FXML
    void mousePressedHandle(MouseEvent event) {
        if (event.getClickCount() == 1) {
        ambilData();
        buttonSimpan.setDisable(true);
        buttonEdit.setDisable(false);
        buttonHapus.setDisable(false);
        buttonPrint.setDisable(false);
    }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        TreeTableColumn<Anggota, Integer> idCol = new TreeTableColumn<>("Id Anggota");
        TreeTableColumn<Anggota, String> namaCol = new TreeTableColumn<>("Nama Anggota");
        TreeTableColumn<Anggota, String> kelasCol = new TreeTableColumn<>("Kelas Anggota");
        
        idCol.setCellValueFactory(param -> param.getValue().getValue().id_anggotaProperty());
        namaCol.setCellValueFactory(param -> param.getValue().getValue().nama_anggotaProperty());
        kelasCol.setCellValueFactory(param -> param.getValue().getValue().kelasProperty());
        
        idCol.prefWidthProperty().bind(table_anggota.prefWidthProperty().multiply(0.2));
        namaCol.prefWidthProperty().bind(table_anggota.prefWidthProperty().multiply(0.5));
        kelasCol.prefWidthProperty().bind(table_anggota.prefWidthProperty().multiply(0.3));
        
        table_anggota.getColumns().add(idCol);
        table_anggota.getColumns().add(namaCol);
        table_anggota.getColumns().add(kelasCol);
        
        TreeItem<Anggota> anggotaRoot = new RecursiveTreeItem<>(anggotaList, RecursiveTreeObject::getChildren);
        
        table_anggota.setRoot(anggotaRoot);
        table_anggota.setShowRoot(false);
        
        anggotaList.addAll(getAnggotaList());
        
        table_anggota.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    }
    

}