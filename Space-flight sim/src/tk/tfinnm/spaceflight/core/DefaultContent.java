package tk.tfinnm.spaceflight.core;

import tk.tfinnm.spaceflight.components.ContentPack;
import tk.tfinnm.spaceflight.components.Engine;
import tk.tfinnm.spaceflight.components.Mission;
import tk.tfinnm.spaceflight.components.Objective;
import tk.tfinnm.spaceflight.components.Planet;
import tk.tfinnm.spaceflight.components.Spacecraft;

public class DefaultContent extends ContentPack {

	@Override
	public Spacecraft[] getSpacecrafts() {
		Engine s2 = new Engine("Stage 2 Engine",5580000,366750,1438,13250,true,null);
		Engine s1 = new Engine("Stage 1 Engine",5580000,366750,1438,13250,false,s2);
		Engine pb = new Engine("Port Booster",13345000,498952,4158,90719);
		Engine sb = new Engine("Starboard Booster",13345000,498952,4158,90719);
		Engine siii = new Engine("S-IVB 3rd Stage Engine",1033100,109000,218,10000,true,null);
		Engine sii = new Engine("S-II 2nd Stage Engine",4900000,444000,1209,36000,true,siii);
		Engine si = new Engine("S-IC 1st Stage Engine",34000000,2169000,12910,131000,false,sii);
		Engine me = new Engine("Main Engine",7500000,95040,190,13800);
		Engine b1 = new Engine("Booster 1",8000000,89730,598,9750);
		Engine b2 = new Engine("Booster 2",8000000,89730,598,9750);
		Engine b3 = new Engine("Booster 3",8000000,89730,598,9750);
		Engine b4 = new Engine("Booster 4",8000000,89730,598,9750);
		Engine m2 = new Engine("Merlin Stage 2",934000,45718,115,3991,true,null);
		Engine m1 = new Engine("Merlin Stage 1",7600000,398540,2460,22226,false,m2);
		Engine m2h = new Engine("Merlin Stage 2",934000,107592,271,3991,true,null);
		Engine m1h = new Engine("Merlin Stage 1",8200000,398540,2131,22226,false,m2h);
		Engine mb1 = new Engine("Merlin Booster 1",8200000,398540,2587,22226);
		Engine mb2 = new Engine("Merlin Booster 2",8200000,398540,2587,22226);
		Engine s2h = new Engine("Stage 2 Engine",14000000,1179340,3103,117934,true,null);
		Engine s1h = new Engine("Stage 1 Engine",74000000,3401943,10308,117934,false,s2h);
		return new Spacecraft[]{new Spacecraft("NASA Space Shuttle", new Engine[]{s1, s2, pb, sb}),new Spacecraft("Saturn V", new Engine[]{si, sii, siii}),new Spacecraft("Soviet Energia", new Engine[]{me,b1,b2,b3,b4}),new Spacecraft("Falcon 9", new Engine[]{m1,m2}),new Spacecraft("Falcon Heavy", new Engine[]{m1h,m2h,mb1,mb2}),new Spacecraft("Space-X Starship", new Engine[]{s1h, s2h})};
	}

	@Override
	public Mission[] getMissions() {
		return new Mission[]{new Mission("Default Missions",new Objective[]{new Objective("Take Off!", 10, 0),new Objective("Reach Space", 122000, 0)}),new Mission("AcademiesHacks Missions",new Objective[]{new Objective("Deliver Potatoes to the ISS!", 330000, 1770),new Objective("To the Moon!", 384000000, 3540)})};
	}

	@Override
	public Planet[] getPlanets() {
		return new Planet[]{new Planet("Earth [Default]",(5.9724*(Math.pow(10, 24))),6378137.0),new Planet("Earth's Moon",(7.342*(Math.pow(10, 22))),1738100.0),new Planet("Mars",(6.417*(Math.pow(10, 23))),3389300.0)};
	}

}
