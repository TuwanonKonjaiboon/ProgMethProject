package asset;

import javafx.scene.media.Media;

public class GameSounds {

	public static int progress = 0;
	public static boolean isResourceReady = false;
	
	public static Media mainBGM;
	public static Media timeStop;
	public static Media timeResume;
	public static Media toBeContinue;
	public static Media yes;
	public static Media konoDioDa;
	public static Media yare;
	
	public static void init() {
		mainBGM = new Media(ClassLoader.getSystemResource("sounds/bgm-1.mp3").toString());
		progress++;
		timeStop = new Media(ClassLoader.getSystemResource("sounds/timestop.mp3").toString());
		progress++;
		timeResume = new Media(ClassLoader.getSystemResource("sounds/timeresume.mp3").toString());
		progress++;
		yes = new Media(ClassLoader.getSystemResource("sounds/yes.mp3").toString());
		progress++;
		toBeContinue = new Media(ClassLoader.getSystemResource("sounds/RoundAbout.mp3").toString());
		progress++;
		konoDioDa = new Media(ClassLoader.getSystemResource("sounds/konodio.mp3").toString());
		progress++;
		yare = new Media(ClassLoader.getSystemResource("sounds/yareyaredaze.mp3").toString());
		progress++;
	}
	
	public static int getProgress() {
		return progress;
	}
	
}
