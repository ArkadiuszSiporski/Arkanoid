package multiPlayer;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import balls.MultiBall;
import main.Main;
import paddles.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
/**
 * Multiplayer mode.
 *
 */
public class Multiplayer extends JPanel implements ActionListener
{
	// constants used by Key Bindings
	private static final int START = 0;
	private static final int BACK = 1;
	private static final int EXIT = 2;
	//game controls
	public boolean play = false;
	public boolean over = false;
	//background
	private Image background = new ImageIcon(Multiplayer.class.getResource("/resources/Multiplayerbg.png")).getImage();;
	//top Player
	private TopPlayer topPlayer;
	private int topVictories = 0;
	//bottom Player
	private BottomPlayer bottomPlayer;
	private int bottomVictories = 0;

	//ball
	private MultiBall ball;
	
	private Timer timer;
	private int delay = 7;
	
	/**
	 * This constructor initializes the timer, both players, ball and makes the cursor transparent as well as sets Key Bindings.
	 */
	public Multiplayer()
	{
		timer = new Timer(delay, this);
		timer.start();
		
		topPlayer = new TopPlayer(30);
		bottomPlayer = new BottomPlayer(700);
		add(topPlayer);
		add(bottomPlayer);
		
		ball = new MultiBall();
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"),"space");
		getActionMap().put("space",new Controls(START));
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB" ), "back");
		getActionMap().put("back", this.new Controls(BACK));
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
		getActionMap().put("exit", this.new Controls(EXIT));
		
		// Transparent 16 x 16 pixel cursor image.
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				// Create a new blank cursor.
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				    cursorImg, new Point(0, 0), "blank cursor");
				// Set the blank cursor to the panel.
				setCursor(blankCursor);
	
	}
	/**
	 * Inner class used for Key Bindings.
	 * 
	 *
	 */
	private class Controls extends AbstractAction
	{
		private int action;
		Controls(int action)
		{
			this.action = action;
		}
		public void actionPerformed(ActionEvent e)
		{
			Main main = ((Main)SwingUtilities.getRoot(Multiplayer.this));
			if(action == START)
			{
				if(play == false && over == true )
				{
					topPlayer.reset();
					bottomPlayer.reset();
					ball.reset();
					over = false;
				}
				else if(play == false && over == false )
				{	
					ball.start();
					play = true;
				}
			}
			//close the window
			else if(action == EXIT)
			{
				main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
			}
			else//back to menu
			{
				main.toMenu();
				main.remove(main.getMultiplayer());
			}
		}
	}
	/**
	 * This method paints background, instructions, lines, players and ball
	 * @param g the Graphics context in which to paint
	 */
	public void paintComponent(Graphics g)
	{
		
		//background
		g.drawImage(background, 0, 0, null);
		//instructions
		if(!play)
		{
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			//how to move
			g.drawString("move using A/D keys", 770, 30);
			g.drawString("move using arrow keys", 750, 700);
			//how to go back
			g.drawString("TAB = back to menu", 30, 30);
			//how to start
			g.drawString("Press SPACEBAR to play", 30, 60);
		}
		//frame
		g.setColor(Color.YELLOW);
		g.drawLine(0, 0, 0, 800);
		g.drawLine(993, 0, 993, 800);
		//middle
		g.drawLine(0, 400, 993, 400);
		g.fillOval(493, 393, 13, 13);
		//top Player
		topPlayer.draw(g);
		//bottom Player
		bottomPlayer.draw(g);
		//ball
		ball.draw(g);
		//result
		if(ball.getBallY() <= 0)
		{
			bottomWon(g);
			
		}
		else if (ball.getBallY() >= 740)
		{
			topWon(g);
		}	
	}
	/**
	 * This method displays message when the bottom player won
	 * @param g the Graphics context in which to paint
	 */
	private void bottomWon(Graphics g)
	{
		
		g.setFont(new Font("Serif", Font.BOLD, 100));
		g.setColor(Color.RED);
		g.drawString("LOST " + topVictories, 350, 200);
		g.setColor(Color.GREEN);
		g.drawString("WON " + bottomVictories, 350, 600);
	}
	/**
	 * This method displays message when the top player won
	 * @param g the Graphics context in which to paint
	 */
	private void topWon(Graphics g)
	{
		
		g.setFont(new Font("Serif", Font.BOLD, 100));
		g.setColor(Color.RED);
		g.drawString("LOST " + bottomVictories, 350, 600);
		g.setColor(Color.GREEN);
		g.drawString("WON " + topVictories, 350, 200);
	}
	/**
	 * This method registers impacts e.g ball hitting frame or the paddles and checks if the ball is still in the playground.
	 */
	public void actionPerformed(ActionEvent e)
	{
			topPlayer.movePaddle(ball);
			bottomPlayer.movePaddle(ball);
		if(play)
		{
			//ball hitting  top paddles
			ball.hittingPaddle(topPlayer.getPaddleX(), topPlayer.getPaddleY(), topPlayer.getPaddleDir(), topPlayer.getPaddle() );
			//ball hitting  bottom paddles
			ball.hittingPaddle(bottomPlayer.getPaddleX(), bottomPlayer.getPaddleY(), bottomPlayer.getPaddleDir(), bottomPlayer.getPaddle());
			//ball hitting the frame
			ball.hittingFrame();
			//ball movement
			ball.movement();
			if(ball.outOfBounds())
			{
				over();
			}
		}
		
		repaint();	
	}
	/**
	 * Updates the score.
	 */
	public void over()
	{
		play = false;
		over = true;
		if(ball.getBallY() <= 0)
		{
			bottomVictories++;
			
		}
		else if (ball.getBallY() >= 740)
		{
			topVictories++;
		}
	}	
}
