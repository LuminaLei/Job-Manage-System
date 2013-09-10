package jms.gui.controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import jms.controller.WorkerController;
import jms.gui.JobStutsPanel;
import jms.model.Job;
import jms.model.JobList;
import jms.model.JobStateCache;
import jms.model.Queue;
import jms.model.WorkerStatus;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class WorkerStatusMonitor implements Runnable {
	
	WorkerController[] workerControllers;
	JobList total_jobs_list;
	
	JList workingServerJList;
	JLabel workingStutsLabel;
	JLabel workingPriceLabel;
	JLabel workingJobLabel;
	JLabel workingjobfinishedLabel;
	//JPanel workingServerInfo;
	ArrayList<JobStutsPanel> jobStuts;
	JobStateCache jobStateCache;
	JPanel jobWorkingPanel;
	
	Queue workerQueue;
	
	public WorkerStatusMonitor(WorkerController[] workers, JobList total_jobs_list, 
			JList workingServerJList,
			JLabel workingStutsLabel,
			JLabel workingPriceLabel,
			JLabel workingJobLabel,
			JLabel workingjobfinishedLabel
			//,JPanel workingServerInfo
			,ArrayList<JobStutsPanel> jobStuts
			,JobStateCache jobStateCache
			,JPanel jobWorkingPanel
			,Queue workerQueue
			){
		this.workerControllers = workers;
		this.total_jobs_list = total_jobs_list;
		this.workingServerJList = workingServerJList;
		this.workingStutsLabel=workingStutsLabel;
		this.workingPriceLabel=workingPriceLabel;
		this.workingJobLabel=workingJobLabel;
		this.workingjobfinishedLabel=workingjobfinishedLabel;
		
		this.jobStuts=jobStuts;
		this.jobStateCache=jobStateCache;
		this.jobWorkingPanel=jobWorkingPanel;
		this.workerQueue=workerQueue;
	}

	public void run() {
		if (workerControllers != null && workerControllers.length > 0) {
			WorkerStatus status =null;// = new WorkerStatus();
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					continue;
				}

				// System.out.println("monitor...test....");

				//int jid = 0;
				int wid;
				if (workingServerJList != null)
					wid = workingServerJList.getSelectedIndex();
				else {
					// System.out.println("monitor...-------------....");
					continue;
				}
				// for(int wid=0;wid<workers.length;wid++){
				// status = workers[wid].getStatus();
				// jid = status.getID();
				//
				// }
				
//				if (wid >= 0 && workers != null && wid < workers.length
//						&& workers[wid] != null
//						&& workers[wid].isAlive()
//						)
//					status = workers[wid].getStatus();
//				else {
//					// System.out.println("monitor...00000000....");
//					continue;
//				}
				// System.out.println("monitor...111111....");
				
				
				String tmpstate = WorkerStatus.DEAD;
				int tmpjid = -1;
				int tmpnumofdone = -1;
				if (wid < 0 || workerControllers == null || wid >= workerControllers.length
						|| workerControllers[wid] == null
						){
					continue;
				}
				else if( workerControllers[wid].isAlive()==true){
					// worker is alive, get status
					status = workerControllers[wid].getStatus();
					if(status != null ){
						tmpstate = status.getState();
						tmpjid = status.getID();
						tmpnumofdone = status.getNumOfDone();
					}
					else{
						for(int wq=0;wq<workerQueue.length();wq++){
							if(wid==workerQueue.getAt(wq)){
								workerQueue.removeAt(wid);
								break;
							}
						}
					}
				}
				else{
					for(int wq=0;wq<workerQueue.length();wq++){
						if(wid==workerQueue.getAt(wq)){
							workerQueue.removeAt(wid);
							break;
						}
					}
				}
				
				if (workingStutsLabel != null)
					workingStutsLabel.setText(tmpstate);
				if (workingPriceLabel != null)
					workingPriceLabel.setText("$"+workerControllers[wid].getPrice()
							+ " /sec");
				if (workingJobLabel != null)
					workingJobLabel.setText("ID: " + tmpjid);
				if (workingjobfinishedLabel != null)
					workingjobfinishedLabel.setText(tmpnumofdone + "");

				// workingServerInfo

				// job state
				// jobStuts
				if (jobStuts.size() > 0) {

					Job job;
					for (int i = 0; i < jobStuts.size(); i++) {

						String state = jobStateCache.getJobState(i);
						jobStuts.get(i).changeStuts(state);
						int w = jobStateCache.getWhichWorker(i);
						String tips = "<html>";
						job = total_jobs_list.getJob(i);
						tips += "Jid: " + i + "<br />";
						tips += "Due time: " + job.getDeadline() + "<br />";
						
						double bgt = job.getBudget();
						bgt = (double) ((int) (bgt * 100) / 100.0);
						tips += "Budget: $" + bgt + "<br />";
						tips += "Priority: " + job.getPriority() + "<br />";
						if (w >= 0 && workerControllers != null && w < workerControllers.length) {
							tips += "Processed by worker: "
									+ workerControllers[w].getWorkerName() + "<br />";
						}
						long st = jobStateCache.getStartTimeMil(i);
						long ct = -1;
						if (st != -1) {
							tips += "Start Time: " + new Timestamp(st)
									+ "<br />";
						}
						long ft = jobStateCache.getFinishTimeMil(i);
						if (ft != -1) {
							ct = ft - st;
							tips += "Finish Time: " + new Timestamp(ft)
									+ "<br />";
						}
						if (ct != -1 && w >= 0 && workerControllers != null
								&& w < workerControllers.length) {
							double cost = workerControllers[w].getPrice() * ct / 1000;
							cost = (double) ((int) (cost * 100) / 100.0);
							tips += "Cost: $" + cost + "<br />";
						}

						tips += "</html>";
						jobStuts.get(i).setToolTipText(tips);
					}
				}
			}
			// end of while
		}
	}
}

