package discreteEventSimulator;

/**
 * @author lee
 * this is the abstract class interface for all events used in the AbstractdDiscreteEventSimulator
 */
public abstract class AbstractEvent implements Comparable <AbstractEvent> {
	
	private double time;
	abstract public void runEvent();
	
	public void setTime(double time)
	{
		this.time=time;
	}
	
	public double getTime(){
		return time;
	}
	
	public int compareTo(AbstractEvent e){
		if(time==e.time)return 0;
		if(time > e.time)return 1;
		return -1;
	}
	
		
}
