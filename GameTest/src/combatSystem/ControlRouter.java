package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import items.GenericWeapon;
import misc.Entity;
import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;
import misc.Utilities;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;

public class ControlRouter {
	public BattleViewPanel battleView;
	private BattleTurnPanel battleTurnPanel;
	private BattleQueue battleQueue;
	private Utilities utilities = new Utilities();
	private Entity character;
	public TurnWait waitForTurn = new TurnWait();
	public boolean flee = false;

	public ControlRouter() {
		battleTurnPanel = new BattleTurnPanel(this);
		displayBattle();
		character = SimpleDungeonCrawler.character;
		startBattleQueue();
	}

	public void startBattleQueue() {
		StandardRoom currentRoom = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		List<Entity> initList = setInitiative(currentRoom);
		battleQueue = new BattleQueue(this, initList);
		setDefaultWeapon();
		battleQueue.start();

	}

	public void playerTurn() {
		switchToTurnPhase();
		letPlayerTakeTurn();
		waitForTurn.reset();
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

	public boolean flee() {
		List<Entity> list = battleQueue.initList;
		boolean successful = false;
		flee = false;
		if (utilities.r20() > 10 + (list.size() - 1) - (SimpleDungeonCrawler.character.stats.getDex() / 10)) {
			flee = true;
			fleeBattle();
			successful = true;
		}
		waitForTurn.endTurn();
		return successful;
	}

	public void fleeBattle() {
		battleTurnPanel.setVisible(false);
		if (SimpleDungeonCrawler.loc.x > 0) {
			SimpleDungeonCrawler.loc.x--; // TODO this is probably not right
		} else if (SimpleDungeonCrawler.loc.y > 0) {
			SimpleDungeonCrawler.loc.y--;
		}
		// SimpleDungeonCrawler.eventChangeRooms("right");
		SimpleDungeonCrawler.frame.add(new CoreGameplayPanel());

	}

	public void switchToAttackPhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.removeAll();
				displayBattle();
			}
		});
	}

	public void switchToTurnPhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.addButtonsToTurnPanel();
			}
		});
	}

	public void attack(Entity attacker, Entity target) {
		double damage = attacker.attack(target);
		Point2D doublePoint = target.getLocation();
		Point location = new Point((int) doublePoint.getX(), (int) doublePoint.getY());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleView.displayDamage(damage, location);

			}
		});
	}

	public void displayBattle() {
		battleView = new BattleViewPanel();
		battleTurnPanel.add(battleView);
	}

	public List<Entity> setInitiative(StandardRoom current) {
		ArrayList<Entity> initList = new ArrayList<Entity>();
		for (int i = 0; i < current.entities.size(); i++) {
			int r = utilities.r6();
			current.entities.get(i).setInitiative(r);
			initList.add(current.entities.get(i));
		}
		int r = utilities.r6();
		character.setInitiative(r);
		initList.add(character);
		Collections.sort(initList);
		return initList;
	}

	private void setDefaultWeapon() { // TODO this is temporary, should go away
										// when inventory is fixed
		GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.array[Images.stickItemIndex]), "weapon");
		weapon.damage = 1.0;
		weapon.ranged = false;
		weapon.speed = 1.0;
		character.setWeapon(weapon);
	}
}
