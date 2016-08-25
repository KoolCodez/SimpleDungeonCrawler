package rooms;

import java.util.ArrayList;
import java.util.List;

import entities.Chest;
import entities.Entity;
import entities.Villager;
import misc.SDC;

public class HomeRoom extends StandardRoom {
	public List<Entity> villagers = new ArrayList<Entity>();
	
	public HomeRoom() {
		super();
		Entity villager = new Villager();
		villagers.add(villager);
		entities.add(villager);
		things.add(villager);
		Chest chest = new Chest(450*SDC.SCALE_FACTOR,100*SDC.SCALE_FACTOR);
		things.add(chest);
	}
}