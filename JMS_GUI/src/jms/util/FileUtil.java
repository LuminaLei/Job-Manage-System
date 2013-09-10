package jms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author Yifeng Wang <yifengw@student.unimelb.edu.au>
 */
public class FileUtil {
	
	/**
	 * Write data to file. 
	 * @param filename
	 * @param data
	 * @return true if success, false if failed.
	 */
	public static boolean saveToFile(String fileName, byte[] data){
		if(data == null) {
			System.err.println("data array is null.");
			return false;
		}
		File file = new File(fileName);
		File dir;
		String name = file.getName();
		if(file.getParent() != null ) {
			dir = new File(file.getParent());
			if( createDirIfNotExist(dir)== false ) return false;
			file = new File(dir, name);
		}
		
		BufferedOutputStream output;
		try {
			output = new BufferedOutputStream(new FileOutputStream(file));
			//output = new BufferedOutputStream(new FileOutputStream(file.getName()));
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException.");
			return false;
		}
		
		try {
			output.write(data, 0, data.length);
			output.flush();
		} 
		catch (IOException e) {
			System.err.println("Write data to file failed!\n"
							+"Error Messages are:\n" + e.getMessage());
			return false;
		}
		finally{
			try {
				output.close();
			} catch (IOException e) {
				System.err.println("Close file output stream failed!");
			}
		}
		return true;
	}
	
	/**
	 * Read data from a file
	 * @param fileName
	 * @return file data as byte array
	 */
	public static byte[] readFromFile(String fileName){
		try {
			File file = new File(fileName);
			byte buffer[] = new byte[(int) file.length()];
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
			input.read(buffer, 0, buffer.length);
			input.close();
			return (buffer);
		} catch (IOException e) {
			System.err.println("Read data from file failed!\n"
							+"Error Messages are:\n" + e.getMessage());
			return (null);
		}
	}
	
	/**
	 * 
	 * @param aFile
	 * @return
	 */
	public static boolean createDirIfNotExist(File dir){
		if (dir == null) {
			return false;
		}
		
		if (dir.exists()) {
			if (dir.isFile()) {
				return false;
			}
			if (dir.isDirectory()) {
				return true;
			}
		}
		else{
			if (!dir.mkdir()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param aFile
	 * @return
	 */
	public static boolean createDirIfNotExist(String name){
		File dir = new File(name);
		
		if (dir.exists()) {
			if (dir.isFile()) {
				return false;
			}
			if (dir.isDirectory()) {
				return true;
			}
		}
		else{
			if (!dir.mkdir()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Read plain-text file, return file content as a string
	 * @param fileName
	 * @return
	 */
	public static String read(String fileName) {
		// ...checks on file
		File file = new File(fileName);
		if(!file.isFile()) return null;
		
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(file));
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			return null;
		}
		return contents.toString();
	}
}

