package tk.tfinnm.spaceflight.core;

import java.awt.*;
import javax.swing.*;

public class FlightPanel {
	
	static JFrame flightpanel;
	
	public static final int version = 0;
	
	public FlightPanel() {
		flightpanel = new JFrame("Spaceflight Sim | Flight Controls");
		flightpanel.setSize(1000, 500);
		flightpanel.setLocation(100, 100);
		flightpanel.setResizable(true);
		
		flightpanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel Launchcontent = new JPanel(new BorderLayout());

		JTabbedPane lc = new JTabbedPane();
		lc.addTab("Launch Controls", Launchcontent);
		flightpanel.setBackground(Color.white);
		flightpanel.setContentPane(lc);
		flightpanel.setVisible(true);
	}

}
