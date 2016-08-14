package misc;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextureGenerator {
	
	public Image compileImage(ArrayList<Image> images) {
		int scaled100 = (int) (100 * SDC.SCALE_FACTOR);
		BufferedImage compiledImage = new BufferedImage(scaled100, scaled100, BufferedImage.TYPE_INT_ARGB);
		Graphics g = compiledImage.createGraphics();
		for (int i = 0; i < images.size(); i++) {
			g.drawImage(images.get(i), 0, 0, null);
		}
		
		return compiledImage;
	}
	
	public Image rotate(Image image, int degrees) {
		double rotationRequired = Math.toRadians (degrees);
		double locationX = image.getWidth(null) / 2;
		double locationY = image.getHeight(null) / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		
		BufferedImage i = new BufferedImage((int) image.getWidth(null), (int) image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = i.createGraphics();
		g2d.drawImage(image, tx, null);
		/*double theta = Math.toRadians (degrees);
		double w = image.getWidth(null);
		double h = image.getHeight(null);
		AffineTransform tx = new AffineTransform();
		tx.translate(h / 2, w / 2);
		tx.rotate(theta);
		tx.translate(-w / 2, -h / 2);
		double newW = Math.cos(theta) * w + Math.sin(theta) * h;
		double newH = Math.sin(theta) * w + Math.cos(theta) * h;
		BufferedImage i = new BufferedImage((int) Math.sqrt((SDC.SCALED_100 * SDC.SCALED_100) * 2), 
				(int) Math.sqrt((SDC.SCALED_100 * SDC.SCALED_100) * 2), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = i.createGraphics();
		g2d.drawImage(image, tx, null);*/
		return i;
	}
}
