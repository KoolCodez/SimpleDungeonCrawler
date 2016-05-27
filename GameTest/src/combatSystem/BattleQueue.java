package combatSystem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import misc.Entity;
import misc.SimpleDungeonCrawler;
import panels.BattleTurnPanel;

public class BattleQueue extends Thread {
	BattleTurnPanel battleTurnPanel;
	List<Entity> initList;
	
	public BattleQueue(BattleTurnPanel p, List<Entity> i) {
		battleTurnPanel = p;
		initList = i;
	}
	
	
	public void run() {
		for (int i = 0; i < initList.size(); i++) {
			Entity currentEntity = initList.get(i);
			if (isEnemy(currentEntity) && !flee) {
				currentEntity.attack(SimpleDungeonCrawler.character);
			} else if (isFriendly(currentEntity) && !flee) {
				PlayerTurn playerTurn = new PlayerTurn(battleTurnPanel);
				playerTurn.run();
				switchToAttackPhase();
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

	private void switchToAttackPhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.removeAll();
				battleTurnPanel.addBattleViewPanel();
			}
		});
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
}
