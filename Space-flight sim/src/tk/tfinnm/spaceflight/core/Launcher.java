package tk.tfinnm.spaceflight.core;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Launcher {

	static JFrame launcher;
	
	
	public static final int version = 0;
	
	/**
	 * Update checker
	 */
    //private final static String URL = "http://spaceflight.tfinnm.tk/updates"; //production
	private final static String URL = "http://localhost/update"; //testing

	public static void main(String[] args) {
		launcher = new JFrame("Spaceflight Sim");
		launcher.setSize(1000, 500);
		launcher.setLocation(100, 100);
		launcher.setResizable(true);
		
		launcher.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel SPcontent = new JPanel(new BorderLayout());
		JEditorPane updatestext = new JEditorPane();
        updatestext.setContentType("text/html");
		updatestext.setEditable(false);

		JScrollPane updatescroll = new JScrollPane(updatestext);
		JTabbedPane lc = new JTabbedPane();
		lc.addTab("Single Player", SPcontent);
		lc.addTab("News & Updates", updatescroll);
		launcher.setBackground(Color.white);
		JButton play = new JButton("Play!");
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new FlightPanel();
					launcher.dispose();
				} catch (Exception e) {
				}
			}
			
		});
		//SPcontent.add(scenarioList,BorderLayout.CENTER); //TODO: rocket designer goes here
		SPcontent.add(play,BorderLayout.SOUTH);
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

}
