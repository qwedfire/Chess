import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class Reg extends JFrame {
    private static Connection conn;
    private final static String USER = "root";//整理資料
    private final static String PASSWORD = "root";
    private final static String URL = "jdbc:mysql://localhost:3306/iii";
    private final static String SQL_CHECK = "select count(*) count from chessmember where account=? ";
    private final static String SQL_INSERT = "insert into chessmember(account,password,nickname,age)values(?,?,?,?) ";
    private JPasswordField jp;
    private JTextArea jt;
    private JButton jb;
    private JButton jb1;

    public Reg() {
        super("用戶註冊");
        setLayout(new GridLayout(1, 1));
        JLabel all = new JLabel();
        add(all);
        JLabel jl1 = new JLabel("用戶");
        JLabel jl2 = new JLabel("密碼");
        JLabel jl3 = new JLabel();
        JLabel jl4 = new JLabel();
        JLabel jl5 = new JLabel("暱稱");
        JLabel jl6 = new JLabel("年齡");
        JLabel jl7 = new JLabel();
        JLabel jl8 = new JLabel();
        JTextArea jt1 = new JTextArea();
        JTextArea jt2 = new JTextArea();
        jp = new JPasswordField(5);
        jb = new JButton("註冊");
        jb1 = new JButton("已存在用戶");
        jt = new JTextArea();
        all.setLayout(new GridLayout(6, 1));
        all.add(jl3);
        all.add(jl4);
        all.add(jl7);
        all.add(jl8);
        all.add(jb);
        all.add(jb1);
        jl3.setLayout(new GridLayout(1, 2));
        jl3.add(jl1);
        jl3.add(jt);
        jl4.setLayout(new GridLayout(1, 2));
        jl4.add(jl2);
        jl4.add(jp);
        jl7.setLayout(new GridLayout(1, 2));
        jl7.add(jl5);
        jl7.add(jt1);
        jl8.setLayout(new GridLayout(1, 2));
        jl8.add(jl6);
        jl8.add(jt2);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Properties prop = new Properties();
                prop.put("user", USER);
                prop.put("password", PASSWORD);
                try {
                    conn = DriverManager.getConnection(URL, prop);
                    if (jt.getText() .equals("") || jp.getText() .equals("") || jt1.getText() .equals("") || jt2.getText() .equals("")) {
                        JOptionPane.showMessageDialog(null, "有欄位尚未輸入");
                    } else if (checkAccount(jt.getText())) {
                        if (addMember(jt.getText(), jp.getText(), jt1.getText(), jt2.getText())) {
                            System.out.println("Register success");
                            setVisible(false);
                            new Login();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "用戶已存在");
                        System.out.println(jt.getText()+"1");
                        //相同帳號
                        System.out.println("acount exist");
                    }
                } catch (Exception ee) {
                    System.out.println(ee);
                }
            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Login();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500, 250);
        setLocation(800, 400);
    }

    /**
     * 檢查帳號是否存在
     *
     * @param account
     * @return
     * @throws Exception
     */
    private static boolean checkAccount(String account) throws Exception {
        boolean ret;
        PreparedStatement pstmt = conn.prepareStatement(SQL_CHECK);
        pstmt.setString(1, account);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        ret = rs.getInt("count") == 0;
        pstmt.close(); //pstmt為區域變數關閉可以讓記憶體釋放
        return ret;
    }

    /**
     * 創建帳號
     *
     * @return
     */
    private static boolean addMember(String account, String password, String nickname, String age) throws Exception {
        boolean isOK = false;
        PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT);
        pstmt.setString(1, account);
        pstmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
        pstmt.setString(3, nickname);
        pstmt.setString(4, age);
        isOK = pstmt.executeUpdate() != 0;
        pstmt.close();
        return isOK; //等於0沒創建成功
    }
}
