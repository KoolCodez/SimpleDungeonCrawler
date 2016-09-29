package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import misc.SDC;
import misc.Utilities;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;
import rooms.StandardRoom;
import things.entities.Entity;
import things.entities.Goblin;

public class BattleQueue extends Thread {
	ControlRouter control;
	ArrayBlockingQueue<Entity> initList;

	Utilities utilities = new Utilities();

	public BattleQueue(ControlRouter p, ArrayBlockingQueue<Entity> i) {
		control = p;
		initList = i;
	}

	public void run() {
		control.switchToQueuePhase();
		StandardRoom currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		while (checkLiving(currentRoom) && !control.flee) {
			for (int i = 0; i < initList.size(); i++) {
				Entity currentEntity = initList.peek();
				if (control.flee || !checkLiving(currentRoom)) {
					return;
				}
				if (isEnemy(currentEntity)) {
					control.enemyTurn(currentEntity);
					//System.out.println("myah!");
				} else if (isFriendly(currentEntity)) {
					control.playerTurn();
					control.switchToQueuePhase();
				} else {
					printEntityError(currentEntity);
				}
				Entity e = initList.remove();
				initList.add(e);
				sleep(1000);
				// checkHealth(currentRoom);
				refreshQueue();
			}
			// TODO xp reward for less rounds?
		}
	}
	
	private void refreshQueue() {
		StandardRoom current = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		for (int i = 0; i < initList.size(); i++) {
			Entity ent = initList.remove();
			boolean isAlive = current.entities.contains(ent);
			if (isAlive || ent.stats.getHealth() > 0) {
				initList.add(ent);
			}
		}
	}

	private boolean checkLiving(StandardRoom current) {
		boolean fAlive = false;
		boolean eAlive = false;
		Entity[] q = initList.toArray(new Entity[initList.size()]);
		for (int i = 0; i < initList.size(); i++) {
			if (q[i].getType().equals("Friendly") || q[i].getType().equals("Character")) {
				fAlive = true;
			} else if(q[i].getType().equals("Enemy")) {
				eAlive = true;
			}
				
		}
		if (fAlive && !eAlive) {
			control.victory();
			return false;
		} else if (!fAlive && eAlive) {
			control.defeat();
			return false;
		} else {
			// System.out.println("CONTINUE THE BATTLE");
			return true;
		}
	}

	private boolean isEnemy(Entity ent) {
		return ent.getType().equals("Enemy");
	}

	private boolean isFriendly(Entity ent) {
		return ent.getType().equals("Friendly") || ent.getType().equals("Character");
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
}
