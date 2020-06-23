package com.dron.dronedelivery;


import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dron.dronedelivery.controller.DroneController;
import com.dron.dronedelivery.model.Drone;
import com.dron.dronedelivery.utils.Constants;
import com.dron.dronedelivery.utils.PropertiesSingleton;

public class App 
{	
	private static Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	try {
    	Properties properties= PropertiesSingleton.getproperties();
    	DroneController droneController= new DroneController();
    	List<Drone>droneList= droneController.bookDrones(properties.getProperty(Constants.IN_PATH));
		droneController.dispatchDrone(droneList);	
    	}catch (Exception e) {
    		logger.error(Constants.NOT_DELIVERIED);
		}
    }
}
