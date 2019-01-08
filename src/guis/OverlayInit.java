package guis;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import framework.Loader;
import framework.UrfQuest;
import guis.menus.ImageButton;
import guis.menus.Slider;
import guis.menus.TextBox;
import guis.menus.TextButton;

public class OverlayInit {
	
	public static Overlay newMainMenu() {
		Set<GUIObject> mainObjects = new HashSet<GUIObject>();
		mainObjects.add(new TextButton("Play", 30, -80, -60, GUIObject.CENTER) {
			public void click() {
				UrfQuest.panel.unpause();
			}
		});
		mainObjects.add(new TextButton("Character", 30, -80, -30, GUIObject.CENTER) {
			public void click() {
				//currentOverlay = characterScreen;
			}
		});
		mainObjects.add(new TextButton("Options", 30, -80, 0, GUIObject.CENTER) {
			public void click() {
				UrfQuest.panel.swap(newOptionsOverlay());
			}
		});
		mainObjects.add(new TextButton("Quit", 30, -80, 30, GUIObject.CENTER) {
			public void click() {
				System.exit(0);
			}
		});
		mainObjects.add(new TextBox("UrfQuest", 60, -160, -120, GUIObject.CENTER));
		return new Overlay("main", new Color(128, 128, 128, 128), mainObjects);
	}

	public static Overlay newTitleScreen() {
		Set<GUIObject> titleObjects = new HashSet<GUIObject>();
		titleObjects.add(new ImageButton("src/resources/arcanists2.png", 1, -185, 80) {
			public void click() {
				UrfQuest.panel.swap(newMainMenu());
			}
		});
		return new Overlay("title", Color.BLACK, titleObjects);
	}
	
	public static Overlay newOptionsOverlay() {	
		Set<GUIObject> optionsObjects = new HashSet<GUIObject>();
		optionsObjects.add(new TextBox("Sound:", 30, -160, -40, GUIObject.CENTER));
		optionsObjects.add(new Slider(30, 0, -40, GUIObject.CENTER) {
			public void click() {
				this.setSliderPosition();
				//SoundEngine.soundVol = this.sliderPos;
			}
		});
		optionsObjects.add(new TextBox("Music:", 30, -160, 0, GUIObject.CENTER));
		optionsObjects.add(new Slider(30, 0, 0, GUIObject.CENTER) {
			public void click() {
				this.setSliderPosition();
				//SoundEngine.musicVol = this.sliderPos;
			}
		});
		optionsObjects.add(new TextButton("Toggle Debug", 30, -80, 40, GUIObject.CENTER) {
			public void click() {
				if (UrfQuest.debug) UrfQuest.debug = false;
				else UrfQuest.debug = true;
			}
		});
		optionsObjects.add(new TextButton("Back", 30, -80, 80, GUIObject.CENTER) {
			public void click() {
				UrfQuest.panel.swap(newMainMenu());
			}
		});
		return new Overlay("options", new Color(128, 128, 128, 128), optionsObjects);
	}
	
	public static Overlay newPauseMenu() {
		Set<GUIObject> pauseObjects = new HashSet<GUIObject>();
		pauseObjects.add(new TextButton("Resume", 30, -80, -60, GUIObject.CENTER) {
			public void click() {
				UrfQuest.panel.unpause();
			}
		});
		pauseObjects.add(new TextButton("Options", 30, -80, -30, GUIObject.CENTER) {
			public void click() {
				UrfQuest.panel.swap(newOptionsOverlay());
			}
		});
		pauseObjects.add(new TextButton("Save", 30, -80, 0, GUIObject.CENTER) {
			public void click() {
				Loader.saveGame();
			}
		});
		pauseObjects.add(new TextButton("Load", 30, -80, 30, GUIObject.CENTER) {
			public void click() {
				Loader.loadGame();
			}
		});
		pauseObjects.add(new TextButton("Main Menu", 30, -80, 60, GUIObject.CENTER) {
			public void click() {
				UrfQuest.panel.swap(newMainMenu());
			}
		});
		pauseObjects.add(new TextButton("Quit Game", 30, -80, 90, GUIObject.CENTER) {
			public void click() {
				System.exit(0);
			}
		});
		pauseObjects.add(new TextBox("Paused", 60, -160, -120, GUIObject.CENTER));
		return new Overlay("pause", new Color(128, 128, 128, 128), pauseObjects);
	}
	
	public static Overlay newGrayLayer() {
		return new Overlay("graylayer", new Color(128, 128, 128, 128));
	}
	
	public static Overlay newGUIOverlay() {
		Set<GUIObject> guiObjects = new HashSet<GUIObject>();
		return new Overlay("gui", null, guiObjects);
	}
}