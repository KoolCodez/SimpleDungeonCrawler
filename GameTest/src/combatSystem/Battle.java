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
	
	public boolean flee = false;
	BattleTurnPanel battleTurnPanel = new BattleTurnPanel(this);

	public Battle() {
		character = SimpleDungeonCrawler.character;
		SimpleDungeonCrawler.frame.add(battleTurnPanel);
	}
	
	public void battleSequence() {
		StandardRoom currentRoom = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		List<Entity> initList = setInitiative(currentRoom);
		BattleQueue battleQueue = new BattleQueue(battleTurnPanel, initList);
		while (checkLiving(currentRoom) && !flee) {
			
			setDefaultWeapon();
			battleQueue.start();
			checkLiving(currentRoom);
			System.out.println("New Turn"); //TODO xp reward for less rounds?
		}
	}
	
	private void setDefaultWeapon() { //TODO this is temporary, should go away when inventory is fixed
		GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.array[Images.stickItemIndex]), "weapon");
		weapon.damage = 1.0;
		weapon.ranged = false;
		weapon.speed = 1.0;
		character.setWeapon(weapon);
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

	
}
