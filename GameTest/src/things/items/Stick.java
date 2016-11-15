package things.items;

import java.util.Random;

import javax.swing.ImageIcon;

import misc.Images;

public class Stick extends Item {
	static String name = "Stick";
	public Stick() {
		super(myImage(), myImage(), name, 20);
	}
	
	private static String myImage() {
		Random rand = new Random();
		int random = rand.nextInt(5) + 1;
		//image = "Items/Stick" + random + ".png";
		return "Items/Stick" + random + ".jpg";
	}
}
