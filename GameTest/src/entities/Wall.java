package entities;

import misc.Images;

public class Wall extends Thing {
	public Wall() {
		setImage(Images.array[Images.blankLayerIndex]);
	}
	
	@Override
	public void interact(Entity interactor) {
		System.out.println("This is a wall");
	}
}
