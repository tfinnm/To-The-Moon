package tk.tfinnm.spaceflight.components;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import tk.tfinnm.spaceflight.core.FlightPanel;

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
	private Color ogColor;
	private boolean burn = false;
	public Engine(String name, int thrust, int fweight, int burnrate, int weight) {
		this.name = name;
		this.thrust = thrust;
		this.fuelWeight = fweight;
		this.maxFuel = fweight;
		this.burnRate = burnrate;
		this.baseWeight = weight;
		ign = new JButton("Ignite "+name);
		rel = new JButton("Jettison "+name);
		ogColor = rel.getBackground();
		ign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rel.setEnabled(true);
				ign.setEnabled(false);
				burn = true;
				FlightPanel.logEvent(name+" Ignited");
			}
		});
		rel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rel.setEnabled(false);
				fuelWeight = 0;
				baseWeight = 0;
				burn = false;
				rel.setBackground(ogColor);
				FlightPanel.logEvent(name+" Jettisoned");
			}
		});
		rel.setEnabled(false);
		fuel = new JProgressBar();
		fuel.setStringPainted(true);
		fuel.setString(name+" Fuel Remaining: "+fweight+"/"+fweight+" (Kg)");
		fuel.setMaximum(fweight);
		fuel.setValue(fweight);
	}
	public Engine(String name, int thrust, int fweight, int burnrate, int weight, boolean pre, Engine e) {
		this.name = name;
		this.thrust = thrust;
		this.fuelWeight = fweight;
		this.maxFuel = fweight;
		this.burnRate = burnrate;
		this.baseWeight = weight;
		this.req = e;
		ign = new JButton("Ignite "+name);
		rel = new JButton("Jettison "+name);
		ogColor = rel.getBackground();
		ign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rel.setEnabled(true);
				ign.setEnabled(false);
				burn = true;
				FlightPanel.logEvent(name+" Ignited");
			}
		});
		rel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fuelWeight = 0;
				baseWeight = 0;
				burn = false;
				rel.setEnabled(false);
				if (req != null) {
					req.ign.setEnabled(true);
					if (FlightPanel.autoignite.isSelected()) {
						req.ign.doClick();
					}
				}
				rel.setBackground(ogColor);
				FlightPanel.logEvent(name+" Jettisoned");
			}
		});
		if (pre) ign.setEnabled(false);
		rel.setEnabled(false);
		fuel = new JProgressBar();
		fuel.setStringPainted(true);
		fuel.setString(name+" Fuel Remaining: "+fweight+"/"+fweight+" (Kg)");
		fuel.setMaximum(fweight);
		fuel.setValue(fweight);
	}
	
	public int getMass() {
		return baseWeight+fuelWeight;
	}
	public int getThrust() {
		if (burn) {
			if (fuelWeight > 0) {
				fuelWeight-=burnRate;
				if (fuelWeight < 0) fuelWeight = 0;
				fuel.setString(name+" Fuel Remaining: "+fuelWeight+"/"+maxFuel+" (Kg)");
				fuel.setValue(fuelWeight);
				return thrust;
			} else {
				rel.setBackground(Color.red);
				if (FlightPanel.autojettison.isSelected()) {
					rel.doClick();
				}
			}
		}
		return 0;
	}
}
