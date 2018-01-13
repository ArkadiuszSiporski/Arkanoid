package utils;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import panels.ArcanoidMenu;
import utils.Internationalizer.Language;

public class LanguageRenderer extends JLabel implements ListCellRenderer<Language> {

	@Override
	public Component getListCellRendererComponent(JList<? extends Language> list, Language value, int index,
			boolean isSelected, boolean cellHasFocus) {
		String stringValue = value.toString().toLowerCase();
		setIcon(new ImageIcon( ArcanoidMenu.class.getResource("/resources/" + stringValue + "Flag.png")));
		setText(stringValue.substring(0, 1).toUpperCase() + stringValue.substring(1));
		return this;
	}
}
