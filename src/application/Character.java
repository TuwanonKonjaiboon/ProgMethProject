package application;

import java.util.ArrayList;

import asset.GameImage;
import i.Collapsible;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import scene.GameScene;

public class Character extends Pane implements Collapsible {
	
	ArrayList<Image> dioImages = GameImage.dioImages;
	ImageView imageView = new ImageView(dioImages.get(0));
	
	// Player velocity vector
	public Point2D playerVelocity = new Point2D(0, 0);
	
	private boolean canJump = true;
	
	public Character() {
		imageView.setFitWidth(48);
		imageView.setFitHeight(110);
		this.setPrefSize(imageView.getFitWidth(), imageView.getFitHeight());
		
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
						if (this.getTranslateY() + this.getHeight() <= platform.getTranslateY() + 5) {
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
				if (GameScene.isGameOver || GameScene.isPause) break;
				if (this.isCollapse(monster)) {
					if (isMovingDown) {
						if (this.isCollapse(monster)) {
							// brown
							if (this.getTranslateY() + this.getHeight() <= monster.getTranslateY() + 20) {
								canJump = true;
								monster.gothit();
								break;
							}
						}
					}
					GameScene.isGameOver = true;
				}
			}
			if (this.getTranslateY() < 250 && this.playerVelocity.getY() < 0) return;
			this.setTranslateY(this.getTranslateY() + (isMovingDown ? 1: -1));		
		}
	}
	
	public void update() {
		if (GameScene.isGameOver) {
			if (imageView.getImage() != dioImages.get(3)) {
				imageView.setImage(dioImages.get(3));
				imageView.setFitWidth(100);
				imageView.setFitHeight(100);
			}
			return;
		}
		if (GameScene.isPause) {
			if (imageView.getImage() != dioImages.get(2)) {
				imageView.setImage(dioImages.get(2));
				imageView.setFitWidth(150);
				imageView.setFitHeight(150);
				imageView.setTranslateX(this.getPrefWidth() / 2 - imageView.getFitWidth() / 2);
			}
			return;
		}
		if (isMovingDown()) {
			if (imageView.getImage() != dioImages.get(1)) {
				imageView.setImage(dioImages.get(1));
				imageView.setFitWidth(55);
				imageView.setFitHeight(100);
				imageView.setTranslateX(this.getPrefWidth() / 2 - imageView.getFitWidth() / 2);
			}
		} else {
			if (imageView.getImage() == dioImages.get(1)) {
				imageView.setImage(dioImages.get(0));
				imageView.setFitWidth(48);
				imageView.setFitHeight(100);
				imageView.setTranslateX(this.getPrefWidth() / 2 - imageView.getFitWidth() / 2);
			}
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
		Shape hb = new Rectangle(this.getPrefWidth() / 2, this.getPrefHeight());
		hb.setTranslateX(this.getPrefWidth() / 2 - hb.getBoundsInParent().getWidth() / 2);
		return hb;
	}

	@Override
	public boolean isCollapse(Collapsible other) {
		return this.localToScene(hb().getBoundsInParent()).intersects(((Pane) other).localToScene(other.hb().getBoundsInParent()));
	}
	
}
