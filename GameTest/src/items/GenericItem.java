package items;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GenericItem {
	public String itemName;
	public ImageIcon itemImage;
	public GenericItem(ImageIcon image, String name) {
		itemImage = image;
		itemName = name;
	}
	
	public ImageIcon getImage() {
		return itemImage;
	}
}
