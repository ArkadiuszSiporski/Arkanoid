package panels;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import main.Main;
/**
 * Class that implements panel in which we can can see the highest scores read from text file.
 */
public class DisplayHighscores extends JPanel
{
	private static final Color GOLD = new Color(255,215,0 );
	private static final Color SILVER = new Color(192,192,192 );
	private static final Color BRONZE = new Color(160,82,45 );
	private static final boolean BACK = true;
	private static final boolean EXIT = false;
	private File path = new File("src/resources/test.txt");
	/**
	 * Constructor that sets up Key Bindings.
	 */
	public DisplayHighscores()
	{
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB"), "back");
		getActionMap().put("back", this.new Controls(BACK));
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
		getActionMap().put("exit", this.new Controls(EXIT));

		
		
	}
	/**
	 * Paints highest scores on the panel.
	 * @param g the Graphics context in which to paint
	 */
	public void paintComponent(Graphics g)
	{
		 Scanner sc;
		String output = "There seem to be no records yet";
		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 850, 900);	
		try
		{
			int i = 1;
			sc = new Scanner(path);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			//reading the file
			while(sc.hasNext())
			{
				switch(i)
				{
					case 1:
						g.setColor(GOLD);
					break;
					case 2:
						g.setColor(SILVER);
					break;
					case 3:
						g.setColor(BRONZE);
					break;
					default:
						g.setColor(Color.WHITE);
					break;
				}
				//drawing each record separately
				output = sc.nextInt() + " " + sc.next();
				g.drawString(i +". " + output,  0, i*70);
				i++;
			}
			
			if(i == 1)
			{
				(SwingUtilities.getRoot(DisplayHighscores.this)).setBounds(140, 0, 850, 150);
				g.setFont(new Font("Serif", Font.BOLD, 60));
				g.setColor(Color.RED);
				g.drawString(output, 0, i*70);
			}
			else
			{
				(SwingUtilities.getRoot(DisplayHighscores.this)).setBounds(400, 0, 400, i *80 );
			}
			sc.close();
		}
		catch(Exception e)
		{
			System.out.printf("There is no file at %s", path);
		}
	}
	/**
	 * Inner class responsible for handling Key Bindings.
	 *
	 */
	private class Controls extends AbstractAction
	{
		boolean action;
		Controls(boolean action)
		{
			this.action = action;
		}
		public void actionPerformed(ActionEvent e)
		{
			Main main = ((Main)SwingUtilities.getRoot(DisplayHighscores.this));
			//back to menu
			if(action)
			{
				main.toMenu();
				main.remove(main.getHighscores());
			}
			//close the window
			else
			{
				main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
			}
		}
	}
}
