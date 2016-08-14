package items;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import entities.Thing;

public class Item extends Thing implements Serializable {
	public String itemName;
	public ImageIcon inventoryImage;
	public Image gameplayImage;
	public Item(ImageIcon inventoryImage, Image gameplayImage, String name) {
		this.inventoryImage = inventoryImage;
		this.gameplayImage = gameplayImage;
		itemName = name;
	}
	
	public ImageIcon getInventoryImage() {
		return inventoryImage;
	}
	public Image getImage() {
		return inventoryImage.getImage();
	}
	public void setImage(Image i) {
		inventoryImage.setImage(i);
	}
}
