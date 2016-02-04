import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.io.*;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class SimpleDungeonCrawler {
	public static boolean end;
	public static final String S_PRESSED = "S";
	public static Polygon character;
	public static StandardRoom[][] roomArray = new StandardRoom[10][10];
	public static Point loc = new Point(0, 0);
	public static Point playerLoc = new Point(250, 250);
	public static int playerSpeed = 10;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		JFrame frame = new JFrame("PENIS");
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setVisible(true);
		JPanel panel = new JPanel();
		panel.setSize(500, 500);
		panel.setLayout(null);
		panel.setVisible(true);
		frame.add(panel);
		InputMap inputMap = panel.getInputMap();
		KeyPress keyBoard = new KeyPress();
		panel.addKeyListener(keyBoard);
		
		Point p = new Point(0, 10);
		roomArray[0][0] = new StandardRoom();
		Image img = ImageIO.read(new File("C:\\Users\\gaubnik000\\My Documents\\Github\\SimpleDungeonCrawler\\Textures\\BasicGround.jpg"));
	}
	
	public static void checkIfLeavingRoom() {
		if (playerLoc.y >= 200 && playerLoc.y <= 300) {
			if (playerLoc.x < 30 && loc.x != 0) {
				loc.x--;
				blankRoom();
				eventChangeRooms();
				
			}
			
			if (playerLoc.x > 470 && loc.x != 9) {
				loc.x++;
				blankRoom();
				eventChangeRooms();
			}
		}
		if (playerLoc.x >= 200 && playerLoc.x <= 300) {
			if (playerLoc.y < 30 && loc.y != 0) {
				loc.y--;
				blankRoom();
				eventChangeRooms();
			}
			
			if (playerLoc.y > 470 && loc.y != 9) {
				loc.y++;
				blankRoom();
				eventChangeRooms();
			}
		}
		
	}
	
	public static void refreshBoard() {
		blankRoom();
		//g.drawString(roomArray[loc.x][loc.y].typeOfRoom, 0, 10);
		//g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
		//g.drawString("[" + playerLoc.x + "][" + playerLoc.y + "]", 0, 30);
	}
	
	public static void drawPlayer() {
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		xPoints[0] = playerLoc.x;
		yPoints[0] = playerLoc.y;
		xPoints[1] = playerLoc.x + 10;
		yPoints[1] = playerLoc.y;
		xPoints[2] = playerLoc.x;
		yPoints[2] = playerLoc.y + 10;
		character = new Polygon(xPoints, yPoints, 3);
		refreshBoard();
	}
	
	public static boolean playerIsInsideBox() {
		boolean withinBox = false;
		if (playerLoc.x >= 34 && playerLoc.x <= 466 && playerLoc.y >= 34 && playerLoc.y <= 466) {
			withinBox = true;
		}
		return withinBox;
	}
	
	public static void movePlayer(String direction) {
		if (direction.equals("left")) { 
			if (playerLoc.y < 300 && playerLoc.y > 200) {
				playerLoc.x -= playerSpeed;
			} else if (playerIsInsideBox()) {
				playerLoc.x -= playerSpeed;
			}
		}
		if (direction.equals("right")) {
			if (playerLoc.y < 300 && playerLoc.y > 200) {
				playerLoc.x += playerSpeed;
			} else if (playerIsInsideBox()) {
				playerLoc.x += playerSpeed;
			}
		}
		if (direction.equals("up")) { 
			if (playerLoc.x < 300 && playerLoc.x > 200) {
				playerLoc.y -= playerSpeed;
			} else if (playerIsInsideBox()) {
				playerLoc.y -= playerSpeed;
			}
		}
		if (direction.equals("down")) { 
			if (playerLoc.x < 300 && playerLoc.x > 200) {
				playerLoc.y += playerSpeed;
			} else if (playerIsInsideBox()) {
				playerLoc.y += playerSpeed;
			}
		}
		drawPlayer();
	}
	
	public static void blankRoom() {
		/*g.setColor(Color.white);
		g.fillRect(0, 0, 500, 500);
		g.setColor(Color.black);
		drawArrows();
		g.drawRect(34, 34, 432, 432);*/
	}
	
	public static void eventChangeRooms() {
		
		if (roomArray[loc.x][loc.y] == null) {
			roomArray[loc.x][loc.y] = new StandardRoom();
			StandardRoom current = roomArray[loc.x][loc.y];
			if (current.typeOfRoom.equals("battle")) {
				
			}
			if (current.typeOfRoom.equals("puzzle")) {
				
			}
			if (current.typeOfRoom.equals("treasure")) {
				
			}
			//g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			//System.out.println("[" + loc.x + "][" + loc.y + "]");
		} else {
			StandardRoom current = roomArray[loc.x][loc.y];
			//g.drawString(current.typeOfRoom, 150, 250);
			//g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			//System.out.println("[" + loc.x + "][" + loc.y + "]");
		}
		playerLoc = new Point(250, 250);
	}

	public static void leftArrow() {
		int n = 3;
		int[] xPoints = new int[n];
		int[] yPoints = new int[n];
		xPoints[0] = 5;
		yPoints[0] = 250;
		xPoints[1] = 25;
		yPoints[1] = 225;
		xPoints[2] = 25;
		yPoints[2] = 275;
		Polygon leftArrow = new Polygon(xPoints, yPoints, n);
		
	}
	
	public static void upArrow() {
		int n = 3;
		int[] xPoints = new int[n];
		int[] yPoints = new int[n];
		xPoints[0] = 250;
		yPoints[0] = 5;
		xPoints[1] = 225;
		yPoints[1] = 25;
		xPoints[2] = 275;
		yPoints[2] = 25;
		Polygon upArrow = new Polygon(xPoints, yPoints, n);
	}
	
	public static void rightArrow() {
		int n = 3;
		int[] xPoints = new int[n];
		int[] yPoints = new int[n];
		xPoints[0] = 495;
		yPoints[0] = 250;
		xPoints[1] = 475;
		yPoints[1] = 225;
		xPoints[2] = 475;
		yPoints[2] = 275;
		Polygon rightArrow = new Polygon(xPoints, yPoints, n);
	}
	
	public static void downArrow() {
		int n = 3;
		int[] xPoints = new int[n];
		int[] yPoints = new int[n];
		xPoints[0] = 250;
		yPoints[0] = 495;
		xPoints[1] = 225;
		yPoints[1] = 475;
		xPoints[2] = 275;
		yPoints[2] = 475;
		Polygon downArrow = new Polygon(xPoints, yPoints, n);
	}
	
	public static void drawArrows() {
		leftArrow();
		upArrow();
		rightArrow();
		downArrow();
	}
	
	public static void leftArrowBig() {
		int n = 3;
		int[] xPoints = new int[n];
		int[] yPoints = new int[n];
		xPoints[0] = 10;
		yPoints[0] = 200;
		xPoints[1] = 60;
		yPoints[1] = 130;
		xPoints[2] = 60;
		yPoints[2] = 270;
		Polygon leftArrow = new Polygon(xPoints, yPoints, n);
	}
	
}