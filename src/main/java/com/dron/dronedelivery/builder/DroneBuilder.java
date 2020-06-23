package com.dron.dronedelivery.builder;

import java.util.List;

import com.dron.dronedelivery.model.Drone;
import com.dron.dronedelivery.model.Location;



public class DroneBuilder implements IdroneBuilder {

	private String id;

	private Location location;

	private int capacity;

	private List<String> addressList;

	public DroneBuilder withId(String id) {
		this.id = id;
		return this;
	}

	public DroneBuilder withLocation(Location location) {
		this.location = location;
		return this;
	}

	public DroneBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public DroneBuilder withAddressList(List<String> addressList) {
		this.addressList = addressList;
		return this;
	}

	@Override
	public Drone build() {

		Drone drone = new Drone();
		drone.setId(id);
		drone.setCapacity(capacity);
		drone.setLocation(location);
		drone.setAddressList(addressList);
		return drone;
	}

}
