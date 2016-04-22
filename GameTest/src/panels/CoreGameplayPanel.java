package panels;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import combatSystem.Battle;
import misc.Images;
import misc.SimpleDungeonCrawler;
import movement.MovementController;

public class CoreGameplayPanel {

	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = Panels.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = Panels.BUTTON_HEIGHT;
	private static int SCALED_100 = Panels.SCALED_100;
	
	private JPanel coreGameplayPanel;

	public CoreGameplayPanel() {
		createCoreGameplayPanel();
		MovementController movementController = new MovementController(coreGameplayPanel);
		createMenuButton();
		createAttackButton();
	}
	
	public JPanel getPanel() {
		return coreGameplayPanel;
	}
	
	private void createCoreGameplayPanel() {
		coreGameplayPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Images.backgroundImg, 0, 0, null);
				g.drawImage(Images.charImg, (int) SimpleDungeonCrawler.character.getLocation().getX(),
						(int) SimpleDungeonCrawler.character.getLocation().getY(), null);
				g.drawImage(Images.rightArrow, (int) (948 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), null);
				g.drawImage(Images.leftArrow, (int) (0 * SCALE_FACTOR), (int) (450 * SCALE_FACTOR), null);
				g.drawImage(Images.bottomArrow, (int) (450 * SCALE_FACTOR), (int) (948 * SCALE_FACTOR), null);
				g.drawImage(Images.topArrow, (int) (450 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), null);
			}
		};
		coreGameplayPanel.setLayout(null);
	}
	
	private void createMenuButton() {
		JButton menuButton = new JButton("PAUSE");
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().remove(coreGameplayPanel);
				Panels.frame.getContentPane().add(new PauseMenuPanel().getPanel());
			}
		});
		menuButton.setBounds((int) (700 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuButton.setFont(SimpleDungeonCrawler.font);
		coreGameplayPanel.add(menuButton);
	}
	
	private void createAttackButton() {
		JButton atkButton = new JButton("ATTACK");
		atkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().remove(coreGameplayPanel);
				AttackPanel attackPanel = new AttackPanel();
				Panels.frame.getContentPane().add(attackPanel.getPanel());
			}
		});
		atkButton.setBounds((int) (700 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		atkButton.setFont(SimpleDungeonCrawler.font);
		coreGameplayPanel.add(atkButton);
	}

}
