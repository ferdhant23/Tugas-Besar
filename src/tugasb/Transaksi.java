package tugasb;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaksi {
    private Mobil mobil;
    private final SimpleIntegerProperty jumlahHari;
    private final SimpleStringProperty namaPenyewa;
    private final SimpleStringProperty gender;
    private final SimpleDoubleProperty totalHarga;

    public Transaksi(Mobil mobil, int jumlahHari, String namaPenyewa, String gender, Double totalHarga) {
        this.mobil = mobil;
        this.jumlahHari = new SimpleIntegerProperty(jumlahHari);
        this.namaPenyewa = new SimpleStringProperty(namaPenyewa);
        this.gender = new SimpleStringProperty(gender);
        this.totalHarga = new SimpleDoubleProperty(totalHarga);
    }

    public Mobil getMobil() {
        return mobil;
    }

    public void setMobil(Mobil mobil) {
        this.mobil = mobil;
    }

    public int getJumlahHari() {
        return jumlahHari.get();
    }

    public void setJumlahHari(int jumlahHari) {
        this.jumlahHari.set(jumlahHari);
    }

    public String getNamaPenyewa() {
        return namaPenyewa.get();
    }

    public void setNamaPenyewa(String namaPenyewa) {
        this.namaPenyewa.set(namaPenyewa);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public Double getTotalHarga() {
        return totalHarga.get();
    }

    public void setTotalHarga(Double totalHarga) {
        this.totalHarga.set(totalHarga);
    }
    
    
    
}
