package things;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import misc.Images;
import misc.SDC;
import rooms.StandardRoom;
import things.entities.Entity;

public class Thing implements Serializable {
	public Rectangle2D outline;
	protected StandardRoom currentRoom;
	public String image;
	public int rarity;
	
	public Thing() {
		outline = new Rectangle2D.Double();
		outline.setRect(250 * SDC.SCALE_FACTOR, 250 * SDC.SCALE_FACTOR, 100 * SDC.SCALE_FACTOR, 100 * SDC.SCALE_FACTOR);
		image = "blankLayer.png";
		rarity = 0;
	}
	
	public Thing(String image, double x, double y, double w, double h, int rarity) {
		outline = new Rectangle2D.Double();
		outline.setRect(x, y, w, h);
		this.image = image;
		this.rarity = rarity;
	}
	
	public void displayOnSide(Graphics g) {
		double scale = SDC.SCALE_FACTOR;
		Image i = getImage().getScaledInstance((int) (300 * scale), (int) (300 * scale), Image.SCALE_SMOOTH);
		g.drawImage(i, (int) (1000 * scale), (int) (100 * scale), null);
	}
	
	public void addOptions(JPanel panel) {
		
	}
	
	public void setImage(String name) {
		image = name;
	}

	public void drawEntity(Graphics g) {
		g.drawImage(getImage(), (int) outline.getX(), (int) outline.getY(), null);
	}
	
	public Image getImage() {
		Image i = Images.loadImage(image, outline.getWidth(), outline.getHeight());
		i = i.getScaledInstance((int) outline.getWidth(), (int) outline.getHeight(), Image.SCALE_SMOOTH);
		return i;
	}
	
	public String getImageName() {
		return image;
	}
	
	public void interact(Entity interactor) {
		System.out.println("This is a thing");
	}
	
	public void move(double deltaX, double deltaY) {
		if (legalMove(deltaX, deltaY)) {
			outline.setFrame(outline.getX() + deltaX, outline.getY() + deltaY, 
					outline.getWidth(), outline.getHeight());
			SDC.checkIfLeavingRoom();
		}
	}
	
	public void setLocation(double newX, double newY) {
		outline.setFrame(newX, newY, outline.getWidth(), outline.getHeight());
	}
	
	public Point2D getLocation() {
		return new Point2D.Double(outline.getX(), outline.getY());
	}
	
	public void setRoom(StandardRoom r) {
		r.things.add(this);
		currentRoom = r;
	}

	public void setSize(double w, double h) {
		outline.setFrame(outline.getX(), outline.getY(), w, h);
	}
	
	public Rectangle2D getRect() {
		return outline;
	}
	
	public double getX() {
		return outline.getX();
	}
	
	public double getY() {
		return outline.getY();
	}
	
	public double getWidth() {
		return outline.getWidth();
	}
	
	public double getHeight() {
		return outline.getHeight();
	}
	
	private boolean legalMove(double deltaX, double deltaY) {
		Rectangle2D ghostRect = new Rectangle2D.Double(outline.getX() + deltaX, outline.getY() + deltaY, 
				outline.getWidth(), outline.getHeight());
		for (int i = 0; i < currentRoom.things.size(); i++) {
			if (ghostRect.intersects(currentRoom.things.get(i).outline) 
					&& this != currentRoom.things.get(i)) {
				return false;
			}
		}
		return rectIsInsideRoom(ghostRect);
	}
	
	private boolean rectIsInsideRoom(Rectangle2D rect) {
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