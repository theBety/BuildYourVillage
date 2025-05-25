package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    Clip video;
    URL[] soundURL = new URL[10];
    FloatControl fc;
    public int volumeScale = 5;//0-10
    float volume;


    public SoundManager() {
        soundURL[0] = getClass().getResource("/sound/backgroundSong.wav");
        soundURL[1] = getClass().getResource("/sound/selectInInventory.wav");
        soundURL[2] = getClass().getResource("/sound/cutTree.wav");
        soundURL[3] = getClass().getResource("/sound/pickupItem.wav");
        soundURL[4] = getClass().getResource("/sound/buyHouse.wav");
    }

    /**
     * Opens specific video
     *
     * @param i index of audio we want to use
     */
    public void setFile(int i) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL[i]);
            video = AudioSystem.getClip();
            video.open(audioIn);
            fc = (FloatControl) video.getControl(FloatControl.Type.MASTER_GAIN);
            volume();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        video.start();
    }

    public void loop() {
        video.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void volume() {
        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -40f;
                break;
            case 2:
                volume = -30f;
                break;
            case 3:
                volume = -25f;
                break;
            case 4:
                volume = -20f;
                break;
            case 5:
                volume = -15f;
                break;
            case 6:
                volume = -10f;
                break;
            case 7:
                volume = -5f;
                break;
            case 8:
                volume = 1f;
                break;
            case 9:
                volume = 3f;
                break;
            case 10:
                volume = 6f;
                break;
        }
        fc.setValue(volume);
    }
}
