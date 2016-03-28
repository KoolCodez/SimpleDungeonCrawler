package misc;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import items.GenericItem;
import items.GenericWeapon;

public class Entity implements Comparable<Entity>{ // extend this class with specific entity-classes.
	private String entityType;
	private Point2D location;
	private List<GenericItem> inventory;
	private double str;
	private double dex;
	private double con;
	private double intl;
	private double wis;
	private double chr;
	private int initiative;
	private double AC;
	private double health;
	private double maxHealth;
	private GenericWeapon selectedWeapon;
	Random rand = new Random();
	
	public Entity() {
		inventory = new ArrayList<GenericItem>();
		entityType = "Generic Entity";
		location = new Point2D.Double(250, 250);
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
	
	public void setType(String newType) {
		entityType = newType;
	}
	
	public String getType() {
		return entityType;
	}
	
	public void setLocation(double deltaX, double deltaY) {
		location.setLocation(location.getX() + deltaX, location.getY() + deltaY);
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public List<GenericItem> getInventory() {
		return inventory;
	}
	
	public void addItem(GenericItem item) {
		inventory.add(item);
	}
	
	public void setStr(double strength) {
		this.str += strength;
	}
	
	public double getStr() {
		return str;
	}
	
	public void setDex(double dexterity) {
		this.dex += dexterity;
	}
	
	public double getDex() {
		return dex;
	}
	
	public void setCon(double constitution) {
		this.con += constitution;
	}
	
	public double getCon() {
		return con;
	}
	
	public void setIntl(double intelligence) {
		this.intl += intelligence;
	}
	
	public double getIntl() {
		return intl;
	}
	
	public void setWis(double wisdom) {
		this.wis += wisdom;
	}
	
	public double getWis() {
		return wis;
	}
	
	public void setChr(double charisma) {
		chr += charisma;
	}
	
	public double getChr() {
		return chr;
	}
	
	public void setInitiative(int init) {
		initiative = init;
	}
	
	public int getInitiative() {
		return initiative;
	}
	
	public void setAC(double deltaAC) {
		AC += deltaAC;
	}
	
	public double getAC() {
		return AC;
	}
	
	public void setHealth(double deltaHealth) {
		health += deltaHealth;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setMaxHealth(double deltaMaxHealth) {
		maxHealth += deltaMaxHealth;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void setWeapon(GenericWeapon weapon) {
		selectedWeapon = weapon;
	}
	
	public GenericWeapon getWeapon() {
		return selectedWeapon;
	}
	
	public int compareTo(Entity otherInit) {
		return this.initiative - otherInit.initiative;
	}
}
