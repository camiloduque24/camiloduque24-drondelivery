package com.dron.dronedelivery.handler;

import com.dron.dronedelivery.model.Location;
import com.dron.dronedelivery.utils.Constants;

public class LeftHandler extends StepHandler {

	@Override
	public Location doStep(Location location, String step) {
		int positionX = location.getPositionX();
		int positionY = location.getPositionY();
		String carty = location.getCardinality();
		if (step.equals(Constants.STEPLEFT)) {
			switch (carty) {
			case Constants.NORTH:
				carty = Constants.WEST;
				break;
			case Constants.SOUTH:
				carty = Constants.EAST;
				break;
			case Constants.EAST:
				carty = Constants.NORTH;
				break;
			default:
				carty = Constants.SOUTH;
			}
			location.setPositionX(positionX);
			location.setPositionY(positionY);
			location.setCardinality(carty);
		} else {
			if (this.getNextStep() != null) {
				this.getNextStep().doStep(location, step);
			}
		}
		return location;
	}

}
