package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import misc.Entity;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;
import misc.Utilities;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;

public class BattleQueue extends Thread {
	ControlRouter control;
	List<Entity> initList;

	Utilities utilities = new Utilities();

	public BattleQueue(ControlRouter p, List<Entity> i) {
		control = p;
		initList = i;
	}

	public void run() {
		StandardRoom currentRoom = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		while (checkLiving(currentRoom) && !control.flee) {
			for (int i = 0; i < initList.size(); i++) {
				Entity currentEntity = initList.get(i);
				if (control.flee) {return;}
				if (isEnemy(currentEntity)) {
					currentEntity.attack(SimpleDungeonCrawler.character);
				} else if (isFriendly(currentEntity)) {
					control.playerTurn();
					control.switchToAttackPhase();
				} else {
					printEntityError(initList.get(i));
				}
				sleep(1000);
				// checkHealth(currentRoom);
			}
			System.out.println("New Turn"); // TODO xp reward for less rounds?
		}
	}

	private boolean checkLiving(StandardRoom current) {
		boolean fAlive = false;
		boolean eAlive = false;
		if (SimpleDungeonCrawler.character.stats.getHealth() <= 0) {
			fAlive = true;
		}
		for (int i = 0; i < current.entities.size(); i++) {
			if (current.entities.get(i).stats.getHealth() <= 0) {
				eAlive = true;
			}
		}
		if (fAlive && !eAlive) {
			// System.out.println("VICTORY!");
			return false;
		} else if (!fAlive && eAlive) {
			// System.out.println("DEFEAT");
			return false;
		} else {
			// System.out.println("CONTINUE THE BATTLE");
			return true;
		}
	}

	private void enemyAttack() {

	}

	private boolean isEnemy(Entity ent) {
		return ent.getType().equals("Enemy");
	}

	private boolean isFriendly(Entity ent) {
		return ent.getType().equals("Friendly");
	}

	private void printEntityError(Entity ent) {
		System.out.print("invalid entity: ");
		System.out.println(ent.getClass().toString());
	}

	private void sleep(int millis) {
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static void bag() {

	}
}
