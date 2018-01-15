package abstractLevels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import balls.SingleBall;
import main.Main;
import paddles.SinglePlayer;
import singlePlayerMisc.SaveScores;
import utils.Clock;
import utils.Internationalizer;

/**
 * Core class from which all levels inherit.
 *
 */
public abstract class CoreLevel extends JPanel implements ActionListener, Level
{
	protected static final Logger LOG = Logger.getLogger(CoreLevel.class);
	//constants used by field over
	protected static final int PLAY = 0;
	protected static final int LOST = 1;
	protected static final int WON = 2;

	
	//controls
	protected boolean play = false;
	protected double score = 1000;
	protected int gameState = PLAY; 
	//level
	protected int level;
	//background
	protected Image background;
	//paddle
	protected SinglePlayer player;
	//ball
	protected SingleBall ball;
	//game timer
	protected Timer timer;
	protected int  delay = 6;
	//main
	protected Main main;
	//SAFE LEAVE
	protected boolean safeLeave = true;
	//
	protected Internationalizer internationalizer = Internationalizer.getInstance();

	/**
	 * This constructor makes the cursor transparent, adds a MouseMotionListener and starts the timer which among others causes the screen to refresh.
	 * @param main window in which the panel is viewed
	 */
	public CoreLevel(Main main)
	{

		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the panel.
		setCursor(blankCursor);
		
		this.main = main;
		player = new SinglePlayer();
		//paddle control
		addMouseMotionListener(new MouseAdapter()
		{
			public void mouseMoved(MouseEvent e)
			{
				if(gameState == PLAY){
					player.movePaddle(e.getX(), play, ball);
				}
			}
			public void mouseDragged(MouseEvent e)
			{
				if(gameState == PLAY){
				player.movePaddle(e.getX(), play, ball);
				}
			}

		});

	
		timer = new Timer(delay, this);
		timer.start();
	}
	/**
	 * Paints some of the features such as frame, score and instructions
	 * @param g the Graphics context in which to paint
	 */
	public void paintComponent(Graphics g)
	{
		//FRAME
		g.setColor(Color.YELLOW);
		g.drawLine(0, 0, 993, 0);
		g.drawLine(0, 0, 0, 800);
		g.drawLine(993, 0, 993, 800);
		//score
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.drawString("" + (int)score, 900, 50);

		if(play == false)
		{
			//instructions
			//how to go back
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString(internationalizer.getString("tab"), 30, 30);
			//how to start
			g.drawString(internationalizer.getString("mouseButtonPlay"), 30, 60);
			//toggling safe leave
			g.drawString(internationalizer.getString("safeLeaveInst"), 350, 30);
			//safe leave status
			g.drawString(internationalizer.getString("safeLeaveStat"), 350, 60);
			if(safeLeave)
			{
				g.setColor(Color.GREEN);
				g.drawString(internationalizer.getString("on"), 520, 60);
			}
				
			else
			{
				g.setColor(Color.RED);
				g.drawString(internationalizer.getString("off"), 520, 60);
			}
				
			
			
		}
		//result
		if(gameState == LOST)
		{
			
			g.setColor(Color.RED);
			g.setFont(new Font("Serif", Font.BOLD, 100));
			g.drawString(internationalizer.getString("youLost"), 200, 500);
			g.setFont(new Font("Serif", Font.BOLD, 45));
			g.drawString(internationalizer.getString("mouseButtonReplay"), 20, 600);
		}
		//ball
		ball.draw(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 50));
		g.drawString(Clock.getTime(), 740, 50);
		g.dispose();
	}
	/**
	 * Ends the game when ball leaves the playground
	 */
	//ball out of the playground
	@Override
	public void over()
	{
			gameState = LOST;
			play = false;
			repaint();
	}


	/**
	 * Sets up a new game
	 */
	public void newGame()
	{
		gameState = PLAY;
		play = false;
		ball.reset();
		score = 1000;
		repaint();
	}
	//switching game's speed to default
	public void resetDelay()
	{
		{
			timer.stop();
			timer= new Timer(delay, this);
			timer.start();
		}
	}
	/**
	 * Changes player's score
	 * @param bonus the amount added to current score
	 */
	public void setScore(double bonus)
	{
		score += bonus;
	}
	/**
	 * Adds best scores to high scores
	 */
	//saving results in a file
	public void victory()
	{
		new Thread (new SaveScores(((Double)score).longValue(), JOptionPane.showInputDialog(main, internationalizer.getString("nick"), internationalizer.getString("saveScore"), JOptionPane.QUESTION_MESSAGE) )).start();
	}


	public Timer getTimer() {
		return timer;
	}

}
