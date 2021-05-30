package tk.tfinnm.spaceflight.components;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JProgressBar;

public class engine {
	public String name;
	public int thrust;
	public int fuelWeight;
	public int maxFuel;
	public int burnRate;
	public int baseWeight;
	public engine req;
	public JButton ign;
	public JButton rel;
	public JProgressBar fuel;
	private Color ogColor;
	private boolean burn = false;
	public engine(String name, int thrust, int fweight, int burnrate, int weight) {
		this.name = name;
		this.thrust = thrust;
		this.fuelWeight = fweight;
		this.maxFuel = fweight;
		this.burnRate = burnrate;
		this.baseWeight = weight;
		ign = new JButton("Ignite "+name);
		rel = new JButton("Detatch "+name);
		ogColor = rel.getBackground();
		ign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rel.setEnabled(true);
				ign.setEnabled(false);
				burn = true;
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
			}
		});
		rel.setEnabled(false);
		fuel = new JProgressBar();
		fuel.setStringPainted(true);
		fuel.setString(name+" Fuel Remaining: "+fweight+"/"+fweight+" (Kg)");
		fuel.setMaximum(fweight);
		fuel.setValue(fweight);
	}
	public engine(String name, int thrust, int fweight, int burnrate, int weight, boolean pre, engine e) {
		this.name = name;
		this.thrust = thrust;
		this.fuelWeight = fweight;
		this.maxFuel = fweight;
		this.burnRate = burnrate;
		this.baseWeight = weight;
		this.req = e;
		ign = new JButton("Ignite "+name);
		rel = new JButton("Detatch "+name);
		ogColor = rel.getBackground();
		ign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rel.setEnabled(true);
				ign.setEnabled(false);
				burn = true;
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
				}
				rel.setBackground(ogColor);
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
				fuel.setString(name+" Fuel Remaining: "+fuelWeight+"/"+maxFuel+" (Kg)");
				fuel.setValue(fuelWeight);
				return thrust;
			} else {
				rel.setBackground(Color.red);
			}
		}
		return 0;
	}
}
