package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import combatSystem.ControlRouter;
import combatSystem.Select;
import effects.Effect;
import effects.FallingDamageNumber;
import entities.Entity;
import misc.Images;
import misc.SDC;
import rooms.StandardRoom;

public class BattleViewPanel extends JPanel {
	private final int CHAR_X_ADJUST = (int) (50 * SCALE_FACTOR);
	private final int CHAR_Y_ADJUST = (int) (25 * SCALE_FACTOR);
	private static final double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private ArrayList<Effect> effects;
	private Entity character = SDC.character;
	private ControlRouter control;
	private Point2D rectLoc;
	
	public BattleViewPanel(ControlRouter c) {
		effects = new ArrayList<Effect>();
		this.setBounds(0, (int) (148 * SCALE_FACTOR), (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR));
		control = c;
		rectLoc = new Point2D.Double(-100, -100);
		Select s = new Select(this, control);
		s.start();
	}
	
	public void addEffect(Effect e) {
		effects.add(e);
		e.start();
	}
	
	public void highlight(Point p) {
		rectLoc.setLocation(p);
	}
	
	public Point2D getBattleLoc(Entity ent) {
		double SCALE_FACTOR = SDC.SCALE_FACTOR;
		double battleRatioX = (696 * SCALE_FACTOR) / (1000 * SCALE_FACTOR); //battle size / normal size
		double battleRatioY = (703 * SCALE_FACTOR) / (1000 * SCALE_FACTOR);
		Point2D battleLoc = new Point2D.Double(ent.location.getX() * (battleRatioX),
				ent.location.getY() * (battleRatioY) + 149); 
		//(0,149)(696, 149)(0,852)(696,852) 696, 703
		return battleLoc;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//g.drawImage(Images.array[Images.battleViewBackgroundIndex], 0, 0, (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR), null);
		g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.white);
		drawGrid(g);
		StandardRoom current = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		for (int i = 0; i < current.entities.size(); i++) {
			drawEntities(g, current, i);
		}
		for (int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			e.draw(g);
		}
		//drawEffects(g);
		g.setColor(Color.yellow);
		g.drawRect((int) rectLoc.getX(), (int) (rectLoc.getY()),
				(int) (140 * SCALE_FACTOR),(int) (140 * SCALE_FACTOR));
		g.setColor(Color.white);
		g.drawString("Turn Points Remining: " + control.waitForTurn.getTurnPoints(), 10, 10);
		
	}
	
	private void drawGrid(Graphics g) {
		g.drawLine(0, 0, 0, (int) (700*SCALE_FACTOR));
		g.drawLine((int) (140*SCALE_FACTOR), 0, (int) (140*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		g.drawLine((int) (280*SCALE_FACTOR), 0, (int) (280*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		g.drawLine((int) (420*SCALE_FACTOR), 0, (int) (420*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		g.drawLine((int) (560*SCALE_FACTOR), 0, (int) (560*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		g.drawLine((int) (700*SCALE_FACTOR), 0, (int) (700*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		
		g.drawLine(0, 0, (int) (700*SCALE_FACTOR), 0);
		g.drawLine(0, (int) (140*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (140*SCALE_FACTOR));
		g.drawLine(0, (int) (280*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (280*SCALE_FACTOR));
		g.drawLine(0, (int) (420*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (420*SCALE_FACTOR));
		g.drawLine(0, (int) (560*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (560*SCALE_FACTOR));
		g.drawLine(0, (int) (700*SCALE_FACTOR), (int) (700*SCALE_FACTOR), (int) (700*SCALE_FACTOR));
		
	}
	
	private void drawEntities(Graphics g, StandardRoom current, int i) {
		//System.out.println(current.entities.size());
		Entity ent = current.entities.get(i);
		g.drawImage(ent.getImage(), (int) ent.location.getX(), (int) ent.location.getY(), null);
	}
	
	private void drawEffects(Graphics g) {
		
	}
	
}
