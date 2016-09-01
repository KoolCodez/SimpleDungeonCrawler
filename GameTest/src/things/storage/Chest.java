package things.storage;

import java.awt.Image;

import misc.Images;
import misc.SDC;
import things.items.Stick;

public class Chest extends Storage {
	static int w = (int) (100*SDC.SCALE_FACTOR);
	static int l = (int) (70*SDC.SCALE_FACTOR);
	public Chest(double x, double y, int rarity) {
		super(loadImage(), x, y, w, l, rarity);
		setCapacity(12);
	}
	
	private static Image loadImage() {
		return Images.loadImage("Items\\Chest.png", w, l);
	}

}
