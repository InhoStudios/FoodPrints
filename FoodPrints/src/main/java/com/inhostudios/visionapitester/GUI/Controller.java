package com.inhostudios.visionapitester.GUI;

import com.inhostudios.visionapitester.Camera.Camera;
import com.inhostudios.visionapitester.DataExtraction;
import com.inhostudios.visionapitester.DataExtractionModel.Recipe;
import com.inhostudios.visionapitester.DataExtractionModel.RecipeManager;
import com.inhostudios.visionapitester.DataExtractionModel.RecipeQuery;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private TextField keyWordsSearchkeyWordsSearch;
    @FXML
    private TextField dietRistrTextbox;
    @FXML
    private TextField caloriesTextbox;
    @FXML
    private TextField cookingTimeTextBox;
    @FXML
    private TextField excludedItemTextBox;
    @FXML
    private WebView webView;
    @FXML
    private ListView<Object> searchListView = new ListView<Object>();
    @FXML
    private Label status;

    @FXML
    private CheckBox keyWordsBox;
    @FXML
    private CheckBox dietRistrBox;
    @FXML
    private CheckBox caloriesBox;
    @FXML
    private CheckBox cookingTimeBox;
    @FXML
    private CheckBox excludedItemBox;


    private RecipeQuery query = new RecipeQuery("");
    private Recipe selectedRecipe;
    private String url;
    private Camera cam;


    //this is where you run your initialization when the window first open
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCamera();
        status.setText("Initialized Camera. Press take Photo to capture Vocabulary");


        // DUMMY LIST, Populating List of Recgonized Food
        ArrayList<String> dummyListFiller = new ArrayList<>();
        dummyListFiller.add("fish");
        dummyListFiller.add("tomato");
        dummyListFiller.add("pasta");
        dummyListFiller.add("thymes");


        searchListView.getItems().addAll(dummyListFiller);
        searchListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //allow list to select multiple songs



        keyWordsSearchkeyWordsSearch.textProperty().addListener((v, oldValue, newValue) -> {
            if (keyWordsBox.isSelected()){
                query.setQ(newValue);
            }
        });

        dietRistrTextbox.textProperty().addListener((v, oldValue, newValue) -> {
            if (dietRistrBox.isSelected()){
                query.setDiet(newValue);
            } else {
                query.setDiet("");
            }

        });

        caloriesTextbox.textProperty().addListener((v, oldValue, newValue) -> {
            if (caloriesBox.isSelected()){
                String[] result = newValue.split(", ");

                query.setCalories(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
            } else {
                query.setCalories(0, 1000000);
            }
        });

        cookingTimeTextBox.textProperty().addListener((v, oldValue, newValue) -> {
            if (cookingTimeBox.isSelected()){
                if (!newValue.contains(",")){
                    int result = Integer.parseInt(newValue);
                    query.setTime(result);
                } else {
                    String[] temp = newValue.split(", ");
                    int result1 = Integer.parseInt(temp[0]);
                    int result2 = Integer.parseInt(temp[1]);
                    query.setTime(result1, result2);
                }
            } else {
                query.setTime(0, 10000000);
            }

        });

        excludedItemTextBox.textProperty().addListener((v, oldValue, newValue) -> {
            if (excludedItemBox.isSelected()){
                query.setExcluded(newValue);
            } else {
                query.setExcluded("");
            }
        });

    }

    public void initCamera() {
        new Thread() {
            @Override
            public void run() {
                cam = new Camera();
                cam.start();
                System.out.println(cam.getOutputs().toString());


                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }.start();
    }

    public void takePhotoBtnClicked(){
        ArrayList<String> results = new ArrayList<>();
        results.addAll(cam.getOutputs());

        if (!results.isEmpty()){
            searchListView.getItems().addAll(results);
        }
    }

    public void SubmitRequestBtnClicked() {
        status.setText("Submitting Query Right Now");

        String cameraKeyWords = "";
        List<Object> selectedItemFromSearchListView = searchListView.getSelectionModel().getSelectedItems();
        if (!selectedItemFromSearchListView.isEmpty()) {
            for (Object obj : selectedItemFromSearchListView) {
                if (obj instanceof String) {
                    cameraKeyWords += ("," + obj);
                }
            }
            query.setQ(cameraKeyWords);
        }


        DataExtraction extracter = new DataExtraction();
        List<Object> recipeJsons = extracter.getEdamamRecipes(query.toURL());

        RecipeManager manager = new RecipeManager(recipeJsons);
        ArrayList<Recipe> availableRecipes = manager.getRecipeList();
        System.out.println("Here are the available recipes " + availableRecipes);

        // Clear the List
        ObservableList<Object> emptyList = FXCollections.observableArrayList();
        searchListView.setItems(emptyList);

        // Re-populate the list with recipes
        searchListView.getItems().addAll(availableRecipes);
        searchListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //allow list to select multiple songs
    }

    public void selectRecipeBtnClicked() {
        selectedRecipe = (Recipe) searchListView.getSelectionModel().getSelectedItem();

        if (selectedRecipe != null) {
            url = selectedRecipe.getUrlToRecipe();
            System.out.println("You have selected this Recipe: " + selectedRecipe.toString());
            return;
        }
        System.out.println("ERROR You have not chosen a Recipe");

    }

    public void openWebpage() {
        if (url == null) {
            url = "https://www.google.com";
        }
        System.out.println(selectedRecipe.getUrlToRecipe());
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);
    }


    public void menuFileCloseClick() {
        Stage window = (Stage) menuBar.getScene().getWindow();
        System.out.println("Window Closed");
        exitProcedure();
        window.close();
    }


    //save the database before exiting
    //being called in Main class
    public void exitProcedure() {
        // TODO, do something before closing the program.
    }
}