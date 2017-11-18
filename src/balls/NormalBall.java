package balls;

import java.awt.Rectangle;

import abstractLevels.NormalLevel;
import singlePlayer.BrickGenerator;

/**
 * Ball used in first and second level.
 *
 */
public class NormalBall extends SingleBall
{
	public NormalBall()
	{
		super();
	}
	public NormalBall(int X, int Y)
	{
		ballX = X;
		ballY = Y;
	}
	/**
	 * Checks if ball hit a brick
	 * @param bricks bricks to break
	 * @param game currently played level
	 * @return returns true if a brick was hit.
	 */
	public boolean hittingBricks(BrickGenerator bricks, NormalLevel game)
	{
		Rectangle ballRect = new Rectangle(ballX, ballY, ball.getWidth(null), ball.getHeight(null));
			for(int i = 0; i < bricks.getMap().length; i++)
			{
				 for(int j = 0; j <(bricks.getMap()[0]).length; j++)
				{
					 //only if the brick hasn't been destroyed yet
					if(bricks.getMap()[i][j] > 0 )
					{			
						//in case of impact
						if(	ballRect.intersects(j * bricks.getBrickWidth() + 70 + (j*2) , i * bricks.getBrickHeight() + 80 + (i*2), bricks.getBrickWidth(), bricks.getBrickHeight() )	)
						{
							//if a brick has been broken
							if( bricks.breakBrick(i, j, game))
							{
								//points, you get more points if you're wearing the mustache
								if(game.getMustacheOn() == true)
									game.setScore(12);
								else
									game.setScore(10);
							}
							//change of direction dependent on which side of the brick we hit
							if(ballX + 14 == j * bricks.getBrickWidth() + 70 + (j*2) || ballX + 1 == (j + 1) * bricks.getBrickWidth() + 70 + (j*2))
							{
								ballDirX = -ballDirX;
							}				
							if(ballY + 14 == i * bricks.getBrickHeight() + 80 + (i*2) || ballY + 1 ==(i + 1) * bricks.getBrickHeight() + 80 + (i*2) )
							{
								ballDirY = -ballDirY;
							}
								
							return true;
						}				
					}
				}
			}
		return false;
	}
	/**
	 * Checks if another ball was hit  
	 * @param otherBall the other ball
	 * @return returns true if another ball was hit
	 */
	public boolean hittingBalls(SingleBall otherBall)
	{
		Rectangle ballRect = new Rectangle(ballX, ballY, ball.getWidth(null), ball.getHeight(null));
		Rectangle otherBallRect = new Rectangle(otherBall.getBallX(), otherBall.getBallY(), ball.getWidth(null), ball.getHeight(null));
		if(ballRect.intersects(otherBallRect))
		{
			//sides hit 
			if(ballX + 14 <= otherBall.getBallX() || ballX + 1 >= otherBall.getBallX() + ball.getWidth(null) );
			{
				ballDirX = -ballDirX;
				otherBall.setBallDirX(otherBall.getBallDirX());
			}
			//bases hit
			if(ballY + 14 <= otherBall.getBallY() || ballY+ 1 >= otherBall.getBallX() + ball.getHeight(null))
			{
				ballDirY = -ballDirY;
				otherBall.setBallDirY(otherBall.getBallDirY());
			}
			//splits balls
			while(ballRect.intersects(otherBallRect))
			{
				ballX +=ballDirX;
				ballY += ballDirY;
				ballRect = new Rectangle(ballX, ballY, ball.getWidth(null), ball.getHeight(null));
			}
			return true;
		}
		return false;		
	}
}
