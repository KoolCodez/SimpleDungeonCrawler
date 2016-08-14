package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import combatSystem.ControlRouter;
import combatSystem.EntityShaker;
import combatSystem.FallingDamageNumber;
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
	private ArrayList<FallingDamageNumber> damageNumbers;
	public MovementController movementController;

	public CoreGameplayPanel() {
		movementController = new MovementController(this);
		createTestDamageButton();
		createMenuButton();
		createAttackButton();
		setLayout(null);
		damageNumbers = new ArrayList<FallingDamageNumber>();
		this.setOpaque(false);
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
		for (int i = 0; i < damageNumbers.size(); i++) {
			FallingDamageNumber currentNum = damageNumbers.get(i);
			Point point = currentNum.getPoint();
			g.setFont(SDC.font);
			g.drawString(currentNum.getDamage() + "", point.x, point.y);
		}
		g.setColor(Color.black);
	}
	
	private void createTestDamageButton() {
		JButton testDamage = new JButton("DAMAGE");
		testDamage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double damage = 10.0 * Math.random();
				Point2D doublePoint = SDC.character.getLocation();
				Point location = new Point((int) doublePoint.getX(), (int) doublePoint.getY());
				FallingDamageNumber currentFallingDamage = new FallingDamageNumber(damage, location);
				damageNumbers.add(currentFallingDamage);
				currentFallingDamage.start();
				
				EntityShaker eShake = new EntityShaker(SDC.character);
				eShake.start();
			}
		});
		testDamage.setBounds((int) (700 * SCALE_FACTOR), (int) (200 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);

		testDamage.setFont(SDC.font);
		add(testDamage);
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
		menuButton.setBounds((int) (700 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuButton.setFont(SDC.font);
		add(menuButton);
	}
	
	private void createBattle() {
		SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
			ControlRouter control;
			@Override
			protected Integer doInBackground() throws Exception {
				System.out.println("new battle");
				control = new ControlRouter();
				return 0;
			}
		};
		worker.execute();
	}
	
	private void createAttackButton() {
		JButton atkButton = new JButton("ATTACK");
		atkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				movementController.stopMovement();
				createBattle();
			}
		});
		atkButton.setBounds((int) (700 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		atkButton.setFont(SDC.font);
		add(atkButton);
	}

}
