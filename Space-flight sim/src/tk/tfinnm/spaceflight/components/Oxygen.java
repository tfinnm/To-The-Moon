package tk.tfinnm.spaceflight.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import tk.tfinnm.spaceflight.core.FlightPanel;

public class Oxygen extends JPanel {

	public double oxygen = 20.9;
	public static boolean status = true;
	
	private static final long serialVersionUID = 1L;

	public Oxygen() {
		setLayout(new BorderLayout());
		JButton toggle = new JButton("Turn Off Scrubber");
		toggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				status = !status;
				if (status) {
					FlightPanel.logEvent("Scrubber Activated");
					toggle.setText("Turn Off Scrubber");
				} else {
					FlightPanel.logEvent("Scrubber Deactivated");
					toggle.setText("Turn On Scrubber");
				}
			}
		});
		JProgressBar level = new JProgressBar();
		level.setMaximum(24);
		level.setValue((int)oxygen);
		level.setMinimum(6);
		level.setStringPainted(true);
		String ox = Double.toString(oxygen);
		level.setString("Oxygen Concentration: "+ox.substring(0, 4)+"% [Ideal: 19.5-23.5%]");
		add(toggle, BorderLayout.CENTER);
		add(level, BorderLayout.NORTH);
		add(new JLabel(" "), BorderLayout.SOUTH);
		add(new JLabel(" "), BorderLayout.EAST);
		add(new JLabel(" "), BorderLayout.WEST);
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (status) {
					if (oxygen < 20.9) {
						oxygen += 0.1;
					}
				} else {
					oxygen -= 0.1;
				}
				String ox = Double.toString(oxygen);
				level.setString("Oxygen Concentration: "+ox.substring(0, 4)+"% [Ideal: 19.5-23.5%]");
				level.setValue((int)oxygen);
				if (oxygen < 14) {
					FlightPanel.flightpanel.setSize(1000, 500);
					FlightPanel.flightpanel.setLocation((int)(Math.random()*200), (int)(Math.random()*200));
				}
				if (oxygen < 6) {
					JOptionPane.showMessageDialog(null, "Life Support Failure; Mission Failed.", "Mission Failed!", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
		timer.setRepeats(true);
		timer.start();
		timer.setRepeats(true);
	}

}
