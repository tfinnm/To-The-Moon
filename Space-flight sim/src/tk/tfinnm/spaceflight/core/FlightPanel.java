package tk.tfinnm.spaceflight.core;

import java.awt.*;
import javax.swing.*;

import tk.tfinnm.spaceflight.components.*;

public class FlightPanel {
	
	static JFrame flightpanel;
	
	public static final int version = 0;
	
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
		menu.add(gameMenu);

		//create launch control
		Launchcontent.add(new LaunchConfirm(), BorderLayout.SOUTH);
		Launchcontent.add(new SolidFuel(), BorderLayout.WEST);
	}

}
