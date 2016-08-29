package things.items.armors;

import java.awt.Image;

import javax.swing.ImageIcon;

import misc.Images;

public class NullArmor extends Armor {
	static Image gameNoImage = Images.array[Images.blankLayerIndex];
	static ImageIcon invNoImage = new ImageIcon(gameNoImage);
	public NullArmor() {
		super(invNoImage, gameNoImage, "nullArmor", 0.0, 0);
	}
}
