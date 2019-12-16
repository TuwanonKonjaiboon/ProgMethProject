package application;

import java.util.ArrayList;

import asset.GameImage;
import javafx.animation.FadeTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import scene.GameScene;

public class Platform extends Pane {
	
	Image platform1Image = GameImage.platform1Image;
	Image platform2Image = GameImage.platform2Image;
	ArrayList<Image> platform3Images = GameImage.platform3Images;
	Image platform9Image = GameImage.platform9Image;
	
	public boolean destroy = false;
	public int id = 0;
	
	ImageView platformView = new ImageView(platform1Image);
	
	private int platformVelocity = 5;
	
	private int type;
	
	public Platform(int type, int x, int y, int width, int height) {
		platformView.setImage(platform1Image);
		platformView.setFitWidth(width);
		platformView.setFitHeight(height);
		platformView.setViewport(new Rectangle2D(0, 0, width, height));
		
		this.type = type;
		
		this.getChildren().add(platformView);
		
		this.setTranslateX(x);
		this.setTranslateY(y);
	}

	public void moveY(int value) {
		for (int i = 0; i < Math.abs(value); i++) {			
			this.setTranslateY(this.getTranslateY() + 1);
		}
	}
	
	public void moveX(int value) {
		boolean isMovingRight = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			this.setTranslateX(this.getTranslateX() + (isMovingRight ? 1 : -1));
		}
	}
	
	public int getType() {
		return type;
	}
	
	public void setDestroy(boolean b) {
		this.destroy = b;
	}
	
	public boolean inScene() {
		boolean in = true;
		if (this.getTranslateY() >= Settings.SCENE_HEIGHT + 1) {
			in = false;
		}
		return in;
	}
	
	public void changeType(int type) {
		if (type == 1) {
			platformView.setImage(platform1Image);
			platformView.setFitWidth(58);
			platformView.setFitHeight(15);
			platformView.setViewport(new Rectangle2D(0, 0, 58, 15));
		} else if (type == 2) {
			platformView.setImage(platform2Image);
			platformView.setFitWidth(56);
			platformView.setFitHeight(16);
			platformView.setViewport(new Rectangle2D(0, 0, 56, 16));
		} else if (type == 3) {
			platformView.setImage(platform3Images.get(0));
			platformView.setFitWidth(68);
			platformView.setFitHeight(20);
			platformView.setViewport(new Rectangle2D(0, 0, 68, 20));
			destroy = false;
			id = 0;
		} else if (type == 9) {
			platformView.setImage(platform9Image);
			platformView.setFitWidth(58);
			platformView.setFitHeight(15);
			platformView.setViewport(new Rectangle2D(0, 0, 58, 15));
		}
		else {
			platformView.setImage(platform1Image);
			platformView.setFitWidth(58);
			platformView.setFitHeight(15);
			platformView.setViewport(new Rectangle2D(0, 0, 58, 15));
		}
		this.type = type;
	}
	
	public void update() {
		if (type == 1) {
			return;
		} else if (type == 2) {
			// blue
			if (this.getTranslateX() + this.getWidth() > Settings.SCENE_WIDTH - 10) {
				platformVelocity = -5;
			} else if (this.getTranslateX() < 10) {
				platformVelocity = 5;
			}
			moveX(platformVelocity);
		} else if (type == 3) {
			// brown animation
			if (destroy) {
				if (id < 5) {
					platformView.setImage(platform3Images.get(++id));
				}
				else {
					platformView.setImage(null);
				}
				moveY(5);
			}
		} else if (type == 9) {
			if (destroy) {
				FadeTransition transition = new FadeTransition(Duration.millis(200), this);
				transition.setOnFinished(event -> {
					GameScene.reGenerateLiveRandomPlatform(this);
				});
				transition.play();
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Platform)) return false;
		Platform other = (Platform) obj;
		return getTranslateX() == other.getTranslateX() && getTranslateY() == other.getTranslateY();
	}
	
	@Override
	public String toString() {
		return "Platform: Type: " + type + " Position: x = " + getTranslateX() + " y = " + getTranslateY();
	}
	
}
