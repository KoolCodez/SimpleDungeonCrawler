package things.items.weapons;

import java.awt.Image;

import javax.swing.ImageIcon;

import things.items.Item;

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
