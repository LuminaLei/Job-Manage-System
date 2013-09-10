package jms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * System information of the Worker's
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class SysInfo {
	
	//public final static String javabin = "/usr/java1.6/bin";
	//public final static String bin = "/bin";
	public final static String ls = System.getProperty("line.separator");
	//public final static String ps = System.getProperty("path.separator");
	public final static String Dir_exes = "executables";
	public final static String Dir_inputs = "inputs";
	public final static String Dir_outputs = "outputs";
	public final static String Result_File_prefix = "result-";
	public final static String Result_File_suffix = ".txt";
	
	/**
	 * Execute an external program
	 * @param args
	 * @return the std output & std error & exception information
	 */
	public static String run(String args){
		String out = "";
		try {
			// using the Runtime exec method:
			Process p = Runtime.getRuntime().exec(args);
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			
			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));
			
			// read the output from the command
			String s;
			while ((s = stdInput.readLine()) != null) {
				out += s + ls;
			}
			
			// read any errors from the attempted command
			int numErr = 0;
			while ((s = stdError.readLine()) != null) {
				if(numErr == 0) out += "Here is the standard error of the command (if any):\n";
				out += s + ls;
				numErr ++;
			}
		} catch (IOException e) {
			out += "Exception happened - here's what I know: ";
			out += e.getMessage() + ls;
		}
		return out;
	}
}

