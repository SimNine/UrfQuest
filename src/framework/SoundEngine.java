package framework;

import java.io.IOException;

import javax.sound.sampled.*;

public class SoundEngine {
	public static int soundVol;

	public static int musicVol;
	
	private static String soundPath = "/assets/sounds/";
    private static Clip clip;
	private static AudioInputStream mouseover, mouseclick;
	
	public static void initSounds() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
        try {
        	mouseover = AudioSystem.getAudioInputStream(UrfQuest.quest.getClass().getResourceAsStream(soundPath + "mouseover.wav"));
        } catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		
        try {
        	mouseclick = AudioSystem.getAudioInputStream(UrfQuest.quest.getClass().getResourceAsStream(soundPath + "mouseclick.wav"));
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
