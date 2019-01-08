package framework;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class SoundEngine {
	private static String soundPath = "assets/sounds/";

    private static Clip clip;
	private static AudioInputStream mouseover, mouseclick;
	
	public static void initSounds() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
        try {
        	mouseover = AudioSystem.getAudioInputStream(new File(soundPath + "mouseover.wav"));
        } catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		
        try {
        	mouseclick = AudioSystem.getAudioInputStream(new File(soundPath + "mouseclick.wav"));
        } catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void mouseover() {
		if (!clip.isRunning()) {
			try {
				clip.close();
				clip.open(mouseover);
				clip.loop(0);
			} catch (LineUnavailableException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void mouseclick() {
		try {
			clip.close();
			clip.open(mouseclick);
			clip.loop(0);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}
}
