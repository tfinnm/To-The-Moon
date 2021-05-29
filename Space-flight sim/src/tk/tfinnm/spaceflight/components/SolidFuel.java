package tk.tfinnm.spaceflight.components;

import java.awt.*;

import javax.swing.*;

public class SolidFuel extends JPanel {

	private static final long serialVersionUID = 1L;

	public SolidFuel() {
		setBorder(BorderFactory.createTitledBorder("Solid Fuel Engines"));
		setLayout(new GridLayout(0,1));
		add(new JButton("Ignite Main Engine"));
		add(new JButton("Ignite Left Booster"));
		add(new JButton("Ignite Right Booster"));
	}

}
