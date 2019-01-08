package entities.items;

public class Gem extends Item {

	public Gem(double x, double y) {
		super(x, y, gemPic);
		type = "gem";
		isStackable = true;
	}

	@Override
	public void update() {
		// nothing here
	}

}