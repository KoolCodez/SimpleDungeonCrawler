package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import combatSystem.FallingDamageNumber;
import misc.Entity;
import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;

public class BattleViewPanel extends JPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private ArrayList<FallingDamageNumber> damageNumbers;
	private Entity character = SimpleDungeonCrawler.character;
	public double moveRadius;
	
	public BattleViewPanel() {
		moveRadius = 0;
		damageNumbers = new ArrayList<FallingDamageNumber>();
		this.setBounds(0, (int) (148 * SCALE_FACTOR), (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR)); //TODO fix pls
	}
	
	public void showAttack() {
		
	}
	
	public void displayDamage(double damage, Point point) {
		FallingDamageNumber n = new FallingDamageNumber(damage, point);
		damageNumbers.add(n);
		n.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.array[Images.battleViewBackgroundIndex], 0, 0, (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR), null);
		Point2D charLoc = getBattleLoc(character);
		g.drawImage(Images.array[Images.battleCharIndex], (int) charLoc.getX(), (int) charLoc.getY(),
				(int) (100 * SCALE_FACTOR), (int) (50 * SCALE_FACTOR), null);
		drawEntities(g);
		g.setColor(Color.red);
		for (int i = 0; i < damageNumbers.size(); i++) {
			FallingDamageNumber currentNum = damageNumbers.get(i);
			Point point = currentNum.getPoint();
			g.setFont(SimpleDungeonCrawler.font);
			g.drawString(currentNum.getDamage() + "", point.x, point.y);
		}
		Point2D p = getBattleLoc(SimpleDungeonCrawler.character);
		g.drawOval((int) (p.getX() - moveRadius/2), (int) (p.getY() - moveRadius/2), (int) moveRadius, (int) moveRadius);
		g.setColor(Color.black);
		
	}
	
	private void drawEntities(Graphics g) {
		StandardRoom current = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		for (int i = 0; i < current.entities.size(); i++) {
			Point2D point = getBattleLoc(current.entities.get(i));
			g.drawImage(Images.array[Images.battleGoblinIndex], (int) point.getX(), (int) point.getY(), (int) (100 * SCALE_FACTOR),
					(int) (50 * SCALE_FACTOR), null);
		}
	}
	
	public Point2D getBattleLoc(Entity ent) {
		double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
		double battleRatioX = (696 * SCALE_FACTOR) / (1000 * SCALE_FACTOR); //battle size / normal size
		double battleRatioY = (703 * SCALE_FACTOR) / (1000 * SCALE_FACTOR);
		Point2D battleLoc = new Point2D.Double(ent.location.getX() * (battleRatioX),
				ent.location.getY() * (battleRatioY)); 
		//(0,149)(696, 149)(0,852)(696,852) 696, 703
		return battleLoc;
	}
}
