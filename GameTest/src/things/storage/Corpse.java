package things.storage;

import java.awt.Image;

import things.entities.Entity;

public class Corpse extends Storage {

	public Corpse(Entity ent) {
		super(ent.deadImage, ent.location.getX(), ent.location.getY(), ent.outline.width, ent.outline.height, ent.rarity);
		setCapacity(10);
		addAll(ent.getInventory());
		addItem(ent.equipped.body);
		addItem(ent.equipped.feet);
		addItem(ent.equipped.hands);
		addItem(ent.equipped.head);
		addItem(ent.equipped.feet);
		addItem(ent.equipped.weapon);
	}
}
