package items;

import javax.swing.ImageIcon;

public class GenericWeapon extends GenericItem{
	public GenericWeapon(ImageIcon image, String name) {
		super(image, name);
	}
	public static boolean ranged;
	public static double damage;
	public static double speed;
	public static String ammoType;
}
