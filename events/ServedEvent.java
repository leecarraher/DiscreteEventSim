package events;

import simulation.CheckOut;
import simulation.Customer;
import discreteEventSimulator.AbstractEvent;

/**
 * @author lee
 * This is the ServedEvent, it is added to the event queue when a customer is moved from
 * the waiting queue to a server. When it is dequeued after service it decrements the
 * used server count and increments the free server count. It then calls serve to see if
 * there are any customers in the waiting queue.
 */
public class ServedEvent extends AbstractEvent  {

	CheckOut co;
	
	public ServedEvent(CheckOut co)
	{
		this.co = co;
		this.setTime(co.getTime()+co.generateServiceTime());//set time to finish service
	}
	
	public void runEvent()
	{		
		co.servedEvent();		
	}
}
