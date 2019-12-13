package application;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Character extends Pane {
	
	Image doodleImg = new Image(ClassLoader.getSystemResourceAsStream("images/doodleR.png"));
	ImageView imageView = new ImageView(doodleImg);
	public Point2D playerVelocity = new Point2D(0, 0);
	private boolean canJump = true;
	
	public Character(int characterType) {
		imageView.resize(76, 76);
		
		getChildren().add(imageView);
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
			for (Platform platform: Game.platforms) {
				if (getBoundsInParent().intersects(platform.getBoundsInParent())) {
					if (isMovingDown) {
						if (this.getTranslateY() + this.getHeight() >= platform.getTranslateY() &&
							this.getTranslateY() + this.getHeight() <= platform.getTranslateY() + 5
								&& platform.getId().equals("1")) {
							// Add Sound
							this.setTranslateY(this.getTranslateY() - 1);
							canJump = true;
							return;
						}
//						if (this.getTranslateY() + this.getHeight() == platform.getTranslateY() && platform.getId().equals("2")) {
//							// Add Sound
//						}
					}
				}
			}			
		}
		this.setTranslateY(this.getTranslateY() + (isMovingDown ? 5 : -5));
		ifFalls();
	}
	
	public boolean ifFalls() {
		boolean falls = false;
		if (this.getTranslateY() > 700) {
			falls = true;
		}
		
		return falls;
	}
	
	public void jumpPlayer() {
		if (canJump) {
			playerVelocity = playerVelocity.add(0, -60);
			canJump = false;
		}
	}
	
	public void setCanJump(boolean b) {
		this.canJump = b;
	}
	
	public String toString() {
		return "Character: Position: x = " + this.getTranslateX() + " y = " + this.getTranslateY();
	}
	
}
