package guis;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import framework.Loader;
import framework.SoundEngine;
import framework.UrfQuest;

public class OverlayInit {
	
	public static Overlay newMainMenu() {
		Set<GUIObject> mainObjects = new HashSet<GUIObject>();
		mainObjects.add(new TextButton("Play", 30, -80, -60) {
			public void click() {
				UrfQuest.panel.currOverlay = null;
				if (!UrfQuest.time.isRunning()) {
					UrfQuest.time.start();
					UrfQuest.game.showGUI();
				}
			}
		});
		mainObjects.add(new TextButton("Character", 30, -80, -30) {
			public void click() {
				//currentOverlay = characterScreen;
			}
		});
		mainObjects.add(new TextButton("Options", 30, -80, 0) {
			public void click() {
				UrfQuest.panel.currOverlay = newOptionsOverlay();
				UrfQuest.panel.prevOverlay = newMainMenu();
			}
		});
		mainObjects.add(new TextButton("Quit", 30, -80, 30) {
			public void click() {
				System.exit(0);
			}
		});
		mainObjects.add(new TextBox("UrfQuest", 60, -160, -100));
		return new Overlay("main", new Color(128, 128, 128, 128), mainObjects);
	}

	public static Overlay newTitleScreen() {
		Set<GUIObject> titleObjects = new HashSet<GUIObject>();
		titleObjects.add(new ImageBox("src/resources/arcanists2.png", 1, -185, 80) {
			public void click() {
				UrfQuest.panel.currOverlay = newMainMenu();
			}
		});
		return new Overlay("title", Color.BLACK, titleObjects);
	}
	
	public static Overlay newOptionsOverlay() {	
		Set<GUIObject> optionsObjects = new HashSet<GUIObject>();
		optionsObjects.add(new TextBox("Sound:", 30, -160, -40));
		optionsObjects.add(new Slider(30, 0, -40) {
			public void click() {
				this.setSliderPosition();
				SoundEngine.soundVol = this.position;
			}
		});
		optionsObjects.add(new TextBox("Music:", 30, -160, 0));
		optionsObjects.add(new Slider(30, 0, 0) {
			public void click() {
				this.setSliderPosition();
				SoundEngine.musicVol = this.position;
			}
		});
		optionsObjects.add(new TextButton("Toggle Debug", 30, -80, 40) {
			public void click() {
				if (UrfQuest.debug) UrfQuest.debug = false;
				else UrfQuest.debug = true;
			}
		});
		optionsObjects.add(new TextButton("Back", 30, -80, 80) {
			public void click() {
				UrfQuest.panel.currOverlay = UrfQuest.panel.prevOverlay;
			}
		});
		return new Overlay("options", new Color(128, 128, 128, 128), optionsObjects);
	}
	
	public static Overlay newPauseMenu() {
		Set<GUIObject> mainObjects = new HashSet<GUIObject>();
		mainObjects.add(new TextButton("Resume", 30, -80, -60) {
			public void click() {
				UrfQuest.panel.currOverlay = null;
				if (!UrfQuest.time.isRunning()) {
					UrfQuest.time.start();
				}
				if (!UrfQuest.game.isGUIVisible()) {
					UrfQuest.game.showGUI();
				}
			}
		});
		mainObjects.add(new TextButton("Options", 30, -80, -30) {
			public void click() {
				UrfQuest.panel.currOverlay = newOptionsOverlay();
				UrfQuest.panel.prevOverlay = newPauseMenu();
			}
		});
		mainObjects.add(new TextButton("Save", 30, -80, 0) {
			public void click() {
				Loader.saveGame();
			}
		});
		mainObjects.add(new TextButton("Load", 30, -80, 30) {
			public void click() {
				Loader.loadGame();
			}
		});
		mainObjects.add(new TextButton("Main Menu", 30, -80, 60) {
			public void click() {
				UrfQuest.panel.currOverlay = newMainMenu();
			}
		});
		mainObjects.add(new TextButton("Quit Game", 30, -80, 90) {
			public void click() {
				System.exit(0);
			}
		});
		mainObjects.add(new TextBox("Paused", 60, -160, -100));
		return new Overlay("pause", new Color(128, 128, 128, 128), mainObjects);
	}
	
}