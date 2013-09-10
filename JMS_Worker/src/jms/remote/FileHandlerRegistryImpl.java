package jms.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import jms.model.WorkerStatus;
import jms.remote.interfaces.FileHandler;
import jms.remote.interfaces.FileHandlerRegistry;
import jms.remote.interfaces.WorkerStatusHandler;
import jms.util.FileUtil;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class FileHandlerRegistryImpl extends UnicastRemoteObject implements FileHandlerRegistry {

	protected FileHandler clientFH = null;
	private String password;
	private WorkerStatus status = null;
	
	public FileHandlerRegistryImpl(String password, WorkerStatus status) throws RemoteException {
		this.password = password;
		this.status = status;
	}
	
	public void registry(FileHandler fh, String password) throws RemoteException {
		if(this.password.equals(password)==false){
			return;
		}
		this.clientFH = fh;
	}

	public boolean uploadFile(int id, String from, String to, String password) throws RemoteException {
		if(this.password.equals(password)==false){
			return false;
		}
		if(this.clientFH == null) return false;
		// set worker status
		if(status != null){
			if(this.status.getState().equals(WorkerStatus.RUNNING)==false &&
						this.status.getState().equals(WorkerStatus.DEAD)==false)
				this.status.startRunning(id);
			else if(this.status.getCurrentJobState().equals(WorkerStatus.Running)==false)
				return false;
		}
		
		if(!FileUtil.saveToFile(to, this.clientFH.downloadFile(id, from, password))){
			this.status.terminate();
			return false;
		}else{
			return true;
		}
	}
}

