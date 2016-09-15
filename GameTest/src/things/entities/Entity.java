package things.entities;

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

import misc.Images;
import misc.SDC;
import misc.TextureGenerator;
import misc.Utilities;
import rooms.StandardRoom;
import things.Thing;
import things.items.Item;
import things.items.weapons.Fists;
import things.items.weapons.Weapon;
import things.storage.Corpse;

public class Entity extends Thing implements Serializable { // extend this class with specific entity-classes.
	private Utilities utilities = new Utilities();
	
	private String entityType;
	private String name;
	public ImageIcon deadImage;
	private int rotation = 0;
	private List<Item> inventory = new ArrayList<Item>();
	public int maxItems = 20;
	public EntityStats stats = new EntityStats();
	public EquippedItems equipped;
	
	public Entity() {
		super();
		name = "Entity";
		equipped = new EquippedItems();
		entityType = "Generic Entity";
		setLocation(250, 250);
		deadImage = new ImageIcon();
		rarity = 0;
	}

	public Entity(double health, double strength, double dexterity, double willPower, int rarity) {
		super();
		equipped = new EquippedItems();
		entityType = "Generic Entity";
		stats.setStats(health, strength, dexterity, willPower);
		deadImage = new ImageIcon();
	}
	
	public void generateStats(int rarity) {
		int portions = 30;
		int[] portionsPerStat = new int[4];
		for (int i = 0; i < portions; i++) {
			portionsPerStat[(int) (Math.random() * 4)]++;
		}
		double health = rarity * portionsPerStat[0] / portions;
		double strength = rarity * portionsPerStat[1] / portions;
		double dexterity = rarity * portionsPerStat[2] / portions;
		double willPower = rarity * portionsPerStat[3] / portions;
		stats.setStats(health, strength, dexterity, willPower);
	}
	
	public void displayOnSide(Graphics g) {
		double scale = SDC.SCALE_FACTOR;
		super.displayOnSide(g);
		g.setColor(Color.red);
		g.fillRect((int) (1000 * scale), (int) (400 * scale), 
				(int) (300 * stats.getHealth() / stats.getMaxHealth() * scale), (int) (50 * scale));
		g.setColor(Color.green);
		g.drawRect((int) (1000 * scale), (int) (400 * scale), (int) (299 * scale), (int) (50 * scale));
		g.setColor(Color.black);
		g.drawString("Name: " + name, (int) (1000 * scale), (int) (475 * scale));
		g.drawString("Entity Type: " + entityType, (int) (1000 * scale), (int) (495 * scale));
		
	}
	
	public BattleEntity generateBattleEnt() {
		BattleEntity battleEnt = new BattleEntity(this);
		return battleEnt;
	}
	
	public void damageEnt(double damage) {
		stats.setHealth(-damage);
		if (stats.getHealth() <= 0) {
			this.currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
			System.out.println(this.currentRoom.entities);
			this.currentRoom.entities.remove(this);
			this.currentRoom.things.remove(this);
			this.currentRoom.things.add(new Corpse(this));
		}
	}
	
	@Override
	public void drawEntity(Graphics g) {
		TextureGenerator ig = new TextureGenerator();
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(getImage());
		images.add(equipped.body.getImage());
		images.add(equipped.feet.getImage());
		images.add(equipped.hands.getImage());
		images.add(equipped.legs.getImage());
		images.add(equipped.weapon.getImage());
		images.add(equipped.head.getImage());
		Image i = ig.compileImage(images);
		i = ig.rotate(i, rotation);
		g.drawImage(i, (int) outline.getX(), (int) outline.getY(), null);
	}
	
	public void setAngle(int degrees) {
		rotation = degrees;
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
}
