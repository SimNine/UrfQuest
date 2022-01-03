package urfquest.client.guis;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import urfquest.Main;
import urfquest.client.guis.menus.*;

public class OverlayInit {
	
	public static GUIContainer newMainMenu() {
		GUIContainer mainMenu = new GUIContainer(GUIAnchor.TOP_LEFT, 
												 0, 
												 0, 
												 Main.panel.getWidth(), 
												 Main.panel.getHeight(), 
												 "main", 
												 null, 
												 new Color(128, 128, 128, 128), null, 0);
		Set<GUIObject> mainObjects = new HashSet<GUIObject>();
		
		mainObjects.add(new TextBox("UrfQuest", 60, -160, -120, GUIAnchor.CENTER, mainMenu));
		mainObjects.add(new TextButton("Play", 30, -80, -60, GUIAnchor.CENTER, mainMenu) {
			public boolean click() {
				Main.panel.unpause();
				return true;
			}
		});
		mainObjects.add(new TextButton("Character", 30, -80, -30, GUIAnchor.CENTER, mainMenu) {
			public boolean click() {
				//currentOverlay = characterScreen;
				return true;
			}
		});
		mainObjects.add(new TextButton("Options", 30, -80, 0, GUIAnchor.CENTER, mainMenu) {
			public boolean click() {
				Main.panel.swap(Main.panel.optionsMenu);
				return true;
			}
		});
		mainObjects.add(new TextButton("Quit", 30, -80, 30, GUIAnchor.CENTER, mainMenu) {
			public boolean click() {
				System.exit(0);
				return true;
			}
		});
		
		mainMenu.addAllObjects(mainObjects);
		return mainMenu;
	}

	public static GUIContainer newTitleScreen() {
		GUIContainer titleScreen = newGrayLayer("title");
		Set<GUIObject> titleObjects = new HashSet<GUIObject>();
		
		titleObjects.add(new ImageButton("src/resources/arcanists2.png", 1, -185, GUIAnchor.CENTER, titleScreen) {
			public boolean click() {
				Main.panel.swap(Main.panel.mainMenu);
				return true;
			}
		});
		
		titleScreen.addAllObjects(titleObjects);
		return titleScreen;
	}
	
	public static GUIContainer newOptionsOverlay() {	
		GUIContainer optionsScreen = newGrayLayer("options");
		
		optionsScreen.addObject(new TextBox("Sound:", 30, -160, -60, GUIAnchor.CENTER, optionsScreen));
		optionsScreen.addObject(new Slider(30, 0, -60, GUIAnchor.CENTER, optionsScreen) {
			public boolean click() {
				this.setSliderPosition();
				//SoundEngine.soundVol = this.sliderPos;
				return true;
			}
		});
		optionsScreen.addObject(new TextBox("Music:", 30, -160, -30, GUIAnchor.CENTER, optionsScreen));
		optionsScreen.addObject(new Slider(30, 0, -30, GUIAnchor.CENTER, optionsScreen) {
			public boolean click() {
				this.setSliderPosition();
				//SoundEngine.musicVol = this.sliderPos;
				return true;
			}
		});
//		optionsScreen.addObject(new TextButton("Toggle Debug", 30, -80, 0, GUIObject.CENTER, optionsScreen) {
//			public boolean click() {
//				Main.debug = !Main.debug;
//				return true;
//			}
//		});
		optionsScreen.addObject(new TextButton("Select Keybindings", 30, -80, 30, GUIAnchor.CENTER, optionsScreen) {
			public boolean click() {
				Main.panel.swap(Main.panel.keybindingView);
				return true;
			}
		});
		optionsScreen.addObject(new TextButton("Back", 30, -80, 60, GUIAnchor.CENTER, optionsScreen) {
			public boolean click() {
				Main.panel.swap(Main.panel.pauseMenu);
				return true;
			}
		});
		
		return optionsScreen;
	}
	
	public static GUIContainer newPauseMenu() {
		GUIContainer pauseScreen = newGrayLayer("pause");
		
		pauseScreen.addObject(new TextBox("Paused", 60, -160, -120, GUIAnchor.CENTER, pauseScreen));
		pauseScreen.addObject(new TextButton("Resume", 30, -80, -60, GUIAnchor.CENTER, pauseScreen) {
			public boolean click() {
				Main.panel.unpause();
				return true;
			}
		});
		pauseScreen.addObject(new TextButton("Options", 30, -80, -30, GUIAnchor.CENTER, pauseScreen) {
			public boolean click() {
				Main.panel.swap(Main.panel.optionsMenu);
				return true;
			}
		});
//		pauseScreen.addObject(new TextButton("Save", 30, -80, 0, GUIAnchor.CENTER, pauseScreen) {
//			public boolean click() {
//				Loader.saveGame();
//				return true;
//			}
//		});
//		pauseScreen.addObject(new TextButton("Load", 30, -80, 30, GUIAnchor.CENTER, pauseScreen) {
//			public boolean click() {
//				Loader.loadGame();
//				return true;
//			}
//		});
		pauseScreen.addObject(new TextButton("Main Menu", 30, -80, 60, GUIAnchor.CENTER, pauseScreen) {
			public boolean click() {
				Main.panel.swap(Main.panel.mainMenu);
				return true;
			}
		});
		pauseScreen.addObject(new TextButton("Quit Game", 30, -80, 90, GUIAnchor.CENTER, pauseScreen) {
			public boolean click() {
				System.exit(0);
				return true;
			}
		});
		
		return pauseScreen;
	}
	
	public static GUIContainer newGrayLayer(String name) {
		return new GUIContainer(GUIAnchor.TOP_LEFT, 
								0, 
								0, 
								Main.panel.getWidth(), 
								Main.panel.getHeight(), 
								name, 
								null, 
								new Color(128, 128, 128, 128), null, 0);
	}
	
	public static GUIContainer newGUIOverlay() {
		//Set<GUIObject> guiObjects = new HashSet<GUIObject>();
		return new GUIContainer(GUIAnchor.TOP_LEFT, 
								0, 
								0, 
								Main.panel.getWidth(), 
								Main.panel.getHeight(), 
								"gui", 
								null, 
								null, null, 0);
	}
}