package singlePlayer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 * Class implements mustache that serves as a bonus feature, which when caught by the player increases his score gain and chance of getting a super power.
 *
 */
public class Mustache
{
	//mustache
	private Image mustache = new ImageIcon(Mustache.class.getResource("/resources/mustache.png")).getImage();;
	private int mustacheX;
	private int mustacheY;
	/**
	 * Sets the mustache coordinates.
	 */
	public Mustache()
	{
		mustacheX = 425;
		mustacheY = 0;
	}
	/**
	 * Draws and moves the mustache.
	 * @param g the Graphics context in which to paint
	 */
	public void draw(Graphics g)
	{
		g.drawImage(mustache, mustacheX, mustacheY++, null);
	}

	public Rectangle getMustache()
	{
		return new Rectangle(mustacheX, mustacheY, mustache.getWidth(null), mustache.getHeight(null));
	}
	/**
	 * Checks for impact with the paddle.
	 * @param paddleRect rectangle representing player's paddle.
	 * @return return true if the paddle was hit.
	 */
	public boolean hittingPaddle(Rectangle paddleRect)
	{
		if(paddleRect.intersects(mustacheX, mustacheY, 150, 120))
		{
			mustacheY = 800;
			return true;
		}
		return false;
	}
	public int getMustacheY()
	{
		return mustacheY;
	}

}
