package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Song;


public class Main extends Application {
    Stage window;
    FXMLLoader loader;
    Controller controller;
    Parent root;

    @FXML
    private ListView<Song> songListView;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        root = loader.load();
        controller = loader.getController();
        window.setTitle("Song Recommender");
        window.setScene(new Scene(root, 800, 500));
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

