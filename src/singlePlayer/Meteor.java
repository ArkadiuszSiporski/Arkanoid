package singlePlayer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import java.util.Random;

/**
 * Class implementing a mateor that the player has to evade in second and third level.
 *
 */
public class Meteor
{
	private static final boolean LEFT = true;
	private static final boolean RIGHT = false;
	private Random generator = new Random();
	private Image meteorLeft = new ImageIcon(Meteor.class.getResource("/resources/meteorLeft.png")).getImage();
	private Image meteorRight = new ImageIcon(Meteor.class.getResource("/resources/meteorRight.png")).getImage();
	private int meteorTimer = 0;
	private int meteorX;
	private int meteorY = 800;
	private boolean origin;
	/**
	 * Draws and moves the meteor.
	 * @param g the Graphics context in which to paint
	 */
	public void draw(Graphics g)
	{
		if(meteorY < 750)
		{
			if(origin)
			{
				g.drawImage(meteorLeft, meteorX, meteorY, null);
				meteorX += 2;
			}
			else
			{
				g.drawImage(meteorRight, meteorX--, meteorY, null);
				meteorX -= 2;
			}
			meteorY+= 2;
		}
	}
	public void reset()
	{
		meteorTimer = 0;
		meteorY = 800;
	}
	/**
	 * Spawns a new meteor on either side of the screen at a random height.
	 */
	private void spawnMeteor()
	{
		
			meteorY = generator.nextInt(300);
			if(generator.nextBoolean())
			{
				meteorX = 0;
				origin = LEFT;
				
			}
			else
			{
				meteorX = 993;
				origin = RIGHT;
			}
		
		
	}
	/**
	 * Increases the timer by given amount. When the timer exceeds 2500 it calls method spawnMeteor().
	 * @param increase the amount the timer is increased by.
	 */
	public void increaseMeteorTimer(int increase)
	{
		if(meteorY >= 750)
		{
			meteorTimer += increase;
			if(meteorTimer >= 2500)
			{
				spawnMeteor();
				meteorTimer = 0;
			}
		}
	}
	/**
	 * Checks for impact with the paddle. 
	 * @param paddleRect rectangle representing player's paddle
	 * @return returns true if the paddle was hit
	 */
	public boolean hittingPaddle(Rectangle paddleRect)
	{
		if(	paddleRect.intersects(meteorX, meteorY, meteorLeft.getWidth(null), meteorLeft.getHeight(null)))
		{
			return true;
		}
		else{
			return false;
		}
	}
}
