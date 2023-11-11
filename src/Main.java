package com.example.jfk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stg;

    @Override
    public void start(Stage stage) throws Exception {
        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
        stage.setTitle("Amrita Integrated Management System");
        Image icon = new Image("../assets/Amrita-Logo.png");
        stage.getIcons().add(icon);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setScene(new Scene(root, 900, 610));
        stage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
