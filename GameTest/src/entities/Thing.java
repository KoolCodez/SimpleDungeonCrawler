package entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import misc.SDC;
import rooms.StandardRoom;

public class Thing {
	public Point2D location;
	public Rectangle outline;
	public StandardRoom currentRoom;
	public Image image;
	
	public Thing() {
		location = new Point2D.Double(250 * SDC.SCALE_FACTOR, 250 * SDC.SCALE_FACTOR);
		outline = new Rectangle();
	}
	
	public Thing(Image i) {
		image = i;
	}
	
	public Thing(Image i, double x, double y) {
		image = i;
		setLocation(x, y);
	}
	
	public Thing(double x, double y, int w, int l) {
		setLocation(x, y);
		setSize(w, l);
	}
	
	public void setImage(Image i) {
		image = i;
	}
	
	public void move(double deltaX, double deltaY) {
		if (legalMove(deltaX, deltaY, outline)) {
			location.setLocation(location.getX() + deltaX, location.getY() + deltaY);
			outline.setLocation((int) (location.getX()), (int) (location.getY()));
			SDC.checkIfLeavingRoom();
		}
	}
	
	public void setLocation(double newX, double newY) {
		location.setLocation(newX, newY);
		outline.setLocation((int) (location.getX()), (int) (location.getY()));
	}
	
	public void setRoom(StandardRoom r) {
		r.things.add(this);
		currentRoom = r;
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public void setSize(int w, int l) {
		outline.setSize(w, l);
	}
	
	public boolean legalMove(double deltaX, double deltaY, Rectangle rect) {
		rect.setLocation((int) (location.getX() + deltaX), (int) (location.getY() + deltaY));
		for (int i = 0; i < currentRoom.things.size(); i++) {
			if (rect.intersects(currentRoom.things.get(i).outline) && this != currentRoom.things.get(i)) {
				
				return false;
			}
		}
		return rectIsInsideRoom(rect);
	}
	
	private boolean rectIsInsideRoom(Rectangle rect) {
		double top = rect.getY();
		double left = rect.getX();
		double bottom = rect.getY() + rect.getHeight();
		double right = rect.getX() + rect.getWidth();
		if (top < 0) {
			return false;
		}
		if (left < 0) {
			return false;
		}
		if (bottom > 1000*SDC.SCALE_FACTOR) {
			return false;
		}
		if (right > 1000*SDC.SCALE_FACTOR) {
			return false;
		}
		return true;
	}
}