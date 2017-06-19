package server;

import java.io.*;
import java.net.Socket;

import static Tool.CloseUtil.closeAll;

/**
 * Created by Dr.Wen on 2017/6/18.
 */
public class ServerThread implements Runnable{
    private DataOutputStream out;
    private DataInputStream in;
    private Boolean isRun = true;

    private ServerThread(Socket server){
        try{
            out = new DataOutputStream(server.getOutputStream());
            in = new DataInputStream(server.getInputStream());
        }catch(IOException e){
            isRun = false;
            closeAll();
        }
    }

    public void run(){

    }
}