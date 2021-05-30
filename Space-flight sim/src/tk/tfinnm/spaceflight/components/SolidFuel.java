package tk.tfinnm.spaceflight.components;

import java.awt.*;

import javax.swing.*;

public class SolidFuel extends JPanel {

	private static final long serialVersionUID = 1L;

	engine s2 = new engine("Stage 2 Engine",5580000,366750,1438,13250,true,null);
	engine s1 = new engine("Stage 1 Engine",5580000,366750,1438,13250,false,s2);
	engine pb = new engine("Port Booster",13345000,498952,4158,90719);
	engine sb = new engine("Starboard Booster",13345000,498952,4158,90719);
	public engine[] engines = {s1, s2, pb, sb};
	
	public SolidFuel() {
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
		//return ((Math.exp(-(alt/1000.0)/10.4))*thrust);
		//return ((1/Math.exp(-(alt/1000.0)/10.4))*thrust);
		return thrust;
	}

	public int getMass() {
		int mass = 0;
		for (engine e: engines) {
			mass += e.getMass();
		}
		return mass;
	}
}
