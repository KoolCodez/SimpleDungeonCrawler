import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.io.*;
import java.util.Random;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class SimpleDungeonCrawler extends JPanel {
	public static boolean end;
	public static final String S_PRESSED = "S";
	public static Polygon character;
	public static StandardRoom[][] roomArray = new StandardRoom[10][10];
	public static Point loc = new Point(0, 0);
	public static Point playerLoc = new Point(250, 250);
	public static int playerSpeed = 2;
	public static Image backgroundImg;
	public static Graphics g;
	public static boolean movingLeft = false;
	public static boolean movingRight = false;
	public static boolean movingUp = false;
	public static boolean movingDown = false;
	public static JFrame frame;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		String current = System.getProperty("user.dir");
        System.out.println("Current working directory in Java : " + current);
		BufferedImage backgroundImg = ImageIO.read(new File("src\\Textures\\BasicGround.jpg"));
		BufferedImage characterImg = ImageIO.read(new File("src\\Textures\\BetterDuder.jpg"));
		frame = new JFrame("Simple Dungeon Crawler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, null);
                g.drawImage(characterImg, playerLoc.x, playerLoc.y, null);
            }
        };
		frame.add(panel);
		InputMap inMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap acMap = panel.getActionMap();
		createKeyBindings(inMap, acMap, frame);
		
		Point p = new Point(0, 10);
		//panel.repaint();
		//roomArray[0][0] = new StandardRoom();
		
	}
	
	public static void createKeyBindings(InputMap inMap, ActionMap acMap, JFrame frame) {
		Action moveLeft = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (!movingLeft) {
		    	movingLeft = true;
		        	MoveLeft t1 = new MoveLeft();
		        	t1.start();
		    	}
		    }
		};
		Action stopMoveLeft = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        movingLeft = false;
		        frame.getContentPane().validate();
		        frame.getContentPane().repaint();
		    }
		};
		
		Action moveRight = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (!movingRight) {
			    	movingRight = true;
			        	MoveRight t1 = new MoveRight();
			        	t1.start();
			    	}
		    }
		};
		Action stopMoveRight = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        movingRight = false;
		        frame.getContentPane().validate();
		        frame.getContentPane().repaint();
		    }
		};
		
		Action moveUp = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (!movingUp) {
			    	movingUp = true;
			        	MoveUp t1 = new MoveUp();
			        	t1.start();
			    	}
		    }
		};
		Action stopMoveUp = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        movingUp = false;
		        frame.getContentPane().validate();
		        frame.getContentPane().repaint();
		    }
		};
		
		Action moveDown = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (!movingDown) {
			    	movingDown = true;
			        	MoveDown t1 = new MoveDown();
			        	t1.start();
			    	}
		    }
		};
		Action stopMoveDown = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        movingDown = false;
		        frame.getContentPane().validate();
		        frame.getContentPane().repaint();
		    }
		};
		
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "move left");
		acMap.put("move left", moveLeft);
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "stop move left");
		acMap.put("stop move left", stopMoveLeft);
		
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "move right");
		acMap.put("move right", moveRight);
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "stop move right");
		acMap.put("stop move right", stopMoveRight);
		
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "move up");
		acMap.put("move up", moveUp);
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "stop move up");
		acMap.put("stop move up", stopMoveUp);
		
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "move down");
		acMap.put("move down", moveDown);
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "stop move down");
		acMap.put("stop move down", stopMoveDown);
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
	
	public static boolean legalMove(String direction) {
		boolean isLegal = false;
		if (direction.equals("left")) {
			if (playerLoc.x - playerSpeed >= 2) {
				isLegal = true;
			}
		} else if (direction.equals("right")) {
			if (playerLoc.x + playerSpeed <= 498) {
				isLegal = true;
			}
		} else if (direction.equals("up")) {
			if (playerLoc.y - playerSpeed >= 2) {
				isLegal = true;
			}
		} else if (direction.equals("down")) {
			if (playerLoc.y + playerSpeed <= 498) {
				isLegal = true;
			}
		}
		
		
		return isLegal;
	}
	
	public static void movePlayer(String direction) {
		if (direction.equals("left")) { 
			if (legalMove(direction)) {
				playerLoc.x -= playerSpeed;
			}
		}
		if (direction.equals("right")) {
			if (legalMove(direction)) {
				playerLoc.x += playerSpeed;
			}
		}
		if (direction.equals("up")) { 
			if (legalMove(direction)) {
				playerLoc.y -= playerSpeed;
			}
		}
		if (direction.equals("down")) { 
			if (legalMove(direction)) {
				playerLoc.y += playerSpeed;
			}
		}
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