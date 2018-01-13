package db.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.apache.commons.dbcp2.BasicDataSource;

import db.dao.mapper.PlayerMapper;
import db.model.Player;
import panels.ArcanoidMenu;

public class PlayerDaoImpl implements PlayerDao {
	private BasicDataSource dataSource;
	private NamedParameterJdbcTemplate namedJdbc;
	private JdbcTemplate jdbcTemplate;
	private String insertQuery;
	private String selectQuery;
	private RowMapper<Player> mapper = new PlayerMapper();

	public PlayerDaoImpl() {
		InputStream input = null;
		try {
			input = new FileInputStream(new File(PlayerDaoImpl.class.getResource("/resources/properties/db.properties").toURI()));

			// load a properties file
			Properties prop = new Properties();
			prop.load(input);
			dataSource  = new BasicDataSource();
			dataSource.setDriverClassName(prop.getProperty("driverClassName"));
			dataSource.setUrl(prop.getProperty("url"));
			dataSource.setUsername(prop.getProperty("username"));
			dataSource.setPassword(prop.getProperty("password"));
			insertQuery = prop.getProperty("insertQuery");
			selectQuery = prop.getProperty("selectQuery");
			namedJdbc = new NamedParameterJdbcTemplate(dataSource);
			jdbcTemplate = new JdbcTemplate(dataSource);


		} catch (IOException | URISyntaxException ex) {
			System.err.println("Couldn't find properties file!");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.err.println("Couldn't close properties file!");
				}
			}
		}
		
	}

	@Override
	public void insert(Player player) {
		BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(player);
		namedJdbc.update(insertQuery, namedParameters);

	}

	@Override
	public List<Player> getTop() {
		return jdbcTemplate.query(selectQuery, mapper);
	}

}
