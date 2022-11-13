package xyz.urffer.urfquest.shared.protocol.types;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.shared.ImageUtils;

public enum ItemType {
	EMPTY_ITEM(0),
	ASTRAL_RUNE(1),
	COSMIC_RUNE(2),
	LAW_RUNE(3),
	CHICKEN_LEG(4),
	CHEESE(5),
	BONE(6),
	GEM(7),
	LOG(8),
	STONE(9),
	MIC(10),
	KEY(11),
	GRENADE_ITEM(12),
	PISTOL(13),
	RPG(14),
	SHOTGUN(15),
	SMG(16),
	PICKAXE(17),
	HATCHET(18),
	SHOVEL(19),
	IRON_ORE(20),
	COPPER_ORE(21);
	
	public int value;
	
	private static final String assetPath = QuestPanel.assetPath + "/items/";
	
	private static final int PROPERTY_CONSUMABLE = 0;
	private static final int NUM_BOOLEAN_PROPERTIES = 1;
	private static final boolean[][] BOOLEAN_PROPERTIES = new boolean[ItemType.values().length][NUM_BOOLEAN_PROPERTIES];
	static {
		BOOLEAN_PROPERTIES[EMPTY_ITEM.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[ASTRAL_RUNE.value][PROPERTY_CONSUMABLE] = true;
		BOOLEAN_PROPERTIES[COSMIC_RUNE.value][PROPERTY_CONSUMABLE] = true;
		BOOLEAN_PROPERTIES[LAW_RUNE.value][PROPERTY_CONSUMABLE] = true;
		BOOLEAN_PROPERTIES[CHICKEN_LEG.value][PROPERTY_CONSUMABLE] = true;
		BOOLEAN_PROPERTIES[CHEESE.value][PROPERTY_CONSUMABLE] = true;
		BOOLEAN_PROPERTIES[BONE.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[GEM.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[LOG.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[STONE.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[MIC.value][PROPERTY_CONSUMABLE] = true;
		BOOLEAN_PROPERTIES[KEY.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[GRENADE_ITEM.value][PROPERTY_CONSUMABLE] = true;
		BOOLEAN_PROPERTIES[PISTOL.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[RPG.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[SHOTGUN.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[SMG.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[PICKAXE.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[HATCHET.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[SHOVEL.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[IRON_ORE.value][PROPERTY_CONSUMABLE] = false;
		BOOLEAN_PROPERTIES[COPPER_ORE.value][PROPERTY_CONSUMABLE] = false;
	}
	
	private static HashMap<Integer, ItemType> map = new HashMap<>();

	private static final int PROPERTY_MAX_COOLDOWN = 0;
	private static final int PROPERTY_MAX_DURABILITY = 1;
	private static final int PROPERTY_MAX_STACKSIZE = 2;
	private static final int NUM_INT_PROPERTIES = 3;
	private static final int[][] INT_PROPERTIES = new int[ItemType.values().length][NUM_INT_PROPERTIES];
	static {
		INT_PROPERTIES[EMPTY_ITEM.value][PROPERTY_MAX_COOLDOWN] = -1;
		INT_PROPERTIES[ASTRAL_RUNE.value][PROPERTY_MAX_COOLDOWN] = 1000;
		INT_PROPERTIES[COSMIC_RUNE.value][PROPERTY_MAX_COOLDOWN] = 1000;
		INT_PROPERTIES[LAW_RUNE.value][PROPERTY_MAX_COOLDOWN] = 1000;
		INT_PROPERTIES[CHICKEN_LEG.value][PROPERTY_MAX_COOLDOWN] = 50;
		INT_PROPERTIES[CHEESE.value][PROPERTY_MAX_COOLDOWN] = 50;
		INT_PROPERTIES[BONE.value][PROPERTY_MAX_COOLDOWN] = -1;
		INT_PROPERTIES[GEM.value][PROPERTY_MAX_COOLDOWN] = -1;
		INT_PROPERTIES[LOG.value][PROPERTY_MAX_COOLDOWN] = -1;
		INT_PROPERTIES[STONE.value][PROPERTY_MAX_COOLDOWN] = -1;
		INT_PROPERTIES[MIC.value][PROPERTY_MAX_COOLDOWN] = 1000;
		INT_PROPERTIES[KEY.value][PROPERTY_MAX_COOLDOWN] = -1;
		INT_PROPERTIES[GRENADE_ITEM.value][PROPERTY_MAX_COOLDOWN] = 100;
		INT_PROPERTIES[PISTOL.value][PROPERTY_MAX_COOLDOWN] = 100;
		INT_PROPERTIES[RPG.value][PROPERTY_MAX_COOLDOWN] = 400;
		INT_PROPERTIES[SHOTGUN.value][PROPERTY_MAX_COOLDOWN] = 400;
		INT_PROPERTIES[SMG.value][PROPERTY_MAX_COOLDOWN] = 10;
		INT_PROPERTIES[PICKAXE.value][PROPERTY_MAX_COOLDOWN] = 100;
		INT_PROPERTIES[HATCHET.value][PROPERTY_MAX_COOLDOWN] = 100;
		INT_PROPERTIES[SHOVEL.value][PROPERTY_MAX_COOLDOWN] = 100;
		INT_PROPERTIES[IRON_ORE.value][PROPERTY_MAX_COOLDOWN] = -1;
		INT_PROPERTIES[COPPER_ORE.value][PROPERTY_MAX_COOLDOWN] = -1;

		INT_PROPERTIES[EMPTY_ITEM.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[ASTRAL_RUNE.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[COSMIC_RUNE.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[LAW_RUNE.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[CHICKEN_LEG.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[CHEESE.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[BONE.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[GEM.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[LOG.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[STONE.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[MIC.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[KEY.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[GRENADE_ITEM.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[PISTOL.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[RPG.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[SHOTGUN.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[SMG.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[PICKAXE.value][PROPERTY_MAX_DURABILITY] = 100;
		INT_PROPERTIES[HATCHET.value][PROPERTY_MAX_DURABILITY] = 100;
		INT_PROPERTIES[SHOVEL.value][PROPERTY_MAX_DURABILITY] = 100;
		INT_PROPERTIES[IRON_ORE.value][PROPERTY_MAX_DURABILITY] = -1;
		INT_PROPERTIES[COPPER_ORE.value][PROPERTY_MAX_DURABILITY] = -1;

		INT_PROPERTIES[EMPTY_ITEM.value][PROPERTY_MAX_STACKSIZE] = -1;
		INT_PROPERTIES[ASTRAL_RUNE.value][PROPERTY_MAX_STACKSIZE] = 10;
		INT_PROPERTIES[COSMIC_RUNE.value][PROPERTY_MAX_STACKSIZE] = 10;
		INT_PROPERTIES[LAW_RUNE.value][PROPERTY_MAX_STACKSIZE] = 10;
		INT_PROPERTIES[CHICKEN_LEG.value][PROPERTY_MAX_STACKSIZE] = 100;
		INT_PROPERTIES[CHEESE.value][PROPERTY_MAX_STACKSIZE] = 100;
		INT_PROPERTIES[BONE.value][PROPERTY_MAX_STACKSIZE] = 100;
		INT_PROPERTIES[GEM.value][PROPERTY_MAX_STACKSIZE] = 100;
		INT_PROPERTIES[LOG.value][PROPERTY_MAX_STACKSIZE] = 100;
		INT_PROPERTIES[STONE.value][PROPERTY_MAX_STACKSIZE] = 100;
		INT_PROPERTIES[MIC.value][PROPERTY_MAX_STACKSIZE] = 10;
		INT_PROPERTIES[KEY.value][PROPERTY_MAX_STACKSIZE] = 100;
		INT_PROPERTIES[GRENADE_ITEM.value][PROPERTY_MAX_STACKSIZE] = 10;
		INT_PROPERTIES[PISTOL.value][PROPERTY_MAX_STACKSIZE] = 1;
		INT_PROPERTIES[RPG.value][PROPERTY_MAX_STACKSIZE] = 1;
		INT_PROPERTIES[SHOTGUN.value][PROPERTY_MAX_STACKSIZE] = 1;
		INT_PROPERTIES[SMG.value][PROPERTY_MAX_STACKSIZE] = 1;
		INT_PROPERTIES[PICKAXE.value][PROPERTY_MAX_STACKSIZE] = 1;
		INT_PROPERTIES[HATCHET.value][PROPERTY_MAX_STACKSIZE] = 1;
		INT_PROPERTIES[SHOVEL.value][PROPERTY_MAX_STACKSIZE] = 1;
		INT_PROPERTIES[IRON_ORE.value][PROPERTY_MAX_STACKSIZE] = 100;
		INT_PROPERTIES[COPPER_ORE.value][PROPERTY_MAX_STACKSIZE] = 100;
	}

	private static final BufferedImage nullImage = new BufferedImage(
			QuestPanel.TILE_WIDTH, 
			QuestPanel.TILE_WIDTH, 
			BufferedImage.TYPE_3BYTE_BGR);
	private static final BufferedImage[] ITEM_IMAGES = new BufferedImage[ItemType.values().length];
	static {
		ITEM_IMAGES[EMPTY_ITEM.value] = nullImage;
		ITEM_IMAGES[ASTRAL_RUNE.value] = ImageUtils.loadImage(assetPath + "astralRune_scaled_30px.png");
		ITEM_IMAGES[COSMIC_RUNE.value] = ImageUtils.loadImage(assetPath + "cosmicRune_scaled_30px.png");
		ITEM_IMAGES[LAW_RUNE.value] = ImageUtils.loadImage(assetPath + "lawRune_scaled_30px.png");
		ITEM_IMAGES[CHICKEN_LEG.value] = ImageUtils.loadImage(assetPath + "chickenLeg_scaled_30px.png");
		ITEM_IMAGES[CHEESE.value] = ImageUtils.loadImage(assetPath + "cheese_scaled_30px.png");
		ITEM_IMAGES[BONE.value] = ImageUtils.loadImage(assetPath + "bone_scaled_30px.png");
		ITEM_IMAGES[GEM.value] = ImageUtils.loadImage(assetPath + "pink_gem_scaled_30px.png");
		ITEM_IMAGES[LOG.value] = ImageUtils.loadImage(assetPath + "log_scaled_30px.png");
		ITEM_IMAGES[STONE.value] = ImageUtils.loadImage(assetPath + "stoneitem_scaled_30px.png");
		ITEM_IMAGES[MIC.value] = ImageUtils.loadImage(assetPath + "mic_scaled_30px.png");
		ITEM_IMAGES[KEY.value] = ImageUtils.loadImage(assetPath + "key_scaled_30px.png");
		ITEM_IMAGES[GRENADE_ITEM.value] = ImageUtils.loadImage(assetPath + "grenade_scaled_30px.png");
		ITEM_IMAGES[PISTOL.value] = ImageUtils.loadImage(assetPath + "gun_scaled_30px.png");
		ITEM_IMAGES[RPG.value] = ImageUtils.loadImage(assetPath + "rocket_scaled_30px.png");
		ITEM_IMAGES[SHOTGUN.value] = ImageUtils.loadImage(assetPath + "shotgun_scaled_30px.png");
		ITEM_IMAGES[SMG.value] = ImageUtils.loadImage(assetPath + "smg_scaled_30px.png");
		ITEM_IMAGES[PICKAXE.value] = ImageUtils.loadImage(assetPath + "pickaxe_scaled_30px.png");
		ITEM_IMAGES[HATCHET.value] = ImageUtils.loadImage(assetPath + "hatchet_scaled_30px.png");
		ITEM_IMAGES[SHOVEL.value] = ImageUtils.loadImage(assetPath + "shovel_scaled_30px.png");
		ITEM_IMAGES[IRON_ORE.value] = ImageUtils.loadImage(assetPath + "ironore_scaled_30px.png");
		ITEM_IMAGES[COPPER_ORE.value] = ImageUtils.loadImage(assetPath + "copperore_scaled_30px.png");
	}
	
	ItemType(int value) {
		this.value = value;
	}

    static {
        for (ItemType startupMode : ItemType.values()) {
            map.put(startupMode.value, startupMode);
        }
    }

    public static ItemType valueOf(int value) {
        return map.get(value);
    }
    
    
    
    public boolean getConsumable() {
    	return BOOLEAN_PROPERTIES[this.value][PROPERTY_CONSUMABLE];
    }
    
    public int getMaxCooldown() {
    	return INT_PROPERTIES[this.value][PROPERTY_MAX_COOLDOWN];
    }
    
    public int getMaxDurability() {
    	return INT_PROPERTIES[this.value][PROPERTY_MAX_DURABILITY];
    }
    
    public int getMaxStacksize() {
    	return INT_PROPERTIES[this.value][PROPERTY_MAX_STACKSIZE];
    }
    
    public int[] getIntProperties() {
    	return INT_PROPERTIES[this.value];
    }
    
    public BufferedImage getImage() {
    	return ITEM_IMAGES[this.value];
    }
    
    
    
    public String toString() {
    	return Integer.toString(this.value);
    }
}
