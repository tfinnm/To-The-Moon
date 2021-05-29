package tk.tfinnm.spaceflight.core;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
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
		flightpanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		//add objectives
		objectiveMenu.add(new objective("Take Off!", 10, 0));

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
		JTabbedPane graphs = new JTabbedPane();
		Chart2D forceChart = new Chart2D();
		Chart2D speedChart = new Chart2D();
		Chart2D accelChart = new Chart2D();
		Chart2D altChart = new Chart2D();
		
		thrust = new Trace2DLtd(600);
		thrust.setColor(Color.green);
		thrust.setName("Thrust Force");
		grav = new Trace2DLtd(600);
		grav.setColor(Color.red);
		grav.setName("Gravity Force");
		drag = new Trace2DLtd(600);
		drag.setColor(Color.black);
		drag.setName("Air Resitance Force");
		velo = new Trace2DLtd(600);
		velo.setColor(Color.black);
		velo.setName("Velocity");
		accel = new Trace2DLtd(600);
		accel.setColor(Color.black);
		accel.setName("Acceleration");
		alti = new Trace2DLtd(600);
		alti.setColor(Color.black);
		alti.setName("Altitude");
		
		graphs.addTab("Force",forceChart);
		graphs.addTab("Velocity",speedChart);
		graphs.addTab("Acceleration", accelChart);
		graphs.addTab("Altitude",altChart);
		
		forceChart.addTrace(thrust);
		forceChart.addTrace(grav);
		forceChart.addTrace(drag);
		speedChart.addTrace(velo);
		accelChart.addTrace(accel);
		altChart.addTrace(alti);
		
		Launchcontent.add(new LaunchConfirm(), BorderLayout.SOUTH);
		Launchcontent.add(solid, BorderLayout.WEST);
		Launchcontent.add(graphs, BorderLayout.CENTER);
		
		
		
		//main loop
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alt += getVelocity(sf.getMass(),sf.getThrust((int) alt));
				if (alt < 0) {
					alt = 0;
				}
				alti.addPoint(alti.getMaxX()+1, alt);
				//System.out.println(alt);
				//System.out.println(sf.getThrust(0));
				//System.out.println(sf.getMass());
			}
		});
		timer.setRepeats(true);
		timer.start();
		timer.setRepeats(true);
	}
	private Trace2DLtd thrust;
	private Trace2DLtd grav;
	private Trace2DLtd drag;
	private Trace2DLtd accel;
	private Trace2DLtd velo;
	private Trace2DLtd alti;
	public static double alt = 0;
	private double velocity = 0;
	private double getVelocity(double mass, double thrust) {
		this.thrust.addPoint(this.thrust.getMaxX()+1, thrust);
		velocity += getAcceleration(mass, thrust);
		if (!(alt > 0)) {
			if (velocity < 0) {
				velocity = 0;
			}
		}
		this.velo.addPoint(this.velo.getMaxX()+1, velocity);
		return velocity;
	}
	private double getAcceleration(double mass, double thrust) {
		double grav = calculateGravity(mass);
		double netforce = thrust-grav-0;
		double acceleration = netforce/mass;
		this.accel.addPoint(this.accel.getMaxX()+1, acceleration);
		this.grav.addPoint(this.grav.getMaxX()+1, grav);
		return acceleration;
	}
	private double calculateGravity(double mass) {
		double r = 6378137+alt;
		double me= (5.9724*(Math.pow(10, 24)));
		double ugc = (6.67430*(Math.pow(10, -11)));
		double top =me*mass*ugc;
		return top/(Math.pow(r, 2));
	}

}
