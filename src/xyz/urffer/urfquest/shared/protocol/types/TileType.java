package xyz.urffer.urfquest.shared.protocol.types;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.shared.ImageUtils;

public enum TileType {
	VOID(0),
	BEDROCK(1),
	GRASS(2),
	MANA_PAD(3),
	HEALTH_PAD(4),
	HURT_PAD(5),
	SPEED_PAD(6),
	WATER(7),
	SAND(8),
	DIRT(9),
	FLOOR_WOOD(10);
	
	public int value;
	
	private static String tileRoot = QuestPanel.assetPath + "/tiles/";
	
	private static HashMap<Integer, TileType> map = new HashMap<>();
	
	private static final int PROPERTY_WALKABLE = 0;
	private static final int PROPERTY_PENETRABLE = 1;
	private static final int NUM_BOOLEAN_PROPERTIES = 2;
	private static final boolean[][] BOOLEAN_PROPERTIES = new boolean[TileType.values().length][NUM_BOOLEAN_PROPERTIES];
	static {
		BOOLEAN_PROPERTIES[VOID.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[BEDROCK.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[GRASS.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[MANA_PAD.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[HEALTH_PAD.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[HURT_PAD.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[SPEED_PAD.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[WATER.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[SAND.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[DIRT.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[FLOOR_WOOD.value][PROPERTY_WALKABLE] = true;

		BOOLEAN_PROPERTIES[VOID.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[BEDROCK.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[GRASS.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[MANA_PAD.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[HEALTH_PAD.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[HURT_PAD.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[SPEED_PAD.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[WATER.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[SAND.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[DIRT.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[FLOOR_WOOD.value][PROPERTY_PENETRABLE] = true;
	}

	private static final int NUM_INT_PROPERTIES = 1;
	private static final int PROPERTY_COLOR = 0;
	private static final int[][] INT_PROPERTIES = new int[TileType.values().length][NUM_INT_PROPERTIES];
	static {
		INT_PROPERTIES[VOID.value][PROPERTY_COLOR] = Color.BLACK.getRGB();
		INT_PROPERTIES[BEDROCK.value][PROPERTY_COLOR] = Color.DARK_GRAY.getRGB();
		INT_PROPERTIES[GRASS.value][PROPERTY_COLOR] = Color.GREEN.getRGB();
		INT_PROPERTIES[MANA_PAD.value][PROPERTY_COLOR] = Color.BLUE.getRGB();
		INT_PROPERTIES[HEALTH_PAD.value][PROPERTY_COLOR] = Color.RED.getRGB();
		INT_PROPERTIES[HURT_PAD.value][PROPERTY_COLOR] = Color.DARK_GRAY.getRGB();
		INT_PROPERTIES[SPEED_PAD.value][PROPERTY_COLOR] = Color.GREEN.brighter().getRGB();
		INT_PROPERTIES[WATER.value][PROPERTY_COLOR] = Color.BLUE.getRGB();
		INT_PROPERTIES[SAND.value][PROPERTY_COLOR] = Color.YELLOW.brighter().getRGB();
		INT_PROPERTIES[DIRT.value][PROPERTY_COLOR] = new Color(179, 136, 37).getRGB();
		INT_PROPERTIES[FLOOR_WOOD.value][PROPERTY_COLOR] = new Color(179, 136, 37).getRGB();
	}

	private static BufferedImage nullImage = new BufferedImage(
			QuestPanel.TILE_WIDTH, 
			QuestPanel.TILE_WIDTH, 
			BufferedImage.TYPE_3BYTE_BGR);
	private static BufferedImage[] TILE_IMAGES = new BufferedImage[TileType.values().length];
	static {
		TILE_IMAGES[TileType.VOID.value] = nullImage;
		TILE_IMAGES[TileType.DIRT.value] = ImageUtils.loadImage(tileRoot + "dirt.png");
		TILE_IMAGES[TileType.BEDROCK.value] = ImageUtils.loadImage(tileRoot + "bedrock.png");
		TILE_IMAGES[TileType.GRASS.value] = ImageUtils.loadImage(tileRoot + "grass.png");
		TILE_IMAGES[TileType.MANA_PAD.value] = ImageUtils.loadImage(tileRoot + "manaPad.png");
		TILE_IMAGES[TileType.HEALTH_PAD.value] = ImageUtils.loadImage(tileRoot + "healthPad.png");
		TILE_IMAGES[TileType.HURT_PAD.value] = ImageUtils.loadImage(tileRoot + "hurtPad.png");
		TILE_IMAGES[TileType.SPEED_PAD.value] = ImageUtils.loadImage(tileRoot + "speedPad.png");
		TILE_IMAGES[TileType.WATER.value] = ImageUtils.loadImage(tileRoot + "water.png");
		TILE_IMAGES[TileType.SAND.value] = ImageUtils.loadImage(tileRoot + "sand.png");
		TILE_IMAGES[TileType.FLOOR_WOOD.value] = ImageUtils.loadImage(tileRoot + "floor_wood.png");
	}
	
	TileType(int value) {
		this.value = value;
	}

    static {
        for (TileType startupMode : TileType.values()) {
            map.put(startupMode.value, startupMode);
        }
    }

    public static TileType valueOf(int value) {
        return map.get(value);
    }
    
    
    
    public boolean getWalkable() {
    	return BOOLEAN_PROPERTIES[this.value][PROPERTY_WALKABLE];
    }
    
    public boolean getPenetrable() {
    	return BOOLEAN_PROPERTIES[this.value][PROPERTY_PENETRABLE];
    }
    
    public int getColor() {
    	return INT_PROPERTIES[this.value][PROPERTY_COLOR];
    }
    
    public BufferedImage getImage() {
    	return TILE_IMAGES[this.value];
    }
    
    
    
    public String toString() {
    	return Integer.toString(this.value);
    }
}
