package jms.gui;

import jms.util.FileUtil;
import jms.util.SysInfo;

public class MasterGUI {
	
	private static int n_finished_jobs = 0;
	
	public synchronized static void increase_n_finished_jobs(){
		n_finished_jobs ++;
	}
	
	public synchronized static int get_n_finished_jobs(){
		return n_finished_jobs;
	}
	
    public MasterGUI(){
        this.initialization();       
    }
    
    public void initialization(){        
        MainFrame app = new MainFrame(); 
        app.setVisible(true);
    }
    
    
    public static void main(String[] args) {
    	if( FileUtil.createDirIfNotExist(SysInfo.Dir_outputs)==false ){
			System.err.println("Cannot initial directories: "+
					SysInfo.Dir_outputs);
			return ;
		}
    	System.out.println(System.getProperty("user.dir"));
        MasterGUI masterGUI=new MasterGUI();
    }
}
