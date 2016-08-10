package items;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Item implements Serializable {
	public String itemName;
	public ImageIcon itemImage;
	public Item(ImageIcon image, String name) {
		itemImage = image;
		itemName = name;
	}
	
	public ImageIcon getImage() {
		return itemImage;
	}
}
