package jms.model;

import jms.util.FileUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class JobList {
	private JSONArray list = null;
	
	/**
	 * Construct a JobList object without initialized the job list array
	 */
	public JobList(){
		//this.list = new JSONArray();
	}
	
	/**
	 * Generate a new JobList object with initialized list array
	 * @return JobList object
	 */
	public static JobList getInstance(){
		JobList list = new JobList();
		list.list = new JSONArray();
		return list;
	}
	
	/**
	 * Construct a JobList object with a valid job list string
	 * @param str job list string
	 */
	public JobList(String str){
		try {
			this.list = new JSONArray(str);
		} catch (JSONException e) {
			this.list = null;
		}
	}
	
	/**
	 * Filter illegal jobs from list
	 * @return true if finish filtering successfully; false otherwise
	 */
	public boolean filterJobs(){
		if(this.list==null) return false;
		Job tmp = new Job();
		int jid;
		JSONObject jo;
		for(int i=0; i<this.length();i++){
			jid = i;
			while(true){
				try {
					jo = this.list.getJSONObject(i);
					tmp.setJob(jid, jo);
					if (tmp.validate() == false) {
						System.out.println("jid: "+jid+" was filtered.");
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
	 * Get the number of jobs in job list
	 * @return The length (or size).
	 */
	public synchronized int length(){
		if(this.list == null) return 0;
		return this.list.length();
	}
	
	/**
	 * Add a job to job list
	 * @param job
	 * @return job id
	 */
	public synchronized int addJob(JSONObject job){
		if(this.list == null) this.list = new JSONArray();
		this.list.put(job);
		return this.list.length() - 1;
	}
	
	public synchronized boolean addJob(int jobIndex, JSONObject job){
		if(this.list == null) this.list = new JSONArray();
		try {
			this.list.put(jobIndex, job);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get a job from job list by id
	 * @param jobIndex Job id
	 * @return job
	 */
	public synchronized Job getJob(int jobIndex){
		if(this.list == null) return null;
		try {
			return new Job(jobIndex, this.list.getJSONObject(jobIndex));
		} catch (JSONException e) {
			return null;
		}
	}
	
	public synchronized JSONObject getJobInfoObject(int jobIndex){
		if(this.list == null) return null;
		try {
			return this.list.getJSONObject(jobIndex);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Remove a job from job list
	 * @param jobIndex
	 * @return true if successfully, otherwise false.
	 */
	public synchronized boolean removeJob(int jobIndex){
		if(this.list == null) return false;
		if(jobIndex < 0 || jobIndex >= this.list.length()) return false;
		this.list.remove(jobIndex);
		return true;
	}
	
	/**
	 * Make a JSON text of this JSONArray. For compactness, no
     * unnecessary whitespace is added. If it is not possible to produce a
     * syntactically correct JSON text then null will be returned instead. This
     * could occur if the array contains an invalid number.
     * <p>
     * 
     * @return a printable, displayable, transmittable
     *  representation of the array.
	 */
	public String toString(){
		if(this.list == null) return null;
		return this.list.toString();
	}
	
	/**
	 * Make a pretty printed JSON text of this JSONArray.
	 * @param indentFactor The number of spaces to add to each level of indentation.
	 * @return a printable, displayable, transmittable
     *  representation of the object, beginning
     *  with <code>[</code>&nbsp;<small>(left bracket)</small> and ending
     *  with <code>]</code>&nbsp;<small>(right bracket)</small>.
	 */
	public String toString(int indentFactor){
		if(this.list == null) return null;
		try {
			return this.list.toString(4);
		} catch (JSONException e) {
			return toString(indentFactor);
		}
	}
	
	/**
	 * Save job list to Disk
	 * @param fileName
	 * @return true if successfully, otherwise false.
	 */
	public synchronized boolean saveAs(String fileName){
		byte[] data = this.toString(4).getBytes();
		return FileUtil.saveToFile(fileName, data);
	}
	
	/**
	 * Load job list from file
	 * @param fileName
	 * @return true if successfully, otherwise false.
	 */
	public synchronized boolean load(String fileName){
		this.list = JobList.read(fileName);
		if(this.list == null) return false;
		return true;
	}
	
	/**
	 * Read job list from file
	 * @param fileName
	 * @return job list
	 */
	public static JSONArray read(String fileName) {
		try {
			return new JSONArray(FileUtil.read(fileName));
		} catch (JSONException e) {
			return null;
		}
	}
	
}

