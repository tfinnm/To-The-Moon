package tk.tfinnm.spaceflight.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class dataManager {
	
	static Properties props;
	private static final String fpath = "settings.xml";
    
	public dataManager() {
		props = new Properties();
		try {
			props.loadFromXML(new FileInputStream(fpath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setProp(String key, String value) {
		props.setProperty(key, value);
    	try {
			props.storeToXML(new FileOutputStream(fpath), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProp(String key, String dvalue) {
		return props.getProperty(key, dvalue);
	}
	
}
