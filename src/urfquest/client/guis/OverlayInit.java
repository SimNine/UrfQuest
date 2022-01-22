package urfquest.client.guis;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import urfquest.Main;
import urfquest.client.Client;
import urfquest.client.guis.menus.*;

public class OverlayInit {
	
	public static GUIContainer newMainMenu(Client c) {
		GUIContainer mainMenu = new GUIContainer(c, 
												 GUIAnchor.TOP_LEFT, 
												 0, 
												 0, 
												 0, 
												 0, 
												 "main", 
												 null, new Color(128, 128, 128, 128), null, 0);
		Set<GUIObject> mainObjects = new HashSet<GUIObject>();
		
		mainObjects.add(new TextBox(c, "UrfQuest", 60, -160, -120, GUIAnchor.CENTER, mainMenu));
		mainObjects.add(new TextButton(c, "Play", 30, -80, -60, GUIAnchor.CENTER, mainMenu) {
			public boolean click() {
				Main.panel.unpause();
				return true;
			}
		});
		mainObjects.add(new TextButton(c, "Character", 30, -80, -30, GUIAnchor.CENTER, mainMenu) {
			public boolean click() {
				//currentOverlay = characterScreen;
				return true;
			}
		});
		mainObjects.add(new TextButton(c, "Options", 30, -80, 0, GUIAnchor.CENTER, mainMenu) {
			public boolean click() {
				Main.panel.swap(Main.panel.optionsMenu);
				return true;
			}
		});
		mainObjects.add(new TextButton(c, "Quit", 30, -80, 30, GUIAnchor.CENTER, mainMenu) {
			public boolean click() {
				System.exit(0);
				return true;
			}
		});
		
		mainMenu.addAllObjects(mainObjects);
		return mainMenu;
	}

	public static GUIContainer newTitleScreen(Client c) {
		GUIContainer titleScreen = newGrayLayer(c, "title");
		Set<GUIObject> titleObjects = new HashSet<GUIObject>();
		
		titleObjects.add(new ImageButton(c, "src/resources/arcanists2.png", 1, -185, GUIAnchor.CENTER, titleScreen) {
			public boolean click() {
				Main.panel.swap(Main.panel.mainMenu);
				return true;
			}
		});
		
		titleScreen.addAllObjects(titleObjects);
		return titleScreen;
	}
	
	public static GUIContainer newOptionsOverlay(Client c) {	
		GUIContainer optionsScreen = newGrayLayer(c, "options");
		
		optionsScreen.addObject(new TextBox(c, "Sound:", 30, -160, -60, GUIAnchor.CENTER, optionsScreen));
		optionsScreen.addObject(new Slider(c, 30, 0, -60, GUIAnchor.CENTER, optionsScreen) {
			public boolean click() {
				this.setSliderPosition();
				//SoundEngine.soundVol = this.sliderPos;
				return true;
			}
		});
		optionsScreen.addObject(new TextBox(c, "Music:", 30, -160, -30, GUIAnchor.CENTER, optionsScreen));
		optionsScreen.addObject(new Slider(c, 30, 0, -30, GUIAnchor.CENTER, optionsScreen) {
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
		optionsScreen.addObject(new TextButton(c, "Select Keybindings", 30, -80, 30, GUIAnchor.CENTER, optionsScreen) {
			public boolean click() {
				Main.panel.keybindingView.setKeybindings(Main.panel.getKeybindings());
				Main.panel.swap(Main.panel.keybindingView);
				return true;
			}
		});
		optionsScreen.addObject(new TextButton(c, "Back", 30, -80, 60, GUIAnchor.CENTER, optionsScreen) {
			public boolean click() {
				Main.panel.swap(Main.panel.pauseMenu);
				return true;
			}
		});
		
		return optionsScreen;
	}
	
	public static GUIContainer newPauseMenu(Client c) {
		GUIContainer pauseScreen = newGrayLayer(c, "pause");
		
		pauseScreen.addObject(new TextBox(c, "Paused", 60, -160, -120, GUIAnchor.CENTER, pauseScreen));
		pauseScreen.addObject(new TextButton(c, "Resume", 30, -80, -60, GUIAnchor.CENTER, pauseScreen) {
			public boolean click() {
				Main.panel.unpause();
				return true;
			}
		});
		pauseScreen.addObject(new TextButton(c, "Options", 30, -80, -30, GUIAnchor.CENTER, pauseScreen) {
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
		pauseScreen.addObject(new TextButton(c, "Main Menu", 30, -80, 60, GUIAnchor.CENTER, pauseScreen) {
			public boolean click() {
				Main.panel.swap(Main.panel.mainMenu);
				return true;
			}
		});
		pauseScreen.addObject(new TextButton(c, "Quit Game", 30, -80, 90, GUIAnchor.CENTER, pauseScreen) {
			public boolean click() {
				System.exit(0);
				return true;
			}
		});
		
		return pauseScreen;
	}
	
	public static GUIContainer newGrayLayer(Client c, String name) {
		return new GUIContainer(c, 
								GUIAnchor.TOP_LEFT, 
								0, 
								0, 
								0, 
								0, 
								name, 
								null, new Color(128, 128, 128, 128), null, 0);
	}
	
	public static GUIContainer newGUIOverlay(Client c) {
		//Set<GUIObject> guiObjects = new HashSet<GUIObject>();
		return new GUIContainer(c, 
								GUIAnchor.TOP_LEFT, 
								0, 
								0, 
								0, 
								0, 
								"gui", 
								null, null, null, 0);
	}
}