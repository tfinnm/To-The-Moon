package tk.tfinnm.spaceflight.core;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.jar.JarFile;

import javax.swing.*;

import tk.tfinnm.spaceflight.components.ContentPack;
import tk.tfinnm.spaceflight.components.Mission;
import tk.tfinnm.spaceflight.components.Planet;
import tk.tfinnm.spaceflight.components.Spacecraft;

public class Launcher {

	static JFrame launcher;


	public static final int version = 0;

	/**
	 * Update checker
	 */
	//private final static String URL = "http://spaceflight.tfinnm.tk/updates"; //production
	private final static String URL = "http://localhost/update"; //testing

	public static void main(String[] args) {
		new dataManager();
		try {
			UIManager.setLookAndFeel(dataManager.getProp("laf", UIManager.getSystemLookAndFeelClassName()));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		launcher = new JFrame("Spaceflight Sim");
		launcher.setSize(1000, 500);
		launcher.setLocation(100, 100);
		launcher.setResizable(true);

		launcher.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel SPcontent = new JPanel(new BorderLayout());
		JEditorPane updatestext = new JEditorPane();
		updatestext.setContentType("text/html");
		updatestext.setEditable(false);

		ArrayList<ContentPack> mods = loadPluggins();
		mods.add(new DefaultContent());
		
		JScrollPane updatescroll = new JScrollPane(updatestext);
		JTabbedPane lc = new JTabbedPane();
		lc.addTab("Single Player", SPcontent);
		lc.addTab("News & Updates", updatescroll);
		launcher.setBackground(Color.white);
		JButton play = new JButton("Play!");
		Vector<Spacecraft> ships = new Vector<Spacecraft>();
		JList<Spacecraft> shipList = new JList<Spacecraft>(ships);
		Vector<Mission> missions = new Vector<Mission>();
		JList<Mission> missionList = new JList<Mission>(missions);
		missionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		shipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JCheckBox labMode = new JCheckBox("Use Lab Mode");
		JSlider simSpeed = new JSlider(JSlider.HORIZONTAL,1,10,1);
		simSpeed.setPaintLabels(true);
		simSpeed.setPaintTicks(true);
		simSpeed.createStandardLabels(3);
		simSpeed.setMajorTickSpacing(1);
		JLabel simLabel = new JLabel("Simulation Speed");
		simLabel.setLabelFor(simSpeed);
		
//		JComboBox<LookAndFeelInfo> looks = new JComboBox<LookAndFeelInfo>(UIManager.getInstalledLookAndFeels());
//		looks.setSelectedItem(UIManager.getLookAndFeel());
//		looks.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					UIManager.setLookAndFeel(((LookAndFeelInfo)looks.getSelectedItem()).getClassName());
//					SwingUtilities.updateComponentTreeUI(launcher);
//					dataManager.setProp("laf", ((LookAndFeelInfo)looks.getSelectedItem()).getClassName());
//				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//							| UnsupportedLookAndFeelException e1) {
//					e1.printStackTrace();
//				}
//			}
//		});
//		
//		JLabel lookLabel = new JLabel("Appearence");
//		lookLabel.setLabelFor(looks);
		
		Vector<Planet> planets = new Vector<Planet>();
		JComboBox<Planet> planet = new JComboBox<Planet>(planets); 
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new FlightPanel(shipList.getSelectedValue(),missionList.getSelectedValuesList(),(Planet) planet.getSelectedItem(), simSpeed.getValue(), labMode.isSelected());
					launcher.dispose();
				} catch (Exception e) {
				}
			}

		});
		SPcontent.add(play,BorderLayout.SOUTH);
		//initialize ships
		for (ContentPack p: mods) {
			if (p.getSpacecrafts() != null) {
				for(Spacecraft s: p.getSpacecrafts()) {
					ships.add(s);
				}
			}
			if (p.getMissions() != null) {
				for(Mission m: p.getMissions()) {
					missions.add(m);
				}
			}
			if (p.getPlanets() != null) {
				for(Planet plan: p.getPlanets()) {
					planets.add(plan);
				}
			}
			if (p.getLAFs() != null) {
				for(LookAndFeel laf: p.getLAFs()) {
					UIManager.installLookAndFeel(laf.getName(), laf.getClass().getCanonicalName());
				}
			}
		}
		//TODO: ship customizer
		//TODO: unlockable parts
		//TODO: custom scenarios
		//TODO: savegames
		//TODO: Debrief screen after failure/on exit
		shipList.setSelectedIndex(0);
		missionList.setSelectedIndices(new int[]{0,1});
		planet.setSelectedIndex(0);
		JPanel shipPanel = new JPanel();
		shipPanel.add(shipList);
		shipPanel.setBorder(BorderFactory.createTitledBorder("Select Rocket"));
		JPanel missionPanel = new JPanel();
		missionPanel.add(missionList);
		missionPanel.setBorder(BorderFactory.createTitledBorder("Select Missions"));
		JPanel advancedPanel = new JPanel();
		advancedPanel.setBorder(BorderFactory.createTitledBorder("Advanced Options"));
		advancedPanel.setLayout(new BorderLayout());
		JPanel simPanel = new JPanel();
		simPanel.setBorder(BorderFactory.createTitledBorder("Simulation"));
		simPanel.setLayout(new GridLayout(0,1));
		JPanel envPanel = new JPanel();
		envPanel.setBorder(BorderFactory.createTitledBorder("Enviroment"));
		simPanel.add(labMode);
		simPanel.add(simLabel);
		simPanel.add(simSpeed);
//		simPanel.add(lookLabel);
//		simPanel.add(looks);
		advancedPanel.add(simPanel,BorderLayout.NORTH);
		envPanel.add(planet);
		advancedPanel.add(envPanel,BorderLayout.CENTER);
		SPcontent.add(shipPanel,BorderLayout.WEST);
		SPcontent.add(missionPanel,BorderLayout.CENTER);
		SPcontent.add(advancedPanel,BorderLayout.EAST);
		launcher.setContentPane(lc);
		launcher.setVisible(true);
		//		try {
		//            if (Integer.parseInt(getLatestVersion()) > version) {
		//                UpdateInfo(getWhatsNew());
		//            }
		//        } catch (Exception ex) {
		//            ex.printStackTrace();
		//        }
	}

	private static String getData(String address)throws Exception
	{
		URL url = new URL(address);

		InputStream html = null;

		html = url.openStream();

		int c = 0;
		StringBuffer buffer = new StringBuffer("");

		while(c != -1) {
			c = html.read();

			buffer.append((char)c);
		}
		return buffer.toString();
	}

	private static JEditorPane infoPane;
	private static JFrame updater;
	private static JScrollPane scp;
	private static JButton ok;
	private static JButton cancel;
	private static JPanel pan1;
	private static JPanel pan2;

	public static void UpdateInfo(String info) {
		initUpdateComponents();
		infoPane.setText(info);
	}

	@SuppressWarnings("deprecation")
	private static void initUpdateComponents() {
		updater = new JFrame();
		updater.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		updater.setTitle("New Update Found");
		pan1 = new JPanel();
		pan1.setLayout(new BorderLayout());

		pan2 = new JPanel();
		pan2.setLayout(new FlowLayout());

		infoPane = new JEditorPane();
		infoPane.setContentType("text/html");
		infoPane.setEditable(false);

		scp = new JScrollPane();
		scp.setViewportView(infoPane);

		ok = new JButton("Update");
		ok.addActionListener( new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					update();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		cancel = new JButton("Cancel");
		cancel.addActionListener( new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				updater.dispose();
			}
		});
		pan2.add(ok);
		pan2.add(cancel);
		pan1.add(pan2, BorderLayout.SOUTH);
		pan1.add(scp, BorderLayout.CENTER);
		updater.add(pan1);
		updater.pack();
		updater.show();
		updater.setSize(600, 400);
	}
	private static void update() throws Exception
	{
		try {
			Desktop.getDesktop().browse(new URI(getURL()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

	}
	public static String getLatestVersion() throws Exception
	{
		String data = getData(URL);
		return data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
	}
	public static String getWhatsNew() throws Exception
	{
		String data = getData(URL);
		return data.substring(data.indexOf("[history]")+9,data.indexOf("[/history]"));
	}

	public static String getURL() throws Exception
	{
		String data = getData(URL);
		return data.substring(data.indexOf("[url]")+5,data.indexOf("[/url]"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<ContentPack> loadPluggins() {
		ArrayList<ContentPack> content = new ArrayList<ContentPack>();
		File pluginDirectory=new File("mods"); //arbitrary directory
		if(!pluginDirectory.exists()) pluginDirectory.mkdir();
		File[] files=pluginDirectory.listFiles((dir, name) -> name.endsWith(".jar"));
		if(files!=null && files.length>0)
		{
			ArrayList<String> classes=new ArrayList<>();
			ArrayList<URL> urls=new ArrayList<>(files.length);
			for(File file:files)
			{
				try {
					JarFile jar = new JarFile(file);

					jar.stream().forEach(jarEntry -> {
						if(jarEntry.getName().endsWith(".class"))
						{
							classes.add(jarEntry.getName());
						}
					});
					URL url=file.toURI().toURL();
					urls.add(url);
					jar.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			URLClassLoader urlClassLoader=new URLClassLoader(urls.toArray(new URL[urls.size()]));
			classes.forEach(className->{
				try
				{
					Class cls= urlClassLoader.loadClass(className.replaceAll("/",".").replace(".class","")); //transforming to binary name
					Class intface = cls.getSuperclass();
					if((intface!=null) && intface.equals(ContentPack.class)) //checking presence of Plugin interface
					{
						ContentPack plugin=(ContentPack) cls.getDeclaredConstructor().newInstance(); //instantiating the Plugin
						content.add(plugin);
					}
				}
				catch (Exception e){e.printStackTrace();}
			});
		}
		return content;
	}

}
