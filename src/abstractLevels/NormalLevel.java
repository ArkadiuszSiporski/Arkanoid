package abstractLevels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import balls.NormalBall;
import balls.SingleBall;
import exceptions.InterruptTimer;
import main.Main;
import singlePlayer.BrickGenerator;
import singlePlayer.DelayTimer;
import singlePlayer.Mustache;

/**
 * Class containing additional features. Superclass of first and second level.
 *
 */
public abstract class NormalLevel extends CoreLevel {
	// 4th over constant
	protected static final int UNLOCKED_NEXT_LEVEL = 3;
	// constants matching with those in SuperPowers
	protected static final int SPEED_UP = 1;
	protected static final int SLOW_DOWN = 2;
	protected static final int WIDEN = 3;
	protected static final int NARROW = 4;
	protected static final int ADDITIONAL_BALL = 5;
	// paddle sizes
	protected static final int SMALL = 0; // 45
	protected static final int MEDIUM = 1; // 75
	protected static final int BIG = 2;// 125
	// superpowers
	protected Thread timeThread = null;
	protected Thread sizeThread = null;
	// MUSTACHE
	protected Mustache mustache = new Mustache();
	protected boolean mustacheEvent = false;
	protected boolean mustacheOn = false;
	protected Image mustacheImage = new ImageIcon(NormalLevel.class.getResource("/resources/mustache.png")).getImage();
	// level to unlock
	protected JLabel nextLevel;
	// BRICKS
	protected BrickGenerator bricks;
	// TIMER
	protected DelayTimer delayTimer;
	// EXTRA BALL
	protected SingleBall additionalBall;

	/**
	 * This constructor initializes the ball and adds a MouseListener.
	 * 
	 * @param main
	 *            window in which the panel is viewed
	 */
	public NormalLevel(Main main) {
		super(main);
		ball = new NormalBall();
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if (safeLeave) {
					timer.start();
					player.resumeTimers();
					if (delayTimer != null && delayTimer.isAlive())
						delayTimer.proceed();
				}

			}

			public void mouseExited(MouseEvent e) {
				if (safeLeave) {
					timer.stop();
					player.pauseTimers();
					if (delayTimer != null && delayTimer.isAlive())
						delayTimer.pause();
				}

			}

			public void mouseReleased(MouseEvent e) {
				// starts the game
				if (play == false && gameState == PLAY) {
					ball.start();
					play = true;
				}
				// sets the game up after a loss so it can start
				else if (gameState != PLAY) {
					ball.reset();
					newGame();
					gameState = PLAY;
				}
				player.movePaddle(e.getX(), play, ball);
			}
		});
	}

	/**
	 * Paints some of the features such as balls, bricks, mustache.
	 */
	@Override
	public void paintComponent(Graphics g) {
		// paddle
		player.draw(g);
		// bricks
		bricks.draw((Graphics2D) g);
		// additional Ball
		if (additionalBall != null)
			additionalBall.draw(g);
		// mustache
		if (mustacheEvent)
			mustache.draw(g);
		if (mustacheOn)
			g.drawImage(mustacheImage, player.getPlayerX() + player.getImageWidth() / 2 - 75, player.getPlayerY() - 40,
					null);
		super.paintComponent(g);
		g.dispose();
	}

	/**
	 * This method registers impacts e.g balls hitting one another, balls
	 * hitting frame or the paddle and checks if there is at least one ball
	 * still in the playground.
	 */
	public void actionPerformed(ActionEvent e) {
		if (play) {
			// time penalty
			score -= 0.001;
			ball.moveBall(player.getPlayer(), player.getPlayerDir(), bricks, this, additionalBall);
			// additionalBall
			if (additionalBall != null)
				additionalBall.moveBall(player.getPlayer(), player.getPlayerDir(), bricks, this, ball);
			// power hitting the paddle
			bricks.getPowers().hittingPaddle(this, bricks, player);
			// mustache hitting the paddle
			if (mustacheEvent == true && mustache.getMustacheY() < 740 && mustache.hittingPaddle(player.getPlayer())) {
				mustacheOn = true;
				// increasing SuperPowerChance by 50%
				bricks.getPowers().setSuperPowerChance(bricks.getPowers().getSuperPowerChance() * 1.5f);
			}
			// check if player lost
			if (ball.outOfBounds()) {
				if (additionalBall == null)
					over();
				else {
					ball = additionalBall;
					additionalBall = null;
				}

			}
			if (additionalBall != null && additionalBall.outOfBounds())
				additionalBall = null;
		}
		if (gameState == PLAY)
			repaint();
	}

	/**
	 * This method is responsible for activating powers that hit the paddle.
	 * 
	 * @param power
	 *            which power has been activated
	 */
	public void activatePower(int power) {
		switch (power) {
		case SPEED_UP:
			timer.stop();
			timer = new Timer(4, this);
			timer.start();
			break;
		case SLOW_DOWN:
			timer.stop();
			timer = new Timer(12, this);
			timer.start();
			break;
		case WIDEN:
			player.setPaddle(BIG);
			break;
		case NARROW:
			player.setPaddle(SMALL);
			break;
		case ADDITIONAL_BALL:
			additionalBall = new NormalBall(player.getPlayerX() + player.getImageWidth() / 2, player.getPlayerY() - 20);
			additionalBall.start();
			break;
		}
		// THREADS/TIMERS
		if (power == SPEED_UP || power == SLOW_DOWN) {
			try {

				if (delayTimer != null && delayTimer.isAlive()) {

					throw new InterruptTimer();
				}
			} catch (Exception e) {
				delayTimer.interrupt();
			}
			delayTimer = new DelayTimer(this, 10);
			delayTimer.start();
		} else if (power == WIDEN || power == NARROW) {
			player.startSizeTimer();
		}
	}
	/**
	 * This method starts the mustache event(triggers the mustache fall)
	 */
	public void MustacheEvent() {
		mustache = new Mustache();
		mustacheEvent = true;
	}

	public boolean getMustacheOn() {
		return mustacheOn;
	}

	/**
	 * This method sets up a new round
	 */

	public void newGame() {
		if (sizeThread != null && sizeThread.isAlive()) {
			sizeThread.interrupt();
		}
		if (timeThread != null && timeThread.isAlive()) {
			timeThread.interrupt();
		}
		super.newGame();
	}

	/**
	 * Finishes the game when the player won. Possibly unlocking next level(if
	 * it hasn't been unlocked yet).
	 */
	public void victory() {
		play = false;
		gameState = WON;
		if (!nextLevel.isEnabled()) {
			nextLevel.setEnabled(true);
			gameState = UNLOCKED_NEXT_LEVEL;
		}
		super.victory();
		repaint();
	}

	public boolean isExtraBall() {
		if (additionalBall == null)
			return false;
		else
			return true;
	}
}
