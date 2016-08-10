package misc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	//Arrows\\
	public static final int rightArrowOnIndex = 1;
	public static final int leftArrowOnIndex = 2;
	public static final int bottomArrowOnIndex = 3;
	public static final int topArrowOnIndex = 4;
	public static final int rightArrowOffIndex = 5;
	public static final int leftArrowOffIndex = 6;
	public static final int bottomArrowOffIndex = 7;
	public static final int topArrowOffIndex = 8;
	//Buttons\\
	public static final int bagButtonIndex = 9;
	public static final int fightButtonIndex = 10;
	public static final int fleeButtonIndex = 11;
	public static final int moveButtonIndex = 12;
	public static final int resumeButtonIndex = 13;
	//Characters\\
	
	public static final int charFrontIndex = 14;
	public static final int charLeftIndex = 15;
	public static final int charRightIndex = 16;
	public static final int charLeftOpArmIndex = 17;
	public static final int charRightOpArmIndex = 18;
	public static final int battleCharIndex = 19;
	//Enemies\\
	public static final int battleGoblinIndex = 20;
	//Grounds\\
	public static final int backgroundImgIndex = 21;
	public static final int battleViewBackgroundIndex = 30;
	//Items\\
	public static final int stickItemIndex = 22;
	public static final int stickItem2Index = 23;
	public static final int stickItem3Index = 24;
	public static final int stickItem4Index = 25;
	public static final int stickItem5Index = 26;
	//Menus\\
	public static final int battleMenuIndex = 27;
	public static final int mainMenuIndex = 28;
	public static final int pauseMenuIndex = 29;
	
	public static Image[] array;
	public static int rightArrowIndex;
	public static int leftArrowIndex;
	public static int bottomArrowIndex;
	public static int topArrowIndex;
	
	private static double scale = SDC.SCALE_FACTOR;
	
	public static void createImages() throws IOException {
		array = new Image[31];
		arrows();
		buttons();
		characters();
		enemies();
		grounds();
		items();
		menus();
	}
	
	public static void arrows() throws IOException {
		Image rightArrowOn = ImageIO.read(new File("src/Textures/Arrows/RightArrowOn.jpg"));
		rightArrowOn = rightArrowOn.getScaledInstance((int) (50 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[rightArrowOnIndex] = rightArrowOn;
		Image leftArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\LeftArrowOn.jpg"));
		leftArrowOn = leftArrowOn.getScaledInstance((int) (50 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[leftArrowOnIndex] = leftArrowOn;
		Image bottomArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\BotArrowOn.jpg"));
		bottomArrowOn = bottomArrowOn.getScaledInstance((int) (100 * scale), (int) (50 * scale), Image.SCALE_SMOOTH);
		array[bottomArrowOnIndex] = bottomArrowOn;
		Image topArrowOn = ImageIO.read(new File("src\\Textures\\Arrows\\TopArrowOn.jpg"));
		topArrowOn = topArrowOn.getScaledInstance((int) (100 * scale), (int) (50 * scale), Image.SCALE_SMOOTH);
		array[topArrowOnIndex] = topArrowOn;
		Image rightArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\RightArrowOff.jpg"));
		rightArrowOff = rightArrowOff.getScaledInstance((int) (50 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[rightArrowOffIndex] = rightArrowOff;
		Image leftArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\LeftArrowOff.jpg"));
		leftArrowOff = leftArrowOff.getScaledInstance((int) (50 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[leftArrowOffIndex] = leftArrowOff;
		Image bottomArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\BotArrowOff.jpg"));
		bottomArrowOff = bottomArrowOff.getScaledInstance((int) (100 * scale), (int) (50 * scale), Image.SCALE_SMOOTH);
		array[bottomArrowOffIndex] = bottomArrowOff;
		Image topArrowOff = ImageIO.read(new File("src\\Textures\\Arrows\\TopArrowOff.jpg"));
		topArrowOff = topArrowOff.getScaledInstance((int) (100 * scale), (int) (50 * scale), Image.SCALE_SMOOTH);
		array[topArrowOffIndex] = topArrowOff;
		rightArrowIndex = rightArrowOnIndex;
		
		leftArrowIndex = leftArrowOffIndex; // starts off
		
		bottomArrowIndex = bottomArrowOnIndex;
		
		topArrowIndex = topArrowOffIndex; // starts off
		
	}
	
	public static void buttons() throws IOException {
		Image bagButton = ImageIO.read(new File("src\\Textures\\Buttons\\BagButton2x.jpg"));
		bagButton = bagButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[bagButtonIndex] = bagButton;
		Image fightButton = ImageIO.read(new File("src\\Textures\\Buttons\\FightButton2x.jpg"));
		fightButton = fightButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[fightButtonIndex] =fightButton;
		Image fleeButton = ImageIO.read(new File("src\\Textures\\Buttons\\FleeButton2x.jpg"));
		fleeButton = fleeButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[fleeButtonIndex] = fleeButton;
		Image moveButton = ImageIO.read(new File("src\\Textures\\Buttons\\MoveButton2x.jpg"));
		moveButton = moveButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[moveButtonIndex] = moveButton;
		Image resumeButton = ImageIO.read(new File("src\\Textures\\Buttons\\ResumeButton2x.jpg"));
		resumeButton = resumeButton.getScaledInstance((int) (300 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[resumeButtonIndex] = resumeButton;
	}
	
	public static void characters() throws IOException {
		Image charFront = ImageIO.read(new File("src\\Textures\\Characters\\MainCharFront.jpg"));
		charFront = charFront.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		array[charFrontIndex] = charFront;
		Image charLeft = ImageIO.read(new File("src\\Textures\\Characters\\MainCharLeft.jpg"));
		charLeft = charLeft.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		array[charLeftIndex] = charLeft;
		Image charRight = ImageIO.read(new File("src\\Textures\\Characters\\MainCharRight.jpg"));
		charRight = charRight.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		array[charRightIndex] = charRight;
		Image charLeftOpArm = ImageIO.read(new File("src\\Textures\\Characters\\MainCharLeftRightArmUp.jpg"));
		charLeftOpArm = charLeftOpArm.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		array[charLeftOpArmIndex] = charLeftOpArm;
		Image charRightOpArm = ImageIO.read(new File("src\\Textures\\Characters\\MainCharRightLeftArmUp.jpg"));
		charRightOpArm = charRightOpArm.getScaledInstance((int) (72 * scale), (int) (92 * scale), Image.SCALE_SMOOTH);
		array[charRightOpArmIndex] = charRightOpArm;
		Image battleChar = ImageIO.read(new File("src\\Textures\\Characters\\MainCharTestClear.png"));
		battleChar = battleChar.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[battleCharIndex] = battleChar;
	}
	
	public static void enemies() throws IOException {
		Image battleGoblin = ImageIO.read(new File("src\\Textures\\Enemies\\BattleGoblin.jpg"));
		battleGoblin = battleGoblin.getScaledInstance((int) (72 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[battleGoblinIndex] = battleGoblin;
	}
	
	public static void grounds() throws IOException { //I AM THE GROUNDS KEEPER
		Image backgroundImg = ImageIO.read(new File("src\\Textures\\Grounds\\BasicGround.jpg"));
		backgroundImg = backgroundImg.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
		array[backgroundImgIndex] = backgroundImg;
		Image battleViewBackground = ImageIO.read(new File("src\\Textures\\Grounds\\BasicGround.jpg"));
		battleViewBackground = battleViewBackground.getScaledInstance((int) (697 * scale), (int) (710 * scale), Image.SCALE_SMOOTH);
		array[battleViewBackgroundIndex] = battleViewBackground;
	}
	
	public static void items() throws IOException {
		Image stickItem = ImageIO.read(new File("src\\Textures\\Items\\Stick.jpg"));
		stickItem = stickItem.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[stickItemIndex] = stickItem;
		Image stickItem2 = ImageIO.read(new File("src\\Textures\\Items\\Stick2.jpg"));
		stickItem2 = stickItem2.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[stickItem2Index] = stickItem2;
		Image stickItem3 = ImageIO.read(new File("src\\Textures\\Items\\Stick3.jpg"));
		stickItem3 = stickItem3.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[stickItem3Index] = stickItem3;
		Image stickItem4 = ImageIO.read(new File("src\\Textures\\Items\\Stick4.jpg"));
		stickItem4 = stickItem4.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[stickItem4Index] = stickItem4;
		Image stickItem5 = ImageIO.read(new File("src\\Textures\\Items\\Stick5.jpg"));
		stickItem5 = stickItem5.getScaledInstance((int) (100 * scale), (int) (100 * scale), Image.SCALE_SMOOTH);
		array[stickItem5Index] = stickItem5;
	}
	
	public static void menus() throws IOException {
		Image battleMenu = ImageIO.read(new File("src\\Textures\\Menus\\BattleMenu2.jpg"));
		battleMenu = battleMenu.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
		array[battleMenuIndex] = battleMenu;
		Image mainMenu = ImageIO.read(new File("src\\Textures\\Menus\\BrickStone.jpg"));
		mainMenu = mainMenu.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
		array[mainMenuIndex] = mainMenu;
		Image pauseMenu = ImageIO.read(new File("src\\Textures\\Menus\\PauseMenu.jpg"));
		pauseMenu = pauseMenu.getScaledInstance((int) (1000 * scale), (int) (1000 * scale), Image.SCALE_SMOOTH);
		array[pauseMenuIndex] = pauseMenu;
	}
}
