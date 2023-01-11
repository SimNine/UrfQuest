package xyz.urffer.urfquest.shared;

import java.io.Serializable;

import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class Tile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6985076109063602926L;

	public static final Tile VOID = new Tile(TileType.VOID, ObjectType.VOID);
	
	public TileType tileType;
	public ObjectType objectType;
	
	public Tile() {
		this.tileType = Constants.DEFAULT_CHUNK_TILE;
		this.objectType = Constants.DEFAULT_CHUNK_OBJECT;
	}
	
	public Tile(TileType tile) {
		this.tileType = tile;
		this.objectType = Constants.DEFAULT_CHUNK_OBJECT;
	}
	
	public Tile(TileType tile, ObjectType object) {
		this.tileType = tile;
		this.objectType = object;
	}
	
	
	
	/*
	 * Methods for getting tile props
	 */
	
	public boolean isWalkable() {
		return isWalkable(this);
	}
	
	public static boolean isWalkable(Tile tile) {
		return isWalkable(tile.tileType, tile.objectType);
	}
	
	public static boolean isWalkable(TileType tileType, ObjectType objectType) {
		if (objectType != ObjectType.VOID) {
			return objectType.getWalkable();
		} else {
			return tileType.getWalkable();
		}
	}
	
	
	
	public boolean isPenetrable() {
		return isPenetrable(this);
	}
	
	public static boolean isPenetrable(Tile tile) {
		return isPenetrable(tile.tileType, tile.objectType);
	}
	
	public static boolean isPenetrable(TileType tileType, ObjectType objectType) {
		if (objectType != ObjectType.VOID) {
			return objectType.getPenetrable();
		} else {
			return tileType.getPenetrable();
		}
	}
	
	
	
	public int minimapColor() {
		return minimapColor(this);
	}
	
	public static int minimapColor(Tile tile) {
		return minimapColor(tile.tileType, tile.objectType);
	}
	
	public static int minimapColor(TileType tileType, ObjectType objectType) {
		if (objectType != ObjectType.VOID) {
			return objectType.getColor();
		} else {
			return tileType.getColor();
		}
	}
	
	
	
	public String toString() {
		return tileType + "/" + objectType;
	}
	
}