package singlePlayerMisc;

import paddles.SinglePlayer;

/**
 * Class implementing timer used for super powers related to the paddle(it's size) and paddle's direction.
 *
 */
public class PaddleTimer extends Thread
{
	private static final int SIZE = 0;
	private boolean run;
	private boolean interrupted = false;
	private final SinglePlayer paddle;
	private int sleepDuration;
	private final int type;
	

	/**
	 * Constructor initializes paddle, duration and which setting should be reseted after the time has passed
	 * @param paddle player's paddle
	 * @param duration how long the timer should wait before reseting to default settings
	 * @param type which setting should be reseted.
	 */
	public PaddleTimer(SinglePlayer paddle, double duration, int type)
	{
		this.paddle = paddle;
		sleepDuration = (int)(duration * 1000);
		this.type = type;
		run = true;
	}
	/**
	 * Waits given amount of time after which resets the chosen setting.
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
			{
				//resets size of the paddle
				if(type == SIZE)
				{
					paddle.setPaddle(1);
				}
				//resets direction of the paddle
				else
					paddle.resetPlayerDir();
			}
			
		}
		catch(Exception ex)
		{	
			//ex.printStackTrace();
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
