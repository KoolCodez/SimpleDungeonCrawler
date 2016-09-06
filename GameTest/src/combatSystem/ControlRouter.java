package combatSystem;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import effects.FallingDamageNumber;
import effects.FallingMiss;
import effects.Swipe;
import misc.Images;
import misc.MouseClick;
import misc.SDC;
import misc.Utilities;
import panels.BagPanel;
import panels.BattleAttackPanel;
import panels.BattleSideBar;
import panels.BattleTurnPanel;
import panels.BattleViewPanel;
import panels.CoreGameplayPanel;
import rooms.HomeRoom;
import rooms.StandardRoom;
import things.BattleAI;
import things.Nothing;
import things.entities.Entity;
import things.entities.Goblin;
import things.entities.Villager;
import things.items.weapons.Weapon;

public class ControlRouter {
	public BattleViewPanel battleView;
	private BattleTurnPanel battleTurnPanel;
	private BattleAttackPanel attackPanel;
	BagPanel bagPanel;
	private BattleSideBar sideBar;
	private BattleQueue battleQueue;
	private Utilities utilities = new Utilities();
	private Entity character;
	public TurnWait waitForTurn = new TurnWait();
	private Entity[][] entTable;
	public boolean flee = false;
	public Point selected;

	public ControlRouter() {
		battleTurnPanel = new BattleTurnPanel(this);
		displayBattle(battleTurnPanel);
		sideBar = new BattleSideBar(this, new Nothing());
		character = SDC.character;
		ArrayList<Entity> currentRoomEnts = (ArrayList<Entity>) SDC.roomArray[SDC.loc.x][SDC.loc.y].entities;
		startBattleQueue();
	}

	public void startBattleQueue() {
		StandardRoom currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		ArrayBlockingQueue<Entity> queue = setInitiative(currentRoom);
		entTable = new Entity[5][5];

		battleQueue = new BattleQueue(this, queue);
		battleQueue.start();
	}
	
	private void displayBattle(JPanel panel) {
		battleView = new BattleViewPanel(this);
		panel.add(battleView);
	}

	public void drawQueue(Graphics g) {
		StandardRoom current = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		Point loc = new Point((int) (800 * SDC.SCALE_FACTOR), (int) (550 * SDC.SCALE_FACTOR));
		Entity[] q = battleQueue.initList.toArray(new Entity[battleQueue.initList.size()]);
		for (int i = 0; i < q.length; i++) {
			Entity e = q[i];
			Image image = e.getImage();
			image = image.getScaledInstance((int) (50 * SDC.SCALE_FACTOR), (int) (50 * SDC.SCALE_FACTOR),
					Image.SCALE_SMOOTH);
			g.drawImage(image, loc.x, loc.y, null);
			loc.y += (int) (60 * SDC.SCALE_FACTOR);
		}
	}
	
	public Entity getSelectedEnt() {
		return entTable[selected.x][selected.y];
	}

	public void enemyTurn(Goblin currentEntity) {
		int turnPoints = 6;
		while (turnPoints > 0) {
			turnPoints = currentEntity.battleAI.nextMove(turnPoints, entTable, this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Point target = SDC.character.battleLoc;
		turnToward(target.x, target.y, currentEntity);
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
			int randIndex = (int) (Math.random() * home.villagers.size());
			Villager v = home.villagers.get(randIndex);
			home.villagers.remove(v);
			home.entities.remove(v);
			home.things.remove(v);
			SDC.character.stats.setHealth(character.stats.getMaxHealth());
			SDC.loc = new Point(0, 0);
			SDC.character.setRoom(SDC.roomArray[SDC.loc.x][SDC.loc.y]);
			exitBattle("");
		} else {
			// "game over" screen
		}
	}

	private void exitBattle(String door) {
		System.out.println("exitBattle: " + door);
		waitForTurn.endTurn();
		if (!door.equals("")) {
			SDC.roomArray[SDC.loc.x][SDC.loc.y].entities.remove(SDC.character);
			SDC.roomArray[SDC.loc.x][SDC.loc.y].things.remove(SDC.character);
			entTable[character.battleLoc.x][character.battleLoc.y] = null;
		}
		// character.setImage(Images.array[Images.battleCharIndex]);
		switch (door) {
		case "left":
			SDC.loc.x--;
			door = "right";
			break;
		case "right":
			SDC.loc.x++;
			door = "left";
			break;
		case "top":
			SDC.loc.y--;
			door = "bottom";
			break;
		case "bottom":
			SDC.loc.y++;
			door = "top";
			break;
		default:
			break;
		}
		battleView.setVisible(false);
		sideBar.setVisible(false);
		battleTurnPanel.setVisible(false);
		SDC.frame.add(new CoreGameplayPanel());
		setLocationFromBattle(character, door);
		if (!door.equals("")) {
			SDC.eventChangeRooms(door);
		}
		// ArrayList<Entity> currentRoomEnts = (ArrayList<Entity>)
		// SDC.roomArray[SDC.loc.x][SDC.loc.y].entities;
		// for (int i = 0; i < currentRoomEnts.size(); i++) {
		// setLocationFromBattle(currentRoomEnts.get(i));
		// }
		flee = true;
	}

	public void switchToQueuePhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.disableButtons();
				// battleTurnPanel.removeAll();
				// displayBattle(battleTurnPanel);
			}
		});
	}

	public void switchToTurnPhase() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				battleTurnPanel.setVisible(true);
				// battleTurnPanel.addButtonsToTurnPanel();
				battleTurnPanel.add(battleView);
				battleTurnPanel.enableButtons();
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

	public void charAttack() {
		Entity target = getSelectedEnt();
		attack(character, target);
	}

	public void attack(Entity attacker, Entity target) {
		
		if (target != null) {
			if (legalAttack(attacker, target)) {
				System.out.println(attacker.getName() + attacker.getType() + " Attack!");
				if (target.stats.getDex() - attacker.stats.getDex()
						+ 10 < utilities.r20()) {
					double damage = 0.0;
					damage = (attacker.stats.getStr() / target.stats.getStr() * attacker.equipped.weapon.damage)
							/ target.equipped.combinedAC();
					target.damageEnt(damage);
					System.out.println("He Hit For " + damage + "Damage!");
					displayDamage(damage, attacker, target);
				} else {
					System.out.println("He Missed!");
					displayMiss(attacker);
				}
			} else {
				System.out.println("Out of reach!");
			}
		}
	}

	private boolean legalAttack(Entity attacker, Entity target) {
		int xDist = Math.abs(attacker.battleLoc.x - target.battleLoc.x);
		int yDist = Math.abs(attacker.battleLoc.y - target.battleLoc.y);
		if (attacker.getType().equals(target.getType())) {
			return false;
		}
		if (attacker.equipped.weapon.ranged) {
			return Math.sqrt((xDist * xDist) * (yDist * yDist)) <= attacker.equipped.weapon.reach;
		} else {
			return xDist + yDist <= 1;
		}
	}
	
	public void shove(Entity attacker, Entity target) {
		if (target != null) {
			if (legalShove(attacker, target)) {
				System.out.println(attacker.getName() + attacker.getType() + " Shove!");
				if (target.stats.getDex() - attacker.stats.getDex()
						+ 8 < utilities.r20()) {
					push(attacker, target);
					System.out.println("Shove Succesful!");
				} else {
					System.out.println("He Missed!");
					displayMiss(attacker);
				}
				//double damage = 0.0;
				//if (attacker.stats.getDex() - target.stats.getDex()
					//	+ 10 < 20/* utilities.r20() */) {
					/*damage = (target.stats.getStr() / attacker.stats.getStr() * target.getWeapon().damage)
							/ attacker.stats.getAC();
					attacker.stats.setHealth(-damage);
					System.out.println(target.toString() + " Hit For " + damage + "Damage!");
					displayDamage(damage, target, attacker);
					//Note, target and attacker are reversed here, 
					//since that is the direction damage is dealt
				} else {
					System.out.println(target.toString() + " Missed!");
				}*/
			} else {
				System.out.println("Out of reach!");
			}
		}
		
		
	}
	
	private boolean legalShove(Entity attacker, Entity target) {
		int xDist = Math.abs(attacker.battleLoc.x - target.battleLoc.x);
		int yDist = Math.abs(attacker.battleLoc.y - target.battleLoc.y);
		return xDist + yDist <= 1;
	}
	
	private void push(Entity attacker, Entity target) {
		int xDist = target.battleLoc.x - attacker.battleLoc.x;
		int yDist = target.battleLoc.y - attacker.battleLoc.y;
		Entity target2 = entTable[target.battleLoc.x + xDist][target.battleLoc.y + yDist];
		if (target2 != null) {
			push(target, target2);
		} else {
			battleMove(xDist, yDist, target, 3);
		}
	}
	
	private void displayDamage(double damage, Entity attacker, Entity target) {
		Point2D doublePoint = attacker.getLocation();
		Point location = new Point((int) doublePoint.getX(), (int) doublePoint.getY() + 10);
		Swipe s = new Swipe(location);
		s.fixLocation(attacker, target);
		battleView.addEffect(s);
		if (damage > 0) {
			final double d = damage;
			Point2D doublePointEnemy = target.getLocation();
			Point locationEnemy = new Point((int) doublePointEnemy.getX(), (int) doublePointEnemy.getY() + 10);
			SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
				@Override
				protected Integer doInBackground() throws Exception {
					battleView.addEffect(new FallingDamageNumber(d, locationEnemy));
					EntityShaker shaker = new EntityShaker(target);
					shaker.run();
					return 0;
				}
			};
			worker.execute();
		}
	}
	
	private void displayMiss(Entity attacker) {
		Point2D doublePoint = attacker.getLocation();
		Point location = new Point((int) doublePoint.getX(), (int) doublePoint.getY() + 10);
		SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
				battleView.addEffect(new FallingMiss(location));
				return 0;
			}
		};
		worker.execute();
	}

	public void select(Point p) {
		selected = p;
		Point scaledPoint = new Point((int) (p.x * 140 * SDC.SCALE_FACTOR), (int) (p.y * 140 * SDC.SCALE_FACTOR));
		battleView.highlight(scaledPoint);
		Entity ent = entTable[p.x][p.y];
		SDC.frame.remove(sideBar);
		if (ent != null) {
			sideBar = new BattleSideBar(this, ent);
		} else {
			sideBar = new BattleSideBar(this, new Nothing());
		}
		turnToward(p.x, p.y, SDC.character);
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

	public int battleMove(int xChange, int yChange, Entity ent, int turnPoints) {
		Point loc = ent.battleLoc;
		if (turnPoints >= 3) {
			turnToward(loc.x + xChange, loc.y + yChange, ent);
			if (legalBattleMove(new Point(loc.x + xChange, loc.y + yChange))) {
				entTable[loc.x + xChange][loc.y + yChange] = entTable[loc.x][loc.y];
				entTable[loc.x][loc.y] = null;
				ent.battleLoc = new Point(loc.x + xChange, loc.y + yChange);
				turnPoints -= 3;
				setEntLocation(ent);
			} else {
				System.out.println("Illegal Move");
			}
		} else {
			System.out.println("out of turnpoints");
		}
		return turnPoints;
	}
	
	public void turnToward(int xTarget, int yTarget, Entity ent) {
		int xChange = xTarget - ent.battleLoc.x;
		int yChange = yTarget - ent.battleLoc.y;
		if (Math.abs(xChange) > Math.abs(yChange) && xTarget != ent.battleLoc.x) {
			if (xChange > 0) {
				ent.setAngle(90);
			} else if (xChange < 0) {
				ent.setAngle(-90);
			}
		}
		if(Math.abs(yChange) > Math.abs(xChange) && yTarget != ent.battleLoc.y) {
			if (yChange > 0) {
				ent.setAngle(180);
			} else if (yChange < 0) {
				ent.setAngle(0);
			}
		}
	}

	private boolean legalBattleMove(Point destination) {

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
			if (destination.y == 5) {
				exitBattle("bottom");
				return false;
			}
		}
		Entity ent = entTable[destination.x][destination.y];
		if (ent == null) {
			return true;
		} else {
			System.out.println("There's a person in that space! You kinky devil you!");
			return false;
		}
		
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
			setEntLocation(temp);
			entTable[temp.battleLoc.x][temp.battleLoc.y] = temp;
			// System.out.println("set " + temp.toString() + " at " +
			// temp.battleLoc.toString());
		}
	}

	private void setEntLocation(Entity ent) {
		int scale = (int) (140 * SDC.SCALE_FACTOR);
		int scaled20 = (int) (20 * SDC.SCALE_FACTOR);
		ent.setLocation(ent.battleLoc.x * scale + scaled20, ent.battleLoc.y * scale + scaled20);
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

	private void setLocationFromBattle(Entity ent, String door) {
		switch (door) {
		case "left":
			character.getLocation().setLocation(100 * SDC.SCALE_FACTOR, 500 * SDC.SCALE_FACTOR);
			break;
		case "right":
			character.getLocation().setLocation(800 * SDC.SCALE_FACTOR, 500 * SDC.SCALE_FACTOR);
			break;
		case "top":
			character.getLocation().setLocation(500 * SDC.SCALE_FACTOR, 100 * SDC.SCALE_FACTOR);
			break;
		case "bottom":
			character.getLocation().setLocation(500 * SDC.SCALE_FACTOR, 800 * SDC.SCALE_FACTOR);
			break;
		default:
			character.getLocation().setLocation(500 * SDC.SCALE_FACTOR, 500 * SDC.SCALE_FACTOR);
			break;
		}
	}

	private ArrayBlockingQueue<Entity> setInitiative(StandardRoom current) {
		ArrayBlockingQueue<Entity> q = new ArrayBlockingQueue<Entity>(current.entities.size());
		List<Entity> eList = new ArrayList<Entity>();
		eList.addAll(current.entities);
		System.out.println(current.entities.toString());
		int size = eList.size();
		for (int i = 0; i < size; i++) {
			Entity randomE = eList.get((int) Math.round(Math.random() * (eList.size() - 1)));
			System.out.println(randomE);
			eList.remove(randomE);
			q.add(randomE);
		}
		return q;
	}
}
