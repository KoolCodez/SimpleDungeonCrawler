package combatSystem;

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

public class ControlRouter {
	private BattleViewPanel battleView;
	private BattleTurnPanel battleTurnPanel;
	private BattleQueue battleQueue;
	private Utilities utilities = new Utilities();
	private Entity character;
	
	public ControlRouter(BattleTurnPanel p) {
		battleTurnPanel = p;
		character = SimpleDungeonCrawler.character;
	}
	
	public void startBattleQueue() {
		StandardRoom currentRoom = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		List<Entity> initList = setInitiative(currentRoom);
		battleQueue = new BattleQueue(this, initList);
		while (checkLiving(currentRoom)/* && !flee*/) {
			setDefaultWeapon();
			battleQueue.start();
			checkLiving(currentRoom);
			System.out.println("New Turn"); //TODO xp reward for less rounds?
		}
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
	
	public void characterAttack() {
		
	}
	
	public void enemyAttack() {
		
	}
	
	public void displayBattle() {
		battleView = new BattleViewPanel();
		SimpleDungeonCrawler.frame.add(battleView);
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

	public boolean checkLiving(StandardRoom current) {
		boolean fAlive = false;
		boolean eAlive = false;
		if (character.stats.getHealth() <= 0) {
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
	
	private void setDefaultWeapon() { //TODO this is temporary, should go away when inventory is fixed
		GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.array[Images.stickItemIndex]), "weapon");
		weapon.damage = 1.0;
		weapon.ranged = false;
		weapon.speed = 1.0;
		character.setWeapon(weapon);
	}
}
