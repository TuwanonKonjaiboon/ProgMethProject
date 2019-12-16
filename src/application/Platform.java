package application;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Platform extends Pane {
	
	public boolean destroyOnce = false;
	Image platform1Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-green.png"));
	Image platform2Image = new Image(ClassLoader.getSystemResourceAsStream("images/p-blue.png"));
	Image platform3Image = new Image(ClassLoader.getSystemResourceAsStream("images/brown/p-brown-1.png"));
	
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
	
	public boolean isDestroyOnce() {
		return destroyOnce;
	}
	
	public void setDestroyOnce(boolean b) {
		this.destroyOnce = b;
	}
	
	public int getType() {
		return type;
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
			platformView.setImage(platform3Image);
			platformView.setFitWidth(68);
			platformView.setFitHeight(20);
			platformView.setViewport(new Rectangle2D(0, 0, 68, 20));
		} else {
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
			if (this.getTranslateX() + this.getWidth() > Settings.SCENE_WIDTH - 10) {
				platformVelocity = -5;
			} else if (this.getTranslateX() < 10) {
				platformVelocity = 5;
			}
			moveX(platformVelocity);
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
