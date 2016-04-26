package combatSystem;

import java.awt.Component;
import java.awt.Point;
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
import panels.AttackPanel;
import panels.BattleTurnPanel;
import panels.CoreGameplayPanel;
import misc.*;

public class Battle {
	private double SCALED_100 = SimpleDungeonCrawler.SCALED_100;
	private Utilities utilities = new Utilities();
	private Entity character;
	public TurnWait waitForTurn = new TurnWait();
	public boolean flee = false;
	AttackPanel attackPanel = new AttackPanel();
	BattleTurnPanel battleTurnPanel = new BattleTurnPanel(this);
	
	public Battle() {
		character = SimpleDungeonCrawler.character;
		SimpleDungeonCrawler.frame.add(attackPanel);
	}
	
	public void battleSequence() {
		StandardRoom currentRoom = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		while (checkLiving(currentRoom) && !flee) {
			waitForTurn.reset();
			GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.stickItem), "weapon");
			weapon.damage = 1.0;
			weapon.ranged = false;
			weapon.speed = 1.0;
			character.setWeapon(weapon);
			List<Entity> initList = setInitiative(currentRoom);
			for (int i = 0; i < initList.size(); i++) {
				SimpleDungeonCrawler.frame.validate();
				SimpleDungeonCrawler.frame.repaint();
				if (initList.get(i).getType().equals("Enemy") && !flee) {
					initList.get(i).attack(character);
					// System.out.println();
				} else if (initList.get(i).getType().equals("Friendly") && !flee) {
					Component[] variables = SimpleDungeonCrawler.frame.getContentPane().getComponents();
					//Component[] lemmeSeeVariables = Panels.frame.getContentPane().getComponents();
					SwingUtilities.invokeLater(new Runnable() {
					    public void run() {
					    	
					    	attackPanel.setVisible(false);;
							battleTurnPanel.addButtonsToTurnPanel();
							SimpleDungeonCrawler.frame.add(battleTurnPanel);
					    }
					  });
					synchronized (waitForTurn) {
						try {
							System.out.println("wait");
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
					SwingUtilities.invokeLater(new Runnable() {
					    public void run() {
					    	attackPanel.setVisible(true);
					    	SimpleDungeonCrawler.frame.remove(battleTurnPanel);
					    }
					  });
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
				
				// checkHealth(currentRoom);
				SimpleDungeonCrawler.frame.validate();
				SimpleDungeonCrawler.frame.repaint();
			}
			checkLiving(currentRoom);
			SimpleDungeonCrawler.frame.validate();
			SimpleDungeonCrawler.frame.repaint();
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
		SimpleDungeonCrawler.frame.getContentPane().addMouseListener(mouse);
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
			if (Math.abs(x - point.x) * (1/SCALED_100) + Math.abs(y - point.y)  * (1/SCALED_100) < waitForTurn.getTurnPoints()) {
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
			battleTurnPanel.setVisible(false);
			SimpleDungeonCrawler.frame.add(new CoreGameplayPanel());
			successful = true;
		}
		return successful;
	}

}
