package jms.model;

import jms.controller.WorkerController;
import jms.util.FileUtil;
import jms.util.Hash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class WorkerList {
	private JSONArray list = null;
	
	public WorkerList(){
		//this.list = new JSONArray();
	}
	
	public static WorkerList getInstance(){
		WorkerList list = new WorkerList();
		list.list = new JSONArray();
		return list;
	}
	
	public WorkerList(String str){
		try {
			this.list = new JSONArray(str);
		} catch (JSONException e) {
			this.list = null;
		}
	}
	
	// TODO
	public boolean filterWorkers(){
		if(this.list==null) return false;
		WorkerController tmp = new WorkerController();
		int wid;
		JSONObject jo;
		for(int i=0; i<this.length();i++){
			wid = i;
			while(true){
				try {
					jo = this.list.getJSONObject(i);
					tmp.setWorker(wid, jo);
					if (tmp.validate() == false) {
						System.out.println("wid: "+wid+" was filtered.");
						this.list.remove(i);
						continue;
					}
					else{
						break;
					}
				} catch (JSONException e) {
					break;
				}
			}//end while
			
		}//end for
		return true;
	}
	
	
	/**
	 * Get the number of workers in worker list
	 * @return The length (or size).
	 */
	public int length(){
		if(this.list == null) return 0;
		return this.list.length();
	}
	
	/**
	 * Add a worker to worker list
	 * @param worker
	 * @return worker index
	 */
	public int addWorker(JSONObject worker){
		if(this.list == null) this.list = new JSONArray();
		this.list.put(worker);
		return this.list.length() - 1;
	}
	
	public boolean addWorker(int workerIndex, JSONObject worker){
		if(this.list == null) this.list = new JSONArray();
		if(workerIndex < 0) return false;
		try {
			this.list.put(workerIndex, worker);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get a worker from worker list by index
	 * @param workerIndex
	 * @return worker
	 */
	public JSONObject getWorker(int workerIndex){
		if(this.list == null) return null;
		try {
			return this.list.getJSONObject(workerIndex);
		} catch (JSONException e) {
			return null;
		}
	}
	
	// TODO .......
//	public JSONObject getFirstWorker(){
//		if(this.list == null) return null;
//		try {
//			if( this.list.length() < 1) return null;
//			return this.list.getJSONObject(0);
//		} catch (JSONException e) {
//			return null;
//		}
//	}
	
	/**
	 * Remove a worker from worker list
	 * @param workerIndex
	 * @return true if successfully, otherwise false.
	 */
	public boolean removeWorker(int workerIndex){
		if(this.list == null) return false;
		if(workerIndex < 0 || workerIndex >= this.list.length()) return false;
		this.list.remove(workerIndex);
		return true;
	}
	
	public String toString(){
		if(this.list == null) return null;
		return this.list.toString();
	}
	
	public String toString(int indentFactor){
		if(this.list == null) return null;
		try {
			return this.list.toString(4);
		} catch (JSONException e) {
			return toString(indentFactor);
		}
	}
	
	/**
	 * Save worker list to Disk
	 * @param fileName
	 * @return true if successfully, otherwise false.
	 */
	public boolean saveAs(String fileName){
		byte[] data = this.toString(4).getBytes();
		return FileUtil.saveToFile(fileName, data);
	}
	
	/**
	 * Load worker list from file
	 * @param fileName
	 * @return true if successfully, otherwise false.
	 */
	public boolean load(String fileName){
		this.list = WorkerList.read(fileName);
		if(this.list == null) return false;
		return true;
	}
	
	/**
	 * Read worker list from file
	 * @param fileName
	 * @return worker list
	 */
	public static JSONArray read(String fileName) {
		try {
			return new JSONArray(FileUtil.read(fileName));
		} catch (JSONException e) {
			return null;
		}
	}
	
	

	/**
	 * Create a worker
	 * @param name
	 * @param address
	 * @param port
	 * @param password
	 * @param price
	 * @return worker object
	 */
	public static JSONObject createWorker(String name, String address, int port,
											String password, double price){
		// check
		if(price < 0) return null;
		if(port < 1024 || port > 65535) return null;
		if(name==null) return null;
		if(address==null) return null;
		if(password==null) return null;
		
		JSONObject worker = new JSONObject();
		try {
			worker.put(WorkerController.NAME, name);
			worker.put(WorkerController.ADDRESS, address);
			worker.put(WorkerController.PORT, port);
			worker.put(WorkerController.PASSWORD, password);
			worker.put(WorkerController.PRICE, price);
		} catch (JSONException e) {
			return createWorker(name, address, port, password, price);
		}
		return worker;
	}
	
	public static void main(String[] args){
		WorkerList wlist = new WorkerList();
		wlist.addWorker(
				WorkerList.createWorker(
						"localhost", "localhost", 1099, Hash.MD5("12345"), 5.5));
		wlist.addWorker(
				WorkerList.createWorker(
						"localhost", "localhost", 1100, Hash.MD5("12345"), 8.5));
		wlist.saveAs("workerList2.wl");
	}
}

