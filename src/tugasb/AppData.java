package tugasb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppData {
    private static final ObservableList<Mobil> mobilList = FXCollections.observableArrayList();

    public static ObservableList<Mobil> getMobilList() {
        return mobilList;
    }
    
    private static final ObservableList<Transaksi> transaksiList = FXCollections.observableArrayList();

    public static ObservableList<Transaksi> getTransaksiList() {
        return transaksiList;
    }
}
