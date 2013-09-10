package jms.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import jms.gui.MasterGUI;
import jms.model.Job;
import jms.model.JobStateCache;
import jms.model.Queue;
import jms.model.WorkerStatus;
import jms.remote.interfaces.FileHandler;
import jms.remote.interfaces.FileHandlerRegistry;
import jms.remote.interfaces.JobHandler;
import jms.remote.interfaces.WorkerStatusHandler;
import jms.util.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class WorkerController implements Runnable {
	
	private final static String File_Handler_Name = "FileHandler";
	private final static String File_Handler_Registry_Name = "FileHandlerRegistry";
	private final static String Job_Handler_Name = "JobHandler";
	private final static String Worker_Status_Handler_Name = "WorkerStatusHandler";
	
	public final static String NAME = "name";
	public final static String ADDRESS = "address";
	public final static String PORT = "port"; // int
	public final static String PASSWORD = "pwd";
	public final static String PRICE = "price"; //double
	
	private JSONObject workerobj = null;
	private int wid ;
	private int jid;
	private Job job;
	private Queue queue;
	private Queue workerQueue;
	
	private WorkerStatusHandler workerStatusHandler;
	private FileHandler fileHanler;
	private FileHandlerRegistry fileHanlerRegistry;
	private JobHandler jobHandler;
	
	private JobStateCache jobStateCache;
	private JobQueueController jobQueueController;
	private boolean ready = true;
	private boolean dead = false;
	
	/**
	 * Check if the worker is dead or alive.
	 * @return true if alive, false if dead.
	 */
	public boolean isAlive(){
		if(dead==true){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Get/Connect to remote objects
	 * @param uploaderHandler local created remote object
	 * @return
	 */
	public boolean connect(FileHandler uploaderHandler){
		String url = this.getURL();
		try {
			this.workerStatusHandler = (WorkerStatusHandler)Naming.lookup(url + Worker_Status_Handler_Name);
			this.fileHanler = (FileHandler)Naming.lookup(url + File_Handler_Name);
			this.fileHanlerRegistry = (FileHandlerRegistry)Naming.lookup(url + File_Handler_Registry_Name);
			this.fileHanlerRegistry.registry(uploaderHandler, this.getPassword());
			this.jobHandler = (JobHandler)Naming.lookup(url + Job_Handler_Name);
			return true;
		} catch (MalformedURLException e) {
			System.err.println("Worker address is incorrect!\n");
			dead = true;
			return false;
		} catch (IOException e) {
			System.err.println("Get Remote Stubs failed!\n");
			dead = true;
			return false;
		} catch (NotBoundException e) {
			System.err.println("No such name in remote registry!\n");
			dead = true;
			return false;
		}
	}
	
	/**
	 * Get worker status (contains job status)
	 * @return The Worker status and Job status ; null if failed
	 */
	public WorkerStatus getStatus(){
		try {
			//System.out.println("Place 1");
			return this.workerStatusHandler.getWorkerStatus(this.getPassword());
			
		} catch (IOException e) {
			//System.out.println("Place 2");
			e.printStackTrace();
			dead = true;
			return null;
		}
	}
	
	/**
	 * Cancel a job
	 * @return The Worker status and Job status
	 */
	public void terminateJob(){
		try {
			this.workerStatusHandler.terminateJob(this.getPassword());
		} catch (IOException e) {
			dead = true;
			//return null;
		}
	}
	
	/**
	 * Upload a job necessaries (program and input files if have)
	 * @param jid
	 * @param job
	 * @return true if successfully ; false if failed
	 */
	public boolean upload(int jid, Job job){
		try {
			boolean flag = false;
			// upload program
			flag = this.fileHanlerRegistry.uploadFile(
						jid,
						job.getProgramOrigString(),
						job.getProgramDestString(), 
						this.getPassword());
			if(flag==false) return false;
			// upload input files
			for (int i = 0; i < job.getNumberOfInputs(); i++) {
				flag = this.fileHanlerRegistry.uploadFile(
						jid,
						job.getInputOrigString(i),
						job.getInputDestString(i), 
						this.getPassword());
				if(flag==false) return false;
			} // end of upload input files
			return flag;
		} catch (IOException e) {
			dead = true;
			return false;
		}
	}
	
	/**
	 * Run the job
	 * @param jid
	 * @param job
	 * @return true if successfully ; false if failed
	 */
	public boolean run(int jid, Job job){
		try {
			return this.jobHandler.run(jid, job.cmdString(), this.getPassword());
		} catch (IOException e) {
			dead = true;
			return false;
		}
	}
	
	/**
	 * Download the job results
	 * @param jid
	 * @param job
	 * @return true if successfully ; false if failed
	 */
	public boolean download(int jid, Job job){
		try {
			byte[] tmp =null;
			tmp = this.fileHanler.downloadFile(
					jid, 
					job.getResultFileString(), 
					this.getPassword());
			
			if(tmp==null)return false;
			return FileUtil.saveToFile(
					job.getResultFileString(), tmp
					);
		} catch (IOException e) {
			dead = true;
			return false;
		}
	}
	
	/**
	 * Construct a empty worker object
	 */
	public WorkerController(){
	}
	
	/**
	 * Construct a worker object with worker id and worker info object
	 * @param wid
	 * @param worker
	 */
	public WorkerController(int wid, JSONObject worker){
		this.wid = wid;
		this.workerobj = worker;
	}
	
	/**
	 * Return a Worker object initialized with an empty worker info object
	 * @return Worker
	 */
	public static WorkerController getInstance(){
		WorkerController workerController = new WorkerController();
		workerController.workerobj = new JSONObject();
		return workerController;
	}
	
	/**
	 * Return Worker info object
	 * @return JSONObject
	 */
	public JSONObject getWorker(){
		return this.workerobj;
	}
	
	/**
	 * Setup a worker object with worker id and worker info object
	 * @param wid
	 * @param worker
	 */
	public void setWorker(int wid, JSONObject worker){
		this.wid = wid;
		this.workerobj = worker;
	}
	
	/**
	 * Get worker id
	 * @return wid
	 */
	public int ID(){
		return this.wid;
	}
	
	/**
	 * Set worker's name in worker info object
	 * @param name
	 * @return true if successfully ; false if failed
	 */
	public boolean setWorkerName(String name){
		try {
			this.workerobj.put(WorkerController.NAME, name);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get worker's name in worker info object
	 * @return Name in String
	 */
	public String getWorkerName(){
		try {
			return this.workerobj.getString(WorkerController.NAME);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Set worker's address in worker info object
	 * @param address
	 * @return true if successfully ; false if failed
	 */
	public boolean setAddress(String address){
		try {
			this.workerobj.put(WorkerController.ADDRESS, address);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get worker's address in worker info object
	 * @return Address in String
	 */
	public String getAddress(){
		try {
			return this.workerobj.getString(WorkerController.ADDRESS);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Set worker's port in worker info object
	 * @param port
	 * @return true if successfully ; false if failed
	 */
	public boolean setPort(int port){
		if(port < 1024) return false;
		try {
			this.workerobj.put(WorkerController.PORT, port);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get worker's port in worker info object
	 * @return port in int
	 */
	public int getPort(){
		try {
			return this.workerobj.getInt(WorkerController.PORT);
		} catch (JSONException e) {
			return 0;
		}
	}
	
	/**
	 * Set worker's one-way hashed password in worker info object
	 * @param password
	 * @return true if successfully ; false if failed
	 */
	public boolean setPassword(String password){
		try {
			this.workerobj.put(WorkerController.PASSWORD, password);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get worker's password in worker info object
	 * @return One-way hashed password in string
	 */
	public String getPassword(){
		try {
			return this.workerobj.getString(WorkerController.PASSWORD);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Set worker's price in worker info object
	 * @param price
	 * @return true if successfully ; false if failed
	 */
	public boolean setPrice(double price){
		if(price < 0) return false;
		try {
			this.workerobj.put(WorkerController.PRICE, price);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get worker's price in worker info object
	 * @return price in float number
	 */
	public double getPrice(){
		try {
			return this.workerobj.getDouble(WorkerController.PRICE);
		} catch (JSONException e) {
			return -1;
		}
	}
	
	/**
	 * Get worker's remote object URL
	 * @return
	 */
	public String getURL(){
		String url = "rmi://";
		url += this.getAddress()+":"+this.getPort()+"/";
		return url;
	}
	
	public void run() {
		
		try{
			ready = false;
			// upload,run,download.
			// if failed, re-schedule.
			if(!this.upload(this.jid, this.job) /**upload program and input files**/
					|| !this.run(this.jid, this.job) /**execute the job on worker**/
					|| !this.download(this.jid, this.job) /**download the result**/
					){
				// failed, re-schedule.
				jobStateCache.addTerminatedJob(jid,wid,System.currentTimeMillis());
				jobStateCache.removeRunningJob(jid);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
				}
				
				if(queue.push(this.jid)==false){
					// blocked, means this job is failed because 
					// user cancelled it, so do not rescheduling
					System.out.println("Cancelled by user, no rescheduling needed.");
				}
				else{
					// TODO rescheduling ...............
					jobQueueController.noticeNewJobs();
					jobStateCache.removeFinishTime(this.jid);
					jobStateCache.removeTerminatedJob(jid);
				}
				
			}
			else{
				//workerQueue.push(this.wid);
				jobStateCache.addFinishedJob(jid,wid,System.currentTimeMillis());
				jobStateCache.removeRunningJob(jid);
				MasterGUI.increase_n_finished_jobs();
			}
		}
		finally{
			if(isAlive()){
				workerQueue.push(this.wid);
				ready = true;
			}
		}
		
//		WorkerStatus ws =this.getStatus();
//		System.out.println("Status of Job: "+this.jid
//				+" at Worker: "+this.wid+"("+this.getWorkerName()+")"
//				+" ...");
//		if(ws!=null){
//			System.out.println(ws.toString());
//			System.out.println("----------------------------------");
//		}
	}
	
	/**
	 * Initialized the worker controller with 
	 * job id, job, 
	 * job waiting queue, 
	 * active worker queue.
	 * @param jid
	 * @param job
	 * @param queue
	 * @param workerQueue
	 */
	public void init(int jid, Job job, Queue queue, Queue workerQueue
			,JobStateCache jobStateCache, JobQueueController jobQueueController) {
		while(true){
			if( ready ){
				break;
			}
			else{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					continue;
				}
			}
		}
		
		this.jid = jid;
		this.job = job;
		this.queue = queue;
		this.workerQueue = workerQueue;
		this.jobStateCache = jobStateCache;
		this.jobQueueController=jobQueueController;
	}

	public boolean validate() {
		if(this.workerobj == null) return false;
		if(this.getAddress()==null)return false;
		if(this.getPort() < 1024 || this.getPort() > 65535) return false;
		if(this.getPrice() < 0) return false;
		if(this.getWorkerName()==null) this.setWorkerName(this.getAddress()+":"+this.getPort());
		
		return true;
	}
}

