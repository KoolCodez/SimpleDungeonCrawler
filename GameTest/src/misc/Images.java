package misc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	public static BufferedImage backgroundImg;
	public static BufferedImage charImg;
	public static BufferedImage rightArrow;
	public static BufferedImage leftArrow;
	public static BufferedImage bottomArrow;
	public static BufferedImage topArrow;
	public static BufferedImage rightArrowOn;
	public static BufferedImage leftArrowOn;
	public static BufferedImage bottomArrowOn;
	public static BufferedImage topArrowOn;
	public static BufferedImage rightArrowOff;
	public static BufferedImage leftArrowOff;
	public static BufferedImage bottomArrowOff;
	public static BufferedImage topArrowOff;
	public static BufferedImage charFront;
	public static BufferedImage charLeft;
	public static BufferedImage charRight;
	public static BufferedImage charLeftOpArm;
	public static BufferedImage charRightOpArm;
	
	public static void createImages() throws IOException {
		charFront = ImageIO.read(new File("src\\Textures\\MainCharFront.jpg"));
		charLeft = ImageIO.read(new File("src\\Textures\\MainCharLeft.jpg"));
		charRight = ImageIO.read(new File("src\\Textures\\MainCharRight.jpg"));
		charLeftOpArm = ImageIO.read(new File("src\\Textures\\MainCharLeftRightArmUp.jpg"));
		charRightOpArm = ImageIO.read(new File("src\\Textures\\MainCharRightLeftArmUp.jpg"));
		backgroundImg = ImageIO.read(new File("src\\Textures\\BasicGround.jpg"));
		charImg = ImageIO.read(new File("src\\Textures\\MainCharFront.jpg"));
		rightArrowOn = ImageIO.read(new File("src\\Textures\\RightArrowOn.jpg"));
		leftArrowOn = ImageIO.read(new File("src\\Textures\\LeftArrowOn.jpg"));
		bottomArrowOn = ImageIO.read(new File("src\\Textures\\BotArrowOn.jpg"));
		topArrowOn = ImageIO.read(new File("src\\Textures\\TopArrowOn.jpg"));
		rightArrowOff = ImageIO.read(new File("src\\Textures\\RightArrowOff.jpg"));
		leftArrowOff = ImageIO.read(new File("src\\Textures\\LeftArrowOff.jpg"));
		bottomArrowOff = ImageIO.read(new File("src\\Textures\\BotArrowOff.jpg"));
		topArrowOff = ImageIO.read(new File("src\\Textures\\TopArrowOff.jpg"));

		rightArrow = rightArrowOn;
		leftArrow = leftArrowOff; // starts off
		bottomArrow = bottomArrowOn;
		topArrow = topArrowOff; // starts off
	}
}
