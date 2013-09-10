package jms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import jms.gui.Verify;
import jms.model.WorkerList;
import jms.util.Hash;

public class WorkerListManager extends JFrame implements ActionListener,
        Runnable {
    int MAXSERVERNUMBER = 200;
    FileDialog fd;
    JButton save;
    JPanel serverManagerPanel[] = new JPanel[MAXSERVERNUMBER];
    Container j;
    JLabel serverLabel[] = new JLabel[MAXSERVERNUMBER];
    JTextField severNameText[] = new JTextField[MAXSERVERNUMBER];
    JLabel addressLabel[] = new JLabel[MAXSERVERNUMBER];
    JTextField addressText[] = new JTextField[MAXSERVERNUMBER];
    JLabel portLabel[] = new JLabel[MAXSERVERNUMBER];
    JTextField portText[] = new JTextField[MAXSERVERNUMBER];
    JLabel passwordLabel[] = new JLabel[MAXSERVERNUMBER];
    JPasswordField passwordText[] = new JPasswordField[MAXSERVERNUMBER];
    JLabel priceLabel[] = new JLabel[MAXSERVERNUMBER];
    JTextField priceText[] = new JTextField[MAXSERVERNUMBER];
    JButton deleteButton[] = new JButton[MAXSERVERNUMBER];
    JButton clearButton[] = new JButton[MAXSERVERNUMBER];
    Boolean done[] = new Boolean[MAXSERVERNUMBER];
    JScrollPane serverScrollPane;
    int serversNumber;
    JPanel serverListPanel;
    int liveServersNumber;
    String fileName;
    JLabel informaiton;
    private Container c = getContentPane();
    private Frame o;

    public WorkerListManager(Container x) {
        j = x;
        serversNumber = 0;
        liveServersNumber = 1;
        o = this;
        this.run();
    }

    public void run() {
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocation((int) (j.getLocation().getX() + 500), (int) j
                .getLocation().getY() + 50);
        this.setSize(350, 300);
        this.setTitle("Server List Manager");
        for (int x = 0; x < MAXSERVERNUMBER; x++) {
            done[x] = false;
        }
        this.setPanel();
    }

    public void setPanel() {

        informaiton = new JLabel(("Number of Server : " + liveServersNumber));
        informaiton.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        serverListPanel = new JPanel();
        serverListPanel.setBorder(BorderFactory
                .createTitledBorder("Server List"));

        serverScrollPane = new JScrollPane(serverListPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        serverScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        serverListPanel.setLayout(new BoxLayout(serverListPanel, 1));
        this.setLayout(new BorderLayout());
        this.setServerPanel(serversNumber);
        done[serversNumber] = true;
        serverListPanel.add(serverManagerPanel[serversNumber]);
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        controlPanel.setLayout(new FlowLayout());
        JButton add = new JButton("add");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (liveServersNumber >= MAXSERVERNUMBER) {
                    JOptionPane.showMessageDialog(c,
                            "You have added max the servers to the list");
                    return;
                }
                do {
                    if (serversNumber < MAXSERVERNUMBER - 1) {
                        serversNumber++;
                    } else {
                        serversNumber = 0;
                    }
                } while (done[serversNumber] == true);
                setServerPanel(serversNumber);
                serverListPanel.add(serverManagerPanel[serversNumber]);
                serverListPanel.setVisible(false);
                serverListPanel.setVisible(true);
                liveServersNumber++;
                informaiton
                        .setText(("Number of Server : " + liveServersNumber));
                informaiton.setVisible(false);
                informaiton.setVisible(true);
                done[serversNumber] = true;
            }
        });
        save = new JButton("save");
        save.addActionListener(this);
        JButton newfile = new JButton("new");
        newfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int x = 0; x < MAXSERVERNUMBER; x++) {
                    if (done[x] == true) {
                        serverListPanel.remove(serverManagerPanel[x]);
                    }
                    done[x] = false;
                }
                serversNumber = 0;
                liveServersNumber = 1;
                informaiton
                        .setText(("Number of Server : " + liveServersNumber));
                informaiton.setVisible(false);
                informaiton.setVisible(true);
                setServerPanel(serversNumber);
                serverListPanel.add(serverManagerPanel[serversNumber]);
                serverListPanel.setVisible(false);
                serverListPanel.setVisible(true);
                done[serversNumber] = true;
                serverListPanel.setVisible(false);
                serverListPanel.setVisible(true);
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
        this.add(serverScrollPane, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
    }

    public void setServerPanel(int index) {
        serverManagerPanel[index] = new JPanel();
        serverManagerPanel[index].setLayout(new GridLayout(6, 2));
        serverManagerPanel[index].setBorder(BorderFactory.createBevelBorder(1));
        serverLabel[index] = new JLabel("server name: ");
        severNameText[index] = new JTextField();
        severNameText[index].setColumns(20);
        addressLabel[index] = new JLabel("address : ");
        addressText[index] = new JTextField();
        addressText[index].setColumns(20);
        portLabel[index] = new JLabel("port : (1024-65535)");
        portText[index] = new JTextField();
        portText[index].setColumns(20);
        passwordLabel[index] = new JLabel("password : ");
        passwordText[index] = new JPasswordField();
        passwordText[index].setColumns(20);
        priceLabel[index] = new JLabel("prince : ($/Sec)");
        priceText[index] = new JTextField();
        priceText[index].setColumns(20);
        deleteButton[index] = new JButton("Delete");
        clearButton[index] = new JButton("clear");
        deleteButton[index].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton obj = (JButton) e.getSource();
                int tempindex = 0;
                for (int i = 0; i < MAXSERVERNUMBER; i++) {
                    if (obj == deleteButton[i]) {
                        tempindex = i;
                        break;
                    }
                }
                serverListPanel.remove(serverManagerPanel[tempindex]);
                serverListPanel.setVisible(false);
                serverListPanel.setVisible(true);
                liveServersNumber--;
                informaiton
                        .setText(("Number of Server : " + liveServersNumber));
                informaiton.setVisible(false);
                informaiton.setVisible(true);
                done[tempindex] = false;
            }
        });
        clearButton[index].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton obj = (JButton) e.getSource();
                int tempindex = 0;
                for (int i = 0; i < MAXSERVERNUMBER; i++) {
                    if (obj == clearButton[i]) {
                        tempindex = i;
                        break;
                    }
                }
                severNameText[tempindex].setText("");
                addressText[tempindex].setText("");
                portText[tempindex].setText("");
                passwordText[tempindex].setText("");
                priceText[tempindex].setText("");
            }
        });
        serverManagerPanel[index].add(serverLabel[index]);
        serverManagerPanel[index].add(severNameText[index]);
        serverManagerPanel[index].add(addressLabel[index]);
        serverManagerPanel[index].add(addressText[index]);
        serverManagerPanel[index].add(portLabel[index]);
        serverManagerPanel[index].add(portText[index]);
        serverManagerPanel[index].add(passwordLabel[index]);
        serverManagerPanel[index].add(passwordText[index]);
        serverManagerPanel[index].add(priceLabel[index]);
        serverManagerPanel[index].add(priceText[index]);
        serverManagerPanel[index].add(deleteButton[index]);
        serverManagerPanel[index].add(clearButton[index]);

    }

    public boolean verify() {
        int times = 0;
        boolean pass = true;
        for (int x = 0; x < MAXSERVERNUMBER; x++) {
            if (times >= this.liveServersNumber) {
                break;
            }
            if (done[x] == true) {
                String dd = severNameText[x].getText();
                if (dd.length() == 0) {
                    severNameText[x].requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "please give an name for the server");
                    pass = false;
                    break;
                }
                if (addressText[x].getText().length() == 0) {
                    addressText[x].requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "please give an address for the server");
                    pass = false;
                    break;
                }
//                if (!Verify.isUrl(addressText[x].getText())
//                        || !addressText[x].getText().equals("localhost")
//                        ||!addressText[x].getText().equals("Localhost")) {
//                    addressText[x].requestFocusInWindow();
//                    JOptionPane.showMessageDialog(this,
//                            "please give an vaild address for the server");
//                    pass = false;
//                    break;
//                }
                if (portText[x].getText().length() == 0) {
                    portText[x].requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "please give an port for the server");
                    pass = false;
                    break;
                }
                if (!Verify.isInt(portText[x].getText())) {
                    portText[x].requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "please give vaild port for the server");
                    pass = false;
                    break;
                }
                int porttemp = Integer.valueOf(portText[x].getText());
                if (porttemp < 1024 || porttemp > 65535) {
                    portText[x].requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "please give port from 1024 to 65535");
                    pass = false;
                    break;
                }
                if (passwordText[x].getPassword().length == 0) {
                    passwordText[x].requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "please give an password for the server");
                    pass = false;
                    break;
                }
                if (priceText[x].getText().length() == 0) {
                    priceText[x].requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "please give an price for the server");
                    pass = false;
                    break;
                }
                if (!Verify.isNum(priceText[x].getText())) {
                    priceText[x].requestFocusInWindow();
                    JOptionPane.showMessageDialog(this,
                            "please give vaild price for the server");
                    pass = false;
                    break;
                }
            }
        }
        return pass;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            if (verify() == true) {
                fd = new FileDialog(o, "Save as...", FileDialog.SAVE);
                ArrayList<String> fileFilterText = new ArrayList<String>();
                fileFilterText.add("wl");
                fileFilterText.add("WL");
                fileFilterText.add("Wl");
                fileFilterText.add("wL");
                fd.setFilenameFilter(new MyFilter(fileFilterText));
                fd.setAlwaysOnTop(true);
                fd.setFile(".wl");
                fd.setVisible(true);

                if ((fd.getDirectory() != null) && (fd.getFile() != null)) {
                    fileName = fd.getDirectory() + fd.getFile();
                    makeFile(fileName);
                }
            }
        }
    }

    public boolean makeFile(String filename) {
        boolean flag = false;
        System.out.println(filename);
        WorkerList wlist = new WorkerList();
        for (int i = 0; i < MAXSERVERNUMBER; i++) {
            if (done[i] == true) {
                String workerName = severNameText[i].getText();
                String address = addressText[i].getText();
                int port;
                port = Integer.parseInt(portText[i].getText());
                String pwd = new String(passwordText[i].getPassword());
                String hashedpwd = Hash.MD5(pwd);
                double price;
                price = Double.parseDouble(priceText[i].getText());

                wlist.addWorker(WorkerList.createWorker(workerName, address,
                        port, hashedpwd, price));

                System.out.print(workerName + " ");
                System.out.print(address + " ");
                System.out.print(port + " ");
                System.out.print(pwd + " " + hashedpwd + " ");
                System.out.print(price + " " + "\n");
            }
        }// end for
         //
         // add the file wl to the file
        flag = wlist.saveAs(filename);
        //
        return flag;
    }

    static class MyFilter implements FilenameFilter {
        ArrayList<String> type;

        public MyFilter(String type) {
            this.type = new ArrayList<String>();
            this.type.add(type);
        }

        public MyFilter(ArrayList<String> type) {
            this.type = new ArrayList<String>();
            for (int i = 0; i < type.size(); i++) {
                this.type.add(type.get(i));
            }
        }

        public boolean accept(File dir, String name) {
            boolean isVaild = false;
            for (int x = 0; x < this.type.size(); x++) {
                if (name.endsWith(type.get(x))) {
                    isVaild = true;
                    break;
                }
            }
            return isVaild;
        }

    }

}
