package misc;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import items.GenericItem;

public class Villager extends Entity {
	double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	public Villager() {
		stats.setStats(Math.random() * 10, Math.random() * 10, Math.random() * 10, Math.random() * 10,
				Math.random() * 10, Math.random() * 10, Math.random() * 10, (int) (Math.random() * 10));
		setType("Villager");
		location = new Point2D.Double(randomLoc(), randomLoc());
		setImage(18);
	}
	
	private double randomLoc() {
		return 500*SCALE_FACTOR + ((Math.random() * 400*SCALE_FACTOR)- 200*SCALE_FACTOR);
	}
}
