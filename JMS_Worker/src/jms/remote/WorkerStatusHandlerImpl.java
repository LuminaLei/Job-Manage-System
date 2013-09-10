package jms.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import jms.model.WorkerStatus;
import jms.remote.interfaces.WorkerStatusHandler;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class WorkerStatusHandlerImpl extends UnicastRemoteObject 
										implements WorkerStatusHandler {
	
	private String password;
	private WorkerStatus status;
	
	public WorkerStatusHandlerImpl(String password, WorkerStatus status) throws RemoteException {
		this.password = password;
		this.status = status;
	}
	
	public WorkerStatus getWorkerStatus(String password) throws RemoteException{
		if(this.password.equals(password)==false){
			return null;
		}
		return this.status;
	}
	
	public WorkerStatus terminateJob(String password) throws RemoteException{
		this.status.terminate();
		return this.status;
	}
}

