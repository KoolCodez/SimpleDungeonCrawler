package items;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;

public class Fists extends Weapon {
	static Image i = Images.array[Images.blankLayerIndex];
	static ImageIcon invI = new ImageIcon(i);

	public Fists() {
		super(invI, i, "Fists");
		ranged = false;
		damage = 1.0;
		speed = 10;
		reach = 1.0;
		
	}
}
