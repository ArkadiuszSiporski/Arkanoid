package main;


import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.Renderer;
import javax.swing.SwingUtilities;

import abstractLevels.CoreLevel;
import multiPlayer.Multiplayer;
import panels.ArcanoidMenu;
import panels.DisplayHighscores;
import panels.Menu;
/**
 * Class serving as the window, that contains all used panels.
 * @author Arkadiusz Siporski
 * @version 1.0
 *
 */
public class Main extends JFrame implements Renderer
{


	private Menu menu;
	private ArcanoidMenu arcanoidMenu;
	private Multiplayer multiplayer;
	private CoreLevel game;
	
	
	
	private DisplayHighscores highscores;
	/**
	 * This constructor sets up the main window.
	 */
	
	public Main()
	{
		setTitle("Menu");
		//					1000 800
		//					300 275
		setBounds(500, 200, 300, 325);
		setFocusTraversalKeysEnabled(false);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		highscores = new DisplayHighscores();
		//MENU
		menu = new Menu();
		add(menu);
		//ARCANOID MENU
		arcanoidMenu = new ArcanoidMenu();
	}
	public static void main(String[] args)
	{
		Main window = new Main();
		SwingUtilities.updateComponentTreeUI(window);
	}

	/**
	 * Changes panel to menu.
	 */

	public void toMenu()
	{
		setTitle("Menu");
		setBounds(500, 200, 300, 275);
		add(menu);
	}
	/**
	 * Changes panel to arcanoidMenu.
	 */
	public void toArcanoidMenu()
	{
		if(game != null)
		{
			game.over();
			remove(game);
		}
		setTitle("Arcanoid Menu");
		setBounds(140, 0, 800, 280);
		add(arcanoidMenu);
	}
	/**
	 * Changes panel to multiplayer.
	 */

	public void toMultiplayer()
	{
		remove(menu);
		setBounds(140, 0, 1000, 800);
		multiplayer = new Multiplayer();	
		add(multiplayer);
	}
	/**
	 * Changes panel to highscores.
	 */
	public void toHighscores()
	{
		remove(menu);
		setBounds(400, 0, 400, 800);
		setTitle("Highscores");
		add(highscores);
	}
	public Menu getMenu()
	{
		return menu;
	}
	/**
	 * Sets next level in single player mode.
	 * @param game which level is to be played
	 */

	public void setGame(CoreLevel game)
	{
		if(this.game != null)
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
	public void toGame()
	{
		remove(arcanoidMenu);
		setBounds(140, 0, 1000, 800);
		setTitle("Arcanoid");
		add(game);
		
	}
	public ArcanoidMenu getArcanoidMenu()
	{
		return arcanoidMenu;
	}
	public DisplayHighscores getHighscores()
	{
		return highscores;
	}
	public CoreLevel getGame()
	{
		return game;
	}
	public Multiplayer getMultiplayer() {
		return multiplayer;
	}
	@Override
	public void setValue(Object aValue, boolean isSelected) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Component getComponent() {
		// TODO Auto-generated method stub
		return null;
	}
}