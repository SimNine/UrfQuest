package entities.items;

public class Cheese extends Item {

	public Cheese(double x, double y) {
		super(x, y, cheesePic);
		type = "cheese";
		isStackable = true;
	}

	@Override
	public void update() {
		// nothing here
	}

}