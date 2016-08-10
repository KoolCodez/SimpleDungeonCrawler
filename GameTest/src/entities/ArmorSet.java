package entities;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import items.Armor;
import misc.SDC;

public class ArmorSet {
	public Armor head;
	public Armor body;
	public Armor legs;
	public Armor feet;
	public Armor hands;
	
	public ArmorSet() {
		setDefaultArmor();
	}
	
	public double combinedAC() {
		double AC = 0;
		AC += head.AC;
		AC += body.AC;
		AC += legs.AC;
		AC += feet.AC;
		AC += hands.AC;
		return AC;
	}
	
	private void setDefaultArmor() {
		int scaled100 = (int) (100 * SDC.SCALE_FACTOR);
		ImageIcon noImage = new ImageIcon(new BufferedImage(scaled100, scaled100, BufferedImage.TYPE_3BYTE_BGR));
		head = new Armor(noImage, "noHelmetArmor", 0.0);
		body = new Armor(noImage, "noBodyArmor", 0.0);
		legs = new Armor(noImage, "noLegsArmor", 0.0);
		feet = new Armor(noImage, "noFeetArmor", 0.0);
		hands = new Armor(noImage, "noHandsArmor", 0.0);
	}
}
