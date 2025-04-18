package com.example;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class AudioMetadata {
    private String artist;
    private String album;
    private String title;
    private String genre;
    private String year;
    private Image artwork;

    public AudioMetadata(File audioFile) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
            AudioFile file = AudioFileIO.read(audioFile);

            Tag tag = file.getTag();

            this.artist = tag.getFirst(FieldKey.ARTIST);
            this.album = tag.getFirst(FieldKey.ALBUM);
            this.title = tag.getFirst(FieldKey.TITLE);
            this.genre = tag.getFirst(FieldKey.GENRE);
            this.year = tag.getFirst(FieldKey.YEAR);

            artist = Objects.equals(artist, "") ? "Unknown" : artist;
            album = Objects.equals(album, "") ? "Unknown" : album;
            title = Objects.equals(title, "") ? "Unknown" : title;
            genre = Objects.equals(genre, "") ? "Unknown" : genre;
            year = Objects.equals(year, "") ? "Unknown" : year;
            // This is set to the default artwork if no artwork is found
            this.artwork = AudioArtworkUtil.getArtwork(audioFile);
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public Image getArtwork() {
        return artwork;
    }
}

