package jms.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServerStutsPanel extends JPanel implements ActionListener {
    JLabel servername = new JLabel();
    JLabel serverPrice = new JLabel();
    JLabel serverAddress = new JLabel();
    JLabel serverPort = new JLabel();
    JPanel borderPanel = new JPanel();
    JLabel tick=new JLabel();
    int index;
    int Stuts;
    JLabel photo ;
    ImageIcon serverPhoto;
    ImageIcon tickPhoto;
    boolean isSeleted;

    public ServerStutsPanel() {
        super();
        servername.setText("");
        this.setDefault();
        this.Stuts = 1;
    }

    public ServerStutsPanel(String s) {
        super();
        servername.setText(s);
        this.setDefault();
        this.Stuts = 1;
    }

    public ServerStutsPanel(int index) {
        super();
        this.index = index;
        servername.setText("Server " + index);
        this.setDefault();
        this.Stuts = 1;
    }

    public ServerStutsPanel(int index, String s) {
        super();
        this.index = index;
        servername.setText(s);
        this.setDefault();
        this.Stuts = 1;
    }

    public ServerStutsPanel(int index, String servername, String serverPrice,
            String serverAddress, String port) {
        this(index, servername);
        this.serverPrice.setText("$"+serverPrice+"/sec");
        this.serverAddress.setText(serverAddress);
        this.serverPort.setText(port);
        this.Stuts = 1;
    }

    public ServerStutsPanel(int index, String servername, String serverPrice,
            String serverAddress, String port, int stuts) {
        this(index, servername, serverPrice, serverAddress, port);
        this.Stuts = stuts;
    }

    private void setDefault() {
        // this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 10));
        this.setLayout(new BorderLayout(20,20));
        this.setBorder(BorderFactory.createBevelBorder(0));
        ImageIcon xx=new ImageIcon("server.gif");
        
        photo = new JLabel(xx);
        xx=new ImageIcon("tick.gif");
        tick=new JLabel(xx);
        tick.setOpaque(true);
        photo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        //this.repaint();
        this.add(photo,BorderLayout.WEST);
        borderPanel.setLayout(new GridLayout(4,2));
        borderPanel.add(new JLabel("     Server Name : "));
        borderPanel.add(servername);
        borderPanel.add(new JLabel("     Server Price : "));
        borderPanel.add(this.serverPrice);
        borderPanel.add(new JLabel("     Server Address : "));
        borderPanel.add(this.serverAddress);
        borderPanel.add(new JLabel("     Server Port : "));
        borderPanel.add(this.serverPort);
        this.add(borderPanel,BorderLayout.CENTER);
        this.add(tick,BorderLayout.EAST);
        this.isSeleted = true;
        this.tick.setVisible(true);
        this.addMouseListener(new MouseListener() {
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

    // public void setToolTipText(String s) {
    // String str = "";
    // if (this.Stuts == 1) {
    // str = "job " + this.index + " is waiting";
    // super.setToolTipText(str);
    // } else if (this.Stuts == 2) {
    // str = "job " + this.index + " is running on servers of ";
    // for (int x = 0; x < this.server.size(); x++) {
    // str += this.server.get(x) + " ";
    // }
    // super.setToolTipText(str);
    // } else if (this.Stuts == 3) {
    // str = "job " + this.index + " is completed by servers ";
    // for (int x = 0; x < this.server.size(); x++) {
    // str += this.server.get(x) + " ";
    // }
    // super.setToolTipText(str);
    // } else if (this.Stuts == 4) {
    // str = "job " + this.index + " is terminated by the reason that "
    // + s;
    // super.setToolTipText(str);
    // } else {
    // super.setToolTipText(s);
    // }
    // }

    public void getSeleted() {
        if (this.isSeleted == false) {
            this.setBorder(BorderFactory.createBevelBorder(0));
            this.isSeleted = true;
            this.tick.setVisible(true);
        } else {
            this.setBorder(BorderFactory.createBevelBorder(1));
            this.isSeleted = false;
            this.tick.setVisible(false);
        }
    }
//    public void paint(Graphics g){
//        g.drawImage(m_Images, 50, 50, photo);
//     }
    public void actionPerformed(ActionEvent e) {

    }

}
