package items;

import javax.swing.ImageIcon;

public class Weapon extends Item{
	public Weapon(ImageIcon image, String name) {
		super(image, name);
	}
	public boolean ranged;
	public double damage;
	public double speed;
	public double reach;
	public String ammoType;
}
