package application;

import java.util.ArrayList;

import asset.GameImage;
import i.Collapsible;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import scene.GameScene;

public class Monster extends Pane implements Collapsible {
	
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
		this.setPrefSize(width, height);
		
		getChildren().addAll(imageView);
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

	@Override
	public Shape hb() {
		Shape hb = new Rectangle(this.getPrefWidth(), this.getPrefHeight() / 2);
		hb.setTranslateY(20);
		return hb;
	}

	@Override
	public boolean isCollapse(Collapsible other) {
		return this.localToScene(hb().getBoundsInParent()).intersects(((Pane) other).localToScene(other.hb().getBoundsInParent()));
	}
}
