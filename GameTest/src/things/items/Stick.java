package things.items;

import java.util.Random;

import javax.swing.ImageIcon;

import misc.Images;

public class Stick extends Item {
	static ImageIcon image = new ImageIcon(Images.array[Images.stickItemIndex]);
	static String name = "Stick";
	
	public Stick() {
		super(image, image.getImage(), name, 20);//TODO get real stick gameplay picture
	}
	
	@Override
	public ImageIcon getInventoryImage() {
		Random rand = new Random();
		int random = rand.nextInt(5) + 1;
		if (random == 1) {
			image = new ImageIcon(Images.array[Images.stickItemIndex]);
			return new ImageIcon(Images.array[Images.stickItemIndex]);
		}
		if (random == 2) {
			image = new ImageIcon(Images.array[Images.stickItem2Index]);
			return new ImageIcon(Images.array[Images.stickItem2Index]);
		}
		if (random == 3) {
			image = new ImageIcon(Images.array[Images.stickItem3Index]);
			return new ImageIcon(Images.array[Images.stickItem3Index]);
		}
		if (random == 4) {
			image = new ImageIcon(Images.array[Images.stickItem4Index]);
			return new ImageIcon(Images.array[Images.stickItem4Index]);
		}
		if (random == 5) {
			image = new ImageIcon(Images.array[Images.stickItem5Index]);
			return new ImageIcon(Images.array[Images.stickItem5Index]);
		}
		return null;
	}
}
