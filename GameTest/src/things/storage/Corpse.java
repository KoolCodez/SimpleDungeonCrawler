package things.storage;

import java.awt.Image;

import misc.SDC;
import things.entities.Entity;

public class Corpse extends Storage {

	public Corpse(Entity ent) {
		super(ent.deadImage, fixVal(ent.battleLoc.getX()), fixVal(ent.battleLoc.getY()), 
				ent.outline.getWidth(), ent.outline.getHeight(), ent.rarity);
		setCapacity(10);
		addAll(ent.getInventory());
		addItem(ent.equipped.body);
		addItem(ent.equipped.feet);
		addItem(ent.equipped.hands);
		addItem(ent.equipped.head);
		addItem(ent.equipped.feet);
		addItem(ent.equipped.weapon);
	}
	
	private static double fixVal(double val) {
		val *= 190.0 * SDC.SCALE_FACTOR + 50;
		System.out.println(val);
		return val;
		
	}
}
