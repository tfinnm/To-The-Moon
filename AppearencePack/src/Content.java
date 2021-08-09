

import javax.swing.LookAndFeel;

import tk.tfinnm.spaceflight.components.ContentPack;
import tk.tfinnm.spaceflight.components.Mission;
import tk.tfinnm.spaceflight.components.Planet;
import tk.tfinnm.spaceflight.components.Spacecraft;
import com.bulenkov.darcula.DarculaLaf;

public class Content extends ContentPack {

	@Override
	public LookAndFeel[] getLAFs() {
		LookAndFeel[] arr = {new DarculaLaf(),};
		return arr;
	}

	@Override
	public Mission[] getMissions() {
		return null;
	}

	@Override
	public Planet[] getPlanets() {
		return null;
	}

	@Override
	public Spacecraft[] getSpacecrafts() {
		return null;
	}

}
