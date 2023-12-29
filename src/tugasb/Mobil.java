package tugasb;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Mobil {
        
    private final SimpleStringProperty namaMobil;
    private final SimpleDoubleProperty hargaHari;
    private final SimpleStringProperty status;

    public Mobil(String namaMobil, double hargaHari,String status) {
        this.namaMobil = new SimpleStringProperty(namaMobil);
        this.hargaHari = new SimpleDoubleProperty(hargaHari);
        this.status = new SimpleStringProperty(status);
    }

    public void setNamaMobil(String namaMobil) {
        this.namaMobil.set(namaMobil);
    }
    
    public String getNamaMobil() {
        return namaMobil.get();
    }

    public void setHargaHari(double hargaHari) {
        this.hargaHari.set(hargaHari);
    }
    
    public double getHargaHari() {
        return hargaHari.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
        

    public String getStatus() {
        return status.get();
    }
}
