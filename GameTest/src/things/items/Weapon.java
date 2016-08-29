package things.items;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Weapon extends Item{
	public Weapon(ImageIcon inventoryImage, Image gameplayImage, String name, int rarity) {
		super(inventoryImage, gameplayImage, name, rarity);
	}
	public boolean ranged;
	public double damage;
	public int speed;
	public double reach;
	public String ammoType;
}
