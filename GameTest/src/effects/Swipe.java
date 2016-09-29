package effects;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import misc.Images;
import misc.SDC;
import misc.TextureGenerator;
import things.entities.Entity;

public class Swipe extends Effect {
	public Swipe(Point l) {
		image = getImage();
		location = l;
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
		Image i = Images.loadImage("Characters/swipe.png", (int) (100*SDC.SCALE_FACTOR), (int) (100*SDC.SCALE_FACTOR));
		return i;
	}
	
	
	
	public void fixLocation(Entity attacker, Entity target) {
		TextureGenerator tg = new TextureGenerator();
		Point aLoc = attacker.battleLoc;
		Point tLoc = target.battleLoc;
		if (aLoc.x > tLoc.x) {
			image = tg.rotate(image, -90);
			location = new Point((int) (attacker.outline.getX() - 40*SDC.SCALE_FACTOR),
					(int) (attacker.outline.getY()));
		} else if(aLoc.x < tLoc.x) {
			image = tg.rotate(image, 90);
			location = new Point((int) (attacker.outline.getX() + 60*SDC.SCALE_FACTOR),
					(int) (attacker.outline.getY()));
		}
		
		if (aLoc.y > tLoc.y) {
			image = tg.rotate(image, 0);
			location = new Point((int) (attacker.outline.getX()),
					(int) (attacker.outline.getY() - 60*SDC.SCALE_FACTOR));
		} else if(aLoc.y < tLoc.y) {
			image = tg.rotate(image, 180);
			location = new Point((int) (attacker.outline.getX()),
					(int) (attacker.outline.getY() + 40*SDC.SCALE_FACTOR));
		}
	}
}
