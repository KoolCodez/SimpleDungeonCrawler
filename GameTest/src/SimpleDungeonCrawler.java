import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.io.*;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class SimpleDungeonCrawler {
	public static boolean end;
	public static final String S_PRESSED = "S";
	public static Polygon character;
	public static DrawingPanel panel = new DrawingPanel(500, 500);
	public static Graphics g = panel.getGraphics();
	public static StandardRoom[][] roomArray = new StandardRoom[10][10];
	public static Point loc = new Point(0, 0);
	public static Point playerLoc = new Point(0, 0);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		KeyPress keyBoard = new KeyPress();
		panel.addKeyListener(keyBoard);
		MouseClick mouse = new MouseClick();
		panel.addMouseListener(mouse);
		end = true;
		Point p = new Point(0, 10);
		blankRoom();
		drawPlayer();
		while (end) {
			synchronized (keyBoard) {
				keyBoard.wait();
			}
			KeyEvent key = KeyPress.whichKey;
			String keyName = Character.toString(key.getKeyChar());
			//System.out.println("You pressed " + key.getKeyChar() + "!");
		}
	}
	
	public static void drawPlayer() {
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		xPoints[0] = 0;
		yPoints[0] = 0;
		xPoints[1] = 10;
		yPoints[1] = 0;
		xPoints[2] = 0;
		yPoints[2] = 10;
		character = new Polygon(xPoints, yPoints, 3);
		g.drawPolygon(character);
	}
	
	public static void movePlayer(String direction) {
		if (direction.equals("left") && playerLoc.x >= 5) { 
			character.translate(-5, 0);
		}
		if (direction.equals("right") && playerLoc.x <= 495) { 
			character.translate(5, 0);
		}
		if (direction.equals("up") && playerLoc.y >= 5) { 
			character.translate(0, -5);
		}
		if (direction.equals("down") && playerLoc.y <= 495) { 
			character.translate(0, 5);
		}
		g.drawPolygon(character);
	}
	
	public static void blankRoom() {
		g.setColor(Color.white);
		g.fillRect(0, 0, 500, 500);
		g.setColor(Color.black);
		drawArrows();
		g.drawRect(34, 34, 432, 432);
	}
	
	public static void eventChangeRooms() {
		
		if (roomArray[loc.x][loc.y] == null) {
			roomArray[loc.x][loc.y] = new StandardRoom();
			StandardRoom current = roomArray[loc.x][loc.y];
			if (current.typeOfRoom.equals("battle")) {
				g.drawString("Let The Battle COMMENCE!!", 150, 250);
			}
			if (current.typeOfRoom.equals("puzzle")) {
				g.drawString("Prepare to Have Your MIND MELTED!", 150, 250);
			}
			if (current.typeOfRoom.equals("treasure")) {
				g.drawString("Congratulations, You Have Found Your TREASURE", 150, 250);
			}
			g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			System.out.println("[" + loc.x + "][" + loc.y + "]");
		} else {
			StandardRoom current = roomArray[loc.x][loc.y];
			g.drawString(current.typeOfRoom, 150, 250);
			g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
			System.out.println("[" + loc.x + "][" + loc.y + "]");
		}
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
		g.drawPolygon(leftArrow);
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
		g.drawPolygon(upArrow);
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
		g.drawPolygon(rightArrow);
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
		g.drawPolygon(downArrow);
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
		g.drawPolygon(leftArrow);
	}
	
}