package com.dron.dronedelivery.handler;

import com.dron.dronedelivery.model.Location;
import com.dron.dronedelivery.utils.Constants;

public class ForwardHandler extends StepHandler {

	@Override
	public Location doStep(Location location, String step) {
		int positionX = location.getPositionX();
		int positionY = location.getPositionY();
		String carty = location.getCardinality();
		if (step.equals(Constants.STEPFWRD)) {
			switch (carty) {
			case Constants.NORTH:
				positionY+=1;
				break;
			case Constants.SOUTH:
				positionY-=1;
				break;
			case Constants.EAST:
				positionX+=1;
				break;
			default:
				positionX-=1;
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
