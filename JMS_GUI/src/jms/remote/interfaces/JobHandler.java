package jms.remote.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public interface JobHandler extends Remote {
	public boolean run(int id, String cmd, String password) throws RemoteException;
}

