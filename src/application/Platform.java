package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Platform extends Pane {
	
	public boolean destroyOnce = false;
	Image platformImage = new Image(ClassLoader.getSystemResourceAsStream("images/p-green.png"));
	ImageView platformView = new ImageView(platformImage);
	
	public void brownPlatfrom() {
		platformView.setImage(null);
		platformView = new ImageView(platformImage);
		int offsetX = 0;
		int offsetY = 14;
		platformView.setViewport(new Rectangle2D(offsetX, offsetY, 68, 20));
		platformView.setFitHeight(20);
		platformView.setFitWidth(68);
		getChildren().addAll(this.platformView);
	}

	public void brokenBrownPlatform() {
		platformView.setImage(null);
		platformView = new ImageView(platformImage);
		int offsetX = 0;
		int offsetY = 34;
		platformView.setViewport(new Rectangle2D(offsetX, offsetY, 68, 20));
		getChildren().addAll(this.platformView);
	}
	
	public Platform(String id, int x, int y) {
		platformView = new ImageView(platformImage);
		platformView.setFitWidth(68);
		platformView.setFitHeight(14);
		setTranslateX(x);
		setTranslateY(y);
		
		this.setId(id);
		
		if (this.getId().equals("1")) {
			platformView.setViewport(new Rectangle2D(0, 0, 68, 14));
		}
		if (this.getId().equals("2")) {
			platformView.setViewport(new Rectangle2D(0, 14, 68, 14));
		}
		
		getChildren().add(platformView);
		Game.platforms.add(this);
		Game.gameRoot.getChildren().add(this);
	}
	
	public boolean isDestroyOnce() {
		return destroyOnce;
	}
	
	public void setDestroyOnce(boolean b) {
		this.destroyOnce = b;
	}
	
}
