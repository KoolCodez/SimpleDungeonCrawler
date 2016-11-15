package things.items.armors;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;
import misc.SDC;

public class Linens extends Armor {
	private static String gameImage;
	public Linens(String type) {
		super(getInvImage(type), gameImage, "Linens", 1, 10, type);
	}
	
	private static String getGameImage(String type) {
		String image = "";
		double size = 100.0;
		switch (type) {
		case "head": image = "Items/headLinens.png";
			break;
		case "body": image = "Items/bodyLinens.png";
			break;
		case "legs": image =  "Items/BlankLayer.png";
			break;
		case "feet": image =  "Items/BlankLayer.png";
			break;
		case "hands": image = "Items/handsLinens.png";
			break;
		default: image = "Items/bodyLinens.png";
			break;
		}
		gameImage = image;
		return image;
	}
	
	private static String getInvImage(String type) {
		return getGameImage(type);
		
	}

}
