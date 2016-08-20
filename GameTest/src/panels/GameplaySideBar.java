package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import entities.Entity;
import entities.Thing;
import misc.SDC;

public class GameplaySideBar extends JPanel {
	private Thing thing;
	private Entity interactor;
	
	public GameplaySideBar(Thing toBeDisplayed, Entity interactor) {
		setLayout(null);
		thing = toBeDisplayed;
		this.interactor = interactor;
		thing.interact(interactor);
		//this.setBounds((int) (1000 * SDC.SCALE_FACTOR), 0, (int) (300 * SDC.SCALE_FACTOR), (int) (1000 * SDC.SCALE_FACTOR));
		SDC.frame.add(this);
		thing.addOptions(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		double scale = SDC.SCALE_FACTOR;
		g.setColor(Color.gray);
		g.fillRect((int) (1000 * scale), 0, (int) (300 * scale), (int) (1000 * scale));
		//System.out.println("drawing sideBar: " + thing);
		//g.drawImage(thing.getImage(), 0, 0, null);
		thing.displayOnSide(g);
		if (interactor.location.distance(thing.location) >= CoreGameplayPanel.PICKUP_RADIUS) {
			SDC.frame.remove(this);
		}
	}
}
