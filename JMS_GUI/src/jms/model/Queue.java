package jms.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class Queue {
	
	public final static String READY = "Ready";
	public final static String WAIT = "Wait";
	
	private JSONArray queue = null;
	private String state = WAIT;
	
	private JSONObject blocklist;
	private boolean block = false;
	
	public synchronized void setReady(){
		this.state = READY;
	}
	
	public synchronized void setWait(){
		this.state = WAIT;
	}
	
	public synchronized boolean isReady(){
		if(this.state.equals(READY)) return true;
		else return false;
	}
	
	public Queue(){
		this.queue = new JSONArray();
	}
	
	public Queue(boolean block){
		this.queue = new JSONArray();
		this.block = block;
		if(block)this.blocklist = new JSONObject();
	}
	
	public synchronized void turnBlockOn(boolean block){
		this.block = block;
		if(this.blocklist==null)
			this.blocklist = new JSONObject();
	}
	
	public synchronized boolean isBlockOn(){
		return this.block;
	}
	
	public synchronized boolean update(Queue queue){
		if(queue == null || queue.queue == null) return false;
		this.queue = queue.queue;
		return true;
	}
	
	public synchronized int length(){
		return this.queue.length();
	}
	
	public synchronized boolean hasNext(){
		if( this.length() < 1) return false;
		else return true;
	}
	
	public synchronized boolean hasAt(int index){
		if( index < 0 || index >= this.length() ) return false;
		else return true;
	}
	
	public synchronized int getAt(int index){
		if (this.hasAt(index)){
			try {
				return this.queue.getInt(index);
			} catch (JSONException e) {
				return -1;
			}
		}
		else{
			return -1;
		}
	}
	
	public synchronized boolean removeAt(int index){
		if (this.hasAt(index)){
			this.queue.remove(index);
			return true;
		}
		else{
			return false;
		}
	}
	
	public synchronized boolean push(int id){
		if(this.block==true){
			if(blocklist!= null && this.blocklist.has(""+id)){
				return false;
			}
		}
		this.queue.put(id);
		return true;
	}
	
	public synchronized void block(int id){
		if(this.block==true){
			if(blocklist!= null){
				try {
					blocklist.put(id+"", true);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized boolean isBlocked(int id){
		if(this.block==true){
			if(blocklist!= null && this.blocklist.has(""+id)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
//	public synchronized void putAt(int index, int id){
//		if(index < 0 ) return;
//		try {
//			this.queue.put(index, id);
//		} catch (JSONException e) {
//			return;
//		}
//	}
	
	public synchronized int pop(){
		//System.out.println("this.length(): "+ this.length());
		if(this.length() < 1) return -2;
		try {
			int id = this.queue.getInt(0);
			this.queue.remove(0);
			return id;
		} catch (JSONException e) {
			return -3;
		}
	}
	
	//////////////////////////////////////////////////
	// for test only !
//	public static JobList loadTestJobList(){
//		JobList list = new JobList();
//		list.load("job5.jl");
//		return list;
//	}
//	
//	public static void main(String[] args){
//		
//	}
}

