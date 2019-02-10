package com.inhostudios.visionapitester.GUI.Andy;

import com.inhostudios.visionapitester.Camera.Camera;
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

    private JEditorPane webpageDisplay;
    private Boolean imageLoaded;
    private JButton webpageButton;
    private JButton recipeButton;
    private JButton camButton;
    private JTextField searchTerms;
    private java.awt.List accessList;
    private Camera camera;
    private ListView<String> listView = new ListView<String>();
    @FXML
    private BorderPane listPane;

    @FXML
    private void camButton(ActionEvent event) {
        ObservableList<String> names = FXCollections.observableArrayList();
        names.addAll(ImageInterpreter.getOutputs(
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

    public void printSomething() {
        System.out.println("You called a method on the application");
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
