package simulation;

/**
 * @author lee
 *This class contains the customer wait time intervals
 *At the end of the simulation this data will be logged
 */
public class Customer {
	private double arrivalTime;
	private double servedTime;
	private double exitTime;
	
	public Customer(){
		
	}
	
	public double getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public double getServedTime() {
		return servedTime;
	}
	public void setServedTime(double servedTime) {
		this.servedTime = servedTime;
	}
	public double getExitTime() {
		return exitTime;
	}
	public void setExitTime(double servicedTime) {
		this.exitTime = servicedTime;
	}
	
}
