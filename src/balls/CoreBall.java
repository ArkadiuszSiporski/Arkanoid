package balls;

import java.awt.Graphics;
import java.awt.Image;


import javax.swing.ImageIcon;

/**
 * Core ball class from which MultiBall and SingleBall inherit.
 *
 */
public abstract  class CoreBall
{
	
	protected final Image ball = new ImageIcon(CoreBall.class.getResource("/resources/ball4.png")).getImage();
	protected int ballX;
	protected int ballY;
	protected int ballDirX;
	protected int ballDirY;
	
	/**
	 * Draws the ball
	 * @param g the Graphics context in which to paint
	 */
	public void draw(Graphics g)
	{
		g.drawImage(ball, ballX, ballY, null);
	}
	/**
	 * This method moves the ball
	 */
	public void movement()
	{
		ballX += ballDirX;
		ballY += ballDirY;
	}

	public int getBallY() {
		return ballY;
	}
	public int getBallX() {
		return ballX;
	}
	public void setBallX(int coordinates)
	{
		ballX = coordinates;
	}
	public void setBallDirX(int dirX)
	{
		ballDirX = dirX;
	}
	public int getBallDirX()
	{
		return ballDirX;
	}
	public void setBallDirY(int dirY)
	{
		ballDirY = dirY;
	}
	public int getBallDirY()
	{
		return ballDirY;
	}
	
	

}
