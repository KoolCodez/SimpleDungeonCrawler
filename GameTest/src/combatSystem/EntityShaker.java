package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;

import misc.Entity;
import misc.SDC;

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
				entity.move(deltaX, deltaY);
				Thread.sleep(SDC.refreshRate);
				entity.move(-deltaX, -deltaY);
				Thread.sleep(SDC.refreshRate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
