package client;

import server.ServerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

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
    private JTextArea chattext,inputtext;

    private DataOutputStream out;
    private Socket client;
    private Color blue = new Color(9,164,220);
    private Color gray = new Color(201,202,203);
    public ClientFrame(){
        createFrame();
        //
        try {
            client = new Socket("127.0.0.1",8888);
            new Thread(new ClientReceiveThread(client,chattext)).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"服务器没有启动!");
            System.exit(0);
        }
        //
        addEvent();
    }
    private void addEvent(){
        //注销按钮
        logoutbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int t = JOptionPane.showConfirmDialog(null,"您是否确认退出登陆?","确认",JOptionPane.OK_CANCEL_OPTION);
                if(t == YES_OPTION){
                    //logoutinfo();
                    closeAll();
                    try {
                        client.close();
                    } catch (IOException e) {

                    }
                    chattext.setText("\t\t您已下线");
                }
            }
        });
        //退出按钮
        exitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //监听下线消息
                System.exit(0);
            }
        });
        //发送按钮
        sendbutton.addActionListener(new ActionListener() {
            String str = null;
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    out = new DataOutputStream(client.getOutputStream());
                } catch (IOException e) {
                    closeAll();
                }
                str = inputtext.getText();
                try {
                    out.writeUTF(str);
                    out.flush();
                } catch (IOException e) {
                    closeAll();
                }
                chattext.append("我:\n");
                chattext.append("    "+str+"\n");
                inputtext.setText("");
            }
        });
    }
//    //发送离线消息
//    private void logoutinfo(){
//        try {
//            out.writeUTF("logout");
//        } catch (IOException e) {
//            closeAll();
//        }
//    }
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
        setLocationRelativeTo(null);
        //添加panel
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
        linklabel = new JLabel("联系人",JLabel.CENTER);
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
        //设置联系人滚动框
        linkscroll = new JScrollPane();
        linkscroll.setOpaque(false);
        linkscroll.getViewport().setOpaque(false);
        linkscroll.setBounds(0,0,240,550);
        linkscroll.setBorder(BorderFactory.createEmptyBorder());
        //设置linkpanel
        linkpanel = new JPanel();
        linkpanel.setBackground(new Color(250,250,250));
        linkpanel.setBounds(80,50,240,550);
        linkpanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
        //箱式布局设置
        BoxLayout boxlayout = new BoxLayout(linkpanel,BoxLayout.Y_AXIS);
        linkpanel.setLayout(boxlayout);
        linkpanel.add(linkscroll);
        //添加组件
        return linkpanel;
    }
    //添加联系人方法
    public void addLinkman(String name,String account,String password){

    }
    //创建chatpanel
    private JPanel createChatpanel(){
        namelabel = new JLabel();
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
    public static void main(String[] args){
        new ClientFrame();
    }
}
