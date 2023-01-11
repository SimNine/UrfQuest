package xyz.urffer.urfquest.server.entities.items;

import java.awt.geom.Rectangle2D;

import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Chicken;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.entities.projectiles.Bullet;
import xyz.urffer.urfquest.server.entities.projectiles.Rocket;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitItem;
import xyz.urffer.urfquest.shared.protocol.types.ItemType;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class ItemStack extends Entity {
		
	private ItemType itemType;
	private int cooldown;
	private int durability;
	private int stackSize;
	
	private int dropTimeout = 500;
	
	public ItemStack(Server srv, ItemType type) {
		this(srv, type, -1);
	}
	
	public ItemStack(Server srv, ItemType type, int durability) {
		this(srv, type, 1, durability);
	}
	
	public ItemStack(Server srv, ItemType type, int stackSize, int durability) {
		super(srv);
		
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
		
		MessageInitItem mii = new MessageInitItem();
		mii.entityID = this.id;
		mii.itemType = this.itemType;
		mii.durability = this.durability;
		mii.stacksize = this.stackSize;
		srv.sendMessageToAllClients(mii);
		
		bounds = new Rectangle2D.Double(0, 0, 1, 1);
	}

	/*
	 * Manipulation methods
	 */
	
	public boolean canUse(int mobID) {
		// Don't use if not cooled down
		if (cooldown > 0) {
			return false;
		}
		
		// Get mob to try using this item on
		Mob m = (Mob)this.server.getState().getEntity(mobID);
		
		// Usability depends on type of item
		switch (this.itemType) {
			case EMPTY_ITEM: {
				throw new IllegalStateException("Tried to use an empty item. Should never happen");
			}
			case ASTRAL_RUNE: {
				return (m.getMana() > 50.0);
			}
			case COSMIC_RUNE: {
				return (m.getMana() > 5.0);
			}
			case LAW_RUNE: {
				return (m.getMana() > 30.0);
			}
			case CHICKEN_LEG: {
				return (m.getFullness() < 95.0);
			}
			case CHEESE: {
				return (m.getFullness() < 95.0);
			}
			case BONE:
				return false;
			case GEM:
				return false;
			case LOG:
				return false;
			case STONE:
				return false;
			case MIC: {
				return (m.getMana() > 30.0);
			}
			case KEY: {
				return false;
			}
			case GRENADE_ITEM: {
				return true;
			}
			case PISTOL: {
				return true;
			}
			case RPG: {
				return true;
			}
			case SHOTGUN: {
				return true;
			}
			case SMG: {
				return true;
			}
			case PICKAXE: {
				Tile tile = m.tileAtDistance(1.0);
				if (tile.objectType == ObjectType.BOULDER) {
					return true;
				} else if (tile.objectType == ObjectType.COPPER_ORE) {		
					return true;
				} else if (tile.objectType == ObjectType.IRON_ORE) {			
					return true;
				} else {
					return false;
				}
			}
			case HATCHET: {
				Tile tileAtDistance = m.tileAtDistance(1.0);
				if (tileAtDistance.objectType == ObjectType.TREE) {
					return true;
				} else {
					return false;
				}
			}
			case SHOVEL: {
				if (m.tileAtDistance(0).tileType == TileType.GRASS) {
					return true;
				} else {
					return false;
				}
			}
			case IRON_ORE:
				return false;
			case COPPER_ORE:
				return false;
			default:
				throw new IllegalArgumentException("something fucked up - nonexistent item ID");
		}
	}
	
	public void use(int mobID) {
		
		// Get mob to use this item by
		Mob m = (Mob)this.server.getState().getEntity(mobID);
		
		switch (itemType) {
		case EMPTY_ITEM: {
			throw new IllegalStateException("Tried to use an empty item. Should never happen");
		}
		case ASTRAL_RUNE: {
			cooldown = getMaxCooldown();
			
			m.incrementMana(-50.0);
			for (int i = 0; i < 180; i++) {
				Bullet b = new Bullet(this.server, m.id);
				b.setPos(m.getCenter(), m.getMapID());
				b.setMovementVector(Math.toRadians(i*2), b.getDefaultVelocity());
			}
			break;
		}
		case COSMIC_RUNE: {
			cooldown = getMaxCooldown();
			
			m.incrementMana(-5.0);
			
			Chicken newChicken = new Chicken(this.server);
			newChicken.setPos(m.getPos(), this.mapID);
			break;
		}
		case LAW_RUNE: {
			cooldown = getMaxCooldown();
			
			m.incrementMana(-30.0);
			PairInt home = m.getMap().getHomeCoords();
			m.setPos(home.toDouble());
			break;
		}
		case CHICKEN_LEG: {
			cooldown = getMaxCooldown();
			
			// TODO: consume
		}
		case CHEESE: {
			cooldown = getMaxCooldown();
			
			// TODO: consume
		}
		case BONE:
			break;
		case GEM:
			break;
		case LOG:
			break;
		case STONE:
			break;
		case MIC: {
			cooldown = getMaxCooldown();
			
			for (int i = 0; i < 20; i++) {
//				map.addProjectile(new Explosion(server, m.getMap(), this.getCenter(), this));
			}
			break;
		}
		case KEY: {
			break;
		}
		case GRENADE_ITEM: {
			cooldown = getMaxCooldown();
			
//			m.getMap().addProjectile(new GrenadeProjectile(server, m.getMap(), m.getCenter(), m));
			break;
		}
		case PISTOL: {
//			cooldown = getMaxCooldown();
			
			double dir = m.getDirection() + (server.randomDouble()/4 - 0.125);
			Bullet b = new Bullet(this.server, m.id);
			b.setPos(m.getCenter(), m.getMapID());
			b.setMovementVector(dir, b.getDefaultVelocity());
			break;
		}
		case RPG: {
			cooldown = getMaxCooldown();
			
			double dir = m.getDirection();
			Rocket r = new Rocket(this.server, m.id);
			r.setPos(m.getCenter(), m.getMapID());
			r.setMovementVector(dir, r.getDefaultVelocity());
			break;
		}
		case SHOTGUN: {
			cooldown = getMaxCooldown();
			
//			PairDouble pos = m.getCenter();
//			int numShots = 15 + (int)(server.randomDouble()*5);
//			for (int i = 0; i < numShots; i++) {
//				double dir = m.getDirection() + (int)((server.randomDouble() - 0.5)*20);
//				m.getMap().addProjectile(new Bullet(server, m.getMap(), pos, dir, server.randomDouble()*0.03 + 0.07, m));
//			}
			break;
		}
		case SMG: {
			cooldown = getMaxCooldown();
			
//			PairDouble pos = m.getCenter();
//			double dir = m.getDirection() + (server.randomDouble() - 0.5)*10;
//			m.getMap().addProjectile(new Bullet(server, m.getMap(), pos, dir, server.randomDouble()*0.03 + 0.07, m));
			break;
		}
		case PICKAXE: {
			Map map = m.getMap();
			PairInt coords = m.tileCoordsAtDistance(1.0);
			Tile tile = m.tileAtDistance(1.0);
			if (tile.objectType == ObjectType.BOULDER) {
				if (tile.tileType == TileType.GRASS) {
					map.setTileAt(coords, new Tile(TileType.GRASS));
				} else if (tile.tileType == TileType.WATER) {
					map.setTileAt(coords, new Tile(TileType.WATER));
				} else if (tile.tileType == TileType.SAND) {
					map.setTileAt(coords, new Tile(TileType.SAND));
				} else if (tile.tileType == TileType.DIRT) {
					map.setTileAt(coords, new Tile(TileType.DIRT));
					double rand = server.randomDouble();
					ItemStack item;
					if (rand > .95) {
						item = new ItemStack(this.server, ItemType.LAW_RUNE);
					} else if (rand > .90) {
						item = new ItemStack(this.server, ItemType.COSMIC_RUNE);
					} else if (rand > .85) {
						item = new ItemStack(this.server, ItemType.ASTRAL_RUNE);
					} else if (rand > .82) {
						item = new ItemStack(this.server, ItemType.SHOTGUN);
					} else if (rand > .79) {
						item = new ItemStack(this.server, ItemType.SMG);
					} else if (rand > .75) {
						item = new ItemStack(this.server, ItemType.GRENADE_ITEM);
					} else {
						item = new ItemStack(this.server, ItemType.STONE);
					}
					item.setPos(coords.toDouble(), this.mapID);
				}
				ItemStack stoneStack = new ItemStack(this.server, ItemType.STONE);
				stoneStack.setPos(coords.toDouble(), this.mapID);
				cooldown = getMaxCooldown();
			} else if (tile.objectType == ObjectType.COPPER_ORE) {
				ItemStack item = new ItemStack(this.server, ItemType.COPPER_ORE);
				item.setPos(coords.toDouble(), this.mapID);
				map.setTileAt(coords, new Tile(TileType.DIRT));
				cooldown = getMaxCooldown();
			} else if (tile.objectType == ObjectType.IRON_ORE) {
				ItemStack item = new ItemStack(this.server, ItemType.IRON_ORE);
				item.setPos(coords.toDouble(), this.mapID);
				map.setTileAt(coords, new Tile(TileType.DIRT));
				cooldown = getMaxCooldown();
			}
			break;
		}
		case HATCHET: {
			Tile tileAtDistance = m.tileAtDistance(1.0);
			if (tileAtDistance.objectType == ObjectType.TREE) {
				PairInt coords = m.tileCoordsAtDistance(1.0);
				m.getMap().setTileAt(coords, new Tile(tileAtDistance.tileType, ObjectType.VOID));
				ItemStack item = new ItemStack(this.server, ItemType.LOG);
				item.setPos(coords.toDouble(), this.mapID);
				
				cooldown = getMaxCooldown();
			}
			break;
		}
		case SHOVEL: {
			if (m.tileAtDistance(0).tileType == TileType.GRASS) {
				PairInt coords = m.tileCoordsAtDistance(0);
				if (server.randomDouble() > .05) {
					m.getMap().setTileAt(coords, new Tile(TileType.DIRT));
				} else {
					m.getMap().setTileAt(coords, new Tile(TileType.DIRT, ObjectType.HOLE));
					
					//int caveSize = 400;
					
					// create new map
					Map newCaveMap = new Map(server, Map.CAVE_MAP);
					PairInt caveMapHome = newCaveMap.getHomeCoords();
					newCaveMap.setTileAt(caveMapHome, new Tile(TileType.DIRT, ObjectType.HOLE));
					
					//generate and add new link
					// TODO: fix
//					MapLink newLink = new MapLink(m.getMap(), coords[0], coords[1], newCaveMap, xHome, yHome);
//					m.getMap().setActiveTile(coords[0], coords[1], newLink);
//					newCaveMap.setActiveTile(xHome, yHome, newLink);
					
					//debug
					if (this.server.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
						this.server.getLogger().debug("soruce: " + coords);
						this.server.getLogger().debug("exit: " + caveMapHome);
					}
				}
				
				cooldown = getMaxCooldown();
			}
			break;
		}
		case IRON_ORE:
			break;
		case COPPER_ORE:
			break;
		default:
			throw new IllegalArgumentException("something fucked up - nonexistent item ID");
		}
	}
	
	public void tick() {
		incrementPos(this.movementVector);
		
		if (getMaxCooldown() > -1) {
			if (cooldown > 0) {
				cooldown--;
			}
		}
		
		if (movementVector.magnitude != 0) {
			movementVector.magnitude -= movementVector.magnitude*0.02;
		}
		
		if (dropTimeout > 0) {
			dropTimeout--;
		}
	}
	
	public void accelerateTowards(Mob m) {
		// TODO: fix
//		double xDiff = (m.getCenter()[0] - this.getCenter()[0]);
//		double yDiff = (m.getCenter()[1] - this.getCenter()[1]);
//		
//		if (xDiff > 0) { // if m is east of this
//			xVel += 0.002;
//		} else if (xDiff < 0) { // if m is west of this
//			xVel -= 0.002;
//		} else {
//			// nada
//		}
//		
//		if (yDiff > 0) { // if m is south of this
//			yVel += 0.002;
//		} else if (yDiff < 0) { // if m is north of this
//			yVel -= 0.002;
//		} else {
//			// nada
//		}
	}
	
	public ItemStack clone() {
		return new ItemStack(this.server, this.itemType, durability);
	}
	
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
		return this.itemType.getMaxStacksize();
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
}