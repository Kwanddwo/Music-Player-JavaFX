package com.example;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

public class Song {
    private AudioMetadata metadata;
    private File audioFile;

    public Song(File audioFile) {
        this.audioFile = audioFile;
        try {
            this.metadata = new AudioMetadata(audioFile);
        } catch (CannotReadException e) {
            throw new RuntimeException(e);
        } catch (TagException e) {
            throw new RuntimeException(e);
        } catch (InvalidAudioFrameException e) {
            throw new RuntimeException(e);
        } catch (ReadOnlyFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AudioMetadata getMetadata() {
        return metadata;
    }

    public File getAudioFile() {
        return audioFile;
    }
}
