import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Reg extends JFrame {
    Login log;
    JPasswordField jp;
    JTextArea jt;
    JButton jb;
    JButton jb1;
    public Reg(){
        super("用戶註冊");
        setLayout(new GridLayout(1,1));
        JLabel all=new JLabel();
        add(all);
        JLabel jl1=new JLabel("用戶");
        JLabel jl2=new JLabel("密碼");
        JLabel jl3=new JLabel();
        JLabel jl4=new JLabel();
        JLabel jl5=new JLabel("暱稱");
        JLabel jl6=new JLabel("年齡");
        JLabel jl7=new JLabel();
        JLabel jl8=new JLabel();
        JTextArea jt1=new JTextArea();
        JTextArea jt2=new JTextArea();
        jp=new JPasswordField(5);
        jb=new JButton("註冊");
        jb1=new JButton("已存在用戶");
        jt=new JTextArea();
        all.setLayout(new GridLayout(6,1));
        all.add(jl3);
        all.add(jl4);
        all.add(jl7);
        all.add(jl8);
        all.add(jb);
        all.add(jb1);
        jl3.setLayout(new GridLayout(1,2));
        jl3.add(jl1);jl3.add(jt);
        jl4.setLayout(new GridLayout(1,2));
        jl4.add(jl2);jl4.add(jp);
        jl7.setLayout(new GridLayout(1,2));
        jl7.add(jl5);jl7.add(jt1);
        jl8.setLayout(new GridLayout(1,2));
        jl8.add(jl6);jl8.add(jt2);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg(jt.getText(),jp.getText(),jt1.getText(),jt2.getText());
            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                log=new Login();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,250);
        setLocation(800,400);
    }
    public static void main(String[] args) {
        Reg t=new Reg();
    }
    public void reg(String username,String password,String nickname,String age){
        if(username==null||password==null||nickname==null||
                age==null||!age.matches("[0-9]+")){
            //如果進入當前判斷，說明錄入信息有問題，不允許繼續向下執行程序了
            System.out.println("輸入欄不能為空");
            return;
        }
        //2.將用戶信息封裝到一個User對象，並序列化到文件中
        User user = new User(username,password,nickname,Integer.parseInt(age));
        //將用戶信息以用戶名.obj的形式存儲到users目錄中
        File userFile = new File("./user", username + ".obj");
        //判斷當前文件是否已存在，如果存在，可以判斷是重複用戶
        if(userFile.exists()){
            System.out.println("用戶以存在");
            setVisible(false);
            return;
        }
        try (
                FileOutputStream fos = new FileOutputStream(userFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            //将user对象序列化到userFile这个File对象所绑定的文件
            oos.writeObject(user);
            //註冊成功
            System.out.println("註冊成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setVisible(false);
        log=new Login();
        System.out.println("處理用戶註冊完畢!!!");
    }
}
