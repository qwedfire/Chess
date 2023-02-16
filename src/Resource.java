import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Resource {
    public static final MP3Player jjPlayer,loopPlay;
    static {
        try {
//            InputStream in=new FileInputStream("dir/maple.mp3");
//            loopPlay=new MP3Player(in);
            loopPlay=new MP3Player(new FileInputStream("dir/maple.mp3"));
            jjPlayer = new MP3Player(new FileInputStream("dir/ding.mp3"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

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
            put("標記", new ImageIcon("dir/select.gif"));
            put("O", new ImageIcon("dir/象棋.png"));
        }
    };
    //給棋盤賦值使用
    public static String[] chessValue = {"兵", "兵", "兵", "兵", "兵", "卒", "卒", "卒", "卒", "卒",
            "傌", "傌", "馬", "馬", "俥", "俥", "車", "車", "炮", "炮", "包",
            "包", "相", "相", "象", "象", "仕", "仕", "士", "士", "帥", "將"};
}
