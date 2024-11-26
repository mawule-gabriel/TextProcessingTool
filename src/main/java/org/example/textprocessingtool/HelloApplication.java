package org.example.textprocessingtool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the Regex.fxml file with the RegexController
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Regex.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);


        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Text Processing Tool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
