package discreteEventSimulator;

import java.util.ArrayList;
import collections.PriorityQueue;




/**
 * @author lee
 *This is the abstract discrete event simulator
 */
public abstract class AbstractDiscreteEventSimulator implements Runnable
{
	double time;
	PriorityQueue<AbstractEvent> events;
	protected int delay = 0;//no delay
	volatile Thread  t;
	int max;
	boolean stop;
	private volatile boolean  threadSuspended;
	
	public abstract void doOnStop();
		
	/** AbstractDiscreteEventSimulator is a threaded event
	 * simulator capable of realtime and instantaneous
	 * simulation. Its main interface tokens are AbstractEvents.
	 * @param max number of iterations before exiting.
	 * Otherwise it will exit when no events remain in the 
	 * queue. for no limit set to -1
	 */
	public AbstractDiscreteEventSimulator(int max)
	{
		this.max = max;
		time = 0;//System.currentTimeMillis();
		t = new Thread(this);
		events = new PriorityQueue<AbstractEvent>(max);
		threadSuspended = false;
	}
	
	/**
	 * Start the simulation
	 * starts the thread for the simulation while loop
	 */
	public void start(){
			stop = false;
			t.start();
	}
	
	/**
	 * toggle the currently running simulation (pause/unpause).
	 * note: this method is synchronized with the currently running thread
	 * and the start method. Used start to resume.
	 */
	public synchronized void toggle()
	{
			threadSuspended = !threadSuspended;
	        if (!threadSuspended)
	            notify();
	}
	
	/**
	 * ends the currently executing simulation and calls the
	 * implemented doOnStop() method.
	 * Note the volatile
	 * Thread t and this reassignment to null replaces the
	 * depricated method Thread.stop() which is unsafe. 
	 */
	public void stop(){
		t = null;
		stop = true;
		doOnStop();
	}
	
	/**Set a realtime (in milliseconds) delay interval for
	 * dequeuing events. Helpful for realtime interactive
	 * Simulations.
	 * @param delay
	 */
	public void setDelay(int delay){
		this.delay = delay;
	}
	
	/**sets the execution time of an event to this
	 * EventSimulators last dequeued event time. It is up to 
	 * the event to modify it after aliasing otherwise it
	 * will be executed immediately.
	 * @param e
	 */
	public void aliasEvent(AbstractEvent e){
		e.setTime(time);
	}
	
	/**returns the execution time of the last dequeued element
	 * @return
	 */
	public double getTime(){
		return time;
	}
	
	/* run method for this thread, it is capable of suspending
	 * and resuming via the synchronization and notify methods
	 * for threads. The boolean value @threadSuspended controls
	 * suspended and resume states.
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		while(!events.isEmpty() && !stop){		
			try {
                if(delay!=0)Thread.currentThread().sleep(delay);
				

                if (threadSuspended) {
                    synchronized(this) {
                        while (threadSuspended)
                            wait();
                    }
                }
                executeEvents();
            } catch (InterruptedException e){
			}
		}

	}
	
	
	/**Executes all events
	 */
	private void executeEvents()
	{
		if(--max==0)
		{
			stop();
		}
		ArrayList<AbstractEvent> bulkExecution = getAllPastEvents();
		
		for(AbstractEvent e : bulkExecution){
			time = e.getTime();
			e.runEvent();
		}
	}
	
	/**add an Abstract Event to the event priority queue
	 * @param e
	 */
	public void add(AbstractEvent e)
	{
		events.add(e);
	}
	
	/**Dequeues all equal events 
	 * @return a list of all equal events
	 */
	private ArrayList<AbstractEvent> getAllPastEvents()
	{
		ArrayList<AbstractEvent> bulkExecution = new ArrayList<AbstractEvent>(10);
		
		AbstractEvent e = events.peek();
		double topTime = e.getTime();
		
		//the first event gets added in the first loop
		// the second iteration updates e, if e is null
		//then the hasMoreElements() function will be false
		//and short circuit logic will not attempt null.getTime()
		while(!events.isEmpty() && e.getTime() == topTime){
			bulkExecution.add(events.remove());
			e = events.peek();
		}
		return bulkExecution;
		
	}
	
}
