package effects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import misc.SDC;

public class DeathMarker extends Effect {
	
	public DeathMarker(Point p) {
		location = p;
	}
	
	@Override
	protected void drawEffect(Graphics2D g2d) {
		g2d.setColor(Color.red);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawLine((int) (location.x * SDC.SCALE_FACTOR), (int) (location.x * SDC.SCALE_FACTOR),
				(int) ((location.y + 140) * SDC.SCALE_FACTOR), (int) ((location.y + 140) * SDC.SCALE_FACTOR));
		g2d.drawLine((int) ((location.x + 140) * SDC.SCALE_FACTOR), (int) (location.y * SDC.SCALE_FACTOR),
				(int) (location.x * SDC.SCALE_FACTOR), (int) ((location.y+140)*SDC.SCALE_FACTOR));
		g2d.setStroke(new BasicStroke(1));
	}
}
