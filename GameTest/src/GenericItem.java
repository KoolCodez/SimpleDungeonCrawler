import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GenericItem {
	public static String itemName;
	public GenericItem(ImageIcon image, String name) {
		JLabel item = new JLabel(image);
		itemName = name;
	}
}
