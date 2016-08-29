package things.entities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import misc.Images;
import misc.SDC;
import things.items.Item;
import things.items.Weapon;
import things.items.armors.Armor;
import things.items.armors.NullArmor;
import things.items.weapons.Fists;

public class EquippedItems {
	public Armor head;
	public Armor body;
	public Armor legs;
	public Armor feet;
	public Armor hands;
	public Weapon weapon;
	
	public EquippedItems() {
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
	
	public ArrayList<Item> allEquipped() {
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(head);
		list.add(body);
		list.add(legs);
		list.add(feet);
		list.add(hands);
		list.add(weapon);
		return list;
	}
	
	private void setDefaultArmor() {
		head = new NullArmor();
		body = new NullArmor();
		legs = new NullArmor();
		feet = new NullArmor();
		hands = new NullArmor();
		weapon = new Fists();
	}
}
