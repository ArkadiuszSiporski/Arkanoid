package paddles;

import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import balls.MultiBall;

/**
 * Core paddle used in multiplayer mode. Both BottomPlayer and TopPlayer inherit from it.
 */
public abstract class CorePlayer extends JComponent
{
	protected Image paddle = new ImageIcon((CorePlayer.class.getResource("/resources/Paddle75.png"))).getImage();
	protected int paddleX = 475;
	protected int paddleY;
	protected int paddleDir;
	protected static  final int LEFT = 1;
	protected static final int RIGHT = 2;
	protected boolean left = false;
	protected boolean right = false;
	/**
	 * Constructor sets paddle's height.
	 * @param Y Y coordinate of the paddle
	 */
	public CorePlayer(int Y)
	{
		paddleY = Y;

	}
	/**
	 * Draws the paddle.
	 * @param g the Graphics context in which to paint
	 */
	public void draw(Graphics g)
	{
		g.drawImage(paddle, paddleX, paddleY, null);
	}
	/**
	 * Moves the paddle within the boundaries.
	 * @param ball a ball in game
	 */
	public void movePaddle(MultiBall ball)
	{

		if(left || right)
		{
			for(int i = 0; i < 9; i++)
			{
				
				if(	((paddleDir == -1) && (paddleX >= 0)) || ((paddleX <= 993 - paddle.getWidth(null)) && (paddleDir == 1))	)
				{
		
					paddleX += paddleDir;
					ball.hittingPaddle(paddleX, paddleY, paddleDir, paddle);
				}
			}
			
		}
		else
			paddleDir = 0;
				
	}
	/**
	 * Inner class responsible for paddle's movement.
	 */
	protected class Movement extends AbstractAction
	{
		int dir;
		Movement(int dir)
		{
			this.dir = dir;
		}
		public void actionPerformed(ActionEvent e)
		{
			switch(dir)
			{
				case LEFT:
					left = true;
					paddleDir = -1;
				break;
				case -LEFT:
					left = false;
					
				break;
				case RIGHT:
					right = true;
					paddleDir = 1;
				break;
				case -RIGHT:
					right = false;
				break;
			}
		}
	}
	public void reset()
	{
		paddleX = 475;
	}

	public Image getPaddle() {
		return paddle;
	}
	public int getPaddleX() {
		return paddleX;
	}
	public int getPaddleY() {
		return paddleY;
	}
	public int getPaddleDir() {
		return paddleDir;
	}
}

