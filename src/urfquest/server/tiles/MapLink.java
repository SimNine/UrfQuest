package urfquest.server.tiles;

import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;

public class MapLink extends ActiveTile {
	private Map a;
	private int ax;
	private int ay;
	private Map b;
	private int bx;
	private int by;

	public MapLink(Map a, int ax, int ay, Map b, int bx, int by) {
		this.a = a;
		this.ax = ax;
		this.ay = ay;
		this.b = b;
		this.bx = bx;
		this.by = by;
	}
	
	public Map getOtherMap(Map m) {
		if (m == a) {
			return b;
		} else if (m == b) {
			return a;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public int[] getOtherCoordinates(Map m) {
		if (m == a) {
			return new int[] {bx, by};
		} else if (m == b) {
			return new int[] {ax, ay};
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void use(Mob m) {
		int[] coords = getOtherCoordinates(m.getMap());
		m.setPos(coords[0], coords[1]);
		
		m.setMap(getOtherMap(m.getMap()));
	}
}