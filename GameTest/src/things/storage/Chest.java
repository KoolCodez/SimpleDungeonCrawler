package things.storage;

import java.awt.Image;

import misc.Images;
import misc.SDC;
import things.items.Stick;

public class Chest extends Storage {
	static int w = (int) (100*SDC.SCALE_FACTOR);
	static int l = (int) (70*SDC.SCALE_FACTOR);
	
	public Chest(double x, double y, int rarity) {
		super();
		this.rarity = rarity;
		super.setSize(w, l);
		super.setLocation(x, y);
		setImage(loadImage());
		setCapacity(12);
	}
	
	private static Image loadImage() {
		return Images.loadImage("Items/Chest.png", w / SDC.SCALE_FACTOR, l / SDC.SCALE_FACTOR);
	}
}
