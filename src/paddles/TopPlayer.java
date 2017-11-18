package paddles;

import javax.swing.KeyStroke;

import paddles.CorePlayer;

import javax.swing.JComponent;
/**
 * Top player's paddle in multiplayer mode.
 *
 */
public class TopPlayer extends CorePlayer
{
	//moves using A/D
	/**
	 * This constructors sets paddle's height as well as Key Bindings responsible for paddle's movement. The player can  move A/D keys.
	 * @param Y paddle's Y coordinate
	 */
	//
	public TopPlayer(int Y)
	{
		super(Y);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"),"left");
		getActionMap().put("left",new Movement(LEFT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"),"releasedA");
		getActionMap().put("releasedA",new Movement(-LEFT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"),"right");
		getActionMap().put("right",new Movement(RIGHT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"),"releasedD");
		getActionMap().put("releasedD",new Movement(-RIGHT));	
	}
}
