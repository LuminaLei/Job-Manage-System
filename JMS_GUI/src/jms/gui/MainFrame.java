package jms.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import java.io.*;
import jms.controller.JobQueueController;
import jms.controller.QueueMonitor;
import jms.controller.WorkerController;
import jms.gui.CurrTimeLabel;
import jms.gui.controller.WorkerStatusMonitor;
import jms.model.JobList;
import jms.model.JobStateCache;
import jms.model.Queue;
import jms.model.WorkerList;
import jms.remote.FileHandlerImpl;
import jms.remote.interfaces.FileHandler;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener, Runnable {

    final Timer fresh = new Timer();
    TimerTask timerTask;

    int MAXNAMELENGTH = 25;
    private Container c = getContentPane();
    private Frame o;
    private WindowAdapter quit;
    private String menuBar[] = { "Flie(F)", "Tool(T)", "Help(H)" };
    private String menuItem[][] = { { "New(N)|78", "Exit(X)|88" },
            { "Server List Mangement(S)|83", "Job Management(J)|74" },
            { "Help(H)|72", "About(F)|70" } };
    private JMenuItem jMenuItem[][] = new JMenuItem[3][2];
    private JMenu jMenu[];
    @SuppressWarnings("rawtypes")
	JComboBox algorithmPlan;
    JButton ServerFileButton;
    String filePath = "";
    String filename = "";
    String allfilename = "";
    int schedulerType;
    JScrollPane serversListFilePane;
    FileDialog fdopen;
    @SuppressWarnings("rawtypes")
	JComboBox serversPlan;
    JPanel serverPanel;
    JPanel jobPanel;
    JPanel workingPanel;
    JPanel jobWorkingPanel;

    WorkerListManager serverListManger;
    JobListManager jobListManager;
    JScrollPane jobScorllPanel;
    JLabel startTimeLabel1;
    JLabel stutsLabel;
    JLabel totalJobLabel;
    JLabel finishedJobLabel;
    JLabel workingStutsLabel;
    JLabel workingPriceLabel;
    JLabel workingJobLabel;
    JLabel workingjobfinishedLabel;
    @SuppressWarnings("rawtypes")
	JList workingServerJList;
    JLabel workingStutsLabel1;
    JPanel workingServerInfo;
    JPanel serverList;

    ArrayList<String> workingServersList = new ArrayList<String>();
    // ArrayList<JobOnServer> workingjob = new ArrayList<JobOnServer>();
    ArrayList<JobStutsPanel> jobStuts = new ArrayList<JobStutsPanel>();
    // int jobindex = 0;
    // int preJobIndex=0;

    // TODO add fields
    WorkerList wlist;
    JobList total_jobs_list;

    FileHandler uploaderHandler;
    WorkerController[] workerControllers;
    Queue workerQueue;
    Queue queue;
    JobQueueController jobQueueController;
    QueueMonitor queueMonitor;
    WorkerStatusMonitor workerStatusMonitor;

    JobStateCache jobStateCache;

    int timerSecond = 1000;
    JButton add;
    //JButton stop;
    JButton newProject;
    JPanel deletePanel;
    JTextField deleteNumber;
    JButton delete;

    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.run();
    }

    @SuppressWarnings("deprecation")
	public void run() {
        this.setDefault();
        this.setVisible(true);
        this.setMenu();
        this.setScreen();
        this.setMainPanel();
        serverListManger = new WorkerListManager(c);
        serverListManger.setVisible(false);
        jobListManager = new JobListManager(c);
        jobListManager.setVisible(false);

        // TODO run()...
        this.setContentPane(serverPanel);
        this.serverPanel.setVisible(true);
        this.show();

        //
        fresh.schedule(timerTask = new TimerTask() {
            public void run() {
                refresh();
            }
        }, 1000, timerSecond);
    }

    private void setDefault() {
        total_jobs_list = JobList.getInstance();

        String name = "Job Management System 1.0";
        setTitle(name);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        try {
            this.addWindowListener(quit = new WindowAdapter() {

                public void windowClosing(WindowEvent e) {
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    int response = JOptionPane.showConfirmDialog(null,
                            "Do you want to continue?", "Confirm",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.NO_OPTION) {

                    } else if (response == JOptionPane.YES_OPTION) {
                        System.exit(1);
                    } else if (response == JOptionPane.CLOSED_OPTION) {

                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unkown Error", "warning",
                    JOptionPane.ERROR_MESSAGE);
        }
        this.setIconImage(Toolkit.getDefaultToolkit().createImage(
                "./logo.gif"));
        o = this;
        fdopen = new FileDialog(o, "open programmer.", FileDialog.LOAD);
        schedulerType = 4;

        // TODO add initial
        try {
            uploaderHandler = new FileHandlerImpl(null, null);
        } catch (RemoteException e1) {
            System.err.println("Unable to open uploader handler.\n");
        }
        workerQueue = new Queue(true);
        queue = new Queue(true);
        jobStateCache = new JobStateCache();

    }

    private void setMenu() {

        JMenuBar barTemp = new JMenuBar();
        jMenu = new JMenu[menuBar.length];
        for (int i = 0; i < menuBar.length; i++) {
            jMenu[i] = new JMenu(menuBar[i]);
            jMenu[i].setMnemonic(menuBar[i].split("\\(")[1].charAt(0));
            barTemp.add(jMenu[i]);
        }
        for (int i = 0; i < menuItem.length; i++) {
            for (int j = 0; j < menuItem[i].length; j++) {
                jMenu[i].addSeparator();
                jMenuItem[i][j] = new JMenuItem(menuItem[i][j].split("\\|")[0]);
                if (menuItem[i][j].split("\\|").length != 1)
                    jMenuItem[i][j].setAccelerator(KeyStroke.getKeyStroke(
                            Integer.parseInt(menuItem[i][j].split("\\|")[1]),
                            ActionEvent.CTRL_MASK));
                jMenuItem[i][j].addActionListener(this);
                jMenuItem[i][j].setMnemonic(menuItem[i][j].split("\\(")[1]
                        .charAt(0));
                jMenu[i].add(jMenuItem[i][j]);
            }
        }
        this.setJMenuBar(barTemp);
    }

    private void setScreen() {
        Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = 0;
        int height = 0;
        width = screen.width;
        height = screen.height;
        if (width < 800 || height < 640) {
            this.setSize(width, height);
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
        } else {
            this.setSize(680, 640);
            this.setLocationRelativeTo(null);
        }
    }

    private void setMainPanel() {
        this.setServerPanel();
        // this.setJobPanel();
        // this.setWoringPanel();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void setServerPanel() {
        serverPanel = new JPanel();
        serverPanel.setVisible(false);
        serverPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        serverPanel.setLayout(new BorderLayout());
        JPanel plan = new JPanel();
        plan.setBorder(BorderFactory
                .createTitledBorder("Server Files and Schedual"));

        serverList = new JPanel();
        serverList.setLayout(new BoxLayout(serverList, 1));

        GridLayout layout = new GridLayout(2, 2);
        layout.setHgap(10);
        layout.setVgap(10);
        plan.setLayout(layout);
        JLabel serverfileinfo = new JLabel("    Choose your Server File :");
        ServerFileButton = new JButton("Choose File...");
        ServerFileButton.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
                JButton obj = (JButton) e.getSource();
                fdopen.setVisible(true);
                if ((fdopen.getDirectory() != null)
                        && (fdopen.getFile() != null)) {
                    filePath = fdopen.getDirectory();
                    filename = fdopen.getFile();
                    allfilename = filePath + filename;
                    String tempname = filename;
                    int length = tempname.length();
                    if (length >= MAXNAMELENGTH) {
                        length = MAXNAMELENGTH;
                        tempname = tempname.substring(0, length) + "...";
                    }
                    ServerFileButton.setText(tempname);

                    // TODO get worker list
                    wlist = new WorkerList();

                    wlist.load(allfilename);
                    // check worker list
                    wlist.filterWorkers();
                    if (wlist.length() < 1)
                        return;

                    WorkerController workerController = new WorkerController();
                    serverList.removeAll();
                    for (int i = 0; i < wlist.length(); i++) {

                        workerController.setWorker(i, wlist.getWorker(i));
                        System.out.print(workerController.getPrice());
                        serverList.add(new ServerStutsPanel(i, workerController
                                .getWorkerName(), workerController.getPrice() + "",
                                workerController.getAddress(), workerController.getPort() + ""));
                    }
                }
                serverList.setVisible(false);
                serverList.setVisible(true);
            }
        });
        JLabel algorithmLabel = new JLabel("    Schedual Algorithm");

        String[] s = { "Fist come, First service", "Earliest Deadline First",
                "Earliest Deadline First constrained by Budget",
                "Priority Queue" };
        algorithmPlan = new JComboBox(s);
        algorithmPlan.setRenderer(new MyCellRenderer());
        plan.add(serverfileinfo);
        plan.add(ServerFileButton);
        plan.add(algorithmLabel);
        plan.add(algorithmPlan);
        serverPanel.add(plan, BorderLayout.NORTH);
        serversListFilePane = new JScrollPane(serverList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        serversListFilePane.setBorder(BorderFactory.createBevelBorder(1));
        serverPanel.add(serversListFilePane, BorderLayout.CENTER);
        JPanel serverButtons = new JPanel();
        serverButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 0));

        // Start system .... ->
        JButton serverNext = new JButton("Start System");
        serverNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                
                if (wlist == null || wlist.length() < 1)
                    return;
                

                // TODO work[]
                workerControllers = new WorkerController[wlist.length()];
                // initialize worker queue and connect to worker
                for (int i = 0; i < wlist.length(); i++) {
                    workerControllers[i] = new WorkerController(i, wlist.getWorker(i));
                    if (workerControllers[i].connect(uploaderHandler)) {
                        workerQueue.push(i);
                        System.out.println("push worker queue: " + i);
                    }else{
//                        JOptionPane.showMessageDialog( o ,
//                                "Invaild work list or Disconnect with server "
//                                        + workers[i].getWorkerName());
                        
                    	while(workerQueue.hasNext()){
                    		workerQueue.pop();
                    	}
                    	JOptionPane.showMessageDialog(null,
								"Connect to worker "+workerControllers[i].getWorkerName()+
								"(id:"+i+") failed.");
                        return;
                    }
                }
                
                setWoringPanel();
                setContentPane(workingPanel);
                workingPanel.setVisible(true);

                workingServerJList.setSelectedIndex(0);
                //
                workerStatusMonitor = new WorkerStatusMonitor(workerControllers,
                        total_jobs_list, workingServerJList, workingStutsLabel,
                        workingPriceLabel, workingJobLabel,
                        workingjobfinishedLabel, jobStuts, jobStateCache,
                        jobWorkingPanel
                        ,workerQueue);

                new Thread(workerStatusMonitor).start();

                int algo = algorithmPlan.getSelectedIndex();
                // job queue controller
                jobQueueController = new JobQueueController(total_jobs_list,
                        queue, algo);
                new Thread(jobQueueController).start();

                // TODO start queue Monitor
                queueMonitor = new QueueMonitor(total_jobs_list, workerControllers,
                        workerQueue, queue, jobStateCache,
                        jobQueueController);
                new Thread(queueMonitor).start();
                System.out.println("Queue monitor started...");
                jMenuItem[0][0].setEnabled(false);
            }
        });

        // TODO cancel job button
        JButton cancel = new JButton("Close");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setContentPane(serverPanel);
                serverPanel.setVisible(false);
            }
        });
        serverButtons.add(serverNext);
        serverButtons.add(cancel);
        serverButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

        serverPanel.add(serverButtons, BorderLayout.SOUTH);
    }

    /*
     * // private void setJobPanel() { // jobPanel = new JPanel(); //
     * jobPanel.setLayout(new BorderLayout()); // jobPanel.setVisible(false); //
     * jobPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); //
     * JLabel jobInstruction = new JLabel("please choose your job"); //
     * jobInstruction // .setBorder(BorderFactory.createEmptyBorder(0, 10, 10,
     * 10)); // JPanel jobPanelStuff = new JPanel(); // jobPanelStuff //
     * .setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 40)); //
     * jobPanelStuff.setLayout(new GridLayout(4, 1)); // JButton jobFileChooser
     * = new JButton("choose job file"); // JPanel algorithmPanel = new
     * JPanel(); // algorithmPanel.setLayout(new GridLayout(1, 2)); // JLabel
     * algorithmLabel = new JLabel("Schedual Algorithm"); // JComboBox
     * algorithmPlan; // String[] s = { "Time", "Cost", "Time Cost" }; //
     * algorithmPlan = new JComboBox(s); // algorithmPanel.add(algorithmLabel);
     * // algorithmPanel.add(algorithmPlan); // JPanel budgetPanel = new
     * JPanel(); // JLabel budgetLabel = new JLabel("Budget"); // JTextField
     * budgetText = new JTextField(); // budgetText.setColumns(55); // //
     * budgetPanel.add(budgetLabel); // budgetPanel.add(budgetText); // JPanel
     * timePanel = new JPanel(); // JLabel timeLabel = new JLabel("Due Time");
     * // DateChooserJButton timeChooser = new DateChooserJButton(); //
     * timePanel.setLayout(new GridLayout(1, 2)); // timePanel.add(timeLabel);
     * // timePanel.add(timeChooser); // jobPanelStuff.add(jobFileChooser); //
     * jobPanelStuff.add(algorithmPanel); // jobPanelStuff.add(budgetPanel); //
     * jobPanelStuff.add(timePanel); // JPanel jobButtons = new JPanel(); //
     * jobButtons.setLayout(new GridLayout(1, 2)); // JButton submit = new
     * JButton("submit"); // JButton previous = new JButton("previous"); //
     * submit.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 20)); //
     * previous.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 20)); //
     * jobButtons.add(submit); // submit.addActionListener(new ActionListener()
     * { // public void actionPerformed(ActionEvent e) { //
     * setContentPane(workingPanel); // workingPanel.setVisible(true); //
     * serverPanel.setVisible(false); // jobPanel.setVisible(false); //
     * jMenu[0].setEnabled(false); // jMenu[1].setEnabled(false); // } // }); //
     * jobButtons.add(previous); // previous.addActionListener(new
     * ActionListener() { // public void actionPerformed(ActionEvent e) { //
     * setContentPane(serverPanel); // serverPanel.setVisible(true); // } // });
     * // jobPanel.add(jobInstruction, BorderLayout.NORTH); //
     * jobPanel.add(jobPanelStuff, BorderLayout.CENTER); //
     * jobPanel.add(jobButtons, BorderLayout.SOUTH); // }
     */

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void setWoringPanel() {
        workingPanel = new JPanel();
        workingPanel.setVisible(false);
        JLabel workingLabel = new JLabel("This system is working...");
        workingLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        JPanel allPanel = new JPanel();
        JPanel informationPanel = new JPanel();
        jobWorkingPanel = new JPanel();

        allPanel.setLayout(new GridLayout(2, 1));

        JPanel serversInfoPanel = new JPanel();
        JPanel workingInfoPanel = new JPanel();
        informationPanel.setLayout(new GridLayout(1, 2));
        // informationPanel.setBorder(BorderFactory.createBevelBorder(1));
        workingInfoPanel.setBorder(BorderFactory
                .createTitledBorder("Running Status"));
        serversInfoPanel.setBorder(BorderFactory
                .createTitledBorder("Servers Status"));
        // jobScorllPanel = new JScrollPane(jobWorkingPanel,
        // JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        // JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // jobScorllPanel.setBorder(BorderFactory.createBevelBorder(1));
        // jobWorkingPanel.setSize(200,200);
        // jobWorkingPanel.setLayout(new FlowLayout());

        
        jobScorllPanel = new JScrollPane(jobWorkingPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jobScorllPanel.setBorder(BorderFactory.createBevelBorder(1));
        jobWorkingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        jobWorkingPanel.setBorder(BorderFactory.createBevelBorder(1));
        jobWorkingPanel.setPreferredSize(new Dimension(jobScorllPanel
                .getWidth() - 150, jobScorllPanel.getHeight() * 1000));
        jobWorkingPanel.revalidate();
        
        // TODO system info....
        JPanel info = new JPanel();
        JPanel message = new JPanel();
        workingInfoPanel.setLayout(new BoxLayout(workingInfoPanel, 1));
        info.setLayout(new GridLayout(5, 2));
        JLabel currentTimeLabel = new JLabel("   Current Time:");
        CurrTimeLabel currentTimeLabel1 = new CurrTimeLabel(
                "yyyy-MM-dd HH:mm:ss");
        JLabel startTimeLabel = new JLabel("   Start Time:");
        // Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat fromatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String strCurrTime = fromatter.format(Calendar.getInstance().getTime());
        startTimeLabel1 = new JLabel(strCurrTime);
        JLabel stutsLabel1 = new JLabel("   JVM Status:");
        // TODO System status.........
        stutsLabel = new JLabel("Working...");

        JLabel totalJobLabel1 = new JLabel("   Total jobs:");
        int totalint = this.total_jobs_list.length();
        totalJobLabel = new JLabel(totalint + "");

        JLabel finishedJobLabel1 = new JLabel("   Finished jobs:");
        int finishint = MasterGUI.get_n_finished_jobs();
        finishedJobLabel = new JLabel(finishint + " of " + totalint);

        info.add(currentTimeLabel);
        info.add(currentTimeLabel1);
        info.add(startTimeLabel);
        info.add(startTimeLabel1);
        info.add(stutsLabel1);
        info.add(stutsLabel);
        info.add(totalJobLabel1);
        info.add(totalJobLabel);
        info.add(finishedJobLabel1);
        info.add(finishedJobLabel);
        workingInfoPanel.add(info);
        message.setLayout(new GridLayout(2, 4));
        JLabel waitingPhotoLabel = new JLabel("  waiting");
        JPanel waitingPhotoPanel = new JPanel();
        waitingPhotoPanel.setBackground(Color.yellow);
        waitingPhotoPanel.setBorder(BorderFactory.createLineBorder(
                c.getBackground(), 10));
        JLabel runningPhotoLabel = new JLabel("  running");
        JPanel runningPhotoPanel = new JPanel();
        runningPhotoPanel.setBackground(Color.cyan);
        runningPhotoPanel.setBorder(BorderFactory.createLineBorder(
                c.getBackground(), 10));
        JLabel finishedPhotoLabel = new JLabel(" completed");
        JPanel finishedPhotoPanel = new JPanel();
        finishedPhotoPanel.setBackground(Color.green);
        finishedPhotoPanel.setBorder(BorderFactory.createLineBorder(
                c.getBackground(), 10));
        JLabel failedPhotoLabel = new JLabel(" terminated");
        JPanel failedPhotoPanel = new JPanel();
        failedPhotoPanel.setBackground(Color.red);
        failedPhotoPanel.setBorder(BorderFactory.createLineBorder(
                c.getBackground(), 10));
        message.add(waitingPhotoLabel);
        message.add(waitingPhotoPanel);
        message.add(runningPhotoLabel);
        message.add(runningPhotoPanel);
        message.add(finishedPhotoLabel);
        message.add(finishedPhotoPanel);
        message.add(failedPhotoLabel);
        message.add(failedPhotoPanel);
        workingInfoPanel.add(message);

        // add the servers list
        WorkerController workerController = new WorkerController();
        for (int i = 0; i < wlist.length(); i++) {
            workerController.setWorker(i, wlist.getWorker(i));
            System.out.print(workerController.getPrice());
            workingServersList.add(workerController.getWorkerName());
        }

        DefaultListModel listModel = new DefaultListModel();
        for (int x = 0; x < workingServersList.size(); x++) {
            listModel.addElement(workingServersList.get(x));
        }
        workingServerJList = new JList(listModel);
        workingServerInfo = new JPanel();
        workingServerJList
                .addListSelectionListener(new ListSelectionListener() {

                    // TODO Worker status event handler
                    public void valueChanged(ListSelectionEvent e) {
                        //int wid = workingServerJList.getSelectedIndex();
                        // getSelectedValue().toString();

                        workingServerInfo.setBorder(BorderFactory
                                .createTitledBorder(workingServerJList
                                        .getSelectedValue().toString()
                                        + " Status"));
                        // .....
                        /*******************************************************************/
                        // workingStutsLabel.setText("waitting");
                        // workingPriceLabel.setText("");
                        // workingJobLabel.setText("Null");
                        // workingjobfinishedLabel.setText("20");
                        /*******************************************************************/
                    }// end of handler

                });
        JScrollPane serversScorllPanel = new JScrollPane(workingServerJList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // TODO worker status ...........
        workingServerInfo.setBorder(BorderFactory
                .createTitledBorder("Worker Status"));
        workingServerInfo.setLayout(new GridLayout(4, 2));
        workingStutsLabel1 = new JLabel("  State:");
        workingStutsLabel = new JLabel(" ");// working/waiting/die
        JLabel workingPriceLabel1 = new JLabel("  Price:");
        workingPriceLabel = new JLabel(" ? $/sec ");// 8 $/second
        JLabel workingJobLabel1 = new JLabel("  Latest job :");
        workingJobLabel = new JLabel("  ");// job 2
        JLabel workingjobfinishedLabel1 = new JLabel("  Finished jobs:");
        workingjobfinishedLabel = new JLabel("");
        workingServerInfo.add(workingStutsLabel1);
        workingServerInfo.add(workingStutsLabel);
        workingServerInfo.add(workingPriceLabel1);
        workingServerInfo.add(workingPriceLabel);
        workingServerInfo.add(workingJobLabel1);
        workingServerInfo.add(workingJobLabel);
        workingServerInfo.add(workingjobfinishedLabel1);
        workingServerInfo.add(workingjobfinishedLabel);

        serversInfoPanel.setLayout(new BoxLayout(serversInfoPanel, 1));
        serversInfoPanel.add(serversScorllPanel);
        serversInfoPanel.add(workingServerInfo);
        // runningInfoPanel.setBorder(BorderFactory.createTitledBorder
        // ("System Information"));
        // runningInfoPanel.setLayout(new GridLayout(5,2));
        // JLabel JmsStatusLabel1 = new JLabel("  JMS Status : ");
        // runningInfoPanel.add(JmsStatusLabel1);
        // JLabel JmsStatusLabel = new JLabel("  runing / finished");
        // runningInfoPanel.add(JmsStatusLabel);
        // JLabel startTimeLabel1 = new JLabel("  Start Time  : ");
        // runningInfoPanel.add(startTimeLabel1);
        // JLabel startTimeLabel = new JLabel();
        // runningInfoPanel.add(startTimeLabel);
        // JLabel finishTimeLabel1 = new JLabel("  Finish Time  : ");
        // runningInfoPanel.add(finishTimeLabel1);
        // JLabel finishTimeLabel = new JLabel();
        // runningInfoPanel.add(finishTimeLabel);
        // JLabel BudgetLabel1 = new JLabel("  Budget Remaining  : ");
        // runningInfoPanel.add(BudgetLabel1);
        // JLabel BudgetLabel = new JLabel();
        // runningInfoPanel.add(BudgetLabel);
        // JLabel jobCompletedLabel1 = new JLabel("  Job Completed  : ");
        // runningInfoPanel.add(jobCompletedLabel1);
        // JLabel jobCompletedLabel = new JLabel();
        // runningInfoPanel.add(jobCompletedLabel);
        //
        // workingServerPanel.setBorder(BorderFactory.createBevelBorder(1));
        // workingInfoPanel.add(runningInfoPanel);
        // workingInfoPanel.add(serversInfoPanel);
        // JPanel controlPanel=new JPanel();
        // stop=new JButton("stop");
        // controlPanel.setLayout(new GridLayout(1,1));
        // controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 240, 0,
        // 20));
        // controlPanel.add(stop);
        // stop.addActionListener(new ActionListener(){
        // public void actionPerformed(ActionEvent e){
        // jMenu[0].setEnabled(true);
        // jMenu[1].setEnabled(true);
        // stop.setEnabled(false);
        // }
        // });
        // TODO
        // this.total_jobs_list = new JobList();
        // this.total_jobs_list.load("a.g");
        // for(int i =0; i <total_jobs_list.length();i++){
        // total_jobs_list.getJob(i).get
        // }

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        add = new JButton("Add Joblist");
        //stop = new JButton("Stop System");
        newProject = new JButton("ReStart System");

        deletePanel = new JPanel();
        JLabel deleteLabel = new JLabel("Job ID");
        deleteNumber = new JTextField(4);
        delete = new JButton("Cancel job");
        deletePanel.setLayout(new FlowLayout());
        deletePanel.add(deleteLabel);
        deletePanel.add(deleteNumber);
        deletePanel.add(delete);
        deleteNumber.setEnabled(false);
        delete.setEnabled(false);
        delete.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
                JButton obj = (JButton) e.getSource();
                if (!Verify.isInt(deleteNumber.getText())) {
                    deleteNumber.requestFocusInWindow();
                    JOptionPane.showMessageDialog(deletePanel,
                            "please give a vaild Job ID");
                    return;
                }
                int deleteJobID = Integer.valueOf(deleteNumber.getText());
                if (deleteJobID < 0 || deleteJobID > total_jobs_list.length()) {
                    deleteNumber.requestFocusInWindow();
                    JOptionPane.showMessageDialog(
                            deletePanel,
                            "Job ID should be between 0 to "
                                    + (total_jobs_list.length() - 1));
                    return;
                } else {

                    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                    // TODO ..... cancel job
                    if (jobStateCache.isJobFinished(deleteJobID)) {
                        JOptionPane.showMessageDialog(deletePanel,
                                "You cannot cancel a finished job: "
                                        + deleteJobID);
                        return;
                    }
                    if (jobStateCache.isJobTerminated(deleteJobID)) {
                        JOptionPane.showMessageDialog(deletePanel,
                                "You cannot cancel a terminated job: "
                                        + deleteJobID);
                        return;
                    }
                    if (jobStateCache.isJobRunning(deleteJobID) == false) {
                        JOptionPane.showMessageDialog(deletePanel,
                                "You cannot cancel a waiting job: "
                                        + deleteJobID);
                        return;
                    }
                    queue.block(deleteJobID);
                    int w = jobStateCache.getWhichWorker(deleteJobID);
                    if (w != -1)
                        workerControllers[w].terminateJob();
                    else
                        return;

                    JOptionPane.showMessageDialog(deletePanel,
                            "You have Cancelled running job: " + deleteJobID);
                    // setWorkingJob();
                }

                jobWorkingPanel.setVisible(false);
                jobWorkingPanel.setVisible(true);
            }
        });
        controlPanel.add(deletePanel);
        controlPanel.add(add);
        add.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
                JButton obj = (JButton) e.getSource();
                fdopen.setVisible(true);
                if ((fdopen.getDirectory() != null)
                        && (fdopen.getFile() != null)) {
                    filePath = fdopen.getDirectory();
                    filename = fdopen.getFile();
                    allfilename = filePath + filename;
                    String tempname = filename;
                    int length = tempname.length();
                    if (length >= MAXNAMELENGTH) {
                        length = MAXNAMELENGTH;
                        tempname = tempname.substring(0, length) + "...";
                    }
                    ServerFileButton.setText(tempname);

                    // TODO add job list

                    JobList tmp = new JobList();
                    tmp.load(allfilename);
                    if (tmp.filterJobs() == false) {
                        System.err.println("Filter jobs failed!");
                    }

                    int curlength = total_jobs_list.length();
                    for (int i = 0; i < tmp.length(); i++) {
                        total_jobs_list.addJob(tmp.getJobInfoObject(i));
                        // TODO add new jobs to queue
                        queue.push(curlength + i);
                    }
                    jobQueueController.noticeNewJobs();

                    
                    // replace  setWorkingJob();
                    //add.setEnabled(false);
                    //jobStuts.clear();
                    //jobWorkingPanel.removeAll();
                    for (int i = 0; i < tmp.length(); i++) {

                        jobStuts.add(new JobStutsPanel(curlength+i));
                        jobWorkingPanel.add(jobStuts.get(curlength+i));
                    }
                    jobWorkingPanel.setPreferredSize(new Dimension(jobScorllPanel
                            .getWidth() - 50, jobScorllPanel.getHeight()
                            * (total_jobs_list.length() / 25)));
                    jobWorkingPanel.revalidate();
                    jobWorkingPanel.setVisible(false);
                    jobWorkingPanel.setVisible(true);
                    
                    //setWorkingJob();
                }
                serverList.setVisible(false);
                serverList.setVisible(true);
                deleteNumber.setEnabled(true);
                delete.setEnabled(true);
            }
        });

//        controlPanel.add(stop);
//        stop.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                // fresh.cancel();
//                System.gc();
//                add.setEnabled(false);
//                stop.setEnabled(false);
//                jMenuItem[0][0].setEnabled(true);
//
//            }
//        });
        controlPanel.add(newProject);
        newProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();

            }
        });
        workingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        workingPanel.setLayout(new BorderLayout());
        informationPanel.add(workingInfoPanel);
        informationPanel.add(serversInfoPanel);
        allPanel.add(informationPanel);
        allPanel.add(jobScorllPanel);
        // workingPanel.add(workingLabel,BorderLayout.NORTH);
        workingPanel.add(allPanel, BorderLayout.CENTER);
        workingPanel.add(controlPanel, BorderLayout.SOUTH);

    }

    // TODO  backup comment
//    public void setWorkingJob() {
//        if (total_jobs_list.length() == 0)
//            return;
//        //add.setEnabled(false);
//        System.gc();
//        jobStuts.clear();
//        jobWorkingPanel.removeAll();
//        for (int i = 0; i < total_jobs_list.length(); i++) {
//
//            jobStuts.add(new JobStutsPanel(i));
//            jobWorkingPanel.add(jobStuts.get(i));
//        }
//        jobWorkingPanel.setPreferredSize(new Dimension(jobScorllPanel
//                .getWidth() - 50, jobScorllPanel.getHeight()
//                * (total_jobs_list.length() / 25)));
//        jobWorkingPanel.revalidate();
//        jobWorkingPanel.setVisible(false);
//        jobWorkingPanel.setVisible(true);
//        //add.setEnabled(true);
//    };

    public void exit() {
        this.quit.windowClosing(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jMenuItem[0][0]) {
            restart();
        } else if (e.getSource() == jMenuItem[0][1]) {
            this.exit();
        } else if (e.getSource() == jMenuItem[1][0]) {
            this.serverListManger.setVisible(true);
        } else if (e.getSource() == jMenuItem[1][1]) {
            this.jobListManager.setVisible(true);
        } else if (e.getSource() == jMenuItem[2][0]) {

        } else if (e.getSource() == jMenuItem[2][1]) {

        } else if (e.getSource() == workingServerJList) {
            this.workingStutsLabel1.setText(workingServerJList
                    .getSelectedValue().toString() + " Status");
            workingStutsLabel1.setVisible(false);
            workingStutsLabel1.setVisible(true);
        }
    }

    @SuppressWarnings({ "rawtypes" })
	class MyCellRenderer extends JLabel implements ListCellRenderer {
        public MyCellRenderer() {
            setOpaque(false);
        }

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            // setBackground(isSelected ? Color.BLUE : Color.LIGHT_GRAY);
            setHorizontalAlignment(SwingConstants.CENTER);
            setText(value.toString());
            return this;
        }
    }

    public void refresh() {
        // "   total job:"
        int totalint = 0;
        if (this.total_jobs_list != null)
            totalint = total_jobs_list.length();
        if (totalJobLabel != null)
            totalJobLabel.setText(totalint + "");

        // "   finished job:"
        int finishint = MasterGUI.get_n_finished_jobs();
        if (finishedJobLabel != null)
            finishedJobLabel.setText(finishint + " of " + totalint);
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public void restart() {
        try {
            ArrayList list = new ArrayList();
            ProcessBuilder pb = null;
            Process p = null;
            String java = System.getProperty("java.home") + File.separator
                    + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            list.add(java);
            list.add("-classpath");
            list.add(classpath);
            list.add(MasterGUI.class.getName());

            pb = new ProcessBuilder(list);

            p = pb.start();

            System.out.println(pb.command());
        } catch (IOException e) {

            e.printStackTrace();
        }
        System.exit(0);
    }
}
