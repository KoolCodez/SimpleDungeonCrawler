package items;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GenericItem {
	public static String itemName;
	public static JLabel item;
	public GenericItem(ImageIcon image, String name) {
		item = new JLabel(image);
		itemName = name;
	}
}
