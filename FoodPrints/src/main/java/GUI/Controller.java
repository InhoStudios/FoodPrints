package GUI;

import com.inhostudios.visionapitester.Camera.Camera;
import com.inhostudios.visionapitester.FoodPrints;
import com.inhostudios.visionapitester.ImageInterpreter;
import exceptions.EmptyPlaylistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.MusicPlayer.MusicPlayer;
import model.Playlist;
import model.PlaylistManager;
import model.Song;
import org.omg.CORBA.Any;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.inhostudios.visionapitester.FoodPrints.getDir;

public class Controller implements Initializable {

//    private Playlist dataBase = new Playlist("database");
//    private Playlist currentQueue = new Playlist("currentQueue");
//    private PlaylistManager pm = new PlaylistManager();
//    private MusicPlayer musicPlayer = MusicPlayer.getInstance();
//    private boolean musicPlayerInitialized = false;

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem menuFileClose;

    @FXML
    private ListView<Object> searchListView = new ListView<Object>();
    @FXML
    private TextField searchTextField;

    @FXML
    private CheckBox favoriteBox;
    @FXML
    private CheckBox hateBox;
    @FXML
    private CheckBox recentlyPlayedBox;
    @FXML
    private CheckBox lostSongBox;
    @FXML
    private CheckBox neverPlayedBox;
    @FXML
    private CheckBox allSongsBox;
    @FXML
    private Button submitButton;

    @FXML
    private Button button;
    @FXML
    private Button pauseButton;
    @FXML
    private Button skipButton;
    @FXML
    private Button stopButton;

    @FXML
    private Label status;


    //this is where you run your initialization when the window first open
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initializing Camera

        ImageInterpreter interpreter = new ImageInterpreter(FoodPrints.getDir()+"screenshot.jpg");
        Camera cam = new Camera();
        cam.start();

        ArrayList<String> guessedNames = cam.getOutput();


        searchListView.getItems().addAll(guessedNames);
        searchListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //allow list to select multiple songs
        //initializing status bar
        status.setText("Starting the Camera");


        //allow searchTextField to update searchListView result
        searchTextField.textProperty().addListener((v, oldValue, newValue) -> {
            handleSearchOnListView(oldValue, newValue);
        });
    }

    public void menuFileCloseClick() {
        Stage window = (Stage) menuBar.getScene().getWindow();
        System.out.println("Window Closed");
        exitProcedure();
        window.close();
    }

    public void playButtonClick() {
        musicPlayer.setPlaylist(currentQueue);

        status.setText("Play Button Clicked.");

        if (musicPlayer.getPlaylist().getListOfSongs().isEmpty()) {
            status.setText("Current Queue is empty. Please select check box and hit submit button!");
            return;
        }


        if (!musicPlayerInitialized) {
            musicPlayer.initializeThreadAndPlay();
            musicPlayerInitialized = true;
        } else {
            musicPlayer.resume();
        }
    }

    public void pauseButtonClick() {
        status.setText("Pause Button Clicked.");
        musicPlayer.pause();
    }

    public void skipButtonClick() {
        status.setText("Skip Button Clicked.");

    }

    public void stopButtonClick() {
        status.setText("Stop Button Clicked");

    }

    public void submitButtonClick() {
        status.setText("New Selections Made...");

        List<Any> selectedItemFromSearchListView = searchListView.getSelectionModel().getSelectedItems();
        if (!selectedItemFromSearchListView.isEmpty()) {
            //clearing out the current queue

            for (Any any : selectedItemFromSearchListView) {
                // do something to the list item
            }

            return;
        }

        handleOptions(favoriteBox, hateBox, recentlyPlayedBox, lostSongBox, neverPlayedBox, allSongsBox);
    }

    //add filters on database base on the selected choiceBox
    private void handleOptions(CheckBox favoriteBox, CheckBox hateBox, CheckBox recentlyPlayedBox, CheckBox lostSongBox, CheckBox neverPlayedBox, CheckBox allSongsBox) {

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