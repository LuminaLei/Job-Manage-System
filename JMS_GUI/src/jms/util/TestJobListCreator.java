package jms.util;

import java.sql.Timestamp;

import jms.model.Job;
import jms.model.JobList;

/**
 * @author Yang Song
 */
public class TestJobListCreator {
	
	public static void main(String args[]) {
		creator0();
		creator1();
		creator2();
		creator3();
		creator4();
		creator5();
		creator6();
		creator7();
		creator8();
	}
	
/**
 * creator0 for testing massive illegal(overdue) jobs. outputs into JLMassOverdue.jl
 */
	
	public static void creator0(){
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
		
		// job 0
		Job j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("15");
		j1.setBudget(1000);
		j1.setPriority(7);

		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 1
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("13");
		j1.setBudget(1500);
		j1.setPriority(9);

		time = new Timestamp(mils-3*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 2
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("19");
		j1.setBudget(3200);
		j1.setPriority(2);

		time = new Timestamp(mils-4*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 3
		j1 = Job.getInstance();
		j1.addInputs("inputs/6.txt");
		j1.addInputs("inputs/2.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(500);
		j1.setPriority(4);
		
		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 4
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("9");
		j1.setBudget(500);
		j1.setPriority(4);

		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 5
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("20");
		j1.setBudget(1000);
		j1.setPriority(8);

		
		time = new Timestamp(mils-12*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 6
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("10");
		j1.setBudget(4200);
		j1.setPriority(1);

		time = new Timestamp(mils-7*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 7
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("10");
		j1.setBudget(315);
		j1.setPriority(2);


		time = new Timestamp(mils-8*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 8
		j1 = Job.getInstance();
		j1.addInputs("inputs/7.txt");
		j1.addInputs("inputs/8.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(8400);
		j1.setPriority(5);

		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 9
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("3");
		j1.setBudget(900);
		j1.setPriority(3);

		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 10
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("18");
		j1.setBudget(2100);
		j1.setPriority(4);

		time = new Timestamp(mils-10*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 11
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("12");
		j1.setBudget(95);
		j1.setPriority(0);

		
		time = new Timestamp(mils-3*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 12
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("13");
		j1.setBudget(10000);
		j1.setPriority(10);

		
		time = new Timestamp(mils-10*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 13
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("4");
		j1.setBudget(1500);
		j1.setPriority(9);

		
		time = new Timestamp(mils-9*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 14
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("14");
		j1.setBudget(2100);
		j1.setPriority(7);

		time = new Timestamp(mils-6*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 15
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("18");
		j1.setBudget(1350);
		j1.setPriority(2);

		
		time = new Timestamp(mils-12*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 16
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("20");
		j1.setBudget(5600);
		j1.setPriority(5);

		
		time = new Timestamp(mils-7*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 17
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("12");
		j1.setBudget(820);
		j1.setPriority(3);

		
		time = new Timestamp(mils-3*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 18
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("17");
		j1.setBudget(2700);
		j1.setPriority(4);

		
		time = new Timestamp(mils-5*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 19
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("15");
		j1.setBudget(1034);
		j1.setPriority(0);

		
		time = new Timestamp(mils-9*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
		
		list.saveAs("JLMassOverdue.jl");
	}
	
/**
 * creator1 for testing mixed illegal(overdue) and legal jobs. outputs into JLMix.jl
 */
	public static void creator1(){
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
		

		// job 0
		Job j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("20");
		j1.setBudget(1000);
		j1.setPriority(7);

		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 1
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("14");
		j1.setBudget(1500);
		j1.setPriority(9);

		time = new Timestamp(mils-7*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 2
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("9");
		j1.setBudget(10200);
		j1.setPriority(5);

		time = new Timestamp(mils-121);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 3
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("12");
		j1.setBudget(1500);
		j1.setPriority(5);
		
		time = new Timestamp(mils+80000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 4
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("6");
		j1.setBudget(6700);
		j1.setPriority(6);

		time = new Timestamp(mils-122*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 5
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("4");
		j1.setBudget(1000);
		j1.setPriority(8);

		
		time = new Timestamp(mils-12*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 6
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("9");
		j1.setBudget(7200);
		j1.setPriority(5);

		time = new Timestamp(mils+12132);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 7
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("10");
		j1.setBudget(315);
		j1.setPriority(2);


		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 8
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("14");
		j1.setBudget(8400);
		j1.setPriority(5);

		time = new Timestamp(mils+3525);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 9
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("6");
		j1.setBudget(900);
		j1.setPriority(3);

		time = new Timestamp(mils-20*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 10
		j1 = Job.getInstance();
		j1.addInputs("inputs/7.txt");
		j1.addInputs("inputs/8.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(2100);
		j1.setPriority(4);

		time = new Timestamp(mils+102876);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 11
		j1 = Job.getInstance();
		j1.addInputs("inputs/7.txt");
		j1.addInputs("inputs/8.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(95);
		j1.setPriority(0);

		
		time = new Timestamp(mils-121);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 12
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("15");
		j1.setBudget(10000);
		j1.setPriority(10);

		
		time = new Timestamp(mils+80000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

			// job 13
		j1 = Job.getInstance();
		j1.addInputs("inputs/7.txt");
		j1.addInputs("inputs/8.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(1500);
		j1.setPriority(9);

		
		time = new Timestamp(mils-9*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 14
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("14");
		j1.setBudget(2100);
		j1.setPriority(7);

		time = new Timestamp(mils-6*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 15
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("19");
		j1.setBudget(1350);
		j1.setPriority(2);

		
		time = new Timestamp(mils+8567);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 16
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("14");
		j1.setBudget(5600);
		j1.setPriority(5);

		
		time = new Timestamp(mils+3821);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 17
		j1 = Job.getInstance();
		j1.addInputs("inputs/7.txt");
		j1.addInputs("inputs/8.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(820);
		j1.setPriority(3);

		
		time = new Timestamp(mils-3*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 18
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("15");
		j1.setBudget(2770);
		j1.setPriority(4);

		
		time = new Timestamp(mils+154);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 19
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("14");
		j1.setBudget(1034);
		j1.setPriority(0);

		
		time = new Timestamp(mils-9*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
		
		list.saveAs("JLMix.jl");
	}


/**
 * creator2 for testing legal jobs. outputs into JLMassLegal.jl
 */
	public static void creator2(){
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
	
		// job 0
		Job j1 = Job.getInstance();
		j1.addInputs("inputs/1.txt");
		j1.addInputs("inputs/2.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(10200);
		j1.setPriority(5);

		time = new Timestamp(mils-121);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 1
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("24");
		j1.setBudget(1500);
		j1.setPriority(4);

		time = new Timestamp(mils+80000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 2
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("30");
		j1.setBudget(7200);
		j1.setPriority(5);

		time = new Timestamp(mils+12132);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 3
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("4");
		j1.setBudget(95);
		j1.setPriority(0);
	
		time = new Timestamp(mils-121);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 4
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("90");
		j1.setBudget(10000);
		j1.setPriority(10);

		time = new Timestamp(mils+80000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 5
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("30");
		j1.setBudget(5600);
		j1.setPriority(9);

	
		time = new Timestamp(mils+3821);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 6
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("30");
		j1.setBudget(2770);
		j1.setPriority(4);

		time = new Timestamp(mils+154);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 7
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("20");
		j1.setBudget(1350);
		j1.setPriority(7);


		time = new Timestamp(mils+8567);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 8
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("90");
		j1.setBudget(87897);
		j1.setPriority(9);

		time = new Timestamp(mils+8971);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 9
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("160");
		j1.setBudget(87882);
		j1.setPriority(10);

		time = new Timestamp(mils-18222);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 10
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("40");
		j1.setBudget(10000);
		j1.setPriority(7);

		time = new Timestamp(mils+878);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 11
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("4");
		j1.setBudget(700);
		j1.setPriority(2);

	
		time = new Timestamp(mils+80000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 12
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("10");
		j1.setBudget(800);
		j1.setPriority(10);

	
		time = new Timestamp(mils+10);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 13
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("28");
		j1.setBudget(7200);
		j1.setPriority(1);

	
		time = new Timestamp(mils+10);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 14
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("8");
		j1.setBudget(700);
		j1.setPriority(8);

		time = new Timestamp(mils+80000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
	
		list.saveAs("JLMassLegal.jl");
	}


/**
 * creator3 for testing small amount of illegal(overdue) jobs. outputs into JLSmallOverdue.jl
 */

	public static void creator3(){	
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
	
		// job 0
		Job j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("14");
		j1.setBudget(1034);
		j1.setPriority(7);

		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 1
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("15");
		j1.setBudget(1000);
		j1.setPriority(7);

		time = new Timestamp(mils-2*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 2
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("10");
		j1.setBudget(3200);
		j1.setPriority(5);

		time = new Timestamp(mils-12*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 3
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("8");
		j1.setBudget(10000);
		j1.setPriority(10);
	
		time = new Timestamp(mils-9*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 4
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("15");
		j1.setBudget(8400);
		j1.setPriority(5);

		time = new Timestamp(mils-8*oneday);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
	
		list.saveAs("JLSmallOverdue.jl");
	
	}
/**
 * creator4 for testing one legal(overdue) job. outputs into JLLegal1.jl
 */
	public static void creator4(){
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
	
		// job 0
		Job j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("60");
		j1.setBudget(10200);
		j1.setPriority(5);

		time = new Timestamp(mils-121);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
		
		list.saveAs("JLLegal1.jl");
	}
	
/**
 * creator5 for testing two legal(overdue) jobs. outputs into JLLegal2.jl
 */
	public static void creator5(){
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
	
		// job 0
		Job j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("20");
		j1.setBudget(7200);
		j1.setPriority(5);

		time = new Timestamp(mils+12132);
		j1.setDeadline(time);
		list.addJob(j1.getJob());		
		
		// job 1
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("30");
		j1.setBudget(87882);
		j1.setPriority(10);

		time = new Timestamp(mils-18222);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
		
		
		
		list.saveAs("JLLegal2.jl");
	}

/**
 * creator6 for testing three legal(overdue) jobs. outputs into JLLegal3.jl
 */
	public static void creator6(){
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
	
		// job 0
		Job j1 = Job.getInstance();
		j1.addInputs("inputs/1.txt");
		j1.addInputs("inputs/2.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(800);
		j1.setPriority(2);

		time = new Timestamp(mils+10);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 1
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("40");
		j1.setBudget(7200);
		j1.setPriority(1);

		time = new Timestamp(mils-9000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 2
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("15");
		j1.setBudget(8721);
		j1.setPriority(5);
	
		time = new Timestamp(mils+10);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		list.saveAs("JLLegal3.jl");
	
	}

/**
 * creator7 for testing four legal(overdue) jobs. outputs into JLLegal4.jl
 */
	public static void creator7(){
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
	
		// job 0
		Job j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("20");
		j1.setBudget(800);
		j1.setPriority(2);

		time = new Timestamp(mils+10);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 1
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("10");
		j1.setBudget(72900);
		j1.setPriority(8);

		time = new Timestamp(mils-19000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 2
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("12");
		j1.setBudget(18121);
		j1.setPriority(9);
	
		time = new Timestamp(mils+1000);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
		
		// job 3
		j1 = Job.getInstance();
		j1.addInputs("inputs/6.txt");
		j1.addInputs("inputs/2.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(12341);
		j1.setPriority(10);
	
		time = new Timestamp(mils+8908);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
		

		list.saveAs("JLLegal4.jl");		
	
	}
	
/**
 * creator8 for testing five legal(overdue) jobs. outputs into JLLegal5.jl
 */
	public static void creator8(){
		JobList list = new JobList();
		//
		// // job 1
		// String[] inputs = new String[2];
		// inputs[0] = "inputs/1.txt";
		// inputs[1] = "inputs/2.txt";
		// list.addJob(Job.createJob("perl","executables/count.pl", inputs));
		//
		long oneday = 86400000;
		long mils = System.currentTimeMillis();
		Timestamp time;
		mils += oneday*4;
		// now mils is 4 days after now
	
		// job 0
		Job j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("20");
		j1.setBudget(12341);
		j1.setPriority(10);

		time = new Timestamp(mils+8908);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 1
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("15");
		j1.setBudget(10200);
		j1.setPriority(5);

		time = new Timestamp(mils-121);
		j1.setDeadline(time);
		list.addJob(j1.getJob());

		// job 2
		j1 = Job.getInstance();
		j1.addInputs("inputs/5.txt");
		j1.addInputs("inputs/2.txt");
		j1.addInputs("inputs/3.txt");
		j1.setProgram("executables/count.pl");
		j1.setExecutor("perl");
		j1.setParameters("");
		j1.setBudget(5600);
		j1.setPriority(5);
	
		time = new Timestamp(mils+3821);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
		
		// job 3
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("12");
		j1.setBudget(800);
		j1.setPriority(7);
	
		time = new Timestamp(mils-121);
		j1.setDeadline(time);
		list.addJob(j1.getJob());		
		
		// job 4
		j1 = Job.getInstance();
		j1.setProgram("executables/sleeper.pl");
		j1.setExecutor("perl");
		j1.setParameters("25");
		j1.setBudget(10000);
		j1.setPriority(7);

		time = new Timestamp(mils-878);
		j1.setDeadline(time);
		list.addJob(j1.getJob());
	
		list.saveAs("JLLegal5.jl");		
		
	}
}
