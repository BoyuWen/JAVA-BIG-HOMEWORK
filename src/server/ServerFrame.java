package server;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dr.Wen on 2017/6/11.
 */
public class ServerFrame extends JFrame implements ActionListener {
    private JButton button1,button2,button3,startbutton;
    private JTextArea text1,text2,text3;
    private JScrollPane scroll1,scroll2,scroll3;
    private JPanel buttonpanel,tabpanel,startpanel;
    CardLayout card = new CardLayout();

    public ServerFrame(){
        createFrame();
    }
    //生成Frame方法
    public void createFrame(){
        //创建三个button
        button1 = new JButton("状态");
        button1.setBounds(10,0,110,50);
        button1.addActionListener(this);
        button2 = new JButton("详情");
        button2.setBounds(120,0,110,50);
        button2.addActionListener(this);
        button3 = new JButton("用户");
        button3.setBounds(230,0,110,50);
        button3.addActionListener(this);
        //设置buttonpanel
        buttonpanel = new JPanel(null);
        buttonpanel.setLayout(null);
        buttonpanel.setBounds(0,0,350,50);
        buttonpanel.add(button1);
        buttonpanel.add(button2);
        buttonpanel.add(button3);
        //创建三个文本框
        text1 = new JTextArea();
        text1.setOpaque(false);
        text1.setLineWrap(true);
        text1.setEditable(false);
        text2 = new JTextArea();
        text2.setOpaque(false);
        text2.setLineWrap(true);
        text2.setEditable(false);
        text3 = new JTextArea();
        text3.setOpaque(false);
        text3.setLineWrap(true);
        text3.setEditable(false);
        //创建三个滚动框
        scroll1 = new JScrollPane(text1);  //第一个
        scroll1.setOpaque(false);
        scroll1.getViewport().setOpaque(false);
        scroll1.setBorder(BorderFactory.createEmptyBorder());
        scroll2 = new JScrollPane(text2);  //第二个
        scroll2.setOpaque(false);
        scroll2.getViewport().setOpaque(false);
        scroll2.setBorder(BorderFactory.createEmptyBorder());
        scroll3 = new JScrollPane(text3);  //第三个
        scroll3.setOpaque(false);
        scroll3.getViewport().setOpaque(false);
        scroll3.setBorder(BorderFactory.createEmptyBorder());
        //设置tabpanel
        tabpanel = new JPanel();
        tabpanel.setLayout(card);     //这里一定要用已经创建的card！要和在事件中的card相同！不可以用 new CardLayou()!
        tabpanel.setBounds(10,55,330,415);
        //tabpanel.setBorder(BorderFactory.createLineBorder(new Color(201,202,203)));
        tabpanel.setBorder(BorderFactory.createEmptyBorder());
        tabpanel.add(scroll1,"状态");
        tabpanel.add(scroll2,"详情");
        tabpanel.add(scroll3,"用户");
        //设置开始按钮
        startbutton = new JButton("运行");
        startbutton.setBounds(120,5,120,40);
        startbutton.setForeground(new Color(9,164,220));
        //设置startpanel
        startpanel = new JPanel();
        startpanel.setLayout(null);
        startpanel.setBounds(0,480,350,50);
        startpanel.setBackground(new Color(9,164,220));
        startpanel.add(startbutton);
        //startpanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0,new Color(201,202,203)));
        //设置Frame
        setLayout(null);
        setTitle("服务器端");
        setSize(350,550);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
        add(buttonpanel);
        add(tabpanel);
        add(startpanel);
    }
    //
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == button1){
            setButton1();
            restoreButton2();
            restoreButton3();
            card.first(tabpanel);
            text1.setText("状态");
        }else if(e.getSource() == button2){
            setButton2();
            restoreButton1();
            restoreButton3();
            card.show(tabpanel,"详情");
            text2.setText("详情");
        }else if(e.getSource() == button3){
            setButton3();
            restoreButton1();
            restoreButton2();
            card.last(tabpanel);
            text3.setText("用户");
        }
    }
    //设置button1样式
    public void setButton1(){
        button1.setBackground(new Color(9,164,220));
        button1.setForeground(Color.BLUE);
        button1.setBorder(BorderFactory.createLineBorder(new Color(9,164,220)));
    }
    //还原button1样式
    public void restoreButton1(){

    }
    //设置button2样式
    public void setButton2(){
        button1.setBackground(new Color(9,164,220));
        button1.setForeground(Color.BLUE);
        button1.setBorder(BorderFactory.createLineBorder(new Color(9,164,220)));
    }
    //还原button2样式
    public void restoreButton2(){

    }
    //设置button3样式
    public void setButton3(){
        button1.setBackground(new Color(9,164,220));
        button1.setForeground(Color.BLUE);
        button1.setBorder(BorderFactory.createLineBorder(new Color(9,164,220)));
    }
    //还原button3样式
    public void restoreButton3(){

    }
    public static void main(String[] args){
        new ServerFrame();
    }
}
