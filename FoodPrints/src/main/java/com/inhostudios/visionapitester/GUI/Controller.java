package com.inhostudios.visionapitester.GUI;

import com.inhostudios.visionapitester.Camera.Camera;
import com.inhostudios.visionapitester.DataExtraction;
import com.inhostudios.visionapitester.DataExtractionModel.RecipeManager;
import com.inhostudios.visionapitester.DataExtractionModel.RecipeQuery;
import com.inhostudios.visionapitester.FoodPrints;
import com.inhostudios.visionapitester.ImageInterpreter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


import javax.sound.midi.Soundbank;
import javax.xml.soap.Text;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.inhostudios.visionapitester.FoodPrints.getDir;

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

    @FXML private CheckBox keyWordsBox;
    @FXML private CheckBox dietRistrBox;
    @FXML private CheckBox caloriesBox;
    @FXML private CheckBox cookingTimeBox;
    @FXML private CheckBox excludedItemBox;


    private RecipeQuery query;
    private String url;


    //this is where you run your initialization when the window first open
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // DUMMY LIST
        ArrayList<String> guessedNames = new ArrayList<>();
        guessedNames.add("fish");
        guessedNames.add("tomato");

        searchListView.getItems().addAll(guessedNames);
        searchListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //allow list to select multiple songs
        //initializing status bar
        status.setText("Initialized");


//        //allow searchTextField to update searchListView result
        keyWordsSearchkeyWordsSearch.textProperty().addListener((v, oldValue, newValue) -> {
            System.out.println(newValue);
            handleSearchOnListView(oldValue, newValue);
        });

        dietRistrTextbox.textProperty().addListener((v, oldValue, newValue) -> {
            System.out.println(newValue);
        });

        caloriesTextbox.textProperty().addListener((v, oldValue, newValue) -> {
            System.out.println(newValue);
        });

        cookingTimeTextBox.textProperty().addListener((v, oldValue, newValue) -> {
            System.out.println(newValue);
        });

        excludedItemTextBox.textProperty().addListener((v, oldValue, newValue) -> {
            System.out.println(newValue);
        });
    }

    // TODO: partially working
    public void initCamera() {
        new Thread() {
            @Override
            public void run() {
                Camera cam = new Camera();
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

    public void openWebpage() {
        if (url == null) {
            url = "https://www.google.com";
        }
        System.out.println(url);
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);
    }

    public void SubmitQueryBtnClicked() {
        status.setText("New Selections Made...");

        String cameraKeyWords = "";
        query = new RecipeQuery("");

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
        System.out.println(manager.getRecipeList());

        handleOptions(keyWordsBox, dietRistrBox, caloriesBox, cookingTimeBox, excludedItemBox);
    }


    public void selectRecipeBtnClicked() {
        url = "http://www.seriouseats.com/recipes/2011/04/fish-with-saffron-tomato-cous-cous-recipe.html";
    }

    public void menuFileCloseClick() {
        Stage window = (Stage) menuBar.getScene().getWindow();
        System.out.println("Window Closed");
        exitProcedure();
        window.close();
    }

    //add filters on database base on the selected choiceBox
    private void handleOptions(CheckBox keyWordsBox, CheckBox dietRistrBox, CheckBox caloriesBox, CheckBox cookingTimeBox, CheckBox excludedItemBox) {
//        recipeManager.filter(dataBase, favoriteBox.isSelected(), hateBox.isSelected(),
//                recentlyPlayedBox.isSelected(), lostSongBox.isSelected(), neverPlayedBox.isSelected(), allSongsBox.isSelected());
    }

    //save the database before exiting
    //being called in Main class
    public void exitProcedure() {
        // TODO, do something before closing the program.
    }

    private void handleSearchOnListView(String oldValue, String newValue) {
        // If the number of characters in the text box is less than last time it must be because the user pressed delete
        if (oldValue != null && (newValue.length() < oldValue.length())) {
            // Restore the lists original set of entries and start from the beginning
            ObservableList<Object> entries = FXCollections.observableArrayList();
//            for (Object obj : recipeManger.getListOfSongs()) {
//                entries.add(obj);
//            }
            searchListView.setItems(entries);
        }

        // Change to upper case so that case is not an issue
        newValue = newValue.toUpperCase();

        // Filter out the entries that don't contain the entered text
        ObservableList<Object> subentries = FXCollections.observableArrayList();
        for (Object entry : searchListView.getItems()) {
            String entryText = entry.toString();
            if (entryText.toUpperCase().contains(newValue)) {
                subentries.add(entry);
            }
        }
        searchListView.setItems(subentries);
    }
}