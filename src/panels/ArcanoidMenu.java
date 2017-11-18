package panels;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import main.Main;
import singlePlayerLevels.LevelOne;
import singlePlayerLevels.LevelThree;
import singlePlayerLevels.LevelTwo;
/**
 * Class that implements arcanoid menu in which we can choose the level we want to play.
 *
 */
public class ArcanoidMenu extends JPanel
{
	// constants used by Key Bindings
	private static final int CHEATS = 0;
	private static final int BACK = 1;
	private static final int EXIT = 2;
	
	private JButton back;
	private JLabel firstLevel;
	private JLabel secondLevel;
	private JLabel thirdLevel;
	private Icon level1Icon = new ImageIcon( ArcanoidMenu.class.getResource("/resources/Level1.png"));
	private Icon level2Icon = new ImageIcon( ArcanoidMenu.class.getResource("/resources/Level2.png"));
	private Icon level3Icon = new ImageIcon( ArcanoidMenu.class.getResource("/resources/Level3.png"));
	private Icon level1RollOver = new ImageIcon( ArcanoidMenu.class.getResource("/resources/Level1RollOver.png"));
	private Icon level2RollOver= new ImageIcon( ArcanoidMenu.class.getResource("/resources/Level2RollOver.png"));
	private Icon level3RollOver= new ImageIcon( ArcanoidMenu.class.getResource("/resources/Level3RollOver.png"));
	/**
	 * This constructor initializes  all objects the panel is built of.
	 */
	public ArcanoidMenu()
	{

		setLayout(new FlowLayout());
		
		firstLevel = setLabel(level1Icon, level1RollOver, 1);
		secondLevel =setLabel(level2Icon,  level2RollOver, 2);
		thirdLevel = 	setLabel(level3Icon,  level3RollOver, 3);

		secondLevel.setEnabled(false);
		thirdLevel.setEnabled(false);
		
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB,KeyEvent.CTRL_DOWN_MASK ), "cheats");
		getActionMap().put("cheats", this.new Controls(CHEATS));
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB" ), "back");
		getActionMap().put("back", this.new Controls(BACK));
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
		getActionMap().put("exit", this.new Controls(EXIT));
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Main main = ((Main)SwingUtilities.getRoot(ArcanoidMenu.this));
				main.toMenu();
				main.remove(main.getArcanoidMenu());
			}
		});
		back.setFocusable(false);
		add(firstLevel);
		add(secondLevel);
		add(thirdLevel);
		add(back);
	
	}
	
	/**
	 * This methond sets up arcanoid menu labels.
	 * @param level icon that is shown when the cursor doesn't hover over the label
	 * @param rollOver icon that is shown when the cursor hovers over the label
	 * @param lvl which level the label is representing
	 * @return returns a reference to set label
	 */
	public JLabel setLabel(Icon level, Icon rollOver, int lvl)
	{
		JLabel label = new JLabel(level);
		label.addMouseListener(new MouseAdapter()
		{
             @Override
             public void mouseEntered(MouseEvent e)
             {
            	 if(label.isEnabled())
            		 label.setIcon(rollOver);
             }
             @Override
             public void mouseExited(MouseEvent e)
             {
            	 label.setIcon(level);
             }
             public void mouseReleased(MouseEvent e)
         	 {
            	 Main main = ((Main)SwingUtilities.getRoot(ArcanoidMenu.this));
            	 if(label.isEnabled())
            	 {   	   
            		 label.setIcon(level);
            		 setBounds(140, 0, 1000, 800);
         			switch(lvl)
         			{
         				case 1:
         					main.setGame(new LevelOne(main, secondLevel, thirdLevel));
         				break;
         				case 2:
         					main.setGame(new LevelTwo(main, thirdLevel));
         				break;
         				case 3:
         					main.setGame(new LevelThree(main));
         				break;
         				
         			}
         			main.toGame();
         			
         			
            	 }
         	 }
		 });
		return label;
	}
	/**
	 * Inner class responsible for handling the Key Bindings.
	 *
	 */
	private class Controls extends AbstractAction
	{
		int action;
		Controls(int action)
		{
			this.action = action;
		}
		public void actionPerformed(ActionEvent e)
		{
			Main main = ((Main)SwingUtilities.getRoot(ArcanoidMenu.this));
			//unlock all levels
			if(action == CHEATS)
			{
				if(!secondLevel.isEnabled() || !thirdLevel.isEnabled())
				{
					secondLevel.setEnabled(true);
					thirdLevel.setEnabled(true);
				}
			}
			//close the window
			else if(action == EXIT)
			{
				main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
			}
			else//back to menu
			{
				main.toMenu();
				main.remove(main.getArcanoidMenu());
			}
		}
	}
}
