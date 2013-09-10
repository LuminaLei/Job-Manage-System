package jms.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class WorkerStatus implements Serializable {
	
	public final static String RUNNING = "Running";
	public final static String ACTIVE = "Active";
	public final static String DEAD = "Dead";
	
	public final static String Waiting = "Waiting";
	public final static String Running = "Running";
	public final static String Finished = "Finished";
	public final static String Terminated = "Terminated";
	
	private String state = DEAD;
	private int n_done = 0;
	private int n_running = 0;
	private int id = -1;
	
	private long currentJobStartTime;
	private long currentJobFinishTime;
	private String currentJobState;
	
	public synchronized long getCurrentJobStartTime() {
		return currentJobStartTime;
	}

	public synchronized long getCurrentJobFinishTime() {
		return currentJobFinishTime;
	}

	public synchronized String getCurrentJobState() {
		return currentJobState;
	}
	
	public synchronized void setStartTime(){
		this.currentJobStartTime = System.currentTimeMillis();
	}
	
	public synchronized void setFinishTime(){
		this.currentJobFinishTime = System.currentTimeMillis();
	}
	
	public synchronized void setJobStateRunning(){
		this.currentJobState = Running;
	}
	
	public synchronized void setJobStateFinished(){
		this.currentJobState = Finished;
	}
	
	public synchronized void setJobStateTerminated(){
		this.currentJobState = Terminated;
	}
	
	public WorkerStatus(){
		this.state = ACTIVE;
	}
	
	protected synchronized void setActive(){
		this.state = ACTIVE;
	}
	
	protected synchronized void setRunning(){
		this.state = RUNNING;
	}
	
	protected synchronized void setDead(){
		this.state = DEAD;
	}
	
	public synchronized String getState(){
		return this.state;
	}
	
	private synchronized void setID(int id){
		this.id = id;
	}
	
	public synchronized int getID(){
		return this.id;
	}
	
	public synchronized int getNumOfDone(){
		return this.n_done;
	}
	
	public synchronized int getNumOfRunning(){
		return this.n_running;
	}
	
	private synchronized void setNumOfRunning(int n){
		this.n_running = n;
	}
	
	public synchronized void startRunning(int id){
		this.setID(id);
		this.setStartTime();
		this.setJobStateRunning();
		this.setRunning();
		this.setNumOfRunning(1);
		System.out.println("Starting\ta job: "+this.id
						+"\tCurrently number of finished jobs: "+this.n_done);
	}
	
	public synchronized void terminate(){
		this.setFinishTime();
		this.setJobStateTerminated();
		this.setActive();
		this.setNumOfRunning(0);
		System.out.println("Failing\ta job: "+ this.id
						+"\tCurrently number of finished jobs: "+this.n_done);
	}
	
	public synchronized void finishJob(){
		this.setFinishTime();
		this.setJobStateFinished();
		this.setActive();
		this.setNumOfRunning(0);
		this.n_done ++;
		System.out.println("Finishing\ta job: "+ this.id
						+"\tCurrently number of finished jobs: "+this.n_done);
	}
	
	public synchronized String toString(){
		String out = "";
		out += "Worker state: "+this.getState()+"\n";
		out += "Number of finished jobs at this worker: "+this.n_done+"\n";
		out += "Current job state: "+this.currentJobState+"\n";
		out += "Start time: "+new Timestamp(this.currentJobStartTime)+"\n";
		out += "Finish/Terminate Time: "+new Timestamp(this.currentJobFinishTime)+"\n";
		long cost = this.currentJobFinishTime - this.currentJobStartTime;
		out += "Time cost: "+cost+" ms";
		return out;
	}
}

