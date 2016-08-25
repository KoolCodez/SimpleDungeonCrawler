package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import items.Item;
import misc.Images;
import misc.SDC;

public class Villager extends Entity {
	double SCALE_FACTOR = SDC.SCALE_FACTOR;
	public Villager() {
		stats.setStats(Math.random() * 10, Math.random() * 10, Math.random() * 10, Math.random() * 10,
				Math.random() * 10, Math.random() * 10, Math.random() * 10, (int) (Math.random() * 10), 3);
		super.setType("Villager");
		super.setName("Nameless Pawn");
		location = new Point2D.Double(randomLoc(), randomLoc());
		setImage(Images.array[18]);
		this.setSize((int) (72 * SCALE_FACTOR), (int) (92 * SCALE_FACTOR));
		this.setLocation(location.getX(), location.getY());
	}
	
	@Override
	public void addOptions(JPanel panel) {
		JButton pokeButton = new JButton("POKE");
		pokeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Ouch! Stop That!");
			}
		});
		pokeButton.setBounds((int) (1000 * SDC.SCALE_FACTOR), (int) (400 * SDC.SCALE_FACTOR), SDC.BUTTON_WIDTH, SDC.BUTTON_HEIGHT);
		panel.add(pokeButton);
	}
	
	private double randomLoc() {
		return 500*SCALE_FACTOR + ((Math.random() * 400*SCALE_FACTOR)- 200*SCALE_FACTOR);
	}
}