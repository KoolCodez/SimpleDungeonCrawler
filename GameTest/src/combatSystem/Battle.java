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
	public TurnWait waitForTurn = new TurnWait();
	public boolean flee = false;
	BattleTurnPanel battleTurnPanel = new BattleTurnPanel(this);

	public Battle() {
		character = SimpleDungeonCrawler.character;
		SimpleDungeonCrawler.frame.add(battleTurnPanel);
	}
	
	public void battleSequence() {
		StandardRoom currentRoom = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		List<Entity> initList = setInitiative(currentRoom);
		while (checkLiving(currentRoom) && !flee) {
			waitForTurn.reset();
			setDefaultWeapon();
			runThroughBattleQueue(initList);
			checkLiving(currentRoom);
			SimpleDungeonCrawler.frame.validate();
			SimpleDungeonCrawler.frame.repaint();
			System.out.println("New Turn"); //TODO reward for less rounds?
		}
	}
	
	public void characterAttack(BattleViewPanel battleView) {
		if (waitForTurn.getTurnPoints() >= 3) {
			waitForTurn.setTurnPoints(-3);
			double damage = SimpleDungeonCrawler.character.attack(
					SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y].entities
							.get(SimpleDungeonCrawler.character.getSelectedEntity()));
			Point2D doublePoint = SimpleDungeonCrawler.character.getLocation();
			Point location = new Point((int) doublePoint.getX(), (int) doublePoint.getY());
			FallingDamageNumber currentFallingDamage = new FallingDamageNumber(damage, location);
			battleView.addDamageNumber(currentFallingDamage);
			currentFallingDamage.start();
		} else {
			System.out.println("Not enough turn points");
		}
	}

	private boolean isEnemy(Entity ent) {
		return ent.getType().equals("Enemy");
	}

	private boolean isFriendly(Entity ent) {
		return ent.getType().equals("Friendly");
	}

	private void switchToTurnPhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.addButtonsToTurnPanel();
			}
		});
	}
	
	private void switchToAttackPhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.removeAll();
			}
		});
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
	
	private void printEntityError(Entity ent) {
		System.out.print("invalid entity");
		System.out.println(ent.getClass().toString());
	}
	
	private void setDefaultWeapon() { //TODO this is temporary, should go away when inventory is fixed
		GenericWeapon weapon = new GenericWeapon(new ImageIcon(Images.array[Images.stickItemIndex]), "weapon");
		weapon.damage = 1.0;
		weapon.ranged = false;
		weapon.speed = 1.0;
		character.setWeapon(weapon);
	}
	
	private void sleep(int millis) {
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	private void runThroughBattleQueue(List<Entity> initList) {
		for (int i = 0; i < initList.size(); i++) {
			Entity currentEntity = initList.get(i);
			if (isEnemy(currentEntity) && !flee) {
				currentEntity.attack(character);
			} else if (isFriendly(currentEntity) && !flee) {
				switchToTurnPhase();
				letPlayerTakeTurn();
				if (flee) {
					if (flee(initList)) {
						return;
					}
				}
				switchToAttackPhase();
			} else {
				printEntityError(initList.get(i));
			}
			sleep(1000);
			// checkHealth(currentRoom);
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
		if (Math.abs(x - point.x) * (1 / SCALED_100) + Math.abs(y - point.y) * (1 / SCALED_100) < waitForTurn
				.getTurnPoints()) {
			// TODO MAKE THIS CHANGE LOCATION AND OR BATTLE LOCATION
			// possibly make setBattleLocation change location in a backwards
			// orientation?
			// ALSO THIS IS GLITCHING, so...
			System.out.println("legalClick");
		} else {
			System.out.println("illegal, u r haxor");
		}
		// TODO MOVEMENT
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
			SimpleDungeonCrawler.eventChangeRooms("right");
			SimpleDungeonCrawler.frame.add(new CoreGameplayPanel());
			successful = true;
		}
		return successful;
	}

}
