package singlePlayer;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import abstractLevels.NormalLevel;
import paddles.SinglePlayer;

/**
 * Class implements super powers that can spawn from broken bricks.
 *
 */
public class SuperPower
{
	//constants matching with those from NormalLevel
	private static final int SPEED_UP = 1;
	private static final int SLOW_DOWN = 2;
	private static final int WIDEN = 3;
	private static final int NARROW = 4;
	private static final int ADDITIONAL_BALL = 5;
	private Random generator = new Random();
	//
	private int alreadyIn = 0;
	//odds of spawning a new super power from a broken brick
	private float superPowerChance = 0.26f;//0.26f;
	//list of powers in the playground(maximum of 2)
	private List<Integer[]> powers = new ArrayList<Integer[]>();
	//map connecting power's number with it's picture
	private HashMap<Integer, Image> powersMap = new HashMap<Integer, Image>();
	private Image speedUp = new ImageIcon(SuperPower.class.getResource("/resources/speedUp.png")).getImage();;
	private Image slowDown = new ImageIcon(SuperPower.class.getResource("/resources/slowDown.png")).getImage();
	private Image widen= new ImageIcon(SuperPower.class.getResource("/resources/widen.png")).getImage();
	private Image narrow = new ImageIcon(SuperPower.class.getResource("/resources/narrow.png")).getImage();
	private Image additionalBall = new ImageIcon(SuperPower.class.getResource("/resources/additionalBall.png")).getImage();
	public SuperPower()
	{
		generator = new Random();
		powersMap.put(SPEED_UP, speedUp);
		powersMap.put(SLOW_DOWN, slowDown);
		powersMap.put(WIDEN, widen);
		powersMap.put(NARROW, narrow);
		powersMap.put(ADDITIONAL_BALL, additionalBall);
	}
	/**
	 * Draws super powers.
	 * @param g the Graphics context in which to paint
	 */
	public void draw (Graphics g)
	{
		//if the list is not empty
		if(!powers.isEmpty() )
		{
			for(Iterator <Integer[]> iterator = powers.iterator(); iterator.hasNext(); )
			{
				Integer[] temp = iterator.next();
				g.drawImage(powersMap.get(temp[0]), temp[1], temp[2]++, null);
				//if power has left the playground it disappears
				if(temp[2] > 750)
					iterator.remove();
			}
		}	
	}
	/**
	 * Adds a new power to list
	 * @param brickWidth  width of a brick
	 * @param brickHeight  height of a brick
	 * @param i column from which the brick is
	 * @param j row from which the brick is
	 * @param isExtraBall  additional ball
	 */
	public void spawnPower(int brickWidth, int brickHeight, int i, int j, boolean isExtraBall)
	{
		if(powers.size() < 2 && generator.nextFloat() <= superPowerChance)
		{	
			int nextPower;
			do
			{
			//if there already is an extra ball
			if(isExtraBall)
				nextPower = generator.nextInt(4) + 1;
			else
					nextPower = generator.nextInt(5) + 1;
			}while(nextPower == alreadyIn);
			alreadyIn = nextPower;
			try{
				throw new Exception();
			} catch(Exception e){
				
			}

			
			//+55 and not +70 because we subtract half of power's width to align centers
			Integer[] power1 = {nextPower, j * brickWidth + 55 + (j*2) + 25, i * brickHeight + 80 + (i*2)};
			powers.add(power1);
			
		}
	}
	/**
	 * Checks if any power was caught by the player.
	 * @param game currently played level
	 * @param bricks bricks to break
	 * @param player player's paddle
	 */
	//
	public void hittingPaddle(NormalLevel game, BrickGenerator bricks, SinglePlayer player)
	{
		for(Iterator <Integer[]> iterator = powers.iterator(); iterator.hasNext(); )
		{
			Integer[] temp = iterator.next();
			if(player.getPlayer().intersects(temp[1], temp[2], 30, 20))
			{
				game.activatePower(temp[0]);
				iterator.remove();
			}	
		}	
	}
	public void setSuperPowerChance(float superPowerChance)
	{
		this.superPowerChance = superPowerChance;
	}
	public float getSuperPowerChance()
	{
		return superPowerChance;
	}
	public List<Integer[]> getPowers()
	{
		return powers;
	}
}
