package singlePlayer;

import abstractLevels.CoreLevel;

/**
 * Class implementing a timer used by NormalLevel class to change game speed for a fixed amount of time. 
 */
public class DelayTimer extends Thread
{	
	private CoreLevel game;
	private int sleepDuration;
	private boolean interrupted = false;
	private boolean run = true;
	/**
	 * This constructor initializes sleepDuration variable with the number of seconds the power should be active.
	 * @param game currently played level
	 * @param duration number of seconds the power should be active
	 */
	public DelayTimer(CoreLevel game, double duration)
	{
		this.game = game;
		sleepDuration = (int)(duration * 1000);
	}
	/**
	 * This class waits the given amount on time after which it restores default game speed.
	 */
	public void run()
	{
		try
		{
			while(sleepDuration > 0 && interrupted == false)
			{
				if(run)
				{
					Thread.sleep(10);
					sleepDuration -= 10;
				}
			}
			if(sleepDuration == 0)
				game.resetDelay();
		}
		catch(Exception ex)
		{	
		}	
	}
    public void pause()
    {
        run = false;
    }
    
    public void proceed()
    {
    	run = true;
    }
    @Override
    public void interrupt()
    {
    	interrupted = true;
    }
}

