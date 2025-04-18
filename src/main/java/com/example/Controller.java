package com.example;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.Slider;

import javafx.scene.image.ImageView;


import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private Label songName;
    @FXML
    private Label songNameSmall;
    @FXML
    private Label songArtist;
    @FXML
    private Label songArtistSmall;
    @FXML
    private ImageView songArtwork;
    @FXML
    private ImageView songArtworkSmall;
    @FXML
    private Button fileButton;
    @FXML
    private Button playButton;
    @FXML
    private ImageView playButtonIcon;
    @FXML
    private Button nextButton;
    @FXML
    private Button backButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider playBar;

    private Player player;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = new Player(volumeSlider, playBar, songName, songNameSmall, songArtist, songArtistSmall, songArtwork, songArtworkSmall, playButtonIcon);

    }

    @FXML
    public void fileButtonClicked(ActionEvent event) throws IOException {
        player.onFileButtonPressed(event);
    }

    @FXML
    public void playMedia(ActionEvent event) throws IOException {
        player.onPlayButtonPressed(playButtonIcon);
    }

    @FXML
    public void previousMedia(ActionEvent event) throws IOException {
        player.onPrevButtonPressed();
    }

    @FXML
    public void nextMedia(ActionEvent event) throws IOException {
        player.onNextButtonPressed();
    }
}