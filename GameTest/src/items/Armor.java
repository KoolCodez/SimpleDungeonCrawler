package items;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Armor extends Item {
	public double AC;
	public Armor(ImageIcon inventoryImage, Image gameplayImage, String name, double AC) {
		super(inventoryImage, gameplayImage, name);
		this.AC = AC;
	}
}
