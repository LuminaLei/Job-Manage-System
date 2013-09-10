package jms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

import jms.gui.DateChooserJButton;
import jms.gui.WorkerListManager.MyFilter;
import jms.model.Job;
import jms.model.JobList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class JobListManager extends JFrame implements ActionListener, Runnable {
	JButton save;
	FileDialog fdopen;
	/***************************************************************/
	JFileChooser fdopenfiles;
	/***************************************************************/
	FileDialog fdsave;
	ArrayList<JTextField> executorName = new ArrayList<JTextField>();
	ArrayList<JButton> jobNameButton = new ArrayList<JButton>();
	ArrayList<JButton> InputFilesButton = new ArrayList<JButton>();
	ArrayList<DateChooserJButton> dueTimeButton = new ArrayList<DateChooserJButton>();
	ArrayList<JTextField> budegetText = new ArrayList<JTextField>();
	ArrayList<JTextField> priorityText = new ArrayList<JTextField>();
	ArrayList<JTextField> parametersText = new ArrayList<JTextField>();
	ArrayList<JButton> deleteButton = new ArrayList<JButton>();
	ArrayList<JButton> clearButton = new ArrayList<JButton>();
	private Frame o;
	private Container c = getContentPane();
	JLabel informaiton;
	ArrayList<JPanel> jobManagerPanel = new ArrayList<JPanel>();
	Container j;
	JPanel jobListPanel;
	JScrollPane jobScrollPane;
	int jobNumber = 0;
	int totalJobNumber = 0;
	ArrayList<String> filePath = new ArrayList<String>();
	ArrayList<String> filename = new ArrayList<String>();
	ArrayList<String> inputfilePath = new ArrayList<String>();
	ArrayList<String> inputfilename = new ArrayList<String>();
	int MAXNAMELENGTH = 18;
	String name;
	/***************************************************************/
	ArrayList<File[]> files = new ArrayList<File[]>();

	/***************************************************************/
	public JobListManager(Container x) {
		j = x;
		o = this;
		fdopen = new FileDialog(o, "open programmer.", FileDialog.LOAD);
		fdopenfiles = new JFileChooser(".");
		fdsave = new FileDialog(o, "save as...", FileDialog.SAVE);
		fdopen.setVisible(false);
		fdopenfiles.setVisible(false);
		fdsave.setVisible(false);
		fdopen.setAlwaysOnTop(true);
		fdopenfiles.setMultiSelectionEnabled(true);
		fdsave.setAlwaysOnTop(true);
		this.run();
	}

	public void run() {
		this.setTitle("Job List Manager");
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocation((int) (j.getLocation().getX() + 500), (int) j
				.getLocation().getY() + 50);
		this.setSize(380, 400);
		this.setPanel();
	}

	public void setPanel() {
		informaiton = new JLabel(("Number of jobs : " + (totalJobNumber + 1)));
		informaiton.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		jobListPanel = new JPanel();
		jobListPanel.setBorder(BorderFactory.createTitledBorder("job List"));
		jobScrollPane = new JScrollPane(jobListPanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jobScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		jobListPanel.setLayout(new BoxLayout(jobListPanel, 1));
		this.setLayout(new BorderLayout());
		this.addjobPanel();
		totalJobNumber++;
		jobListPanel.add((jobManagerPanel).get(jobNumber));
		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		controlPanel.setLayout(new FlowLayout());
		JButton add = new JButton("add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jobNumber++;
				addjobPanel();
				jobListPanel.add(jobManagerPanel.get(jobNumber));
				jobListPanel.setVisible(false);
				jobListPanel.setVisible(true);
				totalJobNumber++;
				informaiton.setText(("Number of Server : " + totalJobNumber));
				informaiton.setVisible(false);
				informaiton.setVisible(true);
			}
		});
		save = new JButton("save");
		save.addActionListener(this);
		JButton newfile = new JButton("new");
		newfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int x = 0; x < totalJobNumber; x++) {
					jobListPanel.remove(jobManagerPanel.get(x));
					executorName.remove(0);
					jobNameButton.remove(0);
					InputFilesButton.remove(0);
					dueTimeButton.remove(0);
					budegetText.remove(0);
					priorityText.remove(0);
					parametersText.remove(0);
					deleteButton.remove(0);
					clearButton.remove(0);
					jobManagerPanel.remove(0);
				}
				jobNumber = 0;
				totalJobNumber = 1;
				informaiton.setText(("Number of Server : " + totalJobNumber));
				informaiton.setVisible(false);
				informaiton.setVisible(true);
				addjobPanel();
				jobListPanel.add(jobManagerPanel.get(jobNumber));
				jobListPanel.setVisible(false);
				jobListPanel.setVisible(true);
			}
		});
		JButton cancel = new JButton("cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		controlPanel.add(add);
		controlPanel.add(save);
		controlPanel.add(newfile);
		controlPanel.add(cancel);
		this.add(informaiton, BorderLayout.NORTH);
		this.add(jobScrollPane, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
	}

	private void addjobPanel() {
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(8, 2));
		filePath.add("");
		filename.add("");
		inputfilePath.add("");
		inputfilename.add("");
		tempPanel.setBorder(BorderFactory.createBevelBorder(1));
		tempPanel.add(new JLabel("      executor"));
		executorName.add(new JTextField(20));
		tempPanel.add(executorName.get(jobNumber));
		tempPanel.add(new JLabel("      Program"));
		jobNameButton.add(new JButton("Choose Programmer"));
		jobNameButton.get(jobNumber).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton obj = (JButton) e.getSource();
				int tempindex = 0;
				for (int i = 0; i < totalJobNumber; i++) {
					if (obj == jobNameButton.get(i)) {
						tempindex = i;
						break;
					}
				}
				fdopen.setVisible(true);
				if ((fdopen.getDirectory() != null)
						&& (fdopen.getFile() != null)) {
					filePath.set(tempindex, fdopen.getDirectory());
					filename.set(tempindex, fdopen.getFile());
					String tempname = filename.set(tempindex, fdopen.getFile());
					int length = tempname.length();
					if (length >= MAXNAMELENGTH) {
						length = MAXNAMELENGTH;
						tempname = tempname.substring(0, length) + "...";
					}
					jobNameButton.get(tempindex).setText(tempname);
				}
			}
		});
		tempPanel.add(jobNameButton.get(jobNumber));
		tempPanel.add(new JLabel("      Parameters"));
		parametersText.add(new JTextField());
		tempPanel.add(parametersText.get(jobNumber));
		tempPanel.add(new JLabel("      Input Files"));
		InputFilesButton.add(new JButton("Choose Input files"));
		/***************************************************************/
		InputFilesButton.get(jobNumber).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton obj = (JButton) e.getSource();
				int tempindex = 0;
				for (int i = 0; i < totalJobNumber; i++) {
					if (obj == InputFilesButton.get(i)) {
						tempindex = i;
						break;
					}
				}
				fdopenfiles.setVisible(true);
				fdopenfiles.setDialogType(JFileChooser.OPEN_DIALOG);
				int result = fdopenfiles.showOpenDialog(o);
				// TODO
				if (result == JFileChooser.APPROVE_OPTION) {
					files.add(fdopenfiles.getSelectedFiles());
					int length = files.get(tempindex).length;
					File tempfile = files.get(tempindex)[0];
					File tempfile2 = files.get(tempindex)[length - 1];
					int templength = tempfile.getName().length() < 5 ? tempfile
							.getName().length() : 5;
					int templength2 = tempfile2.getName().length() < 5 ? tempfile2
							.getName().length() : 5;
					InputFilesButton.get(tempindex).setText(
							tempfile.getName().substring(0, templength)
									+ ((templength < 5) ? " " : "... ")
									+ "to "
									+ tempfile2.getName().substring(0,
											templength2)
									+ ((templength2 < 5) ? " " : "... ")
									+ " All " + length + " Files");
				}
			}
		});
		/***************************************************************/
		tempPanel.add(InputFilesButton.get(jobNumber));
		tempPanel.add(new JLabel("      Due Time"));
		dueTimeButton.add(new DateChooserJButton());
		tempPanel.add(dueTimeButton.get(jobNumber));
		tempPanel.add(new JLabel("      Budeget($)"));
		budegetText.add(new JTextField());
		tempPanel.add(budegetText.get(jobNumber));
		tempPanel.add(new JLabel("      priority(0-10max)"));
		priorityText.add(new JTextField());
		tempPanel.add(priorityText.get(jobNumber));
		deleteButton.add(new JButton("Delete"));
		tempPanel.add(deleteButton.get(jobNumber));
		deleteButton.get(jobNumber).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton obj = (JButton) e.getSource();
				int tempindex = 0;
				for (int i = 0; i < totalJobNumber; i++) {
					if (obj == deleteButton.get(i)) {
						tempindex = i;
						break;
					}
				}
				jobListPanel.remove(jobManagerPanel.get(tempindex));
				executorName.remove(tempindex);
				jobNameButton.remove(tempindex);
				InputFilesButton.remove(tempindex);
				dueTimeButton.remove(tempindex);
				budegetText.remove(tempindex);
				priorityText.remove(tempindex);
				parametersText.remove(tempindex);
				deleteButton.remove(tempindex);
				clearButton.remove(tempindex);
				jobManagerPanel.remove(tempindex);
				jobListPanel.setVisible(false);
				jobListPanel.setVisible(true);
				totalJobNumber--;
				jobNumber--;
				informaiton.setText(("Number of Server : " + totalJobNumber));
				informaiton.setVisible(false);
				informaiton.setVisible(true);
			}
		});
		clearButton.add(new JButton("Clear"));
		tempPanel.add(clearButton.get(jobNumber));
		clearButton.get(jobNumber).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton obj = (JButton) e.getSource();
				int tempindex = 0;
				for (int i = 0; i < totalJobNumber; i++) {
					if (obj == deleteButton.get(i)) {
						tempindex = i;
						break;
					}
				}
				executorName.get(tempindex).setText("");
				jobNameButton.get(tempindex).setText("Choose Programmer");
				// InputFilesButton.get(tempindex).setText("Choose Input files");
				dueTimeButton.get(tempindex).setText("");
				budegetText.get(tempindex).setText("");
				priorityText.get(tempindex).setText("");
				parametersText.get(tempindex).setText("");
			}
		});
		clearButton.get(jobNumber).addActionListener(this);
		jobManagerPanel.add(tempPanel);
	}

	public boolean verify() {
		boolean pass = true;
		if (jobNumber == 0) {
			// if (executorName.get(jobNumber).getText().length() == 0) {
			// executorName.get(jobNumber).requestFocusInWindow();
			// JOptionPane.showMessageDialog(this,
			// "please input an executor for the job");
			// pass = false;
			// }
			// else
			Date currentTime = Calendar.getInstance().getTime();
			Date planTime = dueTimeButton.get(jobNumber).getDate();
			long tmp = planTime.getTime();
			long now = System.currentTimeMillis();

			if (jobNameButton.get(jobNumber).getText()
					.equals("Choose Programmer")) {
				jobNameButton.get(jobNumber).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please choose a program for the server");
				pass = false;
			} else if (tmp <= now) {
				dueTimeButton.get(jobNumber).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"The time should be after current time");
				pass = false;

			} else if (!Verify.isNum(budegetText.get(jobNumber).getText())) {
				budegetText.get(jobNumber).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give vaild budeget for the job");
				pass = false;
			} else if (budegetText.get(jobNumber).getText().length() == 0) {
				budegetText.get(jobNumber).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give vaild budeget for the job");
				pass = false;
			}
			//
			else if (priorityText.get(jobNumber).getText().length() == 0) {
				priorityText.get(jobNumber).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give an priority for the job");
				pass = false;
			} else if (!Verify.isInt(priorityText.get(jobNumber).getText())) {
				priorityText.get(jobNumber).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give vaild priority for the job");
				pass = false;
			} else {
				int p = Integer.parseInt(priorityText.get(jobNumber).getText());
				if (p < 0 || p > 10) {
					priorityText.get(jobNumber).requestFocusInWindow();
					JOptionPane.showMessageDialog(this,
							"please give vaild priority for the job");
					pass = false;
				}

			}

		}
		for (int x = 0; x < jobNumber; x++) {
			// if (executorName.get(x).getText().length() == 0) {
			// executorName.get(x).requestFocusInWindow();
			// JOptionPane.showMessageDialog(this,
			// "please input an executor for the job");
			// pass = false;
			// break;
			// }
			if ((filename.get(x) == null) || (filePath.get(x) == null)) {
				jobNameButton.get(x).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please choose a program for the server");
				pass = false;
				break;
			}

			Date currentTime = Calendar.getInstance().getTime();
			Date planTime = dueTimeButton.get(x).getDate();
			// SimpleDateFormat dateFormat=new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// currentTime.parse("yyyy-MM-dd HH:mm:ss");
			long tmp = planTime.getTime();
			long now = System.currentTimeMillis();
			if (tmp <= now) {
				dueTimeButton.get(x).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"The time should be after current time");
				pass = false;
				break;
			}

			if (!Verify.isNum(budegetText.get(x).getText())) {
				budegetText.get(x).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give vaild budeget for the job");
				pass = false;
				break;
			}
			if (budegetText.get(x).getText().length() == 0) {
				budegetText.get(x).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give vaild budeget for the job");
				pass = false;
				break;
			}
			//
			if (priorityText.get(x).getText().length() == 0) {
				priorityText.get(x).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give an priority for the job");
				pass = false;
				break;
			}
			if (!Verify.isInt(priorityText.get(x).getText())) {
				priorityText.get(x).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give vaild priority for the job");
				pass = false;
				break;
			}

			int p = Integer.parseInt(priorityText.get(x).getText());
			if (p < 0 || p > 10) {
				priorityText.get(x).requestFocusInWindow();
				JOptionPane.showMessageDialog(this,
						"please give vaild priority for the job");
				pass = false;
				break;
			}

			// int prioritytemp =
			// Integer.valueOf(priorityText.get(x).getText());
			// if (prioritytemp < 0 || prioritytemp > 10) {
			// priorityText.get(x).requestFocusInWindow();
			// JOptionPane.showMessageDialog(this,
			// "please give port from 0 to 10");
			// pass = false;
			// break;
			// }
		}
		return pass;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == save) {
			if (verify() == true) {
				ArrayList<String> fileFilterText = new ArrayList<String>();
				fileFilterText.add("jl");
				fileFilterText.add("Jl");
				fileFilterText.add("jL");
				fileFilterText.add("JL");
				fdsave.setFilenameFilter(new MyFilter(fileFilterText));
				fdsave.setAlwaysOnTop(true);
				fdsave.setFile(".jl");
				fdsave.setVisible(true);
				if ((fdsave.getDirectory() != null)
						&& (fdsave.getFile() != null)) {
					name = fdsave.getDirectory() + fdsave.getFile();
					makeFile(name);
				}
			}
		}
	}

	public void makeFile(String s) {
		System.out.println(s);
		JobList list = new JobList();
		for (int x = 0; x < totalJobNumber; x++) {
			String executor = executorName.get(x).getText();
			String progpath = filePath.get(x) + filename.get(x);
			String params = parametersText.get(x).getText();
			/***************************************************************/
			// String infile = inputfilePath.get(x) + inputfilename.get(x);
			String[] inputs = null;
			// for (int x1 = 0; x1 < files.size(); x1++) {
			// if (infile != null && infile.equals("") == false) {
			// inputs = new String[1];
			// inputs[x1] = infile;
			// }
			// }
			if (files.size() > 0 && files.get(x).length != 0) {
				inputs = new String[files.get(x).length];
				for (int x1 = 0; x1 < files.get(x).length; x1++) {
					inputs[x1] = files.get(x)[x1].getPath()
							+ files.get(x)[x1].getName();
					System.out.print(" " + inputs[x1]);
				}
			} else {
				inputs = new String[0];
			}
			/*********************************************************/
			long duetime = dueTimeButton.get(x).getDate().getTime();
			Timestamp duetimestamp = new Timestamp(duetime);
			double budget;
			budget = Double.parseDouble(budegetText.get(x).getText());
			int priority;
			priority = Integer.parseInt(priorityText.get(x).getText());
			list.addJob(Job.createJob(executor, progpath, params, inputs,
					budget, duetimestamp, priority));
			System.out.print(executor + " ");
			System.out.print(progpath + " ");
			System.out.print(params + " ");
			// Date need to be saved by specially type
			SimpleDateFormat fromatter = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			String strCurrTime = fromatter.format(dueTimeButton.get(x)
					.getDate());
			System.out.print(duetimestamp + " ");
			System.out.print(budget + " ");
			System.out.println(priority + " ");
		}// end for
		list.saveAs(s);
	}
}