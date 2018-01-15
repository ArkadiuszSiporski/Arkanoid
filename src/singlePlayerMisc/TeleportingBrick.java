package singlePlayerMisc;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Class implements target used in third level of single player mode.
 *
 */
public class TeleportingBrick
{
	private Random generator = new Random();
	private int targetX;
	private int targetY;
	private int targetHP;
	private int targetTimer;
	private Image target;
	private Image brick = new ImageIcon (BrickGenerator.class.getResource("/resources/brick2.png")).getImage();
	private Image brickCracked = new ImageIcon (BrickGenerator.class.getResource("/resources/brickCracked.png")).getImage();
	/**
	 * Constructor initializes the target.
	 * @param HP number of hits it takes to destroy the target
	 */
	public TeleportingBrick(int HP)
	{
		moveTarget(new Rectangle(800, 800, 0, 0));
		targetTimer = 2900;
		targetHP = HP;
		target = brick;
	}
	/**
	 * Draws the target
	 * @param g the Graphics context in which to paint
	 */
	public void draw(Graphics g)
	{
		g.drawImage(target, targetX, targetY, null);
	}
	public int getTargetX() {
		return targetX;
	}
	public int getTargetY() {
		return targetY;
	}
	public Image getTarget() {
		return target;
	}
	public int getTargetHP() {
		return targetHP;
	}
	/**
	 * Moves the target after fixed amount of time. Target cannot move to ball's location.
	 * @param ballRect rectangle representing the ball
	 */
	public void moveTarget(Rectangle ballRect)
	{
		do
		{
			targetX = generator.nextInt(943);
			targetY = generator.nextInt(300);
		}while(ballRect.intersects(targetX, targetY,brick.getWidth(null) , brick.getHeight(null)));
		
	}
	/**
	 * Increases target timer which after exceeding a certain value invokes moveTarget method.
	 * @param increase amount by which  the timer is increased.
	 * @param ballRect rectangle representing the ball.
	 */
	public void increaseTargetTimer(int increase,Rectangle ballRect)
	{
		targetTimer += increase;
		if(targetTimer >= 3000)
		{
			moveTarget(ballRect);
			targetTimer = 0;
		}
	}
	/**
	 * Decreases target's durability by 1.
	 * @return return true when the target has just been destroyed.
	 */
	public boolean breakTarget()
	{
		targetHP--;
		if(targetHP == 2)
		{
			target = brickCracked;
		}
		else if (targetHP == 0)
		{
			return true;
		}
		return false;
	}
}
