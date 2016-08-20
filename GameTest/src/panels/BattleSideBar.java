package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import combatSystem.ControlRouter;
import entities.Entity;
import entities.Thing;
import misc.SDC;

public class BattleSideBar extends JPanel {
	
	private ControlRouter control;
	private Thing thing;
	
	public BattleSideBar(ControlRouter c, Thing toBeDisplayed) {
		control = c;
		thing = toBeDisplayed;
		SDC.frame.add(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		double scale = SDC.SCALE_FACTOR;
		g.fillRect((int) (1000 * scale), 0, (int) (300 * scale), (int) (1000 * scale));
		thing.displayOnSide(g);
	}
}
