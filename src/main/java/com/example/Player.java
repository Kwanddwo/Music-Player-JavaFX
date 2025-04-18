package com.example;

import javafx.event.ActionEvent;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Player {
    private Slider playBar;
    private Slider volumeSlider;
    private Label songName;
    private Label songNameSmall;
    private Label songArtist;
    private Label songArtistSmall;
    private ImageView songArtwork;
    private ImageView songArtworkSmall;
    private ImageView playButtonIcon;

    private final Timer timer = new Timer(true);
    private int count = 0;
    private Media currMedia;
    private ArrayList<Song> songs;
    private int songIndex = 0;

    public MediaPlayer mediaPlayer;
    private boolean playing;

    public Player(Slider volumeSlider, Slider playBar, Label songName, Label songNameSmall, Label songArtist, Label songArtistSmall, ImageView songArtwork, ImageView songArtworkSmall, ImageView playButtonIcon) {
        this.volumeSlider = volumeSlider;
        this.playBar = playBar;
        this.songName = songName;
        this.songNameSmall = songNameSmall;
        this.songArtist = songArtist;
        this.songArtistSmall = songArtistSmall;
        this.songArtwork = songArtwork;
        this.songArtworkSmall = songArtworkSmall;
        this.playButtonIcon = playButtonIcon;

        songs = new ArrayList<Song>();

        File directory = new File("src/main/resources/com/example/music");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                songs.add(new Song(file));
                System.out.println(file);
            }
        }

        playCurrSong();

        initializeVolumeSlider();
        initializePlayBar();
    }

    private void playCurrSong() {
        currMedia = new Media(songs.get(songIndex).getAudioFile().toURI().toString());
        mediaPlayer = new MediaPlayer(currMedia);
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        setCurrentSongMetadata();
        play();
    }

    private void updateSliderFromMouse(MouseEvent event) {
        double mouseX = event.getX();
        double width  = this.playBar.getWidth();
        double newVal = (mouseX / width) * (playBar.getMax() - playBar.getMin()) + playBar.getMin();
        this.playBar.setValue(newVal);
        mediaPlayer.seek(new Duration(this.currMedia.getDuration().toMillis() * newVal / 100));
    }

    private void setCurrentSongMetadata() {
        AudioMetadata metadata = songs.get(songIndex).getMetadata();
        songName.setText(metadata.getTitle());
        songNameSmall.setText(metadata.getTitle());
        songArtist.setText(metadata.getArtist());
        songArtistSmall.setText(metadata.getArtist());
        songArtwork.setImage(metadata.getArtwork());
        songArtworkSmall.setImage(metadata.getArtwork());
    }

    private void initializeVolumeSlider() {
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(volumeSlider.getValue() * 0.01));

    }

    private void initializePlayBar() {
        playBar.setOnMousePressed(this::updateSliderFromMouse);
        playBar.setOnMouseDragged(this::updateSliderFromMouse);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if (playing && !playBar.isValueChanging()) {
                    playBar.setValue(mediaPlayer.getCurrentTime().toSeconds() / currMedia.getDuration().toSeconds() * 100);
                }
            }
        };
        timer.schedule(task, 0, 100);
    }

    public void onPrevButtonPressed() {
        if (count == 0) {
            mediaPlayer.seek(Duration.ZERO);
            count = 1;

            timer.schedule(new TimerTask() {
                @Override public void run() {
                    count = 0;
                }
            }, 500);
        }
        else if (count == 1) {
            // second press within 250 ms
            count = 0;
            goToPreviousSong();
        }
    }

    private void goToPreviousSong() {
        songIndex = songIndex > 0 ? songIndex - 1 : songs.size() - 1 ;
        mediaPlayer.stop();
        playCurrSong();
    }

    public void onNextButtonPressed() {
        goToNextSong();
    }

    private void goToNextSong() {
        songIndex = (songIndex + 1) % songs.size();
        mediaPlayer.stop();
        playCurrSong();
    }

    public void onPlayButtonPressed(ImageView playButtonIcon) {
        if (playing) {
            pause();
        } else {
            play();
        }
    }

    public void pause() {
        mediaPlayer.pause();
        playButtonIcon.setImage(new Image(getClass().getResource("/com/example/icons/play.png").toExternalForm()));
        playing = false;
    }

    public void play() {
        mediaPlayer.play();
        playButtonIcon.setImage(new Image(getClass().getResource("/com/example/icons/pause.png").toExternalForm()));
        playing = true;
    }

    public void onFileButtonPressed(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Sound files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Audio Files", "*.mp3", "*.wav", "*.flac"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            songs.clear();
            for (File file : selectedFiles) {
                songs.add(new Song(file));
                System.out.println(file);
            }
        } else {
            System.out.println("No files selected.");
        }
    }
}
