package jms.algorithm;

import java.util.ArrayList;

import jms.model.JobList;
import jms.model.Queue;

public class Scheduler {
	private ArrayList<Integer> readyQueue;
	private int algorithm = 0;
	
	private Queue queue;
	private JobList total_job_list;
	
	public void init(JobList total_job_list, Queue queue){
		this.total_job_list = total_job_list;
		this.queue = queue;
	}
	
	public Scheduler(JobList total_job_list, Queue queue, int algorithm) {
		this.total_job_list = total_job_list;
		this.queue = queue;
		this.algorithm = algorithm;
	}
	
	public void choose(){
		int i = 0;
		int element = 0;
		//int algorithm = 0;
		int length = queue.length();
		
		
		
		if(algorithm ==1 || algorithm==2 || algorithm==3){
			readyQueue = new ArrayList<Integer>(0);
			for (i = 0; i < length; i++) {
				element = queue.pop();
				readyQueue.add(i, element);
				//readyQueue.add(element);
			}
		}
//		System.out.println("Choose a algorithm:");
//		System.out.println("1. Earliest Deadline First");
//		System.out.println("2. Earliest Deadline First constrained by Budget");
//		System.out.println("3. Priority Queue");
//		System.out.println("4. Fist come, First service");
//		String ch = System.console().readLine();
//		algorithm = Integer.parseInt(ch);
		
		switch (algorithm) {
		case 1:
			EDF();
			break;
		case 2:
			EDF_CB();
			break;

		case 3:
			PQ();
			break;

		default:
			//FCFS();
			break;
		}
		// set new queue
		//queue.update(new Queue());
//		for (i = 0; i < readyQueue.size(); i++) {
//			element = readyQueue.get(i);
//			queue.push(element);
//			System.out.println(element);
//		}
		
		if(algorithm ==1 || algorithm==2 || algorithm==3){
			for (i = 0; i < readyQueue.size(); i++) {
				element = readyQueue.get(i);
				queue.push(element);
				System.out.println(element);
			}
		}
	}
	
	public void EDF() {
		int i = 0;
		int j = 0;
		int element = 0;
		for (i = 0; i < readyQueue.size() - 1; i++) {
			for (j = 0; j < readyQueue.size() - i - 1; j++) {
				if (total_job_list.getJob(readyQueue.get(j)).getDeadlineMil() > total_job_list
						.getJob(readyQueue.get(j + 1)).getDeadlineMil()) {
					element = readyQueue.get(j);
					readyQueue.set(j, readyQueue.get(j + 1));
					readyQueue.set(j + 1, element);
				}
			}
		}
	}

	public void EDF_CB() {
		int i = 0;
		int j = 0;
		int element = 0;
		for (i = 0; i < readyQueue.size() - 1; i++) {
			for (j = 0; j < readyQueue.size() - i - 1; j++) {
				if (total_job_list.getJob(readyQueue.get(j)).getDeadlineMil() > total_job_list
						.getJob(readyQueue.get(j + 1)).getDeadlineMil()) {
					element = readyQueue.get(j);
					readyQueue.set(j, readyQueue.get(j + 1));
					readyQueue.set(j + 1, element);
				}
				if (total_job_list.getJob(readyQueue.get(j)).getDeadlineMil() == total_job_list
						.getJob(readyQueue.get(j + 1)).getDeadlineMil()) {
					if (total_job_list.getJob(readyQueue.get(j)).getBudget() < total_job_list
							.getJob(readyQueue.get(j + 1)).getBudget()) {
						element = readyQueue.get(j);
						readyQueue.set(j, readyQueue.get(j + 1));
						readyQueue.set(j + 1, element);
					}
				}
			}
		}
	}

	public void PQ() {
		int i = 0;
		int j = 0;
		int element = 0;
		for (i = 0; i < readyQueue.size() - 1; i++) {
			for (j = 0; j < readyQueue.size() - i - 1; j++) {
				if (total_job_list.getJob(readyQueue.get(j)).getPriority() < total_job_list
						.getJob(readyQueue.get(j + 1)).getPriority()) {
					element = readyQueue.get(j);
					readyQueue.set(j, readyQueue.get(j + 1));
					readyQueue.set(j + 1, element);
				}
			}
		}
	}

	public void FCFS() {
		int i = 0;
		int j = 0;
		int element = 0;
		for (i = 0; i < readyQueue.size() - 1; i++) {
			for (j = 0; j < readyQueue.size() - i - 1; j++) {
				if (readyQueue.get(j) > readyQueue.get(j + 1)) {
					element = readyQueue.get(j);
					readyQueue.set(j, readyQueue.get(j + 1));
					readyQueue.set(j + 1, element);
				}
			}
		}
	}
}
