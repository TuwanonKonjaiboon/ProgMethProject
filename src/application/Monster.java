package application;

import java.util.ArrayList;

import asset.GameImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import scene.GameScene;

public class Monster extends Pane {
	
	ArrayList<Image> monsterImage = GameImage.monster1Images;
	ImageView imageView = new ImageView(monsterImage.get(0));
	private boolean alreadyhit;
	int id = 0;
	
	public Monster(int type, int x, int y, int width, int height) {
		setTranslateX(x);
		setTranslateY(y);
		alreadyhit = false;
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		imageView.setPreserveRatio(true);
		getChildren().add(imageView);
	}
	public boolean isDead() {
		return alreadyhit;
	}
	
	public void moveY(int value) {
		for (int i = 0; i < Math.abs(value); i++) {			
			this.setTranslateY(this.getTranslateY() + 1);
		}
	}
	
	public void update() {
		imageView.setImage(monsterImage.get(++id % 3));
		
	}
	
	public void gothit() {
		this.alreadyhit = true;
	}
	
	public boolean inScene() {
		boolean in = true;
		if (this.getTranslateY() >= Settings.SCENE_HEIGHT + 1) {
			in = false;
		}
		return in;
	}
}
