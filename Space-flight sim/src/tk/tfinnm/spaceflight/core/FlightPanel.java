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
	
	public static JFrame flightpanel;
	
	public static final int version = 0;
	
	public static DisabledPanel solid;
	public static DisabledPanel payload;
	public static JMenuItem ignite;
	public static JCheckBoxMenuItem autojettison;
	public static JCheckBoxMenuItem autoignite;
	
	public static int maxAlt = 0;
	public static int maxVel = 0;
	public static int maxAcc = 0;
	
	private JMenuItem maxAlti = new JMenuItem("Maximum ALtitude: "+maxAlt+"m");
	private JMenuItem maxVelo = new JMenuItem("Maximum Velocity: "+maxVel+"m/s");
	private JMenuItem maxAccel = new JMenuItem("Maximum Acceleration: "+maxAcc+"m/s/s");
	private JMenuItem time = new JMenuItem("Mission Length: 0s");
	
	public FlightPanel() {
		maxAlt = 0;
		maxVel = 0;
		maxAcc = 0;
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
		JPanel fuelcontent = new JPanel(new GridLayout(0,1));
		JPanel lifesupportcontent = new JPanel(new BorderLayout());
		JPanel payloadcontent = new JPanel(new GridLayout(0,1));
		
		//Create Menus
		JMenu quickMenu = new JMenu("Quick Controls");
		JMenu SummaryMenu = new JMenu("Mission Summary");
		JMenu objectiveMenu = new JMenu("Mission Objectives");
		JMenu gameMenu = new JMenu("Game Options");
		
		SummaryMenu.add(maxAlti);
		SummaryMenu.add(maxVelo);
		SummaryMenu.add(maxAccel);
		SummaryMenu.add(time);
		
		//add objectives
		objectiveMenu.add(new objective("Take Off!", 10, 0));
		objectiveMenu.add(new objective("Reach Space", 122000, 0));
		objectiveMenu.add(new objective("Deliver Potatoes to the ISS!", 330000, 1770));
		objectiveMenu.add(new objective("To the Moon!", 384000000, 3540));

		//add Quick Controls
		ignite = new JMenuItem("Ignite All Engines");
		quickMenu.add(ignite);
		autojettison = new JCheckBoxMenuItem("Auto-Jettison");
		quickMenu.add(autojettison);
		autoignite = new JCheckBoxMenuItem("Auto-Ignite");
		quickMenu.add(autoignite);
		
		//Configure Life Support
		JPanel energy = new JPanel(new GridLayout(0,1));
		lifesupportcontent.add(energy,BorderLayout.WEST);
		energy.add(new Reactor());
		energy.add(new Power());
		JTabbedPane ls = new JTabbedPane();
		lifesupportcontent.add(ls,BorderLayout.CENTER);
		ls.addTab("Oxygen", new Oxygen());
		ls.addTab("Temperature", new Heat());
		
		//Configure Flight Panel Content
		JTabbedPane lc = new JTabbedPane();
		flightpanel.setContentPane(lc);
		JMenuBar menu = new JMenuBar();
		flightpanel.setJMenuBar(menu);
		
		lc.addTab("Flight Controls", Launchcontent);
		lc.addTab("Life Support Controls", lifesupportcontent);
		lc.addTab("Payload", payloadcontent);
		
		menu.add(quickMenu);
		menu.add(SummaryMenu);
		menu.add(objectiveMenu);
		menu.add(gameMenu);
		
		//game menu
		JMenuItem exit = new JMenuItem("Exit Game");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);	
			}
		});
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flightpanel.dispose();
				new FlightPanel();
				alt = 0;
			}
		});
		gameMenu.add(newGame);
		gameMenu.add(exit);
		
		//Setup payload screen
		Potatoes cargo = new Potatoes();
		payload = new DisabledPanel(cargo);
		payload.setEnabled(true);
		payloadcontent.add(payload);
		
		//create launch control
		SolidFuel sf = new SolidFuel();
		ignite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (engine eng: sf.engines) {
					eng.ign.doClick();
				}
			}
		});
		ignite.setEnabled(false);
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
		graphs.addTab("Fuel", fuelcontent);
		
		forceChart.addTrace(thrust);
		forceChart.addTrace(grav);
		forceChart.addTrace(drag);
		speedChart.addTrace(velo);
		accelChart.addTrace(accel);
		altChart.addTrace(alti);
		
		Launchcontent.add(new LaunchConfirm(), BorderLayout.SOUTH);
		Launchcontent.add(solid, BorderLayout.WEST);
		Launchcontent.add(graphs, BorderLayout.CENTER);
		
		//setup fuel screen
		for (engine e: sf.engines) {
			fuelcontent.add(e.fuel);	
		}
		
		//main loop
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alt += getVelocity((sf.getMass()+cargo.getMass()+(Power.control.getValue()*1000)),sf.getThrust((int) alt));
				if (alt < 0) {
					alt = 0;
				}
				if (alt > maxAlt) {
					maxAlt = (int) alt;
					maxAlti.setText("Maximum ALtitude: "+maxAlt+"m");
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
		if ((alt <= 0)) {
			if (maxAlt > 10) {
				JOptionPane.showMessageDialog(null, "Ship Crashed; Mission Failed.", "Mission Failed!", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			if (velocity < 0) {
				velocity = 0;
			}
		}
		if (velocity > maxVel) {
			maxVel = (int) velocity;
			maxVelo.setText("Maximum Velocity: "+maxVel+"m/s");
		}
		this.velo.addPoint(this.velo.getMaxX()+1, velocity);
		int hours =(((int)this.velo.getMaxX())/360);
		int minutes = (((int)this.velo.getMaxX())%360)/60;
		int seconds = ((int)this.velo.getMaxX())%60;
		String hour = (hours < 10)?"0"+hours:""+hours;
		String minute = (minutes < 10)?"0"+minutes:""+minutes;
		String second = (seconds < 10)?"0"+seconds:""+seconds;
		time.setText("Mission Length: "+hour+":"+minute+":"+second);
		return velocity;
	}
	private double getAcceleration(double mass, double thrust) {
		double grav = calculateGravity(mass);
		double netforce = thrust-grav-0;
		double acceleration = netforce/mass;
		if (acceleration > maxAcc) {
			maxAcc = (int) acceleration;
			maxAccel.setText("Maximum Acceleration: "+maxAcc+"m/s/s");
		}
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
