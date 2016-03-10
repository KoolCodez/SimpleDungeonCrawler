package items;

import java.util.Random;

import javax.swing.ImageIcon;

import misc.Images;

public class Stick extends GenericItem {
	static ImageIcon image = new ImageIcon(Images.stickItem);
	static String name = "Stick";
	
	public Stick() {
		super(image, name);
	}
	
	@Override
	public ImageIcon getImage() {
		Random rand = new Random();
		int random = rand.nextInt(5) + 1;
		if (random == 1) {
			image = new ImageIcon(Images.stickItem);
			return new ImageIcon(Images.stickItem);
		}
		if (random == 2) {
			image = new ImageIcon(Images.stickItem2);
			return new ImageIcon(Images.stickItem2);
		}
		if (random == 3) {
			image = new ImageIcon(Images.stickItem3);
			return new ImageIcon(Images.stickItem3);
		}
		if (random == 4) {
			image = new ImageIcon(Images.stickItem4);
			return new ImageIcon(Images.stickItem4);
		}
		if (random == 5) {
			image = new ImageIcon(Images.stickItem5);
			return new ImageIcon(Images.stickItem5);
		}
		return null;
	}
}
