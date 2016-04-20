package panels;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import misc.Images;
import misc.SimpleDungeonCrawler;

public class CoreGameplayPanel {

	private static double SCALE_FACTOR = SimpleDungeonCrawler.SCALE_FACTOR;
	private static int BUTTON_WIDTH = Panels.BUTTON_WIDTH;
	private static int BUTTON_HEIGHT = Panels.BUTTON_HEIGHT;
	private static int SCALED_100 = Panels.SCALED_100;

	public static void createCoreGameplayPanel() {
		// Declarations
		JButton menuButton = new JButton("PAUSE");
		JButton atkButton = new JButton("ATTACK");
		// panel
		Panels.coreGameplayPanel = new JPanel() {
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
		Panels.coreGameplayPanel.setLayout(null);
		Panels.coreGameplayPanel.add(atkButton);
		Panels.coreGameplayPanel.add(menuButton);

		// menu button
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().add(Panels.menuPanel);
				Panels.frame.getContentPane().remove(Panels.atkPanel);
				Panels.frame.getContentPane().remove(Panels.coreGameplayPanel);
			}
		});
		menuButton.setBounds((int) (700 * SCALE_FACTOR), (int) (0 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		menuButton.setFont(SimpleDungeonCrawler.font);

		// attack button
		atkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panels.frame.getContentPane().remove(Panels.coreGameplayPanel);
				Panels.frame.getContentPane().add(Panels.atkPanel);
				SwingWorker<Integer, String> worker = new SwingWorker<Integer, String>() {
					@Override
					protected Integer doInBackground() throws Exception {
						SimpleDungeonCrawler.battleSequence(/* console1 */);
						return 0;
					}
				};
				SimpleDungeonCrawler.flee = false;
				worker.execute();
			}
		});
		atkButton.setBounds((int) (700 * SCALE_FACTOR), (int) (100 * SCALE_FACTOR), BUTTON_WIDTH, BUTTON_HEIGHT);
		atkButton.setFont(SimpleDungeonCrawler.font);
		// attackButton.setIcon(defaultIcon);
	}

}
