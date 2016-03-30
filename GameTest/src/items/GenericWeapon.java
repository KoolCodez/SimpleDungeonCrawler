package items;

import javax.swing.ImageIcon;

public class GenericWeapon extends GenericItem{
	public GenericWeapon(ImageIcon image, String name) {
		super(image, name);
	}
	public boolean ranged;
	public double damage;
	public double speed;
	public String ammoType;
}
