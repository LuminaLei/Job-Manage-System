package jms.remote.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public interface FileHandlerRegistry extends Remote {
	
	public void registry(FileHandler fh, String password) throws RemoteException;
	
	public boolean uploadFile(int id, String from, String to, String password) throws RemoteException;
}

