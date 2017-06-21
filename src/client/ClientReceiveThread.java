package client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

import static Tool.CloseUtil.closeAll;

/**
 * Created by Dr.Wen on 2017/6/19.
 */
public class ClientReceiveThread implements Runnable{
    JTextArea chattext;
    private DataOutputStream out;
    private DataInputStream in;
    private Boolean isRun = true;
    private String account = null;
    private String nickname = null;

    public ClientReceiveThread(Socket client, JTextArea chattext,String account,String nickname){
        this.chattext = chattext;
        this.account = account;
        this.nickname = nickname;
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
    //发送事件消息
    private void send(String str){
        try {
            out.writeUTF(str);
            out.flush();
        } catch (IOException e) {

        }

    }
    //在面板显示
    private void show(String str){
        chattext.append("对方:\n");
        chattext.append("    "+str+"\n");
    }
    public void run(){
        send("用户:"+nickname+" 已登录");
        String str = null;
        while(isRun){
            str = receive();
            if (str != null) {
                show(str);
            }
        }
    }
}
