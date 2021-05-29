package tk.tfinnm.spaceflight.components;

import java.awt.*;

import javax.swing.*;

public class SolidFuel extends JPanel {

	private static final long serialVersionUID = 1L;

	engine s2 = new engine("State 2",5580000,366750,1438,26500,true,null);
	engine s1 = new engine("Stage 1",5580000,366750,1438,26500,false,s2);
	engine pb = new engine("Port Booster",13345000,498952,4158,90719);
	engine sb = new engine("Starboard Booster",13345000,498952,4158,90719);
	engine[] engines = {s1, s2, pb, sb};
	
	public SolidFuel() {
		setEnabled(false);
		setBorder(BorderFactory.createTitledBorder("Solid Fuel Engines"));
		setLayout(new GridLayout(0,1));
		for (engine e: engines) {
			add(e.ign);
			add(e.rel);
		}
	}
	
	
	public double getThrust(int alt) {
		int thrust = 0;
		for (engine e: engines) {
			thrust += e.getThrust();
		}
		return (Math.exp(-(alt/1000)/10.4)*thrust);
	}

	public int getMass() {
		int mass = 0;
		for (engine e: engines) {
			mass += e.getMass();
		}
		return mass;
	}
}
