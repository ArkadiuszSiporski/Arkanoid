package singlePlayerLevels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import abstractLevels.CoreLevel;
import abstractLevels.NormalLevel;
import balls.SpecialBall;
import main.Main;
import singlePlayerMisc.Meteor;
import singlePlayerMisc.TeleportingBrick;

/**
 * Class implements third level of single player mode.
 */
public class LevelThree extends CoreLevel {
	// constants used by Key Bindings
	private static final int BACK = 1;
	private static final int EXIT = 2;
	private static final int SAFE_LEAVE = 3;
	// target's hp
	private static final int TARGET_HP = 5;
	private TeleportingBrick target;
	private Meteor meteor = new Meteor();

	/**
	 * Constructor initializes background, ball, adds a mouse listener and Key
	 * Bindings.
	 * 
	 * @param main
	 *            JFrame in which the panel is displayed.
	 */
	public LevelThree(Main main) {
		super(main);
		target = new TeleportingBrick(TARGET_HP);

		ball = new SpecialBall();
		background = new ImageIcon(NormalLevel.class.getResource("/resources/Level3bg.png")).getImage();
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if (safeLeave)
					timer.start();
			}

			public void mouseExited(MouseEvent e) {
				if (safeLeave)
					timer.stop();
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
			}
		});
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB"), "back");
		getActionMap().put("back", new Controls(BACK));
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
		getActionMap().put("exit", new Controls(EXIT));
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "safe leave");
		getActionMap().put("safe leave", new Controls(SAFE_LEAVE));
	}

	/**
	 * Inner class responsible for handling Key Bindings.
	 *
	 */
	private class Controls extends AbstractAction {
		int instruction;

		Controls(int instruction) {
			this.instruction = instruction;
		}

		public void actionPerformed(ActionEvent e) {
			if (instruction == BACK) {
				main.toArcanoidMenu();
			} else if (instruction == EXIT) {
				main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
			} else if (instruction == SAFE_LEAVE && timer.isRunning()) {

				safeLeave = !safeLeave;
				repaint();
			}
		}
	}

	/**
	 * Paints background, paddle, meteor, target and game result. Calls super
	 * class' paint method.
	 * 
	 * @param g
	 *            the Graphics context in which to paint
	 */
	public void paint(Graphics g) {
		// background

		g.drawImage(background, 0, 0, null);
		// paddle
		player.draw(g);
		// meteor
		meteor.draw(g);
		// target
		target.draw(g);
		// won
		if (gameState == WON) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setColor(Color.GREEN);
			g.setFont(new Font("Serif", Font.BOLD, 70));
			g.drawString(internationalizer.getString("congratulations"), 150, 500);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString(internationalizer.getString("finishedGame"), 300, 600);
		}
		super.paint(g);
	}

	/**
	 * Controls the game - increases target's timer, decreases score overtime,
	 * checks for impacts between ball and paddle/target, checks if the ball is
	 * still in the playground.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (play) {
			target.increaseTargetTimer(delay, ((SpecialBall) ball).getBallRect());
			// time penalty
			score -= 0.001;
			// ball hitting the frame
			ball.hittingFrame();
			// ball hitting the paddle
			ball.hittingPaddle(player.getPlayer(), player.getPlayerDir());
			// ball hitting target
			if (((SpecialBall) ball).hittingTarget(target)) {
				setScore(1000);
				victory();
			}

			// meteorTimer
			meteor.increaseMeteorTimer(3000);
			// meteor hitting the paddle
			if (meteor.hittingPaddle(player.getPlayer())) {
				over();
			}
			// check if player lost
			if (ball.outOfBounds())
				over();
			// ball movement
			ball.movement();
		}
		if (gameState == PLAY)
			repaint();
	}

	/**
	 * Ends game when the player broke the target, calls super class' victory
	 * method.
	 */

	public void victory() {
		play = false;
		gameState = WON;
		super.victory();
		repaint();
	}

	/**
	 * Sets up a new game.
	 */
	public void newGame() {
		target = new TeleportingBrick(TARGET_HP);
		meteor.reset();
		player.reset();
		super.newGame();
	}
}
