package tk.tfinnm.spaceflight.components;

public class Spacecraft {

	private Engine[] e;
	private String n;
	
	public Spacecraft(String name, Engine[] engines) {
		e = engines;
		n = name;
	}
	
	public Engine[] getEngines() {
		return e;
	}
	
	public String toString() {
		return n;
	}

}
