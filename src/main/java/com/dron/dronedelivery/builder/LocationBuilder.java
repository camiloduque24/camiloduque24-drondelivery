package com.dron.dronedelivery.builder;

import com.dron.dronedelivery.model.Location;

public class LocationBuilder implements IlocationBuilder {

	private int positionX;
	private int positionY;
	private String cardinality;

	public LocationBuilder() {

	}

	public LocationBuilder withMyPositionX(int positionX) {
		this.positionX = positionX;
		return this;
	}

	public LocationBuilder withMyPositionY(int positionY) {
		this.positionY = positionY;
		return this;
	}

	public LocationBuilder withMyCardinality(String cardinality) {
		this.cardinality = cardinality;
		return this;
	}

	@Override
	public Location build() {
		Location location = new Location();
		location.setPositionX(positionX);
		location.setPositionY(positionY);
		location.setCardinality(cardinality);
		return location;
	}

}
