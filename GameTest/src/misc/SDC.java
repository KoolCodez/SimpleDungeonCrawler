package misc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import combatSystem.ControlRouter;
import effects.Effect;
import panels.CoreGameplayPanel;
import panels.MainMenu;
import rooms.BattleRoom;
import rooms.HomeRoom;
import rooms.StandardRoom;
import rooms.TreasureRoom;
import things.entities.Entity;
import things.entities.Character;

public class SDC extends JPanel { //SimpleDungeonCrawler
	private static final String ROOM_ARRAY_SAVE_TAG = "roomArray";
	private static final String CHARACTER_SAVE_TAG = "character";
	private static final String LOC_SAVE_TAG = "loc";
	public static final double SCALE_FACTOR = .75;
	public static final int SCALED_100 = (int) (100 * SCALE_FACTOR);
	public static final int MENU_SIZE = (int) (1000 * SCALE_FACTOR);
	public static final int BUTTON_HEIGHT = (int) (100 * SCALE_FACTOR);
	public static final int BUTTON_WIDTH = (int) (300 * SCALE_FACTOR);
	public static StandardRoom[][] roomArray;
	public static Point loc = new Point(0,0);
	public static Entity character;
	public static ArrayList<Effect> effects;
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
		createOrLoadRoomArray();
		createOrLoadLoc();
		createOrLoadCharacter();
		Refresh r1 = new Refresh();
		r1.start();
		refreshArrows();
	}
	
	private static void frameSetup() {
		frame = new JFrame("Simple Dungeon Crawler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) (1300 * SCALE_FACTOR + 16), (int) (1000 * SCALE_FACTOR + 38));
		frame.setVisible(true);
		frame.add(new MainMenu());
		g = frame.getGraphics();
		g.setColor(Color.white);
	}
	
	private static void createOrLoadLoc() throws ClassNotFoundException {
		if (new File("src\\save\\" + LOC_SAVE_TAG).exists()) {
			loc = (Point) loadObject(LOC_SAVE_TAG);
		} else if (loc == null) {
			loc = new Point(0, 0);
		}
	}
	
	private static void createOrLoadCharacter() throws ClassNotFoundException {
		if (new File("src\\save\\" + CHARACTER_SAVE_TAG).exists()) {
			character = (Entity) loadObject(CHARACTER_SAVE_TAG);
		} else if (character == null) {
			character = new Character();
		}
	}
	
	private static void createOrLoadRoomArray() throws ClassNotFoundException{
		if (new File("src\\save\\" + ROOM_ARRAY_SAVE_TAG).exists()) {
			roomArray = (StandardRoom[][]) loadObject(ROOM_ARRAY_SAVE_TAG);
		} else if (roomArray == null) {
			roomArray = new StandardRoom[10][10];
			StandardRoom current = new HomeRoom();
			current.typeOfRoom = "Standard";
			roomArray[0][0] = current;
		}
	}
	
	public static void saveAllImportantStuff() throws IOException {
		saveObject(SDC.character, CHARACTER_SAVE_TAG);
		saveObject(roomArray, ROOM_ARRAY_SAVE_TAG);
		saveObject(loc, LOC_SAVE_TAG);
	}
	
	public static void loadAllImportantStuff() throws ClassNotFoundException {
		createOrLoadCharacter();
		createOrLoadRoomArray();
		createOrLoadLoc();
	}
	
	public static void saveObject(Object object, String name) throws IOException {
		
		FileOutputStream fileOutput = new FileOutputStream("./save/" + name);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		objectOutput.writeObject(object);
		objectOutput.close();
		fileOutput.close();
		System.out.println("Object " + name + " saved.");
	}
	
	public static Object loadObject(String name) throws ClassNotFoundException {
		try {
			FileInputStream fileInput = new FileInputStream("./save/" + name);
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			Object object = objectInput.readObject();
			objectInput.close();
			fileInput.close();
			System.out.println("Object " + name + " loaded.");
			return object;
		} catch(IOException e) {
			return null;
		}
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

	public static void eventChangeRooms(String door) {
		if (roomArray[loc.x][loc.y] == null) {
			createRoom();
			//g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			// System.out.println("[" + loc.x + "][" + loc.y + "]");
		} else {
			StandardRoom current = roomArray[loc.x][loc.y];
			g.drawString(current.typeOfRoom, 150, 250);
			g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			// System.out.println("[" + loc.x + "][" + loc.y + "]");
		}
		refreshArrows();
		if (roomArray[loc.x][loc.y].typeOfRoom.equals("battle")) {
			System.out.println("starting battle");
			stopGameplayPanel();
			character.setRoom(roomArray[loc.x][loc.y]);
			ControlRouter control = new ControlRouter(door);
		} else {
			switch (door) {
			case "left": character.getLocation().setLocation(100 * SCALE_FACTOR, 500 * SCALE_FACTOR);
				break;
			case "right": character.getLocation().setLocation(800 * SCALE_FACTOR, 500 * SCALE_FACTOR);
				break;
			case "top": character.getLocation().setLocation(500 * SCALE_FACTOR, 100 * SCALE_FACTOR);
				break;
			case "bottom": character.getLocation().setLocation(500 * SCALE_FACTOR, 800 * SCALE_FACTOR);
				break;
			default: character.getLocation().setLocation(500 * SCALE_FACTOR, 500 * SCALE_FACTOR);
				break;
			}
			character.setRoom(roomArray[loc.x][loc.y]);
		}
	}
	
	public static void stopGameplayPanel() {
		Component[] panels = frame.getContentPane().getComponents();
		for (int i = 0; i < panels.length; i++) {
			if (panels[i].getClass().getName().equals("panels.CoreGameplayPanel")) {
				CoreGameplayPanel panel = (CoreGameplayPanel) panels[i];
				panel.shutdownPanel();
			}
		}
	}
	
	private static void createRoom() {
		StandardRoom current = new StandardRoom();
		//current.typeOfRoom = "Standard";
		roomArray[loc.x][loc.y] = current;
		Random rand = new Random();
		int typeNum = rand.nextInt(4) + 3;
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