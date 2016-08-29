package rooms;

import java.util.ArrayList;
import java.util.List;

import misc.SDC;
import things.entities.Entity;
import things.entities.Villager;
import things.storage.Chest;

public class HomeRoom extends StandardRoom {
	public List<Villager> villagers = new ArrayList<Villager>();
	
	public HomeRoom() {
		super();
		Villager villager = new Villager();
		villagers.add(villager);
		entities.add(villager);
		things.add(villager);
		Chest chest = new Chest(450*SDC.SCALE_FACTOR,100*SDC.SCALE_FACTOR, 0);
		things.add(chest);
	}
}