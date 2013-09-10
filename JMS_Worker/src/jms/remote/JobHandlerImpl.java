package jms.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import jms.model.WorkerStatus;
import jms.remote.interfaces.JobHandler;
import jms.remote.interfaces.WorkerStatusHandler;
import jms.util.SysInfo;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class JobHandlerImpl extends UnicastRemoteObject implements JobHandler {
	
	private String password;
	private WorkerStatus status;
	
	public JobHandlerImpl(String password, WorkerStatus status) throws RemoteException {
		this.password = password;
		this.status = status;
	}
	
	public boolean run(int id, String cmd, String password) throws RemoteException {
		if(this.password.equals(password)==false){
			return false;
		}
		// set worker status
		if(status != null && this.status.getState().equals(WorkerStatus.RUNNING)==false){
			this.status.terminate();
			return false;
		}
		if(this.status.getCurrentJobState().equals(WorkerStatus.Terminated)){
			return false;
		}
		SysInfo.run(cmd);
		SysInfo.run("perl cleanup.pl");
		return true;
	}
}

