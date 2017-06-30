package client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Tool.CloseUtil.closeAll;

/**
 * Created by Dr.Wen on 2017/6/19.
 */
public class ClientReceiveThread implements Runnable{
    private JTextArea linktext,chattext;
    private Connection con;
    private DataOutputStream out;
    private DataInputStream in;
    private Boolean isRun = true;
    private String account = null;
    private String nickname = null;

    public ClientReceiveThread(Socket client,Connection con,JTextArea linktext,JTextArea chattext,String account,String nickname){
        this.con = con;
        this.linktext = linktext;
        this.chattext = chattext;
        this.account = account;
        this.nickname = nickname;
        try {
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
            System.out.println("ClientThread() out&in");
        } catch (IOException e) {
            System.out.println("ClientThread() closeall");
            isRun = false;
            closeAll();
        }
    }
    //接收消息
    private String receive(){
        String str = null;
        try {
            str = in.readUTF();
            System.out.println("receive readUTF");
        } catch (IOException e) {
            isRun = false;
            closeAll();
            System.out.println("receive closeall");
        }
        if (str==null || str.equals("")) {
            System.out.println("receive null");
            return null;
        }
        return str;
    }
    //发送事件消息
    private void send(String str){
        try {
            out.writeUTF(str);
            out.flush();
            System.out.println("send write");
        } catch (IOException e) {
            System.out.println("send closeall");
            closeAll();
        }
    }
    //绘制在线用户列表
    private void writeList(){
        linktext.setText("");
        String onlineaccount;
        String onlinenickname;
        String sql = "select * from OnlineUsers";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                onlineaccount = rs.getString(1);
                onlinenickname = rs.getString(2);
                linktext.append("昵称:"+onlinenickname+"\t账号:"+onlineaccount+"\n");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.exit(0);
        }
    }
    //在面板显示
    private void show(String str){
        chattext.append(str);
    }
    public void run(){
        send(account);
        send(nickname);
        writeList();
        String str;
        while(isRun){
            str = receive();
            System.out.println("接收消息");
            if (str != null) {
                show(str);
                System.out.println("显示");
                writeList();
            }
        }
    }
}
