package xyz.urffer.urfquest.client.entities.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.client.entities.mobs.Chicken;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.entities.projectiles.Bullet;
import xyz.urffer.urfquest.client.entities.projectiles.GrenadeProjectile;
import xyz.urffer.urfquest.client.entities.projectiles.Rocket;
import xyz.urffer.urfquest.client.entities.projectiles.RocketExplosion;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.PairInt;
import xyz.urffer.urfquest.shared.protocol.types.ItemType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class ItemStack extends Entity {

	private ItemType itemType;
	private int cooldown;
	private int durability;
	private int stackSize;
	
	private PairDouble vel = new PairDouble(0, 0);
	
	private int dropTimeout = 500;
	
	public ItemStack(Client c, int id, Map m, PairDouble pos, ItemType type) {
		this(c, id, m, pos, type, 1, -1);
	}
	
	public ItemStack(Client c, int id, Map m, PairDouble pos, ItemType type, int durability) {
		this(c, id, m, pos, type, 1, durability);
	}
	
	public ItemStack(Client c, int id, Map m, PairDouble pos, ItemType type, int stackSize, int durability) {
		super(c, id, m, pos);
		bounds = new Rectangle2D.Double(pos.x, pos.y, 1, 1);
		
		this.itemType = type;
		this.cooldown = 0;
		if (this.degrades()) {
			if (durability == -1) {
				this.durability = this.getMaxDurability();
			} else {
				this.durability = durability;
			}
		} else {
			this.durability = -1;
		}
		if (stackSize == -1) {
			this.stackSize = this.maxStackSize();
		} else {
			this.stackSize = stackSize;
		}
	}

	/*
	 * Manipulation methods
	 */
	
	// true if used successfully, false if otherwise
	public boolean use(Mob m) {
		// don't use if not cooled down
		if (cooldown > 0) {
			return false;
		}
		
		switch (itemType) {
		case EMPTY_ITEM:
			throw new IllegalStateException("something fucked up");
		case ASTRAL_RUNE:
			if (m.getMana() < 50.0) {
				return false;
			} // else
			cooldown = getMaxCooldown();
			
			m.incrementMana(-50.0);
			PairDouble pos = m.getCenter();
			for (int i = 0; i < 180; i++) {
				//m.getMap().addProjectile(new Bullet(client, id, map, pos, i*2, Bullet.getDefaultVelocity(), m, map));
			}
			return true;
		case COSMIC_RUNE:
			if (m.getMana() < 5.0) {
				return false;
			} // else
			cooldown = getMaxCooldown();
			
			m.incrementMana(-5.0);
			PairDouble pos3 = m.getPos();
			//m.getMap().addMob(new Chicken(client, map, pos3[0], pos3[1]));
			return true;
		case LAW_RUNE:
			if (m.getMana() < 30.0) {
				return false;
			}
			cooldown = getMaxCooldown();
			
			m.incrementMana(-30.0);
			PairInt home = m.getMap().getHomeCoords();
			m.setPos(home.toDouble());
			return true;
		case CHICKEN_LEG:
			cooldown = getMaxCooldown();
			
			if (m.getFullness() > 95.0) {
				return false;
			} else {
				m.incrementFullness(5.0);
				return true;
			}
		case CHEESE:
			cooldown = getMaxCooldown();
			
			if (m.getFullness() > 95.0) {
				return false;
			} else {
				m.setFullness(m.getMaxFullness());
				return true;
			}
		case BONE:
			return false;
		case GEM:
			return false;
		case LOG:
			return false;
		case STONE:
			return false;
		case MIC:
			if (m.getMana() < 30.0) {
				return false;
			}
			cooldown = getMaxCooldown();
			
			for (int i = 0; i < 20; i++) {
				//map.addProjectile(new RocketExplosion(null, bounds.x + (Math.random() - 0.5)*20, bounds.y + (Math.random() - 0.5)*20, this, map));
			}
			return true;
		case KEY:
			return false;
		case GRENADE_ITEM:
			cooldown = getMaxCooldown();
			
			//m.getMap().addProjectile(new GrenadeProjectile(null, m.getCenter()[0], m.getCenter()[1], m, m.getMap()));
			return true;
		case PISTOL:
			cooldown = getMaxCooldown();
			
//			double[] pos1 = m.getCenter();
//			int dir = m.getDirection() + (int)((Math.random() - 0.5)*10);
//			m.getMap().addProjectile(new Bullet(pos1[0], pos1[1], dir, Bullet.getDefaultVelocity(), m, map));
			return true;
		case RPG:
			cooldown = getMaxCooldown();
//			
//			double[] pos2 = m.getCenter();
//			int dir1 = m.getDirection();
//			m.getMap().addProjectile(new Rocket(client, id, m.getMap(), pos2[0], dir1, Rocket.getDefaultVelocity(), m));
			
			return true;
		case SHOTGUN:
			cooldown = getMaxCooldown();
			
			PairDouble pos4 = m.getCenter();
			int dir4;
			int numShots = 15 + (int)(Math.random()*5);
			
//			for (int i = 0; i < numShots; i++) {
//				dir4 = m.getDirection() + (int)((Math.random() - 0.5)*20);
//				m.getMap().addProjectile(new Bullet(pos4[0], pos4[1], dir4, Bullet.getDefaultVelocity(), m, map));
//			}
			
			return true;
		case SMG:
			cooldown = getMaxCooldown();
			
//			double[] pos5 = m.getCenter();
//			int dir5 = m.getDirection() + (int)((Math.random() - 0.5)*10);
//			m.getMap().addProjectile(new Bullet(pos5[0], pos5[1], dir5, Bullet.getDefaultVelocity(), m, map));
			return true;
		case PICKAXE: {
			// TODO: fix
//			int[] tile = m.tileAtDistance(1.0);
//			if (tile[0] == TileImages.BOULDER) {
//				int[] coords = m.tileCoordsAtDistance(1.0);
//				if (tile[1] == TileImages.GRASS_BOULDER) {
//					//m.getMap().setTileAt(coords[0], coords[1], Tiles.GRASS);
//				} else if (tile[1] == TileImages.WATER_BOULDER) {
//					//m.getMap().setTileAt(coords[0], coords[1], Tiles.WATER);
//				} else if (tile[1] == TileImages.SAND_BOULDER) {
//					//m.getMap().setTileAt(coords[0], coords[1], Tiles.SAND);
//				} else if (tile[1] == TileImages.DIRT_BOULDER) {
//					//m.getMap().setTileAt(coords[0], coords[1], Tiles.DIRT);
//					double rand = Math.random();
//					if (rand > .95) {
//						//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.LAW_RUNE));
//					} else if (rand > .90) {
//						//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.COSMIC_RUNE));
//					} else if (rand > .85) {
//						//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.ASTRAL_RUNE));
//					} else if (rand > .82) {
//						//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.SHOTGUN));
//					} else if (rand > .79) {
//						//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.SMG));
//					} else if (rand > .75) {
//						//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.GRENADE_ITEM));
//					} else {
//						//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.STONE));
//					}
//				}
//				//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.STONE));
//				cooldown = getMaxCooldown();
//				return true;
//			} else if (tile[0] == TileImages.STONE) {
//				int[] coords = m.tileCoordsAtDistance(1.0);
//				if (tile[1] == TileImages.STONE_DEF) {
//					// nothing else
//				} else if (tile[1] == TileImages.COPPERORE_STONE) {
//					//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.COPPER_ORE));
//				} else if (tile[1] == TileImages.IRONORE_STONE) {
//					//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.IRON_ORE));
//				}
//				//m.getMap().setTileAt(coords[0], coords[1], Tiles.DIRT);
//				cooldown = getMaxCooldown();			
//				return true;
//			} else {
//				return false;
//			}
			return false;
		}
		case HATCHET: {
			// TODO: fix
//			if (m.tileAtDistance(1.0).objectType == TileType.TREE) {
//				int[] coords = m.tileCoordsAtDistance(1.0);
//				//m.getMap().setTileAt(coords[0], coords[1], Tiles.GRASS);
//				//m.getMap().addItem(new Item(client, map, coords[0], coords[1], Item.LOG));
//				
//				cooldown = getMaxCooldown();
//				return true;
//			} else {
//				return false;
//			}
			return false;
		}
		case SHOVEL: {
//			if (m.tileAtDistance(0)[0] == TileImages.GRASS) {
//				int[] coords = m.tileCoordsAtDistance(0);
//				if (Math.random() > .05) {
//					m.getMap().setTileAt(coords, 0);
//				} else {
//					m.getMap().setTileAt(coords, 13);
//					
//					//int caveSize = 400;
//					
//					// create new map
////					Map newCaveMap = new Map(Map.CAVE_MAP);
////					int xHome = newCaveMap.getHomeCoords()[0];
////					int yHome = newCaveMap.getHomeCoords()[1];
////					newCaveMap.setTileAt(xHome, yHome, 13);
////					
////					//generate and add new link
////					MapLink newLink = new MapLink(m.getMap(), coords[0], coords[1], newCaveMap, xHome, yHome);
////					m.getMap().setActiveTile(coords[0], coords[1], newLink);
////					newCaveMap.setActiveTile(xHome, yHome, newLink);
////					
////					//debug
////					if (UrfQuestClient.debug) {
////						System.out.println("soruce: " + coords[0] + ", " + coords[1]);
////						System.out.println("exit: " + xHome + ", " + yHome);
////					}
//				}
//				
//				cooldown = getMaxCooldown();
//				return true;
//			} else {
//				return false;
//			}
			return false;
		}
		case IRON_ORE:
			return false;
		case COPPER_ORE:
			return false;
		default:
			throw new IllegalArgumentException("something fucked up - nonexistent item ID");
		}
	}
	
	public void update() {
		incrementPos(vel);
		
		if (getMaxCooldown() > -1) {
			if (cooldown > 0) {
				cooldown--;
			}
		}
		
		if (vel.x != 0) {
			vel.x -= vel.x*0.02;
		}
		if (vel.y != 0) {
			vel.y -= vel.y*0.02;
		}
		
		if (dropTimeout > 0) {
			dropTimeout--;
		}
	}
	
	public void accelerateTowards(Mob m) {
		PairDouble diff = m.getCenter().subtract(this.getCenter());
		
		if (diff.x > 0) { // if m is east of this
			vel.x += 0.002;
		} else if (diff.x < 0) { // if m is west of this
			vel.x -= 0.002;
		} else {
			// nada
		}
		
		if (diff.y > 0) { // if m is south of this
			vel.y += 0.002;
		} else if (diff.y < 0) { // if m is north of this
			vel.y -= 0.002;
		} else {
			// nada
		}
	}
	
//	public Item clone() {
//		//return new Item(client, id, map, bounds.x, bounds.y, itemType, stackSize, durability);
//	}
	
	/*
	 * Getters and setters
	 */
	
	public boolean isConsumable() {
		return itemType.getConsumable();
	}
	
	public int getMaxCooldown() {
		return itemType.getMaxCooldown();
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	public void setCooldown(int i) {
		if (getMaxCooldown() > -1) {
			cooldown = i;
		} else {
			throw new IllegalArgumentException("This item has no cooldown");
		}
	}
	
	public double getCooldownPercentage() {
		return (cooldown/(double)getMaxCooldown());
	}
	
	public boolean isUsable() {
		return (getMaxCooldown() > -1);
	}
	
	public int getMaxDurability() {
		return itemType.getMaxDurability();
	}
	
	public int getDurability() {
		return durability;
	}
	
	public void setDurability(int i) {
		if (getMaxDurability() > -1) {
			if (i < 0) {
				durability = 0;
			} else {
				durability = i;
			}
		} else {
			throw new IllegalArgumentException("This item has no durability");
		}
	}
	
	public void incDurability(int i) {
		setDurability(durability + i);
	}
	
	public double getDurabilityPercentage() {
		return (durability/(double)getMaxDurability());
	}
	
	public boolean degrades() {
		return (getMaxDurability() > -1);
	}
	
	public int currStackSize() {
		return stackSize;
	}
	
	public void incStackSize(int i) {
		if (stackSize + i < 0) {
			stackSize = 0;
		} else {
			stackSize += i;
		}
	}
	
	public void setStackSize(int i) {
		if (i < 1) {
			throw new IllegalArgumentException();
		}
		
		if (i > 1 && maxStackSize() == 1) {
			stackSize = 1;
		}
		
		stackSize = i;
	}
	
	public int maxStackSize() {
		return itemType.getMaxStacksize();
	}
	
	public ItemType getType() {
		return itemType;
	}
	
	public void resetDropTimeout() {
		dropTimeout = 500;
	}
	
	public boolean isPickupable() {
		return (dropTimeout == 0);
	}
	
	/*
	 * drawing methods
	 */
	
	protected void drawEntity(Graphics g) {
		g.drawImage(itemType.getImage(), 
					client.getPanel().gameToWindowX(bounds.getX()), 
					client.getPanel().gameToWindowY(bounds.getY()), 
					null);
	}

	public void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					 (int) client.getPanel().gameToWindowX(bounds.getX()),
					 (int) client.getPanel().gameToWindowY(bounds.getY()));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 (int) client.getPanel().gameToWindowX(bounds.getX()),
				 (int) client.getPanel().gameToWindowY(bounds.getY()) + 10);
	};

	public BufferedImage getPic() {
		return itemType.getImage();
	}
}