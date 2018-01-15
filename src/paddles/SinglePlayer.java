package paddles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


import javax.swing.ImageIcon;


import balls.SingleBall;
import singlePlayerMisc.PaddleTimer;

/**
 * Paddle used in single player mode.
 */
public class SinglePlayer
{
	//
	private static final int LEFT = -1;
	private static final int RIGHT = 1;
	private Image[] paddles = {
			new ImageIcon(SinglePlayer.class.getResource("/resources/Paddle45.png")).getImage(),
			new ImageIcon(SinglePlayer.class.getResource("/resources/Paddle75.png")).getImage(),
			new ImageIcon(SinglePlayer.class.getResource("/resources/Paddle125.png")).getImage()

							 };
	private Image paddle;
	private int playerX = 500;
	private int playerY = 700;
	private int playerDir;
	private PaddleTimer dirTimer;
	private PaddleTimer sizeTimer;
	
	public SinglePlayer()
	{
		paddle = paddles[1];
	}
	public void draw(Graphics g)
	{
		g.drawImage(paddle, playerX, playerY, null);
	}
	public void setPaddle(int index)
	{
		paddle = paddles[index];
	}
	public void resetPlayerDir()
	{
		playerDir = 0;
	}
	private void startDirTimer()
	{
		if(dirTimer != null && dirTimer.isAlive())
		{
			dirTimer.interrupt();
		}
		dirTimer =new PaddleTimer(this, 0.05, 2); 
		dirTimer.start();
	}
	public void startSizeTimer()
	{
		if(sizeTimer != null && sizeTimer.isAlive())
		{
			sizeTimer.interrupt();
		}
		sizeTimer = new PaddleTimer(this, 15, 0); 
		sizeTimer.start();
	}
	//stops timers when we user leave the window with safeLeave option enabled
	public void pauseTimers()
	{
		if(sizeTimer !=null && sizeTimer.isAlive())
			sizeTimer.pause();
		if(dirTimer != null && dirTimer.isAlive())
			dirTimer.pause();
	}
	//restars timers when we user comes back ot the window with safeLeave option enabled
	public void resumeTimers()
	{
		if(sizeTimer !=null && sizeTimer.isAlive())
			sizeTimer.proceed();
		if(dirTimer != null && dirTimer.isAlive())
			dirTimer.proceed();
	}
	public void movePaddle(int mouse,boolean play, SingleBall ball)
	{
	//mouse X != paddle's center && 2x paddle doesn't go out of frame 
		while(mouse != playerX + (paddle.getWidth(null)/2) && mouse > paddle.getWidth(null)/2 && mouse < (993 - paddle.getWidth(null)/2))
		{
			//mouse is on the left from paddle's center
			if(mouse < playerX + (paddle.getWidth(null)/2))
			{
				playerDir = LEFT;
			}
			//mouse is on the right from paddle's center
			else
			{
				playerDir =RIGHT;
			}
			startDirTimer();
			//movement
			playerX += playerDir;
			//before the game starts the ball is glued to the paddle
			if(play == false)
				ball.setBallX(playerX + (paddle.getWidth(null)/2 - 7));
	    }
	}
	public void reset()
	{
		if(dirTimer != null && dirTimer.isAlive())
		{
			dirTimer.interrupt();
		}
		if(sizeTimer != null && sizeTimer.isAlive())
		{
			sizeTimer.interrupt();
		}
		paddle = paddles[1];
	}
	public Rectangle getPlayer()
	{
		return new Rectangle(playerX, playerY, paddle.getWidth(null), paddle.getHeight(null) );
	}
	public int getPlayerDir()
	{
		return playerDir;
	}
	public int getPlayerX()
	{
		return playerX;
	}
	public int getPlayerY()
	{
		return playerY;
	}
	public int getImageWidth()
	{
		return paddle.getWidth(null);
	}
	
}
