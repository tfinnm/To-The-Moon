package tk.tfinnm.spaceflight.components;

public class Spacecraft {

	private Engine[] e;
	private String n;
	private int cs;
	
	public Spacecraft(String name, int CrewSize, Engine[] engines) {
		e = engines;
		n = name;
		cs = CrewSize;
	}
	
	public Engine[] getEngines() {
		return e;
	}
	
	public String toString() {
		return n;
	}
	
	public int getCrewSize() {
		return cs;
	}

}
