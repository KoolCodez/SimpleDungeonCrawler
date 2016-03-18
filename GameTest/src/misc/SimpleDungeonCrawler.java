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

import javax.swing.*;

import items.GenericItem;
import items.Stick;
import movement.MoveDown;
import movement.MoveDownLeft;
import movement.MoveDownRight;
import movement.MoveLeft;
import movement.MoveRight;
import movement.MoveUp;
import movement.MoveUpLeft;
import movement.MoveUpRight;

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
	public static Point2D playerLoc = new Point2D.Double(250.0, 250.0);
	public static FriendlyEntity character;
	public static double playerSpeed = 4;
	public static double diagSpeed = playerSpeed / Math.sqrt(2);
	public static Graphics g;
	public static boolean movingLeft = false;
	public static boolean movingRight = false;
	public static boolean movingUp = false;
	public static boolean movingDown = false;
	public static JFrame frame;
	public static JPanel panel;
	public static JPanel menuPanel;
	public static JPanel atkPanel;
	public static JPanel invPanel;
	public static JPanel charPanel;
	public static JPanel mainMenu;
	public static int refreshRate = 25; // number of millis to wait
	public static Font font = new Font("Harrington", Font.BOLD, 18);

	public static void main(String[] args) throws InterruptedException, IOException {
		String current = System.getProperty("user.dir");
		// System.out.println("Current working directory in Java : " + current);
		frame = new JFrame("Simple Dungeon Crawler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(516, 538);
		frame.setVisible(true);
		g = frame.getGraphics();
		g.setColor(Color.white);
		character = new FriendlyEntity(5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		Images.createImages();
		character.addItem(new Stick());
		createButtonsAndPanels();
		InputMap inMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap acMap = panel.getActionMap();
		createKeyBindings(inMap, acMap);
		Refresh r1 = new Refresh();
		r1.start();
		Point p = new Point(0, 10);
		roomArray[0][0] = new BattleRoom(1);
	}

	public static void createButtonsAndPanels() {
		// Declarations
		JButton menuButton = new JButton("PAUSE");
		JButton atkButton = new JButton("ATTACK");
		// panel
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.backgroundImg, 0, 0, null);
				g.drawImage(Images.charImg, (int) playerLoc.getX(), (int) playerLoc.getY(), null);
				g.drawImage(Images.rightArrow, 474, 225, null);
				g.drawImage(Images.leftArrow, 0, 225, null);
				g.drawImage(Images.bottomArrow, 225, 474, null);
				g.drawImage(Images.topArrow, 225, 0, null);
			}
		};
		panel.setLayout(null);
		panel.add(atkButton);
		panel.add(menuButton);

		// menu button
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(menuPanel);
				frame.getContentPane().remove(atkPanel);
				frame.getContentPane().remove(panel);
			}
		});
		menuButton.setBounds(350, 0, 150, 50);
		menuButton.setFont(font);

		// attack button
		atkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(panel);
				frame.getContentPane().add(atkPanel);
			}
		});
		atkButton.setBounds(350, 50, 150, 50);
		atkButton.setFont(font);
		// attackButton.setIcon(defaultIcon);

		// initializing frame stuff
		createMainMenu();
		createAtkPanel();
		createMenu();
		createInventory();
		createCharScreen();
		frame.getContentPane().add(mainMenu);
	}

	public static void createMainMenu() {
		JButton startButton = new JButton("START");
		JButton exitButton = new JButton("EXIT");
		Point menuCoord = new Point(175, 225);
		mainMenu = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.mainMenu, 0, 0, 500, 500, null);

			}
		};
		mainMenu.setLayout(null);
		mainMenu.add(startButton);
		mainMenu.add(exitButton);

		// start button
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				frame.getContentPane().remove(mainMenu);
			}
		});
		startButton.setBounds(menuCoord.x, menuCoord.y, 150, 50);
		menuCoord.y += 50;
		startButton.setFont(font);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, 150, 50);
		menuCoord.y += 50;
		exitButton.setFont(font);
	}

	public static void createCharScreen() {
		JButton exitButton = new JButton("EXIT");
		JButton strUp = new JButton("+1");
		JButton strDown = new JButton("-1");
		JButton dexUp = new JButton("+1");
		JButton dexDown = new JButton("-1");
		JButton conUp = new JButton("+1");
		JButton conDown = new JButton("-1");
		JButton wisUp = new JButton("+1");
		JButton wisDown = new JButton("-1");
		JButton intUp = new JButton("+1");
		JButton intDown = new JButton("-1");
		JButton chrUp = new JButton("+1");
		JButton chrDown = new JButton("-1");
		// attack panel
		charPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawString("STRENGTH: " + character.str, 200, 100);
				g.drawString("DEXTERITY: " + character.dex, 200, 150);
				g.drawString("CONSTITUTION: " + character.con, 200, 200);
				g.drawString("WISDOM: " + character.wis, 200, 250);
				g.drawString("INTELLIGENCE: " + character.intl, 200, 300);
				g.drawString("CHARISMA: " + character.chr, 200, 350);
			}
		};
		charPanel.add(exitButton);
		charPanel.add(strUp);
		charPanel.add(strDown);
		charPanel.add(dexUp);
		charPanel.add(dexDown);
		charPanel.add(conUp);
		charPanel.add(conDown);
		charPanel.add(wisUp);
		charPanel.add(wisDown);
		charPanel.add(intUp);
		charPanel.add(intDown);
		charPanel.add(chrUp);
		charPanel.add(chrDown);
		charPanel.setLayout(null);

		// level buttons
		strUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.str++;
			}
		});
		strUp.setBounds(200, 100, 50, 25);
		strDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.str--;
			}
		});
		strDown.setBounds(250, 100, 50, 25);

		dexUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.dex++;
			}
		});
		dexUp.setBounds(200, 150, 50, 25);
		dexDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.dex--;
			}
		});
		dexDown.setBounds(250, 150, 50, 25);

		conUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.con++;
			}
		});
		conUp.setBounds(200, 200, 50, 25);
		conDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.con--;
			}
		});
		conDown.setBounds(250, 200, 50, 25);

		wisUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.wis++;
			}
		});
		wisUp.setBounds(200, 250, 50, 25);
		wisDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.wis--;
			}
		});
		wisDown.setBounds(250, 250, 50, 25);

		intUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.intl++;
			}
		});
		intUp.setBounds(200, 300, 50, 25);
		intDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.intl--;
			}
		});
		intDown.setBounds(250, 300, 50, 25);

		chrUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.chr++;
			}
		});
		chrUp.setBounds(200, 350, 50, 25);
		intDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.chr--;
			}
		});
		chrDown.setBounds(250, 350, 50, 25);

		// exit button
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(menuPanel);
				frame.getContentPane().remove(charPanel);
			}
		});
		exitButton.setBounds(350, 100, 150, 50);

	}

	public static void createInventory() {
		JButton exitButton = new JButton("EXIT");
		JButton addStick = new JButton("STICK ME");

		// attack panel
		invPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};
		invPanel.add(addStick);
		invPanel.add(exitButton);
		invPanel.setLayout(null);

		// exit button
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				invPanel.removeAll();
				invPanel.add(addStick);
				invPanel.add(exitButton);
				frame.getContentPane().add(menuPanel);
				frame.getContentPane().remove(invPanel);
			}
		});
		exitButton.setBounds(350, 100, 100, 50);
		exitButton.setFont(font);

		// stick button
		addStick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (character.entityInventory.size() < 20) {
					Stick stick = new Stick();
					stick.getImage();
					character.addItem(stick);
					invPanel.removeAll();
					invPanel.add(addStick);
					invPanel.add(exitButton);
					refreshInv();
				}
			}
		});
		addStick.setBounds(350, 150, 100, 50);
		addStick.setFont(font);
	}

	public static void refreshInv() {
		Rectangle rText = new Rectangle(0, 50, 50, 20);
		Rectangle rImage = new Rectangle(0, 0, 50, 50);
		for (int i = character.entityInventory.size() - 1; i >= 0; i--) {
			GenericItem item = character.entityInventory.get(i);
			JTextArea text = new JTextArea(item.itemName);
			text.setEditable(false);
			text.setBounds(rText);
			rText.x += 50;
			if (rText.x >= 450) {
				rText.x -= 450;
				rText.y += 70;
			}
			invPanel.add(text);
			JLabel itemLabel = new JLabel(item.itemImage);
			itemLabel.setBounds(rImage);
			rImage.x += 50;
			if (rImage.x >= 450) {
				rImage.x -= 450;
				rImage.y += 70;
			}
			invPanel.add(itemLabel);
		}
	}

	public static void createAtkPanel() {
		JButton exitButton = new JButton("EXIT");
		JButton bagButton = new JButton();
		JButton fightButton = new JButton();
		JButton fleeButton = new JButton();
		JButton moveButton = new JButton();
		JButton debugHealth = new JButton("-1 hp");
		// attack panel
		atkPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.battleMenu, 0, 0, 500, 500, null);
				g.setColor(Color.red);
				g.fillRect(107, 466, 220 * character.health / character.maxHealth, 18);
				g.setColor(Color.black);
				g.drawImage(Images.battleChar, 150, 300, 100, 50, null);
				g.drawImage(Images.battleGoblin, 150, 100, 100, 50, null);

			}
		};
		atkPanel.add(bagButton);
		atkPanel.add(fightButton);
		atkPanel.add(fleeButton);
		atkPanel.add(moveButton);
		atkPanel.add(debugHealth);
		// atkPanel.add(exitButton);
		atkPanel.setLayout(null);

		debugHealth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.health--;
			}
		});
		debugHealth.setBounds(107, 456, 30, 10);
		StandardRoom currentRoom = roomArray[loc.x][loc.y];
		int selectedEnemy = 0;
		// exit button
		
		
		fightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Entity> initList = setInitiative(currentRoom);
				for (int i = 0; i < initList.size(); i++) {
					if (initList.get(i).getClass().toString().equals("EnemyEntity")) {
						enemyAttack((EnemyEntity) initList.get(i));
					} else {
						characterAttack(currentRoom.enemyEntities.get(selectedEnemy));
					}
				}
				checkHealth(currentRoom);
			}
		});
		fightButton.setBounds(349, 74, 150, 50);
		fightButton.setIcon(new ImageIcon(Images.fightButton));

		moveButton.setBounds(349, 174, 150, 50);
		moveButton.setIcon(new ImageIcon(Images.moveButton));

		bagButton.setBounds(349, 276, 150, 50);
		bagButton.setIcon(new ImageIcon(Images.bagButton));
		
		fleeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				frame.getContentPane().remove(atkPanel);
			}
		});
		fleeButton.setBounds(349, 376, 150, 50);
		fleeButton.setIcon(new ImageIcon(Images.fleeButton));

		
	}
	
	public static List<Entity> setInitiative(StandardRoom current) {
		List<Entity> initList = new ArrayList<Entity>();
		for (int i = 0; i < current.enemyEntities.size(); i++) {
			int r = r6();
			current.enemyEntities.get(i).initiative = r;
			System.out.print(r + "");
			initList.add(current.enemyEntities.get(i));
		}
		int r = r6();
		character.initiative = r;
		System.out.print(r + "");
		initList.add(character);
		Collections.sort(null); //TODO sort based on initiative
		return initList;
	}
	
	public static void checkHealth(StandardRoom current) {
		
	}
	
	public static void characterAttack(EnemyEntity enemy) {
		//does it hit
			if (character.dex > EnemyEntity.dex) {
				//how much damage does it do
				double damage = 0.0;
				damage = character.selectedWeapon.damage + character.str - enemy.con;
				//subtract damage
				enemy.enemyHealth -= (int) damage;
			}
	}
	
	public static void enemyAttack(EnemyEntity enemy) {
		if (enemy.dex > character.dex) {
			double damage = 0.0;
			damage = enemy.selectedWeapon.damage + enemy.str - character.con;
			//subtract damage
			character.health -= (int) damage;
		}
	}
	
	public static void friendlyAttack() {
		
	}

	public static void move() {

	}

	public static void bag() {

	}

	public static void flee() {

	}

	public static int r20() {
		return new Random().nextInt(20) + 1;
	}
	
	public static int r6() {
		return new Random().nextInt(6) + 1;
	}

	public static void createMenu() {
		JButton resume = new JButton("RESUME");
		JButton charButton = new JButton("CHARACTER");
		JButton invButton = new JButton("INVENTORY");
		JButton saveButton = new JButton("SAVE");
		JButton exitButton = new JButton("QUIT");
		Point menuCoord = new Point(175, 125);

		// menu panel
		menuPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.pauseMenu, 0, 0, null);
			}
		};
		menuPanel.setLayout(null);
		menuPanel.add(resume);
		menuPanel.add(charButton);
		menuPanel.add(invButton);
		menuPanel.add(saveButton);
		menuPanel.add(exitButton);

		// start button
		resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(panel);
				frame.getContentPane().remove(atkPanel);
				frame.getContentPane().remove(menuPanel);
			}
		});
		resume.setBounds(menuCoord.x, menuCoord.y, 150, 50);
		menuCoord.y += 50;
		resume.setFont(font);

		// char button
		charButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(charPanel);
				frame.getContentPane().remove(menuPanel);
			}
		});
		charButton.setBounds(menuCoord.x, menuCoord.y, 150, 50);
		menuCoord.y += 50;
		charButton.setFont(font);

		// inventory button
		invButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(menuPanel);
				frame.getContentPane().add(invPanel);
				refreshInv();
			}
		});
		invButton.setBounds(menuCoord.x, menuCoord.y, 150, 50);
		menuCoord.y += 50;
		invButton.setFont(font);

		// save button
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveButton.setBounds(menuCoord.x, menuCoord.y, 150, 50);
		menuCoord.y += 50;
		saveButton.setFont(font);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(menuPanel);
				frame.getContentPane().add(mainMenu);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, 150, 50);
		exitButton.setFont(font);
	}

	public static void createKeyBindings(InputMap inMap, ActionMap acMap) {

		Action pause = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				frame.remove(panel);
				createMenu();
				frame.add(menuPanel);
			}
		};

		Action moveLeft = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (!movingLeft) {
					if (movingUp) {
						movingLeft = true;
						MoveUpLeft t1 = new MoveUpLeft();
						t1.start();
					} else if (movingDown) {
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
					} else if (movingDown) {
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
					} else if (movingRight) {
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
					} else if (movingRight) {
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

		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "pause");
		acMap.put("pause", pause);

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
		if ((int) playerLoc.getY() >= 200 && (int) playerLoc.getY() <= 254) {
			if (playerLoc.getX() < 30 && loc.x != 0) {
				loc.x--;
				eventChangeRooms();

			}

			if (playerLoc.getX() > 444 && loc.x != 9) {
				loc.x++;
				eventChangeRooms();
			}
		}
		if (playerLoc.getX() >= 200 && playerLoc.getX() <= 264) {
			if ((int) playerLoc.getY() < 30 && loc.y != 0) {
				loc.y--;
				eventChangeRooms();
			}

			if ((int) playerLoc.getY() > 434 && loc.y != 9) {
				loc.y++;
				eventChangeRooms();
			}
		}

	}

	public static boolean legalMove(double deltaX, double deltaY) { // character
																	// 46
		// tall, 36
		// wide // wall 34
		boolean isLegal = false;
		double x = playerLoc.getX() + deltaX;
		double y = playerLoc.getY() + deltaY;
		double left = x;
		double right = x + 36;
		double top = y;
		double bottom = y + 46;
		if (bottom <= 464 && top >= 36 && right <= 464 && left >= 36) { // main
																		// room
																		// box
			isLegal = true;
		}
		if (bottom <= 300 && top >= 200 && right <= 500 && left >= 0) { // right
																		// and
																		// left
																		// doors
			isLegal = true;
		}
		if (bottom <= 500 && top >= 0 && right <= 300 && left >= 200) { // top
																		// and
																		// bottom
																		// doors
			isLegal = true;
		}

		return isLegal;
	}

	public static void movePlayer(double deltaX, double deltaY) {
		if (legalMove(deltaX, deltaY)) {
			playerLoc.setLocation(playerLoc.getX() + deltaX, playerLoc.getY() + deltaY);
			checkIfLeavingRoom();
		}
		checkIfLeavingRoom();
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
		playerLoc = new Point(250, 250);
	}
}