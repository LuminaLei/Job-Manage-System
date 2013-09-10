package jms.model;

import java.io.File;
import java.sql.Timestamp;

import jms.util.SysInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class Job {
	public final static String Executor = "executor";
	public final static String Program = "program";
	public final static String Program_Path = "program_path";
	public final static String Parameters = "params";
	public final static String Where_is_inputs = "inputs";
	public final static String BUDGET = "budget";
	public final static String DEADLINE = "deadline";
	public final static String PRIORITY = "priority";
	
	private JSONObject job = null;
	private int jid ;
	
	/**
	 * Construct a empty job
	 */
	public Job(){
		
	}
	
	/**
	 * Construct a job initialized with job id and job info object
	 * @param jid
	 * @param job
	 */
	public Job(int jid, JSONObject job){
		this.jid = jid;
		this.job = job;
	}
	
	/**
	 * Generate a new Job class object, 
	 * which initialized job filed object with initial values.
	 * @return Job object
	 */
	public static Job getInstance(){
		Job job = new Job();
		job.job = new JSONObject();
		JSONArray ja = new JSONArray();
		try {
			job.job.put(Job.Executor, "");
			job.job.put(Job.Parameters, "");
			job.job.put(Job.Where_is_inputs, ja);
		} catch (JSONException e) {
			return getInstance();
		}
		return job;
	}
	
	/**
	 * Get job info object
	 * @return job info object
	 */
	public JSONObject getJob(){
		return this.job;
	}
	
	/**
	 * Set job info object
	 * @param job
	 */
	public void setJob(JSONObject job){
		this.job = job;
	}
	
	/**
	 * Set program name and original program path
	 * @param prog_path original program path
	 * @return true if successfully; false if failed
	 */
	public boolean setProgram(String prog_path){
		try {
			this.job.put(Job.Program_Path, prog_path);
			File file = new File(prog_path);
			String programName = file.getName();
			this.job.put(Job.Program, programName);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Check whether inputs files info array is initialized; 
	 * initialized it if not.
	 * @return true if initialized; false otherwise
	 */
	public boolean initInputsArray(){
		JSONArray ja;
		if(this.job.has(Job.Where_is_inputs)==false){
			ja = new JSONArray();
		}
		else{
			try {
				ja = this.job.getJSONArray(Job.Where_is_inputs);
			} catch (JSONException e) {
				ja = new JSONArray();
			}
			if(ja==null )ja = new JSONArray();
		}
		try {
			this.job.put(Job.Where_is_inputs, ja);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Add input file's file path
	 * @param input file path
	 * @return true if successfully; false if failed
	 */
	public boolean addInputs(String input){
		if(this.initInputsArray()==false) return false;
		JSONArray ja;
		try {
			ja = this.job.getJSONArray(Job.Where_is_inputs);
		} catch (JSONException e) {
			return false;
		}
		ja.put(input);
		try {
			this.job.put(Job.Where_is_inputs, ja);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get number of input files
	 * @return number in int
	 */
	public int getNumberOfInputs(){
		JSONArray ja;
		try {
			ja = this.job.getJSONArray(Job.Where_is_inputs);
			return ja.length();
		} catch (JSONException e) {
			return 0;
		}
	}
	
	/**
	 * Get original input file path from file path array by index
	 * @param index
	 * @return original file path in String
	 */
	public String getInputOrigString(int index){
		JSONArray ja;
		try {
			ja = this.job.getJSONArray(Job.Where_is_inputs);
			return ja.getString(index);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Construct destination input file path for specified index of input file
	 * @param index
	 * @return destination file path in String
	 */
	public String getInputDestString(int index){
		JSONArray ja;
		try {
			ja = this.job.getJSONArray(Job.Where_is_inputs);
			String from = ja.getString(index);
			File file = new File(from);
			String name = file.getName();
			String inputTo = "";
			inputTo += SysInfo.Dir_inputs+"/" + name;
			return inputTo;
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Setup job object with job id and job info object
	 * @param jid job id
	 * @param job job info object
	 */
	public void setJob(int jid, JSONObject job){
		this.jid = jid;
		this.job = job;
	}
	
	/**
	 * Set job id
	 * @param jid
	 */
	public void setJobID(int jid){
		this.jid = jid;
	}
	
	/**
	 * Get job id
	 * @return job id
	 */
	public int ID(){
		return this.jid;
	}
	
	/**
	 * Construct the command string with parameters for executing
	 * @return command String
	 */
	public String cmdString(){
		String args = "";
		// TODO 
		args += this.getExecutor()+" "+SysInfo.Dir_exes+"/"+this.getProgramName()+" ";
		args += this.ID()+" ";
		args += this.getParameters();
		return args;
	}
	
	/**
	 * The command string used to grant execute authority for program on Unix/Linux/Mac
	 * @return command string used to grant execute authority
	 */
	public String permissionString(){
		String args = "";
		//args += SysInfo.bin+"/chmod 700 ";
		args += "chmod 700 ";
		args+= SysInfo.Dir_exes+"/"+this.getProgramName();
		return args;
	}
	
	/**
	 * Get program's name
	 * @return program's name in string
	 */
	public String getProgramName(){
		try {
			return this.job.getString(Job.Program);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Get program's parameters
	 * @return program's parameters in string
	 */
	public String getParameters(){
		try {
			return this.job.getString(Job.Parameters);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Set program's parameters
	 * @param params program's parameters
	 * @return true if successfully; false if failed
	 */
	public boolean setParameters(String params){
		if(params ==null) params = "";
		try {
			this.job.put(Job.Parameters, params);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get program's executor. 
	 * <p>i.e. "java" for execute java jar or class object, 
	 * "perl" for run a perl script.
	 * @return executor String; empty string if no executor needed
	 */
	public String getExecutor(){
		try {
			return this.job.getString(Job.Executor);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Set program's executor. 
	 * <p>i.e. "java" for execute java jar or class object, 
	 * <br>"perl" for run a perl script.
	 * <br>Empty string "" if no executor needed.
	 * @param executor
	 * @return true if successfully; false if failed
	 */
	public boolean setExecutor(String executor){
		if(executor ==null) executor = "";
		try {
			this.job.put(Job.Executor, executor);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get original program path
	 * @return original program path
	 */
	public String getProgramOrigString(){
		try {
			return this.job.getString(Job.Program_Path);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Get destination program path
	 * @return destination program path
	 */
	public String getProgramDestString(){
		return SysInfo.Dir_exes+"/"+this.getProgramName();
	}
	
	/**
	 * Construct result file path
	 * @return result file path
	 */
	public String getResultFileString(){
		return SysInfo.Dir_outputs+"/"
				+SysInfo.Result_File_prefix
				+this.ID()+SysInfo.Result_File_suffix;
	}
	
	/**
	 * Set budget in job info object
	 * @param budget budget in $. i.e. 0.50$ = 50 cents
	 * @return true if successfully; false if failed
	 */
	public boolean setBudget(double budget){
		if(budget<0) budget = 0;
		try {
			this.job.put(Job.BUDGET, budget);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get budget in job info object
	 * @return budget; -1 if failed.
	 */
	public double getBudget(){
		try {
			return this.job.getDouble(Job.BUDGET);
		} catch (JSONException e) {
			return -1;
		}
	}
	
	/**
	 * Set deadline in job info object
	 * @param time deadline in Time stamp
	 * @return true if successfully; false if failed
	 */
	public boolean setDeadline(Timestamp time){
		try {
			this.job.put(Job.DEADLINE, time.getTime());
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get deadline in million seconds in job info object
	 * @return deadline in million seconds
	 */
	public long getDeadlineMil(){
		try {
			return this.job.getLong(DEADLINE);
		} catch (JSONException e) {
			return -1;
		}
	}
	
	/**
	 * Get deadline in Time stamp in job info object
	 * @return deadline in Time stamp
	 */
	public Timestamp getDeadline(){
		try {
			long t = this.job.getLong(DEADLINE);
			return new Timestamp(t);
		} catch (JSONException e) {
			return null;
		}
	}
	
	/**
	 * Set priority in job info object. 
	 * Range is 0 - 10 and 10 is the Highest priority.
	 * <p>
	 * If input value < 0, set as 0;<br>
	 * else if input value > 10, set as 10.
	 * @param priority the priority value
	 * @return true if successfully; false if failed
	 */
	public boolean setPriority(int priority){
		if(priority > 10) priority = 10;
		else if(priority < 0) priority = 0;
		try {
			this.job.put(Job.PRIORITY, priority);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get priority in job info object.
	 * @return the priority value in int
	 */
	public int getPriority(){
		try {
			return this.job.getInt(Job.PRIORITY);
		} catch (JSONException e) {
			return 0;
		}
	}
	
	/**
	 * Validate the job info.
	 * @return false, if<li>job info object is null</li>
	 * 					<li>program name is null</li>
	 * 					<li>program path is null</li>
	 * 					<li>input file path array is null (empty is OK)</li>
	 * 					<li>due time is already passed at start time.</li>
	 * 					<li>budget is < 0</li><p/>
	 * 		<br><p>otherwise, true</p>
	 */
	public boolean validate(){
		if(this.job == null) return false;
		if(this.getExecutor()==null) this.setExecutor("");
		if(this.getProgramName()==null) return false;
		if(this.getProgramOrigString()==null) return false;
		if(this.getParameters()==null) this.setParameters("");
		if(this.initInputsArray()==false) return false;
		if(this.getDeadlineMil()<=System.currentTimeMillis()) return false;
		if(this.getBudget() < 0) return false;
		if(this.getPriority() > 10) this.setPriority(10);
		else if(this.getPriority() < 0) this.setPriority(0);
		return true;
	}
	
	/**
	 * Create a new  job info object
	 * @param executor The program's executor
	 * @param program_path The program's original path
	 * @param params The program's parameters
	 * @param inputs The input file path array
	 * @param budget The budget of this job
	 * @param time The deadline of this job
	 * @param priority The priority
	 * @return a job info object
	 */
	public static JSONObject createJob(String executor, String program_path,
										String params, String[] inputs,
										double budget, Timestamp time, int priority){
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		try {
			jo.put(Job.Executor, executor);
			jo.put(Job.Program_Path, program_path);
			
			File file = new File(program_path);
			String programName = file.getName();
			jo.put(Job.Program, programName);
			
			if(inputs != null){
				for(int i=0; i<inputs.length;i++){
					ja.put(inputs[i]);
				}
			}
			jo.put(Job.Parameters, params);
			jo.put(Job.Where_is_inputs, ja);
			jo.put(Job.BUDGET, budget);
			jo.put(Job.DEADLINE, time.getTime());
			jo.put(Job.PRIORITY, priority);
		} catch (JSONException e) {
			return createJob(executor, program_path, params, inputs,
								budget, time, priority);
		}
		return jo;
	}
}

