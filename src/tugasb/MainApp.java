package tugasb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {   
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
        
        Scene scene = new Scene(root);

        stage.setTitle("Aplikasi Penyewaan Mobil");

        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/asset/car-rental.png")));
        
        stage.setResizable(false);

        stage.setMaximized(false);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
