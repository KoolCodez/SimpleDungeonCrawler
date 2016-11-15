package effects;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import misc.Images;
import misc.SDC;

public class Effect extends Thread {
	public Image image;
	public Point location;
	protected float opacity;
	
	public Effect() {
		image = Images.loadImage("Items/BlankLayer.png", 100.0, 100.0);
		image = image.getScaledInstance(SDC.SCALED_100, SDC.SCALED_100, Image.SCALE_SMOOTH);
		location = new Point(0, 0);
		opacity = 1.0f;
	}
	
	public Effect(Image i, Point l) {
		image = i;
		location = l;
		opacity = 1.0f;
	}
	
	public void draw(Graphics g) {
		if (opacity >= 0) {
			opacity = opacity - .01f;
		} else {
			opacity = 0;
		}
		if (opacity > 0) {
			Graphics2D g2d = (Graphics2D) g;
			//System.out.println(opacity);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			drawEffect(g2d);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		} 
	}
	
	protected void drawEffect(Graphics2D g2d) {
		g2d.drawImage(image, location.x, location.y, null);
	}
}
