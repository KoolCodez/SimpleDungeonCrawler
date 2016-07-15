package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import items.GenericWeapon;
import misc.Entity;
import misc.Images;
import misc.MouseClick;
import misc.SDC;
import misc.Utilities;
import panels.BagPanel;
import panels.BattleAttackPanel;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;
import rooms.StandardRoom;

public class ControlRouter {
	public BattleViewPanel battleView;
	private BattleTurnPanel battleTurnPanel;
	private BattleAttackPanel attackPanel;
	BagPanel bagPanel;
	private BattleQueue battleQueue;
	private Utilities utilities = new Utilities();
	private Entity character;
	public TurnWait waitForTurn = new TurnWait();
	public boolean flee = false;

	public ControlRouter() {
		battleTurnPanel = new BattleTurnPanel(this);
		displayBattle(battleTurnPanel);
		character = SDC.character;
		setDefaultWeapon();
		setLocationForBattle(character);
		ArrayList<Entity> currentRoomEnts = (ArrayList<Entity>) SDC.roomArray[SDC.loc.x][SDC.loc.y].entities;
		for (int i = 0; i < currentRoomEnts.size(); i++) {
			setLocationForBattle(currentRoomEnts.get(i));
		}
		startBattleQueue();
	}

	public void startBattleQueue() {
		StandardRoom currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		List<Entity> initList = setInitiative(currentRoom);
		battleQueue = new BattleQueue(this, initList);
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
		if (utilities.r20() > 10 + (list.size() - 1) - (SDC.character.stats.getDex() / 10)) {
			flee = true;
			fleeBattle();
			successful = true;
		}
		waitForTurn.endTurn();
		return successful;
	}

	public void fleeBattle() {
		battleTurnPanel.setVisible(false);
		if (SDC.loc.x > 0) {
			SDC.loc.x--;
		} else if (SDC.loc.y > 0) {
			SDC.loc.y--;
		}
		character.setRoom(SDC.roomArray[SDC.loc.x][SDC.loc.y]);
		// SimpleDungeonCrawler.eventChangeRooms("right");
		SDC.frame.add(new CoreGameplayPanel());
		setLocationFromBattle(character);
		ArrayList<Entity> currentRoomEnts = (ArrayList<Entity>) SDC.roomArray[SDC.loc.x][SDC.loc.y].entities;
		for (int i = 0; i < currentRoomEnts.size(); i++) {
			setLocationFromBattle(currentRoomEnts.get(i));
		}
	}

	public void switchToQueuePhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.removeAll();
				displayBattle(battleTurnPanel);
			}
		});
	}

	public void switchToTurnPhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.setVisible(true);
				battleTurnPanel.addButtonsToTurnPanel();
				battleTurnPanel.add(battleView);
			}
		});
	}
	
	public void switchToAttackPhase() {
		battleTurnPanel.setVisible(false);
		attackPanel = new BattleAttackPanel(this);
		attackPanel.add(battleView);
	}
	
	public void switchToBagPanel() {
		battleTurnPanel.setVisible(false);
		bagPanel = new BagPanel(this);
	}

	public void attack(Entity attacker, Entity target) {
		double damage = attacker.meleeAttack(target);
		if (damage > 0) {
			Point2D doublePoint = target.getLocation();
			Point location = new Point((int) doublePoint.getX(), (int) doublePoint.getY());
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					battleView.displayDamage(damage, location);
					EntityShaker shaker = new EntityShaker(target);
					shaker.run();
				}
			});
		}
	}
	
	public void selectEntity() {
		
		MouseClick mouse = new MouseClick();
		SDC.frame.getContentPane().addMouseListener(mouse);
		Entity character = SDC.character;
		battleView.attackRadius = character.getWeapon().reach;
		StandardRoom currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		synchronized (mouse) {
			try {
				mouse.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		Point mousePoint = mouse.getLocation();
		Point mouseInPanel = new Point((int) (mousePoint.getX() - 10), (int) (mousePoint.getY() - 139));
		Entity target = currentRoom.entities.get(0);
		double lastDist = mouseInPanel.distance(target.location);
		for (int i = 1; i < currentRoom.entities.size(); i++) {
			Point2D enemyLoc = currentRoom.entities.get(i).location;
			if (mouseInPanel.distance(enemyLoc) < lastDist) {
				target = currentRoom.entities.get(i);
				lastDist = mouseInPanel.distance(enemyLoc);
			}
		}
		character.setSelectedEntity(target);
		battleView.highlight(target);
		battleView.attackRadius = -1.0;
	}
	
	public void highlight(Entity ent) {
		battleView.highlight(ent);
	}
	
	public void move() {
		int turnPoints = waitForTurn.getTurnPoints();
		MouseClick mouse = new MouseClick();
		SDC.frame.getContentPane().addMouseListener(mouse);
		Entity character = SDC.character;
		int SCALED_100 = SDC.SCALED_100;
		battleView.moveRadius = turnPoints * SCALED_100;
		synchronized (mouse) {
			try {
				mouse.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		Point mousePoint = mouse.getLocation();
		
		Point mouseInPanel = new Point((int) (mousePoint.getX() - 10), (int) (mousePoint.getY() - 139));
		Point2D charPoint= character.getLocation();
		double deltaX = -(charPoint.getX() - mouseInPanel.x);
		double deltaY = -(charPoint.getY() - mouseInPanel.y);
		double newX = charPoint.getX() + deltaX;
		double newY = charPoint.getY() + deltaY;
		boolean xWithinBounds = newX > 40 && newX < 656;
		boolean yWithinBounds = newY > 40 && newY < 663;
		boolean distWithinMaxDist = mouseInPanel.distance(charPoint) < turnPoints*SCALED_100;
		if (xWithinBounds && yWithinBounds && distWithinMaxDist) {
			double pointsBasedOnDist = Math.abs(mouseInPanel.distance(charPoint))/ SCALED_100;
			waitForTurn.setTurnPoints((int) Math.ceil(pointsBasedOnDist) * -1);
			character.setLocation(newX, newY);
			// TODO MAKE THIS CHANGE LOCATION AND OR BATTLE LOCATION
			// possibly make setBattleLocation change location in a backwards
			// orientation?
			System.out.println("legalClick");
		} else {
			System.out.println("illegal, u r haxor");
		}
		battleView.moveRadius = -1;
	}
	
	private void setLocationForBattle(Entity ent) {
		double xFactor = 703.0 / 1000.0;
		double yFactor = 696.0 / 1000.0;
		ent.setLocation(ent.location.getX() * xFactor, ent.location.getY() * yFactor);
	}
	
	private void setLocationFromBattle(Entity ent) {
		double xFactor = 1000 / 703;
		double yFactor = 1000 / 696;
		ent.setLocation(ent.location.getX() * xFactor, ent.location.getY() * yFactor);
	}

	public void displayBattle(JPanel panel) {
		battleView = new BattleViewPanel(this);
		panel.add(battleView);
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
		weapon.reach = 100;
		character.setWeapon(weapon);
	}
	
}
