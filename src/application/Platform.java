package application;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Platform extends Pane {
	
	public boolean destroyOnce = false;
	Image platformImage = new Image(ClassLoader.getSystemResourceAsStream("images/p-green.png"));
	ImageView platformView = new ImageView(platformImage);
	
	private int type;
	
	public Point2D platformVelocity = new Point2D(0, 0);
	
	public Platform(int type, int x, int y, int width, int height) {
		platformView.setImage(platformImage);
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
	
	public boolean isDestroyOnce() {
		return destroyOnce;
	}
	
	public void setDestroyOnce(boolean b) {
		this.destroyOnce = b;
	}
	
	public int getType() {
		return type;
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
