package com.gihun456.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.gihun456.Gihun456;

/**
 * A GUI for Gihun456 using FXML.
 */
public class Main extends Application {

    private Gihun456 gihun = new Gihun456();

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setGihun(gihun);  // inject the Duke instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
