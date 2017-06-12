package client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dr.Wen on 2017/6/11.
 */
public class ClientFrame extends JFrame {
    public ClientFrame(){
        createFrame();
    }
    //创建Frame函数
    void createFrame(){

        add(createSelfpanel());
        add(createLinkpanel());
        add(createChatpanel());
        add(createTextpanel());
        //设置Frame
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(3);
        setSize(800,550);
        setLocationRelativeTo(null);
        //添加panel
    }
    //创建selfpanel
    public JPanel createSelfpanel(){
        //头像Label
        ImageIcon headimg = new ImageIcon("image/头像.jpg");
        headimg.setImage(headimg.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
        JLabel imglabel = new JLabel(headimg);
        imglabel.setBounds(10,10,50,50);
        //注销按钮
        JButton logoutbutton = new JButton("注销");
        logoutbutton.setBounds(5,450,60,30);
        logoutbutton.setForeground(new Color(9,164,220));
        //退出按钮
        JButton exitbutton = new JButton("退出");
        exitbutton.setBounds(5,480,60,30);
        exitbutton.setForeground(new Color(9,164,220));
        //设置selfpanel
        JPanel selfpanel = new JPanel();
        selfpanel.setLayout(null);
        selfpanel.setBounds(0,0,70,550);
        selfpanel.setBackground(new Color(9,164,220));
        //添加组件
        selfpanel.add(imglabel);
        selfpanel.add(logoutbutton);
        selfpanel.add(exitbutton);
        return selfpanel;
    }
    //创建linkpanel
    public JPanel createLinkpanel(){
        JLabel linklabel = new JLabel("联系人",JLabel.CENTER);
        linklabel.setFont(new Font(null,Font.PLAIN,20));
        linklabel.setBounds(10,0,230,50);
        //设置linkpanel
        JPanel linkpanel = new JPanel();
        linkpanel.setLayout(null);
        linkpanel.setBounds(70,0,250,550);
        linkpanel.setBorder(BorderFactory.createMatteBorder(0,0,0,1,new Color(201,202,203)));
        //添加组件
        linkpanel.add(linklabel);
        return linkpanel;
    }
    //创建chatpanel
    public JPanel createChatpanel(){
        JLabel namelabel = new JLabel();
        namelabel.setBounds(0,0,480,50);
        namelabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(201,202,203)));
        //设置chatpanel
        JPanel chatpanel = new JPanel();
        chatpanel.setLayout(null);
        chatpanel.setBounds(320,0,480,350);
        chatpanel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(201,202,203)));
        //添加组件
        chatpanel.add(namelabel);
        return chatpanel;
    }
    //创建textpanel
    public JPanel createTextpanel(){
        //发送按钮
        JButton sendbutton = new JButton("发送");
        sendbutton.setBounds(410,10,70,160);
        sendbutton.setBorder(BorderFactory.createMatteBorder(0,1,0,0,new Color(201,202,203)));
        //输入框
        JTextArea inputarea = new JTextArea();
        inputarea.setLineWrap(true); //设置自动换行
        inputarea.setOpaque(false);
        //滚动框
        JScrollPane inputscroll = new JScrollPane(inputarea);
        inputscroll.setBounds(15,15,395,150);
        inputscroll.setBorder(BorderFactory.createEmptyBorder());
        inputscroll.setOpaque(false);
        inputscroll.getViewport().setOpaque(false); //设置文本框透明，三处都要有
        //设置textpanel
        JPanel textpanel = new JPanel();
        textpanel.setLayout(null);
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
