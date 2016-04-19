package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

import items.GenericItem;
import items.Stick;
import misc.Images;
import misc.StandardRoom;
import movement.MoveDown;
import movement.MoveDownLeft;
import movement.MoveDownRight;
import movement.MoveLeft;
import movement.MoveRight;
import movement.MoveUp;
import movement.MoveUpLeft;
import movement.MoveUpRight;

public class PanelSubsystem {
	
	public static JFrame frame;
	public static JPanel panel;
	public static JPanel menuPanel;
	public static JPanel atkPanel;
	public static JPanel turnPanel;
	public static JPanel invPanel;
	public static JPanel charPanel;
	public static JPanel mainMenu;
	public static JPanel bagPanel;
	
	public PanelSubsystem() {
		
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
				if (waitForTurn.getTurnPoints() > 2) {
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
	
	public static void createMenu() {
		JButton resume = new JButton();
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
		resume.setIcon(new ImageIcon(Images.resumeButton));
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
				PanelSubsystem.frame.getContentPane().validate();
				PanelSubsystem.frame.getContentPane().repaint();
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
				PanelSubsystem.frame.getContentPane().validate();
				PanelSubsystem.frame.getContentPane().repaint();
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
	
}
