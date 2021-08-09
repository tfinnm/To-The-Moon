package tk.tfinnm.spaceflight.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import tk.tfinnm.spaceflight.components.Crew.crewType;
import tk.tfinnm.spaceflight.core.FlightPanel;

public class Reactor extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static int output = 0;
	public static int temperature = 0;
	public static int radiation = 1;
	public static Timer timer;
	
	static JSlider control;
	static JButton SCRAM;
	
	public Reactor() {
		setBorder(BorderFactory.createTitledBorder("Reactor"));
		setLayout(new GridLayout(0,1));
		SCRAM = new JButton("SCRAM/Reactor Trip");
		JProgressBar Out = new JProgressBar();
		Out.setMaximum(10);
		Out.setValue(output);
		Out.setString("Output: "+(output*60)+"watts");
		Out.setStringPainted(true);
		JProgressBar Temp = new JProgressBar();
		Temp.setMaximum(10);
		Temp.setValue(output);
		Temp.setString("Temp: "+temperature+"C [Safe <100C]");
		Temp.setStringPainted(true);
		JProgressBar Rad = new JProgressBar();
		Rad.setMaximum(400);
		Rad.setValue(radiation);
		Rad.setString("Radiation: "+radiation+"mSv");
		Rad.setStringPainted(true);
		control = new JSlider(JSlider.HORIZONTAL,0,10,0);
		control.setPaintLabels(true);
		control.setPaintTicks(true);
		control.createStandardLabels(1);
		control.setMajorTickSpacing(1);
		SCRAM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FlightPanel.logEvent("Reactor SCRAMed");
				temperature = 0;
				output = 0;
				control.setValue(0);
				boolean hasTech = false;
				for (Crew c: Crew.crew) {
					if (c.type.equals(crewType.Technician)) {
						hasTech = true;
					}
				}
				if (!hasTech) {
					control.setEnabled(false);
					SCRAM.setEnabled(false);
				}
			}
		});
		add(control);
		add(Out);
		add(Temp);
		add(Rad);
		add(SCRAM);
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (temperature < control.getValue()*10) {
					temperature += 10;
				} else if (temperature > control.getValue()*10) {
					temperature -= 10;
				}
				output = temperature/10;
				if ((temperature+Heat.temp) > 100) {
					radiation += 50*(((temperature+Heat.temp)-100)/10);
					if (FlightPanel.autoscram.isSelected()) {
						SCRAM.doClick();
					}
				} else {
					if (radiation > 1) {
						radiation--;
					}
				}
				if (radiation >= 400) {
					FlightPanel.logEvent("Reactor Meltdown [Temp: "+(temperature+Heat.temp)+"; Rad: "+radiation+"mSv]");
					JOptionPane.showMessageDialog(null, "Total Meltdown; Mission Failed.", "Mission Failed!", JOptionPane.ERROR_MESSAGE);
					FlightPanel.debrief();
				}
				Out.setString("Output: "+output*60+"watts");
				Temp.setString("Temp: "+(temperature+Heat.temp)+"C [Safe <100C]");
				Rad.setString("Radiation: "+radiation+"mSv");
			}
		});
		timer.setRepeats(true);
		timer.start();
		timer.setRepeats(true);
	}
}
