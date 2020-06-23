package com.dron.dronedelivery.handler;

import com.dron.dronedelivery.model.Location;
import com.dron.dronedelivery.utils.Constants;

public class RightHandler extends StepHandler {

	@Override
	public Location doStep(Location location,String step) {
		int positionX = location.getPositionX();
		int positionY = location.getPositionY();
		String carty = location.getCardinality();
		if (step.equals(Constants.STEPRIGHT)) {		
			switch (carty) {
			case Constants.NORTH:
				carty = Constants.EAST;
				break;
			case Constants.SOUTH:
				carty = Constants.WEST;
				break;
			case Constants.EAST:
				carty = Constants.SOUTH;
				break;
			default:
				carty = Constants.NORTH;
			}
			location.setPositionX(positionX);
			location.setPositionY(positionY);
			location.setCardinality(carty);
		} 
		return location;
		
	}

}
