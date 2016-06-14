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
import panels.MainMenu;

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
	private static final String ROOM_ARRAY_SAVE_TAG = "roomArray";
	private static final String CHARACTER_SAVE_TAG = "character";
	private static final String LOC_SAVE_TAG = "loc";
	public static final double SCALE_FACTOR = .75;
	public static final int SCALED_100 = (int) (100 * SCALE_FACTOR);
	public static final int MENU_SIZE = (int) (1000 * SCALE_FACTOR);
	public static final int BUTTON_HEIGHT = (int) (100 * SCALE_FACTOR);
	public static final int BUTTON_WIDTH = (int) (300 * SCALE_FACTOR);
	public static StandardRoom[][] roomArray;
	public static Point loc;
	public static Entity character;
	public static double playerSpeed = 8 * SCALE_FACTOR;
	public static double diagSpeed = playerSpeed / Math.sqrt(2);
	public static Graphics g;
	public static int refreshRate = 25; // number of millis to wait
	public static int fps = 30;
	public static JFrame frame;
	public static Font font = new Font("Harrington", Font.BOLD, 18);

	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
		//String current = System.getProperty("user.dir");
		// System.out.println("Current working directory in Java : " + current);
		frameSetup();
		Images.createImages();
		createCharacter();
		createRoomArray();
		createLoc();
		Refresh r1 = new Refresh();
		r1.start();
		refreshArrows();
	}
	
	private static void frameSetup() {
		frame = new JFrame("Simple Dungeon Crawler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) (1000 * SCALE_FACTOR + 16), (int) (1000 * SCALE_FACTOR + 38));
		frame.setVisible(true);
		frame.add(new MainMenu());
		g = frame.getGraphics();
		g.setColor(Color.white);
	}
	
	private static void createLoc() throws ClassNotFoundException, IOException {
		if (new File("src\\save\\" + LOC_SAVE_TAG).exists()) {
			loc = (Point) loadObject(LOC_SAVE_TAG);
		} else {
			loc = new Point(0, 0);
		}
	}
	
	private static void createCharacter() throws ClassNotFoundException, IOException {
		if (new File("src\\save\\" + CHARACTER_SAVE_TAG).exists()) {
			character = (Entity) loadObject(CHARACTER_SAVE_TAG);
		} else {
			character = new Entity(5, 10, 10, 10, 10, 10, 10, 1);
			character.setType("Friendly");
			character.addItem(new Stick());
		}
	}
	
	private static void createRoomArray() throws ClassNotFoundException, IOException {
		if (new File("src\\save\\" + ROOM_ARRAY_SAVE_TAG).exists()) {
			roomArray = (StandardRoom[][]) loadObject(ROOM_ARRAY_SAVE_TAG);
		} else {
			roomArray = new StandardRoom[10][10];
			StandardRoom current = new StandardRoom();
			current.typeOfRoom = "Standard";
			roomArray[0][0] = current;
		}
	}
	
	public static void saveAllImportantStuff() throws IOException {
		saveObject(SimpleDungeonCrawler.character, CHARACTER_SAVE_TAG);
		saveObject(roomArray, ROOM_ARRAY_SAVE_TAG);
		saveObject(loc, LOC_SAVE_TAG);
	}
	
	public static void loadAllImportantStuff() throws ClassNotFoundException, IOException {
		character = (Entity) loadObject(CHARACTER_SAVE_TAG);
		roomArray = (StandardRoom[][]) loadObject(ROOM_ARRAY_SAVE_TAG);
		loc = (Point) loadObject(LOC_SAVE_TAG);
	}
	
	public static void saveObject(Object object, String name) throws IOException {
		FileOutputStream fileOutput = new FileOutputStream("src\\save\\" + name);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		objectOutput.writeObject(object);
		objectOutput.close();
		fileOutput.close();
		System.out.println("Object " + name + " saved.");
	}
	
	public static Object loadObject(String name) throws ClassNotFoundException, IOException {
			FileInputStream fileInput = new FileInputStream("src\\save\\" + name);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			Object object = objectInput.readObject();
			objectInput.close();
			fileInput.close();
			System.out.println("Object " + name + " loaded.");
			return object;
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
			character.moveLocation(deltaX, deltaY);
			checkIfLeavingRoom();
		}
		checkIfLeavingRoom();
	}

	public static void eventChangeRooms(String door) {
		if (roomArray[loc.x][loc.y] == null) {
			StandardRoom current = new StandardRoom();
			//current.typeOfRoom = "Standard";
			roomArray[loc.x][loc.y] = current;
			Random rand = new Random();
			int typeNum = rand.nextInt(10);
			if (typeNum >= 0 && typeNum <= 2) {
				current = new TreasureRoom();
			} else if(typeNum >= 3 && typeNum <= 7) {
				int randomness = rand.nextInt(3);
				current = new BattleRoom(randomness);
				System.out.println(randomness);
			} else if(typeNum >= 8 || typeNum <= 9) {
				current = new PuzzleRoom();
			} else {
				current = new StandardRoom();
			}
			roomArray[loc.x][loc.y] = current;
			System.out.println(current.entities.size());
			System.out.println(current.typeOfRoom);
			g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			// System.out.println("[" + loc.x + "][" + loc.y + "]");
		} else {
			StandardRoom current = roomArray[loc.x][loc.y];
			g.drawString(current.typeOfRoom, 150, 250);
			g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			// System.out.println("[" + loc.x + "][" + loc.y + "]");
		}
		refreshArrows();
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

	private static void refreshArrows() {
		if (loc.x == 0) {
			Images.leftArrowIndex = Images.leftArrowOffIndex;
		} else if (loc.x == 9) {
			Images.rightArrowIndex = Images.rightArrowOffIndex;
		} else {
			Images.rightArrowIndex = Images.rightArrowOnIndex;
			Images.leftArrowIndex = Images.leftArrowOnIndex;
		}
		if (loc.y == 0) {
			Images.topArrowIndex = Images.topArrowOffIndex;
		} else if (loc.y == 9) {
			Images.bottomArrowIndex = Images.bottomArrowOffIndex;
		} else {
			Images.topArrowIndex = Images.topArrowOnIndex;
			Images.bottomArrowIndex = Images.bottomArrowOnIndex;
		}
	}
}