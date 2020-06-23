package com.dron.dronedelivery.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class FileHelper {

	public static File[] listFilesForFolder(String path) {
		Properties properties = PropertiesSingleton.getproperties();
		File[] fileList = new File(path).listFiles();
		if (fileList == null || fileList.length == 0
				|| fileList.length > Integer.parseInt(properties.getProperty(Constants.NUMDRONES))) {
			throw new IllegalArgumentException(Constants.DRONE_QTY_ERROR_MSG);
		} else {
			return fileList;
		}
	}

	public static void writeFile(List<String> deliveries, String id) throws Exception {
		Properties properties = PropertiesSingleton.getproperties();
		StringBuilder finalLocation = new StringBuilder();
		for (String line : deliveries) {
			finalLocation.append(line);
			finalLocation.append(System.lineSeparator());
		}
		StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append(properties.getProperty(Constants.OUT_PATH));
		pathBuilder.append(String.format(Constants.OUTPUT_FORMAT, id));
		Files.write(Paths.get(pathBuilder.toString()), finalLocation.toString().getBytes());
	}

}
