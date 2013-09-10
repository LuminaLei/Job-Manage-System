package jms.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class JobStateCache {
	public final static String Waiting = "Waiting";
	public final static String Running = "Running";
	public final static String Finished = "Finished";
	public final static String Terminated = "Terminated";
	
	public final static String STATE = "state";
	public final static String WID = "wid";
	
	public final static String StartTime = "st";
	public final static String FinishTime = "ft";
	
	private JSONObject flist;
	private JSONObject rlist;
	private JSONObject tlist;
	
	private JSONObject timelist;
	
	public JobStateCache(){
		flist = new JSONObject();
		rlist = new JSONObject();
		tlist = new JSONObject();
		timelist = new JSONObject();
	}
	
	public synchronized void addFinishedJob(int jid, int wid, long finishtimemil){
		String key = jid+"";
		int value = wid;
		try {
			this.flist.put(key, value);
			key += JobStateCache.FinishTime;
			this.timelist.put(key, finishtimemil);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	
	public synchronized void addRunningJob(int jid, int wid, long starttimemil){
		String key = jid+"";
		int value = wid;
		try {
			this.rlist.put(key, value);
			key += JobStateCache.StartTime;
			this.timelist.put(key, starttimemil);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	
	public synchronized void addTerminatedJob(int jid, int wid, long finishtimemil){
		String key = jid+"";
		int value = wid;
		try {
			this.tlist.put(key, value);
			key += JobStateCache.FinishTime;
			this.timelist.put(key, finishtimemil);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	
	public synchronized boolean removeFinishedJob(int jid){
		String key = jid+"";
		if(flist.has(key)){
			flist.remove(key);
			return true;
		}
		else{
			return false;
		}
	}
	
	public synchronized boolean removeRunningJob(int jid){
		String key = jid+"";
		if(rlist.has(key)){
			rlist.remove(key);
			return true;
		}
		else{
			return false;
		}
	}
	
	public synchronized boolean removeTerminatedJob(int jid){
		String key = jid+"";
		if(tlist.has(key)){
			tlist.remove(key);
			return true;
		}
		else{
			return false;
		}
	}
	
	public synchronized boolean isJobFinished(int jid){
		String key = jid+"";
		if(flist.has(key)) return true;
		else return false;
	}
	
	public synchronized boolean isJobRunning(int jid){
		String key = jid+"";
		if(rlist.has(key)) return true;
		else return false;
	}
	
	public synchronized boolean isJobTerminated(int jid){
		String key = jid+"";
		if(tlist.has(key)) return true;
		else return false;
	}
	
	public synchronized String getJobState(int jid){
		
		if(this.isJobTerminated(jid))return Terminated;
		else if(this.isJobFinished(jid))return Finished;
		else if(this.isJobRunning(jid)) return Running;
		else return Waiting;
		
	}
	
	public synchronized int getWhichWorker(int jid){
		String key = jid+"";
		if(tlist.has(key)){
			try {
				return tlist.getInt(key);
			} catch (JSONException e) {
				return -1;
			}
		}
		else if(flist.has(key)){
			try {
				return flist.getInt(key);
			} catch (JSONException e) {
				return -1;
			}
		}
		else if(rlist.has(key)){
			try {
				return rlist.getInt(key);
			} catch (JSONException e) {
				return -1;
			}
		}
		else{
			return -1;
		}
	}
	
	public synchronized long getStartTimeMil(int jid){
		String key = jid+"";
		key += JobStateCache.StartTime;
		if(timelist.has(key)){
			try {
				return timelist.getLong(key);
			} catch (JSONException e) {
				return -1;
			}
		}
		else{
			return -1;
		}
	}
	
	public synchronized long getFinishTimeMil(int jid){
		String key = jid+"";
		key += JobStateCache.FinishTime;
		if(timelist.has(key)){
			try {
				return timelist.getLong(key);
			} catch (JSONException e) {
				return -1;
			}
		}
		else{
			return -1;
		}
	}
	
	public synchronized boolean removeFinishTime(int jid){
		String key = jid+"";
		key += JobStateCache.FinishTime;
		if(timelist.has(key)){
			timelist.remove(key);
			return true;
		}
		else{
			return false;
		}
	}
}

