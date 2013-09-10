package jms.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import jms.model.WorkerStatus;
import jms.remote.interfaces.FileHandler;
import jms.remote.interfaces.WorkerStatusHandler;
import jms.util.FileUtil;


/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class FileHandlerImpl extends UnicastRemoteObject implements FileHandler {
	
	private String password = null;
	private WorkerStatus status = null;
	
	public FileHandlerImpl(String password, WorkerStatus status) throws RemoteException {
		this.password = password;
		this.status = status;
	}
	
	public byte[] downloadFile(int id, String fileName, String password) {
		if(status!=null && 
				this.password!=null && 
				this.password.equals(password)==true &&
				this.status.getState().equals(WorkerStatus.RUNNING) &&
				this.status.getCurrentJobState().equals(WorkerStatus.Running)){
			byte[] tmp = FileUtil.readFromFile(fileName);
			if(tmp == null) this.status.terminate();
			else this.status.finishJob();
			return tmp;
		}
		else return null;
	}
}
