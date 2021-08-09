package tk.tfinnm.spaceflight.core;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Debrief {
	
	public static JFrame debriefPanel;
	
	public static final int version = 0;
	
	public Debrief(ArrayList<String> flightDataLog, ArrayList<String> eventLog, ArrayList<String> lifeDataLog, JMenu objectiveMenu, String summaryText, String objectiveText) {

		//Configure Flight Panel
		debriefPanel = new JFrame("Spaceflight Sim | Debrief");
		debriefPanel.setSize(1000, 500);
		debriefPanel.setLocation(100, 100);
		debriefPanel.setResizable(true);
		debriefPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		debriefPanel.setBackground(Color.white);
		debriefPanel.setVisible(true);
		
		//Create Screens
		JPanel mainContent = new JPanel(new BorderLayout());
		
		//Create Menus
		JMenu gameMenu = new JMenu("Options");
		
		//Configure Flight Panel Content
		debriefPanel.setContentPane(mainContent);
		JMenuBar menu = new JMenuBar();
		debriefPanel.setJMenuBar(menu);
		
		menu.add(gameMenu);
		menu.add(objectiveMenu);
		
		//game menu
		JMenuItem export = new JMenuItem("Export Flight Data");
		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				export(flightDataLog);
			}
		});
		JMenuItem export2 = new JMenuItem("Export Event Log");
		export2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				export(eventLog);
			}
		});
		JMenuItem export3 = new JMenuItem("Export Life Support Data");
		export2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				export(lifeDataLog);
			}
		});
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
				debriefPanel.dispose();
				Launcher.main(new String[0]);
				FlightPanel.alt = 0;
			}
		});
		JMenu looks = new JMenu("Appearence");
		for (LookAndFeelInfo i: UIManager.getInstalledLookAndFeels()) {
			JMenuItem temp = new JMenuItem(i.getName());
			temp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						UIManager.setLookAndFeel(i.getClassName());
						SwingUtilities.updateComponentTreeUI(debriefPanel);
						dataManager.setProp("laf", i.getClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						e1.printStackTrace();
					}
				}
			});
			looks.add(temp);
		}
		gameMenu.add(export);
		gameMenu.add(export2);
		gameMenu.add(export3);
		gameMenu.add(looks);
		gameMenu.add(newGame);
		gameMenu.add(exit);
		
		JTabbedPane pages = new JTabbedPane();
		mainContent.add(pages,BorderLayout.CENTER);
		String[][] arr = new String[eventLog.size()][];
		for (int i = 0; i < eventLog.size(); i++) {
			arr[i] = eventLog.get(i).split(",");
		}
		pages.addTab("Event Log", new JScrollPane(new JTable(arr, new String[] {"Time","Event"})));
		arr = new String[flightDataLog.size()-1][];
		for (int i = 1; i < flightDataLog.size(); i++) {
			arr[i-1] = flightDataLog.get(i).split(",");
		}
		pages.addTab("Flight Data", new JScrollPane(new JTable(arr, flightDataLog.get(0).split(","))));
		arr = new String[lifeDataLog.size()-1][];
		for (int i = 1; i < lifeDataLog.size(); i++) {
			arr[i-1] = lifeDataLog.get(i).split(",");
		}
		pages.addTab("Life Support Data", new JScrollPane(new JTable(arr, lifeDataLog.get(0).split(","))));
		JTextPane summary = new JTextPane();
		summary.setEditable(false);
		summary.setText("Mission Summary");
		summary.setText(summary.getText()+"\n"+summaryText);
		summary.setText(summary.getText()+"\n"+objectiveText);
		mainContent.add(summary,BorderLayout.WEST);

	}
	
	private void export(ArrayList<String> arr) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify where to save");
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Comma Seperated Values (CSV) File", "csv"));
		int userSelection = fileChooser.showSaveDialog(debriefPanel);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    try {
		    	if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
		    		fileToSave = new File(fileToSave.getAbsolutePath()+".csv");
		    	}
				fileToSave.createNewFile();
		        PrintWriter out = new PrintWriter(fileToSave);

		        for (String s: arr) {
		        	out.println(s);
		        }

		        out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
