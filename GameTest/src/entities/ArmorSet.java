package entities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import items.Armor;
import misc.Images;
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
		Image image = Images.array[Images.blankLayerIndex];
		ImageIcon noImage = new ImageIcon(image);
		noImage.setImage(image);
		head = new Armor(noImage, image, "noHelmetArmor", 0.0);
		body = new Armor(noImage, image, "noBodyArmor", 0.0);
		legs = new Armor(noImage, image, "noLegsArmor", 0.0);
		feet = new Armor(noImage, image, "noFeetArmor", 0.0);
		hands = new Armor(noImage, image, "noHandsArmor", 0.0);
	}
}
