package panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import db.dao.PlayerDaoImpl;
import main.Main;
import utils.Internationalizer;
import utils.LanguageRenderer;
import utils.Internationalizer.Language;

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
	private JList<Language> languages;
	private JComboBox<String> comboBox;
	private Map<String, String> plafs;
	private Internationalizer internationalizer = Internationalizer.getInstance();

	/**
	 * Constructor sets up Key Bindings and invokes method to set up all used
	 * buttons.
	 */
	public Menu() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		singlePlayer = setButton(internationalizer.getString("singlePlayer"));
		multiPlayer = setButton(internationalizer.getString("multiPlayer"));
		highscores = setButton(internationalizer.getString("highscores"));
		exit = setButton(internationalizer.getString("exit"));
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
		// COMBO BOX
		comboBox = setComboBox();

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		add(languages);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(comboBox);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(singlePlayer);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(multiPlayer);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(highscores);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(exit);

	}

	private JList<Language> setLanguageList() {
		JList<Language> list = new JList<Language>();
		list.setSize(new Dimension(300, 50));
		list.setFixedCellHeight(25);
		list.setFixedCellWidth(150);
		list.setBounds(100, 0, 300, 50);
		list.setOpaque(false);
		// SwingUtilities.updateComponentTreeUI(((Main)
		// SwingUtilities.getRoot(this)));
		// ((Main) SwingUtilities.getRoot(this)).repaint();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);

		DefaultListModel<Language> defaultListModel = new DefaultListModel<Language>();
		for(Language lang: Language.values()){
			defaultListModel.addElement(lang);
			
		}
		list.setModel(defaultListModel);
		list.setCellRenderer(new LanguageRenderer());
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					if(internationalizer.initLanguage(list.getSelectedValue())){
						((Main) SwingUtilities.getRoot(Menu.this)).init();
					}
				}
			}

		});
		// list.setVisibleRowCount(1);
		list.setDropMode(DropMode.ON);
		return list;
	}

	/**
	 * Creates an object button.
	 * 
	 * @param text
	 *            what should be written on a particular button
	 * @return returns the created button's reference
	 */
	private JButton setButton(String text) {
		Dimension size = new Dimension(300, (325 - 70) / 4);
		JButton button = new JButton(text);
		button.setAlignmentX(LEFT_ALIGNMENT);
		button.setVisible(true);
		button.setFocusable(false);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		return button;
	}

	private JComboBox<String> setComboBox() {
		Dimension size = new Dimension(200, 25);
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setToolTipText(internationalizer.getString("designTooltip"));

		comboBox.addItem("Metal");
		comboBox.addItem("Nimbus");
		comboBox.addItem("CDE/Motif");
		comboBox.addItem("Windows");
		comboBox.addItem("Windows Classic");
		initializePlafs();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					try {
						UIManager.setLookAndFeel(plafs.get(comboBox.getSelectedItem()));
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
				}
				SwingUtilities.updateComponentTreeUI(((Main) SwingUtilities.getRoot(Menu.this)));
				((Main) SwingUtilities.getRoot(Menu.this)).repaint();
			}
		});

		comboBox.setAlignmentX(LEFT_ALIGNMENT);
		comboBox.setVisible(true);
		comboBox.setFocusable(false);
		comboBox.setMinimumSize(size);
		comboBox.setMaximumSize(size);
		comboBox.setPreferredSize(size);
		return comboBox;
	}

	private void initializePlafs() {
		plafs = new HashMap<>();
		for (LookAndFeelInfo plaf : UIManager.getInstalledLookAndFeels()) {
			plafs.put(plaf.getName(), plaf.getClassName());
		}

	}

	/**
	 * Inner class responsible for handling buttons' functionality.
	 *
	 */
	private class ActionHandler implements ActionListener {

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
