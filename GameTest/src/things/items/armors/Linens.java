package things.items.armors;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;

public class Linens extends Armor {
	static Image gameNoImage = Images.array[Images.blankLayerIndex];
	static ImageIcon invNoImage = new ImageIcon(gameNoImage);
	public Linens() {
		super(invNoImage, gameNoImage, "Linens", 1, 10);
	}

}
