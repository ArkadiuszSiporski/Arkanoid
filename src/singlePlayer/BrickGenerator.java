package singlePlayer;



import java.awt.Graphics2D;
import java.awt.Image;


import javax.swing.ImageIcon;

import abstractLevels.NormalLevel;
/**
 * Class used in first and second level to create bricks.
 *
 */
public class BrickGenerator
{

	//POWERS
	private SuperPower powers;

	private int map[][];
	private Image brick = new ImageIcon (BrickGenerator.class.getResource("/resources/brick2.png")).getImage();
	private Image brickCracked = new ImageIcon (BrickGenerator.class.getResource("/resources/brickCracked.png")).getImage();
	private int brickWidth = brick.getWidth(null);
	private int brickHeight= brick.getHeight(null);
	private int numberOfBricks;
	private final int maxBricks;

	


	/**
	 * Initializes bricks and super powers.
	 * @param rows number of rows  to generate
	 * @param columns number of columns  to generate
	 */
	public BrickGenerator(int rows, int columns)
	{
		powers = new SuperPower();
		numberOfBricks = rows * columns;
		maxBricks = numberOfBricks;
		map = new int[rows][columns];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				map[i][j] = 2;
			}
		}
	}
	/**
	 * Draws bricks
	 * @param g the Graphics context in which to paint
	 */
	public void draw(Graphics2D g)
	{
		//rows
		for(int i = 0; i < map.length; i++) 
		{
			//columns
			for(int j = 0; j < map[0].length; j++)
			{
				if(map[i][j] >0 )
				{
					if(map[i][j] ==2)
						g.drawImage(brick,j * brickWidth + 70 + (j*2), i * brickHeight + 80 + (i*2),  null);
					else
						g.drawImage(brickCracked,j * brickWidth + 70 + (j*2), i * brickHeight + 80 + (i*2),  null);					
				}
			}
		}
		powers.draw(g);
	}
	/**
	 * Reduces brick's durability by 1.
	 * @param row in which row the hit brick is
	 * @param column in which column the hit brick is
	 * @param game level that's currently being played
	 * @return return true if the brick has been broken
	 */
	//returns true if a brick has been broken
	public boolean breakBrick(int row, int column, NormalLevel game)
	{
		if(map[row][column] > 0)
		{
			map[row][column]--;	
			if(map[row][column] == 0)
			{
				numberOfBricks--;
				if(numberOfBricks == maxBricks/2)
					game.MustacheEvent();
				else if (numberOfBricks == 0)
					game.victory();
				powers.spawnPower(brickWidth, brickHeight, row, column, game.isExtraBall());
				return true;
			}
		}
		return false;
	}
	public SuperPower getPowers()
	{
		return powers;
	}
	public int[][] getMap()
	{
		return map;
	}
	public int getNumberOfBricks()
	{
		return numberOfBricks;
	}
	public int getBrickWidth()
	{
		return brickWidth;
	}
	public int getBrickHeight()
	{
		return brickHeight;
	}	
}
