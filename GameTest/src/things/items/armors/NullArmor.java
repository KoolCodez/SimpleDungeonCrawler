package things.items.armors;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;

public class NullArmor extends Armor {
	static String gameNoImage = "Items/BlankLayer.png";
	static String invNoImage = "Items/BlankLayer.png";
	public NullArmor() {
		super(invNoImage, gameNoImage, "nullArmor", 0.0, 0, "");
	}
}
