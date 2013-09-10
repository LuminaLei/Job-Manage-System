package jms;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import jms.model.WorkerStatus;
import jms.remote.FileHandlerImpl;
import jms.remote.FileHandlerRegistryImpl;
import jms.remote.JobHandlerImpl;
import jms.remote.WorkerStatusHandlerImpl;
import jms.remote.interfaces.FileHandler;
import jms.remote.interfaces.FileHandlerRegistry;
import jms.remote.interfaces.JobHandler;
import jms.remote.interfaces.WorkerStatusHandler;
import jms.util.FileUtil;
import jms.util.Hash;
import jms.util.SysInfo;

/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class Worker {
	private static String url = "rmi://localhost/";
	private final static String File_Handler_Name = "FileHandler";
	private final static String File_Handler_Registry_Name = "FileHandlerRegistry";
	private final static String Job_Handler_Name = "JobHandler";
	private final static String Worker_Status_Handler_Name = "WorkerStatusHandler";
	
	//private final static String Login_Handler_Name = "LoginHandler";
	
	//private final static String PASSWORD = Hash.MD5("");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// initial directories
		
		if(FileUtil.createDirIfNotExist(SysInfo.Dir_exes)==false 
				|| FileUtil.createDirIfNotExist(SysInfo.Dir_inputs)==false 
				|| FileUtil.createDirIfNotExist(SysInfo.Dir_outputs)==false 
				){
			System.err.println("Cannot initial directories: \n"+
					SysInfo.Dir_exes+"\n"+
					SysInfo.Dir_exes+"\n"+
					SysInfo.Dir_exes);
			return ;
		}
		
		
		int port;
		String password = null;
		
		if(args.length == 2){
			try{
				port = Integer.parseInt(args[0]);
			}
			catch(Exception e){
				System.out.println("Invalid parameters.");
				System.out.println("How-to-run:");
				System.out.println("  java jms.Worker <port> <password>");
				return;
			}
			password = args[1];
		}
		else{
			System.out.println("Invalid parameters.");
			System.out.println("How-to-run:");
			System.out.println("  java jms.Worker <port> <password>");
			return;
		}
		
		password = Hash.MD5(password);
		Worker.url = "rmi://localhost:"+port+"/";
		WorkerStatus status = new WorkerStatus();
		try {
			
			WorkerStatusHandler workerStatusHandler = new WorkerStatusHandlerImpl(password, status);
			FileHandler fileHandler = new FileHandlerImpl(password, status);
			FileHandlerRegistry fileHandlerRegistry = new FileHandlerRegistryImpl(password, status);
			JobHandler jobHandler = new JobHandlerImpl(password, status);
			
			//LoginHandler loginHandler = new LoginHandlerImpl(password, port);
			//Naming.rebind(url + Login_Handler_Name, loginHandler);
			
			Naming.rebind(url + Worker_Status_Handler_Name, workerStatusHandler);
			Naming.rebind(url + File_Handler_Name, fileHandler);
			Naming.rebind(url + File_Handler_Registry_Name, fileHandlerRegistry);
			Naming.rebind(url + Job_Handler_Name, jobHandler);
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
	}

}

