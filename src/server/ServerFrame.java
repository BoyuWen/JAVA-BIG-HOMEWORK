package server;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dr.Wen on 2017/6/11.
 */
public class ServerFrame extends JFrame{
    public ServerFrame(){
        createFrame();
    }
    //生成Frame方法
    public void createFrame(){
        //创建三个文本框
        JTextArea text1 = new JTextArea();
        text1.setOpaque(false);
        text1.setLineWrap(true);
        text1.setEditable(false);
        JTextArea text2 = new JTextArea();
        text2.setOpaque(false);
        text2.setLineWrap(true);
        text2.setEditable(false);
        JTextArea text3 = new JTextArea();
        text3.setOpaque(false);
        text3.setLineWrap(true);
        text3.setEditable(false);
        //创建三个滚动框
        JScrollPane scroll1 = new JScrollPane(text1);
        scroll1.setBounds(0,50,340,430);
        scroll1.setOpaque(false);
        scroll1.getViewport().setOpaque(false);
        JScrollPane scroll2 = new JScrollPane(text2);
        scroll2.setBounds(0,50,340,430);
        scroll2.setOpaque(false);
        scroll2.getViewport().setOpaque(false);
        JScrollPane scroll3 = new JScrollPane(text3);
        scroll3.setBounds(0,50,340,430);
        scroll3.setOpaque(false);
        scroll3.getViewport().setOpaque(false);
//        创建按钮
//        JButton button1 = new JButton("状态");
//        JButton button2 = new JButton("运行详情");
//        JButton button3 = new JButton("用户");
        //设置tabbedpane
        JTabbedPane tabbed = new JTabbedPane(JTabbedPane.TOP);
        tabbed.add("状态",scroll1);
        tabbed.add("详情",scroll2);
        tabbed.add("用户",scroll3);
        //设置tabpanel
        JPanel tabpanel = new JPanel();
        tabpanel.setLayout(null);
        setBounds(0,0,360,500);
        tabpanel.add(tabbed);
        //设置buttonpanel
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(null);
        buttonpanel.setBounds(0,500,360,50);
        buttonpanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        //设置Frame
        JFrame frame = new JFrame();
        setLayout(null);
        setSize(360,550);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
        add(tabpanel);
        add(buttonpanel);
    }
    //
    //
    //
    //
    //
    //
    //
    public static void main(String[] args){
        new ServerFrame();
    }
}
