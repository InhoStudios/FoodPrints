package GUI;

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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Playlist dataBase = new Playlist("database");
    private Playlist currentQueue = new Playlist("currentQueue");
    private PlaylistManager pm = new PlaylistManager();
    private MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private boolean musicPlayerInitialized = false;

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem menuFileClose;

    @FXML
    private ListView<Song> songListView = new ListView<>();
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
        //initializing songList
        System.out.println("Loading DataBase...");
        dataBase.readFromFile("savedFiles/savedPlaylists/database.txt");
        songListView.getItems().addAll(dataBase.getListOfSongs());
        songListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //allow list to select multiple songs
        //initializing status bar
        status.setText("Loaded Playlist");


        System.out.println("The following is the current data base:");
        dataBase.print();
        System.out.println("\n");

        System.out.println("The following is the current music player queue:");
        musicPlayer.getPlaylist().print();
        System.out.println("\n");

        //allow searchTextField to update songListView result
        searchTextField.textProperty().addListener((v, oldValue, newValue)->{
            handleSearchOnSongListView(oldValue, newValue);
        });
    }

    public void menuFileCloseClick(){
        Stage window = (Stage) menuBar.getScene().getWindow();
        System.out.println("Window Closed");
        exitProcedure();
        window.close();
    }

    public void playButtonClick(){
        musicPlayer.setPlaylist(currentQueue);

        status.setText("Play Button Clicked.");

        if(musicPlayer.getPlaylist().getListOfSongs().isEmpty()){
            status.setText("Current Queue is empty. Please select check box and hit submit button!");
            return;
        }


        if (!musicPlayerInitialized){
            musicPlayer.initializeThreadAndPlay();
            musicPlayerInitialized = true;
        } else{
            musicPlayer.resume();
        }
    }

    public void pauseButtonClick(){
        status.setText("Pause Button Clicked.");
        musicPlayer.pause();
    }

    public void skipButtonClick(){
        status.setText("Skip Button Clicked.");
        musicPlayer.skip();
    }

    public void stopButtonClick(){
        status.setText("Stop Button Clicked");
        musicPlayer.stop();
        musicPlayer = MusicPlayer.refreshInstance();
        musicPlayer.setPlaylist(currentQueue);
        musicPlayerInitialized = false;
    }

    public void submitButtonClick(){
        status.setText("New Selections Made...");

        List<Song> selectedSongsFromSongListView = songListView.getSelectionModel().getSelectedItems();
        if (!selectedSongsFromSongListView.isEmpty()){
            //clearing out the current queue
            currentQueue = new Playlist("currentQueue");
            for (Song song: selectedSongsFromSongListView){
                currentQueue.addSong(song);
            }
            currentQueue.print();
            return;
        }

        handleOptions(favoriteBox, hateBox, recentlyPlayedBox, lostSongBox, neverPlayedBox, allSongsBox);
    }

    //add filters on database base on the selected choiceBox
    private void handleOptions(CheckBox favoriteBox, CheckBox hateBox, CheckBox recentlyPlayedBox, CheckBox lostSongBox, CheckBox neverPlayedBox, CheckBox allSongsBox){

        currentQueue = pm.filter(dataBase, favoriteBox.isSelected(), hateBox.isSelected(),
                recentlyPlayedBox.isSelected(), lostSongBox.isSelected(), neverPlayedBox.isSelected(), allSongsBox.isSelected());

        currentQueue.print();
    }

    //save the database before exiting
    //being called in Main class
    public void exitProcedure(){
        //todo comeback and tne turn this saving mode on
        try {
            System.out.println("Saving to database...");
            dataBase.writeToFile(dataBase.convertToGsonString());
        } catch (EmptyPlaylistException e){
            e.printStackTrace();
        }
    }

    private void handleSearchOnSongListView(String oldValue, String newValue){
        // If the number of characters in the text box is less than last time it must be because the user pressed delete
        if ( oldValue != null && (newValue.length() < oldValue.length()) ) {
            // Restore the lists original set of entries and start from the beginning
            ObservableList<Song> entries = FXCollections.observableArrayList();
            for (Song song: dataBase.getListOfSongs()){
                entries.add(song);
            }
            songListView.setItems(entries);
        }

        // Change to upper case so that case is not an issue
        newValue = newValue.toUpperCase();

        // Filter out the entries that don't contain the entered text
        ObservableList<Song> subentries = FXCollections.observableArrayList();
        for ( Song entry: songListView.getItems() ) {
            String entryText = entry.getSongName();
            if ( entryText.toUpperCase().contains(newValue) ) {
                subentries.add(entry);
            }
        }
        songListView.setItems(subentries);
    }
}