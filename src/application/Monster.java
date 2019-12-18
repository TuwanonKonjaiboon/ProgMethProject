package application;

import java.util.ArrayList;

import asset.GameImage;
import i.Collapsible;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Monster extends Pane implements Collapsible {
	
	private boolean alreadyhit = false;
	private ArrayList<Image> monsterImage = GameImage.monsterImages;
	
	ImageView imageView = new ImageView(monsterImage.get(0));
	
	public Monster(int type, int x, int y) {
		this.setTranslateX(x);
		this.setTranslateY(y);
		
		alreadyhit = false;
		
		setImageType(type);
		
		getChildren().addAll(imageView);
	}
	
	
	public boolean isDead() {
		return alreadyhit;
	}
	
	public void setImageType(int type) {
		imageView.setImage(monsterImage.get(type));
		if (type == 0) {
			imageView.setFitWidth(80);
			imageView.setFitHeight(100);
		} else if (type == 1) {
			imageView.setFitWidth(100);
			imageView.setFitHeight(120);
		}
	}
	
	public void moveY(int value) {
		for (int i = 0; i < Math.abs(value); i++) {			
			this.setTranslateY(this.getTranslateY() + 1);
		}
	}
	
	public void update() {
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
		Shape hb = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
		return hb;
	}

	@Override
	public boolean isCollapse(Collapsible other) {
		return this.localToScene(hb().getBoundsInParent()).intersects(((Pane) other).localToScene(other.hb().getBoundsInParent()));
	}
}
