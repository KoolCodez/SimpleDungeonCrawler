package items;

import javax.swing.ImageIcon;

public class Armor extends Item {
	public double AC;
	public Armor(ImageIcon image, String name, double AC) {
		super(image, name);
		this.AC = AC;
	}
}
