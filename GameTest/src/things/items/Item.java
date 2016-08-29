package things.items;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import things.Thing;
import things.entities.Entity;

public class Item extends Thing implements Serializable {
	public String itemName;
	public ImageIcon inventoryImage;
	public Image gameplayImage;
	public int rarity;
	
	public Item(ImageIcon inventoryImage, Image gameplayImage, String name, int rarity) {
		this.inventoryImage = inventoryImage;
		this.gameplayImage = gameplayImage;
		itemName = name;
		this.rarity = rarity;
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
	
	@Override
	public void interact(Entity interactor) {
		interactor.getInventory().add(this);
	}
}
