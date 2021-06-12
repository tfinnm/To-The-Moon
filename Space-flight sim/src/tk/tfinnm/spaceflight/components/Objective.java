package tk.tfinnm.spaceflight.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.Timer;

import tk.tfinnm.spaceflight.core.FlightPanel;

public class Objective extends JCheckBoxMenuItem{

	private static final long serialVersionUID = 1L;
	
	private int minAlt;
	private int minPotato;
	private String name;
	private boolean complete = false;
	public Objective(String name, int alt, int potatoes) {
		super(name+" ["+alt+"m, "+potatoes+" potatoes]");
		this.setEnabled(false);
		this.minAlt = alt;
		this.minPotato = potatoes;
		this.name = name;
	}
	public void start() {
		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkIfComplete((int) FlightPanel.alt,Potatoes.ammount.getValue());
			}
		});
		timer.setRepeats(true);
		timer.start();
		timer.setRepeats(true);
	}
	public void checkIfComplete(int alt, int potato) {
		if (alt >= minAlt && potato >= minPotato) {
			if (!complete) {
				this.setSelected(true);
				FlightPanel.logEvent("Completed Objective: "+name);
				complete = true;
			}
		}
	}

}
