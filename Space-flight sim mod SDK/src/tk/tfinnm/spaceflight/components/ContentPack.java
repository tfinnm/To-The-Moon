package tk.tfinnm.spaceflight.components;

import javax.swing.LookAndFeel;

public abstract class ContentPack {

	public abstract Spacecraft[] getSpacecrafts();
	public abstract Mission[] getMissions();
	public abstract Planet[] getPlanets();
	public abstract LookAndFeel[] getLAFs();
	
}
