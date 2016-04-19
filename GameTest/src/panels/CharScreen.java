package panels;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CharScreen {
	
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

}
