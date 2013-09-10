package jms.controller;

import jms.model.Job;
import jms.model.JobList;
import jms.model.JobStateCache;
import jms.model.Queue;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class QueueMonitor implements Runnable {
	
	JobList total_jobs_list;
	Queue workerQueue;
	Queue queue;
	WorkerController[] workerControllers;
	JobStateCache jobStateCache;
	JobQueueController jobQueueController;
	
	
	public QueueMonitor(JobList total_jobs_list, WorkerController[] workers, 
			Queue workerQueue, Queue queue
			,JobStateCache jobStateCache
			,JobQueueController jobQueueController){
		this.total_jobs_list = total_jobs_list;
		this.workerControllers = workers;
		this.workerQueue = workerQueue;
		this.queue = queue;
		this.jobStateCache = jobStateCache;
		this.jobQueueController=jobQueueController;
	}
	
	public void updateJQ(Queue queue){
		this.queue = queue;
	}
	
	public void run() {
		 while(true){
				if(queue.isReady()==false || queue.hasNext()==false){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						continue;
					}
					continue;
				}
				
				while(workerQueue.hasNext()==false){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						continue;
					}
					continue;
				}
				//System.out.println("job was popped: ");
				int jid = queue.pop();
				if(jid < 0 || jid > total_jobs_list.length()) continue;
				//System.out.println("wk was popped: ");
				int wid = workerQueue.pop();
				Job job = total_jobs_list.getJob(jid);
				//System.out.println("monitor popped: ");
				//System.out.println("jid: "+jid);
				//System.out.println("wid: "+wid);
				workerControllers[wid].init(jid, job, queue, workerQueue,jobStateCache,jobQueueController);
				jobStateCache.addRunningJob(jid,wid,System.currentTimeMillis());
				new Thread(workerControllers[wid]).start();
			}
			//end of while
	}

}

