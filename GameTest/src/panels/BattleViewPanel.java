package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import combatSystem.Battle;
import combatSystem.FallingDamageNumber;
import misc.Entity;
import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;

public class BattleViewPanel extends JPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private Battle battle;
	private ArrayList<FallingDamageNumber> damageNumbers;
	private Entity character = SimpleDungeonCrawler.character;
	public double moveRadius;
	
	public BattleViewPanel(Battle b) {
		moveRadius = 0;
		battle = b;
		damageNumbers = new ArrayList<FallingDamageNumber>();
		this.setBounds(0, (int) (148 * SCALE_FACTOR), (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR)); //TODO fix pls
	}
	
	public void addDamageNumber(FallingDamageNumber n) {
		damageNumbers.add(n);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.array[Images.battleViewBackgroundIndex], 0, 0, (int) (697 * SCALE_FACTOR), (int) (710 * SCALE_FACTOR), null);
		Point2D charLoc = character.getBattleLoc();
		g.drawImage(Images.array[Images.battleCharIndex], (int) charLoc.getX(), (int) charLoc.getY(),
				(int) (100 * SCALE_FACTOR), (int) (50 * SCALE_FACTOR), null);
		StandardRoom current = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		for (int i = 0; i < current.entities.size(); i++) {
			Point2D point = current.entities.get(i).getBattleLoc();
			g.drawImage(Images.array[Images.battleGoblinIndex], (int) point.getX(), (int) point.getY(), (int) (100 * SCALE_FACTOR),
					(int) (50 * SCALE_FACTOR), null);
		}
		g.setColor(Color.red);
		for (int i = 0; i < damageNumbers.size(); i++) {
			FallingDamageNumber currentNum = damageNumbers.get(i);
			Point point = currentNum.getPoint();
			g.setFont(SimpleDungeonCrawler.font);
			g.drawString(currentNum.getDamage() + "", point.x, point.y);
		}
		Point2D p = SimpleDungeonCrawler.character.getBattleLoc();
		g.drawOval((int) (p.getX() - moveRadius/2), (int) (p.getY() - moveRadius/2), (int) moveRadius, (int) moveRadius);
		g.setColor(Color.black);
		
	}
}
