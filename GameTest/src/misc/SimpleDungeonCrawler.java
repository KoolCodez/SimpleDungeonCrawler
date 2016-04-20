package misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import items.GenericItem;
import items.GenericWeapon;
import items.Stick;
import movement.MoveDown;
import movement.MoveDownLeft;
import movement.MoveDownRight;
import movement.MoveLeft;
import movement.MoveRight;
import movement.MoveUp;
import movement.MoveUpLeft;
import movement.MoveUpRight;
import panels.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class SimpleDungeonCrawler extends JPanel {
	public static StandardRoom[][] roomArray = new StandardRoom[10][10];
	public static Point loc = new Point(0, 0);
	// public static Point2D character.getLocation() = new Point2D.Double(250.0,
	// 250.0);
	public static final double SCALE_FACTOR = 1;
	public static FriendlyEntity character;
	public static double playerSpeed = 8 * SCALE_FACTOR;
	public static double diagSpeed = playerSpeed / Math.sqrt(2);
	public static Graphics g;
	public static int refreshRate = 25; // number of millis to wait
	public static int fps = 50;
	public static Font font = new Font("Harrington", Font.BOLD, 18);
	public static TurnWait waitForTurn = new TurnWait();
	public static boolean flee = false;

	public static void main(String[] args) throws InterruptedException, IOException {
		String current = System.getProperty("user.dir");
		// System.out.println("Current working directory in Java : " + current);
		Panels.frame = new JFrame("Simple Dungeon Crawler");
		Panels.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panels.frame.setSize((int) (1000 * SCALE_FACTOR + 16), (int) (1000 * SCALE_FACTOR + 38));
		Panels.frame.setVisible(true);
		g = Panels.frame.getGraphics();
		g.setColor(Color.white);
		character = new FriendlyEntity(5, 10, 10, 10, 10, 10, 10, 1);
		Images.createImages();
		character.addItem(new Stick());
		Panels panelInitializer = new Panels();
		InputMap inMap = Panels.coreGameplayPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap acMap = Panels.coreGameplayPanel.getActionMap();
		Refresh r1 = new Refresh();
		r1.start();
		Point p = new Point(0, 10);
		roomArray[0][0] = new BattleRoom(0);
	}

	

	public static void battleSequence(/* ArrayList<String> console1 */) {
		StandardRoom currentRoom = roomArray[loc.x][loc.y];
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
				if (initList.get(i).getClass().toString().equals("class misc.EnemyEntity") && !flee) {
					enemyAttack((EnemyEntity) initList.get(i)/* , console1 */);
					// System.out.println();
				} else if (initList.get(i).getClass().toString().equals("class misc.FriendlyEntity") && !flee) {
					Panels.frame.remove(Panels.atkPanel);
					Panels.frame.add(Panels.turnPanel);
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
					Panels.frame.remove(Panels.turnPanel);
					Panels.frame.add(Panels.atkPanel);
					//characterAttack(currentRoom.enemyEntities.get(selectedEnemy)/* , console1 */);
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

	public static List<Entity> setInitiative(StandardRoom current) {
		ArrayList<Entity> initList = new ArrayList<Entity>();
		for (int i = 0; i < current.enemyEntities.size(); i++) {
			int r = r6();
			current.enemyEntities.get(i).setInitiative(r);
			initList.add(current.enemyEntities.get(i));
		}
		int r = r6();
		character.setInitiative(r);
		initList.add(character);
		Collections.sort(initList);
		return initList;
	}

	public static boolean checkLiving(StandardRoom current) {
		boolean fAlive = false;
		boolean eAlive = false;
		if (character.getHealth() <= 0) {
			fAlive = true;
		}
		for (int i = 0; i < current.enemyEntities.size(); i++) {
			if (current.enemyEntities.get(i).getHealth() <= 0) {
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

	public static void characterAttack(
			EnemyEntity enemy/* , ArrayList<String> console */) {
		// console.add("Character Attack!");
		System.out.println("Character Attack!");
		// does it hit
		if (enemy.getDex() - character.getDex() + 10 < r20()) {
			// how much damage does it do
			double damage = 0.0;
			damage = (character.getStr() / enemy.getStr() * character.getWeapon().damage) / enemy.getAC();
			// subtract damage
			enemy.setHealth(-damage);
			// console.add("He Hit For " + damage + "Damage!");
			System.out.println("He Hit For " + damage + "Damage!");
		} else {
			// console.add("He Missed!");
			System.out.println("He Missed!");
		}
	}

	public static void enemyAttack(
			EnemyEntity enemy/* , ArrayList<String> console */) {
		// console.add("Enemy Attack!");
		System.out.println("Enemy Attack!");
		if (character.getDex() - enemy.getDex() + 10 < r20()) {
			double damage = 0.0;
			damage = (enemy.getStr() / character.getStr() * enemy.getWeapon().damage) / character.getAC();
			// subtract damage
			character.setHealth(-damage);
			// console.add("He Hit For " + damage + "Damage!");
			System.out.println("He Hit For " + damage + "Damage!");
		} else {
			// console.add("He Missed!");
			System.out.println("He Missed!");
		}
	}

	public static void friendlyAttack() {

	}

	public static void move() {
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

	public static boolean flee(List<Entity> list) {
		boolean successful = false;
		if (r20() > 10 + (list.size() - 1) - (character.getDex() / 10)) { //TODO speed rather than dex
			Panels.frame.remove(Panels.turnPanel);
			Panels.frame.add(Panels.coreGameplayPanel);
			successful = true;
		}
		return successful;
	}

	public static int r20() {
		return new Random().nextInt(20) + 1;
	}

	public static int r6() {
		return new Random().nextInt(6) + 1;
	}

	public static void checkIfLeavingRoom() {
		if ((int) character.getLocation().getY() >= (int) (400*SCALE_FACTOR) && (int) character.getLocation().getY() <= (int) (508*SCALE_FACTOR)) {
			if (character.getLocation().getX() < (int) (50*SCALE_FACTOR) && loc.x != 0) {
				loc.x--;
				eventChangeRooms("right");

			}

			if (character.getLocation().getX() > (int) (888*SCALE_FACTOR) && loc.x != 9) {
				loc.x++;
				eventChangeRooms("left");
			}
		}
		if (character.getLocation().getX() >= (int) (400*SCALE_FACTOR) && character.getLocation().getX() <= (int) (528*SCALE_FACTOR)) {
			if ((int) character.getLocation().getY() < (int) (50*SCALE_FACTOR) && loc.y != 0) {
				loc.y--;
				eventChangeRooms("bottom");
			}

			if ((int) character.getLocation().getY() > (int) (868 * SCALE_FACTOR) && loc.y != 9) {
				loc.y++;
				eventChangeRooms("top");
			}
		}

	}

	public static boolean legalMove(double deltaX, double deltaY) { // character
																	// 46
		// tall, 36
		// wide // wall 34
		boolean isLegal = false;
		double x = character.getLocation().getX() + deltaX;
		double y = character.getLocation().getY() + deltaY;
		double left = x;
		double right = x + 72 * SCALE_FACTOR;
		double top = y;
		double bottom = y + 92 * SCALE_FACTOR;
		if (bottom <= (int) (928*SCALE_FACTOR) && top >= (int) (72*SCALE_FACTOR) && right <= (int) (928*SCALE_FACTOR) && left >= (int) (72*SCALE_FACTOR)) { // main
																		// room
																		// box
			isLegal = true;
		}
		if (bottom <= (int) (600*SCALE_FACTOR) && top >= (int) (400*SCALE_FACTOR) && right <= (int) (1000*SCALE_FACTOR) && left >= (int) (0*SCALE_FACTOR)) { // right
																		// and
																		// left
																		// doors
			isLegal = true;
		}
		if (bottom <= (int) (1000*SCALE_FACTOR) && top >= (int) (0*SCALE_FACTOR) && right <= (int) (600*SCALE_FACTOR) && left >= (int) (400*SCALE_FACTOR)) { // top
																		// and
																		// bottom
																		// doors
			isLegal = true;
		}

		return isLegal;
	}

	public static void movePlayer(double deltaX, double deltaY) {
		if (legalMove(deltaX, deltaY)) {
			character.getLocation().setLocation(character.getLocation().getX() + deltaX,
					character.getLocation().getY() + deltaY);
			checkIfLeavingRoom();
		}
		checkIfLeavingRoom();
	}

	public static void eventChangeRooms(String door) {
		if (roomArray[loc.x][loc.y] == null) {
			roomArray[loc.x][loc.y] = new StandardRoom();
			StandardRoom current = roomArray[loc.x][loc.y];
			if (current.typeOfRoom.equals("battle")) {
				System.out.println("battle");
			}
			if (current.typeOfRoom.equals("puzzle")) {
				System.out.println("puzzle");
			}
			if (current.typeOfRoom.equals("treasure")) {
				System.out.println("treasure");
			}
			g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			// System.out.println("[" + loc.x + "][" + loc.y + "]");
		} else {
			StandardRoom current = roomArray[loc.x][loc.y];
			g.drawString(current.typeOfRoom, 150, 250);
			g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			// System.out.println("[" + loc.x + "][" + loc.y + "]");
		}
		if (loc.x == 0) {
			Images.leftArrow = Images.leftArrowOff;
		} else if (loc.x == 9) {
			Images.rightArrow = Images.rightArrowOff;
		} else {
			Images.rightArrow = Images.rightArrowOn;
			Images.leftArrow = Images.leftArrowOn;
		}
		if (loc.y == 0) {
			Images.topArrow = Images.topArrowOff;
		} else if (loc.y == 9) {
			Images.bottomArrow = Images.bottomArrowOff;
		} else {
			Images.topArrow = Images.topArrowOn;
			Images.bottomArrow = Images.bottomArrowOn;
		}
		if (door.equals("left")) {
			character.getLocation().setLocation(100 * SCALE_FACTOR, 500 * SCALE_FACTOR);
		} else if (door.equals("right")) {
			character.getLocation().setLocation(800 * SCALE_FACTOR, 500 * SCALE_FACTOR);
		} else if (door.equals("top")) {
			character.getLocation().setLocation(500 * SCALE_FACTOR, 100 * SCALE_FACTOR);
		} else if (door.equals("bottom")) {
			character.getLocation().setLocation(500 * SCALE_FACTOR, 800 * SCALE_FACTOR);
		} else {
			character.getLocation().setLocation(500 * SCALE_FACTOR, 500 * SCALE_FACTOR);
		}
		
	}
}