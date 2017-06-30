package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static Tool.CloseUtil.closeAll;
import static javax.swing.JOptionPane.YES_OPTION;

/**
 * Created by Dr.Wen on 2017/6/11.
 */
public class ClientFrame extends JFrame {
    private JLabel imglabel,linklabel,namelabel;
    private JButton logoutbutton,exitbutton,sendbutton;
    private JPanel selfpanel,labelpanel,linkpanel,chatpanel,textpanel;
    private JScrollPane linkscroll,chatscroll,inputscroll;
    private JTextArea linktext,chattext,inputtext;

    private Socket client;
    private DataOutputStream output;
    private Color blue = new Color(9,164,220);
    private Color gray = new Color(201,202,203);

    private String account = null;
    private String nickname = null;

    private Connection con;

    public ClientFrame(String account,String nickname){
        this.account = account;
        this.nickname = nickname;
        createFrame();
        onlineUsers();
        messageSend();
        addEvent();
    }
    //连接在线用户表并插入记录
    private void onlineUsers(){
        String sql = "insert into OnlineUsers() values(?,?)";
        PreparedStatement ps;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ChatSoftware?characterEncoding=utf8","root","wdywdy318");
            ps = con.prepareStatement(sql);
            ps.setString(1,account);
            ps.setString(2,nickname);
            ps.executeUpdate();
            ps.close();
        } catch (ClassNotFoundException e) {
            System.exit(0);
        } catch (SQLException e) {
            System.exit(0);
        }
    }
    //建立Socket通信
    private void messageSend(){
        try {
            client = new Socket("127.0.0.1",8888);
            new Thread(new ClientReceiveThread(client,con,linktext,chattext,account,nickname)).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"服务器没有启动!");
            System.exit(0);
        }
    }
    //添加事件方法
    private void addEvent(){
        //关闭按钮
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent event)
            {
                deleteUser();
                try {
                    output = new DataOutputStream(client.getOutputStream());
                    output.writeUTF("e1ex0xi2it9t");
                    output.flush();
                } catch (IOException e) {}
                try {
                    client.close();
                } catch (IOException e) {}
                System.exit(0);
            }
        });
        //注销按钮
        logoutbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int t = JOptionPane.showConfirmDialog(null,"您是否确认注销?","确认",JOptionPane.OK_CANCEL_OPTION);
                if(t == YES_OPTION){
                    deleteUser();
                    try {
                        output = new DataOutputStream(client.getOutputStream());
                        output.writeUTF("e1ex0xi2it9t");
                        output.flush();
                    } catch (IOException e) {}
                    closeAll();
                    try {
                        client.close();
                    } catch (IOException e) {}
                    chattext.setText("\t\t您已下线");
                }
            }
        });
        //退出按钮
        exitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int t = JOptionPane.showConfirmDialog(null,"您是否确认退出?","",JOptionPane.OK_CANCEL_OPTION);
                if (t == YES_OPTION){
                    deleteUser();
                    try {
                        output = new DataOutputStream(client.getOutputStream());
                        output.writeUTF("e1ex0xi2it9t");
                        output.flush();
                    } catch (IOException e) {}
                    closeAll();
                    try {
                        client.close();
                    } catch (IOException e) {}
                    System.exit(0);
                }
            }
        });
        //发送按钮
        sendbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    output = new DataOutputStream(client.getOutputStream());
                    System.out.println("new output");
                } catch (IOException e) {
                    System.out.println("new output closeall");
                    closeAll();
                }
                String str = inputtext.getText();
                if (str!=null && !str.equals("")){
                    Date date=new Date(System.currentTimeMillis());
                    DateFormat format=new SimpleDateFormat("HH:mm:ss");
                    String time=format.format(date);
                    System.out.println(time);
                    try {
                        output.writeUTF(nickname+"  "+time+"\n     "+str+"\n");
                        output.flush();
                        System.out.println("button write");
                    } catch (IOException e) {
                        closeAll();
                        System.out.println("button closeall");
                    }
                    chattext.append("我:\n");
                    chattext.append("     "+str+"\n");
                    inputtext.setText("");
                }
            }
        });
    }
    //删除在线用户表记录
    private void deleteUser(){
        String sql = "delete from OnlineUsers where account=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,account);
            ps.executeUpdate();
            ps.close();
            System.out.println("delete");
        } catch (SQLException e) {
            System.out.println("delete exit");
            System.exit(0);
        }
    }

    //创建Frame函数
    private void createFrame(){
        add(createSelfpanel());
        add(createLabelpanel());
        add(createLinkpanel());
        add(createChatpanel());
        add(createTextpanel());
        //设置Frame
        setLayout(null);
        setBackground(new Color(250,250,250));
        setVisible(true);
        setDefaultCloseOperation(3);
        setSize(800,550);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    //创建selfpanel
    private JPanel createSelfpanel(){
        //头像Label
        ImageIcon headimg = new ImageIcon("image/头像.jpg");
        headimg.setImage(headimg.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
        imglabel = new JLabel(headimg);
        imglabel.setBounds(10,10,50,50);
        //注销按钮
        logoutbutton = new JButton("注销");
        logoutbutton.setBounds(5,455,60,30);
        logoutbutton.setForeground(blue);
        //退出按钮
        exitbutton = new JButton("退出");
        exitbutton.setBounds(5,485,60,30);
        exitbutton.setForeground(blue);
        //设置selfpanel
        selfpanel = new JPanel();
        selfpanel.setLayout(null);
        selfpanel.setBounds(0,0,70,550);
        selfpanel.setBackground(blue);
        //添加组件
        selfpanel.add(imglabel);
        selfpanel.add(logoutbutton);
        selfpanel.add(exitbutton);
        return selfpanel;
    }
    //创建labelpanel
    private JPanel createLabelpanel(){
        linklabel = new JLabel("在线用户",JLabel.CENTER);
        linklabel.setFont(new Font(null,Font.PLAIN,20));
        linklabel.setBounds(0,0,250,50);

        labelpanel = new JPanel();
        labelpanel.setLayout(null);
        labelpanel.setBackground(new Color(250,250,250));
        labelpanel.setBounds(70,0,250,50);
        labelpanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
        labelpanel.add(linklabel);
        return labelpanel;
    }
    //创建linkpanel
    private JPanel createLinkpanel(){
        linktext = new JTextArea();
        linktext.setOpaque(false);
        linktext.setLineWrap(true);
        linktext.setEditable(false);
        //设置联系人滚动框
        linkscroll = new JScrollPane(linktext);
        linkscroll.setOpaque(false);
        linkscroll.getViewport().setOpaque(false);
        linkscroll.setBounds(0,0,240,550);
        linkscroll.setBorder(BorderFactory.createEmptyBorder());
        //设置linkpanel
        linkpanel = new JPanel();
        linkpanel.setBackground(new Color(250,250,250));
        linkpanel.setBounds(80,50,240,550);
        linkpanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
        linkpanel.setLayout(null);
        linkpanel.add(linkscroll);
        return linkpanel;
    }
    //创建chatpanel
    private JPanel createChatpanel(){
        namelabel = new JLabel("聊天室",JLabel.CENTER);
        namelabel.setFont(new Font(null,Font.PLAIN,20));
        namelabel.setBounds(0,0,480,50);
        namelabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,gray));
        //聊天文本框
        chattext = new JTextArea();
        chattext.setOpaque(false);
        chattext.setLineWrap(true);
        chattext.setEditable(false);
        //聊天滚动框
        chatscroll = new JScrollPane(chattext);
        chatscroll.setOpaque(false);
        chatscroll.getViewport().setOpaque(false);
        chatscroll.setBorder(BorderFactory.createEmptyBorder());
        chatscroll.setBounds(10,60,460,280);
        //设置chatpanel
        chatpanel = new JPanel();
        chatpanel.setLayout(null);
        chatpanel.setBackground(new Color(240,240,240));
        chatpanel.setBounds(320,0,480,350);
        chatpanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,gray));
        //添加组件
        chatpanel.add(chatscroll);
        chatpanel.add(namelabel);
        return chatpanel;
    }
    //创建textpanel
    private JPanel createTextpanel(){
        //发送按钮
        sendbutton = new JButton("发送");
        sendbutton.setBounds(410,10,70,160);
        sendbutton.setBorder(BorderFactory.createMatteBorder(0,1,0,0,gray));
        //输入框
        inputtext = new JTextArea();
        inputtext.setLineWrap(true); //设置自动换行
        inputtext.setOpaque(false);
        //滚动框
        inputscroll = new JScrollPane(inputtext);
        inputscroll.setBounds(15,15,395,150);
        inputscroll.setBorder(BorderFactory.createEmptyBorder());
        inputscroll.setOpaque(false);
        inputscroll.getViewport().setOpaque(false); //设置文本框透明，三处都要有
        //设置textpanel
        textpanel = new JPanel();
        textpanel.setLayout(null);
        textpanel.setBackground(new Color(240,240,240));
        textpanel.setBounds(320,350,480,200);
        //添加组件
        textpanel.add(sendbutton);
        textpanel.add(inputscroll);
        return textpanel;
    }
}
