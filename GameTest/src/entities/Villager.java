package entities;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import items.Item;
import misc.Images;
import misc.SDC;

public class Villager extends Entity {
	double SCALE_FACTOR = SDC.SCALE_FACTOR;
	public Villager() {
		stats.setStats(Math.random() * 10, Math.random() * 10, Math.random() * 10, Math.random() * 10,
				Math.random() * 10, Math.random() * 10, Math.random() * 10, (int) (Math.random() * 10));
		setType("Villager");
		location = new Point2D.Double(randomLoc(), randomLoc());
		setImage(Images.array[18]);
		this.setSize((int) (72 * SCALE_FACTOR), (int) (92 * SCALE_FACTOR));
		this.setLocation(location.getX(), location.getY());
	}
	
	private double randomLoc() {
		return 500*SCALE_FACTOR + ((Math.random() * 400*SCALE_FACTOR)- 200*SCALE_FACTOR);
	}
}