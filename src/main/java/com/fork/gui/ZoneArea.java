package com.fork.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class ZoneArea extends JPanel {
	private ImageIcon image;

	/**
	 * Create the panel.
	 */
	public ZoneArea() {
		image = new ImageIcon("Workgroup-Switch-icon.png");
	}

	public void initialize() {
		setBounds(177, 84, 438, 214);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
	}

	public JLabel addImage(String name, String ip) {
		JLabel label = new JLabel(name + "-" + ip, image, SwingConstants.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		add(label);
		revalidate();
		repaint();
		return label;
	}

	public void removeAllComponents() {
		removeAll();
		revalidate();
		repaint();
	}

}
