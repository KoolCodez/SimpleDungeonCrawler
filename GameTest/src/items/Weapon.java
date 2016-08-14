package items;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Weapon extends Item{
	public Weapon(ImageIcon inventoryImage, Image gameplayImage, String name) {
		super(inventoryImage, gameplayImage, name);
	}
	public boolean ranged;
	public double damage;
	public double speed;
	public double reach;
	public String ammoType;
}
