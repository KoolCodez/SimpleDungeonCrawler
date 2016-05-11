package misc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	public static Image[] array;
	//Arrows\\
	public static int rightArrow;
	public static int leftArrow;
	public static int bottomArrow;
	public static int topArrow;
	public static int rightArrowOn;
	public static int leftArrowOn;
	public static int bottomArrowOn;
	public static int topArrowOn;
	public static int rightArrowOff;
	public static int leftArrowOff;
	public static int bottomArrowOff;
	public static int topArrowOff;
	//Buttons\\
	public static int bagButton;
	public static int fightButton;
	public static int fleeButton;
	public static int moveButton;
	public static int resumeButton;
	//Characters\\
	public static int charImg;
	public static int charFront;
	public static int charLeft;
	public static int charRight;
	public static int charLeftOpArm;
	public static int charRightOpArm;
	public static int battleChar;
	//Enemies\\
	public static int battleGoblinIndex;
	//Grounds\\
	public static int backgroundImg;
	//Items\\
	public static int stickItem;
	public static int stickItem2;
	public static int stickItem3;
	public static int stickItem4;
	public static int stickItem5;
	//Menus\\
	public static int battleMenu;
	public static int mainMenu;
	public static int pauseMenu;
	
	private static double scale = SimpleDungeonCrawler.SCALE_FACTOR;
	
	public static void createImages() throws IOException {
		array = new Image[33];
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
		bagButton = ImageIO.read(new File("src\\Textures\\Buttons\\BagButton2x.jpg"));
		bagButton = bagButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		fightButton = ImageIO.read(new File("src\\Textures\\Buttons\\FightButton2x.jpg"));
		fightButton = fightButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		fleeButton = ImageIO.read(new File("src\\Textures\\Buttons\\FleeButton2x.jpg"));
		fleeButton = fleeButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		moveButton = ImageIO.read(new File("src\\Textures\\Buttons\\MoveButton2x.jpg"));
		moveButton = moveButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		resumeButton = ImageIO.read(new File("src\\Textures\\Buttons\\ResumeButton2x.jpg"));
		resumeButton = resumeButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);

	}
	
	public static void characters() throws IOException {
		Image charFront = ImageIO.read(new File("src\\Textures\\Characters\\MainCharFront.jpg"));
		charFront = charFront.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		array[charFrontIndex] = charFront;
		Image charLeft = ImageIO.read(new File("src\\Textures\\Characters\\MainCharLeft.jpg"));
		charLeft = charLeft.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		Image charRight = ImageIO.read(new File("src\\Textures\\Characters\\MainCharRight.jpg"));
		charRight = charRight.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		Image charLeftOpArm = ImageIO.read(new File("src\\Textures\\Characters\\MainCharLeftRightArmUp.jpg"));
		charLeftOpArm = charLeftOpArm.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		Image charRightOpArm = ImageIO.read(new File("src\\Textures\\Characters\\MainCharRightLeftArmUp.jpg"));
		charRightOpArm = charRightOpArm.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		Image charImg = charFront;
		Image battleChar = ImageIO.read(new File("src\\Textures\\Characters\\BattleMainChar.jpg"));
		battleChar = battleChar.getScaledInstance((int) (72 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
	}
	
	public static void enemies() throws IOException {
		Image battleGoblin = ImageIO.read(new File("src\\Textures\\Enemies\\BattleGoblin.jpg"));
		battleGoblin = battleGoblin.getScaledInstance((int) (72 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[battleGoblinIndex] = battleGoblin;
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
