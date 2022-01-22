package urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.event.KeyEvent;

import urfquest.client.Client;
import urfquest.client.Keybindings;
import urfquest.client.guis.Clickable;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;

public class KeybindingOverlay extends GUIContainer {
	private KeybindingButton boxToWaitFor = null;
	private Keybindings keybindings = new Keybindings();
	
	public KeybindingOverlay(Client c) {
		super(c, 
			  GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  0, 
			  0, 
			  "keybindings", 
			  null, new Color(128, 128, 128, 128), null, 0);
		
		guiObjects.add(new TextBox(c, "Map view: ", 30, -160, -150, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.TOGGLEMAPVIEW), 30, 120, -150, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.TOGGLEMAPVIEW = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox(c, "Drop item: ", 30, -160, -120, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.DROPITEM), 30, 120, -120, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.DROPITEM = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox(c, "Cycle minimap: ", 30, -160, -90, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.CYCLE_MINIMAP), 30, 120, -90, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CYCLE_MINIMAP = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox(c, "Console: ", 30, -160, -60, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.CONSOLE), 30, 120, -60, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CONSOLE = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox(c, "Fullscreen: ", 30, -160, -30, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.FULLSCREEN), 30, 120, -30, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.FULLSCREEN = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox(c, "Build mode: ", 30, -160, 0, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.BUILDMODE), 30, 120, 0, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.BUILDMODE = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox(c, "Map Link: ", 30, -160, 30, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.MAPLINK), 30, 120, 30, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.MAPLINK = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox(c, "Crafting menu: ", 30, -160, 60, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.CRAFTING), 30, 120, 60, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CRAFTING = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextBox(c, "Chat: ", 30, -160, 90, GUIAnchor.CENTER, this));
		guiObjects.add(new KeybindingButton(c, KeyEvent.getKeyText(keybindings.CHAT), 30, 120, 90, GUIAnchor.CENTER, this) {
			public void setKey(int k) {
				text = KeyEvent.getKeyText(k);
				keybindings.CHAT = k;
				boxToWaitFor = null;
				setWaiting(false);
			}
		});
		
		guiObjects.add(new TextButton(c, "Apply", 30, -80, 120, GUIAnchor.CENTER, this) {
			public boolean click() {
				// TODO: find workaround
				//Main.panel.setKeybindings(keybindings);
				return true;
			}
		});
		guiObjects.add(new TextButton(c, "Back", 30, -80, 150, GUIAnchor.CENTER, this) {
			public boolean click() {
				if (boxToWaitFor == null) {
					// TODO: find workaround
					//this.panel.swap(OverlayInit.newOptionsOverlay(this.client));
				}
				this.client.getLogger().debug(keybindings.toString());
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
				this.client.getLogger().debug("GUIObject " + o.getClass().getSimpleName() + " clicked");
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