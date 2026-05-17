package dk.sdu.imada.oop26.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BackgroundMusic {

    private static final String MUSIC_PATH = "/images/Pacman.wav"; // Path to the background music file
    private Clip audioClip;

    public void playBackgroundMusic() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream( // Load the music file from the resources
                                                                            // folder
                    getClass().getResource(MUSIC_PATH)); // Load the music file from the resources folder
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
            audioClip.start();
        } catch (Exception e) {
            System.err.println("Kunne ikke afspille musik: " + e.getMessage());
        }
    }

    public void stopMusic() {
        if (audioClip != null) {
            audioClip.stop();
        }
    }
}
