package com.inhostudios.visionapitester.GUI;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.omg.CORBA.Any;

import java.io.File;
import java.net.URL;


public class Main extends Application {
    Stage window;
    FXMLLoader loader;
    Controller controller;
    Parent root;

    @FXML
    private ListView<Any> searchListView; // could be either string or recipe

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

//      URL location = getClass().getResource("./PocketDoc/FoodPrints/src/main/java/com/inhostudios/visionapitester/GUI/MainFrame.fxml");
//      System.out.println(location);

        // PATH HAS TO BE REAL PATH, NOT RELATIVE PATH
        String path = "/Users/harrychuang/Developer/PocketDoc/FoodPrints/src/main/java/com/inhostudios/visionapitester/GUI/MainFrame.fxml";
        URL location = new File(path).toURI().toURL();

        loader = new FXMLLoader(location);
        root = loader.load();
        controller = loader.getController();
        window.setTitle("FoodPrint: Zero Food Waste Recipe");
        window.setScene(new Scene(root, 1440, 900));
        window.show();

        // when use close the window, the database is updated with new records
        window.setOnCloseRequest(e -> {
            e.consume();
            System.out.println("Window Closed");
            controller.exitProcedure();
            window.close();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}