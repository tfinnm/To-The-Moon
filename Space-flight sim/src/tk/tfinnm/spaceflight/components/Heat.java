package tk.tfinnm.spaceflight.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.Timer;

public class Heat extends JPanel {

	public static int temp = 20;
	public static boolean status = true;
	public static JSlider control;
	
	private static final long serialVersionUID = 1L;

	public Heat() {
		setLayout(new BorderLayout());
		JButton toggle = new JButton("Turn Off Heater");
		toggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				status = !status;
				if (status) {
					toggle.setText("Turn Off Heater");
				} else {
					toggle.setText("Turn On Heater");
				}
			}
		});
		control = new JSlider(JSlider.HORIZONTAL,0,30,20);
		control.setPaintLabels(true);
		control.setPaintTicks(true);
		control.createStandardLabels(5);
		control.setMajorTickSpacing(1);
		add(control,BorderLayout.SOUTH);
		JProgressBar level = new JProgressBar();
		level.setMaximum(30);
		level.setValue(temp);
		level.setMinimum(0);
		level.setStringPainted(true);
		level.setString("Temperature: "+temp+"C [Ideal: 10-20C]");
		add(toggle, BorderLayout.CENTER);
		add(level, BorderLayout.NORTH);
		add(new JLabel(" "), BorderLayout.EAST);
		add(new JLabel(" "), BorderLayout.WEST);
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (status) {
					if (temp < control.getValue()) {
						temp++;
					} else if (temp > control.getValue()) {
						temp--;
					}
				} else {
					temp -= 2;
				}
				level.setString("Temperature: "+temp+"C [Ideal: 10-20C]");
				level.setValue(temp);
				if (temp <= 0) {
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
