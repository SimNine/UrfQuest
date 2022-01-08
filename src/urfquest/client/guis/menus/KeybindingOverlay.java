package urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.event.KeyEvent;

import urfquest.Main;

import urfquest.client.Keybindings;
import urfquest.client.guis.Clickable;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.client.guis.OverlayInit;

public class KeybindingOverlay extends GUIContainer {
	private KeybindingButton boxToWaitFor = null;
	private Keybindings keybindings = new Keybindings();
	
	public KeybindingOverlay() {
		super(GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  0, 
			  0, 
			  "keybindings", 
			  null, 
			  new Color(128, 128, 128, 128), null, 0);
		
		guiObjects.add(new TextBox("Map view: ", 30, -160, -150, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.TOGGLEMAPVIEW), 30, 120, -150, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.TOGGLEMAPVIEW = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Drop item: ", 30, -160, -120, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.DROPITEM), 30, 120, -120, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.DROPITEM = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Cycle minimap: ", 30, -160, -90, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.CYCLE_MINIMAP), 30, 120, -90, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CYCLE_MINIMAP = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Console: ", 30, -160, -60, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.CONSOLE), 30, 120, -60, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CONSOLE = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Fullscreen: ", 30, -160, -30, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.FULLSCREEN), 30, 120, -30, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.FULLSCREEN = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Build mode: ", 30, -160, 0, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.BUILDMODE), 30, 120, 0, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.BUILDMODE = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Map Link: ", 30, -160, 30, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.MAPLINK), 30, 120, 30, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.MAPLINK = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Crafting menu: ", 30, -160, 60, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.CRAFTING), 30, 120, 60, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CRAFTING = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox("Chat: ", 30, -160, 90, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(KeyEvent.getKeyText(keybindings.CHAT), 30, 120, 90, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CHAT = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextButton("Apply", 30, -80, 120, GUIAnchor.CENTER, this) {
			public boolean click() {
				Main.panel.setKeybindings(keybindings);
				return true;
			}
		});
		guiObjects.add(new TextButton("Back", 30, -80, 150, GUIAnchor.CENTER, this) {
			public boolean click() {
				if (boxToWaitFor == null) {
					Main.panel.swap(OverlayInit.newOptionsOverlay());
				}
				Main.client.getLogger().debug(keybindings.toString());
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
				Main.client.getLogger().debug("GUIObject " + o.getClass().getSimpleName() + " clicked");
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
	
	public void setKeybindings(Keybindings k) {
		this.keybindings = k;
	}
}