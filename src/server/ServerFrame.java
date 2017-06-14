package server;

import javax.swing.*;
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
    Font forefont = new Font(null,0,15);
    Font hindfont = new Font(null,1,17);
    Color blue = new Color(9,164,220);
    Color gray = new Color(231,231,231);

    public ServerFrame(){
        createFrame();
    }
    //生成Frame方法
    public void createFrame(){
        add(createButtonpanel());
        add(createTabpanel());
        add(createStartpanel());
        //设置Frame
        setLayout(null);
        setTitle("服务器端");
        setSize(350,550);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
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
    //创建buttonpanel
    public JPanel createButtonpanel(){
        //创建三个button
        button1 = new JButton("状态");
        restoreButton1();
        button1.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
        button1.addActionListener(this);
        button2 = new JButton("详情");
        restoreButton2();
        button2.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
        button2.addActionListener(this);
        button3 = new JButton("用户");
        restoreButton3();
        button3.addActionListener(this);
        //设置buttonpanel
        buttonpanel = new JPanel(null);
        buttonpanel.setLayout(null);
        buttonpanel.setBounds(0,0,350,40);
        buttonpanel.setBackground(blue);
        buttonpanel.add(button1);
        buttonpanel.add(button2);
        buttonpanel.add(button3);
        return buttonpanel;
    }
    //创建tabpanel
    public JPanel createTabpanel(){
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
        tabpanel.setBounds(10,45,330,415);
        tabpanel.setBorder(BorderFactory.createEmptyBorder());
        tabpanel.add(scroll1,"状态");
        tabpanel.add(scroll2,"详情");
        tabpanel.add(scroll3,"用户");
        return tabpanel;
    }
    //创建startpanel
    public JPanel createStartpanel(){
        //设置开始按钮
        startbutton = new JButton("运行");
        startbutton.setFont(forefont);
        startbutton.setBounds(120,5,120,40);
        startbutton.setForeground(blue);
        //设置startpanel
        startpanel = new JPanel();
        startpanel.setLayout(null);
        startpanel.setBounds(0,480,350,50);
        startpanel.setBackground(blue);
        startpanel.add(startbutton);
        return startpanel;
    }
    //设置button1样式
    public void setButton1(){
        button2.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
        button1.setBounds(0,0,116,40);
        button1.setFont(hindfont);
        button1.setForeground(blue);
        button1.setBackground(gray);
        button1.setOpaque(true);
    }
    //还原button1样式
    public void restoreButton1(){
        button1.setFont(forefont);
        button1.setBounds(0,7,116,26);
        button1.setOpaque(false);
        button1.setForeground(gray);
    }
    //设置button2样式
    public void setButton2(){
        button1.setBorder(BorderFactory.createMatteBorder(0,0,0,1,blue));
        button2.setBounds(116,0,118,40);
        button2.setFont(hindfont);
        button2.setForeground(blue);
        button2.setBackground(gray);
        button2.setOpaque(true);
        button2.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
    }
    //还原button2样式
    public void restoreButton2(){
        button2.setFont(forefont);
        button2.setBounds(116,7,118,26);
        button2.setOpaque(false);
        button2.setForeground(gray);
        //button2.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
    }
    //设置button3样式
    public void setButton3(){
        button1.setBorder(BorderFactory.createMatteBorder(0,0,0,1,gray));
        button2.setBorder(BorderFactory.createMatteBorder(0,0,0,1,blue));
        button3.setBounds(234,0,116,40);
        button3.setFont(hindfont);
        button3.setForeground(blue);
        button3.setBackground(gray);
        button3.setOpaque(true);
    }
    //还原button3样式
    public void restoreButton3(){
        button3.setFont(forefont);
        button3.setBounds(234,7,116,26);
        button3.setOpaque(false);
        button3.setForeground(gray);
        button3.setBorderPainted(false);
    }
    public static void main(String[] args){
        new ServerFrame();
    }
}
