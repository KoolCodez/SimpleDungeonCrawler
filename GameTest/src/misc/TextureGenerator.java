package misc;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextureGenerator {
	
	public Image compileImage(ArrayList<Image> images) {
		int scaled100 = (int) (100 * SDC.SCALE_FACTOR);
		BufferedImage compiledImage = new BufferedImage(scaled100, scaled100, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = compiledImage.createGraphics();
		for (int i = 0; i < images.size(); i++) {
			g.drawImage(images.get(0), 0, 0, null);
		}
		return compiledImage;
	}
}
