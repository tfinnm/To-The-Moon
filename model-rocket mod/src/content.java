import javax.swing.LookAndFeel;

import tk.tfinnm.spaceflight.components.ContentPack;
import tk.tfinnm.spaceflight.components.Engine;
import tk.tfinnm.spaceflight.components.Mission;
import tk.tfinnm.spaceflight.components.Planet;
import tk.tfinnm.spaceflight.components.Spacecraft;

public class content extends ContentPack {

	@Override
	public LookAndFeel[] getLAFs() {
		return null;
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
		Engine ea83 = new Engine("Estes A8-3",10700,14,14,41);
		Engine eb64 = new Engine("Estes C6-4",12100,23,23,40);
		Engine eb64b = new Engine("Estes C6-4",12100,23,23,40);
		Engine ec65 = new Engine("Estes C6-5",15300,43,22,42);
		Engine ec60 = new Engine("Estes C6-0",15300,43,22,33,true,null);
		Engine ed120 = new Engine("Estes D12-0",32900,84,42,59,true,ec60);
		Engine ec67 = new Engine("Estes C6-7",15300,43,22,42,false,ed120);
		
		return new Spacecraft[]{
				new Spacecraft("Generic Small Model Rocket", 0, new Engine[]{ea83}),
				new Spacecraft("Generic Medium Model Rocket", 0, new Engine[]{eb64}),
				new Spacecraft("Generic Large Model Rocket", 0, new Engine[]{ec65}),
				new Spacecraft("Estes SIDEKICK Model Rocket", 0, new Engine[] {eb64,eb64b}),
				new Spacecraft("Estes COMANCHE-3 Model Rocket", 0, new Engine[] {ec67,ed120,ec60})
		};
	}

}
