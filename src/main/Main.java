package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import abstractLevels.CoreLevel;
import abstractLevels.Level;
import abstractLevels.NullLevel;
import multiPlayer.Multiplayer;
import panels.ArcanoidMenu;
import panels.DisplayHighscores;
import panels.Menu;
import utils.Clock;
import utils.Internationalizer;
import utils.LevelFactory;

/**
 * Class serving as the window, that contains all used panels.
 * 
 * @author Arkadiusz Siporski
 * @version 1.0
 *
 */
public class Main extends JFrame {
	private static final Logger LOG = Logger.getLogger(Main.class);

	private Menu menu;
	private ArcanoidMenu arcanoidMenu;
	private Multiplayer multiplayer;
	private Level game;

	private DisplayHighscores highscores;
	private Internationalizer internationalizer = Internationalizer.getInstance();

	/**
	 * This constructor sets up the main window.
	 */

	public Main() {
		LOG.debug("Main constructor beginning ");
		setTitle(internationalizer.getString("menu"));
		LevelFactory.setMain(this);
		// 1000 800
		// 300 275
		setBounds(500, 200, 300, 325);
		setFocusTraversalKeysEnabled(false);
		setResizable(false);
		init();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LOG.debug("Main constructor ending ");
		new Clock().start();

	}

	public void init() {
		LOG.debug("Main init beginning ");
		// HIGHSCORES
		highscores = new DisplayHighscores();
		game = new NullLevel();
		// MENU
		if (menu != null) {
			remove(menu);
		}
		menu = new Menu();
		add(menu);
		// ARCANOID MENU
		arcanoidMenu = new ArcanoidMenu();
		try {
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.debug("Main init ending ");
	}

	public static void main(String[] args) {
		Main window = new Main();
		LOG.debug("Window initialized");
	}

	/**
	 * Changes panel to menu.
	 */

	public void toMenu() {
		LOG.debug("Switching to Menu panel");
		setTitle(internationalizer.getString("menu"));
		setBounds(500, 200, 300, 325);
		add(menu);
	}

	/**
	 * Changes panel to arcanoidMenu.
	 */
	public void toArcanoidMenu() {
		LOG.debug("Switching to ArcanoidMenu panel");
		if (game != null) {
			game.over();
			remove((JPanel)game);
		}
		setTitle(internationalizer.getString("arcanoidMenu"));
		setBounds(140, 0, 800, 280);
		add(arcanoidMenu);
		SwingUtilities.updateComponentTreeUI(this);
	}

	/**
	 * Changes panel to multiplayer.
	 */

	public void toMultiplayer() {
		LOG.debug("Switching to Multiplayer panel");
		remove(menu);
		setBounds(140, 0, 1000, 800);
		setTitle(internationalizer.getString("multiPlayer"));
		multiplayer = new Multiplayer();
		add(multiplayer);
	}

	/**
	 * Changes panel to highscores.
	 */
	public void toHighscores() {
		LOG.debug("Switching to Highscores panel");
		highscores.init();
		remove(menu);
		setBounds(400, 0, 400, 800);
		setTitle(internationalizer.getString("highscores"));
		add(highscores);
	}

	public Menu getMenu() {
		return menu;
	}

	/**
	 * Sets next level in single player mode.
	 * 
	 * @param game
	 *            which level is to be played
	 */

	public void setGame(Level game) {
		this.game.over();
		remove((JPanel)this.game);
		this.game = game;
		add((JPanel)this.game);
		SwingUtilities.updateComponentTreeUI(this);
	}

	/**
	 * Changes panel to game.
	 */
	public void toGame() {
		remove(arcanoidMenu);
		setBounds(140, 0, 1000, 800);
		setTitle(internationalizer.getString("arcanoid"));
		add((JPanel)game);

	}

	public ArcanoidMenu getArcanoidMenu() {
		return arcanoidMenu;
	}

	public DisplayHighscores getHighscores() {
		return highscores;
	}

	public Level getGame() {
		return game;
	}

	public Multiplayer getMultiplayer() {
		return multiplayer;
	}
}