import javax.swing.*;
//1.Rule.compareB 須改

public class Main extends JFrame {
    public Main() {
        super("象棋遊戲");
        add(new Panel());
        setSize(Resource.pictures.get("棋盤").getIconWidth(), Resource.pictures.get("棋盤").getIconHeight());
        setLocation(700, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Reg();
    }
}