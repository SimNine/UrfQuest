package entities.items;

public class Gun extends Item {

	public Gun(double x, double y) {
		super(x, y, gunPic);
		type = "gun";
		isStackable = false;
	}

	@Override
	public void update() {
		// nothing here
	}

}