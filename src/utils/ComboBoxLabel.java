package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComboBox;
import javax.swing.JComponent;

public class ComboBoxLabel extends JComponent {
	private Font font = new Font("Serif", Font.BOLD, 40);
	private Color color = new Color(0x1a, 0xc6, 0xff);
	private String text;
	private JComboBox<?> comboBox;
	int x;
	int y;
	int width;
	int height;

	public ComboBoxLabel() {
		init();
	}

	public ComboBoxLabel(String text) {
		init();
		this.text = text;

	}

	private void init() {
		x = 20;
		y = 20;
		width = 20;
		height = 20;
		setOpaque(false);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(20, 20);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		if (comboBox != null) {
			((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics.setColor(color);
			graphics.setFont(font);
			width = graphics.getFontMetrics().stringWidth(text);
			height = graphics.getFontMetrics().getHeight();
			x = comboBox.getX() + comboBox.getWidth() / 2 - width / 2;
			y = comboBox.getY() - height;
			graphics.drawString(text, x, y - 15);
		}

	}

	public JComboBox<?> getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox<?> comboBox) {
		this.comboBox = comboBox;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
