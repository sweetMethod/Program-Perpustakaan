package perpustakaan.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import perpustakaan.QRCodeLabel;
import static perpustakaan.models.Buku.getBukuList;
import perpustakaan.models.Buku;

public class BukuController implements Initializable {
    @FXML
    private AnchorPane anchor;
    @FXML
    private JFXTreeTableView<Buku> tableView;
    @FXML
    private JFXButton simpanButton;
    @FXML
    private JFXTextField judulField;
    @FXML
    private JFXButton batalButton;
    @FXML
    private JFXTextField penerbitField;
    @FXML
    private JFXTextField nomorField;
    @FXML
    private JFXTextField pengarangField;
    @FXML
    private JFXTextField jmlField;
    @FXML
    private JFXButton editButton;
    @FXML
    private JFXButton hapusButton;
    @FXML
    private JFXButton buttonBatal;
    
    private final ObservableList<Buku> bukuList = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Membuat column
        TreeTableColumn<Buku, String> nomorCol = new TreeTableColumn<>("No.");
        TreeTableColumn<Buku, String> judulCol = new TreeTableColumn<>("Judul");
        TreeTableColumn<Buku, String> penerbitCol = new TreeTableColumn<>("Penerbit");
        TreeTableColumn<Buku, String> pengarangCol = new TreeTableColumn<>("Pengarang");
        TreeTableColumn<Buku, Integer> jmlCol = new TreeTableColumn<>("Jumlah");
        //Setting data column
        nomorCol.setCellValueFactory(param -> param.getValue().getValue().nomor_bukuProperty());
        judulCol.setCellValueFactory(param -> param.getValue().getValue().judul_bukuProperty());
        penerbitCol.setCellValueFactory(param -> param.getValue().getValue().penerbitProperty());
        pengarangCol.setCellValueFactory(param -> param.getValue().getValue().pengarangProperty());
        jmlCol.setCellValueFactory(param -> param.getValue().getValue().jml_bukuProperty());
        
        //tambah colum ke table
        tableView.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        tableView.getColumns().add(nomorCol);
        tableView.getColumns().add(judulCol);
        tableView.getColumns().add(penerbitCol);
        tableView.getColumns().add(pengarangCol);
        tableView.getColumns().add(jmlCol);
        
        
        //buat root table
        TreeItem<Buku> bukuRoot = new RecursiveTreeItem<>(bukuList, RecursiveTreeObject::getChildren);
        
        //set root ke table
        tableView.setRoot(bukuRoot);
        tableView.setShowRoot(false);
        
        bukuList.addAll(getBukuList());
    }    
    
    private void tambahData() {
        Buku buku = new Buku(
                nomorField.getText(),
                judulField.getText(),
                penerbitField.getText(),
                pengarangField.getText(),
                Integer.valueOf(jmlField.getText()));
        if (buku.createBukuList()>0){
            bukuList.add(buku);
            resetForm();
        }
    }
    private void deleteData() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        Buku buku = bukuList.get(index);
        if (buku.deleteBukuList()>0){
            bukuList.remove(buku);
            resetForm();
        }
    }

    private void updateData() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        Buku buku = bukuList.get(index);
        buku.setNomor_buku(nomorField.getText());
        buku.setJudul_buku(judulField.getText());
        buku.setPenerbit(penerbitField.getText());
        buku.setPengarang(pengarangField.getText());
        buku.setJml_buku(Integer.valueOf(jmlField.getText()));
        if (buku.updateBukuList()>0){
            tableView.refresh();
            resetForm();
        }
    }
    
    private void ambilData() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        
        Buku buku = bukuList.get(index);
        nomorField.setText(String.valueOf(buku.getNomor_buku()));
        judulField.setText(buku.getJudul_buku());
        pengarangField.setText(buku.getPengarang());
        penerbitField.setText(buku.getPenerbit());
        jmlField.setText(String.valueOf(buku.getJml_buku()));
    }
    
     private boolean validasi(){
        return !nomorField.getText().isEmpty() && !judulField.getText().isEmpty() && !pengarangField.getText().isEmpty() && !penerbitField.getText().isEmpty() && !jmlField.getText().isEmpty();
    }
    
     private void konfirHapus () {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Hapus Data Buku");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk menghapus data buku?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Alert keluar = new Alert(AlertType.INFORMATION);
            keluar.setTitle("Data Buku");
            keluar.setHeaderText(null);
            keluar.setContentText("data berhasil di hapus");
            keluar.showAndWait();
            deleteData();
            hapusButton.setDisable(true);
            editButton.setDisable(true);
            simpanButton.setDisable(false);
        } else {
            alert.close();
        }
    }
     
    private void konfirEdit () {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Edit Data Buku");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin untuk mengedit data buku?");
        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Alert keluar = new Alert(AlertType.INFORMATION);
            keluar.setTitle("Data Buku");
            keluar.setHeaderText(null);
            keluar.setContentText("Data berhasil di edit");
            keluar.showAndWait();
            updateData();
            hapusButton.setDisable(true);
            editButton.setDisable(true);
            simpanButton.setDisable(false);
        } else {
            alert.close();
        }
    }
    
    private void resetForm() {
        nomorField.setPromptText("Masukan Nomor Buku");
        nomorField.setText("");
        judulField.setText("");
        penerbitField.setText("");
        pengarangField.setText("");
        jmlField.setText("");
        nomorField.setDisable(false);
    }

    @FXML
    private void simpanBuku(javafx.event.ActionEvent event) {
        if(validasi()){
            tambahData();
            resetForm();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Buku");
            alert.setContentText("Data Harus Diisi ");
            alert.show();
        }
    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent parent = loader.load();
        anchor.getChildren().add(parent);
    }

    @FXML
    void editBuku(ActionEvent event) {
        konfirEdit();
    }

    @FXML
    void hapusBuku(ActionEvent event) {
        konfirHapus();
    }
    
    @FXML
    void printButton(ActionEvent event) {
        ObservableList<Integer> indexs = tableView.getSelectionModel().getSelectedIndices();
        List <Buku> list = new ArrayList();
        
        indexs.stream().forEach((index) -> {
            list.add(bukuList.get(index));
        });
        Task <QRCodeLabel> labeltask = new Task <QRCodeLabel>() {
            @Override
            protected QRCodeLabel call() throws Exception {
                return new QRCodeLabel (list);
            }
        
        };
        
        labeltask.setOnSucceeded(evt->{
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("out/label.pdf"));
            } catch (FileNotFoundException | DocumentException ex) {
                Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.setMargins(14f, 14f, 17f, 324f);
            document.open();
            try {
                document.add(labeltask.get().getLayout());
            } catch (InterruptedException | ExecutionException | DocumentException ex) {
                Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.close();
            
            File file = new File ("out/label.pdf");
            
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException ex) {
                Logger.getLogger(BukuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        labeltask.setOnFailed(evt -> labeltask.getException().printStackTrace());
        Thread thread = new Thread(labeltask);
        thread.setDaemon(true);
        thread.start();
        
    }
    
    @FXML
    void batalButtton(ActionEvent event) {
        resetForm();
        hapusButton.setDisable(true);
        editButton.setDisable(true);
        simpanButton.setDisable(false);
    }
    
    @FXML
    void mousePressedHandle(MouseEvent event) {
        if (event.getClickCount() == 1) {
            ambilData();   
            nomorField.setDisable(true);
            nomorField.setPromptText("Nomor Buku");
            simpanButton.setDisable(true);
            hapusButton.setDisable(false);
            editButton.setDisable(false);
        }
    } 
}