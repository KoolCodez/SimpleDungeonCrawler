package rooms;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.Villager;

public class HomeRoom extends StandardRoom {
	public List<Entity> villagers = new ArrayList<Entity>();
	
	public HomeRoom() {
		super();
		Entity villager = new Villager();
		villagers.add(villager);
		entities.add(villager);
		things.add(villager);
	}
}