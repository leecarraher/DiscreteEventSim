package events;

import simulation.CheckOut;
import discreteEventSimulator.AbstractDiscreteEventSimulator;
import discreteEventSimulator.AbstractEvent;

/**
 * @author lee
 * This Event represents an Arrival event. The customer is not needed yet
 * so it does not contain one as a parameter.
 */
public class ArrivalEvent extends AbstractEvent {

	CheckOut co;
	
	public ArrivalEvent(CheckOut co)
	{
		this.co = co;
		this.setTime(co.generateInterArrivalTime()+co.getTime());//set arrival time for new customer
	}
	
	public void runEvent()
	{
		co.arrivalEvent();
	}
}
