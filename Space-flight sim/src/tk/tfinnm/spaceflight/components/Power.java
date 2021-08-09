package tk.tfinnm.spaceflight.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import tk.tfinnm.spaceflight.core.FlightPanel;

public class Power extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static int Input = 0;
	public static int Output = 0;
	public static int storage = 0;
	public static JSlider control;
	public static Timer timer;
	
	public Power() {
		setBorder(BorderFactory.createTitledBorder("Power"));
		setLayout(new GridLayout(0,1));
		control = new JSlider(JSlider.HORIZONTAL,0,15,0);
		control.setPaintLabels(true);
		control.setPaintTicks(true);
		control.createStandardLabels(1);
		control.setMajorTickSpacing(1);
		add(control);
		JProgressBar Out = new JProgressBar();
		Out.setMaximum(10);
		Out.setValue(Output);
		Out.setString("Consumption: "+Output*60+"watts");
		Out.setStringPainted(true);
		JProgressBar In = new JProgressBar();
		In.setMaximum(10);
		In.setValue(Input);
		In.setString("Production: "+Input*60+"watts");
		In.setStringPainted(true);
		JProgressBar Store = new JProgressBar();
		Store.setMaximum(control.getValue());
		Store.setValue(storage);
		Store.setString("Stored: "+storage+"/"+control.getValue()+"Wh");
		Store.setStringPainted(true);
		add(Out);
		add(In);
		add(Store);
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Store.setMaximum(control.getValue());
				if (LaunchConfirm.Status==LaunchConfirm.status.lanched) {
					Input = Reactor.output;
					Output = 1;
					if (Oxygen.status) {
						Output += 3;
					}
					if (Heat.status) {
						Output += 2;
						if (Heat.temp == Heat.control.getValue()) {
							Output += 1;
						} else if (Heat.temp < Heat.control.getValue()) {
								Output += 3;
						}
					}
				}
				if (FlightPanel.autoadjustreactor.isSelected()) {
					if (Input-10 >= Output) {
						Reactor.control.setValue(Reactor.control.getValue()-1);
					} else if (Output > Input && Reactor.control.getValue() < 10) {
						if ((Reactor.temperature+Heat.temp) == 100) {
							if (Heat.temp > 10 && FlightPanel.autoadjusttemp.isSelected()) {
								Heat.control.setValue(10);
							}
						} else {
							Reactor.control.setValue(Reactor.control.getValue()+1);
						}
					}
				}
				storage += Input-Output;
				if (storage > (control.getValue())) {
					storage = control.getValue();
				}
				if (LaunchConfirm.Status != LaunchConfirm.status.ready) {
					FlightPanel.lifeDataLog.add(FlightPanel.formatTime((int)FlightPanel.alti.getMaxX())+","+Input*60+","+Output*60+","+storage+","+Heat.temp+","+Oxygen.oxygen+","+Reactor.temperature+Heat.temp+","+Reactor.radiation);
				}
				boolean oop = false;
				if (storage < 0) {
					oop = true;
					storage = 0;
				}
				
				Out.setString("Consumption: "+Output*60+"watts");
				In.setString("Production: "+Input*60+"watts");
				Store.setString("Stored: "+storage+"/"+control.getValue()+"Wh");
				
				if (oop) {
					if (!FlightPanel.lab) {
						FlightPanel.logEvent("Power Failure");
						JOptionPane.showMessageDialog(null, "Power Failure; Mission Failed.", "Mission Failed!", JOptionPane.ERROR_MESSAGE);
						FlightPanel.debrief();
					}
				}
			}
		});
		timer.setRepeats(true);
		timer.start();
		timer.setRepeats(true);
	}
}
