package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import static Tool.CloseUtil.closeAll;


/**
 * Created by Dr.Wen on 2017/6/11.
 */
public class ServerFrame extends JFrame implements ActionListener {
    private JButton button1,button3,startbutton;
    private JTextArea text1,text3;
    private JScrollPane scroll1,scroll3;
    private JPanel buttonpanel,tabpanel,startpanel;

    private CardLayout card = new CardLayout();
    private Font forefont = new Font(null,0,15);
    private Font hindfont = new Font(null,1,17);
    private Color blue = new Color(9,164,220);
    private Color gray = new Color(231,231,231);

    private ArrayList<ServerThread> all = new ArrayList<ServerThread>();
    private ServerSocket serversocket;

    private ServerFrame(){
        createFrame();
        addEvent();
    }
    //生成Frame方法
    private void createFrame(){
        add(createButtonpanel());
        add(createTabpanel());
        add(createStartpanel());
        //设置Frame
        setLayout(null);
        setTitle("服务器端");
        setSize(320,465);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    //
    private void addEvent(){
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                int t = JOptionPane.showConfirmDialog(null, "确认要退出服务器吗？", "确认退出", JOptionPane.OK_CANCEL_OPTION);
                if (t == JOptionPane.OK_OPTION)
                {
                    System.exit(0);
                }
            }
        });
        startbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    serversocket = new ServerSocket(8888);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            startServer();
                        }
                    }).start();
                }catch (IOException e){}
            }
        });
        button1.addActionListener(this);
        button3.addActionListener(this);
    }
    //
    private void startServer(){
        while(true){
            try{
                text1.append("监听客户端连接中...\n");
                Socket server = serversocket.accept();
                DataInputStream in = new DataInputStream(server.getInputStream());
                text1.append("已从IP "+server.getInetAddress().getHostAddress()+",端口 "+server.getPort()+" 接收到数据...\n");
                String account = in.readUTF();
                String nickname = in.readUTF();
                ServerThread serverthread = new ServerThread(server,all,account,nickname,text1,text3);
                all.add(serverthread);
                text1.append("用户:"+account+","+nickname+" 已上线\n");
                writeList();
                sendAll(nickname);
                new Thread(serverthread).start();
            }catch (IOException e){
                closeAll();
            }
        }
    }
    //发送系统消息
    private void sendAll(String nickname){
        for (ServerThread temp:all){
            String str = "\t                用户 "+nickname+" 进入聊天室\n";
            temp.send(str);
        }
    }
    //绘制在线用户列表
    private void writeList(){
        String account;
        String nickname;
        text3.setText("");
        text3.append("\t账号\t昵称\n");
        for (ServerThread temp:all){
            account = temp.getAccount();
            nickname = temp.getNickname();
            text3.append("\t"+account+"\t"+nickname+"\n");
        }
    }
    //
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == button1){
            setButton1();
            restoreButton3();
            card.first(tabpanel);
        }else if(e.getSource() == button3){
            setButton3();
            restoreButton1();
            card.last(tabpanel);
        }
    }
    //创建buttonpanel
    private JPanel createButtonpanel(){
        button1 = new JButton("状态监听");
        setButton1();
        button3 = new JButton("在线用户");
        restoreButton3();
        //设置buttonpanel
        buttonpanel = new JPanel(null);
        buttonpanel.setLayout(null);
        buttonpanel.setBounds(0,0,320,40);
        buttonpanel.setBackground(blue);
        buttonpanel.add(button1);
        buttonpanel.add(button3);
        return buttonpanel;
    }
    //创建tabpanel
    private JPanel createTabpanel(){
        //创建两个文本框
        text1 = new JTextArea();
        text1.setOpaque(false);
        text1.setLineWrap(true);
        text1.setEditable(false);
        text3 = new JTextArea();
        text3.setOpaque(false);
        text3.setLineWrap(true);
        text3.setEditable(false);
        //创建两个滚动框
        scroll1 = new JScrollPane(text1);  //第一个
        scroll1.setOpaque(false);
        scroll1.getViewport().setOpaque(false);
        scroll1.setBorder(BorderFactory.createEmptyBorder());
        scroll3 = new JScrollPane(text3);  //第三个
        scroll3.setOpaque(false);
        scroll3.getViewport().setOpaque(false);
        scroll3.setBorder(BorderFactory.createEmptyBorder());
        //设置tabpanel
        tabpanel = new JPanel();
        tabpanel.setLayout(card);     //这里一定要用已经创建的card！要和在事件中的card相同！不可以用 new CardLayou()!
        tabpanel.setBounds(10,45,300,330);
        tabpanel.setBorder(BorderFactory.createEmptyBorder());
        tabpanel.add(scroll1,"状态");
        tabpanel.add(scroll3,"用户");
        return tabpanel;
    }
    //创建startpanel
    private JPanel createStartpanel(){
        //设置开始按钮
        startbutton = new JButton("运行");
        startbutton.setFont(forefont);
        startbutton.setBounds(110,5,100,40);
        startbutton.setForeground(blue);
        //设置startpanel
        startpanel = new JPanel();
        startpanel.setLayout(null);
        startpanel.setBounds(0,395,320,50);
        startpanel.setBackground(blue);
        startpanel.add(startbutton);
        return startpanel;
    }
    //设置button1样式
    private void setButton1(){
        button1.setBounds(0,0,160,40);
        button1.setFont(hindfont);
        button1.setForeground(blue);
        button1.setBackground(gray);
        button1.setOpaque(true);
        button1.setBorderPainted(false);
    }
    //还原button1样式
    private void restoreButton1(){
        button1.setFont(forefont);
        button1.setBounds(0,7,160,26);
        button1.setOpaque(false);
        button1.setForeground(gray);
    }
    //设置button3样式
    private void setButton3(){
        button3.setBounds(160,0,160,40);
        button3.setFont(hindfont);
        button3.setForeground(blue);
        button3.setBackground(gray);
        button3.setOpaque(true);
    }
    //还原button3样式
    private void restoreButton3(){
        button3.setFont(forefont);
        button3.setBounds(160,7,160,26);
        button3.setOpaque(false);
        button3.setForeground(gray);
        button3.setBorderPainted(false);
    }
    public static void main(String[] args){
        new ServerFrame();
    }
}
