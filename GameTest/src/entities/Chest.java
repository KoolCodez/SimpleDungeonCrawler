package entities;

import java.awt.Image;

import items.Stick;
import misc.Images;
import misc.SDC;

public class Chest extends Storage {
	static int w = (int) (100*SDC.SCALE_FACTOR);
	static int l = (int) (70*SDC.SCALE_FACTOR);
	public Chest(double x, double y) {
		super(loadImage(), x, y, w, l);
		setCapacity(12);
		addItem(new Stick());
		addItem(new Stick());
		addItem(new Stick());
		addItem(new Stick());
	}
	
	private static Image loadImage() {
		return Images.loadImage("Items\\Chest.png", w, l);
	}

}
