package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import misc.Entity;
import misc.SimpleDungeonCrawler;
import misc.Utilities;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;

public class BattleQueue extends Thread {
	ControlRouter control;
	List<Entity> initList;
	public TurnWait waitForTurn = new TurnWait();
	public boolean flee = false;
	Utilities utilities = new Utilities();
	
	public BattleQueue(ControlRouter p, List<Entity> i) {
		control = p;
		initList = i;
	}
	
	public void run() {
		for (int i = 0; i < initList.size(); i++) {
			Entity currentEntity = initList.get(i);
			if (isEnemy(currentEntity) && !flee) {
				currentEntity.attack(SimpleDungeonCrawler.character);
			} else if (isFriendly(currentEntity) && !flee) {
				playerTurn();
				control.switchToAttackPhase();
			} else {
				printEntityError(initList.get(i));
			}
			sleep(1000);
			// checkHealth(currentRoom);
		}
	}
	
	private boolean isEnemy(Entity ent) {
		return ent.getType().equals("Enemy");
	}

	private boolean isFriendly(Entity ent) {
		return ent.getType().equals("Friendly");
	}

	private void printEntityError(Entity ent) {
		System.out.print("invalid entity");
		System.out.println(ent.getClass().toString());
	}
	
	private void sleep(int millis) {
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public void playerTurn() {
		control.switchToTurnPhase();
		letPlayerTakeTurn();
		if (flee) {
			if (flee(initList)) {
				return;
			}
		}
		waitForTurn.reset();
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
	
	private void letPlayerTakeTurn() {
		synchronized (waitForTurn) {
			try {
				System.out.println("wait");
				waitForTurn.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void bag() {

	}

	public boolean flee(List<Entity> list) {
		boolean successful = false;
		flee = false;
		if (utilities.r20() > 10 + (list.size() - 1) - (SimpleDungeonCrawler.character.stats.getDex() / 10)) {
			flee = true;
			battleTurnPanel.setVisible(false);
			if (SimpleDungeonCrawler.loc.x > 0) {
				SimpleDungeonCrawler.loc.x--; //TODO this is probably not right
			} else if (SimpleDungeonCrawler.loc.y > 0) {
				SimpleDungeonCrawler.loc.y--;
			}
			//SimpleDungeonCrawler.eventChangeRooms("right");
			SimpleDungeonCrawler.frame.add(new CoreGameplayPanel());
			successful = true;
		}
		return successful;
	}
}
