import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

public class Login extends JFrame {
    private final static String USER="root";//整理資料
    private final static String PASSWORD="root";
    private final static String URL="jdbc:mysql://localhost:3306/iii";
    private final static String SQL_LOGIN="select * from member where account=? ";
    private Main m;
    private JPasswordField jp;
    private JTextArea jt;
    private JButton jb;
    public Login(){
        super("用戶登入");
        setLayout(new GridLayout(1,1));
        JLabel all=new JLabel();
        add(all);
        JLabel jl1=new JLabel("用戶");
        JLabel jl2=new JLabel("密碼");
        JLabel jl3=new JLabel();
        JLabel jl4=new JLabel();
        JTextArea jt1=new JTextArea();
        JTextArea jt2=new JTextArea();
        jp=new JPasswordField(5);
        jb=new JButton("登入");
        jt=new JTextArea();
        all.setLayout(new GridLayout(3,1));
        all.add(jl3);
        all.add(jl4);
        all.add(jb);
        jl3.setLayout(new GridLayout(1,2));
        jl3.add(jl1);jl3.add(jt);
        jl4.setLayout(new GridLayout(1,2));
        jl4.add(jl2);jl4.add(jp);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(jt.getText(),jp.getText());
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(300,150);
        setLocation(800,400);
    }
    public void login(String username,String password){
        System.out.println("開始處理用戶登入....");
        if(username==null||password==null){
            System.out.println("用戶或密碼不能為空");
            return;
        }
        Properties prop=new Properties();
        prop.put("user", USER);prop.put("password",PASSWORD);
        try (Connection conn= DriverManager.getConnection(URL,prop);
             PreparedStatement pstmt=conn.prepareStatement(SQL_LOGIN)){
            pstmt.setString(1, jt.getText());
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()) { //唯一一筆
                System.out.println(3);
                if(BCrypt.checkpw(jp.getText(),rs.getString("password"))) {
                    //login
                    User user=new User(rs.getString("account"),rs.getString("password"),rs.getString("nickname"),rs.getString("age"));
                    System.out.printf("welcome,%s/%s\n",user.getNickname(),user.getUsername());
                    m=new Main();
                    //change password process
//                            System.out.println("change password(newpassword or no):");
//                            String newPassword=sc.next();
//                            if(newPassword.length()>=6) {
//                                PreparedStatement pstmt2=conn.prepareStatement(SQL_CHPASSWORD);
//                                pstmt2.setString(1, BCrypt.hashpw(newPassword,BCrypt.gensalt()));
//                                if(pstmt2.executeUpdate()>0) {
//                                    //SUCCESS
//                                    System.out.println("change password success!");
//                                }else {
//                                    //更換失敗
//                                    System.out.println("change password failure!");
//                                }
//                            }else {
//                                //不想換
//                                System.out.println("no change~");
//                            }
                }else {
                    //帳號不存在
                    System.out.println("login failure(2)");
                }
            }else {
                System.out.println("帳號或密碼不存在");
            }
        }catch(Exception ee) {
            System.out.println(ee);
        }
    }
}
