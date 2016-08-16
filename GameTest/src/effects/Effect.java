package effects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import misc.Images;

public class Effect extends Thread {
	public Image image;
	public Point location;
	
	public Effect() {
		image = Images.array[Images.blankLayerIndex];
		location = new Point(0, 0);
	}
	
	public Effect(Image i, Point l) {
		image = i;
		location = l;
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, location.x, location.y, null);
	}
}
