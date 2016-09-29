package effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import misc.Images;

public class Speech extends Effect {
	String statement;
	
	public Speech() {
		image = Images.array[Images.blankLayerIndex];
		location = new Point(0, 0);
		statement = "my programmer is stupid";
		
	}
	
	public Speech(Image i, Point l, String statement) {
		image = i;
		location = l;
		this.statement = statement;
	}
	
	@Override
	protected void drawEffect(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.drawString(statement, location.x, location.y);
	}
}
