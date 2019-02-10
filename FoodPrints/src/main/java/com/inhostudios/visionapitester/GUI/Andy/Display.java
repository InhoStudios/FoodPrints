package com.inhostudios.visionapitester.GUI.Andy;

import com.inhostudios.visionapitester.Camera.Camera;
import com.inhostudios.visionapitester.DataExtractionModel.Recipe;
import com.inhostudios.visionapitester.ImageInterpreter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CountDownLatch;

public class Display extends Application {

    public static final CountDownLatch latch = new CountDownLatch(1);
    public static Display displayTest = null;


    private JPanel panel1;
    private WebEngine engine;

    private JEditorPane webpageDisplay;
    private Boolean imageLoaded;
    private JButton webpageButton;
    private JButton recipeButton;
    private JButton camButton;
    private JTextField searchTerms;
    private java.awt.List accessList;
    private Camera camera;
    private Recipe selectedRecipe;
    private String url;
    @FXML
    private WebView webView;
    @FXML
    private ListView<String> listView = new ListView<String>();
    private ObservableList<String> names = FXCollections.observableArrayList();
    @FXML
    private BorderPane listPane;



    @FXML
    private void camButton(ActionEvent event) {
        listView.getItems().addAll(ImageInterpreter.getOutputs(
                System.getProperty("user.dir")+ "/src/main/resources/screenshot.jpg"));
    }

    public static Display waitForStartUp(){
        try{
            latch.wait();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return displayTest;
    }

    public static void setDisplayTest(Display startUpDisplay) {
        displayTest = startUpDisplay;
        latch.countDown();
    }

    public Display() {
        setDisplayTest(this);
    }

    @FXML
    public void select() {//TODO: fix this pls
        selectedRecipe = (Recipe) listView.getSelectionModel().getSelectedItem();

        if (selectedRecipe != null) {
            url = selectedRecipe.getUrlToRecipe();
            System.out.println("You have selected this Recipe: " + selectedRecipe.toString());
            return;
        }
        System.out.println("ERROR You have not chosen a Recipe");
    }

    public void initCamera() {
        new Thread() {
            @Override
            public void run() {
                camera = new Camera();
                camera.start();
                System.out.println(camera.getOutputs().toString());


                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }.start();
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Display.fxml"));
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(loader.load(), 500, 500);
        accessList.setMultipleMode(true);
        stage.setScene(scene);
        initCamera();


        accessList.setMultipleMode(true);
        accessList.addComponentListener(new ComponentAdapter() {
        });
        accessList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int indices[] = accessList.getSelectedIndexes();
                String srcterms = "";
                for(int index : indices){
                    srcterms = srcterms + ", " + accessList.getItem(index);
                }
                updateSelection(srcterms);

            }
            public void updateSelection(String str){
                searchTerms.setText(str);
            }
        });

//        webView = new WebView();
//        engine = webView.getEngine();
//        engine.load("https://www.google.com/");
//        if (url == null) {
//            url = "https://www.google.com";
//        }
//        System.out.println(selectedRecipe.getUrlToRecipe());
//        WebEngine webEngine = webView.getEngine();
//        webEngine.load(url);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        Scene scene = new Scene(loader.load(), 500, 500);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }


//    public Display(String title, int width, int height) {
//        JFrame frame = new JFrame(title);
//        frame.setSize(width, height);
//        frame.setContentPane(this.panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);
//        frame.pack();
//
//        // multiple select for list
//        accessList.setMultipleMode(true);
//
//        // create a camera
//        camera = new Camera();
//        camera.start();
//
//        webpageButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try{
//                    webpageDisplay.setContentType("text/html");
//                    webpageDisplay.setPage("http://inhostudios.tech");
//                } catch(IOException exp){
//                    webpageDisplay.setContentType("text/html");
//                    webpageDisplay.setText("<html>Could not load</html>");
//                    System.out.println("Could not load");
//                }
//            }
//        });
//        camButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cameraList();
//            }
//
//            public void cameraList(){
//                accessList.removeAll();
//                ArrayList<String> potentials = camera.getOutputs();
//                for(String hit : potentials){
//                    accessList.add(hit);
//                }
//            }
//        });
//        recipeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//        accessList.addComponentListener(new ComponentAdapter() {
//        });
//        accessList.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int indices[] = accessList.getSelectedIndexes();
//                String srcterms = "";
//                for(int index : indices){
//                    srcterms = srcterms + ", " + accessList.getItem(index);
//                }
//                updateSelection(srcterms);
//            }
//
//            public void updateSelection(String str){
//                searchTerms.setText(str);
//            }
//        });
//    }

}
