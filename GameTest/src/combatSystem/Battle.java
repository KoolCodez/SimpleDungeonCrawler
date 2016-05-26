package combatSystem;

import java.awt.Component;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import items.GenericWeapon;
import misc.Entity;
import misc.Images;
import misc.MouseClick;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;
import misc.Utilities;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;
import misc.*;

public class Battle {
	private double SCALED_100 = SimpleDungeonCrawler.SCALED_100;
	private Utilities utilities = new Utilities();
	private Entity character;
	
	

	public Battle() {
		character = SimpleDungeonCrawler.character;
	}
	
	public void characterAttack(BattleViewPanel battleView) {
		if (waitForTurn.getTurnPoints() >= 3) {
			waitForTurn.setTurnPoints(-3);
			Entity targetedEntity = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y].entities
					.get(SimpleDungeonCrawler.character.getSelectedEntity());
			double damage = SimpleDungeonCrawler.character.attack(targetedEntity);
			Point2D doublePoint = targetedEntity.getLocation();
			Point location = new Point((int) doublePoint.getX(), (int) doublePoint.getY());
			FallingDamageNumber currentFallingDamage = new FallingDamageNumber(damage, location);
			battleView.addDamageNumber(currentFallingDamage);
			currentFallingDamage.start();
		} else {
			System.out.println("Not enough turn points");
		}
	}
	
	
	public static void bag() {

	}

	

}
