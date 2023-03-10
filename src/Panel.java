import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


public class Panel extends JPanel implements Serializable{
    static final long serialVersionUID = 42L;
    private JLabel player;
    private JLabel boardLabel;
    private ChessBoard board;
    private ArrayList<BoardItem> items;  //所有棋的座標、圖片
    private ArrayList<BoardItem> selects; //所有標記的圖片 座標
    private JButton button;

    public Panel() {
        board = new ChessBoard();
        player = new JLabel("");
        addMouseListener(new MyListener());
        boardLabel = new JLabel(Resource.pictures.get("棋盤"));
        items = new ArrayList<>();
        selects = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board.getChessBoard()[i][j] + " ");
                items.add(new BoardItem(53 + 57 * j, 55 + 58 * i, Resource.pictures.get("背面")));
                selects.add(new BoardItem(53 + 57 * j, 55 + 58 * i, Resource.pictures.get("")));
            }
            System.out.println();
        }
        add(boardLabel);
        boardLabel.setLayout(new FlowLayout());
        boardLabel.add(player);
        for (int i = 0; i < 32; i++) {
            boardLabel.add(items.get(i));
            boardLabel.add(selects.get(i));
        }
    }

    private class BoardItem extends JLabel {
        private int x;
        private int y;
        private Icon icon;

        public BoardItem(int x, int y, Icon icon) {
            this.x = x;
            this.y = y;
            this.icon = icon;
        }

        /**
         * 棋盤物件定位在X座標 (改寫JLabel方法)
         *
         * @return
         */
        @Override
        public int getX() {
            return x;
        }

        /**
         * 棋盤物件定位在Y座標 (改寫JLabel方法)
         *
         * @return
         */
        @Override
        public int getY() {
            return y;
        }

        /**
         * 改變棋盤物件的圖片
         *
         * @param icon the default icon this component will display
         */
        @Override
        public void setIcon(Icon icon) {
            this.icon = icon;
        }

        public JLabel selectIcon() {
            BoardItem boardItem = new BoardItem(getX(), getY(), getIcon());
            BoardItem boardItem1 = new BoardItem(getX(), getY(), Resource.pictures.get("標記"));
            JLabel jLabel = new JLabel();
            jLabel.add(boardItem1);
            jLabel.add(boardItem);
            return jLabel;
        }

        /**
         * 返回現在物件的圖片
         *
         * @return
         */
        @Override
        public Icon getIcon() {
            return icon;
        }

        /**
         * 判斷圖片是否跟現在的圖相同
         *
         * @param icon
         * @return
         */
        public boolean containIcon(Icon icon) {
            return this.icon == icon;
        }
    }

    private class MyListener extends MouseAdapter {
        private ArrayList<int[]> points = new ArrayList<>(); //紀錄點座標

        @Override
        public void mouseClicked(MouseEvent e) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 8; j++) {
                    if (haveItem(i, j, e)) { //如果點擊的位置有物件
                        int point[] = {i, j}; //點的座標
                        if (isCover(i, j)) { //如果棋子覆蓋狀態
                            board.openChess(board.getCoverchess(), board.getChessBoard(), point);  //文字翻棋
                            setAllIcon();
                            setPlayer();
                        } else { //點擊棋子一定是移動 第一次標記棋子 第二次移動到指定位置
                            if (haveSelect()) { //如果有標記的棋子
                                if (isBao(points.get(0)[0], points.get(0)[1])) {  //如果第一次選包或炮
                                    if (Math.abs(points.get(0)[0] + points.get(0)[1] - i - j) == 1) { //判斷位移一格
                                        board.move(board.getCoverchess(), points.get(0), point); // 棋子移動
                                        setAllIcon(); //重新畫所有的圖片
                                        setPlayer(); //設定PlayerLabel
                                        points.clear();
                                        System.out.println();
                                    } else { //如果包或炮飛
                                        board.bao(board.getCoverchess(), points.get(0), point);
                                        setAllIcon();
                                        setPlayer();
                                        points.clear();
                                        System.out.println();
                                    }
                                } else {  //如果第一次移動不是包或炮
                                    if (Math.abs(points.get(0)[0] + points.get(0)[1] - i - j) == 1) { //判斷位移一格
                                        points.add(point);
                                        System.out.println(points.get(0)[0] + "," + points.get(0)[1] + " " + points.get(1)[0] + "," + points.get(1)[1]);
                                        board.move(board.getCoverchess(), points.get(0), point);
                                        setAllIcon();
                                        setPlayer();
                                        points.clear();
                                        System.out.println();
                                    } else { //如果不是移動一格
                                        System.out.println("無法移動");
                                        setAllIcon();
                                        points.clear();
                                        System.out.println();
                                    }
                                }
                            } else { //點第一次
                                if (!isEmpty(i, j)) { //第一次點不能選空格
                                    if (board.getblackFirst()) { //判斷順序(黑棋)
                                        if (board.getCount() % 2 == 0 && Rule.chessBlack.containsKey(board.getCoverchess()[i][j])) { //黑
                                            player.setText("Player1(黑棋)");
                                            points.add(point);
                                            System.out.println("選擇" + board.getCoverchess()[i][j]);
                                            selects.get(i * 8 + j).setIcon(Resource.pictures.get("標記"));
                                        } else if (board.getCount() % 2 == 1 && Rule.chessRed.containsKey(board.getCoverchess()[i][j])) { //紅
                                            player.setText("Player2(紅棋)");
                                            points.add(point);
                                            System.out.println("選擇" + board.getCoverchess()[i][j]);
                                            selects.get(i * 8 + j).setIcon(Resource.pictures.get("標記"));

                                        } else {
                                            System.out.println("只能選自己的顏色");
                                        }
                                    } else { //判斷順序(紅棋)
                                        if (board.getCount() % 2 == 0 && Rule.chessRed.containsKey(board.getCoverchess()[i][j])) { //紅
                                            player.setText("Player1(紅棋)");
                                            points.add(point);
                                            System.out.println("選擇" + board.getCoverchess()[i][j]);
                                            selects.get(i * 8 + j).setIcon(Resource.pictures.get("標記"));

                                        } else if (board.getCount() % 2 == 1 && Rule.chessBlack.containsKey(board.getCoverchess()[i][j])) { //黑
                                            player.setText("Player2(黑棋)");
                                            points.add(point);
                                            System.out.println("選擇" + board.getCoverchess()[i][j]);
                                            selects.get(i * 8 + j).setIcon(Resource.pictures.get("標記"));

                                        } else {
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

        /**
         * 如果點擊的位置有物件
         *
         * @param x
         * @param y
         * @param e
         * @return
         */
        private boolean haveItem(int x, int y, MouseEvent e) {
            if (52 + 57 * y < e.getX() && e.getX() < 94 + 57 * y && 67 + 58 * x < e.getY() &&
                    e.getY() < 105 + 58 * x) {
                return true;
            }
            return false;
        }

        private void setAllIcon() {
            for (int k = 0; k < 32; k++) {
                items.get(k).setIcon(Resource.pictures.get(board.getCoverchess()[k / 8][k % 8]));
                selects.get(k).setIcon(Resource.pictures.get(board.getCoverchess()[k / 8][k % 8]));
                if (k % 8 == 0) {
                    System.out.println();
                }
                System.out.print(board.getCoverchess()[k / 8][k % 8] + " ");
            }
        }

        /**
         * 判斷是否為覆蓋的牌
         *
         * @param x
         * @param y
         * @return
         */
        private boolean isCover(int x, int y) {
            if (board.getCoverchess()[x][y].equals("O")) {
                return true;
            }
            return false;
        }

        /**
         * 設定playerLabel的文字
         */
        private void setPlayer() {
            if (board.getblackFirst()) { //黑棋開局
                if (board.isPlayer1()) { //第一手
                    player.setText("Player1(黑棋)");
                } else {
                    player.setText("Player2(紅棋)");
                }
            } else {  //紅棋開局
                if (board.isPlayer1()) {  //第一手
                    player.setText("Player1(紅棋)");
                } else {
                    player.setText("Player2(黑棋)");
                }
            }
        }

        /**
         * 如果已經有標記的棋子(點第二次)
         *
         * @return
         */
        private boolean haveSelect() {
            for (int i = 0; i < 32; i++) {  //32顆棋子
                if (selects.get(i).containIcon(Resource.pictures.get("標記"))) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 判斷是不是包(點第一次)
         *
         * @return
         */
        private boolean isBao(int x, int y) {
            if (board.getCoverchess()[x][y].equals("包") ||
                    board.getCoverchess()[x][y].equals("炮")) {
                return true;
            }
            return false;
        }

        private boolean isEmpty(int x, int y) {
            if (board.getCoverchess()[x][y].equals("1")) {
                return true;
            }
            return false;
        }
    }

    /**
     * 存檔
     * @param outputStream
     * @throws Exception
     */
    public void save(OutputStream outputStream) throws Exception{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        GameSave gameSave = new GameSave(board,this);
        objectOutputStream.writeObject(gameSave);
        System.out.println("已存入遊戲");

    }

    /**
     * 載入
     * @param inputStream
     * @throws Exception
     */
    public void load(InputStream inputStream) throws Exception{
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        GameSave gameSave = (GameSave) objectInputStream.readObject();
        board=gameSave.getChessBoard();
        for (int i = 0; i < 32; i++) {
            items.get(i).setIcon(Resource.pictures.get(board.getCoverchess()));
        }
        repaint();
        System.out.println("已載入遊戲");
//        chessBoard = (XqChessBoard) gameSave.getChessBoard();
//        red = gameSave.isRed();
//        playing = gameSave.isPlaying();
//        selectPoint = null;
//        walkState.setChessBoard(chessBoard);
//        repaintBoard();
    }
}
