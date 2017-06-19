package client;

import java.io.*;
import java.net.Socket;

import static Tool.CloseUtil.closeAll;

/**
 * Created by Dr.Wen on 2017/6/19.
 */
public class ClientReceiveThread implements Runnable{
    private DataOutputStream out;
    private DataInputStream in;
    private Boolean isRun = true;

    public ClientReceiveThread(Socket client){
        try {
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            isRun = false;
            closeAll();
        }
    }
    //接收消息
    private String receive(){
        String str = null;
        try {
            str = in.readUTF();
        } catch (IOException e) {
            isRun = false;
            closeAll();
        }
        if (str==null || str.equals("")) {
            return null;
        }
        return str;
    }
    //在面板显示
    private void show(String str){

    }

    public void run(){
        String str = null;
        while(isRun){
            str = receive();
            if (str != null) {
                show(str);
            }
        }
    }
}
