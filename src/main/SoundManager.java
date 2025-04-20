package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    Clip video;
    URL soundURL[] = new URL[30];

    public SoundManager() {
        soundURL[0] = getClass().getResource("/sound/backgroundSong.wav");
    }

    /**
     * Opens specific video
     * @param i index of audio we want to use
     */
    public void setFile(int i){
        try{
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL[i]);
            video = AudioSystem.getClip();
            video.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play(){
        video.start();
    }

    public void loop(){
        video.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopSound(){
        video.stop();
    }
}
