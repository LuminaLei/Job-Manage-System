package jms.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import jms.model.WorkerStatus;
import jms.remote.interfaces.FileHandler;
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
		return FileUtil.readFromFile(fileName);
	}
}
