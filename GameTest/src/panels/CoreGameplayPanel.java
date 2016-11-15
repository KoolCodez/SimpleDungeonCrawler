package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import combatSystem.ControlRouter;
import combatSystem.EntityShaker;
import effects.Effect;
import effects.FallingDamageNumber;
import misc.Images;
import misc.SDC;
import movement.MovementController;
import rooms.StandardRoom;
import things.Nothing;
import things.Thing;
import things.entities.Entity;
import things.entities.Character;

public class CoreGameplayPanel extends JPanel{

	private static double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	private static int SCALED_100 = SDC.SCALED_100;
	private static int SCALED_50 = (int) (50 * SDC.SCALE_FACTOR);
	public static final double PICKUP_RADIUS = 200 * SDC.SCALE_FACTOR;
	
	public MovementController movementController;
	private GameplaySideBar sideBar;

	public CoreGameplayPanel() {
		movementController = new MovementController(this);
		createKeybinds();
		setLayout(null);
		SDC.effects = new ArrayList<Effect>();
		this.setOpaque(false);
		createMenuButton();
		sideBar = new GameplaySideBar(new Nothing(), null);
	}
	
	public void shutdownPanel() {
		movementController.stopMovement();
		SDC.frame.remove(sideBar);
		setVisible(false);
	}
	
	public void createKeybinds() {
		InputMap inMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap acMap = this.getActionMap();
		JPanel p = this;
		Action pause = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				SDC.frame.remove(p);
				movementController.stopMovement();
				SDC.frame.add(new PauseMenuPanel());
			}
		};
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "pause");
		acMap.put("pause", pause);
		
		Action pickup = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Thing t = findNearest();
				//SDC.frame.remove(sideBar);
				
				sideBar = new GameplaySideBar(t, SDC.character);
			}
		};
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, false), "pickup");
		acMap.put("pickup", pickup);
	}
	
	private Thing findNearest() {
		StandardRoom currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		ArrayList<Thing> things = new ArrayList<Thing>();
		things.addAll(currentRoom.things);
		Point2D charLoc = SDC.character.getLocation();
		Rectangle2D r = SDC.character.getRect();
		charLoc = new Point2D.Double(charLoc.getX() + r.getWidth()/2.0, charLoc.getY() + r.getHeight()/2.0);
		Thing nearest = new Nothing();
		nearest.setLocation(Integer.MAX_VALUE, Integer.MAX_VALUE);
		for (int i = 0; i < things.size(); i++) {
			Thing t = things.get(i);
			Point2D tLoc = t.getLocation();
			Rectangle2D tr = t.getRect();
			tLoc = new Point2D.Double(tLoc.getX() + tr.getWidth()/2.0, tLoc.getY() + tr.getHeight()/2.0);
			double dist = charLoc.distance(tLoc);
			
			Point2D nLoc = nearest.getLocation();
			Rectangle2D nr = nearest.getRect();
			nLoc = new Point2D.Double(nLoc.getX() + nr.getWidth()/2.0, nLoc.getY() + nr.getHeight()/2.0);
			if (dist <= PICKUP_RADIUS && dist < charLoc.distance(nLoc) && t != SDC.character) {
				nearest = t;
			}
		}
		return nearest;
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		this.setBackground(Color.white);
		drawPanel(g);
	}
	private Image left;
	private Image right;
	private Image up;
	private Image down;
	private void drawPanel(Graphics g) {
		g.drawImage(Images.loadImage("Grounds/BasicGround.jpg", 1000, 1000), 0, 0, 
				(int) (1000*SDC.SCALE_FACTOR), (int) (1000*SDC.SCALE_FACTOR), null);
		refreshArrows();
		g.drawImage(right, (int) (948 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), 
				SCALED_50, SCALED_100, null);
		g.drawImage(left, (int) (0 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), 
				SCALED_50, SCALED_100, null);
		g.drawImage(down, (int) (450 * SCALE_FACTOR), (int) (948 * SCALE_FACTOR),
				SCALED_100, SCALED_50, null);
		g.drawImage(up, (int) (450 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR),
				SCALED_100, SCALED_50, null);
		SDC.character.drawEntity(g);
		StandardRoom current = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		for (int i = 0; i < current.things.size(); i++) {
			Thing t = current.things.get(i);
			if (t.image != null && t != SDC.character) {
				t.drawEntity(g);
				if (t instanceof Character) {
					System.err.println("how");
					current.things.remove(t);
				}
			}
		}
		g.setColor(Color.red);
		for (int i = 0; i < SDC.effects.size(); i++) {
			SDC.effects.get(i).draw(g);
		}
		g.setColor(Color.black);
	}
	
	private void refreshArrows() {
		if (SDC.loc.x == 0) {
			left = Images.loadImage("Arrows/LeftArrowOff.jpg", 100, 50);
		}  else {
			left = Images.loadImage("Arrows/LeftArrowOn.jpg", 100, 50);
		}
		if (SDC.loc.x == 9) {
			right = Images.loadImage("Arrows/RightArrowOff.jpg", 100, 50);
		} else {
			right = Images.loadImage("Arrows/RightArrowOn.jpg", 100, 50);
		}
		if (SDC.loc.y == 0) {
			up = Images.loadImage("Arrows/TopArrowOff.jpg", 50, 100);
		} else {
			up = Images.loadImage("Arrows/TopArrowOn.jpg", 50, 100);
		}
		if (SDC.loc.y == 9) {
			down = Images.loadImage("Arrows/BotArrowOff.jpg", 50, 100);
		} else {
			down = Images.loadImage("Arrows/BotArrowOn.jpg", 50, 100);
		}
	}
	
	private void createMenuButton() {
		JButton menuButton = new JButton("PAUSE");
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SDC.stopGameplayPanel();
				SDC.frame.add(new PauseMenuPanel());
			}
		});
		menuButton.setBounds((int) (1000 * SDC.SCALE_FACTOR), 0, SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		menuButton.setFont(SDC.font);
		add(menuButton);
	}
	
}
