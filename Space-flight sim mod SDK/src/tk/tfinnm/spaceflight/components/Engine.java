package tk.tfinnm.spaceflight.components;

import javax.swing.JButton;
import javax.swing.JProgressBar;


public class Engine {
	public String name;
	public int thrust;
	public int fuelWeight;
	public int maxFuel;
	public int burnRate;
	public int baseWeight;
	public Engine req;
	public JButton ign;
	public JButton rel;
	public JProgressBar fuel;
	public Engine(String name, int thrust, int fweight, int burnrate, int weight) {
	}
	public Engine(String name, int thrust, int fweight, int burnrate, int weight, boolean pre, Engine e) {
	}
	
	public int getMass() {
		return 0;
	}
	public int getThrust() {
		return 0;
	}
}
