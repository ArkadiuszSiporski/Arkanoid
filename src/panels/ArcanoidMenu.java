package panels;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import internationalization.Internationalizer;
import main.Main;
import singlePlayerLevels.LevelOne;
import singlePlayerLevels.LevelThree;
import singlePlayerLevels.LevelTwo;
import utils.LevelFactory;
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
	
	private Internationalizer internationalizer = Internationalizer.getInstance();
	
	/**
	 * This constructor initializes  all objects the panel is built of.
	 */
	public ArcanoidMenu()
	{

		setLayout(new GridBagLayout());
		
		thirdLevel = 	setLabel(level3Icon,  level3RollOver, 3);
		LevelFactory.setThirdLevel(thirdLevel);
		secondLevel =setLabel(level2Icon,  level2RollOver, 2);
		LevelFactory.setSecondLevel(secondLevel);
		firstLevel = setLabel(level1Icon, level1RollOver, 1);

		secondLevel.setEnabled(false);
		thirdLevel.setEnabled(false);
		
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB,KeyEvent.CTRL_DOWN_MASK ), "cheats");
		getActionMap().put("cheats", this.new Controls(CHEATS));
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB" ), "back");
		getActionMap().put("back", this.new Controls(BACK));
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
		getActionMap().put("exit", this.new Controls(EXIT));
		
		back = new JButton(internationalizer.getString("back"));
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Main main = ((Main)SwingUtilities.getRoot(ArcanoidMenu.this));
				main.toMenu();
				main.remove(ArcanoidMenu.this);
			}
		});
		back.setFocusable(false);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		add(firstLevel, gridBagConstraints);
		gridBagConstraints.gridx =1;
		add(secondLevel, gridBagConstraints);
		gridBagConstraints.gridx = 2;
		add(thirdLevel, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		add(back, gridBagConstraints);
	
	}
	
	public void translate(){
		back.setText(internationalizer.getString("back"));
	}
	
	/**
	 * This methond sets up arcanoid menu labels.
	 * @param level icon that is shown when the cursor doesn't hover over the label
	 * @param rollOver icon that is shown when the cursor hovers over the label
	 * @param lvl which level the label is representing
	 * @return returns a reference to set label
	 */
	private JLabel setLabel(final Icon level, final Icon rollOver, final int lvl)
	{
		final JLabel label = new JLabel(level);
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
         					main.setGame(LevelFactory.getLevel(lvl));
         				break;
         				case 2:
         					main.setGame(LevelFactory.getLevel(lvl));
         				break;
         				case 3:
         					main.setGame(LevelFactory.getLevel(lvl));
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
				main.remove(ArcanoidMenu.this);
			}
		}
	}
}
