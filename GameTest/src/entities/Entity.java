package entities;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import items.Fists;
import items.Item;
import items.Weapon;
import misc.Images;
import misc.TextureGenerator;
import misc.Utilities;
import rooms.StandardRoom;

public class Entity extends Thing implements Comparable<Entity>, Serializable { // extend this class with specific entity-classes.
	private Utilities utilities = new Utilities();
	
	private String entityType;
	private String name = "Entity";
	public Point battleLoc;
	public Image finalImage;
	private int rotation = 0;
	private List<Item> inventory = new ArrayList<Item>();
	public EntityStats stats = new EntityStats();
	private int initiative;
	protected Weapon weapon;
	protected ArmorSet armor;
	
	private int imageIndex;
	private int deadImageIndex;
	
	public Entity() {
		armor = new ArmorSet();
		weapon = new Fists();
		entityType = "Generic Entity";
		location = new Point2D.Double(250, 250);
		battleLoc = new Point(0,0);
	}

	public Entity(double health, double strength, double dexterity, double constitution, double intelligence,
			double wisdom, double charisma, int AC, double spd) {
		super();
		armor = new ArmorSet();
		weapon = new Fists();
		entityType = "Generic Entity";
		stats.setStats(health, strength, dexterity, constitution, intelligence, wisdom, charisma, AC, spd);
		battleLoc = new Point(0,0);
	}
	
	public Image getImage() {
		//return super.image;
		return finalImage;
	}
	
	public void setImage(Image i) {
		super.setImage(i);
		TextureGenerator ig = new TextureGenerator();
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(i);
		//images.add(armor.body.getImage());
		//images.add(armor.feet.getImage());
		//images.add(armor.hands.getImage());
		//images.add(armor.legs.getImage());
		//images.add(weapon.getImage());
		//images.add(armor.head.getImage());
		finalImage = ig.compileImage(images);
	}
	
	public void setAngle(int degrees) {
		int deltaAngle = degrees - rotation;
		rotation = degrees;
		TextureGenerator ig = new TextureGenerator();
		finalImage = ig.rotate(finalImage, deltaAngle);
	}
	
	public void setDeadImage(int index) {
		deadImageIndex = index;
	}

	
	
	public void setRoom(StandardRoom r) {
		super.setRoom(r);
		r.entities.add(this);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String newType) {
		entityType = newType;
	}
	
	public String getType() {
		return entityType;
	}
	
	public List<Item> getInventory() {
		return inventory;
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	public void setInitiative(int init) {
		initiative = init;
	}
	
	public int getInitiative() {
		return initiative;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public int compareTo(Entity otherInit) {
		return this.initiative - otherInit.initiative;
	}
}
