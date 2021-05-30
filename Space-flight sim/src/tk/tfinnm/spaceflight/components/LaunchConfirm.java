package tk.tfinnm.spaceflight.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import tk.tfinnm.spaceflight.core.*;

public class LaunchConfirm extends JPanel{
	
	public static enum status {
		ready,
		pending,
		lanched,
		aborted
	}
	
	public static status Status = status.ready;

	private static final long serialVersionUID = 1L;
	
	private int time = 10;

	private JButton launch = new JButton("Ready to Launch");
	private JButton abort = new JButton("Abort Launch");
	private JProgressBar countdown = new JProgressBar();
	public LaunchConfirm() {
		this.setLayout(new BorderLayout());
		Status = status.ready;
		time = 10;
		this.add(launch, BorderLayout.CENTER);
		Timer timer = new Timer(1000, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (time>0) {
					time--;
					countdown.setValue(time);
					countdown.setString("T-minus "+time+" seconds");
					FlightPanel.payload.setEnabled(false);
					Power.control.setEnabled(false);
					if (time <= 3) {
						FlightPanel.solid.setEnabled(true);
						FlightPanel.ignite.setEnabled(true);
					}
				} else {
					Status = status.lanched;
					countdown.setString("Lift Off!");
					countdown.setIndeterminate(true);
					if (FlightPanel.autoignite.isSelected()) {
						FlightPanel.ignite.doClick();
					}
					timer.stop();
				}
			}
		});
		timer.setRepeats(true);
		launch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Status = status.pending;
				time = 10;
				remove(launch);
				add(countdown,BorderLayout.CENTER);
				add(abort,BorderLayout.EAST);
				countdown.setValue(time);
				countdown.setMaximum(10);
				countdown.setStringPainted(true);
				countdown.setString("T-minus "+time+" seconds");
				timer.start();
				timer.setRepeats(true);
			}
		});
		abort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (time > 3) {
					timer.stop();
					remove(countdown);
					remove(abort);
					add(launch, BorderLayout.CENTER);
					Status = status.ready;
					time = 10;
					FlightPanel.payload.setEnabled(true);
					Power.control.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Launch Aborted Successfully.", "Aborted!", JOptionPane.INFORMATION_MESSAGE);
				} else {
					timer.stop();
					Status = status.aborted;
					JOptionPane.showMessageDialog(null, "Launch Aborted; Mission Failed.", "Mission Failed!", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
	}
	

}
