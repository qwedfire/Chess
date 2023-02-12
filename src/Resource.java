import javax.swing.*;
import java.util.HashMap;

public class Resource {
    public static HashMap<String, Icon> pictures = new HashMap<>() {
        {
            put("兵", new ImageIcon("dir/红卒.gif"));
            put("傌", new ImageIcon("dir/红马.gif"));
            put("炮", new ImageIcon("dir/红炮.gif"));
            put("俥", new ImageIcon("dir/红车.GIF"));
            put("相", new ImageIcon("dir/红象.gif"));
            put("仕", new ImageIcon("dir/红士.gif"));
            put("帥", new ImageIcon("dir/红将.gif"));
            put("卒", new ImageIcon("dir/黑卒.gif"));
            put("馬", new ImageIcon("dir/黑马.gif"));
            put("包", new ImageIcon("dir/黑炮.gif"));
            put("車", new ImageIcon("dir/黑车.gif"));
            put("象", new ImageIcon("dir/黑象.gif"));
            put("士", new ImageIcon("dir/黑士.gif"));
            put("將", new ImageIcon("dir/黑将.gif"));
            put("背面", new ImageIcon("dir/象棋.png"));
            put("棋盤", new ImageIcon("dir/main.gif"));
            put("標記",new ImageIcon("dir/select.gif"));
            put("O", new ImageIcon("dir/象棋.png"));
        }
    };
}
