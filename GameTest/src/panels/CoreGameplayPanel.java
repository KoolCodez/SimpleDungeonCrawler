package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
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
import entities.Entity;
import entities.Thing;
import misc.Images;
import misc.SDC;
import movement.MovementController;
import rooms.StandardRoom;

public class CoreGameplayPanel extends JPanel{

	private static double SCALE_FACTOR = SDC.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SDC.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SDC.BUTTON_HEIGHT;
	private static int SCALED_100 = SDC.SCALED_100;
	private ArrayList<Effect> effects;
	public MovementController movementController;

	public CoreGameplayPanel() {
		movementController = new MovementController(this);
		createKeybinds();
		createMenuButton();
		setLayout(null);
		effects = new ArrayList<Effect>();
		this.setOpaque(false);
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
				System.out.println(t);
			}
		};
		inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, false), "pickup");
		acMap.put("pickup", pickup);
	}
	
	private Thing findNearest() {
		int pickupRadius = (int) (100 * SDC.SCALE_FACTOR);
		StandardRoom currentRoom = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		ArrayList<Thing> things = new ArrayList<Thing>();
		things.addAll(currentRoom.things);
		things.remove(SDC.character);
		Point2D charLoc = SDC.character.location;
		charLoc = new Point2D.Double(charLoc.getX() + 50*SDC.SCALE_FACTOR, charLoc.getY() + 50*SDC.SCALE_FACTOR);
		Thing nearest = new Thing();
		System.out.println(nearest);
		nearest.setLocation(Integer.MAX_VALUE, Integer.MAX_VALUE);
		for (int i = 0; i < things.size(); i++) {
			Thing t = things.get(i);
			double dist = charLoc.distance(t.location);
			if (dist <= pickupRadius && dist < charLoc.distance(nearest.location)) {
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
	
	private void drawPanel(Graphics g) {
		g.drawImage(Images.array[Images.backgroundImgIndex], 0, 0, null);
		g.drawImage(Images.array[Images.rightArrowIndex], (int) (948 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), null);
		g.drawImage(Images.array[Images.leftArrowIndex], (int) (0 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), null);
		g.drawImage(Images.array[Images.bottomArrowIndex], (int) (450 * SCALE_FACTOR), (int) (948 * SCALE_FACTOR), null);
		g.drawImage(Images.array[Images.topArrowIndex], (int) (450 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), null);
		g.drawImage(SDC.character.getImage(), (int) SDC.character.getLocation().getX(),
				(int) SDC.character.getLocation().getY(), null);
		StandardRoom current = SDC.roomArray[SDC.loc.x][SDC.loc.y];
		for (int i = 0; i < current.things.size(); i++) {
			Thing t = current.things.get(i);
			if (t.image != null && t != SDC.character) {
				g.drawImage(t.image, (int) t.getLocation().getX(), (int) t.getLocation().getY(), null);
			}
		}
		g.setColor(Color.red);
		for (int i = 0; i < effects.size(); i++) {
			effects.get(i).draw(g);
		}
		g.setColor(Color.black);
	}
	
	private void createMenuButton() {
		JButton menuButton = new JButton("PAUSE");
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				movementController.stopMovement();
				SDC.frame.add(new PauseMenuPanel());
			}
		});
		menuButton.setBounds((int) (1000 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuButton.setFont(SDC.font);
		add(menuButton);
	}
}
