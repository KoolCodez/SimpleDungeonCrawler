package misc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	//Arrows\\
	public static Image rightArrow;
	public static Image leftArrow;
	public static Image bottomArrow;
	public static Image topArrow;
	public static Image rightArrowOn;
	public static Image leftArrowOn;
	public static Image bottomArrowOn;
	public static Image topArrowOn;
	public static Image rightArrowOff;
	public static Image leftArrowOff;
	public static Image bottomArrowOff;
	public static Image topArrowOff;
	//Buttons\\
	public static Image bagButton;
	public static Image fightButton;
	public static Image fleeButton;
	public static Image moveButton;
	//Characters\\
	public static Image charImg;
	public static Image charFront;
	public static Image charLeft;
	public static Image charRight;
	public static Image charLeftOpArm;
	public static Image charRightOpArm;
	public static Image battleChar;
	//Enemies\\
	public static Image battleGoblin;
	//Grounds\\
	public static Image backgroundImg;
	//Items\\
	public static Image stickItem;
	public static Image stickItem2;
	public static Image stickItem3;
	public static Image stickItem4;
	public static Image stickItem5;
	//Menus\\
	public static Image battleMenu;
	public static Image mainMenu;
	public static Image pauseMenu;
	
	private static double scale = SimpleDungeonCrawler.SCALE_FACTOR;
	
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
		rightArrowOn = rightArrowOn.getScaledInstance((int) (50 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		leftArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\LeftArrowOn.jpg"));
		leftArrowOn = leftArrowOn.getScaledInstance((int) (50 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		bottomArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\BotArrowOn.jpg"));
		bottomArrowOn = bottomArrowOn.getScaledInstance((int) (100 * scale), (int) (50 * scale), Image.SCALE_SMOOTH);
		topArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\TopArrowOn.jpg"));
		topArrowOn = topArrowOn.getScaledInstance((int) (100 * scale), (int) (50 * scale), Image.SCALE_SMOOTH);
		rightArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\RightArrowOff.jpg"));
		rightArrowOff = rightArrowOff.getScaledInstance((int) (50 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		leftArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\LeftArrowOff.jpg"));
		leftArrowOff = leftArrowOff.getScaledInstance((int) (50 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		bottomArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\BotArrowOff.jpg"));
		bottomArrowOff = bottomArrowOff.getScaledInstance((int) (100 * scale), (int) (50 * scale), Image.SCALE_SMOOTH);
		topArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\TopArrowOff.jpg"));
		topArrowOff = topArrowOff.getScaledInstance((int) (100 * scale), (int) (50 * scale), Image.SCALE_SMOOTH);
		rightArrow = rightArrowOn;
		leftArrow = leftArrowOff; // starts off
		bottomArrow = bottomArrowOn;
		topArrow = topArrowOff; // starts off
	}
	
	public static void buttons() throws IOException {
		bagButton = ImageIO.read(new File("src\\Textures\\Buttons\\BagButton.jpg"));
		bagButton = bagButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		fightButton = ImageIO.read(new File("src\\Textures\\Buttons\\FightButton.jpg"));
		fightButton = fightButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		fleeButton = ImageIO.read(new File("src\\Textures\\Buttons\\FleeButton.jpg"));
		fleeButton = fleeButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		moveButton = ImageIO.read(new File("src\\Textures\\Buttons\\MoveButton.jpg"));
		moveButton = moveButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);

	}
	
	public static void characters() throws IOException {
		charFront = ImageIO.read(new File("src\\Textures\\Characters\\MainCharFront.jpg"));
		charFront = charFront.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		charLeft = ImageIO.read(new File("src\\Textures\\Characters\\MainCharLeft.jpg"));
		charLeft = charLeft.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		charRight = ImageIO.read(new File("src\\Textures\\Characters\\MainCharRight.jpg"));
		charRight = charRight.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		charLeftOpArm = ImageIO.read(new File("src\\Textures\\Characters\\MainCharLeftRightArmUp.jpg"));
		charLeftOpArm = charLeftOpArm.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		charRightOpArm = ImageIO.read(new File("src\\Textures\\Characters\\MainCharRightLeftArmUp.jpg"));
		charRightOpArm = charRightOpArm.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		charImg = charFront;
		battleChar = ImageIO.read(new File("src\\Textures\\Characters\\BattleMainChar.jpg"));
		battleChar = battleChar.getScaledInstance((int) (72 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
	}
	
	public static void enemies() throws IOException {
		battleGoblin = ImageIO.read(new File("src\\Textures\\Enemies\\BattleGoblin.jpg"));
		battleGoblin = battleGoblin.getScaledInstance((int) (200 * scale), (int) (300 * scale), Image.SCALE_SMOOTH);
	}
	
	public static void grounds() throws IOException { //I AM THE GROUNDS KEEPER
		backgroundImg = ImageIO.read(new File("src\\Textures\\Grounds\\BasicGround.jpg"));
		backgroundImg = backgroundImg.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
	}
	
	public static void items() throws IOException {
		stickItem = ImageIO.read(new File("src\\Textures\\Items\\Stick.jpg"));
		stickItem = stickItem.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		stickItem2 = ImageIO.read(new File("src\\Textures\\Items\\Stick2.jpg"));
		stickItem2 = stickItem2.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		stickItem3 = ImageIO.read(new File("src\\Textures\\Items\\Stick3.jpg"));
		stickItem3 = stickItem3.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		stickItem4 = ImageIO.read(new File("src\\Textures\\Items\\Stick4.jpg"));
		stickItem4 = stickItem4.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		stickItem5 = ImageIO.read(new File("src\\Textures\\Items\\Stick5.jpg"));
		stickItem5 = stickItem5.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
	}
	
	public static void menus() throws IOException {
		battleMenu = ImageIO.read(new File("src\\Textures\\Menus\\BattleMenu2.jpg"));
		battleMenu = battleMenu.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
		mainMenu = ImageIO.read(new File("src\\Textures\\Menus\\BrickStone.jpg"));
		mainMenu = mainMenu.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
		pauseMenu = ImageIO.read(new File("src\\Textures\\Menus\\PauseMenu.jpg"));
		pauseMenu = pauseMenu.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
	}
}
