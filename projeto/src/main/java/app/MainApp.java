package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        URL fxmlUrl = getClass().getResource("login-view.fxml");
        
        if (fxmlUrl == null) {
            System.err.println("Não foi possível encontrar o FXML de login.");
            return;
        }

        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Mini Biblioteca - Acesso");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}