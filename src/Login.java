import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Login extends JFrame {
    Main m;
    JPasswordField jp;
    JTextArea jt;
    JButton jb;
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
//    public static void main(String[] args) {
//        new Login();
//    }
    public void login(String username,String password){
        System.out.println("開始處理用戶登入....");
        System.out.println(username + "," + password);
        if(username==null||password==null){
            System.out.println("用戶或密碼不能為空");
            return;
        }
        //2
        File userFile = new File("./user",username+".obj");
        if(userFile.exists()){//用户名输入正确
            try (
                    FileInputStream fis = new FileInputStream(userFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
            ){
                //读取该注册用户信息
                User user = (User)ois.readObject();
                //如果用戶提交的密碼和註冊用戶紀錄的密碼相同，則允許登入
                if(user.getPassword().equals(password)){//密码正确
                    //登入成功
                    System.out.println("登入成功");
                    setVisible(false);
                    m=new Main();
                    return;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        //如果程序走到這裡，用戶名或密碼有誤
        System.out.println("輸入用戶或密碼有誤");
        System.out.println("處理用戶登入完畢!!!");
    }
}
