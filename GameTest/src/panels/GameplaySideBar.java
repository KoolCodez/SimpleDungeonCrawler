package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.SDC;
import things.Thing;
import things.entities.Entity;

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
		g.setColor(Color.white);
		g.fillRect((int) (1000 * scale), 0, (int) (300 * scale), (int) (1000 * scale));
		//System.out.println("drawing sideBar: " + thing);
		//g.drawImage(thing.getImage(), 0, 0, null);
		thing.displayOnSide(g);
		if (interactor.getLocation().distance(thing.getLocation()) >= CoreGameplayPanel.PICKUP_RADIUS) {
			SDC.frame.remove(this);
		}
	}
}
