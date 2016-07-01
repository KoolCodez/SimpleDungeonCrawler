package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import combatSystem.ControlRouter;
import misc.Entity;
import misc.Images;
import misc.SimpleDungeonCrawler;
import rooms.StandardRoom;

public class BattleAttackPanel extends JPanel {
	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = SimpleDungeonCrawler.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = SimpleDungeonCrawler.BUTTON_HEIGHT;
	private static int MENU_SIZE = SimpleDungeonCrawler.MENU_SIZE;
	private static int SCALED_100 = SimpleDungeonCrawler.SCALED_100;

	private BattleViewPanel battleView;
	private ControlRouter control;
	
	public BattleAttackPanel(ControlRouter c) {
		setLayout(null);
		SimpleDungeonCrawler.frame.add(this);
		control = c;
		battleView = control.battleView;
		createAttackButton();
		createSelectButton();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(Images.array[Images.battleMenuIndex], 0, 0, MENU_SIZE, MENU_SIZE, null);
		g.setColor(Color.red);
		g.fillRect((int) (214 * SCALE_FACTOR), (int) (932 * SCALE_FACTOR),
				(int) (440 * SCALE_FACTOR * SimpleDungeonCrawler.character.stats.getHealth()
						/ SimpleDungeonCrawler.character.stats.getMaxHealth()),
				(int) (36 * SCALE_FACTOR));
		g.setColor(Color.black);
		// g.drawString("Turn Points" + battle.waitForTurn.getTurnPoints(), 50,
		// 50);
		// g.drawString(console1.get(console1.size() - 1), 10, 100);
	}
	
	private void attack() {
		if (control.waitForTurn.getTurnPoints() >= 3) {
			control.waitForTurn.setTurnPoints(-3);
			control.attack(SimpleDungeonCrawler.character, SimpleDungeonCrawler.character.getSelectedEntity());
			System.out.println("3");
		} else {
			System.out.println("Not enough turn points");
		}
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setVisible(false);
		control.switchToTurnPhase();
	}
	
	private void createAttackButton() {
		JButton attackButton = new JButton("ATTACK");
		attackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Entity character = SimpleDungeonCrawler.character;
				attack();
			
			}
		});
		attackButton.setBounds((int) (698 * SCALE_FACTOR), (int) (148 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		add(attackButton);
	}
	
	private void createSelectButton() {
		StandardRoom currentRoom = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y];
		SimpleDungeonCrawler.character.setSelectedEntity(currentRoom.entities.get(0));
		JButton selectButton = new JButton("Select Enemy");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						control.selectEntity();;
						return 0;
					}
				};
				worker.execute();
			}
		});
		selectButton.setBounds((int) (698 * SCALE_FACTOR), (int) (348 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		add(selectButton);
	}
	
	private void selectEntity() {
		Entity targetedEntity = SimpleDungeonCrawler.roomArray[SimpleDungeonCrawler.loc.x][SimpleDungeonCrawler.loc.y].entities
				.get(0);
		control.highlight(targetedEntity);
		
		SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
			@Override
			protected Integer doInBackground() throws Exception {
				control.selectEntity();
				return 0;
			}
		};
		worker.execute();
		control.highlight(targetedEntity);
	}
}