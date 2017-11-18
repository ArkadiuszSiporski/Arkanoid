package singlePlayerLevels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import javax.swing.KeyStroke;

import abstractLevels.NormalLevel;
import main.Main;
import singlePlayer.BrickGenerator;
import singlePlayer.Meteor;

/**
 * Class implements second level of single player mode.
 */
public class LevelTwo extends NormalLevel
{
	// constants used by Key Bindings
	private static final int CONTINUE = 0;
	private static final int BACK = 1;
	private static final int EXIT = 2;
	private static final int SAFE_LEAVE = 3;
	private Meteor meteor = new Meteor();
	/**
	 * Constructor sets up Key Bindings, reference to third level and JFrame in which the panel is displayed.
	 * @param main JFrame in which the panel is displayed.
	 * @param thirdLevel reference to label representing next level
	 */
	public LevelTwo(Main main, JLabel thirdLevel)
	{
		super(main);
		this.nextLevel = thirdLevel;
		background = new ImageIcon(NormalLevel.class.getResource("/resources/Level2bg.png")).getImage();
		bricks = new BrickGenerator(10, 15);
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"),"continue");
		getActionMap().put("continue",new Controls(CONTINUE));
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB"),"back");
		getActionMap().put("back",new Controls(BACK));
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"),"exit");
		getActionMap().put("exit",new Controls(EXIT));
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"),"safe leave");
		getActionMap().put("safe leave",new Controls(SAFE_LEAVE));

	}
	/**
	 * Inner class responsible for handling Key Bindings.
	 */
	private class Controls extends AbstractAction
	{
		int instruction;
		Controls(int instruction)
		{
			this.instruction = instruction;
		}
		public void actionPerformed(ActionEvent e)
		{
			if(instruction == BACK)
			{
				main.toArcanoidMenu();
			}
			else if( gameState == UNLOCKED_NEXT_LEVEL && instruction == CONTINUE )
			{
				main.setGame(new LevelThree(main));
			}
			else if(instruction == EXIT)
			{
				main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
			}
			else if (instruction == SAFE_LEAVE && 	timer.isRunning())
			{
				if(safeLeave)
					safeLeave = false;
				else
					safeLeave = true;
			}
		}
	}
	
	/**
	 * Paint background, meteor, result and calls super class' paint method.
	 * @param g the Graphics context in which to paint
	 */
	public void paint(Graphics g)
	{
		//background
		g.drawImage(background,0, 0, null);
		//meteor
		meteor.draw(g);
		//won
		if(gameState >= WON)
		{
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setColor(Color.GREEN);
			g.setFont(new Font("Serif", Font.BOLD, 100));
			g.drawString("YOU WON!", 250, 500);
			if(gameState == UNLOCKED_NEXT_LEVEL )
			{
				g.setFont(new Font("Serif", Font.BOLD, 50));
				g.drawString("LEVEL 3 HAS BEEN UNLOCKED", 150, 600);
				g.setFont(new Font("Serif", Font.BOLD, 25));
				g.drawString("(Press SPACE to continue)", 350, 650);
				g.drawString("(or a mouse button to replay this level)", 280, 670);
			}
		}
		super.paint(g);
	}
	/**
	 * Increses meteor's timer and checks for impact between meteor and the paddle.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(play)
		{
			meteor.increaseMeteorTimer(delay);
			//meteor hitting the paddle
			if(meteor.hittingPaddle(player.getPlayer()))
			{
				over();
			}
		}
		super.actionPerformed(e);	
	}
	/**
	 * Sets up a new game.
	 */
	public void newGame()
	{
		resetDelay();
		bricks = new BrickGenerator(10, 15);
		meteor.reset();
		player.reset();
		super.newGame();
	}
}
