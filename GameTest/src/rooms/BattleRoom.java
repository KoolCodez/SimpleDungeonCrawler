package rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import entities.Entity;
import entities.Goblin;
import items.Weapon;
import misc.Images;
import misc.SDC;

public class BattleRoom extends StandardRoom {
	private final double SCALE_FACTOR = SDC.SCALE_FACTOR;
	public BattleRoom(int enemies) {
		super();
		typeOfRoom = BATTLE_TAG;
		for (int i = 0; i <= enemies; i++) {
			Entity temp = new Goblin();
			temp.setLocation(700*SCALE_FACTOR*Math.random() + 50,  700*SCALE_FACTOR*Math.random() + 50);
			temp.setRoom(SDC.roomArray[SDC.loc.x][SDC.loc.y]);
			entities.add(temp);
			things.add(temp);
		}
	}

}
