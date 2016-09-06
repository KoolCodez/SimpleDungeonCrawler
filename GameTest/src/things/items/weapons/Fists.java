package things.items.weapons;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;

public class Fists extends Weapon {
	static Image i = Images.array[Images.blankLayerIndex];
	static ImageIcon invI = new ImageIcon(i);

	public Fists() {
		super(invI, i, "Fists", 0);
		ranged = false;
		damage = .2;
		speed = 1;
		reach = 1;
		
	}
}
