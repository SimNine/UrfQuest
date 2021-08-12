package urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.event.KeyEvent;

import urfquest.Main;

import urfquest.client.Keybindings;
import urfquest.client.guis.Clickable;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.client.guis.OverlayInit;

public class KeybindingOverlay extends GUIContainer {
	private KeybindingButton boxToWaitFor = null;
	private Keybindings keybindings = Main.panel.getKeybindings();
	
	public KeybindingOverlay() {
		super(GUIObject.TOP_LEFT, 
			  0, 
			  0, 
			  Main.panel.getWidth(), 
			  Main.panel.getHeight(), 
			  "keybindings", 
			  null, 
			  new Color(128, 128, 128, 128), null, 0);
		
		guiObjects.add(new TextBox("Map view: ", 30, -160, -150, GUIObject.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.TOGGLEMAPVIEW), 30, 120, -150, GUIObject.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.TOGGLEMAPVIEW = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Drop item: ", 30, -160, -120, GUIObject.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.DROPITEM), 30, 120, -120, GUIObject.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.DROPITEM = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Cycle minimap: ", 30, -160, -90, GUIObject.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.CYCLEMINIMAP), 30, 120, -90, GUIObject.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CYCLEMINIMAP = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Console: ", 30, -160, -60, GUIObject.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.CONSOLE), 30, 120, -60, GUIObject.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CONSOLE = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Fullscreen: ", 30, -160, -30, GUIObject.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.FULLSCREEN), 30, 120, -30, GUIObject.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.FULLSCREEN = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Build mode: ", 30, -160, 0, GUIObject.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.BUILDMODE), 30, 120, 0, GUIObject.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.BUILDMODE = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Map Link: ", 30, -160, 30, GUIObject.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.MAPLINK), 30, 120, 30, GUIObject.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.MAPLINK = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Crafting menu: ", 30, -160, 60, GUIObject.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.CRAFTING), 30, 120, 60, GUIObject.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CRAFTING = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextButton("Toggle Debug", 30, -80, 90, GUIObject.CENTER, this) {
			public boolean click() {
				Main.debug = !Main.debug;
				return true;
			}
		});
		guiObjects.add(new TextButton("Back", 30, -80, 120, GUIObject.CENTER, this) {
			public boolean click() {
				if (boxToWaitFor == null) {
					Main.panel.swap(OverlayInit.newOptionsOverlay());
				}
				if (Main.debug) {
					System.out.println(keybindings);
				}
				return true;
			}
		});
	}
	
	public boolean click() { // returns true if an object in this overlay was clicked
		boolean ret = false;
		for (GUIObject o : guiObjects) {
			if (o instanceof Clickable && o.isMouseOver()) {
				if (o instanceof KeybindingButton) {
					if (boxToWaitFor == null) {
						boxToWaitFor = ((KeybindingButton) o);
						((KeybindingButton) o).click();
					}
				} else {
					((Clickable) o).click();
				}
				ret = true;
				if (Main.debug) {
					System.out.println("GUIObject " + o.getClass().getSimpleName() + " clicked");
				}
			}
		}
		return ret;
	}
	
	public void keypress(int key) {
		if (boxToWaitFor == null) {
			return;
		}
		
		boxToWaitFor.setKey(key);
	}
}