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
	
	public static GUIContainer newMainMenu() {
		GUIContainer mainMenu = new GUIContainer(GUIObject.TOP_LEFT, 
												 0, 
												 0, 
												 UrfQuest.panel.getWidth(), 
												 UrfQuest.panel.getHeight(), 
												 "main", 
												 null, 
												 new Color(128, 128, 128, 128), null, 0);
		Set<GUIObject> mainObjects = new HashSet<GUIObject>();
		
		mainObjects.add(new TextBox("UrfQuest", 60, -160, -120, GUIObject.CENTER, mainMenu));
		mainObjects.add(new TextButton("Play", 30, -80, -60, GUIObject.CENTER, mainMenu) {
			public boolean click() {
				UrfQuest.panel.unpause();
				return true;
			}
		});
		mainObjects.add(new TextButton("Character", 30, -80, -30, GUIObject.CENTER, mainMenu) {
			public boolean click() {
				//currentOverlay = characterScreen;
				return true;
			}
		});
		mainObjects.add(new TextButton("Options", 30, -80, 0, GUIObject.CENTER, mainMenu) {
			public boolean click() {
				UrfQuest.panel.swap(UrfQuest.panel.optionsMenu);
				return true;
			}
		});
		mainObjects.add(new TextButton("Quit", 30, -80, 30, GUIObject.CENTER, mainMenu) {
			public boolean click() {
				System.exit(0);
				return true;
			}
		});
		
		mainMenu.addAllObjects(mainObjects);
		return mainMenu;
	}

	public static GUIContainer newTitleScreen() {
		GUIContainer titleScreen = new GUIContainer(GUIObject.TOP_LEFT, 
													0, 
													0, 
													UrfQuest.panel.getWidth(), 
													UrfQuest.panel.getHeight(), 
													"title", 
													null, 
													Color.BLACK, null, 0);
		Set<GUIObject> titleObjects = new HashSet<GUIObject>();
		
		titleObjects.add(new ImageButton("src/resources/arcanists2.png", 1, -185, 80, titleScreen) {
			public boolean click() {
				UrfQuest.panel.swap(UrfQuest.panel.mainMenu);
				return true;
			}
		});
		
		titleScreen.addAllObjects(titleObjects);
		return titleScreen;
	}
	
	public static GUIContainer newOptionsOverlay() {	
		GUIContainer optionsScreen = new GUIContainer(GUIObject.TOP_LEFT, 
													0, 
													0, 
													UrfQuest.panel.getWidth(), 
													UrfQuest.panel.getHeight(), 
													"options", 
													null, 
													new Color(128, 128, 128, 128), null, 0);
		Set<GUIObject> optionsObjects = new HashSet<GUIObject>();
		
		optionsObjects.add(new TextBox("Sound:", 30, -160, -60, GUIObject.CENTER, optionsScreen));
		optionsObjects.add(new Slider(30, 0, -60, GUIObject.CENTER, optionsScreen) {
			public boolean click() {
				this.setSliderPosition();
				//SoundEngine.soundVol = this.sliderPos;
				return true;
			}
		});
		optionsObjects.add(new TextBox("Music:", 30, -160, -30, GUIObject.CENTER, optionsScreen));
		optionsObjects.add(new Slider(30, 0, -30, GUIObject.CENTER, optionsScreen) {
			public boolean click() {
				this.setSliderPosition();
				//SoundEngine.musicVol = this.sliderPos;
				return true;
			}
		});
		optionsObjects.add(new TextButton("Toggle Debug", 30, -80, 0, GUIObject.CENTER, optionsScreen) {
			public boolean click() {
				UrfQuest.debug = !UrfQuest.debug;
				return true;
			}
		});
		optionsObjects.add(new TextButton("Select Keybindings", 30, -80, 30, GUIObject.CENTER, optionsScreen) {
			public boolean click() {
				UrfQuest.panel.swap(UrfQuest.panel.keybindingView);
				return true;
			}
		});
		optionsObjects.add(new TextButton("Back", 30, -80, 60, GUIObject.CENTER, optionsScreen) {
			public boolean click() {
				UrfQuest.panel.swap(UrfQuest.panel.pauseMenu);
				return true;
			}
		});
		
		optionsScreen.addAllObjects(optionsObjects);
		return optionsScreen;
	}
	
	public static GUIContainer newPauseMenu() {
		GUIContainer pauseScreen = new GUIContainer(GUIObject.TOP_LEFT, 
													0, 
													0, 
													UrfQuest.panel.getWidth(), 
													UrfQuest.panel.getHeight(), 
													"pause", 
													null, 
													new Color(128, 128, 128, 128), null, 0);
		Set<GUIObject> pauseObjects = new HashSet<GUIObject>();
		
		pauseObjects.add(new TextBox("Paused", 60, -160, -120, GUIObject.CENTER, pauseScreen));
		pauseObjects.add(new TextButton("Resume", 30, -80, -60, GUIObject.CENTER, pauseScreen) {
			public boolean click() {
				UrfQuest.panel.unpause();
				return true;
			}
		});
		pauseObjects.add(new TextButton("Options", 30, -80, -30, GUIObject.CENTER, pauseScreen) {
			public boolean click() {
				UrfQuest.panel.swap(UrfQuest.panel.optionsMenu);
				return true;
			}
		});
		pauseObjects.add(new TextButton("Save", 30, -80, 0, GUIObject.CENTER, pauseScreen) {
			public boolean click() {
				Loader.saveGame();
				return true;
			}
		});
		pauseObjects.add(new TextButton("Load", 30, -80, 30, GUIObject.CENTER, pauseScreen) {
			public boolean click() {
				Loader.loadGame();
				return true;
			}
		});
		pauseObjects.add(new TextButton("Main Menu", 30, -80, 60, GUIObject.CENTER, pauseScreen) {
			public boolean click() {
				UrfQuest.panel.swap(UrfQuest.panel.mainMenu);
				return true;
			}
		});
		pauseObjects.add(new TextButton("Quit Game", 30, -80, 90, GUIObject.CENTER, pauseScreen) {
			public boolean click() {
				System.exit(0);
				return true;
			}
		});
		
		pauseScreen.addAllObjects(pauseObjects);
		return pauseScreen;
	}
	
	public static GUIContainer newGrayLayer() {
		return new GUIContainer(GUIObject.TOP_LEFT, 
								0, 
								0, 
								UrfQuest.panel.getWidth(), 
								UrfQuest.panel.getHeight(), 
								"graylayer", 
								null, 
								new Color(128, 128, 128, 128), null, 0);
	}
	
	public static GUIContainer newGUIOverlay() {
		//Set<GUIObject> guiObjects = new HashSet<GUIObject>();
		return new GUIContainer(GUIObject.TOP_LEFT, 
								0, 
								0, 
								UrfQuest.panel.getWidth(), 
								UrfQuest.panel.getHeight(), 
								"gui", 
								null, 
								null, null, 0);
	}
}