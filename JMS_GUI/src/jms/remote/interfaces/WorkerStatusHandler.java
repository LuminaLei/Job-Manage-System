package jms.remote.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jms.model.WorkerStatus;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public interface WorkerStatusHandler extends Remote {
	public WorkerStatus getWorkerStatus(String password) throws RemoteException;
	public void terminateJob(String password) throws RemoteException;
}

