package items;

import javax.swing.ImageIcon;

import misc.Images;

public class Stick extends GenericItem {
	static ImageIcon image = new ImageIcon(Images.stickItem);
	static String name = "Stick";
	
	public Stick() {
		super(image, name);
		// TODO Auto-generated constructor stub
	}

}
