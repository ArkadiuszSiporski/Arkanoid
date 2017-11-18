package balls;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;

import abstractLevels.NormalLevel;
import singlePlayer.BrickGenerator;

/**
 * Ball used in single player mode. NormalBall and SpecialBall inherit from it.
 *
 */

public class SingleBall extends CoreBall
{
	public SingleBall()
	{
		ballX = 537;
		ballY = 685;

	}

	//checks if ball left the playground
	@Override
	public boolean outOfBounds() 
	{
		return (ballY > 740);
	}

	@Override
	public void reset() 
	{
		//setting paddle's position in the middle
		try
		{
		    Robot robot = new Robot();
		    robot.mouseMove(620, 500);
		    robot = null;
		} 
		catch (AWTException ex)
		{}
		ballY = 680;
		
		
	}
	@Override
	public void start() 
	{
		ballDirX = 2;
		ballDirY = -3;
	}
	/**
	 *  Checks for impact between ball and paddle
	 * @param paddleRect rectangle representing the paddle
	 * @param playerDir directions in which the paddles is moving
	 * @return returns true if the paddle was hit
	 */
	public boolean hittingPaddle(Rectangle paddleRect, int playerDir)
	{
		Rectangle ballRect = new Rectangle(ballX, ballY, 15, 15);
		if(	ballRect.intersects(paddleRect) || paddleRect.intersects(ballRect)	)
		{
			//keeping ball's X speed between -3 and 3
			if(playerDir != 0 && Math.abs(ballDirX) < 3)
			{
				ballDirX += playerDir;
			}
			//lowers ball's X speed if the paddle is not moving
			else if (playerDir == 0)
			{
				if(ballDirX < 0)
					ballDirX++;
				else if(ballDirX > 0)
					ballDirX--;
			}
			//bounces the ball if it hits top half of the paddle
			if(ballY + ball.getHeight(null)/2 < 710 )
			{
				while(ballRect.intersects(paddleRect))
				{
					ballY -= 1;
					ballRect = new Rectangle(ballX, ballY, ball.getWidth(null), ball.getHeight(null));
				}
					
				ballDirY = -ballDirY;
			}
			
			return true;
		}
		return false;
	}
	/**
	 * Checks if the frame was hit.
	 * @return returns true if  the frame was hit.
	 */
	public boolean hittingFrame()
	{
		//flag determining if either side of the frame was hit
		boolean flag = false;
		//left || right side of frame
		if(ballX >=978 || ballX <= 0)
		{
			ballDirX = -ballDirX;
			flag =  true;
		}
		//top base of the frame
		if(ballY <= 0)
		{
			ballDirY = -ballDirY;
			flag =  true;
		}
		return flag;
		
	}
	/**
	 * Moves the ball and checks after each step if anything was hit.
	 * @param player rectangle representing the paddle
	 * @param playerDir the direction in which the paddle is moving
	 * @param bricks set of bricks
	 * @param game currently played game
	 * @param otherBall additional ball
	 */
	public void moveBall(Rectangle player,int playerDir, BrickGenerator bricks, NormalLevel game, SingleBall otherBall)
	{
		//variables determining how many times ball should move in either direction
		int moveX = Math.abs(ballDirX);
		int moveY = Math.abs(ballDirY);
		//which directions should the ball move
		int dirX;
		int dirY = ballDirY/Math.abs(ballDirY);
		//avoiding division by 0
		if(ballDirX != 0)
			dirX = ballDirX/Math.abs(ballDirX);
		else
			dirX = 0;
		boolean cont = true;
		while((moveX > 0 || moveY > 0) && cont)
		{
			//moving the ball along X axis
			if(moveX > 0)
			{
				ballX += dirX;
				moveX--;
			}
			//moving the ball along Y axis
			if(moveY > 0)
			{
				ballY += dirY;
				moveY--;
			}
			//the ball stops moving when it hits something
			if(hittingPaddle(player, playerDir) || ((NormalBall)this).hittingBricks(bricks, game) || hittingFrame() ||(otherBall != null &&  ((NormalBall)this).hittingBalls(otherBall)))
				cont = false;
		}
	}
}
