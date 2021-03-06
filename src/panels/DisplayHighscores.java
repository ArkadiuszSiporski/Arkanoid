package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import db.dao.PlayerDao;
import db.dao.PlayerDaoImpl;
import db.model.Player;
import exceptions.NoRecordsException;
import internationalization.Internationalizer;
import main.Main;

/**
 * Class that implements panel in which we can can see the highest scores read
 * from text file.
 */
public class DisplayHighscores extends JPanel {

	private static final Logger LOG = Logger.getLogger(DisplayHighscores.class);
	private static final Color GOLD = new Color(255, 215, 0);
	private static final Color SILVER = new Color(192, 192, 192);
	private static final Color BRONZE = new Color(160, 82, 45);
	private static final boolean BACK = true;
	private static final boolean EXIT = false;
	private Initialization initialization = Initialization.INPROGRESS;
	private PlayerDao playerDao = new PlayerDaoImpl();
	private List<Player> players = new ArrayList<>();
	private Internationalizer internationalizer = Internationalizer.getInstance();

	private enum Initialization {
		SUCCESS, FAILURE, INPROGRESS;
	}

	/**
	 * Constructor that sets up Key Bindings.
	 */
	public DisplayHighscores() {
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("TAB"), "back");
		getActionMap().put("back", this.new Controls(BACK));
		getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
		getActionMap().put("exit", this.new Controls(EXIT));

	}

	public void init() {
		new RecordFetcher().start();
	}

	private class RecordFetcher extends Thread {
		public void run() {
			try {
				initialization = Initialization.INPROGRESS;
				players = playerDao.getTop();
				initialization = Initialization.SUCCESS;

			} catch (Exception e) {
				LOG.error("Failed to connect with the database", e);
				initialization = Initialization.FAILURE;

			}
			repaint();
		}
	}

	/**
	 * Paints highest scores on the panel.
	 * 
	 * @param g
	 *            the Graphics context in which to paint
	 */
	public void paintComponent(Graphics g) {

		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 850, 900);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		if (Initialization.SUCCESS.equals(initialization)) {

			int i = 1;
			String output = internationalizer.getString("noRecords");

			// reading the file
			try {
				if (players.size() <= 0) {
					throw new NoRecordsException();
				}

				for (Player player : players) {
					switch (i) {
					case 1:
						g.setColor(GOLD);
						break;
					case 2:
						g.setColor(SILVER);
						break;
					case 3:
						g.setColor(BRONZE);
						break;
					default:
						g.setColor(Color.WHITE);
						break;
					}
					// drawing each record separately
					output = player.getNick() + " " + player.getScore();
					g.drawString(i + ". " + output, 0, i * 70);
					i++;
				}

			} catch (Exception e) {
				LOG.error("No records found in the database", e);
			}
			if (i == 1) {
				(SwingUtilities.getRoot(DisplayHighscores.this)).setBounds(140, 0, 850, 150);
				g.setFont(new Font("Serif", Font.BOLD, 60));
				g.setColor(Color.RED);
				g.drawString(output, 0, i * 70);
			} else {
				(SwingUtilities.getRoot(DisplayHighscores.this)).setBounds(400, 0, 400, i * 80);
			}
		} else if (Initialization.INPROGRESS.equals(initialization)) {
			g.setFont(new Font("Serif", Font.BOLD, 60));
			g.setColor(Color.GREEN);
			g.drawString(internationalizer.getString("loading"), 0, 70);
		} else {
			(SwingUtilities.getRoot(DisplayHighscores.this)).setBounds(400, 0, 800, 140);
			g.setFont(new Font("Serif", Font.BOLD, 50));
			g.setColor(Color.RED);
			g.drawString(internationalizer.getString("dbFailure"), 0, 70);
		}
	}

	/**
	 * Inner class responsible for handling Key Bindings.
	 *
	 */
	private class Controls extends AbstractAction {
		boolean action;

		Controls(boolean action) {
			this.action = action;
		}

		public void actionPerformed(ActionEvent e) {
			Main main = ((Main) SwingUtilities.getRoot(DisplayHighscores.this));
			// back to menu
			if (action) {
				initialization = Initialization.FAILURE;
				main.toMenu();
				main.remove(DisplayHighscores.this);
			}
			// close the window
			else {
				main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
			}
		}
	}
}
