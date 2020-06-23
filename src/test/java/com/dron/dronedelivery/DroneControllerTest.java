package com.dron.dronedelivery;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.dron.dronedelivery.controller.DroneController;
import com.dron.dronedelivery.model.Drone;
import com.dron.dronedelivery.utils.Constants;


public class DroneControllerTest {

	@Test
	public void dispatchDrones() throws Exception {
		ClassLoader classLoader = DroneControllerTest.class.getClassLoader();
		String resource = classLoader.getResource(Constants.DELIVERIES).getPath();
		DroneController droneController = new DroneController();
		List<Drone> dronelist = droneController.bookDrones(resource);
		Boolean isDispatched = droneController.dispatchDrone(dronelist);
		assertTrue(isDispatched);
	}

	@Test
	public void bookdronesTest() throws Exception {
		ClassLoader classLoader = DroneControllerTest.class.getClassLoader();
		String resource = classLoader.getResource(Constants.DELIVERIES).getPath();
		DroneController droneController = new DroneController();
		List<Drone> dronelist = droneController.bookDrones(resource);
		assertNotNull(dronelist);
		
	}
	
	@Test
	public void maxDronesExceed() throws Exception  {		
		ClassLoader classLoader = DroneControllerTest.class.getClassLoader();
		String resource = classLoader.getResource(Constants.DELIVERIES_TEST).getPath();
		DroneController droneController = new DroneController();
		IllegalArgumentException exception=assertThrows(IllegalArgumentException.class,()-> droneController.bookDrones(resource));
		assertTrue(exception.getMessage().contains(Constants.DRONE_QTY_ERROR_MSG));

	}

}
