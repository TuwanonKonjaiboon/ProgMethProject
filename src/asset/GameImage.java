package asset;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class GameImage {
	
	public static Image platform1Image;
	public static Image platform2Image;
	public static ArrayList<Image> platform3Images;
	public static Image platform9Image;
	
	public static ArrayList<Image> monster1Images;
	
	public static void init() {
		platform1Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-green.png"));
		platform2Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-blue.png"));
		platform3Images = new ArrayList<Image>();
		for (int i = 1; i <= 6; i++) {
			platform3Images.add(new Image(ClassLoader.getSystemResourceAsStream("images/brown/p-brown-" + i + ".png")));
		}
		platform9Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-white.png"));
		monster1Images = new ArrayList<Image>();
		for (int i = 1; i <= 3; i++) {
			monster1Images.add(new Image(ClassLoader.getSystemResourceAsStream("images/monsters/bat" + i + ".png")));
		}
	}
	
}
