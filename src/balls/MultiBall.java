package balls;


import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

/**
 * Ball used in multiplayer mode.
 *
 */
public class MultiBall extends CoreBall
{
	private Random generator = new Random();
	
	public MultiBall()
	{
		ballX = 493;
		ballY = 393;
	}
	/**
	 * This method starts the game by setting ball's directions(movement)
	 */
	
	public void start()
	{
		//random X speed and Y direction
		ballDirX = generator.nextInt(8) - 4;
		if(generator.nextBoolean())
			ballDirY = 3;
		else
			ballDirY = -3;
	}
	/**
	 * This method changes ball's movement if it hit the frame
	 */
	public void hittingFrame()
	{	//left || right side of frame
		if(ballX >=978 || ballX <= 0)
		{
			ballDirX = -ballDirX;
		}
	}
	//checks if ball is still within the playground
	public boolean outOfBounds()
	{
		return (ballY  <= 0 || ballY >= 740);
	}
	//puts ball in the middle
	public void reset()
	{
		ballX = 493;
		ballY = 393;
	}
	/**
	 * Checks for impact between ball and paddle
	 * @param paddleX X coordinate of the paddle
	 * @param paddleY Y coordinate of the paddle
	 * @param paddleDir direction in which the paddle is moving
	 * @param paddle image of the paddle
	 */
	public void hittingPaddle(int paddleX, int paddleY,int paddleDir ,Image paddle)
	{
		Rectangle ballRect = new Rectangle(ballX, ballY, 15, 15);
		Rectangle paddleRect = new Rectangle (paddleX, paddleY, paddle.getWidth(null), paddle.getHeight(null));
		//if ball hit the paddle
		if(	ballRect.intersects(paddleRect) || paddleRect.intersects(ballRect)	)
		{
			//keeping ball's X speed between -4 and 4
			if(paddleDir != 0 && Math.abs(ballDirX) <= 3)
			{
				ballDirX += paddleDir;
				
			}
			else if (paddleDir == 0)
			{
				if(ballDirX < 0)
					ballDirX++;
				else if(ballDirX > 0)
					ballDirX--;
			}
			//pushing the ball in the direction the paddle is moving if the ball hits the edge of the  paddle
			if((ballY > paddleY && paddleY == 700) || (ballY < paddleY && paddleY == 30) )
			{
				ballX += paddleDir;
			}	
			//ball bounces back only when it hits top half of the bottom  paddle
			if(ballY + ball.getHeight(null)/2 < 710 && paddleY == 700 )
			{
				ballY -= 1;
				ballDirY = -Math.abs(ballDirY);
			}
			//ball bounces back only when it hits bottom half of the top paddle
			else if (ballY > 40 && paddleY == 30 )
			{
				
				ballY += 1;
				ballDirY = Math.abs(ballDirY);
			}	
		}
	}
}
