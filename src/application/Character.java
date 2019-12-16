package application;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import scene.GameScene;

public class Character extends Pane {
	
	Image doodleImg = new Image(ClassLoader.getSystemResourceAsStream("images/doodleR.png"));
	ImageView imageView = new ImageView(doodleImg);
	
	// Player velocity vector
	public Point2D playerVelocity = new Point2D(0, 0);
	// Player Facing Right: 1, Left: -1 
	public int hFacing = 1;
	
	private boolean canJump = true;
	
	public Character() {
		imageView.setFitHeight(48);
		imageView.setFitWidth(48);
		this.setStyle("-fx-border-color: red;");
		getChildren().addAll(imageView);
	}

	public void moveX(int value) {
		boolean isMovingRight = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			this.setTranslateX(this.getTranslateX() + (isMovingRight ? 1 : -1));
		}
	}
	
	public void moveY(int value) {
		boolean isMovingDown = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (Platform platform : GameScene.platforms) {
				if (isMovingDown) {
					if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
						// brown
						
						if (this.getTranslateY() + this.getHeight() <= platform.getTranslateY() + 2) {
							if (platform.getType() == 3) {
								break;
							}
							canJump = true;
							if (GameScene.lastHitPlatform == null) {
								GameScene.lastHitPlatform = platform;
							} else if (GameScene.lastHitPlatform == platform) {
								GameScene.samePlatform = true;
							} else {
								GameScene.lastHitPlatform = platform;
								GameScene.samePlatform = false;
							}
							break;
						}
					}
				}
			}
			if (this.getTranslateY() < 250 && this.playerVelocity.getY() < 0) return;
			this.setTranslateY(this.getTranslateY() + (isMovingDown ? 1: -1));			
		}
	}
	
	public boolean isFalls() {
		boolean falls = false;
		if (this.getTranslateY() >= 550) {
			falls = true;
		}
		
		return falls;
	}
	
	public boolean ifHitPlatform() {
		boolean hit = true;
		return hit;
	}
	
	public void jumpPlayer() {
		if (canJump) {
			playerVelocity = playerVelocity.add(0, -30 - playerVelocity.getY());
			canJump = false;
		}
	}
	
	public void setCanJump(boolean b) {
		this.canJump = b;
	}
	
	public String toString() {
		return "Character: Position: x = " + this.getTranslateX() + " y = " + this.getTranslateY();
	}
	
	public boolean isMovingDown() {
		return playerVelocity.getY() >= 0;
	}
	
}
