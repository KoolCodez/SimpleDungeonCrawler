package rooms;

import java.util.ArrayList;
import java.util.List;

import misc.SDC;
import things.entities.Entity;
import things.entities.Villager;
import things.items.armors.Linens;
import things.items.weapons.Club;
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
		chest.addItem(new Linens("head"));
		chest.addItem(new Club());
		chest.addItem(new Linens("body"));
		chest.addItem(new Linens("hands"));
		//chest.addItem(new Linens("legs"));
		things.add(chest);
	}
}