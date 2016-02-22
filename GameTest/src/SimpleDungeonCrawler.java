import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.io.*;
import java.util.Random;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	public static int playerSpeed = 4;
	public static Image backgroundImg;
	public static Graphics g;
	public static boolean movingLeft = false;
	public static boolean movingRight = false;
	public static boolean movingUp = false;
	public static boolean movingDown = false;
	public static JFrame frame;
	public static int refreshRate = 25; //number of millis to wait

	public static void main(String[] args) throws IOException, InterruptedException {
		String current = System.getProperty("user.dir");
		//System.out.println("Current working directory in Java : " + current);
		BufferedImage backgroundImg = ImageIO.read(new File("src\\Textures\\BasicGround.jpg"));
		BufferedImage characterImg = ImageIO.read(new File("src\\Textures\\BetterDuder.jpg"));
		frame = new JFrame("Simple Dungeon Crawler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		g = frame.getGraphics();
		g.setColor(Color.white);
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImg, 0, 0, null);
				g.drawImage(characterImg, playerLoc.x, playerLoc.y, null);
			}
		};
		JButton attackButton = new JButton("ATTACK");
		attackButton.setBounds(0, 0, 100, 50);
		attackButton.setLocation(35, 35);
		attackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("You attacked for 0 dmg lmao");
				
			}
		});
		frame.add(attackButton);
		frame.add(panel);
		InputMap inMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap acMap = panel.getActionMap();
		createKeyBindings(inMap, acMap, frame);
		Refresh r1 = new Refresh();
		r1.start();
		Point p = new Point(0, 10);
		roomArray[0][0] = new StandardRoom();

	}

	public static void createKeyBindings(InputMap inMap, ActionMap acMap, JFrame frame) {
		Action moveLeft = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (!movingLeft) {
					if (movingUp) {
						movingLeft = true;
						MoveUpLeft t1 = new MoveUpLeft();
						t1.start();
					} else if(movingDown) {
						movingLeft = true;
						MoveDownLeft t1 = new MoveDownLeft();
						t1.start();
					}
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
					if (movingUp) {
						movingRight = true;
						MoveUpRight t1 = new MoveUpRight();
						t1.start();
					} else if(movingDown) {
						movingRight = true;
						MoveDownRight t1 = new MoveDownRight();
						t1.start();
					}
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
					if (movingLeft) {
						movingUp = true;
						MoveUpLeft t1 = new MoveUpLeft();
						t1.start();
					} else if(movingRight) {
						movingUp = true;
						MoveUpRight t1 = new MoveUpRight();
						t1.start();
					}
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
					if (movingLeft) {
						movingDown = true;
						MoveDownLeft t1 = new MoveDownLeft();
						t1.start();
					} else if(movingRight) {
						movingDown = true;
						MoveDownRight t1 = new MoveDownRight();
						t1.start();
					}
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
		if (playerLoc.y >= 200 && playerLoc.y <= 254) {
			if (playerLoc.x < 30 && loc.x != 0) {
				loc.x--;
				eventChangeRooms();

			}

			if (playerLoc.x > 444 && loc.x != 9) {
				loc.x++;
				eventChangeRooms();
			}
		}
		if (playerLoc.x >= 200 && playerLoc.x <= 264) {
			if (playerLoc.y < 30 && loc.y != 0) {
				loc.y--;
				eventChangeRooms();
			}

			if (playerLoc.y > 434 && loc.y != 9) {
				loc.y++;
				eventChangeRooms();
			}
		}

	}

	public static void refreshBoard() {
		blankRoom();
		// g.drawString(roomArray[loc.x][loc.y].typeOfRoom, 0, 10);
		// g.drawString(("[" + loc.x + "][" + loc.y + "]"), 0, 20);
		// g.drawString("[" + playerLoc.x + "][" + playerLoc.y + "]", 0, 30);
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

	public static boolean legalMove(String direction) { // character 46 tall, 36
														// wide // wall 34
		boolean isLegal = false;
		int leftSide = playerLoc.x;
		int rightSide = playerLoc.x + 36;
		int top = playerLoc.y;
		int bottom = playerLoc.y + 46;
		// movement bounded to the floor of the room.
		if (direction.equals("left")) { // move left
			if (leftSide - playerSpeed >= 36 && bottom <= 464 && top >= 36) {
				isLegal = true;
			}
		} else if (direction.equals("right")) { // move right
			if (rightSide + playerSpeed <= 464 && bottom <= 464 && top >= 36) {
				isLegal = true;
			}
		} else if (direction.equals("up")) { // move up
			if (top - playerSpeed >= 36 && rightSide <= 464 && leftSide >= 36) {
				isLegal = true;
			}
		} else if (direction.equals("down")) { // move down
			if (bottom + playerSpeed <= 454 && rightSide <= 464 && leftSide >= 36) {
				isLegal = true;
			}
		}
		// movement bounded to the doorway area
		if (direction.equals("left") || direction.equals("right")) { // left and
																		// right
																		// doorways
			if (top >= 200 && bottom <= 300 && leftSide - playerSpeed >= 2 && rightSide + playerSpeed <= 498) {
				isLegal = true;
			}
		} else if (direction.equals("up")) {
			if (top - playerSpeed >= 200 && bottom <= 300 && leftSide >= 2 && rightSide <= 498) {
				isLegal = true;
			}
		} else if (direction.equals("down")) {
			if (top >= 200 && bottom + playerSpeed <= 300 && leftSide >= 2 && rightSide <= 498) {
				isLegal = true;
			}
		}

		if (direction.equals("up") || direction.equals("down")) { // top and
																	// bottom
																	// doorways
			if (leftSide >= 200 && rightSide <= 300 && top - playerSpeed >= 2 && bottom + playerSpeed <= 498) {
				isLegal = true;
			}
		} else if (direction.equals("left")) {
			if (leftSide - playerSpeed >= 200 && rightSide <= 300 && top >= 2 && bottom <= 498) {
				isLegal = true;
			}
		} else if (direction.equals("right")) {
			if (leftSide >= 200 && rightSide + playerSpeed <= 300 && top >= 2 && bottom <= 462) {
				isLegal = true;
			}
		}
		
		if (direction.equals("down left")) {
			isLegal = true;
		}
		if (direction.equals("down right")) {
			isLegal = true;
		}
		if (direction.equals("up left")) {
			isLegal = true;
		}
		if (direction.equals("up right")) {
			isLegal = true;
		}

		return isLegal;
	}

	public static void movePlayer(String direction) {
		if (direction.equals("left")) {
			if (legalMove(direction)) {
					playerLoc.x -= playerSpeed;
					checkIfLeavingRoom();
			}
		}
		
		if (direction.equals("right")) {
			if (legalMove(direction)) {
					playerLoc.x += playerSpeed;
					checkIfLeavingRoom();
			}
		}
		if (direction.equals("up")) {
			if (legalMove(direction)) {
					playerLoc.y -= playerSpeed;
					checkIfLeavingRoom();
			}
		}
		if (direction.equals("down")) {
			if (legalMove(direction)) {
					playerLoc.y += playerSpeed;
					checkIfLeavingRoom();
			}
		}
		if (direction.equals("down left")) {
			if (legalMove(direction)) {
					playerLoc.x -= playerSpeed - 1;
					playerLoc.y += playerSpeed - 1;
					checkIfLeavingRoom();
			}
		}
		if (direction.equals("down right")) {
			if (legalMove(direction)) {
					playerLoc.x += playerSpeed - 1;
					playerLoc.y += playerSpeed - 1;
					checkIfLeavingRoom();
			}
		}
		if (direction.equals("up left")) {
			if (legalMove(direction)) {
					playerLoc.x -= playerSpeed - 1;
					playerLoc.y -= playerSpeed - 1;
					checkIfLeavingRoom();
			}
		}
		if (direction.equals("up right")) {
			if (legalMove(direction)) {
					playerLoc.x += playerSpeed - 1;
					playerLoc.y -= playerSpeed - 1;
					checkIfLeavingRoom();
			}
		}
		checkIfLeavingRoom();
	}

	public static void blankRoom() {
		/*
		 * g.setColor(Color.white); g.fillRect(0, 0, 500, 500);
		 * g.setColor(Color.black); drawArrows(); g.drawRect(34, 34, 432, 432);
		 */
	}

	public static void eventChangeRooms() {

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