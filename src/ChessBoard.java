import java.io.*;
import java.util.LinkedList;
public class ChessBoard implements Serializable {
    static final long serialVersionUID = 42L;
    public final static int column = 4; //列
    public final static int row = 8; //行
    private boolean blackFirst;
    private int count;
    private String[][] chessBoard; //所有棋的子(無覆蓋)
    private String[][] coverChess; //目前棋盤狀態(有覆蓋)
    private boolean[][] isSelects; //被選
    public ChessBoard() {
        chessBoard = new String[column][row];
        coverChess = new String[column][row];
        count = 0;
        blackFirst = false;
        newGame();
    }
    private boolean canMoved(String o1, String o2) {
        //如果棋子移動過去是空的或比對方大或一樣大
        if (o2.equals("1") || Rule.compare(o1, o2)) {
            return true;
        }
        System.out.println("同隊(o1" + o1 + "o2" + o2 + ")");
        System.out.println("無法吃");
        return false;
    }

    private String[] swap(String[] arr, int i, int j) {
        String tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        return arr;
    }

    private void washChess(int num, String chess[]) {
        for (int i = 0; i < 100000; i++) {
            int rand1 = (int) (Math.random() * num);
            int rand2 = (int) (Math.random() * num);
            chess = swap(chess, rand1, rand2);
        }
    }

    /**
     * 新局
     */
    public void newGame() {
        washChess(Resource.chessValue.length, Resource.chessValue);
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                chessBoard[i][j] = Resource.chessValue[j + row * i];  //擺好棋子chessboard值
                coverChess[i][j] = "O"; //覆蓋排全為英文O coverchess牌面
            }
        }
        System.out.println("開啟新局");
    }

    /**
     * 翻棋
     *
     * @param coverchess
     * @param chessboard
     * @param arr
     */
    public void openChess(String[][] coverchess, String[][] chessboard, int arr[]) {

        if (coverchess[arr[0]][arr[1]].equals("O")) { //如果還是覆蓋的狀態
            coverchess[arr[0]][arr[1]] = chessboard[arr[0]][arr[1]]; //打開賦值
            System.out.println("以翻開【" + coverchess[arr[0]][arr[1]] + "】(" + arr[0] + "," + arr[1] + ")");
            count++;
        }
        if (count == 1) {
            if (Rule.chessBlack.containsKey(coverchess[arr[0]][arr[1]])) {
                System.out.println("Player1:黑棋,Player2:紅棋");
                blackFirst = true;
            } else {
                System.out.println("Player1:紅棋,Player2:黑棋");
                blackFirst = false;
            }
        }
    }

    /**
     * 點擊移動
     *
     * @param coverchess
     * @param select
     * @param move
     */
    public void move(String[][] coverchess, int select[], int move[]) {
        int dx = move[0] - select[0]; //X變化量
        int dy = move[1] - select[1]; //Y變化量
        if (!coverchess[select[0]][select[1]].equals("包") && !coverchess[select[0]][select[1]].equals("炮")) {
            if (canMoved(coverchess[select[0]][select[1]], coverchess[select[0] + dx][select[1] + dy])) {
                coverchess[select[0] + dx][select[1] + dy] = coverchess[select[0]][select[1]];
                coverchess[select[0]][select[1]] = "1";
                System.out.println("以移動");
                count++;
            }
        } else if (coverchess[select[0]][select[1]].equals("包") || coverchess[select[0]][select[1]].equals("炮")) {
            if (coverchess[select[0] + dx][select[1] + dy].equals("1")) {
                coverchess[select[0] + dx][select[1] + dy] = coverchess[select[0]][select[1]];
                coverchess[select[0]][select[1]] = "1";
                System.out.println("以移動");
                count++;
            } else {
                System.out.println("無法移動");
            }
        }
    }

    /**
     * 返回無覆蓋的所有棋
     *
     * @return
     */
    public String[][] getChessBoard() {
        return this.chessBoard;
    }

    /**
     * 返回目前狀態的所有棋
     *
     * @return
     */
    public String[][] getCoverchess() {
        return this.coverChess;
    }

    /**
     * 包移動功能 點擊
     *
     * @param coverchess
     * @param select
     * @param move
     */
    public void bao(String[][] coverchess, int select[], int move[]) {
        LinkedList<Integer> haveChessUp = new LinkedList<>();
        LinkedList<Integer> haveChessLeft = new LinkedList<>();
        LinkedList<Integer> haveChessDown = new LinkedList<>();
        LinkedList<Integer> haveChessRight = new LinkedList<>();
        Math.abs(move[0] + move[1] - select[0] - select[1]);
        int dx = move[0] - select[0];
        int dy = move[1] - select[1];
        if (dx < 0) {
            for (int i = 1; i <= select[0]; i++) { //上
                int up;
                //蒐集整排內所有元素 直到list內有2顆棋
                if (!coverchess[select[0] - i][select[1]].equals("1") && haveChessUp.size() < 2) {
                    up = select[0] - i; //收集到的棋子座標
                    haveChessUp.add(up);
                }
            }
        } else if (dx > 0) {
            for (int i = 1; i <= 3 - select[0]; i++) {  //下
                int down;
                //蒐集整排內所有元素 直到list內有2顆棋
                if (!coverchess[select[0] + i][select[1]].equals("1") && haveChessDown.size() < 2) {
                    down = select[0] + i; //收集到的棋子座標
                    haveChessDown.add(down);
                }
            }
        } else if (dy < 0) {
            for (int i = 1; i <= select[1]; i++) { //左
                int left;
                if (!coverchess[select[0]][select[1] - i].equals("1") && haveChessLeft.size() < 2) {
                    left = select[1] - i; //收集到的棋子座標
                    haveChessLeft.add(left);
                }
            }
        } else if (dy > 0) {
            for (int i = 1; i <= 7 - select[1]; i++) { //右
                int right;
                //蒐集整排內所有元素 直到list內有2顆棋
                if (!coverchess[select[0]][select[1] + i].equals("1") && haveChessRight.size() < 2) {
                    right = select[1] + i; //收集到的棋子座標
                    haveChessRight.add(right);
                }
            }
        }
        //判斷list內是否有2個元素
        if (haveChessUp.size() == 2) {
            //rule包的規則判斷 如果不是同隊
            if (Rule.compareBao(coverchess[select[0]][select[1]], coverchess[haveChessUp.get(1)][select[1]])) {
                coverchess[haveChessUp.get(1)][select[1]] = coverchess[select[0]][select[1]];
                coverchess[select[0]][select[1]] = "1";
                System.out.println("以移動");
                count++;
            }
        }
        if (haveChessDown.size() == 2) {
            if (Rule.compareBao(coverchess[select[0]][select[1]], coverchess[haveChessDown.get(1)][select[1]])) {
                coverchess[haveChessDown.get(1)][select[1]] = coverchess[select[0]][select[1]];
                coverchess[select[0]][select[1]] = "1";
                System.out.println("以移動");
                count++;
            }
        }
        if (haveChessLeft.size() == 2) {
            if (Rule.compareBao(coverchess[select[0]][select[1]], coverchess[select[0]][haveChessLeft.get(1)])) {
                coverchess[select[0]][haveChessLeft.get(1)] = coverchess[select[0]][select[1]];
                coverchess[select[0]][select[1]] = "1";
                System.out.println("以移動");
                count++;
            }
        }
        if (haveChessRight.size() == 2) {
            if (Rule.compareBao(coverchess[select[0]][select[1]], coverchess[select[0]][haveChessRight.get(1)])) {
                coverchess[select[0]][haveChessRight.get(1)] = coverchess[select[0]][select[1]];
                coverchess[select[0]][select[1]] = "1";
                System.out.println("以移動");
                count++;
            }
        }
    }

    /**
     * 返回blackFirst值
     *
     * @return
     */
    public boolean getblackFirst() {
        return this.blackFirst;
    }

    /**
     * 返回count值
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * 是否先手方
     *
     * @return
     */
    public boolean isPlayer1() {
        if (count % 2 == 0) {
            return true;
        }
        return false;
    }
}
