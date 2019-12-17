package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import scene.GameScene;

public class Knife extends Pane {
	Image knifeImage = new Image(ClassLoader.getSystemResourceAsStream("images/knife/monster_fatman.png"));
	ImageView knifeView = new ImageView(knifeImage) ;
	
	private static int yspeed;
	private boolean alreadyhit;
	private boolean inScene;
	private int xspeed;
	private int direction;
	public Knife() {
		this.setTranslateX(GameScene.player.getTranslateX());
		this.setTranslateY(GameScene.player.getTranslateY());
		yspeed = 5;
		alreadyhit = false;
		direction = 0;
		xspeed = 0;
		knifeView.setFitHeight(48);
		knifeView.setFitWidth(48);
		this.getChildren().add(knifeView);
	}
	
	public void setdirection() {
		if (GameScene.isPress(KeyCode.RIGHT)) {
			direction = 1;
		}	
		else if (GameScene.isPress(KeyCode.LEFT)) {
			direction = -1;
		}	
		else {
			direction = 0;
		}
		xspeed = 4*direction;
		
	}
	
	private void isHit() {
		for (Monster monster: GameScene.monsters) {
			if (this.getBoundsInParent().intersects(monster.getBoundsInParent())) {
				monster.gothit();
				//hitting!
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
	
	}
