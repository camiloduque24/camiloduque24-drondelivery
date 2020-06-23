package com.dron.dronedelivery.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dron.dronedelivery.builder.DroneBuilder;
import com.dron.dronedelivery.builder.LocationBuilder;
import com.dron.dronedelivery.handler.ForwardHandler;
import com.dron.dronedelivery.handler.LeftHandler;
import com.dron.dronedelivery.handler.RightHandler;
import com.dron.dronedelivery.handler.StepHandler;
import com.dron.dronedelivery.model.Drone;
import com.dron.dronedelivery.model.Location;
import com.dron.dronedelivery.utils.Constants;
import com.dron.dronedelivery.utils.FileHelper;
import com.dron.dronedelivery.utils.PropertiesSingleton;

public class DroneController {

	private Logger logger = LoggerFactory.getLogger(DroneController.class);

	public List<Drone> bookDrones(String path) throws Exception {
		File[] files = FileHelper.listFilesForFolder(path);
		List<Drone> droneList = new ArrayList<>();
		Drone drone = new Drone();
		for (File file : files) {
			if (file.getName().contains(Constants.FILE_FORMAT)) {
				String fileindex = file.getName().replaceAll(Constants.INDEX_FORMAT, "");
				logger.info(String.format(Constants.BOOK_DRONE, fileindex));
				List<String> deliveries = new ArrayList<>();
				try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
					String bfRead;
					while ((bfRead = bf.readLine()) != null) {
						deliveries.add(bfRead);
					}
				} catch (Exception e) {
					logger.error(Constants.DRONE_QTY_ERROR_MSG);
				}
				drone = prepareDron(deliveries, fileindex);
				if (deliveries.size() <= drone.getCapacity()) {
					droneList.add(drone);
					logger.info(String.format(Constants.DRONE_BOOKED, fileindex));
				} else {
					logger.info(String.format(Constants.EXCEED_CAP, drone.getId()));
				}
			}
		}
		return droneList;
	}

	public Boolean dispatchDrone(List<Drone> droneList) {
		try {
			final StepHandler step1 = new ForwardHandler();
			StepHandler step2 = new LeftHandler();
			StepHandler step3 = new RightHandler();
			step1.setNextStep(step2);
			step2.setNextStep(step3);
			for (final Drone drone : droneList) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							List<String> finalLocationList = new ArrayList<>();
							for (String delivery : drone.getAddressList()) {
								StringBuilder finalLocation = new StringBuilder();
								String[] steps = delivery.split("");
								for (String step : steps) {
									Location location = step1.doStep(drone.getLocation(), step);
									drone.setLocation(location);
								}
								finalLocation.append(finalLoactionWithFormat(drone));
								finalLocationList.add(finalLocation.toString());
								logger.info(
										String.format(Constants.DELIVERIED, drone.getId(), finalLocation.toString()));
							}
							FileHelper.writeFile(finalLocationList, drone.getId());
						} catch (Exception e) {
							logger.error(String.format(Constants.NOT_DELIVERY, drone.getId()));
						}

					}
				});
				thread.start();
			}
		} catch (Exception e) {
			logger.error(Constants.DELIVERY_ERROR_MSG);
			return false;
		}
		return true;
	}

	private Drone prepareDron(List<String> deliveries, String id) {
		Properties properties = PropertiesSingleton.getproperties();
		LocationBuilder locationBuilder = new LocationBuilder();
		Location location = locationBuilder.withMyPositionX(Integer.parseInt(properties.getProperty(Constants.INITX)))
				.withMyPositionY(Integer.parseInt(properties.getProperty(Constants.INITY)))
				.withMyCardinality(Constants.NORTH).build();
		DroneBuilder droneBuilder = new DroneBuilder();
		Drone drone = droneBuilder.withId(id).withCapacity(Integer.parseInt(properties.getProperty(Constants.CAPACITY)))
				.withLocation(location).withAddressList(deliveries).build();
		return drone;
	}

	private String finalLoactionWithFormat(Drone drone) {
		String x = "" + drone.getLocation().getPositionX();
		String y = "" + drone.getLocation().getPositionY();
		String carty;
		switch (drone.getLocation().getCardinality()) {
		case Constants.NORTH:
			carty = Constants.NORTH_DESC;
			break;

		case Constants.SOUTH:
			carty = Constants.SOUTH_DESC;
			break;

		case Constants.EAST:
			carty = Constants.EAST_DESC;
			break;
		default:
			carty = Constants.WEST_DESC;
			break;
		}
		String finalLocation = String.format(Constants.LOCATION_FORMAT, x.toString(), y.toString(), carty.toString());
		return finalLocation;

	}

}
