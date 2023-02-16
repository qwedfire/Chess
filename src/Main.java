import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends JFrame {
    private static Panel panel;
    public Main()   {
        super("象棋遊戲");
//        Resource.loopPlay.loopPlay();
        panel=new Panel();
        add(panel);
        setSize(Resource.pictures.get("棋盤").getIconWidth(), Resource.pictures.get("棋盤").getIconHeight()+43);
        setLocation(700, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
//        new Reg();
        new Main();
        String keyword=sc.nextLine();
        if(keyword.equals("1")){
            getPanel().save(new FileOutputStream("save/1.game"));
        }
        if(keyword.equals("2")){
            getPanel().load(new FileInputStream("save/1.game"));
        }
    }
    public static Panel getPanel(){
        return panel;
    }

}