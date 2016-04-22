package combatSystem;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

import items.GenericWeapon;
import misc.Entity;
import misc.Images;
import misc.MouseClick;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;
import misc.Utilities;
import panels.AttackPanel;
import panels.CoreGameplayPanel;
import panels.Panels;

public class Battle {
	private Utilities utilities = new Utilities();
	private Entity character;
	public TurnWait waitForTurn = new TurnWait();
	public boolean flee = false;
	public Battle() {
		character = SimpleDungeonCrawler.character;
	}
	
	public void battleSequence() {
		AttackPanel attackPanel = new AttackPanel();
		StandardRoom currentRoom = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		while (checkLiving(currentRoom) && !flee) {
			GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.stickItem), "weapon");
			weapon.damage = 1.0;
			weapon.ranged = false;
			weapon.speed = 1.0;
			character.setWeapon(weapon);
			List<Entity> initList = setInitiative(currentRoom);
			for (int i = 0; i < initList.size(); i++) {
				Panels.frame.validate();
				Panels.frame.repaint();
				if (initList.get(i).getType().equals("Enemy") && !flee) {
					initList.get(i).attack(character);
					// System.out.println();
				} else if (initList.get(i).getType().equals("Friendly") && !flee) {
					attackPanel.addButtonsToAttackPanel();
					System.out.println("Created Buttons");
					synchronized (waitForTurn) {
						try {
							waitForTurn.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (flee) {
						flee = false;
						if (flee(initList)) {
							return;
						}
					}
					attackPanel.removeButtonsFromAttackPanel();
					//characterAttack(currentRoom.enemyEntities.get(selectedEnemy));
					// System.out.println();
				} else {
					// System.out.println(initList.get(i).getClass().toString());
				}
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				waitForTurn.reset();
				// checkHealth(currentRoom);
				Panels.frame.validate();
				Panels.frame.repaint();
			}
			checkLiving(currentRoom);
			Panels.frame.validate();
			Panels.frame.repaint();
			System.out.println("New Turn");
		}
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
			//System.out.println("VICTORY!");
			return false;
		} else if (!fAlive && eAlive) {
			//System.out.println("DEFEAT");
			return false;
		} else {
			//System.out.println("CONTINUE THE BATTLE");
			return true;
		}
	}

	public void move() {
		MouseClick mouse = new MouseClick();
		Panels.frame.getContentPane().addMouseListener(mouse);
		synchronized (mouse) {
			try {
				mouse.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
			Point point = mouse.getLocation();
			double x = character.getBattleLoc().getX();
			double y = character.getBattleLoc().getY();
			if (Math.abs(x - point.x) * (1/Panels.SCALED_100) + Math.abs(y - point.y)  * (1/Panels.SCALED_100) < waitForTurn.getTurnPoints()) {
				//TODO MAKE THIS CHANGE LOCATION AND OR BATTLE LOCATION
				//possibly make setBattleLocation change location in a backwards orientation?
				//ALSO THIS IS GLITCHING, so...
				System.out.println("legalClick");
		} else {
			System.out.println("illegal, u r haxor");
		}
		//TODO MOVEMENT
	}

	public static void bag() {

	}

	public boolean flee(List<Entity> list) {
		boolean successful = false;
		if (utilities.r20() > 10 + (list.size() - 1) - (character.stats.getDex() / 10)) { //TODO speed rather than dex
			Panels.frame.removeAll();
			Panels.frame.add(new CoreGameplayPanel().getPanel());
			successful = true;
		}
		return successful;
	}

}
