package paddles;



import javax.swing.KeyStroke;

import paddles.CorePlayer;

import javax.swing.JComponent;
/**
 * Bottom player's paddle in multiplayer mode.
 *
 */
public class BottomPlayer extends CorePlayer
{
	private static final long serialVersionUID = 4145857394574179491L;

	/**
	 * This constructors sets paddle's height as well as Key Bindings responsible for paddle's movement. The player can  move using left/right arrows or 4/6 numlock numbers
	 * @param Y paddle's Y coordinate
	 */
	//
	public BottomPlayer(int Y)
	{
		super(Y);
		//arrowkeys
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"left");
		getActionMap().put("left",new Movement(LEFT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"),"releasedA");
		getActionMap().put("releasedA",new Movement(-LEFT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"right");
		getActionMap().put("right",new Movement(RIGHT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"),"releasedD");
		getActionMap().put("releasedD",new Movement(-RIGHT));
		
		//numlock arrows
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD4"),"left");
		getActionMap().put("left",new Movement(LEFT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released NUMPAD4"),"releasedA");
		getActionMap().put("releasedA",new Movement(-LEFT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("NUMPAD6"),"right");
		getActionMap().put("right",new Movement(RIGHT));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released NUMPAD6"),"releasedD");
		getActionMap().put("releasedD",new Movement(-RIGHT));
		
	}


}
