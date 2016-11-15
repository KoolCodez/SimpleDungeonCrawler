package things.items.weapons;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;

public class Fists extends Weapon {

	public Fists() {
		super( "Items/BlankLayer.png",  "Items/BlankLayer.png", "Fists", 0);
		ranged = false;
		damage = .2;
		speed = 1;
		reach = 1;
		
	}
}
