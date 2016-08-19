package entities;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import items.Item;

public class Storage extends Thing {
	private List<Item> inventory = new ArrayList<Item>();
	public Storage(Image i, double x, double y, int w, int l) {
		super(x, y, w, l);
		super.setImage(i);
	}
}
