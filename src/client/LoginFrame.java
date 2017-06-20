package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Dr.Wen on 2017/6/11.
 */
public class LoginFrame extends JFrame {
    private JPanel northpanel,westpanel,centerpanel;
    private JLabel headimg;
    private JTextField name;
    private JPasswordField pwd;
    private JButton btnlogin;

    private LoginFrame(){
        createFrame();
        addEvent();
    }
    //添加事件
    private void addEvent(){
        btnlogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String namestr = name.getText();
                String pwdstr = pwd.getPassword().toString().trim();
                if (namestr.equals("") || pwdstr.equals("")){
                    JOptionPane.showMessageDialog(null,"请输入账号和密码");
                }else{
                    if (!namestr.equals("100001")) {//账号不存在
                        JOptionPane.showMessageDialog(null,"账号不存在,请重新输入");

                    }
                }
            }
        });
    }
    //创建Frame
    private void createFrame(){
        setLayout(new BorderLayout());
        add(setNorthpanel(),BorderLayout.NORTH);
        add(setWestpanel(),BorderLayout.WEST);
        add(setCenterpanel(),BorderLayout.CENTER);
        setSize(395,310);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
    }
    //生成顶部面板
    private JPanel setNorthpanel(){
        northpanel = new NorthPanel();
        northpanel.setPreferredSize(new Dimension(395,162));
        return northpanel;
    }
    //生成左侧面板
    private JPanel setWestpanel() {
        westpanel = new JPanel();
        westpanel.setLayout(null);
        ImageIcon img = new ImageIcon("image/头像.jpg");
        img.setImage(img.getImage().getScaledInstance(105,111,Image.SCALE_DEFAULT)); //使图片跟随label大小
        headimg = new JLabel(img);
        headimg.setBounds(40,8,105,111);
        westpanel.setPreferredSize(new Dimension(160,0));
        westpanel.setOpaque(false);
        westpanel.add(headimg);
        return westpanel;
    }
    //生成中部面板
    private JPanel setCenterpanel(){
        centerpanel = new JPanel();
        centerpanel.setFocusable(true); //使焦点不默认出现在文本框上
        centerpanel.setLayout(null);
        //文本密码框
        String info1 = "QQ号/手机号/邮箱";
        String info2 = "********";
        name = new JTextField();
        pwd = new JPasswordField();
        name.setText(info1);
        name.setForeground(Color.GRAY);
        pwd.setText(info2);
        pwd.setForeground(Color.GRAY);
        name.setBounds(0,5,190,37);
        pwd.setBounds(0,37,190,37);

        btnlogin = new JButton("登陆");
        btnlogin.setBounds(4,79,182,40);
        btnlogin.setBackground(new Color(9,164,220));
        btnlogin.setForeground(Color.WHITE);
        btnlogin.setOpaque(true);
        btnlogin.setBorderPainted(false);
        //设置监听器
        name.addFocusListener(new myFocusListener(info1,name));
        pwd.addFocusListener(new myFocusListener(info2,pwd));
        centerpanel.add(name);
        centerpanel.add(pwd);
        centerpanel.add(btnlogin);
        return centerpanel;
    }
    //设置文本密码框监听器
    class myFocusListener implements FocusListener {
        String str;
        JTextField text;
        private myFocusListener(String str,JTextField text){
            this.str = str;
            this.text = text;
        }
        public void focusGained(FocusEvent e){
            String s = text.getText();
            if (s.equals(str)){
                text.setText("");
            }
        }
        public void focusLost(FocusEvent e){
            String s = text.getText();
            if (s.equals("")){
                text.setText(str);
                text.setForeground(Color.GRAY);
            }
        }
    }
    //NorthPanel内部类
    class NorthPanel extends JPanel{
        private NorthPanel(){}
        public void paintComponent(Graphics g){
            Image bgimg = new ImageIcon("image/loginbackground.jpg").getImage();
            g.drawImage(bgimg,0,0,null);
        }
    }
    public static void main(String[] args){
        new LoginFrame();
    }
}
