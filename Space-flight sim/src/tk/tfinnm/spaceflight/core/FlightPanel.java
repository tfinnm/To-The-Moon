package tk.tfinnm.spaceflight.core;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import tk.tfinnm.spaceflight.components.*;
import tk.tfinnm.spaceflight.helpers.DisabledPanel;

public class FlightPanel {
	
	static JFrame flightpanel;
	
	public static final int version = 0;
	
	public static DisabledPanel solid;
	
	public FlightPanel() {
		//Configure Flight Panel
		flightpanel = new JFrame("Spaceflight Sim | Flight Controls");
		flightpanel.setSize(1000, 500);
		flightpanel.setLocation(100, 100);
		flightpanel.setResizable(true);
		flightpanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		flightpanel.setBackground(Color.white);
		flightpanel.setVisible(true);
		
		//Create Screens
		JPanel Launchcontent = new JPanel(new BorderLayout());
		JPanel fuelcontent = new JPanel(new BorderLayout());
		JPanel lifesupportcontent = new JPanel(new BorderLayout());
		JPanel reactorcontent = new JPanel(new BorderLayout());
		JPanel payloadcontent = new JPanel(new BorderLayout());
		
		//Create Menus
		JMenu quickMenu = new JMenu("Quick Controls");
		JMenu SummaryMenu = new JMenu("Mission Summary");
		JMenu objectiveMenu = new JMenu("Mission Objectives");
		JMenu gameMenu = new JMenu("Game Options");

		//Configure Flight Panel Content
		JTabbedPane lc = new JTabbedPane();
		flightpanel.setContentPane(lc);
		JMenuBar menu = new JMenuBar();
		flightpanel.setJMenuBar(menu);
		
		lc.addTab("Launch Controls", Launchcontent);
		lc.addTab("Fuel", fuelcontent);
		lc.addTab("Life Support Controls", lifesupportcontent);
		lc.addTab("Reactor & Power Controls", reactorcontent);
		lc.addTab("Payload", payloadcontent);
		
		menu.add(quickMenu);
		menu.add(SummaryMenu);
		menu.add(objectiveMenu);
		menu.add(gameMenu);

		//create launch control
		SolidFuel sf = new SolidFuel();
		solid = new DisabledPanel(sf);
		solid.setEnabled(false);
		
		Launchcontent.add(new LaunchConfirm(), BorderLayout.SOUTH);
		Launchcontent.add(solid, BorderLayout.WEST);
		
		
		
		
		//main loop
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(sf.getThrust(0));
				System.out.println(sf.getMass());
			}
		});
		timer.setRepeats(true);
		timer.start();
		timer.setRepeats(true);
	}

}
