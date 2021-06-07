package tk.tfinnm.spaceflight.components;

public class Planet {

	private String n;
	private Double m;
	private Double r;
	
	public Planet(String name, Double mass, Double radius) {
		m = mass;
		r = radius;
		n = name;
	}
	
	public Double getRadius() {
		return r;
	}
	
	public Double getMass() {
		return m;
	}
	
	public String toString() {
		return n;
	}

}
