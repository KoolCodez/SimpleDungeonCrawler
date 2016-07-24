package combatSystem;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entities.BattleAI;
import entities.Entity;
import entities.Goblin;
import items.GenericWeapon;
import misc.Images;
import misc.MouseClick;
import misc.SDC;
import misc.Utilities;
import panels.BagPanel;
import panels.BattleAttackPanel;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;
import rooms.HomeRoom;
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
	private Entity[][] entTable;
	public boolean flee = false;

	public ControlRouter() {
		battleTurnPanel = new BattleTurnPanel(this);
		displayBattle(battleTurnPanel);
		character = SDC.character;
		setDefaultWeapon();
		ArrayList<Entity> currentRoomEnts = (ArrayList<Entity>) SDC.roomArray[SDC.loc.x][SDC.loc.y].entities;
		startBattleQueue();
	}

	public void startBattleQueue() {
		StandardRoom currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		List<Entity> initList = setInitiative(currentRoom);
		entTable = new Entity[5][5];

		battleQueue = new BattleQueue(this, initList);
		battleQueue.start();
	}

	public void enemyTurn(Goblin currentEntity) {
		int turnPoints = 5;
		while (turnPoints > 0) {
			String nextMove = currentEntity.battleAI.getNextMove();
			switch (nextMove) {
			case BattleAI.ATTACK_TAG:
				if (turnPoints >= 2) {
					attack(currentEntity, SDC.character);
					turnPoints -= 2;
				} else {
					turnPoints = 0;
				}
				break;
			case BattleAI.MOVE_TOWARD_TAG:
				// turnPoints -= enemyMoveToward(currentEntity, SDC.character,
				// turnPoints);
				break;
			default:
				attack(currentEntity, SDC.character);
				turnPoints -= 2;
				break;
			}
		}
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

	public void victory() {
		System.out.println("Victory!");
		exitBattle("");
	}

	public void defeat() {
		HomeRoom home = (HomeRoom) SDC.roomArray[0][0];
		if (home.villagers.size() > 0) {
			home.villagers.remove((int) (Math.random() * home.villagers.size()));
			Entity character = SDC.character;
			character.stats.setHealth(character.stats.getMaxHealth());
			SDC.loc = new Point(0, 0);
			exitBattle("");
		} else {
			// "game over" screen
		}
	}

	private void exitBattle(String door) {
		System.out.println("exitBattle");
		waitForTurn.endTurn();
		if (!door.equals("")) {
			SDC.roomArray[SDC.loc.x][SDC.loc.y].entities.remove(SDC.character);
			SDC.roomArray[SDC.loc.x][SDC.loc.y].things.remove(SDC.character);
			entTable[character.battleLoc.x][character.battleLoc.y] = null;
			SDC.eventChangeRooms(door);
		}
		switch (door) {
		case "left": SDC.loc.x--;
			break;
		case "right": SDC.loc.x++;
			break;
		case "top": SDC.loc.y--;
			break;
		case "bottom": SDC.loc.y++;
			break;
		default:
			break;
		}
		battleTurnPanel.setVisible(false);
		SDC.frame.add(new CoreGameplayPanel());
		setLocationFromBattle(character);
		
		ArrayList<Entity> currentRoomEnts = (ArrayList<Entity>) SDC.roomArray[SDC.loc.x][SDC.loc.y].entities;
		for (int i = 0; i < currentRoomEnts.size(); i++) {
			setLocationFromBattle(currentRoomEnts.get(i));
		}
		flee = true;
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

	public int battleMove(int xChange, int yChange, Entity ent, int turnPoints) {
		// int xChange = 0;
		// int yChange = 0;
		/*
		 * switch (direction) { case "left": xChange = -1; break; case "right":
		 * xChange = 1; break; case "down": yChange = 1; break; case "up":
		 * yChange = -1; break; default: break; }
		 */
		Point loc = ent.battleLoc;
		if (turnPoints >= 3) {
			if (legalBattleMove(new Point(loc.x + xChange, loc.y + yChange))) {
				entTable[loc.x + xChange][loc.y + yChange] = entTable[loc.x][loc.y];
				entTable[loc.x][loc.y] = null;
				ent.battleLoc = new Point(loc.x + xChange, loc.y + yChange);
				turnPoints -= 3;
				System.out.println(waitForTurn.getTurnPoints() + "-3=" + turnPoints);
			} else {
				System.out.println("There's a person in that space! You kinky devil you!");
			}
		} else {
			System.out.println("out of turnpoints");
		}
		return turnPoints;
	}

	public boolean legalBattleMove(Point destination) {
		
		if (destination.y == 2) {
			if (destination.x == -1) {
				exitBattle("left");
				return false;
			}
			if (destination.x == 5) {
				exitBattle("right");
				return false;
			}
		}
		if (destination.x == 2) {
			if (destination.y == -1) {
				exitBattle("top");
				return false;
			}
			if (destination.y == -1) {
				exitBattle("bottom");
				return false;
			}
		}
		Entity ent = entTable[destination.x][destination.y];
		return ent == null;
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

	public void select(Point point) {

	}

	public void highlight(Entity ent) {
		battleView.highlight(ent);
	}

	public void move(String direction) {
		int newTurnPoints = 0;
		switch (direction) {
		case "left":
			newTurnPoints = battleMove(-1, 0, character, waitForTurn.getTurnPoints());
			break;
		case "right":
			newTurnPoints = battleMove(1, 0, character, waitForTurn.getTurnPoints());
			break;
		case "up":
			newTurnPoints = battleMove(0, -1, character, waitForTurn.getTurnPoints());
			break;
		case "down":
			newTurnPoints = battleMove(0, 1, character, waitForTurn.getTurnPoints());
			break;
		default:
			break;
		}
		waitForTurn.setTurnPoints(newTurnPoints);
	}

	public void setLocationForBattle(String direction) {
		StandardRoom currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		Point enemyPoint;
		Point friendlyPoint;
		boolean horizontal = false;
		switch (direction) {
		case "left":
			enemyPoint = new Point(4, 2);
			friendlyPoint = new Point(0, 2);
			break;
		case "right":
			enemyPoint = new Point(0, 2);
			friendlyPoint = new Point(4, 2);
			break;
		case "top":
			enemyPoint = new Point(2, 4);
			friendlyPoint = new Point(2, 0);
			horizontal = true;
			break;
		case "bottom":
			enemyPoint = new Point(2, 0);
			friendlyPoint = new Point(2, 4);
			horizontal = true;
			break;
		default:
			enemyPoint = new Point(2, 0);
			friendlyPoint = new Point(2, 4);
			horizontal = true;
			break;
		}

		for (int i = 0; i < currentRoom.entities.size(); i++) {
			Entity temp = currentRoom.entities.get(i);
			if (temp.getType().equals("Enemy")) {
				temp.battleLoc = new Point(enemyPoint.x, enemyPoint.y);
				enemyPoint = nextPoint(enemyPoint, horizontal);
			} else if (temp.getType().equals("Friendly")) {
				temp.battleLoc = new Point(friendlyPoint.x, friendlyPoint.y);
				friendlyPoint = nextPoint(friendlyPoint, horizontal);
			}
			entTable[temp.battleLoc.x][temp.battleLoc.y] = temp;
			System.out.println("set " + temp.toString() + " at " + temp.battleLoc.toString());
		}
	}

	private Point nextPoint(Point point, boolean horizontal) {
		int place;
		if (horizontal) {
			place = point.x;
		} else {
			place = point.y;
		}
		switch (place) {
		case 1:
			place = 4;
			break;
		case 2:
			place = 3;
			break;
		case 3:
			place = 1;
			break;
		case 4:
			place = 0;
			break;
		}
		if (horizontal) {
			point.x = place;
		} else {
			point.y = place;
		}
		return point;
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
