package misc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import items.GenericItem;
import items.GenericWeapon;

public class Entity implements Comparable<Entity>{ // extend this class with specific entity-classes.
	public String entityType;
	public Point entityLocation;
	public List<GenericItem> entityInventory;
	public double str;
	public double dex;
	public double con;
	public double intl;
	public double wis;
	public double chr;
	public int initiative;
	public double AC;
	public double health;
	public double maxHealth;
	public GenericWeapon selectedWeapon;
	Random rand = new Random();
	
	public void strUp(double strength) {
		this.str += strength;
	}
	
	public void dexUp(double dexterity) {
		this.dex += dexterity;
	}
	
	public void conUp(double constitution) {
		this.con += constitution;
	}
	
	public void intlUp(double intelligence) {
		this.intl += intelligence;
	}
	
	public void wisUp(double wisdom) {
		this.wis += wisdom;
	}

	public Entity() {
		entityInventory = new ArrayList<GenericItem>();
		entityType = "Generic Entity";
		entityLocation = new Point(250 - rand.nextInt(), 250 + rand.nextInt());
	}
	
	public void addItem(GenericItem item) {
		entityInventory.add(item);
	}

	public void setStats(double health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma, int AC) {
		maxHealth = health;
		this.health = maxHealth;
		str = strength;
		dex = dexterity;
		con = constitution;
		intl = intelligence;
		wis = wisdom;
		chr = charisma;
		this.AC = AC;
	}
	
	public void setWeapon(GenericWeapon weapon) {
		selectedWeapon = weapon;
	}
	
	public int compareTo(Entity otherInit) {
		return this.initiative - otherInit.initiative;
	}
}
