package application;

import i.Collapsible;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import scene.GameScene;

public class Character extends Pane implements Collapsible {
	
	Image doodleImg = new Image(ClassLoader.getSystemResourceAsStream("images/doodleR.png"));
	ImageView imageView = new ImageView(doodleImg);
	
	// Player velocity vector
	public Point2D playerVelocity = new Point2D(0, 0);
	// Player Facing Right: 1, Left: -1 
	
	Input input;
	
	public int hFacing = 1;
	private boolean canJump = true;
	
	public Character() {
		imageView.setFitHeight(48);
		imageView.setFitWidth(48);
		this.setPrefSize(32, 48);
		
		input = new Input(GameScene.scene);
		input.addListeners();
		
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
				if (GameScene.isGameOver) break;
				if (isMovingDown) {
					if (this.isCollapse(platform)) {
						// brown
						if (this.getTranslateY() + this.getHeight() <= platform.getTranslateY() + 2) {
							if (platform.getType() == 3) {
								platform.setDestroy(true);
								break;
							}
							canJump = true;
							if (platform.getType() == 9 && platform.destroy != true) {
								platform.setDestroy(true);
							}
							break;
						}
					}
				}
			}
			for (Monster monster : GameScene.monsters) {
				// Hit monster, then Game Over!!!
				if (this.isCollapse(monster)) {
					GameScene.isGameOver = true;
				}
			}
			if (this.getTranslateY() < 250 && this.playerVelocity.getY() < 0) return;
			this.setTranslateY(this.getTranslateY() + (isMovingDown ? 1: -1));		
		}
	}
	
	public boolean isFalls() {
		boolean falls = false;
		if (this.getTranslateY() > 600) {
			falls = true;
		}
		return falls;
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

	@Override
	public Shape hb() {
		Shape hb = new Rectangle(this.getPrefWidth(), this.getPrefHeight());
		return hb;
	}

	@Override
	public boolean isCollapse(Collapsible other) {
		return this.localToScene(hb().getBoundsInParent()).intersects(((Pane) other).localToScene(other.hb().getBoundsInParent()));
	}
	
}
