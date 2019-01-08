package entities.items;

public class Pistol extends Item {

	public Pistol(double x, double y) {
		super(x, y, gunPic);
		type = "pistol";
		isStackable = false;
	}

	@Override
	public void update() {
		// nothing here
	}

}