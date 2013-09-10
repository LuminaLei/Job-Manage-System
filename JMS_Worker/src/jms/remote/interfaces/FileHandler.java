package jms.remote.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public interface FileHandler extends Remote {
	/**
	 * 
	 * @param fileName
	 * @return file data as byte array
	 * @throws RemoteException
	 */
	public byte[] downloadFile(int id, String fileName, String password) throws RemoteException;
}

