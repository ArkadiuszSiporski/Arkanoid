package panels;

import java.util.List;

import db.dao.PlayerDao;
import db.dao.PlayerDaoImpl;
import db.model.Player;

public class DisplayHighscoresProxy extends DisplayHighscores {
	private PlayerDao playerDao = new PlayerDaoImpl();

	public void init() {
		new Thread() {
			public void run() {
				List<Player> players = playerDao.getTop();
				DisplayHighscoresProxy.super.setPlayers(players);
				DisplayHighscoresProxy.super.init();
			}
		}.start();
	}
}
