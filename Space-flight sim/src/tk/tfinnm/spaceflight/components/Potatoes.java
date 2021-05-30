package tk.tfinnm.spaceflight.components;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Potatoes extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	//0.2825 kg/each
	public static JSlider ammount;
	public Potatoes() {
		
		setBorder(BorderFactory.createTitledBorder("Cargo: Potatoes"));
		setLayout(new BorderLayout());
		ammount = new JSlider(JSlider.HORIZONTAL,0,10000,0);
		ammount.setSnapToTicks(true);
		ammount.setName("Number of Potatoes");
		ammount.createStandardLabels(1000);
		ammount.setMajorTickSpacing(1000);
		ammount.setMinorTickSpacing(100);
		ammount.setPaintLabels(true);
		ammount.setPaintTicks(true);
		ammount.setSize(this.getWidth(), ammount.getHeight());
		add(ammount, BorderLayout.CENTER);
	}
	
	public double getMass() {
		return ammount.getValue()*0.2825;
	}

}
