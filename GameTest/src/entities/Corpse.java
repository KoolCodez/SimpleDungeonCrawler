package entities;

import java.awt.Image;

public class Corpse extends Storage {

	public Corpse(Entity ent) {
		super(ent.deadImage, ent.location.getX(), ent.location.getY(), ent.outline.width, ent.outline.height);
		setCapacity(10);
		addAll(ent.getInventory());
	}
}
