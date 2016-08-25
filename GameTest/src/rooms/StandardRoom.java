package rooms;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.Entity;
import entities.Thing;
import entities.Wall;
import items.Item;
import misc.SDC;

public class StandardRoom implements Serializable {
	public static final String BATTLE_TAG = "battle";
	public static final String PUZZLE_TAG = "puzzle";
	public static final String TREASURE_TAG = "treasure";
	public static final double SCALE_FACTOR = SDC.SCALE_FACTOR;
	
	public String typeOfRoom;
	public List<Entity> entities;
	public List<Thing> things;
	public List<Item> items;
	
	public StandardRoom() {
		entities = new ArrayList<Entity>();
		things = new ArrayList<Thing>();
		things.addAll(entities);
		items = new ArrayList<Item>();
		createWalls();
	}

	private void createWalls() {
		Thing tl = new Wall();
		tl.setLocation(0, 0);
		tl.setSize((int) (400 * SCALE_FACTOR), (int) (72 * SCALE_FACTOR));
		things.add(tl);
		
		Thing tr = new Wall();
		tr.setLocation(600*SCALE_FACTOR, 0);
		tr.setSize((int) (400 * SCALE_FACTOR), (int) (72 * SCALE_FACTOR));
		things.add(tr);
		
		Thing lt = new Wall();
		lt.setLocation(0, 72*SCALE_FACTOR);
		lt.setSize((int) (72 * SCALE_FACTOR), (int) (328 * SCALE_FACTOR));
		things.add(lt);
		
		Thing lb = new Wall();
		lb.setLocation(0, 600 * SCALE_FACTOR);
		lb.setSize((int) (72 * SCALE_FACTOR), (int) (328 * SCALE_FACTOR));
		things.add(lb);
		
		Thing bl = new Wall();
		bl.setLocation(0, 928 * SCALE_FACTOR);
		bl.setSize((int) (400 * SCALE_FACTOR), (int) (72 * SCALE_FACTOR));
		things.add(bl);
		
		Thing br = new Wall();
		br.setLocation(600 * SCALE_FACTOR, 928 * SCALE_FACTOR);
		br.setSize((int) (400 * SCALE_FACTOR), (int) (72 * SCALE_FACTOR));
		things.add(br);
		
		Thing rb = new Wall();
		rb.setLocation(928 * SCALE_FACTOR, 600 * SCALE_FACTOR);
		rb.setSize((int) (72 * SCALE_FACTOR), (int) (328 * SCALE_FACTOR));
		things.add(rb);
		
		Thing rt = new Wall();
		rt.setLocation(928 * SCALE_FACTOR, 72 * SCALE_FACTOR);
		rt.setSize((int) (72 * SCALE_FACTOR), (int) (328 * SCALE_FACTOR));
		things.add(rt);
	}
}
