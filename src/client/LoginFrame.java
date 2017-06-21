package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import static javax.swing.JOptionPane.*;

/**
 * Created by Dr.Wen on 2017/6/11.
 */
public class LoginFrame extends JFrame {
    private JPanel northpanel,westpanel,centerpanel;
    private JLabel headimg;
    private JTextField name;
    private JPasswordField pwd;
    private JButton btnlogin;

    private Connection con = null;

    private LoginFrame(){
        createFrame();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ChatSoftware?characterEncoding=utf8","root","wdywdy318");
        } catch (ClassNotFoundException e) {
            System.exit(0);
        } catch (SQLException e) {
            System.exit(0);
        }
        addEvent();
    }
    //添加事件
    private void addEvent(){
        btnlogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent){
                String nick = null;
                String namestr = name.getText();
                String pwdstr = String.valueOf(pwd.getPassword());

                PreparedStatement ps = null;
                ResultSet rs = null;
                String existaccount = null;
                String existpassword = null;
                String existnick = null;
                Boolean exist = false;
                //获取数据库信息
                try {
                    String sql_select = "select * from AccountInfo";
                    ps = con.prepareStatement(sql_select);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        existaccount = rs.getString(1);
                        if (namestr.equals(existaccount)){
                            exist = true;
                            existpassword = rs.getString(2);
                            existnick = rs.getString(3);
                            break;
                        }
                    }
                } catch (SQLException e) {

                }finally{
                    try {
                        ps.close();
                    } catch (SQLException e) {}
                    try{
                        rs.close();
                    } catch (SQLException e) {}
                }
                if (namestr.equals("") || pwdstr.equals("") || namestr.equals("QQ号/手机号/邮箱") || pwdstr.equals("*********")){
                    JOptionPane.showMessageDialog(null,"请输入账号和密码");
                }else{
                    if (!exist) {   //账号不存在
                        int t = JOptionPane.showConfirmDialog(null,"账号不存在,是否注册新用户?","",JOptionPane.OK_CANCEL_OPTION);
                        if (t != YES_OPTION){   //不注册新用户
                            name.setText("QQ号/手机号/邮箱");
                            pwd.setText("*********");
                        }else {   //注册新用户
                            nick = JOptionPane.showInputDialog("请输入您的昵称:");
                            if (nick == null){
                                name.setText("QQ号/手机号/邮箱");
                                pwd.setText("*********");
                            }else if (nick.equals("") ){   //昵称为空
                                JOptionPane.showMessageDialog(null,"注册失败,昵称不能为空");
                                name.setText("QQ号/手机号/邮箱");
                                pwd.setText("*********");
                            }else{   //昵称非空
                                //数据库插入新用户
                                String sql = "insert into AccountInfo() values(?,?,?)";
                                try {
                                    ps = con.prepareStatement(sql);
                                    ps.setObject(1,namestr);
                                    ps.setObject(2,pwdstr);
                                    ps.setObject(3,nick);
                                    ps.executeUpdate();
                                    JOptionPane.showMessageDialog(null,"注册成功");
                                    new ClientFrame(namestr,nick);
                                    dispose();
                                } catch (SQLException e) {
                                    System.exit(0);
                                }finally{
                                    try{
                                        ps.close();
                                    } catch (SQLException e) {}
                                    try {
                                        con.close();
                                    } catch (SQLException e) {}
                                }
                            }
                        }
                    }else{   //账号存在
                        if (!pwdstr.equals(existpassword)){   //密码不正确
                            JOptionPane.showMessageDialog(null,"密码不正确,请重新输入");
                            pwd.setText("");
                            pwd.requestFocus();
                        }else{   //密码正确
                            JOptionPane.showMessageDialog(null,"登录成功");
                            new ClientFrame(existaccount,existnick);
                            dispose();
                        }
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
        String info2 = "*********";
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
