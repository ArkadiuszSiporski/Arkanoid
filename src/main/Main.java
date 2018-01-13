package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import abstractLevels.CoreLevel;
import multiPlayer.Multiplayer;
import panels.ArcanoidMenu;
import panels.DisplayHighscores;
import panels.DisplayHighscoresProxy;
import panels.Menu;
import utils.Internationalizer;

import javax.swing.UIManager.*;

/**
 * Class serving as the window, that contains all used panels.
 * 
 * @author Arkadiusz Siporski
 * @version 1.0
 *
 */
public class Main extends JFrame {

	private Menu menu;
	private ArcanoidMenu arcanoidMenu;
	private Multiplayer multiplayer;
	private CoreLevel game;

	private DisplayHighscores highscores;
	private Internationalizer internationalizer = Internationalizer.getInstance();

	/**
	 * This constructor sets up the main window.
	 */

	public Main() {
		setTitle(internationalizer.getString("menu"));
		// 1000 800
		// 300 275
		setBounds(500, 200, 300, 325);
		setFocusTraversalKeysEnabled(false);
		setResizable(false);
		init();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void init() {
		// HIGHSCORES
		highscores = new DisplayHighscoresProxy();
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
		
	}

	public static void main(String[] args) {
		Main window = new Main();
	}

	/**
	 * Changes panel to menu.
	 */

	public void toMenu() {
		setTitle(internationalizer.getString("menu"));
		setBounds(500, 200, 300, 275);
		add(menu);
	}

	/**
	 * Changes panel to arcanoidMenu.
	 */
	public void toArcanoidMenu() {
		if (game != null) {
			game.over();
			remove(game);
		}
		setTitle(internationalizer.getString("arcanoidMenu"));
		setBounds(140, 0, 800, 280);
		add(arcanoidMenu);
	}

	/**
	 * Changes panel to multiplayer.
	 */

	public void toMultiplayer() {
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

	public void setGame(CoreLevel game) {
		if (this.game != null)

		{
			this.game.over();
			remove(this.game);
		}
		this.game = game;
		add(this.game);
		SwingUtilities.updateComponentTreeUI(this);
	}

	/**
	 * Changes panel to game.
	 */
	public void toGame() {
		remove(arcanoidMenu);
		setBounds(140, 0, 1000, 800);
		setTitle(internationalizer.getString("arcanoid"));
		add(game);

	}

	public ArcanoidMenu getArcanoidMenu() {
		return arcanoidMenu;
	}

	public DisplayHighscores getHighscores() {
		return highscores;
	}

	public CoreLevel getGame() {
		return game;
	}

	public Multiplayer getMultiplayer() {
		return multiplayer;
	}
}