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
	
	public Item equipItem(Item item) {
		System.out.println(item.getClass().getName());
		if (item instanceof Armor) {
			Armor e = (Armor) item;
			switch (e.type) {
			case "head": final Item e1 = head;
				head = (Armor) item;
				return e1;
			case "body": final Item e2 = body;
				body = (Armor) item;
				return e2;
			case "legs": final Item e3 = legs;
				legs = (Armor) item;
				return e3;
			case "feet": final Item e4 = feet;
				feet = (Armor) item;
				return e4;
			case "hands": final Item e5 = hands;
				hands = (Armor) item;
				return e5;
			default: System.out.println("Item is not equippable as Armor!");
				return null;
			}
		} else if (item instanceof Weapon) {
			final Item e = weapon;
			weapon = (Weapon) item;
			return e;
		} else {
			System.out.println("Item is not equippable!");
			return null;
		}
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
