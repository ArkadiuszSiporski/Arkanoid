package singlePlayer;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Vector;

import db.dao.PlayerDao;
import db.dao.PlayerDaoImpl;
import db.model.Player;
import exceptions.EmptyNickException;
import exceptions.NickLengthException;

/**
 * Class responsible for saving player's score after they finish one of the
 * single player levels.
 *
 */
public class SaveScores implements Runnable {
	private Long newScore;
	private String nick;
	private PlayerDao playerDao = new PlayerDaoImpl();

	/**
	 * Initializes score, nick to be saved in file and the path to the file.
	 * 
	 * @param newScore
	 *            player's score
	 * @param nick
	 *            player's nick
	 */
	public SaveScores(Long newScore, String nick) {
		this.newScore = newScore;
		try {
			testNick(nick);
			this.nick = nick;
		} catch (EmptyNickException e) {
			this.nick = "Anonymous";
		} catch (NickLengthException e) {
			this.nick = nick.substring(0, 10);
		}
		
		

	}

	private void testNick(String nick) throws EmptyNickException, NickLengthException {
		if (nick == null || nick.isEmpty()) {
			throw new EmptyNickException();
		} else if (nick.length() > 10) {
			throw new NickLengthException();
		}
	}

	/**
	 * Writes player's score to data base
	 */
	public void run() {
		save();
	}
	
	private void save(){
		Player player = new Player();
		player.setNick(nick);
		player.setScore(newScore);
		playerDao.insert(player);
	}
}
