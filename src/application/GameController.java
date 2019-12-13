package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

public class GameController {
	
	Scene scene;
	
	// DOODLE BOUNCE DOWN SPEED (SMALLER (negative) number for higher jump!)
	private double SDV = -9;
	// PLATFORM SCROLL DOWN SPEED (higher number to fall faster)
	private double BSDS = 5;
	// DOODLE SCROLL DOWN SPEED (higher number to fall faster)
	private double DSDS = 1;
	private int level = 0;
	
	private int score = 0;
	
	private boolean gamestart = false;

	public AnimationTimer gameLoop;
	
	public GameController(Scene scene) {
		setScene(scene);
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void init() {
		scene.setOnKeyPressed(event -> {
			
		});
	}
	
	public void start() {
		
		gameLoop = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				System.out.println(now);
			}
		};
		gameLoop.start();
	}
	
}
