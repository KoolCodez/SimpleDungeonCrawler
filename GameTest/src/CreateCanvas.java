import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CreateCanvas extends JComponent {
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Image backgroundImg = Toolkit.getDefaultToolkit().getImage("C:\\Users\\gaubnik000\\My Documents\\Github\\SimpleDungeonCrawler\\Textures\\BasicGround.jpg");
		g2.drawImage(backgroundImg, 0, 0, this);
		g2.finalize();
	}
}
