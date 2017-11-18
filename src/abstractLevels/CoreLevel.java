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

import balls.SingleBall;
import main.Main;
import paddles.SinglePlayer;
import singlePlayer.SaveScores;

/**
 * Core class from which all levels inherit.
 *
 */
public abstract class CoreLevel extends JPanel implements ActionListener
{
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
				player.movePaddle(e.getX(), play, ball);
			}
			public void mouseDragged(MouseEvent e)
			{
				player.movePaddle(e.getX(), play, ball);
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
			g.drawString("TAB = back to menu", 30, 30);
			//how to start
			g.drawString("Press a MOUSE BUTTON to play", 30, 60);
			//toggling safe leave
			g.drawString("Press 'S' to toggle 'safe cursor leave'", 350, 30);
			//safe leave status
			g.drawString("Safe cursor leave is ", 350, 60);
			if(safeLeave)
			{
				g.setColor(Color.GREEN);
				g.drawString("ON", 520, 60);
			}
				
			else
			{
				g.setColor(Color.RED);
				g.drawString("OFF", 520, 60);
			}
				
			
			
		}
		//result
		if(gameState == LOST)
		{
			
			g.setColor(Color.RED);
			g.setFont(new Font("Serif", Font.BOLD, 100));
			g.drawString("YOU LOST", 250, 500);
			g.setFont(new Font("Serif", Font.BOLD, 50));
			g.drawString("(Press a mouse button to play again)", 150, 600);
		}
		//ball
		ball.draw(g);

	
		g.dispose();
	}
	/**
	 * Ends the game when ball leaves the playground
	 */
	//ball out of the playground
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
		new Thread (new SaveScores((int)score, JOptionPane.showInputDialog(main, "What's your nick? (up to 10 characters)", "Save your score", JOptionPane.QUESTION_MESSAGE) )).start();
	}


	public Timer getTimer() {
		return timer;
	}

}
