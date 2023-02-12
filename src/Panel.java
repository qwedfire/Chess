import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Panel extends JPanel {
    private JLabel player;
    private JLabel boardLabel;
    private ChessBoard board;
    private ArrayList<BoardItem> items;  //所有棋的座標、圖片

    private boolean haveSelect = false; //判斷有沒有挑選棋子移動
    private ArrayList<int[]> points = new ArrayList<>(); //紀錄點座標
    private MyListener listener = new MyListener();

    public Panel() {
        board = new ChessBoard();
        board.newGame();
        player=new JLabel("   Player1");
        addMouseListener(listener);
        boardLabel = new JLabel(Resource.pictures.get("棋盤"));
        items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board.getChessboard()[i][j] + " ");
                items.add(new BoardItem(53 + 57 * j, 55 + 58 * i, Resource.pictures.get("背面")));
            }
            System.out.println();
        }
        add(boardLabel);
        boardLabel.setLayout(new FlowLayout());
        boardLabel.add(player);
        for (int i = 0; i < 32; i++) {
            boardLabel.add(items.get(i));
        }
    }

    private class BoardItem extends JLabel {
        private int x;
        private int y;
        private Icon icon;
        private JLabel jLabel;

        public BoardItem(int x, int y, Icon icon) {
            this.x = x;
            this.y = y;
            this.icon = icon;
        }

        public BoardItem(int x, int y, JLabel jLabel) {
            this.x = x;
            this.y = y;
            this.jLabel = jLabel;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void setIcon(Icon icon) {
            this.icon = icon;
        }

        public JLabel getjLabel() {
            return jLabel;
        }

        @Override
        public Icon getIcon() {
            return icon;
        }

        public boolean containIcon(Icon icon) {
            return this.icon == icon;
        }
    }

    private class MyListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 8; j++) {
                    if (52 + 57 * j < e.getX() && e.getX() < 94 + 57 * j && 67 + 58 * i < e.getY() &&
                            e.getY() < 105 + 58 * i) {
                        int point[] = {i, j}; //選擇棋子 (滑鼠點擊動作)
                        if (board.getCoverchess()[point[0]][point[1]].equals("O")) { //如果棋子覆蓋狀態
                            System.out.println(board.getCoverchess()[point[0]][point[1]]);
                            board.openChess(board.getCoverchess(), board.getChessboard(), point);
                            items.get(i * 8 + j).setIcon(Resource.pictures.get(board.getChessboard()[i][j]));  //翻開的動作
                            if(board.getblackFirst()){ //黑棋開局
                                if(board.getCount()%2==0){ //第一手
                                    player.setText("Player1(黑棋)");
                                }else {
                                    player.setText("Player2(紅棋)");
                                }
                            }else {  //紅棋開局
                                if(board.getCount()%2==0){  //第一手
                                    player.setText("Player1(紅棋)");
                                }else {
                                    player.setText("Player2(黑棋)");
                                }
                            }

                        } else { //點擊棋子一定是移動 第一次標記棋子 第二次移動到指定位置
                            haveSelect = false;
                            for (int k = 0; k < 32; k++) {
                                if (items.get(k).containIcon(Resource.pictures.get("標記"))) {
                                    haveSelect = true;
                                }
                            }
                            if (haveSelect) { //點第二次
                                if (board.getCoverchess()[points.get(0)[0]][points.get(0)[1]].equals("包") ||
                                        board.getCoverchess()[points.get(0)[0]][points.get(0)[1]].equals("炮")) { //如果第一次選包或炮移動
                                    if (Math.abs(points.get(0)[0] + points.get(0)[1] - i - j) == 1) { //判斷位移一格
                                        board.move(board.getCoverchess(), points.get(0), point);
                                        for (int k = 0; k < 32; k++) {
                                            items.get(k).setIcon(Resource.pictures.get(board.getCoverchess()[k / 8][k % 8]));
                                            if (k % 8 == 0) {
                                                System.out.println();
                                            }
                                            System.out.print(board.getCoverchess()[k / 8][k % 8] + " ");
                                        }
                                        if(board.getblackFirst()) {
                                            if(board.getCount()%2==0){
                                                player.setText("Player1(黑棋)");
                                            }else {
                                                player.setText("Player2(紅棋)");
                                            }
                                        }else {
                                            if(board.getCount()%2==0){
                                                player.setText("Player1(紅棋)");
                                            }else {
                                                player.setText("Player2(黑棋)");
                                            }
                                        }
                                        points.clear();
                                        System.out.println();
                                    } else { //如果包或炮飛
                                        board.bao1(board.getCoverchess(), points.get(0), point);
                                        for (int k = 0; k < 32; k++) {
                                            items.get(k).setIcon(Resource.pictures.get(board.getCoverchess()[k / 8][k % 8]));
                                            if (k % 8 == 0) {
                                                System.out.println();
                                            }
                                            System.out.print(board.getCoverchess()[k / 8][k % 8] + " ");
                                        }
                                        if(board.getblackFirst()) {
                                            if(board.getCount()%2==0){
                                                player.setText("Player1(黑棋)");
                                            }else {
                                                player.setText("Player2(紅棋)");
                                            }
                                        }else {
                                            if(board.getCount()%2==0){
                                                player.setText("Player1(紅棋)");
                                            }else {
                                                player.setText("Player2(黑棋)");
                                            }
                                        }
                                        points.clear();
                                        System.out.println();
                                    }
                                } else {  //如果第一次移動不是包或炮
                                    if (Math.abs(points.get(0)[0] + points.get(0)[1] - i - j) == 1) { //判斷位移一格
                                        points.add(point);
                                        System.out.println(points.get(0)[0] + "," + points.get(0)[1] + " " + points.get(1)[0] + "," + points.get(1)[1]);
                                        board.move(board.getCoverchess(), points.get(0), point);
                                        for (int l = 0; l < 32; l++) {
                                            items.get(l).setIcon(Resource.pictures.get(board.getCoverchess()[l / 8][l % 8]));
                                            if (l % 8 == 0) {
                                                System.out.println();
                                            }
                                            System.out.print(board.getCoverchess()[l / 8][l % 8] + " ");
                                        }
                                        if(board.getblackFirst()) {
                                            if(board.getCount()%2==0){
                                                player.setText("Player1(黑棋)");
                                            }else {
                                                player.setText("Player2(紅棋)");
                                            }
                                        }else {
                                            if(board.getCount()%2==0){
                                                player.setText("Player1(紅棋)");
                                            }else {
                                                player.setText("Player2(黑棋)");
                                            }
                                        }
                                        points.clear();
                                        System.out.println();
                                    } else { //如果不是移動一格
                                        System.out.println("無法移動");
                                        for (int k = 0; k < 32; k++) {
                                            items.get(k).setIcon(Resource.pictures.get(board.getCoverchess()[k / 8][k % 8]));
                                            if (k % 8 == 0) {
                                                System.out.println();
                                            }
                                            System.out.print(board.getCoverchess()[k / 8][k % 8] + " ");
                                        }
                                        points.clear();
                                        System.out.println();
                                    }
                                }
                            } else { //點第一次
                                if (!board.getCoverchess()[i][j].equals("1")) { //第一次點不能選空格
                                    if(board.getblackFirst()) { //黑棋先
                                        if(board.getCount()%2==0&&Rule.chessBlack.containsKey(board.getCoverchess()[i][j])) { //黑
                                            player.setText("Player1(黑棋)");
                                            points.add(point);
                                            System.out.println("選擇" + board.getCoverchess()[point[0]][point[1]]);
                                            items.get(i * 8 + j).setIcon(Resource.pictures.get("標記"));
                                        } else if (board.getCount()%2==1&&Rule.chessRed.containsKey(board.getCoverchess()[i][j])) { //紅
                                            player.setText("Player2(紅棋)");
                                            points.add(point);
                                            System.out.println("選擇" + board.getCoverchess()[point[0]][point[1]]);
                                            items.get(i * 8 + j).setIcon(Resource.pictures.get("標記"));
                                        }else {
                                            System.out.println("只能選自己的顏色");
                                        }
                                    }else { //紅棋先
                                        if(board.getCount()%2==0&&Rule.chessRed.containsKey(board.getCoverchess()[i][j])) { //紅
                                            player.setText("Player1(紅棋)");
                                            points.add(point);
                                            System.out.println("選擇" + board.getCoverchess()[point[0]][point[1]]);
                                            items.get(i * 8 + j).setIcon(Resource.pictures.get("標記"));
                                        } else if (board.getCount()%2==1&&Rule.chessBlack.containsKey(board.getCoverchess()[i][j])) { //黑
                                            player.setText("Player2(黑棋)" +
                                                    "");
                                            points.add(point);
                                            System.out.println("選擇" + board.getCoverchess()[point[0]][point[1]]);
                                            items.get(i * 8 + j).setIcon(Resource.pictures.get("標記"));
                                        }else {
                                            System.out.println("只能選自己的顏色");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            repaint(); //重畫component類別方法
        }
    }
}
