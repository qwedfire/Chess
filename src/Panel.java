import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DragGestureEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Panel extends JPanel {
    //private BufferedImage bb=ImageIO.read(new File(""));
    private ArrayList<JLabel> jlist;
    private ArrayList<HashMap<String ,Integer>> press; //點擊座標
    private ArrayList<Icon> icons;
    private JLabel jl1;
    private JLabel jl2;
    private JLabel jl3;
    private JLabel jl4;
    private JLabel jl5;
    private JLabel jl6;
    private JLabel jl7;
    private JLabel jl8;
    private JLabel jl9;
    private JLabel jl10;
    private JLabel jl11;
    private JLabel jl12;
    private JLabel jl13;
    private JLabel jl14;
    private JLabel jl15;
    private JLabel jl16;
    private JLabel jl17;
    private JLabel jl18;
    private JLabel jl19;
    private JLabel jl20;
    private JLabel jl21;
    private JLabel jl22;
    private JLabel jl23;
    private JLabel jl24;
    private JLabel jl25;
    private JLabel jl26;
    private JLabel jl27;
    private JLabel jl28;
    private JLabel jl29;
    private JLabel jl30;
    private JLabel jl31;
    private JLabel jl32;
    private JLabel jl33; //放棋盤
    private JLabel jl34;
    private JLabel jl35;
    private Icon redBing; //兵
    private Icon redMa; //馬
    private Icon redChe; //車
    private Icon redXiang; //相
    private Icon redPao; //炮
    private Icon redShi; //士
    private Icon redShuai; //帥
    private Icon cover; //蓋牌
    private Icon blackZu; //卒
    private Icon blackMa; //馬
    private Icon blackChe; //車
    private Icon blackXiang; //象
    private Icon blackPao; //包
    private Icon blackShi; //士
    private Icon blackJiang; //將
    private Icon chessBoard; //棋盤
    private JButton surrender;

    public Panel() {
        MyListener listener=new MyListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        surrender = new JButton("投降");
        surrender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        press=new ArrayList<>();
        redBing = new ImageIcon("dir/红卒.gif");
        redShi = new ImageIcon("dir/红士.gif");
        redShuai = new ImageIcon("dir/红将.gif");
        redPao = new ImageIcon("dir/红炮.gif");
        redXiang = new ImageIcon("dir/红象.gif");
        redChe = new ImageIcon("dir/红车.GIF");
        redMa = new ImageIcon("dir/红马.gif");
        cover = new ImageIcon("dir/象棋.png");
        blackZu = new ImageIcon("dir/黑卒.gif");
        blackShi = new ImageIcon("dir/黑士.gif");
        blackJiang = new ImageIcon("dir/黑将.gif");
        blackPao = new ImageIcon("dir/黑炮.gif");
        blackXiang = new ImageIcon("dir/黑象.gif");
        blackChe = new ImageIcon("dir/黑车.gif");
        blackMa = new ImageIcon("dir/黑马.gif");
        chessBoard = new ImageIcon("dir/main.gif");
        jl1 = new JLabel(cover);
        jl2 = new JLabel(cover);
        jl3 = new JLabel(cover);
        jl4 = new JLabel(cover);
        jl5 = new JLabel(cover);
        jl6 = new JLabel(cover);
        jl7 = new JLabel(cover);
        jl8 = new JLabel(cover);
        jl9 = new JLabel(cover);
        jl10 = new JLabel(cover);
        jl11 = new JLabel(cover);
        jl12 = new JLabel(cover);
        jl13 = new JLabel(cover);
        jl14 = new JLabel(cover);
        jl15 = new JLabel(cover);
        jl16 = new JLabel(cover);
        jl17 = new JLabel(cover);
        jl18 = new JLabel(cover);
        jl19 = new JLabel(cover);
        jl20 = new JLabel(cover);
        jl21 = new JLabel(cover);
        jl22 = new JLabel(cover);
        jl23 = new JLabel(cover);
        jl24 = new JLabel(cover);
        jl25 = new JLabel(cover);
        jl26 = new JLabel(cover);
        jl27 = new JLabel(cover);
        jl28 = new JLabel(cover);
        jl29 = new JLabel(cover);
        jl30 = new JLabel(cover);
        jl31 = new JLabel(cover);
        jl32 = new JLabel(cover);
        jl33 = new JLabel(chessBoard);
        jl34 = new JLabel();
        jl35 = new JLabel();
        jlist = new ArrayList<>();  //jlist包含所有棋子(32顆) 一開始全部預設為cover圖片
        jlist.add(jl1);
        jlist.add(jl2);
        jlist.add(jl3);
        jlist.add(jl4);
        jlist.add(jl5);
        jlist.add(jl6);
        jlist.add(jl7);
        jlist.add(jl8);
        jlist.add(jl9);
        jlist.add(jl10);
        jlist.add(jl11);
        jlist.add(jl12);
        jlist.add(jl13);
        jlist.add(jl14);
        jlist.add(jl15);
        jlist.add(jl16);
        jlist.add(jl17);
        jlist.add(jl18);
        jlist.add(jl19);
        jlist.add(jl20);
        jlist.add(jl21);
        jlist.add(jl22);
        jlist.add(jl23);
        jlist.add(jl24);
        jlist.add(jl25);
        jlist.add(jl26);
        jlist.add(jl27);
        jlist.add(jl28);
        jlist.add(jl29);
        jlist.add(jl30);
        jlist.add(jl31);
        jlist.add(jl32);

        setLayout(new GridLayout(1, 1));
        add(jl33); //先放棋盤
        jl33.setLayout(new GridLayout(2, 1));
        jl33.add(jl34);
        jl33.add(jl35); //再將33分上下部分(34 35)
        jl34.setLayout(new FlowLayout()); //(34上)放入按鈕投降
        jl34.add(surrender);

        jl35.setLayout(new GridLayout(4, 8, 1, 1));
        for (JLabel jLabel : jlist) {
            jl35.add(jLabel);  //(35下)放入所有棋子
        }
    }
    private class MyListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            HashMap<String ,Integer> point=new HashMap();
            point.put("x", e.getX());point.put("y", e.getY());
            System.out.println(e.getX()+"  "+e.getY());
            press.add(point);
//            repaint(); //重畫component類別方法
        }

//        @Override
//        public void mousePressed(MouseEvent e) {
//            HashMap<String ,Integer> point=new HashMap();
//            point.put("x", e.getX());point.put("y", e.getY());
//            System.out.println(e.getX()+"  "+e.getY());
//            press.add(point);
//            repaint(); //重畫component類別方法
//        }

        @Override
        public void mouseDragged(MouseEvent e) {
            System.out.println(e.getX()+"  "+e.getY());
//            repaint();
        }
    }

    public Icon getChessBoard() {
        return chessBoard;
    }
}
