package tk.tfinnm.spaceflight.components;

import java.awt.*;

import javax.swing.*;

public class SolidFuel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Engine[] engines;
	
	public SolidFuel(Spacecraft spacecraft) {
		engines = spacecraft.getEngines();
		setBorder(BorderFactory.createTitledBorder("Solid Fuel Engines"));
		setLayout(new GridLayout(0,1));
		for (Engine e: engines) {
			add(e.ign);
			add(e.rel);
		}
	}
	
	
	public double getThrust(int alt) {
		int thrust = 0;
		for (Engine e: engines) {
			thrust += e.getThrust();
		}
		//return ((Math.exp(-(alt/1000.0)/10.4))*thrust);
		//return ((1/Math.exp(-(alt/1000.0)/10.4))*thrust);
		return thrust;
	}

	public int getMass() {
		int mass = 0;
		for (Engine e: engines) {
			mass += e.getMass();
		}
		return mass;
	}
}
