package application;

import asset.GameImage;
import i.Collapsible;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import scene.GameScene;

public class Knife extends Pane implements Collapsible {
	ImageView knifeView = new ImageView(GameImage.knifeImage) ;
	
	private static int yspeed;
	private boolean alreadyhit;
	private boolean inScene;
	private int xspeed;
	private int direction;
	public Knife() {
		this.setTranslateX(GameScene.player.getTranslateX());
		this.setTranslateY(GameScene.player.getTranslateY());
		xspeed = 0;
		yspeed = 20;
		alreadyhit = false;
		direction = 0;
		
		knifeView.setFitHeight(48);
		knifeView.setFitWidth(48);
		this.setRotate(50);
		this.setScaleX(1.2);
		this.setScaleY(1.2);
		
		RotateTransition rt = new RotateTransition(Duration.millis(500), this);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.setFromAngle(0);
		rt.setToAngle(360);
		rt.play();
		
		this.getChildren().add(knifeView);
	}
	
	public void setdirection() {
		if (GameScene.isPress(KeyCode.RIGHT)) {
			direction = 3;
		}	
		else if (GameScene.isPress(KeyCode.LEFT)) {
			direction = -3;
		}	
		else {
			direction = 0;
		}
		xspeed = 4*direction;
		
	}
	
	private void isHit() {
		for (Monster monster: GameScene.monsters) {
			if (this.isCollapse(monster)) {
				monster.gothit();
				alreadyhit = true;
			}
		}
	}
	public void update() {
		moveX();
		moveY();
		isHit();
		inScene();
	}
	
	public void inScene() {
		boolean isout = true;
		if (this.getTranslateY() >= Settings.SCENE_HEIGHT  ) {
			isout = false;
		}
		if (this.getTranslateX() >= Settings.SCENE_WIDTH) {
			isout = false;
		}
		inScene = isout;
	}
	
	public void moveY(int value) {
		for (int i = 0; i < Math.abs(value); i++) {			
			this.setTranslateY(this.getTranslateY() + 1);
		}
	}
	
	public void moveX() {
		this.setTranslateX(this.getTranslateX() + xspeed);
	}
	public void moveY() {
		this.setTranslateY(this.getTranslateY() - yspeed);
	}

	public boolean isAlreadyhit() {
		return alreadyhit;
	}
	
	public boolean isInScene() {
		return inScene;
	}

	@Override
	public Shape hb() {
		Shape hb = new Rectangle(30, 10, Color.BROWN);
		hb.setTranslateX(knifeView.getFitWidth() / 2 - hb.getBoundsInParent().getWidth() / 2);
		hb.setTranslateY(knifeView.getFitHeight() / 2 - hb.getBoundsInParent().getHeight() / 2);
		return hb;
	}

	@Override
	public boolean isCollapse(Collapsible other) {
		return this.localToScene(hb().getBoundsInParent()).intersects(((Pane) other).localToScene(other.hb().getBoundsInParent()));
	}
	
	}
