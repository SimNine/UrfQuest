package server.entities.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.entities.Entity;
import server.entities.mobs.Chicken;
import server.entities.mobs.Mob;
import server.entities.projectiles.Bullet;
import server.entities.projectiles.GrenadeProjectile;
import server.entities.projectiles.Rocket;
import server.entities.projectiles.RocketExplosion;
import server.game.QuestMap;
import framework.UrfQuest;
import server.tiles.MapLink;
import server.tiles.Tiles;

public class Item extends Entity {
	public static final String assetPath = "/assets/items/";
	
	public static final int EMPTY_ITEM = 0;
	public static final int ASTRAL_RUNE = 1;
	public static final int COSMIC_RUNE = 2;
	public static final int LAW_RUNE = 3;
	public static final int CHICKEN_LEG = 4;
	public static final int CHEESE = 5;
	public static final int BONE = 6;
	public static final int GEM = 7;
	public static final int LOG = 8;
	public static final int STONE = 9;
	public static final int MIC = 10;
	public static final int KEY = 11;
	public static final int GRENADE_ITEM = 12;
	public static final int PISTOL = 13;
	public static final int RPG = 14;
	public static final int SHOTGUN = 15;
	public static final int SMG = 16;
	public static final int PICKAXE = 17;
	public static final int HATCHET = 18;
	public static final int SHOVEL = 19;
	public static final int IRON_ORE = 20;
	public static final int COPPER_ORE = 21;
	
	public static final BufferedImage[] itemImages = new BufferedImage[21];
	
	public static final boolean[][] itemBooleanProperties = 
			   //consumable
			{ {true},//1
			  {true},//2
			  {true},
			  {true},//4
			  {true},
			  {false},//6
			  {false},
			  {false},//8
			  {false},
			  {true},//10
			  {false},
			  {true},//12
			  {false},
			  {false},//14
			  {false},
			  {false},//16
			  {false},
			  {false},//18
			  {false},
			  {false},//20
			  {false} };
		
	public static final int[][] itemIntProperties = 
			   //maxCooldown	//maxDurability	//maxStackSize
			{ {1000, 			-1,				10},
			  {1000,			-1,				10},//2
			  {1000,			-1,				10},
			  {50,				-1,				100},//4
			  {50,				-1, 			100},
			  {-1,				-1,				100},//6
			  {-1,				-1,				100},
			  {-1,				-1,				100},//8
			  {-1,				-1,				100},
			  {1000,			-1,				10},//10
			  {-1,				-1,				100},
			  {100,				-1,				10},//12
			  {100,				-1,				1},
			  {400,				-1,				1},//14
			  {400,				-1,				1},
			  {10,				-1,				1},//16
			  {100,				100,			1},
			  {100,				100,			1},//18
			  {100,				100,			1},
			  {-1,				-1,				100},//20
			  {-1,				-1,				100} };
	
	private int cooldown;
	private int durability;
	private int stackSize;
	private int itemType;
	
	private double xVel = 0;
	private double yVel = 0;
	
	private int dropTimeout = 500;
	
	public Item(double x, double y, int type, QuestMap m) {
		this(x, y, type, 1, -1, m);
	}
	
	public Item(double x, double y, int type, int durability, QuestMap m) {
		this(x, y, type, 1, durability, m);
	}
	
	public Item(double x, double y, int type, int stackSize, int durability, QuestMap m) {
		super(x, y, m);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		
		if (itemImages[type - 1] == null) {
			initItemPics();
		}
		
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
	 * Pic initialization
	 */
	
	private static void initItemPics() {
		try {
			Class<?> c = UrfQuest.quest.getClass();
			itemImages[0] = ImageIO.read(c.getResourceAsStream(assetPath + "astralRune_scaled_30px.png"));
			itemImages[1] = ImageIO.read(c.getResourceAsStream(assetPath + "cosmicRune_scaled_30px.png"));
			itemImages[2] = ImageIO.read(c.getResourceAsStream(assetPath + "lawRune_scaled_30px.png"));
			itemImages[3] = ImageIO.read(c.getResourceAsStream(assetPath + "chickenLeg_scaled_30px.png"));
			itemImages[4] = ImageIO.read(c.getResourceAsStream(assetPath + "cheese_scaled_30px.png"));
			itemImages[5] = ImageIO.read(c.getResourceAsStream(assetPath + "bone_scaled_30px.png"));
			itemImages[6] = ImageIO.read(c.getResourceAsStream(assetPath + "pink_gem_scaled_30px.png"));
			itemImages[7] = ImageIO.read(c.getResourceAsStream(assetPath + "log_scaled_30px.png"));
			itemImages[8] = ImageIO.read(c.getResourceAsStream(assetPath + "stoneitem_scaled_30px.png"));
			itemImages[9] = ImageIO.read(c.getResourceAsStream(assetPath + "mic_scaled_30px.png"));
			itemImages[10] = ImageIO.read(c.getResourceAsStream(assetPath + "key_scaled_30px.png"));
			itemImages[11] = ImageIO.read(c.getResourceAsStream(assetPath + "grenade_scaled_30px.png"));
			itemImages[12] = ImageIO.read(c.getResourceAsStream(assetPath + "gun_scaled_30px.png"));
			itemImages[13] = ImageIO.read(c.getResourceAsStream(assetPath + "rocket_scaled_30px.png"));
			itemImages[14] = ImageIO.read(c.getResourceAsStream(assetPath + "shotgun_scaled_30px.png"));
			itemImages[15] = ImageIO.read(c.getResourceAsStream(assetPath + "smg_scaled_30px.png"));
			itemImages[16] = ImageIO.read(c.getResourceAsStream(assetPath + "pickaxe_scaled_30px.png"));
			itemImages[17] = ImageIO.read(c.getResourceAsStream(assetPath + "hatchet_scaled_30px.png"));
			itemImages[18] = ImageIO.read(c.getResourceAsStream(assetPath + "shovel_scaled_30px.png"));
			itemImages[19] = ImageIO.read(c.getResourceAsStream(assetPath + "ironore_scaled_30px.png"));
			itemImages[20] = ImageIO.read(c.getResourceAsStream(assetPath + "copperore_scaled_30px.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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
		case Item.EMPTY_ITEM:
			throw new IllegalStateException("something fucked up");
		case Item.ASTRAL_RUNE:
			if (m.getMana() < 50.0) {
				return false;
			} // else
			cooldown = getMaxCooldown();
			
			m.incrementMana(-50.0);
			double[] pos = m.getCenter();
			for (int i = 0; i < 180; i++) {
				m.getMap().addProjectile(new Bullet(pos[0], pos[1], i*2, Bullet.getDefaultVelocity(), m, map));
			}
			return true;
		case Item.COSMIC_RUNE:
			if (m.getMana() < 5.0) {
				return false;
			} // else
			cooldown = getMaxCooldown();
			
			m.incrementMana(-5.0);
			double[] pos3 = m.getPos();
			m.getMap().addMob(new Chicken(pos3[0], pos3[1], map));
			return true;
		case Item.LAW_RUNE:
			if (m.getMana() < 30.0) {
				return false;
			}
			cooldown = getMaxCooldown();
			
			m.incrementMana(-30.0);
			int[] home = m.getMap().getHomeCoords();
			m.setPos(home[0], home[1]);
			return true;
		case Item.CHICKEN_LEG:
			cooldown = getMaxCooldown();
			
			if (m.getFullness() > 95.0) {
				return false;
			} else {
				m.incrementFullness(5.0);
				return true;
			}
		case Item.CHEESE:
			cooldown = getMaxCooldown();
			
			if (m.getFullness() > 95.0) {
				return false;
			} else {
				m.setFullness(m.getMaxFullness());
				return true;
			}
		case Item.BONE:
			return false;
		case Item.GEM:
			return false;
		case Item.LOG:
			return false;
		case Item.STONE:
			return false;
		case Item.MIC:
			if (m.getMana() < 30.0) {
				return false;
			}
			cooldown = getMaxCooldown();
			
			for (int i = 0; i < 20; i++) {
				map.addProjectile(new RocketExplosion(bounds.x + (Math.random() - 0.5)*20, bounds.y + (Math.random() - 0.5)*20, this, map));
			}
			return true;
		case Item.KEY:
			return false;
		case Item.GRENADE_ITEM:
			cooldown = getMaxCooldown();
			
			m.getMap().addProjectile(new GrenadeProjectile(m.getCenter()[0], m.getCenter()[1], m, m.getMap()));
			return true;
		case Item.PISTOL:
			cooldown = getMaxCooldown();
			
			double[] pos1 = m.getCenter();
			int dir = m.getDirection() + (int)((Math.random() - 0.5)*10);
			m.getMap().addProjectile(new Bullet(pos1[0], pos1[1], dir, Bullet.getDefaultVelocity(), m, map));
			return true;
		case Item.RPG:
			cooldown = getMaxCooldown();
			
			double[] pos2 = m.getCenter();
			int dir1 = m.getDirection();
			m.getMap().addProjectile(new Rocket(pos2[0], pos2[1], dir1, Rocket.getDefaultVelocity(), m, m.getMap()));
			
			return true;
		case Item.SHOTGUN:
			cooldown = getMaxCooldown();
			
			double[] pos4 = m.getCenter();
			int dir4;
			int numShots = 15 + (int)(Math.random()*5);
			
			for (int i = 0; i < numShots; i++) {
				dir4 = m.getDirection() + (int)((Math.random() - 0.5)*20);
				m.getMap().addProjectile(new Bullet(pos4[0], pos4[1], dir4, Bullet.getDefaultVelocity(), m, map));
			}
			
			return true;
		case Item.SMG:
			cooldown = getMaxCooldown();
			
			double[] pos5 = m.getCenter();
			int dir5 = m.getDirection() + (int)((Math.random() - 0.5)*10);
			m.getMap().addProjectile(new Bullet(pos5[0], pos5[1], dir5, Bullet.getDefaultVelocity(), m, map));
			return true;
		case Item.PICKAXE:
			int[] tile = m.tileAtDistance(1.0);
			if (tile[0] == Tiles.BOULDER) {
				int[] coords = m.tileCoordsAtDistance(1.0);
				if (tile[1] == Tiles.GRASS_BOULDER) {
					m.getMap().setTileAt(coords[0], coords[1], Tiles.GRASS);
				} else if (tile[1] == Tiles.WATER_BOULDER) {
					m.getMap().setTileAt(coords[0], coords[1], Tiles.WATER);
				} else if (tile[1] == Tiles.SAND_BOULDER) {
					m.getMap().setTileAt(coords[0], coords[1], Tiles.SAND);
				} else if (tile[1] == Tiles.DIRT_BOULDER) {
					m.getMap().setTileAt(coords[0], coords[1], Tiles.DIRT);
					double rand = Math.random();
					if (rand > .95) {
						m.getMap().addItem(new Item(coords[0], coords[1], Item.LAW_RUNE, map));
					} else if (rand > .90) {
						m.getMap().addItem(new Item(coords[0], coords[1], Item.COSMIC_RUNE, map));
					} else if (rand > .85) {
						m.getMap().addItem(new Item(coords[0], coords[1], Item.ASTRAL_RUNE, map));
					} else if (rand > .82) {
						m.getMap().addItem(new Item(coords[0], coords[1], Item.SHOTGUN, map));
					} else if (rand > .79) {
						m.getMap().addItem(new Item(coords[0], coords[1], Item.SMG, map));
					} else if (rand > .75) {
						m.getMap().addItem(new Item(coords[0], coords[1], Item.GRENADE_ITEM, map));
					} else {
						m.getMap().addItem(new Item(coords[0], coords[1], Item.STONE, map));
					}
				}
				m.getMap().addItem(new Item(coords[0], coords[1], Item.STONE, map));
				cooldown = getMaxCooldown();
				return true;
			} else if (tile[0] == Tiles.STONE) {
				int[] coords = m.tileCoordsAtDistance(1.0);
				if (tile[1] == Tiles.STONE_DEF) {
					// nothing else
				} else if (tile[1] == Tiles.COPPERORE_STONE) {
					m.getMap().addItem(new Item(coords[0], coords[1], Item.COPPER_ORE, map));
				} else if (tile[1] == Tiles.IRONORE_STONE) {
					m.getMap().addItem(new Item(coords[0], coords[1], Item.IRON_ORE, map));
				}
				m.getMap().setTileAt(coords[0], coords[1], Tiles.DIRT);
				cooldown = getMaxCooldown();			
				return true;
			} else {
				return false;
			}
		case Item.HATCHET:
			if (m.tileAtDistance(1.0)[0] == Tiles.TREE) {
				int[] coords = m.tileCoordsAtDistance(1.0);
				m.getMap().setTileAt(coords[0], coords[1], Tiles.GRASS);
				m.getMap().addItem(new Item(coords[0], coords[1], Item.LOG, map));
				
				cooldown = getMaxCooldown();
				return true;
			} else {
				return false;
			}
		case Item.SHOVEL:
			if (m.tileAtDistance(0)[0] == Tiles.GRASS) {
				int[] coords = m.tileCoordsAtDistance(0);
				if (Math.random() > .05) {
					m.getMap().setTileAt(coords[0], coords[1], 0);
				} else {
					m.getMap().setTileAt(coords[0], coords[1], 13);
					
					int caveSize = 400;
					
					// create new map
					QuestMap newCaveMap = new QuestMap(caveSize, caveSize, QuestMap.CAVE_MAP);
					int xHome = newCaveMap.getHomeCoords()[0];
					int yHome = newCaveMap.getHomeCoords()[1];
					newCaveMap.setTileAt(xHome, yHome, 13);
					
					//generate and add new link
					MapLink newLink = new MapLink(m.getMap(), coords[0], coords[1], newCaveMap, xHome, yHome);
					m.getMap().setActiveTile(coords[0], coords[1], newLink);
					newCaveMap.setActiveTile(xHome, yHome, newLink);
					
					//debug
					if (UrfQuest.debug) {
						System.out.println("soruce: " + coords[0] + ", " + coords[1]);
						System.out.println("exit: " + xHome + ", " + yHome);
					}
				}
				
				cooldown = getMaxCooldown();
				return true;
			} else {
				return false;
			}
		case Item.IRON_ORE:
			return false;
		case Item.COPPER_ORE:
			return false;
		default:
			throw new IllegalArgumentException("something fucked up - nonexistent item ID");
		}
	}
	
	public void update() {
		move(xVel, yVel);
		
		if (getMaxCooldown() > -1) {
			if (cooldown > 0) {
				cooldown--;
			}
		}
		
		if (xVel != 0) {
			xVel -= xVel*0.02;
		}
		if (yVel != 0) {
			yVel -= yVel*0.02;
		}
		
		if (dropTimeout > 0) {
			dropTimeout--;
		}
	}
	
	public void accelerateTowards(Mob m) {
		double xDiff = (m.getCenter()[0] - this.getCenter()[0]);
		double yDiff = (m.getCenter()[1] - this.getCenter()[1]);
		
		if (xDiff > 0) { // if m is east of this
			xVel += 0.002;
		} else if (xDiff < 0) { // if m is west of this
			xVel -= 0.002;
		} else {
			// nada
		}
		
		if (yDiff > 0) { // if m is south of this
			yVel += 0.002;
		} else if (yDiff < 0) { // if m is north of this
			yVel -= 0.002;
		} else {
			// nada
		}
	}
	
	public Item clone() {
		return new Item(bounds.x, bounds.y, itemType, stackSize, durability, map);
	}
	
	/*
	 * drawing methods
	 */
	
	protected void drawEntity(Graphics g) {
		g.drawImage(itemImages[itemType - 1], 
					UrfQuest.panel.gameToWindowX(bounds.getX()), 
					UrfQuest.panel.gameToWindowY(bounds.getY()), 
					null);
	}

	public void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					 (int) UrfQuest.panel.gameToWindowX(bounds.getX()),
					 (int) UrfQuest.panel.gameToWindowY(bounds.getY()));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 (int) UrfQuest.panel.gameToWindowX(bounds.getX()),
				 (int) UrfQuest.panel.gameToWindowY(bounds.getY()) + 10);
	};
	
	/*
	 * Getters and setters
	 */
	
	public BufferedImage getPic() {
		return itemImages[itemType - 1];
	}
	
	public boolean isConsumable() {
		return itemBooleanProperties[itemType - 1][0];
	}
	
	public int getMaxCooldown() {
		return itemIntProperties[itemType - 1][0];
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
		return itemIntProperties[itemType - 1][1];
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
		return itemIntProperties[itemType - 1][2];
	}
	
	public int getType() {
		return itemType;
	}
	
	public void resetDropTimeout() {
		dropTimeout = 500;
	}
	
	public boolean isPickupable() {
		return (dropTimeout == 0);
	}
}