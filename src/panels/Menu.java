package panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import main.Main;

/**
 * Class that implements menu panel in which we can choose the mode we want to
 * play, display highscores or exit the game.
 *
 */
public class Menu extends JPanel {

	private JButton singlePlayer;
	private JButton multiPlayer;
	private JButton highscores;
	private JButton exit;
	private JList<String> languages;

	/**
	 * Constructor sets up Key Bindings and invokes method to set up all used
	 * buttons.
	 */
	public Menu() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		singlePlayer = setButtons("Single Player");
		multiPlayer = setButtons("1v1");
		highscores = setButtons("Highscores");
		exit = setButtons("Exit");
		// KEY BINDINGS
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
		getActionMap().put("exit", this.new Controls());
		// BUTTONS' LISTENER
		ActionHandler handler = new ActionHandler();
		singlePlayer.addActionListener(handler);
		multiPlayer.addActionListener(handler);
		highscores.addActionListener(handler);
		exit.addActionListener(handler);
		// LIST
		languages = setLanguageList();

		add(languages);
		add(Box.createRigidArea(new Dimension(0, 5)));

		add(singlePlayer);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(multiPlayer);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(highscores);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(exit);
		

	}

	/**
	 * Creates an object button.
	 * 
	 * @param text
	 *            what should be written on a particular button
	 * @return returns the created button's reference
	 */
	private JList<String> setLanguageList() {
		JList<String> list = new JList<String>(new String[] { "Polish", "English" });
		list.setSize(new Dimension(300, 50));
		list.setFixedCellHeight(20);
		list.setFixedCellWidth(300);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(1);
		list.setDropMode(DropMode.ON);
		return list;
	}

	private JButton setButtons(String text) {
		Dimension max = new Dimension(300, (325 - 70) / 4);
		JButton button = new JButton(text);
		button.setVisible(true);
		button.setFocusable(false);
		button.setMaximumSize(max);
		return button;
	}

	/**
	 * Inner class responsible for handling buttons' functionality.
	 *
	 */
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Main main = ((Main) SwingUtilities.getRoot(Menu.this));
			// switching to arcanoidMenu
			if (e.getSource() == singlePlayer) {
				main.toArcanoidMenu();
				main.remove(main.getMenu());
			}
			// switching to multiplayer
			else if (e.getSource() == multiPlayer) {
				main.toMultiplayer();
			}
			// switching to Highscores
			else if (e.getSource() == highscores) {
				main.toHighscores();
			}
			// closing the window
			else if (e.getSource() == exit) {
				main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
			}
		}
	}

	/**
	 * Inner class responsible for handling Key Bindings.
	 *
	 */
	private class Controls extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			Main main = ((Main) SwingUtilities.getRoot(Menu.this));
			// close the window
			main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
		}
	}
}
