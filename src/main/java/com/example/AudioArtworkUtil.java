package com.example;

import javafx.scene.image.Image;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

public abstract class AudioArtworkUtil {
    public static Image getArtwork(File audioFile) {
        try {
            // Read metadata from file
            AudioFile file = AudioFileIO.read(audioFile);
            Tag tag = file.getTag();

            // Get artwork
            List<Artwork> artworkList = tag.getArtworkList();
            if (!artworkList.isEmpty()) {
                Artwork artwork = artworkList.get(0);
                byte[] imageData = artwork.getBinaryData();

                // Convert byte array to JavaFX Image
                return new Image(new ByteArrayInputStream(imageData));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("No album cover found.");
        return new Image("file:defaultArtwork.png");
    }
}