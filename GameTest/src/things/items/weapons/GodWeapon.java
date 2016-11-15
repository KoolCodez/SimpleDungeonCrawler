package things.items.weapons;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;

public class GodWeapon extends Weapon {
	public GodWeapon() {
		super("Items/BlankLayer.png",  "Items/BlankLayer.png", "GodWeapon", 1005);
		ranged = false;
		damage = 100;
		speed = 1;
		reach = 1;
	}
}
