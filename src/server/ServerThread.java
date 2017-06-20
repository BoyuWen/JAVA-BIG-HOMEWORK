package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static Tool.CloseUtil.closeAll;

/**
 * Created by Dr.Wen on 2017/6/18.
 */
public class ServerThread implements Runnable{
    private ArrayList<ServerThread> all;
    private DataOutputStream out;
    private DataInputStream in;
    private Boolean isRun = true;

    public ServerThread(Socket server, ArrayList<ServerThread> all){
        this.all = all;
        try{
            out = new DataOutputStream(server.getOutputStream());
            in = new DataInputStream(server.getInputStream());
        }catch(IOException e){
            isRun = false;
            closeAll();
            this.all.remove(this);
        }
    }
    private String receive(){
        String str = null;
        try {
            str = in.readUTF();
        } catch (IOException e) {
            isRun = false;
            closeAll();
            this.all.remove(this);
        }
        if (str==null || str.equals("")){
            return null;
        }
        return str;
    }
    private void send(String str){
        if (str!=null && !str.equals("")){
            try {
                out.writeUTF(str);
                out.flush();
            } catch (IOException e) {
                isRun = false;
                closeAll();
                all.remove(this);
            }
        }
    }
    private void sendOthers(){
        String str = this.receive();
        for (ServerThread temp:all){
            if (temp != this){
                temp.send(str);
            }
        }
    }

    public void run(){
        while(isRun){
            sendOthers();
        }
    }
}