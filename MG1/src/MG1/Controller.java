package MG1;

import java.util.*;
import java.io.*;

public class Controller {

		public static final int ARRIVE = 0; // represents arrival
	    public static final int DEPART = 1; // represents departure

	    public static final double sigma = 0.0001;
	    ////////////////////////////////System variables////////////////////////
	    LinkedList<Event> schedule;
	    double clock;
	    double endTime;
	    
	    double nextArrival;				// the time of the next scheduled arrival to the system
	    double nextDeparture;			// the time of the next scheduled departure from the system
	    double lambda;					// arrival rate
	    double ts;						// mean service time
	    
	    ///////////////////////////////Monitor check points////////////////////
	    ArrayList<Double> checkpoints;
	    int numChecks;
	    
	    ///////////////////////////////Statistic variables/////////////////////
	    double Tq,Tw,Ts;				// Tq: Time of customers in the queue , Tw: Total time waiting, Ts: Total time of service 	
	    int w, q;						// w: number of waiting customer, q: number of customer were in the queue
	    int requests, serviced;    		// Total number os request and service
	    
	    public Controller(double lambda, double ts, double endTime){
	    	this.lambda = lambda;
	    	this.ts     = ts;
	    	this.endTime= endTime;
	    	
	    	this.clock = 0.0;
	    	this.schedule = new LinkedList<Event>();
	    	
	    	/*
	    	 * generate a list of monitoring events for ease
	    	 * */
	    	this.checkpoints = new ArrayList<Double>();
	    	double n = 0;
	    	while(n < this.endTime) {
	    		this.numChecks++;
	    		n += PoissonDistribs(lambda);
	    		checkpoints.add(n); // get all set of arrival time for the check point
	    	}
	    	
	        this.nextArrival = PoissonDistribs(lambda);			// arrival time at initial is exponential distributed
	        this.nextDeparture = Double.POSITIVE_INFINITY;  		//set the next departure time to infinity
	    	
	    	
	    	
	        Tq = 0; Tw = 0; Ts = 0;
	        w= 0; q = 0;
	        requests = 0; serviced = 0;
	    	
	    }
	    
	    public void arriveHandler(double time) {
	    	if (this.schedule.isEmpty()) { // if the queue is empty, schedule a departure
	    		scheduleDepart(time);
	    	}else { // if it isn't
	    		this.schedule.add(new Event(time,ARRIVE));
	    	}
	    	nextArrival += PoissonDistribs(lambda);
	    }  
	    
	    public void departHandler() {
	    	this.schedule.remove();
	    	if (!this.schedule.isEmpty()) { // if there is a pending request, modify its contents to be a death event 
	    		Event next = this.schedule.remove();
	    		scheduleDepart(next.getTime());     		        		
	    	}else {
	    		this.nextDeparture = Double.POSITIVE_INFINITY;
	    		this.nextArrival  += PoissonDistribs(this.lambda);
	    	}
	    }
	    /*
	     * actual function where system's clock is update 
	     * besides, it also help to collect some statistics info
	     * about time in the system 
	     * 
	     * */
	    public void scheduleDepart(double arrivalTime) {
	    	this.nextDeparture = this.clock + GaussianDistribs(ts); // schedule the time as GaussianDis for nextDepart
	    	this.schedule.addFirst(new Event(this.nextDeparture,DEPART)); // write it to the schedule
	    	this.serviced++;
	    	this.Tq += (this.nextDeparture - arrivalTime); // time in the queue
	    	this.Tw += (this.clock - arrivalTime);         // time waiting = system time - time arrive
	    	this.Ts += (this.nextDeparture - this.clock);  // time service = gaussianDistribs(mu) or take the nextdeparture - clock
	    }
	    //// This is the poisson distribution for arrival time
	    public static double PoissonDistribs(double lambda) {
		   	double L = Math.exp(-lambda);
				  double p = 1.0;
				  float k = 0;

				  do {
				    k++;
				    p *= Math.random();
				  } while (p > L);
				  
				  return 1/(k - 1);
	    }
	    /// this is the gaussian distribution for service time
	    public static double GaussianDistribs(double ts) {
	    	Random r = new Random();
	    	double x = r.nextGaussian()*sigma + ts;
	    	return x;
	    } 
	    // Our monitor
	    public void monitorHandler(PrintWriter out) {
	    	int cur_q = schedule.size(); // get the current number of people in the queue
	    	int cur_w = (cur_q > 0)? (schedule.size() - 1) : 0;
	    	this.q += cur_q; 
	    	this.w += cur_w;
	    	
	    	checkpoints.remove(0); // remove the current checkpoint
	    	
	    	out.println("\tnum waiting (w): " + cur_w);
	    	out.println("\tcurrent queue size " + cur_q);
	    	
	    }
	    
	    /*
	     *  Main Simulation function for the queue
	     *
	     * */
	    public void run(PrintWriter out) {
	    	while(this.clock < this.endTime){
	    		if(checkpoints.get(0) < nextArrival && checkpoints.get(0) < nextDeparture) {/// monitor things
	    			this.clock = checkpoints.get(0);
	    			out.println("\tmonitoring at " + this.clock);
	    			monitorHandler(out);
	    		}else if(nextArrival <= nextDeparture) { // arrival
	    			this.clock = this.nextArrival;
	    			out.println("\tarrival at " + this.clock);
	    			arriveHandler(nextArrival);
	    			this.requests++;
	    		}else { // Departs
	    			this.clock  = this.nextDeparture;
	    			out.println("\tdeparture at " + this.clock);
	    			departHandler();
	    			this.serviced++;
	    		}	
	    	}
	    	
	    	printStats(out);
	    	out.close();
	    }
	    
	    
	    public void printStats(PrintWriter out) {
	    	out.println("\nSTATISTICS OF RUN");
	    	out.println("Total number of waiting customers = " + w);
	    	out.println("Total number of customer in the queue = " + q);
	    	out.println("Total waiting time = " + Tw);
	    	out.println("Total time in the queue = " + Tq);
	    	out.println("Total service time = " + Ts);

	    	System.out.println("\nSTATISTICS OF RUN");
	    	System.out.println("Total number of waiting customers = " + w);
	    	System.out.println("Total number of customer in the queue = " + q);
	    	System.out.println("Total waiting time = " + Tw);
	    	System.out.println("Total time in the queue = " + Tq);
	    	System.out.println("Total service time = " + Ts);
	    }

}
