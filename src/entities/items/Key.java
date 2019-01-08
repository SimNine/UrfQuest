package entities.items;

public class Key extends Item {

	public Key(double x, double y) {
		super(x, y, keyPic);
		type = "key";
		isStackable = true;
	}

	@Override
	public void update() {
		// nothing here
	}

}