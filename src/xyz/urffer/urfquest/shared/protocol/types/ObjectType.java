package xyz.urffer.urfquest.shared.protocol.types;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.shared.ImageUtils;

public enum ObjectType {
	VOID(0),
	FLOWERS(1),
	TREE(2),
	BOULDER(3),
	HOLE(4),
	STONE(5),
	IRON_ORE(6),
	COPPER_ORE(7),
	CHEST(8),
	WALL_STONE(9);
	
	public int value;
	
	private static String tileRoot = QuestPanel.assetPath + "/tiles/";
	
	private static HashMap<Integer, ObjectType> map = new HashMap<>();
	
	private static final int PROPERTY_WALKABLE = 0;
	private static final int PROPERTY_PENETRABLE = 1;
	private static final int NUM_BOOLEAN_PROPERTIES = 2;
	private static final boolean[][] BOOLEAN_PROPERTIES = new boolean[ObjectType.values().length][NUM_BOOLEAN_PROPERTIES];
	static {
		BOOLEAN_PROPERTIES[VOID.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[FLOWERS.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TREE.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[BOULDER.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[HOLE.value][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[STONE.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[IRON_ORE.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[COPPER_ORE.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[CHEST.value][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[WALL_STONE.value][PROPERTY_WALKABLE] = false;

		BOOLEAN_PROPERTIES[VOID.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[FLOWERS.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TREE.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[BOULDER.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[HOLE.value][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[STONE.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[IRON_ORE.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[COPPER_ORE.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[CHEST.value][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[WALL_STONE.value][PROPERTY_PENETRABLE] = false;
	}

	private static final int NUM_INT_PROPERTIES = 1;
	private static final int PROPERTY_COLOR = 0;
	private static final int[][] INT_PROPERTIES = new int[ObjectType.values().length][NUM_INT_PROPERTIES];
	static {
		INT_PROPERTIES[VOID.value][PROPERTY_COLOR] = Color.BLACK.getRGB();
		INT_PROPERTIES[FLOWERS.value][PROPERTY_COLOR] = Color.CYAN.getRGB();
		INT_PROPERTIES[TREE.value][PROPERTY_COLOR] = Color.GREEN.darker().getRGB();
		INT_PROPERTIES[BOULDER.value][PROPERTY_COLOR] = Color.DARK_GRAY.getRGB();
		INT_PROPERTIES[HOLE.value][PROPERTY_COLOR] = Color.BLACK.getRGB();
		INT_PROPERTIES[STONE.value][PROPERTY_COLOR] = Color.DARK_GRAY.getRGB();
		INT_PROPERTIES[IRON_ORE.value][PROPERTY_COLOR] = Color.GRAY.darker().getRGB();
		INT_PROPERTIES[COPPER_ORE.value][PROPERTY_COLOR] = Color.ORANGE.getRGB();
		INT_PROPERTIES[CHEST.value][PROPERTY_COLOR] = Color.ORANGE.darker().getRGB();
		INT_PROPERTIES[WALL_STONE.value][PROPERTY_COLOR] = Color.DARK_GRAY.getRGB();
	}

	private static BufferedImage nullImage = new BufferedImage(
			QuestPanel.TILE_WIDTH, 
			QuestPanel.TILE_WIDTH, 
			BufferedImage.TYPE_3BYTE_BGR);
	private static BufferedImage[] OBJECT_IMAGES = new BufferedImage[TileType.values().length];
	static {
		OBJECT_IMAGES[ObjectType.VOID.value] = nullImage;
		OBJECT_IMAGES[ObjectType.FLOWERS.value] = ImageUtils.loadImage(tileRoot + "grass_flowers.png");
		OBJECT_IMAGES[ObjectType.TREE.value] = ImageUtils.loadImage(tileRoot + "tree_scaled.png");
		OBJECT_IMAGES[ObjectType.BOULDER.value] = ImageUtils.loadImage(tileRoot + "boulder_scaled_30px.png");
		OBJECT_IMAGES[ObjectType.HOLE.value] = ImageUtils.loadImage(tileRoot + "hole_scaled_30px.png");
		OBJECT_IMAGES[ObjectType.STONE.value] = ImageUtils.loadImage(tileRoot + "stone.png");
		OBJECT_IMAGES[ObjectType.IRON_ORE.value] = ImageUtils.loadImage(tileRoot + "ironore_scaled_30px.png");
		OBJECT_IMAGES[ObjectType.COPPER_ORE.value] = ImageUtils.loadImage(tileRoot + "copperore_scaled_30px.png");
		OBJECT_IMAGES[ObjectType.CHEST.value] = ImageUtils.loadImage(tileRoot + "chest_scaled_30px.png");
		OBJECT_IMAGES[ObjectType.WALL_STONE.value] = ImageUtils.loadImage(tileRoot + "wall_stone.png");
	}
	
	ObjectType(int value) {
		this.value = value;
	}

    static {
        for (ObjectType startupMode : ObjectType.values()) {
            map.put(startupMode.value, startupMode);
        }
    }

    public static ObjectType valueOf(int value) {
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
    	return OBJECT_IMAGES[this.value];
    }
    
    
    
    public String toString() {
    	return Integer.toString(this.value);
    }
}
