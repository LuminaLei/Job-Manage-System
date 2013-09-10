package jms.gui;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.text.SimpleDateFormat; 
import java.util.Calendar; 
import javax.swing.JLabel; 
import javax.swing.Timer; 
import javax.swing.*;
/** 
* This class is use to show current time,can auto refresh 
* 
*/ 

public class CurrTimeLabel extends JLabel{ 
       /** 
        * Define a second 
        */ 
       private final static int ONE_SECOND=1000; 
        
        
       /** 
        * Timer Object,use to refresh label's text 
        */ 
       Timer timer=new Timer(ONE_SECOND,new ActionListener(){ 
               public void actionPerformed(ActionEvent evt){ 
                       setCurrTimeToText(); 
               } 
       });      
        
       /** 
        * Constructor 
        * @param strText 
        */ 
       public CurrTimeLabel(String strText){ 
               super(strText); 
               timer.start(); 
       } 
        
       /** 
        * Set current time to label's text 
        * 
        */ 
       private void setCurrTimeToText(){ 
               this.setText(getCurrTime()); 
       } 
        
       /** 
        * Get current time,format is yyyy/MM/dd HH:mm:ss 
        * @return current time String 
        */ 
       private String getCurrTime(){ 
               Calendar cal= Calendar.getInstance(); 
               SimpleDateFormat fromatter=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
               String strCurrTime=fromatter.format(cal.getTime()); 
                
               return strCurrTime; 
       } 
       public static void main(String args[]){
           JFrame x=new JFrame();
           CurrTimeLabel y=new CurrTimeLabel("ddd");
           x.add(y);
           x.setVisible(true);
           
       }
} 
