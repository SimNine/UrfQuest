package xyz.urffer.urfquest.shared.protocol.types;

public enum ProjectileType {
	NONE(0),
	
	BULLET(1),
	GRENADE(2),
	ROCKET(3),
	EXPLOSION(4);
	
	public int value;
	
	ProjectileType(int value) {
		this.value = value;
	}
    
    
    
    public String toString() {
    	return Integer.toString(this.value);
    }
}
