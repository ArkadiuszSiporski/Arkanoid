package db.dao;

import java.util.List;

import db.model.Player;

public interface PlayerDao {
	void insert(Player player);
	List<Player> getTop();
	
}
