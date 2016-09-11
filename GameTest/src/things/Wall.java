package things;

import misc.Images;
import things.entities.Entity;

public class Wall extends Thing {
	public Wall() {
		super();
		setImage(Images.array[Images.blankLayerIndex]);
	}
	
	@Override
	public void interact(Entity interactor) {
		System.out.println("This is a wall");
	}
}
