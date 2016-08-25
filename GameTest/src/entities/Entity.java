package entities;

import java.awt.Color;
import java.awt.Graphics;
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
import misc.SDC;
import misc.TextureGenerator;
import misc.Utilities;
import rooms.StandardRoom;

public class Entity extends Thing implements Serializable { // extend this class with specific entity-classes.
	private Utilities utilities = new Utilities();
	
	private String entityType;
	private String name;
	public Point battleLoc;
	public Image finalImage;
	public Image deadImage;
	private int rotation = 0;
	private List<Item> inventory = new ArrayList<Item>();
	public int maxItems = 20;
	public EntityStats stats = new EntityStats();
	protected Weapon weapon;
	protected ArmorSet armor;
	
	public Entity() {
		name = "Entity";
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
	
	public void displayOnSide(Graphics g) {
		double scale = SDC.SCALE_FACTOR;
		super.displayOnSide(g);
		g.setColor(Color.red);
		g.fillRect((int) (1000 * scale), (int) (300 * scale), 
				(int) (350 * stats.getHealth() / stats.getMaxHealth() * scale), (int) (50 * scale));
		g.setColor(Color.green);
		g.drawString("Name: " + name, (int) (1000 * scale), (int) (375 * scale));
		g.drawString("Entity Type: " + entityType, (int) (1000 * scale), (int) (400 * scale));
		
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
		images.add(armor.body.getImage());
		images.add(armor.feet.getImage());
		images.add(armor.hands.getImage());
		images.add(armor.legs.getImage());
		images.add(weapon.getImage());
		images.add(armor.head.getImage());
		finalImage = ig.compileImage(images);
	}
	
	public void setAngle(int degrees) {
		int deltaAngle = degrees - rotation;
		rotation = degrees;
		TextureGenerator ig = new TextureGenerator();
		finalImage = ig.rotate(finalImage, deltaAngle);
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
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
}
