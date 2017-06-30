package server;

import javax.swing.*;
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

    private String account;
    private String nickname;

    private JTextArea text1;
    private JTextArea text3;

    public ServerThread(Socket server, ArrayList<ServerThread> all,String account,String nickname,JTextArea text1,JTextArea text3){
        this.all = all;
        this.account = account;
        this.nickname = nickname;
        this.text1 = text1;
        this.text3 = text3;
        try{
            out = new DataOutputStream(server.getOutputStream());
            in = new DataInputStream(server.getInputStream());
        }catch(IOException e){
            isRun = false;
            closeAll();
            this.all.remove(this);
        }
    }
    public void send(String str){
        if (str!=null && !str.equals("")){
            try {
                out.writeUTF(str);
                out.flush();
                System.out.println("send write");
            } catch (IOException e) {
                isRun = false;
                closeAll();
                all.remove(this);
                System.out.println("send closeall");
            }
        }
    }
    public String getAccount(){
        return account;
    }
    public String getNickname(){
        return nickname;
    }
    private String receive(){
        String str = null;
        try {
            str = in.readUTF();
            System.out.println("receive read");
        } catch (IOException e) {
            System.out.println("receive closeall");
            isRun = false;
            closeAll();
            this.all.remove(this);
        }
        if (str==null || str.equals("")){
            System.out.println("receive null");
            return null;
        }

        System.out.print(str);
        return str;
    }
    private void sendOthers(String str){
        System.out.println("send others");
        for (ServerThread temp:all){
            System.out.println("send others for all");
            if (temp != this){
                temp.send(str);
            }
        }
    }
    private void sendAll(String str){
        System.out.println("sendall");
        for (ServerThread temp:all){
            temp.send(str);
        }
    }
    //判断远程连接是否断开
    private Boolean isServerClose(String str){
        if (str.equals("e1ex0xi2it9t")){
            return true;
        }else{
            return false;
        }
    }
    //绘制联系人列表
    private void writeList(){
        String accountwrite;
        String nicknamewrite;
        text3.setText("");
        text3.append("\t账号\t昵称\n");
        for (ServerThread temp:all){
            accountwrite = temp.getAccount();
            nicknamewrite = temp.getNickname();
            text3.append("\t"+accountwrite+"\t"+nicknamewrite+"\n");
        }
    }
    public void run(){
        String str;
        while(isRun){
            str = receive();
            if (str == null){
                continue;
            }
            if (isServerClose(str)){  //断开
                sendAll("\t                用户 " + nickname + " 离开聊天室\n");
                all.remove(this);
                text1.append("用户:" + account + "," + nickname + " 已下线\n");
                writeList();
            }else{
                sendOthers(str);
                System.out.println("完毕");
            }
        }
    }
}