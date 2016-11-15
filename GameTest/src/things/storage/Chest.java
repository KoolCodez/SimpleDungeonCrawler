package things.storage;

import java.awt.Image;

import misc.Images;
import misc.SDC;

public class Chest extends Storage {
	static int w = (int) (100*SDC.SCALE_FACTOR);
	static int l = (int) (70*SDC.SCALE_FACTOR);
	
	public Chest(double x, double y, int rarity) {
		super();
		this.rarity = rarity;
		super.setSize(w, l);
		super.setLocation(x, y);
		setImage("Storage/chest.png");
		//Images.loadImage("Storage/Chest.png", w / SDC.SCALE_FACTOR, l / SDC.SCALE_FACTOR)
		setCapacity(12);
	}
}
