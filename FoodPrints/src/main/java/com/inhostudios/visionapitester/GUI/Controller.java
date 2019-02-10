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


    private RecipeQuery query;
    private Recipe selectedRecipe;
    private String url;
    private Camera cam;


    //this is where you run your initialization when the window first open
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCamera();


        // DUMMY LIST, Populating List of Recgonized Food
        ArrayList<String> dummyListFiller = new ArrayList<>();
        dummyListFiller.add("fish");
        dummyListFiller.add("tomato");
        dummyListFiller.add("pasta");
        dummyListFiller.add("coke");


        searchListView.getItems().addAll(dummyListFiller);
        searchListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //allow list to select multiple songs

        status.setText("Initialized Camera. Press take Photo to capture Vocabulary");


//        //allow searchTextField to update searchListView result
//        keyWordsSearchkeyWordsSearch.textProperty().addListener((v, oldValue, newValue) -> {
//            System.out.println(newValue);
//            handleSearchOnListView(oldValue, newValue);
//        });
//
//        dietRistrTextbox.textProperty().addListener((v, oldValue, newValue) -> {
//            System.out.println(newValue);
//        });
//
//        caloriesTextbox.textProperty().addListener((v, oldValue, newValue) -> {
//            System.out.println(newValue);
//        });
//
//        cookingTimeTextBox.textProperty().addListener((v, oldValue, newValue) -> {
//            System.out.println(newValue);
//        });
//
//        excludedItemTextBox.textProperty().addListener((v, oldValue, newValue) -> {
//            System.out.println(newValue);
//        });

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
        ArrayList<Recipe> availableRecipes = manager.getRecipeList();
        System.out.println("Here are the available recipes " + availableRecipes);

        // Clear the List
        ObservableList<Object> emptyList = FXCollections.observableArrayList();
        searchListView.setItems(emptyList);

        // Re-populate the list with recipes
        searchListView.getItems().addAll(availableRecipes);
        searchListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //allow list to select multiple songs


//        handleOptions(keyWordsBox, dietRistrBox, caloriesBox, cookingTimeBox, excludedItemBox);
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