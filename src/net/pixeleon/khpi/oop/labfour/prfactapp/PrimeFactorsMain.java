package net.pixeleon.khpi.oop.labfour.prfactapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PrimeFactorsMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(new FileInputStream("prfscheme.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Prime Factors");
            primaryStage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
