package com.dron.dronedelivery.handler;

import com.dron.dronedelivery.model.Location;

public abstract class StepHandler  {
	
	
	protected StepHandler nextStep;

	
	
	public StepHandler getNextStep() {
		return nextStep;
	}


	public void setNextStep(StepHandler nextStep) {
		this.nextStep = nextStep;
	}

	public abstract Location doStep(Location location,String step);
}
