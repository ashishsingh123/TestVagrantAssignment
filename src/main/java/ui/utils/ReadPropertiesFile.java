package ui.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadPropertiesFile {

	String path = System.getProperty("user.dir") + "//config//";
	private  String configFile = path+"app.properties";

	/**
	 * It load the configuration file and read data
	 * 
	 * @return the found value from properties file.
	 */
	public  String getPropertyData(String key) {
		String value = "";
		try {

			Properties properties = new Properties();
			File file = new File(configFile);
			if (file.exists()) {
				properties.load(new FileInputStream(file));
				value = properties.getProperty(key);
			}
		} catch (Exception e) {
			System.out.println("file not found");
		}
		return value;
	}

}
