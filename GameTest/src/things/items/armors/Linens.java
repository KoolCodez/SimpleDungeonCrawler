package things.items.armors;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;
import misc.SDC;

public class Linens extends Armor {
	private static Image gameImage;
	public Linens(String type) {
		super(getInvImage(type), gameImage, "Linens", 1, 10, type);
	}
	
	private static Image getGameImage(String type) {
		Image image = null;
		double size = 100.0;
		switch (type) {
		case "head": image = Images.loadImage("Items\\headLinens.png", size, size);
			break;
		case "body": image = Images.loadImage("Items\\bodyLinens.png", size, size);
			break;
		case "legs": image = Images.array[Images.blankLayerIndex];;
			break;
		case "feet": image = Images.array[Images.blankLayerIndex];
			break;
		case "hands": image = Images.loadImage("Items\\handsLinens.png", size, size);
			break;
		default: image = Images.loadImage("Items\\bodyLinens.png", size, size);
			break;
		}
		gameImage = image;
		return image;
	}
	
	private static ImageIcon getInvImage(String type) {
		return new ImageIcon(getGameImage(type));
		
	}

}
