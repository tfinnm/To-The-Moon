package tk.tfinnm.spaceflight.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import tk.tfinnm.spaceflight.core.*;

public class Crew extends JPanel{
	
	public static enum crewType {
		Empty,
		Captain,	//automates engine related stuff
		Technician, //automates lifesupport and allows reactor to restart
	//	Scientist,	//allows user to do expiriments to complete objectives
	//	Astronaut,	//can do spacewalks to complete objectives
	//	Colonist,	//used for colonization objectives
	//	Engineer,	//used for colonization objectives
	//	TourGuide,	//allows tourists to go on spacewalks
	//	Tourist		//can go on spacewalks (if there is a guide) to complete objectives
	}
	
	public static double getMass() {
		double mass = 0.0;
		for (Crew c: crew) {
			if (!c.type.equals(crewType.Empty)) {
				mass += 226.9;
			}
		}
		return mass;
	}
	
	public static ArrayList<Crew> crew = new ArrayList<Crew>();
	
	public crewType type = crewType.Empty;

	private static final long serialVersionUID = 1L;
	
	public JComboBox<crewType> select;
	
	public Crew() {
		crew.add(this);
		this.setLayout(new BorderLayout());
		select = new JComboBox<crewType>(crewType.values());
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = (crewType) select.getSelectedItem();
				if (type.equals(crewType.Captain)) {
					FlightPanel.autojettison.setEnabled(true);
					FlightPanel.autoignite.setEnabled(true);
				} else {
					boolean hasCapt = false;
					for (Crew c: crew) {
						if (c.type.equals(crewType.Captain)) {
							hasCapt = true;
						}
					}
					if (!hasCapt) {
						FlightPanel.autoignite.setSelected(false);
						FlightPanel.autoignite.setEnabled(false);
						FlightPanel.autojettison.setSelected(false);
						FlightPanel.autojettison.setEnabled(false);
					}
				}
				if (type.equals(crewType.Technician)) {
					FlightPanel.autoscram.setEnabled(true);
					FlightPanel.autoadjustreactor.setEnabled(true);
					FlightPanel.autoadjusttemp.setEnabled(true);
					Reactor.control.setEnabled(true);
					Reactor.SCRAM.setEnabled(true);
				} else {
					boolean hasTech = false;
					for (Crew c: crew) {
						if (c.type.equals(crewType.Technician)) {
							hasTech = true;
						}
					}
					if (!hasTech) {
						FlightPanel.autoscram.setSelected(false);
						FlightPanel.autoscram.setEnabled(false);
						FlightPanel.autoadjustreactor.setSelected(false);
						FlightPanel.autoadjustreactor.setEnabled(false);
						FlightPanel.autoadjusttemp.setSelected(false);
						FlightPanel.autoadjusttemp.setEnabled(false);
					}
				}
			}
		});
		add(select, BorderLayout.NORTH);
	}
	

}
