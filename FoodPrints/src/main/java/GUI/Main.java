package GUI;

import com.inhostudios.visionapitester.Camera.Camera;
import com.inhostudios.visionapitester.ImageInterpreter;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.omg.CORBA.Any;


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

        loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        root = loader.load();
        controller = loader.getController();
        window.setTitle("FoodPrint: Zero Food Waste Recipe");
        window.setScene(new Scene(root, 800, 500));
        window.show();

        // when use close the window, the database is updated with new records
        window.setOnCloseRequest(e -> {
            e.consume();
            System.out.println("Window Closed");
            controller.exitProcedure();
            window.close();
        });


        // TODO: How i may be using the camera obkect
//        ImageInterpreter interpreter = new ImageInterpreter(getDir()+"screenshot.jpg");
//        Camera cam = new Camera();
//        cam.start();
//
//        arrayString = cam.getIneterpreter.getOutput(0);

    }

    public static void main(String[] args) {
        launch(args);
    }
}

