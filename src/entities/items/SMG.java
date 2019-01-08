package entities.items;

public class SMG extends Item {

	public SMG(double x, double y) {
		super(x, y, smgPic);
		type = "smg";
		isStackable = false;
	}

	@Override
	public void update() {
		// nothing here
	}

}