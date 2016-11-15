package things.items;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import misc.Images;
import misc.SDC;
import things.Thing;
import things.entities.Entity;

public class Item extends Thing {
	public String itemName;
	public String inventoryImage;
	public String gameplayImage;
	public int rarity;
	
	public Item(String inventoryImage, String gameplayImage, String name, int rarity) {
		this.inventoryImage = inventoryImage;
		setImage(gameplayImage);
		itemName = name;
		this.rarity = rarity;
	}
	
	public ImageIcon getInventoryImage() {
		Image i = Images.loadImage(inventoryImage, outline.getWidth(), outline.getHeight());
		i = i.getScaledInstance((int) outline.getWidth(), (int) outline.getHeight(), Image.SCALE_SMOOTH);
		return new ImageIcon(i);
	}
	
	public void setInventoryImage(String i) {
		inventoryImage = i;
	}
	
	@Override
	public void interact(Entity interactor) {
		interactor.getInventory().add(this);
	}
}
