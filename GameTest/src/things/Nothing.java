package things;

import java.awt.geom.Point2D;

import misc.Images;
import misc.SDC;
import things.entities.Entity;

public class Nothing extends Thing {
	public Nothing () {
		super();
		super.setImage("Items/BlankLayer.png");
		super.setSize((int) (100 * SDC.SCALE_FACTOR), (int) (100 * SDC.SCALE_FACTOR));
	}
	
	@Override
	public void interact(Entity interactor) {
		System.out.println("This is nothing");
	}
}
