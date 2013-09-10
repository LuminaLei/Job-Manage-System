package jms.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JobStutsPanel extends JPanel implements ActionListener {
    JLabel jobname = new JLabel();
    JPanel borderPanel = new JPanel();
    int index;
    //ArrayList<String> server = new ArrayList<String>();
    int Stuts;
    boolean isSeleted;

    public JobStutsPanel() {
        super();
        jobname.setText("");
        this.setDefault();
    }

    public JobStutsPanel(String s) {
        super();
        jobname.setText(s);
        this.setDefault();
    }

    public JobStutsPanel(int index) {
        super();
        this.index = index;
        if(index<10) jobname.setText("job  " + index);
        else jobname.setText("job " + index);
        this.setDefault();
    }

    private void setDefault() {
//        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 10));
        this.changeStuts("1");
        this.Stuts = 1;
        this.setLayout(new GridLayout(1, 1));
        borderPanel.setBorder(BorderFactory.createBevelBorder(0));
        borderPanel.add(jobname);
        this.add(borderPanel);
        this.isSeleted = false;
        this.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                getSeleted();               
            }

            public void mousePressed(MouseEvent e) {

                
            }

            public void mouseReleased(MouseEvent e) {
                
            }

            public void mouseEntered(MouseEvent e) {
                
            }

            public void mouseExited(MouseEvent e) {
                
            }
            
        });

    }

    public void changeStuts(int s) {
        String x=s+"";
        changeStuts(x);
    }

    public void changeStuts(String s) {
        if (s.equals("Waiting") || s.equals("1")) {
            borderPanel.setBackground(Color.yellow);
            this.Stuts = 1;
        }
        if (s.equals("Running") || s.equals("2")) {
            borderPanel.setBackground(Color.cyan);
            this.Stuts = 2;
        }
        if (s.equals("Finished") || s.equals("3")) {
            borderPanel.setBackground(Color.green);
            this.Stuts = 3;
        }

        if (s.equals("Terminated") || s.equals("4")) {
            borderPanel.setBackground(Color.red);
            this.Stuts = 4;
        }
        this.setToolTipText("");
        borderPanel.setVisible(false);
        borderPanel.setVisible(true);

    }

    public void setToolTipText(String s) {
        String str = "";
        if(s==null) s = "";
        
        if (this.Stuts == 1) {
            str = "<html>job " + this.index + " is waiting</html>";
        } 
        else if (this.Stuts == 2) {
        	str += s;
            //str = "job " + this.index + " is running on worker " + s;
//            for (int x = 0; x < this.server.size(); x++) {
//                str += this.server.get(x) + " ";
//            }
        } 
        else if (this.Stuts == 3) {
            str = "job " + this.index + " is completed by worker "+s;
//            for (int x = 0; x < this.server.size(); x++) {
//                str += this.server.get(x) + " ";
//            }
        } 
        else if (this.Stuts == 4) {
            str = "job " + this.index + " is terminated by the reason that "
                    + s;
        } 
        else {
            
        }
        
        super.setToolTipText(s);
    }

    public void getSeleted() {
        if (this.isSeleted == false) {
            this.borderPanel.setBorder(BorderFactory.createBevelBorder(0));
            this.isSeleted = true;
        } else {
            this.borderPanel.setBorder(BorderFactory.createBevelBorder(1));
            this.isSeleted = false;
        }
    }

    public void actionPerformed(ActionEvent e) {

    }

}
