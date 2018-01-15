package balls;

import java.awt.Rectangle;

import singlePlayerMisc.TeleportingBrick;
/**
 * Ball used in the third level.
 *
 */
public class SpecialBall extends SingleBall
{
	/**
	 * Checks if the target was hit.
	 * @param target an extraordinary brick
	 * @return returns true if the target was hit
	 */
	public boolean hittingTarget(TeleportingBrick target)
	{
		Rectangle ballRect = new Rectangle(ballX, ballY, ball.getWidth(null), ball.getHeight(null));
		Rectangle targetRect = new Rectangle(target.getTargetX(), target.getTargetY(), target.getTarget().getWidth(null), target.getTarget().getHeight(null));
		if(ballRect.intersects(targetRect))
		{
			//change of direction dependent on which side of the target we hit
			if(ballX + (15 - ballDirX) <= target.getTargetX() || ballX - ballDirX >= target.getTargetX() + target.getTarget().getWidth(null) )
				ballDirX = -ballDirX;
			else
				ballDirY = -ballDirY;
			if(target.breakTarget())
				return true;
		}
		return false;
	}
	
	public Rectangle getBallRect()
	{
		return new Rectangle(ballX, ballY, ball.getWidth(null), ball.getHeight(null));
	}
}
