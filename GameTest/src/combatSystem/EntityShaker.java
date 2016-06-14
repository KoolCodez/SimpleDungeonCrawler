package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;

import misc.Entity;
import misc.SimpleDungeonCrawler;

public class EntityShaker extends Thread {
	private Entity entity;
	private Point2D startingPoint;

	public EntityShaker(Entity entity) {
		this.entity = entity;
		startingPoint = entity.getLocation();
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				double deltaX = Math.random() * 5;
				double deltaY = Math.random() * 5;
				entity.moveLocation(deltaX, deltaY);
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
				entity.setLocation(-deltaX, -deltaY);
				Thread.sleep(SimpleDungeonCrawler.refreshRate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
