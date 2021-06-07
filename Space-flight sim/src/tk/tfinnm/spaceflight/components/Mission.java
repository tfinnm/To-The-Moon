package tk.tfinnm.spaceflight.components;

public class Mission {

	private Objective[] o;
	private String n;
	
	public Mission(String name, Objective[] objectives) {
		o = objectives;
		n = name;
	}
	
	public Objective[] getObjectives() {
		return o;
	}
	
	public String toString() {
		return n;
	}

}
