package effects;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import entities.Entity;
import misc.Images;
import misc.SDC;
import misc.TextureGenerator;

public class Swipe extends Effect {
	private float opacity;
	public Swipe(Point l) {
		image = getImage();
		location = l;
		opacity = 1.0f;
	}

	public void run() {
		for (int i = 0; i <= 10; i++) {
			try {
				Thread.sleep(SDC.refreshRate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Image getImage() {
		Image i = Images.loadImage("Characters//swipe.png", (int) (200*SDC.SCALE_FACTOR), (int) (200*SDC.SCALE_FACTOR));
		return i;
	}
	
	public void draw(Graphics g) {
		if (opacity >= 0) {
			opacity = opacity - .02f;
		} else {
			opacity = 0;
		}
		if (opacity > 0) {
			Graphics2D g2d = (Graphics2D) g;
			//System.out.println(opacity);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			g2d.drawImage(image, location.x, location.y, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		} 
	}
	
	public void fixLocation(Entity attacker, Entity target) {
		TextureGenerator tg = new TextureGenerator();
		Point aLoc = attacker.battleLoc;
		Point tLoc = target.battleLoc;
		if (aLoc.x > tLoc.x) {
			image = tg.rotate(image, -90);
			location = new Point((int) (attacker.location.getX() + 20*SDC.SCALE_FACTOR),
					(int) (attacker.location.getY()));
		} else if(aLoc.x < tLoc.x) {
			image = tg.rotate(image, 90);
			location = new Point((int) (attacker.location.getX() + 50*SDC.SCALE_FACTOR),
					(int) (attacker.location.getY()));
		}
		
		if (aLoc.y > tLoc.y) {
			image = tg.rotate(image, 0);
			location = new Point((int) (attacker.location.getX()),
					(int) (attacker.location.getY() + 20*SDC.SCALE_FACTOR));
		} else if(aLoc.y < tLoc.y) {
			image = tg.rotate(image, 180);
			location = new Point((int) (attacker.location.getX() + 50*SDC.SCALE_FACTOR),
					(int) (attacker.location.getY()));
		}
	}
}
