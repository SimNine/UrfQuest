package urfquest.server.entities.items;

import java.awt.geom.Rectangle2D;

import urfquest.Logger;
import urfquest.server.Server;
import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Chicken;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.entities.projectiles.Bullet;
import urfquest.server.entities.projectiles.GrenadeProjectile;
import urfquest.server.entities.projectiles.Rocket;
import urfquest.server.entities.projectiles.RocketExplosion;
import urfquest.server.map.Map;
import urfquest.server.state.State;
import urfquest.server.tiles.MapLink;
import urfquest.server.tiles.Tiles;

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
	
	private Server server;
	
	private int cooldown;
	private int durability;
	private int stackSize;
	private int itemType;
	
	private double xVel = 0;
	private double yVel = 0;
	
	private int dropTimeout = 500;
	
	public Item(Server srv, State s, Map m, double x, double y, int type) {
		this(srv, s, m, x, y, type, 1, -1);
	}
	
	public Item(Server srv, State s, Map m, double x, double y, int type, int durability) {
		this(srv, s, m, x, y, type, 1, durability);
	}
	
	public Item(Server srv, State s, Map m, double x, double y, int type, int stackSize, int durability) {
		super(srv, s, m, x, y);
		this.server = srv;
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		
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
				m.getMap().addProjectile(new Bullet(this.state, this.map, pos[0], pos[1], i*2, Bullet.getDefaultVelocity(), m));
			}
			return true;
		case Item.COSMIC_RUNE:
			if (m.getMana() < 5.0) {
				return false;
			} // else
			cooldown = getMaxCooldown();
			
			m.incrementMana(-5.0);
			double[] pos3 = m.getPos();
			m.getMap().addMob(new Chicken(this.server, this.state, this.map, pos3[0], pos3[1]));
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
				map.addProjectile(new RocketExplosion(this.state, m.getMap(), bounds.x + (server.randomDouble() - 0.5)*20, bounds.y + (server.randomDouble() - 0.5)*20, this));
			}
			return true;
		case Item.KEY:
			return false;
		case Item.GRENADE_ITEM:
			cooldown = getMaxCooldown();
			
			m.getMap().addProjectile(new GrenadeProjectile(this.state, m.getMap(), m.getCenter()[0], m.getCenter()[1], m));
			return true;
		case Item.PISTOL:
			cooldown = getMaxCooldown();
			
			double[] pos1 = m.getCenter();
			int dir = m.getDirection() + (int)((server.randomDouble() - 0.5)*10);
			m.getMap().addProjectile(new Bullet(this.state, m.getMap(), pos1[0], pos1[1], dir, Bullet.getDefaultVelocity(), m));
			return true;
		case Item.RPG:
			cooldown = getMaxCooldown();
			
			double[] pos2 = m.getCenter();
			int dir1 = m.getDirection();
			m.getMap().addProjectile(new Rocket(this.state, m.getMap(), pos2[0], pos2[1], dir1, Rocket.getDefaultVelocity(), m));
			
			return true;
		case Item.SHOTGUN:
			cooldown = getMaxCooldown();
			
			double[] pos4 = m.getCenter();
			int dir4;
			int numShots = 15 + (int)(server.randomDouble()*5);
			
			for (int i = 0; i < numShots; i++) {
				dir4 = m.getDirection() + (int)((server.randomDouble() - 0.5)*20);
				m.getMap().addProjectile(new Bullet(this.state, m.getMap(), pos4[0], pos4[1], dir4, Bullet.getDefaultVelocity(), m));
			}
			
			return true;
		case Item.SMG:
			cooldown = getMaxCooldown();
			
			double[] pos5 = m.getCenter();
			int dir5 = m.getDirection() + (int)((server.randomDouble() - 0.5)*10);
			m.getMap().addProjectile(new Bullet(this.state, m.getMap(), pos5[0], pos5[1], dir5, Bullet.getDefaultVelocity(), m));
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
					double rand = server.randomDouble();
					if (rand > .95) {
						m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.LAW_RUNE));
					} else if (rand > .90) {
						m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.COSMIC_RUNE));
					} else if (rand > .85) {
						m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.ASTRAL_RUNE));
					} else if (rand > .82) {
						m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.SHOTGUN));
					} else if (rand > .79) {
						m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.SMG));
					} else if (rand > .75) {
						m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.GRENADE_ITEM));
					} else {
						m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.STONE));
					}
				}
				m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.STONE));
				cooldown = getMaxCooldown();
				return true;
			} else if (tile[0] == Tiles.STONE) {
				int[] coords = m.tileCoordsAtDistance(1.0);
				if (tile[1] == Tiles.STONE_DEF) {
					// nothing else
				} else if (tile[1] == Tiles.COPPERORE_STONE) {
					m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.COPPER_ORE));
				} else if (tile[1] == Tiles.IRONORE_STONE) {
					m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.IRON_ORE));
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
				m.getMap().addItem(new Item(this.server, this.state, this.map, coords[0], coords[1], Item.LOG));
				
				cooldown = getMaxCooldown();
				return true;
			} else {
				return false;
			}
		case Item.SHOVEL:
			if (m.tileAtDistance(0)[0] == Tiles.GRASS) {
				int[] coords = m.tileCoordsAtDistance(0);
				if (server.randomDouble() > .05) {
					m.getMap().setTileAt(coords[0], coords[1], 0);
				} else {
					m.getMap().setTileAt(coords[0], coords[1], 13);
					
					//int caveSize = 400;
					
					// create new map
					Map newCaveMap = new Map(srv, Map.CAVE_MAP);
					int xHome = newCaveMap.getHomeCoords()[0];
					int yHome = newCaveMap.getHomeCoords()[1];
					newCaveMap.setTileAt(xHome, yHome, 13);
					
					//generate and add new link
					MapLink newLink = new MapLink(m.getMap(), coords[0], coords[1], newCaveMap, xHome, yHome);
					m.getMap().setActiveTile(coords[0], coords[1], newLink);
					newCaveMap.setActiveTile(xHome, yHome, newLink);
					
					//debug
					if (this.server.getLogger().getLogLevel().compareTo(LogLevel.LOG_DEBUG) >= 0) {
						this.server.getLogger().debug("soruce: " + coords[0] + ", " + coords[1]);
						this.server.getLogger().debug("exit: " + xHome + ", " + yHome);
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
	
	public void tick() {
		incrementPos(xVel, yVel);
		
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
		return new Item(this.server, this.state, this.map, bounds.x, bounds.y, itemType, stackSize, durability);
	}
	
	/*
	 * Getters and setters
	 */
	
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