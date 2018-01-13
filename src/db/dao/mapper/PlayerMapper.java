package db.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import db.model.Player;

public class PlayerMapper implements RowMapper<Player>{

	@Override
	public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
		Player player = new Player();
		player.setNick(rs.getString("NICK"));
		player.setScore(rs.getLong("SCORE"));
		return player;
	}


}
