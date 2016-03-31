package misc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	//Arrows\\
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
	//Buttons\\
	public static BufferedImage bagButton;
	public static BufferedImage fightButton;
	public static BufferedImage fleeButton;
	public static BufferedImage moveButton;
	//Characters\\
	public static BufferedImage charImg;
	public static BufferedImage charFront;
	public static BufferedImage charLeft;
	public static BufferedImage charRight;
	public static BufferedImage charLeftOpArm;
	public static BufferedImage charRightOpArm;
	public static BufferedImage battleChar;
	//Enemies\\
	public static BufferedImage battleGoblin;
	//Grounds\\
	public static Image backgroundImg;
	//Items\\
	public static BufferedImage stickItem;
	public static BufferedImage stickItem2;
	public static BufferedImage stickItem3;
	public static BufferedImage stickItem4;
	public static BufferedImage stickItem5;
	//Menus\\
	public static BufferedImage battleMenu;
	public static BufferedImage mainMenu;
	public static BufferedImage pauseMenu;
	
	public static void createImages() throws IOException {
		arrows();
		buttons();
		characters();
		enemies();
		grounds();
		items();
		menus();
	}
	
	public static void arrows() throws IOException {
		rightArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\RightArrowOn.jpg"));
		leftArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\LeftArrowOn.jpg"));
		bottomArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\BotArrowOn.jpg"));
		topArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\TopArrowOn.jpg"));
		rightArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\RightArrowOff.jpg"));
		leftArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\LeftArrowOff.jpg"));
		bottomArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\BotArrowOff.jpg"));
		topArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\TopArrowOff.jpg"));
		rightArrow = rightArrowOn;
		leftArrow = leftArrowOff; // starts off
		bottomArrow = bottomArrowOn;
		topArrow = topArrowOff; // starts off
	}
	
	public static void buttons() throws IOException {
		bagButton = ImageIO.read(new File("src\\Textures\\Buttons\\BagButton.jpg"));
		fightButton = ImageIO.read(new File("src\\Textures\\Buttons\\FightButton.jpg"));
		fleeButton = ImageIO.read(new File("src\\Textures\\Buttons\\FleeButton.jpg"));
		moveButton = ImageIO.read(new File("src\\Textures\\Buttons\\MoveButton.jpg"));

	}
	
	public static void characters() throws IOException {
		charFront = ImageIO.read(new File("src\\Textures\\Characters\\MainCharFront.jpg"));
		charLeft = ImageIO.read(new File("src\\Textures\\Characters\\MainCharLeft.jpg"));
		charRight = ImageIO.read(new File("src\\Textures\\Characters\\MainCharRight.jpg"));
		charLeftOpArm = ImageIO.read(new File("src\\Textures\\Characters\\MainCharLeftRightArmUp.jpg"));
		charRightOpArm = ImageIO.read(new File("src\\Textures\\Characters\\MainCharRightLeftArmUp.jpg"));
		charImg = charFront;
		battleChar = ImageIO.read(new File("src\\Textures\\Characters\\BattleMainChar.jpg"));
	}
	
	public static void enemies() throws IOException {
		battleGoblin = ImageIO.read(new File("src\\Textures\\Enemies\\BattleGoblin.jpg"));
	}
	
	public static void grounds() throws IOException { //I AM THE GROUNDS KEEPER
		backgroundImg = ImageIO.read(new File("src\\Textures\\Grounds\\BasicGround.jpg"));
		double scale = SimpleDungeonCrawler.SCALE_FACTOR;
		Image scaledBackground = backgroundImg.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
		backgroundImg = scaledBackground;
	}
	
	public static void items() throws IOException {
		stickItem = ImageIO.read(new File("src\\Textures\\Items\\Stick.jpg"));
		stickItem2 = ImageIO.read(new File("src\\Textures\\Items\\Stick2.jpg"));
		stickItem3 = ImageIO.read(new File("src\\Textures\\Items\\Stick3.jpg"));
		stickItem4 = ImageIO.read(new File("src\\Textures\\Items\\Stick4.jpg"));
		stickItem5 = ImageIO.read(new File("src\\Textures\\Items\\Stick5.jpg"));
	}
	
	public static void menus() throws IOException {
		battleMenu = ImageIO.read(new File("src\\Textures\\Menus\\BattleMenu2.jpg"));
		mainMenu = ImageIO.read(new File("src\\Textures\\Menus\\BrickStone.jpg"));
		pauseMenu = ImageIO.read(new File("src\\Textures\\Menus\\PauseMenu.jpg"));
	}
}
