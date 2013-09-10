package jms.gui;

import java.util.ArrayList;

public class JobOnServer {
    String name;
    int index;
    ArrayList <String> servers=new ArrayList <String>();
    int stuts=1;
    public JobOnServer(int x){
        this.index=x;
    }
    public JobOnServer(int x,int y){
        this.index=x;
        this.stuts=y;
    }
    public void setStuts(int x){
        this.stuts=x;
    }
}
