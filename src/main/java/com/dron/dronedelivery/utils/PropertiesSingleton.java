package com.dron.dronedelivery.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesSingleton {

	private String propertiesPath;
	private static Properties properties;

	private PropertiesSingleton() {
		properties = new Properties();
		propertiesPath = Constants.PROPERTIES;
		try (InputStream input = PropertiesSingleton.class.getClassLoader().getResourceAsStream(propertiesPath)) {
			if (input == null) {
				System.out.println("Sorry, unable to find param.properties");
			}
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static Properties getproperties() {
		if (properties == null) {
			PropertiesSingleton propertiesSingleton = new PropertiesSingleton();
		}
		return properties;
	}

}
