    package tugasb;

    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.File;
    import java.io.FileReader;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.net.URL;
    import java.util.ResourceBundle;
    import javafx.beans.property.SimpleStringProperty;
    import javafx.beans.property.StringProperty;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.util.StringConverter;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.Node;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.ComboBox;
    import javafx.scene.control.Label;
    import javafx.scene.control.TableColumn;
    import javafx.scene.control.TableView;
    import javafx.scene.control.TextField;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;


    public class MainAppController implements Initializable {
        @FXML
        private AnchorPane home_form;

        @FXML
        private AnchorPane mobil_form;

        @FXML
        private AnchorPane transaksi_form;

        @FXML
        private Button Home_btn;

        @FXML
        private Button Mobil_btn;

        @FXML
        private Button Transaksi_btn;

        @FXML
        private TextField addMobil;

        @FXML
        private TextField addHarga;

        @FXML
        private ComboBox<String> addStatus;

        @FXML
        private TextField updateMobil;

        @FXML
        private TextField updateHarga;

        @FXML
        private ComboBox<String> updateStatus;

        @FXML
        private TableView<Mobil> table;

        @FXML
        private TableColumn<Mobil, String> mobilCol;

        @FXML
        private TableColumn<Mobil, Double> hargaCol;

        @FXML
        private TableColumn<Mobil, String> statusCol;

        @FXML
        private ComboBox<Mobil> pilihMobil;

        @FXML
        private TextField namaPenyewa;

        @FXML
        private ComboBox<String> inputGender;

        @FXML
        private TextField inputHari;

        @FXML
        private Label totalHargaLabel;

        @FXML
        private TableView<Transaksi> transaksiTable;

        @FXML
        private TableColumn<Transaksi, String> mobilTransaksiCol;

        @FXML
        private TableColumn<Transaksi, String> namaCol;

        @FXML
        private TableColumn<Transaksi, Integer> jumlahHariCol;

        @FXML
        private TableColumn<Transaksi, String> genderCol;
        
        @FXML
        private TableColumn<Transaksi, Double> totalCol;

        @FXML
        private Label totalMobilLabel;

        @FXML
        private Label totalTransaksiLabel;
        
        // Method untuk menghitung Total Data Transaksi
        private void updateTotalLabelsMobil() {
            int totalMobil = AppData.getMobilList().size();

            totalMobilLabel.setText("" + totalMobil);
        }
    
        // Method untuk menghitung Total Data Transaksi
        private void updateTotalLabelsTransaksi() {
            int totalTransaksi = AppData.getTransaksiList().size();

            totalTransaksiLabel.setText("" + totalTransaksi);
        }

        @FXML
        public void goMobil(MouseEvent event){
            mobilColumn();
        }

        @FXML
        public void goTransact(MouseEvent event){
            transaksiColumn();
        }

        // Method untuk Switch Form
        @FXML
        public void switchForm(ActionEvent event) {
            if (event.getSource() == Home_btn) {
                home_form.setVisible(true);
                mobil_form.setVisible(false);
                transaksi_form.setVisible(false);

                Home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
                Mobil_btn.setStyle("-fx-background-color:transparent");
                Transaksi_btn.setStyle("-fx-background-color:transparent");
                updateTotalLabelsMobil();
                updateTotalLabelsTransaksi();

            } else if (event.getSource() == Mobil_btn) {
                home_form.setVisible(false);
                mobil_form.setVisible(true);
                transaksi_form.setVisible(false);

                Home_btn.setStyle("-fx-background-color:transparent");
                Mobil_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");
                Transaksi_btn.setStyle("-fx-background-color:transparent");

                mobilColumn();

            } else if (event.getSource() == Transaksi_btn) {
                home_form.setVisible(false);
                mobil_form.setVisible(false);
                transaksi_form.setVisible(true);

                Home_btn.setStyle("-fx-background-color:transparent");
                Mobil_btn.setStyle("-fx-background-color:transparent");
                Transaksi_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #686f86, #8e9296);");

                transaksiColumn();

            }
        }

        // Method untuk menambah Data Mobil
        public void tambahDataMobil(ActionEvent event) {
            try {
                String namaMobil = addMobil.getText();
                double hargaHari = Double.parseDouble(addHarga.getText());
                String status = addStatus.getValue();

                if (namaMobil.isEmpty() || addHarga.getText().isEmpty() || addStatus.getSelectionModel().getSelectedItem() == null) {
                    tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Incomplete Fields", "Please fill all blank fields");
                } else {
                    Mobil mobilBaru = new Mobil(namaMobil, hargaHari, status);
                    AppData.getMobilList().add(mobilBaru);

                    tampilkanAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Added!", null);

                    clearInputTambahMobil();
                    
                    updateTotalLabelsMobil();
                    updateTotalLabelsTransaksi();
                }
            } catch (NumberFormatException e) {
                tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Input", "Please enter a valid number for Harga");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Untuk Set Combo Box Status
        public void statusComboBox() {
                ObservableList<String> statusOptions = FXCollections.observableArrayList("Tersedia","Tidak Tersedia");
                addStatus.setItems(statusOptions);
                updateStatus.setItems(statusOptions);
        }

        // Untuk Clear Input Tambah Mobil
        public void clearInputTambahMobil() {
            addMobil.clear();
            addHarga.clear();
            addStatus.getSelectionModel().clearSelection();
        }

        // Untuk Memilih Baris dari Table Mobil
        public void tableCarSelect(MouseEvent event) {
            Mobil selectedMobil = table.getSelectionModel().getSelectedItem();
            if (selectedMobil != null) {
                // Populate the update fields with the selected data
                updateMobil.setText(selectedMobil.getNamaMobil());
                updateHarga.setText(String.valueOf(selectedMobil.getHargaHari()));
                updateStatus.setValue(String.valueOf(selectedMobil.getStatus()));
            }
        }

        
        // Method untuk Update Data Mobil
        public void updateDataMobil(ActionEvent event) {
            try {
                Mobil selectedMobil = table.getSelectionModel().getSelectedItem();

                if (selectedMobil == null) {
                    tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "No Selection", "Please select a row to update");
                    return;
                }
                String updatedNamaMobil = updateMobil.getText();
                double updatedHargaHari = Double.parseDouble(updateHarga.getText());
                String updatedStatus = updateStatus.getValue();

                if (updatedStatus == null) {
                    tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Input", "Please select a status");
                    return;
                }
                selectedMobil.setNamaMobil(updatedNamaMobil);
                selectedMobil.setHargaHari(updatedHargaHari);
                selectedMobil.setStatus(updatedStatus);

                tampilkanAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Updated!", null);

                clearInputUpdateMobil();
                
                updateTotalLabelsMobil();
                updateTotalLabelsTransaksi();
                
                refreshSelectedRow(selectedMobil);

            } catch (NumberFormatException e) {
                tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Input", "Please enter a valid number for Harga");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Untuk refresh baris table
        private void refreshSelectedRow(Mobil selectedMobil) {
            int selectedIndex = table.getItems().indexOf(selectedMobil);

            if (selectedIndex >= 0) {
                table.getItems().set(selectedIndex, selectedMobil);
            }
        }

        // Untuk Clear Input Update Mobil
        public void clearInputUpdateMobil() {
            updateMobil.clear();
            updateHarga.clear();
            updateStatus.getSelectionModel().clearSelection();
        }

        // Untuk menghapus Data Mobil dari table Mobil
        public void hapusMobil() {
            Mobil selectedMobil = table.getSelectionModel().getSelectedItem();
            if (selectedMobil != null) {
                AppData.getMobilList().remove(selectedMobil);
                updateTotalLabelsMobil();
                updateTotalLabelsTransaksi();
                tampilkanAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Deleted!", null);
            } else {
                tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Invalid", "Please choose data to delete");
            }
        }

        // Untuk Menampilkan dan Set Data Table Mobil
        private void mobilColumn() {
            mobilCol.setCellValueFactory(new PropertyValueFactory<>("namaMobil"));
            hargaCol.setCellValueFactory(new PropertyValueFactory<>("hargaHari"));
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

            table.setItems(AppData.getMobilList());
        }

        // Method untuk mengeksport data Mobil dari file teks dengan memilih file
        public void exportMobilTXT(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Data to Text File");

            // Set ekstensi file filter ke txt
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            // Untuk memilih file yang ingin di export
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (Mobil mobil : AppData.getMobilList()) {
                        String line = mobil.getNamaMobil() + "\t" + mobil.getHargaHari() + "\t" + mobil.getStatus();
                        writer.write(line);
                        writer.newLine();
                    }
                    tampilkanAlert(Alert.AlertType.INFORMATION, "Information Message", "Export Successful!", null);
                } catch (IOException e) {
                    tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Export Failed", "An error occurred while exporting data");
                    e.printStackTrace();
                }
            }
        }

        // Method untuk mengimport data Mobil dari file teks dengan memilih file
        public void importMobilTXT(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Text File");
            

            // Set ekstensi file filter ke txt
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            // Untuk memilih file yang ingin di export
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split("\t");

                        if (data.length == 3) {
                            String namaMobil = data[0];
                            double hargaHari = Double.parseDouble(data[1]);
                            String status = data[2];

                            Mobil mobilBaru = new Mobil(namaMobil, hargaHari, status);
                            AppData.getMobilList().add(mobilBaru);
                        } else {
                            System.out.println("Format data tidak sesuai. Skipped line: " + line);
                        }
                    }
                    tampilkanAlert(Alert.AlertType.INFORMATION, "Information Message", "Import Successful!", null);
                } catch (IOException | NumberFormatException e) {
                    tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Import Failed", "An error occurred while importing data");
                    e.printStackTrace();
                }
            }
        }

        
        // Untuk Menampilkan dan Set Data Table Transaksi
        private void transaksiColumn() {
            mobilTransaksiCol.setCellValueFactory(cellData -> {
                StringProperty namaMobilProperty = new SimpleStringProperty(cellData.getValue().getMobil().getNamaMobil());
                return namaMobilProperty;
            });
            namaCol.setCellValueFactory(new PropertyValueFactory<>("namaPenyewa"));
            jumlahHariCol.setCellValueFactory(new PropertyValueFactory<>("jumlahHari"));
            genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
            totalCol.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));

                transaksiTable.setItems(AppData.getTransaksiList());
        }

        // Method filter untuk menampilkan Mobil yang status nya Tersedia
        private void pilihMobilInput() {
            pilihMobil.setItems(AppData.getMobilList().filtered(mobil -> mobil.getStatus().equals("Tersedia")));
            pilihMobil.setConverter(new StringConverter<Mobil>() {
                @Override
                public String toString(Mobil object) {
                    return object != null ? object.getNamaMobil() : "";
                }

                @Override
                public Mobil fromString(String string) {
                    return null;
                }
            });
    }
        
        
        // Method untuk Menambah Transaksi
        @FXML
        public void hitungTotalHarga(ActionEvent event) {
            try {
                Mobil mobilInput = pilihMobil.getValue();
                String hariInput = inputHari.getText();
                String namaInput = namaPenyewa.getText();
                String genderInput = inputGender.getValue();
                int jumlahHari = Integer.parseInt(hariInput);

                if (mobilInput != null && jumlahHari > 0 && !namaInput.isEmpty() && genderInput != null) {
                    double hargaMobil = mobilInput.getHargaHari();
                    double totalHarga = hargaMobil * jumlahHari;

                    // Create a new Transaksi object
                    Transaksi transaksi = new Transaksi(mobilInput, jumlahHari, namaInput, genderInput, totalHarga);

                    transaksi.setTotalHarga(totalHarga);

                    transaksiTable.getItems().add(transaksi);

                    totalHargaLabel.setText(" " + totalHarga);

                    mobilInput.setStatus("Tidak Tersedia");

                    tampilkanAlert(Alert.AlertType.INFORMATION, "Information Message", "Data Transaksi Berhasil Ditambahkan!", "Total Transaksi : " + totalHarga);

                    table.refresh();

                    clearInputTransaksi();

                    pilihMobilInput();

                    updateTotalLabelsMobil();
                    updateTotalLabelsTransaksi();
                } else {
                    tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Input", "Please fill all the required fields");
                }
            } catch (NumberFormatException e) {
                tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Input", "Please enter a valid number for Hari");
            }
        }

        // Method untuk clear input Transaksi
        public void clearInputTransaksi() {
            pilihMobil.getSelectionModel().clearSelection();
            inputHari.clear();
            namaPenyewa.clear();
            inputGender.getSelectionModel().clearSelection();
            totalHargaLabel.setText("0");
        }
        
        // Method untuk mengexport data transaksi dari file teks dengan memilih file
        public void exportTransaksiTXT(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Data Transaksi ke Text File");

            // Set ekstensi file filter ke txt
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            // Untuk memilih file yang ingin di export
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (Transaksi transaksi : AppData.getTransaksiList()) {
                        String line = transaksi.getMobil().getNamaMobil() + "\t" +
                                      transaksi.getJumlahHari() + "\t" +
                                      transaksi.getNamaPenyewa() + "\t" +
                                      transaksi.getGender() + "\t" +
                                      transaksi.getTotalHarga();
                        writer.write(line);
                        writer.newLine();
                    }

                    tampilkanAlert(Alert.AlertType.INFORMATION, "Information Message", "Transaction Data Export Successful!", null);
                } catch (IOException e) {
                    tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Transaction Data Export Failed", "An error occurred while exporting transaction data");
                    e.printStackTrace();
                }
            }
        }

    // Method untuk mengimport data transaksi dari file teks dengan memilih file
    public void importTransaksiTXT(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Data Transaksi ke Text File");

        // Set ekstensi file filter ke txt
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Untuk memilih file yang ingin di import
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\t");

                    if (data.length == 5) {
                        String namaMobil = data[0];
                        int jumlahHari = Integer.parseInt(data[1]);
                        String namaPenyewa = data[2];
                        String gender = data[3];
                        double totalHarga = Double.parseDouble(data[4]);

                        Mobil mobil = AppData.getMobilList().stream()
                                .filter(m -> m.getNamaMobil().equals(namaMobil))
                                .findFirst()
                                .orElse(null);

                        if (mobil != null) {
                            Transaksi transaksiBaru = new Transaksi(mobil, jumlahHari, namaPenyewa, gender, totalHarga);
                            AppData.getTransaksiList().add(transaksiBaru);
                        } else {
                            System.out.println("Mobil dengan nama '" + namaMobil + "' tidak ditemukan. Skipped line: " + line);
                        }
                    } else {
                        System.out.println("Format data tidak sesuai. Skipped line: " + line);
                    }
                }
                tampilkanAlert(Alert.AlertType.INFORMATION, "Information Message", "Transaction Data Import Successful!", null);
            } catch (IOException | NumberFormatException e) {
                tampilkanAlert(Alert.AlertType.ERROR, "Error Message", "Transaction Data Import Failed", "An error occurred while importing transaction data");
                e.printStackTrace();
            }
        }
    }

    
        // Method Untuk Alert
        private void tampilkanAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);
            alert.showAndWait();
        }

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            statusComboBox();
            pilihMobilInput();

            ObservableList<String> genderOptions = FXCollections.observableArrayList("Laki-Laki", "Perempuan");
            inputGender.setItems(genderOptions);

            AppData.getMobilList().add(new Mobil("APV", 100000, "Tersedia"));
            AppData.getMobilList().add(new Mobil("MPV", 120000, "Tidak Tersedia"));

            AppData.getTransaksiList().add(new Transaksi(AppData.getMobilList().get(1),3,"John Doe","Laki-Laki",300000.0));
            
            mobilColumn();
            transaksiColumn();
            
            updateTotalLabelsMobil();
            updateTotalLabelsTransaksi();
        }    
    }
