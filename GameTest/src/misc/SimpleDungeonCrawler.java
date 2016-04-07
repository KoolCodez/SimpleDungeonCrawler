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
	public static final double SCALE_FACTOR = .75;
	public static FriendlyEntity character;
	public static double playerSpeed = 8 * SCALE_FACTOR;
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
	public static JPanel turnPanel;
	public static JPanel invPanel;
	public static JPanel charPanel;
	public static JPanel mainMenu;
	public static JPanel bagPanel;
	public static int refreshRate = 25; // number of millis to wait
	public static Font font = new Font("Harrington", Font.BOLD, 18);
	public static TurnWait t = new TurnWait();
	public static boolean flee = false;
	private static final int SCALED_100 = (int) (100*SCALE_FACTOR);
	private static final int MENU_SIZE = (int) (1000*SCALE_FACTOR);
	private static final int BUTTON_HEIGHT = (int) (100*SCALE_FACTOR);
	private static final int BUTTON_WIDTH = (int) (300*SCALE_FACTOR);

	public static void main(String[] args) throws InterruptedException, IOException {
		String current = System.getProperty("user.dir");
		// System.out.println("Current working directory in Java : " + current);
		frame = new JFrame("Simple Dungeon Crawler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int) (1000 * SCALE_FACTOR + 16), (int) (1000 * SCALE_FACTOR + 38));
		frame.setVisible(true);
		g = frame.getGraphics();
		g.setColor(Color.white);
		character = new FriendlyEntity(5, 10, 10, 10, 10, 10, 10, 1);
		Images.createImages();
		character.addItem(new Stick());
		createButtonsAndPanels();
		InputMap inMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap acMap = panel.getActionMap();
		createKeyBindings(inMap, acMap);
		Refresh r1 = new Refresh();
		r1.start();
		Point p = new Point(0, 10);
		roomArray[0][0] = new BattleRoom(0);
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
				g.drawImage(Images.charImg, (int) character.getLocation().getX(), (int) character.getLocation().getY(),
						null);
				g.drawImage(Images.rightArrow, (int) (948*SCALE_FACTOR), (int) (450*SCALE_FACTOR), null);
				g.drawImage(Images.leftArrow, (int) (0*SCALE_FACTOR), (int) (450*SCALE_FACTOR), null);
				g.drawImage(Images.bottomArrow, (int) (450*SCALE_FACTOR), (int) (948*SCALE_FACTOR), null);
				g.drawImage(Images.topArrow, (int) (450*SCALE_FACTOR), (int) (0*SCALE_FACTOR), null);
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
		menuButton.setBounds((int) (700*SCALE_FACTOR), (int) (0*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuButton.setFont(font);

		// attack button
		atkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(panel);
				frame.getContentPane().add(atkPanel);
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						battleSequence(/* console1 */);
						return 0;
					}
				};
				flee = false;
				worker.execute();
			}
		});
		atkButton.setBounds((int) (700*SCALE_FACTOR), (int) (100*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
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
		Point menuCoord = new Point((int) (350*SCALE_FACTOR), (int) (450*SCALE_FACTOR));
		mainMenu = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.mainMenu, (int) (0*SCALE_FACTOR), (int) (0*SCALE_FACTOR), MENU_SIZE, MENU_SIZE, null);

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
		startButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		startButton.setFont(font);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		exitButton.setFont(font);
	}

	public static void createCharScreen() {
		JButton exitButton = new JButton("EXIT");
		JButton setStr = new JButton("+1");
		JButton strDown = new JButton("-1");
		JButton setDex = new JButton("+1");
		JButton dexDown = new JButton("-1");
		JButton setCon = new JButton("+1");
		JButton conDown = new JButton("-1");
		JButton setWis = new JButton("+1");
		JButton wisDown = new JButton("-1");
		JButton intUp = new JButton("+1");
		JButton intDown = new JButton("-1");
		JButton setChr = new JButton("+1");
		JButton chrDown = new JButton("-1");
		// attack panel
		charPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawString("STRENGTH: " + character.getStr(), 200, 100);
				g.drawString("DEXTERITY: " + character.getDex(), 200, 150);
				g.drawString("CONSTITUTION: " + character.getCon(), 200, 200);
				g.drawString("WISDOM: " + character.getWis(), 200, 250);
				g.drawString("INTELLIGENCE: " + character.getIntl(), 200, 300);
				g.drawString("CHARISMA: " + character.getChr(), 200, 350);
			}
		};
		charPanel.add(exitButton);
		charPanel.add(setStr);
		charPanel.add(strDown);
		charPanel.add(setDex);
		charPanel.add(dexDown);
		charPanel.add(setCon);
		charPanel.add(conDown);
		charPanel.add(setWis);
		charPanel.add(wisDown);
		charPanel.add(intUp);
		charPanel.add(intDown);
		charPanel.add(setChr);
		charPanel.add(chrDown);
		charPanel.setLayout(null);

		// level buttons
		setStr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setStr(1);
			}
		});
		setStr.setBounds(200, 100, 50, 25);
		strDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setStr(-1);
			}
		});
		strDown.setBounds(250, 100, 50, 25);

		setDex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setDex(1);
			}
		});
		setDex.setBounds(200, 150, 50, 25);
		dexDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setDex(-1);
			}
		});
		dexDown.setBounds(250, 150, 50, 25);

		setCon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setCon(1);
			}
		});
		setCon.setBounds(200, 200, 50, 25);
		conDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setCon(-1);
			}
		});
		conDown.setBounds(250, 200, 50, 25);

		setWis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setWis(1);
			}
		});
		setWis.setBounds(200, 250, 50, 25);
		wisDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setWis(-1);
			}
		});
		wisDown.setBounds(250, 250, 50, 25);

		intUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setIntl(1);
			}
		});
		intUp.setBounds(200, 300, 50, 25);
		intDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setIntl(-1);
			}
		});
		intDown.setBounds(250, 300, 50, 25);

		setChr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setChr(1);
			}
		});
		setChr.setBounds(200, 350, 50, 25);
		intDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				character.setChr(-1);
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
		exitButton.setBounds((int) (700*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.setFont(font);

		// stick button
		addStick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (character.getInventory().size() < 20) {
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
		addStick.setBounds((int) (0*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		addStick.setFont(font);
	}

	public static void refreshInv() {
		Rectangle rText = new Rectangle(0, SCALED_100, SCALED_100, (int) (40*SCALE_FACTOR));
		Rectangle rImage = new Rectangle(0, 0, SCALED_100, SCALED_100);
		for (int i = character.getInventory().size() - 1; i >= 0; i--) {
			GenericItem item = character.getInventory().get(i);
			JTextArea text = new JTextArea(item.itemName);
			text.setEditable(false);
			text.setBounds(rText);
			rText.x += SCALED_100;
			if (rText.x >= (int) (900*SCALE_FACTOR)) {
				rText.x -= (int) (900*SCALE_FACTOR);
				rText.y += (int) (140*SCALE_FACTOR);
			}
			invPanel.add(text);
			JLabel itemLabel = new JLabel(item.itemImage);
			itemLabel.setBounds(rImage);
			rImage.x += SCALED_100;
			if (rImage.x >= (int) (900*SCALE_FACTOR)) {
				rImage.x -= (int) (900*SCALE_FACTOR);
				rImage.y += (int) (140*SCALE_FACTOR);
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
		JButton endTurn = new JButton("END TURN");
		ArrayList<String> console1 = new ArrayList<String>();
		// console1.add("Console is funtioning.");
		// attack panel
		atkPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.battleMenu, 0, 0, MENU_SIZE, MENU_SIZE, null);
				g.setColor(Color.red);
				g.fillRect((int) (214*SCALE_FACTOR), (int) (932*SCALE_FACTOR), (int) (440*SCALE_FACTOR * character.getHealth() / character.getMaxHealth()), (int) (36*SCALE_FACTOR));
				g.setColor(Color.black);
				StandardRoom current = roomArray[loc.x][loc.y];
				for (int i = 0; i < current.enemyEntities.size(); i++) {
					Point2D point = current.enemyEntities.get(i).getBattleLoc();
					g.drawImage(Images.battleGoblin, (int) point.getX(), (int) point.getY(), (int) (200*SCALE_FACTOR), (int) (100*SCALE_FACTOR), null);
				}
				g.drawImage(Images.battleChar, (int) (300*SCALE_FACTOR), (int) (600*SCALE_FACTOR), (int) (200*SCALE_FACTOR), (int) (100*SCALE_FACTOR), null);
				// g.drawString(console1.get(console1.size() - 1), 10, 100);
			}
		};

		turnPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.battleMenu, 0, 0, MENU_SIZE, MENU_SIZE, null);
				g.setColor(Color.red);
				g.fillRect((int) (214*SCALE_FACTOR), (int) (932*SCALE_FACTOR), (int) (440*SCALE_FACTOR * character.getHealth() / character.getMaxHealth()), (int) (36*SCALE_FACTOR));
				g.setColor(Color.black);
				g.drawImage(Images.battleChar, (int) (300*SCALE_FACTOR), (int) (600*SCALE_FACTOR), (int) (200*SCALE_FACTOR), (int) (100*SCALE_FACTOR), null);
				StandardRoom current = roomArray[loc.x][loc.y];
				for (int i = 0; i < current.enemyEntities.size(); i++) {
					Point2D point = current.enemyEntities.get(i).getBattleLoc();
					g.drawImage(Images.battleGoblin, (int) point.getX(), (int) point.getY(), (int) (200*SCALE_FACTOR), (int) (100*SCALE_FACTOR), null);
				}
				g.drawString("Turn Points" + t.getTurnPoints(), 0, 0);
				// g.drawString(console1.get(console1.size() - 1), 10, 100);
			}
		};

		turnPanel.add(bagButton);
		turnPanel.add(fightButton);
		turnPanel.add(fleeButton);
		turnPanel.add(moveButton);
		turnPanel.add(endTurn);
		turnPanel.setLayout(null);
		createBagPanel();

		fightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (t.getTurnPoints() >= 3) {
					t.setTurnPoints(-3);
					characterAttack(roomArray[loc.x][loc.y].enemyEntities.get(character.getSelectedEntity()));
				} else {
					System.out.println("Not enough turn points");
				}
			}
		});
		fightButton.setBounds((int) (699*SCALE_FACTOR), (int) (148*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		fightButton.setIcon(new ImageIcon(Images.fightButton));
		
		endTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t.endTurn();
			}
		});
		endTurn.setBounds((int) (699*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);

		moveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						move();
						return 0;
					}
				};
				worker.execute();
			}
		});
		moveButton.setBounds((int) (699*SCALE_FACTOR), (int) (348*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		moveButton.setIcon(new ImageIcon(Images.moveButton));
		
		bagButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.remove(turnPanel);
				frame.add(bagPanel);
				JLabel weaponLabel = new JLabel(character.getWeapon().getImage());
				weaponLabel.setBounds(0, 0, SCALED_100, SCALED_100);
				bagPanel.add(weaponLabel);
			}
		});
		bagButton.setBounds((int) (699*SCALE_FACTOR), (int) (552*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		bagButton.setIcon(new ImageIcon(Images.bagButton));

		fleeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flee = true;
				t.endTurn();
			}
		});
		fleeButton.setBounds((int) (699*SCALE_FACTOR), (int) (752*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		fleeButton.setIcon(new ImageIcon(Images.fleeButton));
	}
	
	public static void createBagPanel() {
		JButton returnButton = new JButton("RETURN");
		JButton selectWeapon = new JButton("SELECT WEAPON");
		bagPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawString("SELECTED WEAPON", 0, (int) (118*SCALE_FACTOR));
			}
		};
		bagPanel.add(selectWeapon);
		bagPanel.add(returnButton);
		bagPanel.setLayout(null);
		
		selectWeapon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (t.getTurnPoints() > 2) {
					//TODO choose weapon method
				}
			}
		});
		selectWeapon.setBounds((int) (0*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.add(turnPanel);
				frame.remove(bagPanel);
			}
		});
		returnButton.setBounds((int) (700*SCALE_FACTOR), (int) (900*SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
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
				frame.validate();
				frame.repaint();
				if (initList.get(i).getClass().toString().equals("class misc.EnemyEntity") && !flee) {
					enemyAttack((EnemyEntity) initList.get(i)/* , console1 */);
					// System.out.println();
				} else if (initList.get(i).getClass().toString().equals("class misc.FriendlyEntity") && !flee) {
					frame.remove(atkPanel);
					frame.add(turnPanel);
					synchronized (t) {
						try {
							t.wait();
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
					frame.remove(turnPanel);
					frame.add(atkPanel);
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
				t.reset();
				// checkHealth(currentRoom);
				frame.validate();
				frame.repaint();
			}
			checkLiving(currentRoom);
			frame.validate();
			frame.repaint();
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
		frame.getContentPane().addMouseListener(mouse);
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
			if (Math.abs(x - point.x) * (1/SCALED_100) + Math.abs(y - point.y)  * (1/SCALED_100) < t.getTurnPoints()) {
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
			frame.remove(turnPanel);
			frame.add(panel);
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

	public static void createMenu() {
		JButton resume = new JButton("RESUME");
		JButton charButton = new JButton("CHARACTER");
		JButton invButton = new JButton("INVENTORY");
		JButton saveButton = new JButton("SAVE");
		JButton exitButton = new JButton("QUIT");
		Point menuCoord = new Point((int) (350*SCALE_FACTOR), (int) (250*SCALE_FACTOR));

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
		resume.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		resume.setFont(font);

		// char button
		charButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().add(charPanel);
				frame.getContentPane().remove(menuPanel);
			}
		});
		charButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
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
		invButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		invButton.setFont(font);

		// save button
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		saveButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
		menuCoord.y += BUTTON_HEIGHT;
		saveButton.setFont(font);

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(menuPanel);
				frame.getContentPane().add(mainMenu);
			}
		});
		exitButton.setBounds(menuCoord.x, menuCoord.y, BUTTON_WIDTH, BUTTON_HEIGHT);
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