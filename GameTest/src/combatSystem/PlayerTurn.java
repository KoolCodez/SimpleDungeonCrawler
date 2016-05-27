package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.SwingUtilities;

import misc.Entity;
import misc.SimpleDungeonCrawler;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;

public class PlayerTurn {
	public TurnWait waitForTurn = new TurnWait();
	BattleTurnPanel battleTurnPanel;
	
	public PlayerTurn(BattleTurnPanel b) {
		battleTurnPanel = b;
	}
	
	public void run() {
		switchToTurnPhase();
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
	
	private void switchToTurnPhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.addButtonsToTurnPanel();
			}
		});
	}
	
	public static void bag() {

	}

	public boolean flee(List<Entity> list) {
		boolean successful = false;
		flee = false;
		if (utilities.r20() > 10 + (list.size() - 1) - (character.stats.getDex() / 10)) {
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
