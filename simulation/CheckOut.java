package simulation;

import java.util.LinkedList;

import random.MultLCG;

import discreteEventSimulator.AbstractDiscreteEventSimulator;
import events.ArrivalEvent;
import events.ServedEvent;

/**
 * @author lee
 *This is the primary simulation class. It Extends AbstractDiscreteEventSimulator.
 */
public class CheckOut extends AbstractDiscreteEventSimulator 
{
	
	int inUseServers;
	int freeServers;
	double mServTime;
	double mInterArrivialTime;
	LinkedList<Customer> waitingQueue;
	MultLCG iatRand;
	MultLCG mstRand;
	LinkedList<Customer> exitQueue;//queue containing past customer data
	
	
	public static void main(String[] args)
	{
			if(args.length==4)
			{
				CheckOut co = new CheckOut(1D/Double.parseDouble(args[0]),1D/Double.parseDouble(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
				co.start();
			}
			else
				System.out.println("Usage:java -jar Checkout.jar MeanInterarrivalTime MeanServiceTime NumberOfServers NumberOfInterations");
			
			
	}
	
	public CheckOut(double mInterArrivalTime, double mServTime, int numOfServers, int iterations){
		super(iterations);
		this.inUseServers = 0;
		this.freeServers = numOfServers;
		this.mServTime = mServTime;
		this.mInterArrivialTime = mInterArrivalTime;
		waitingQueue = new LinkedList<Customer>();
		exitQueue = new LinkedList<Customer>();
		iatRand = new MultLCG(555);
		mstRand = new MultLCG(1223);
		
		arrivalEvent();//create an initial arrival event
	}

	/** used by the ArrivalEvent constructor
	 * @return an interarrival time
	 */
	public double generateInterArrivalTime()
	{
		double iat = iatRand.exponentialVariate(mInterArrivialTime);
		return iat;
	}
	
	/** used by the ServedEvent constructor
	 * @return a service time
	 */
	public double generateServiceTime()
	{
		double mst = mstRand.exponentialVariate(mServTime);
		return mst;
	}
	
	/**
	 * This method checks for free servers and then if there are customers in 
	 * the waitingQueue it dequeues them and creates a service event.
	 */
	private void serveNewCustomer()
	{
		if(freeServers>0)
		{
			if(waitingQueue.size() > 0)
			{
				freeServers--;
				inUseServers++;

				ServedEvent se = new ServedEvent(this);
				
				//the exit time is already known to be the scheduled ServedEvent's 
				//dequeue time. Unlike arrivalTime there is no waiting queue blocking
				//therefor all customer data is known and can be logged.
				Customer c = waitingQueue.removeFirst();
				c.setServedTime(getTime());
				c.setExitTime(se.getTime());
				
				exitQueue.add(c);
				add(se);
				
			}
		}
		logSysChange();
	}
	
	/**This is the response method to the ServedEvent which occurs when a customer has beed served.
	 * The servers are updated and the serveNewCustomer Function is called
	 * 
	 */
	public void servedEvent()
	{
		freeServers++;
		inUseServers--;
		serveNewCustomer();
	}
	
	/**
	 * response to an arrival event. A new Customer is queued into the waiting queue
	 * with arrival time now. A new arrival event is created. and the serveNewCustomer
	 * function is called.
	 */
	public void arrivalEvent(){
		Customer c = new Customer();
		c.setArrivalTime(this.getTime());
		waitingQueue.add(c);
		add(new ArrivalEvent(this));
		serveNewCustomer();
	}
	
/*
 * Calculations
 */
	double avgPopulation=0;
	double utilization =0;
	double[] loggedData = {0,0,0,0,0};
	double[] toleranceData = {0,0,0,0,0};
	double t_nMinus1 = 0;

	
	
	/**This method accumulates the overall population*deltatime in the system
	 */
	private void logSysChange()
	{
		//mean population, mean utilization
		double deltaTime = getTime()-t_nMinus1;
		avgPopulation += (waitingQueue.size()+inUseServers)*deltaTime;
		utilization += inUseServers/(freeServers+inUseServers)*deltaTime;
		
		if(getTime()!=0){
			loggedData[0] = avgPopulation/getTime();
			loggedData[1] = utilization/getTime();
		}
		
		
		//mean waiting time, mean service time, mean total time 
		int i = 0;
		for(Customer c : exitQueue)
		{
			//System.out.println(c.getArrivalTime() + " " + c.getServedTime() + " " + c.getExitTime());
			loggedData[2]+= (c.getServedTime()-c.getArrivalTime());
			loggedData[3]+=(c.getExitTime()-c.getServedTime());
			loggedData[4]+=(c.getExitTime()-c.getArrivalTime());
			i++;
		}
		
		loggedData[2]/=(double)i;
		loggedData[3]/=(double)i;
		loggedData[4]/=(double)i;
		t_nMinus1 = getTime();
		
//		boolean flagTolerance = true;
//		
//		for(i=0;i<5;i++)
//		{
//			flagTolerance = (toleranceData[i] !=0 && Math.abs(toleranceData[i] - loggedData[i]) < .01 && flagTolerance);
//			toleranceData[i] = loggedData[i];
//		}
//		if(flagTolerance){
//			stop();
//		}

	}

	@Override
	public void doOnStop() 
	{
		System.out.println("#Customers\tUtilization\tWait Time\tService Time\tTotal Time");
		for(double d: loggedData)
		{
			System.out.print(d + "\t");
		}
		System.out.println();

	}
}
