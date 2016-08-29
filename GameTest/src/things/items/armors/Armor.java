package things.items.armors;

import java.awt.Image;

import javax.swing.ImageIcon;

import things.items.Item;

public class Armor extends Item {
	public double AC;
	public Armor(ImageIcon inventoryImage, Image gameplayImage, String name, double AC, int rarity) {
		super(inventoryImage, gameplayImage, name, rarity);
		this.AC = AC;
	}
}
