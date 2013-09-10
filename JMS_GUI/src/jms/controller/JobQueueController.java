package jms.controller;

import jms.algorithm.Scheduler;
import jms.model.JobList;
import jms.model.Queue;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class JobQueueController implements Runnable {

	private Queue queue;
	private JobList total_job_list;
	
	private boolean hasNewJobs = false;
	private Scheduler scheduler = null;
	
	private int algorithm;
	
	/**
	 * notice the controller that new jobs are incoming
	 */
	public synchronized void noticeNewJobs(){
		queue.setWait();
		this.hasNewJobs = true;
	}
	
	/**
	 * Reset new jobs notice after finishing scheduling new jobs
	 */
	public synchronized void noticeReset(){
		this.hasNewJobs = false;
	}
	
	/**
	 * Constructor
	 * @param total_job_list The total job list, 
	 * contains all jobs including finished and unfinished jobs
	 * @param queue The waiting queue for which assigns jobs to workers
	 */
	public JobQueueController(JobList total_job_list, Queue queue, int algorithm){
		this.total_job_list = total_job_list;
		this.queue = queue;
		this.algorithm = algorithm;
	}
	
	/**
	 * Update the waiting queue after rescheduling
	 * @param queue New waiting queue
	 */
	public synchronized void update(Queue queue){
		this.queue = queue;
	}
	
	//TODO 
	public synchronized void loadMore(){
		
	}
	
	public void run() {
		while(true){
			if(hasNewJobs==false){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					continue;
				}
				continue;
			}
			else if(queue.length() < 1){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					continue;
				}
				continue;
			}
			// found new job
			else{
				queue.setWait();
				if(scheduler==null) scheduler = new Scheduler(total_job_list,queue,algorithm);
				scheduler.init(total_job_list, queue);
				scheduler.choose();
				queue.setReady();
				noticeReset();
			}
			
		}//end of while
	}
}

