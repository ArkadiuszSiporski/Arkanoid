package utils;

import javax.swing.JLabel;

import abstractLevels.Level;
import abstractLevels.NullLevel;
import main.Main;
import singlePlayerLevels.LevelOne;
import singlePlayerLevels.LevelThree;
import singlePlayerLevels.LevelTwo;

public class LevelFactory {
	private static Main main;
	private static JLabel secondLevel;
	private static JLabel thirdLevel;
	public static Level getLevel(int level){
		Level gameLevel;
		switch(level){
		case 1:
			gameLevel = new LevelOne(main, secondLevel, thirdLevel);
			break;
		case 2:
			gameLevel = new LevelTwo(main, thirdLevel);
			break;
			
		case 3:
			gameLevel = new LevelThree(main);
			break;
			default:
				gameLevel = new NullLevel();
		}
		return gameLevel;
	}
	public static void setThirdLevel(JLabel thirdLevel) {
		LevelFactory.thirdLevel = thirdLevel;
	}
	public static void setSecondLevel(JLabel secondLevel) {
		LevelFactory.secondLevel = secondLevel;
	}
	public static void setMain(Main main) {
		LevelFactory.main = main;
	}
}
