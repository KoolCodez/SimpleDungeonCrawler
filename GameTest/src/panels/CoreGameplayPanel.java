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
import misc.Entity;
import misc.Images;
import misc.SimpleDungeonCrawler;
import misc.StandardRoom;
import movement.MovementController;

public class CoreGameplayPanel extends JPanel{

	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	private static int SCALED_100 = SimpleDungeonCrawler.SCALED_100;
	private ArrayList<FallingDamageNumber> damageNumbers;

	public CoreGameplayPanel() {
		MovementController movementController = new MovementController(this);
		createTestDamageButton();
		createMenuButton();
		createAttackButton();
		setLayout(null);
		damageNumbers = new ArrayList<FallingDamageNumber>();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		drawPanel(g);
	}
	
	private void drawPanel(Graphics g) {
		g.drawImage(Images.array[Images.backgroundImgIndex], 0, 0, null);
		g.drawImage(Images.array[Images.charImgIndex], (int) SimpleDungeonCrawler.character.getLocation().getX(),
				(int) SimpleDungeonCrawler.character.getLocation().getY(), null);
		g.drawImage(Images.array[Images.rightArrowIndex], (int) (948 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), null);
		g.drawImage(Images.array[Images.leftArrowIndex], (int) (0 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), null);
		g.drawImage(Images.array[Images.bottomArrowIndex], (int) (450 * SCALE_FACTOR), (int) (948 * SCALE_FACTOR), null);
		g.drawImage(Images.array[Images.topArrowIndex], (int) (450 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), null);
		StandardRoom current = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		for (int i = 0; i < current.entities.size(); i++) {
			Entity entity = current.entities.get(i);
			g.drawImage(Images.array[entity.getImage()], (int) entity.getLocation().getX(), (int) entity.getLocation().getY(), null);
		}
		g.setColor(Color.red);
		for (int i = 0; i < damageNumbers.size(); i++) {
			FallingDamageNumber currentNum = damageNumbers.get(i);
			Point point = currentNum.getPoint();
			g.setFont(SimpleDungeonCrawler.font);
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
				Point2D doublePoint = SimpleDungeonCrawler.character.getLocation();
				Point location = new Point((int) doublePoint.getX(), (int) doublePoint.getY());
				FallingDamageNumber currentFallingDamage = new FallingDamageNumber(damage, location);
				damageNumbers.add(currentFallingDamage);
				currentFallingDamage.start();
				
				EntityShaker eShake = new EntityShaker(SimpleDungeonCrawler.character);
				eShake.start();
			}
		});
		testDamage.setBounds((int) (700 * SCALE_FACTOR), (int) (200 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);

		testDamage.setFont(SimpleDungeonCrawler.font);
		add(testDamage);
	}
	
	private void createMenuButton() {
		JButton menuButton = new JButton("PAUSE");
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SimpleDungeonCrawler.frame.add(new PauseMenuPanel());
			}
		});
		menuButton.setBounds((int) (700 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuButton.setFont(SimpleDungeonCrawler.font);
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
				Component[] variables = SimpleDungeonCrawler.frame.getContentPane().getComponents();
				setVisible(false);
				createBattle();
			}
		});
		atkButton.setBounds((int) (700 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		atkButton.setFont(SimpleDungeonCrawler.font);
		add(atkButton);
	}

}
