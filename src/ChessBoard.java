import java.awt.*;
import java.util.Scanner;

public class ChessBoard {
    private final static int column = 4; //列
    private final static int row = 8; //行
    //所有象棋
    private static final String[] chessValue = {"兵", "兵", "兵", "兵", "兵", "卒", "卒", "卒", "卒", "卒",
            "傌", "傌", "馬", "馬", "俥", "俥", "車", "車", "炮", "炮", "包",
            "包", "相", "相", "象", "象", "仕", "仕", "士", "士", "帥", "將"};

    public enum Action {
        移動,
        吃;

        public static Action getvalues(int index) {
            Action[] actions = Action.values();
            return actions[index - 1];
        }
    }

    public enum Direction {
        上,
        下,
        左,
        右,
        上一步;

        public static Direction getvalues(int index) {
            Direction[] direction = Direction.values();
            return direction[index - 1];
        }
    }

    private String[][] chessBoard = new String[column][row]; //所有棋的子(無覆蓋)
    private String[][] coverChess = new String[column][row]; //目前棋盤狀態(有覆蓋)

    public ChessBoard() {
    }

    private boolean canMoved(String o1, String o2) {
        //如果棋子移動過去是空的或比對方大或一樣大
        if (o2.equals("1") || Rule.compare(o1, o2) || Rule.compare(o1, o2)) {
            return true;
        }
        System.out.println("無法吃");
        return false;
    }

    private boolean canEat(String o1, String o2) {
        if (Rule.compareB(o1, o2)) {
            return true;
        }
        return false;
    }

    private String[] swap(String[] arr, int i, int j) {
        String tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        return arr;
    }

    private void washchess(int num, String chess[]) {
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
        washchess(chessValue.length, chessValue);
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                chessBoard[i][j] = chessValue[j + row * i];  //擺好棋子chessboard值
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
            System.out.println("以翻開" + "(" + arr[0] + "," + arr[1] + ")");
        }
    }

    /**
     * 移動
     *
     * @param coverchess
     */
    public void move(String[][] coverchess, int arr[]) {
        Scanner sc = new Scanner(System.in);
        //不是包包炮炮正常棋子
        if (!coverchess[arr[0]][arr[1]].equals("O") && !coverchess[arr[0]][arr[1]].equals("1")) {
            System.out.println("選擇移動方向:");
            while (true) {
                System.out.println("1:上 2:下 3:左 4:右 5:取消");
                int dir = sc.nextInt();
                boolean stop = false;
                switch (Direction.getvalues(dir)) {
                    case 上:
                        //如果沒超過邊界而且下一步不是蓋牌狀態(普通移動含吃)
                        if (arr[0] - 1 >= 0 && !coverchess[arr[0] - 1][arr[1]].equals("O")) {
                            if (canMoved(coverchess[arr[0]][arr[1]], coverchess[arr[0] - 1][arr[1]])) {
                                coverchess[arr[0] - 1][arr[1]] = coverchess[arr[0]][arr[1]];
                                coverchess[arr[0]][arr[1]] = "1";
                                stop = true;
                                System.out.println("以移動");
                                break;
                            }
                        }
                        System.out.println("請重新選擇移動方向:");
                        break;
                    case 下:
                        if (arr[0] + 1 < column && !coverchess[arr[0] + 1][arr[1]].equals("O")) {
                            if (canMoved(coverchess[arr[0]][arr[1]], coverchess[arr[0] + 1][arr[1]])) {
                                coverchess[arr[0] + 1][arr[1]] = coverchess[arr[0]][arr[1]];
                                coverchess[arr[0]][arr[1]] = "1";
                                stop = true;
                                System.out.println("以移動");
                                break;
                            }
                        }
                        System.out.println("請重新選擇移動方向:");
                        break;
                    case 右:
                        if (arr[1] + 1 < row && !coverchess[arr[0]][arr[1] + 1].equals("O")) {
                            if (canMoved(coverchess[arr[0]][arr[1]], coverchess[arr[0]][arr[1] + 1])) {
                                coverchess[arr[0]][arr[1] + 1] = coverchess[arr[0]][arr[1]];
                                coverchess[arr[0]][arr[1]] = "1";
                                stop = true;
                                System.out.println("以移動");
                                break;
                            }
                        }
                        System.out.println("請重新選擇移動方向:");
                        break;
                    case 左:
                        if (arr[1] - 1 >= 0 && !coverchess[arr[0]][arr[1] - 1].equals("O")) {
                            if (canMoved(coverchess[arr[0]][arr[1]], coverchess[arr[0]][arr[1] - 1])) {
                                coverchess[arr[0]][arr[1] - 1] = coverchess[arr[0]][arr[1]];
                                coverchess[arr[0]][arr[1]] = "1";
                                stop = true;
                                System.out.println("以移動");
                                break;
                            }
                        }
                        System.out.println("請重新選擇移動方向:");
                        break;
                    case 上一步:
                        stop = true;
                        break;
                }
                if (stop) {
                    break;
                }
            }
        }
    }

    public void eat(String[][] coverchess, int arr[]) {
        //如果棋子是包包炮炮打一炮
        Scanner sc = new Scanner(System.in);
        System.out.println("選擇飛的方向:");
        while (true) {
            System.out.println("1:上 2:下 3:左 4:右 5:取消");
            int dir = sc.nextInt();
            boolean stop = false;
            switch (Direction.getvalues(dir)) {
                case 上:
                    //如果沒超過邊界而且下一步不是蓋牌狀態
                    if (arr[0] - 2 >= 0 && !coverchess[arr[0] - 2][arr[1]].equals("O") && !coverchess[arr[0] - 2][arr[1]].equals("1")) {
                        //如果不是同隊就可以飛而且中間不能為空
                        if (canEat(coverchess[arr[0]][arr[1]], coverchess[arr[0] - 2][arr[1]]) && !coverchess[arr[0] - 1][arr[1]].equals("1")) {
                            coverchess[arr[0] - 2][arr[1]] = coverchess[arr[0]][arr[1]];
                            coverchess[arr[0]][arr[1]] = "1";
                            stop = true;
                            System.out.println("以移動");
                            break;
                        }
                    }
                    System.out.println("請重新選擇移動方向:");
                    break;
                case 下:
                    if (arr[0] + 2 < column && !coverchess[arr[0] + 2][arr[1]].equals("O") && !coverchess[arr[0] - 2][arr[1]].equals("1")) {
                        if (canEat(coverchess[arr[0]][arr[1]], coverchess[arr[0] + 2][arr[1]])&& !coverchess[arr[0] - 1][arr[1]].equals("1")) {
                            coverchess[arr[0] + 2][arr[1]] = coverchess[arr[0]][arr[1]];
                            coverchess[arr[0]][arr[1]] = "1";
                            stop = true;
                            System.out.println("以移動");
                            break;
                        }
                    }
                    System.out.println("請重新選擇移動方向:");
                    break;
                case 右:
                    if (arr[1] + 2 < row && !coverchess[arr[0]][arr[1] + 2].equals("O") && !coverchess[arr[0] - 2][arr[1]].equals("1")) {
                        if (canEat(coverchess[arr[0]][arr[1]], coverchess[arr[0]][arr[1] + 2])&& !coverchess[arr[0] - 1][arr[1]].equals("1")) {
                            coverchess[arr[0]][arr[1] + 2] = coverchess[arr[0]][arr[1]];
                            coverchess[arr[0]][arr[1]] = "1";
                            stop = true;
                            System.out.println("以移動");
                            break;
                        }
                    }
                    System.out.println("請重新選擇移動方向:");
                    break;
                case 左:
                    if (arr[1] - 2 >= 0 && !coverchess[arr[0]][arr[1] - 2].equals("O") && !coverchess[arr[0] - 2][arr[1]].equals("1")) {
                        if (canEat(coverchess[arr[0]][arr[1]], coverchess[arr[0]][arr[1] - 2])&& !coverchess[arr[0] - 1][arr[1]].equals("1")) {
                            coverchess[arr[0]][arr[1] - 2] = coverchess[arr[0]][arr[1]];
                            coverchess[arr[0]][arr[1]] = "1";
                            stop = true;
                            System.out.println("以移動");
                            break;
                        }
                    }
                    System.out.println("請重新選擇移動方向:");
                    break;
                case 上一步:
                    stop = true;
                    break;
            }
            if (stop) {
                break;
            }
        }
    }

    /**
     * 選棋子 返回棋子
     *
     * @param coverchess
     * @return
     */
    public int[] selectChess(String[][] coverchess) {
        Scanner sc = new Scanner(System.in);
        System.out.println("選擇棋子(x,y):");
        int[] arr = new int[2];
        while (true) {
            arr[0] = sc.nextInt();
            arr[1] = sc.nextInt();
            if (arr[0] > 3 || arr[0] < 0 || arr[1] > 7 || arr[1] < 0) { //在無效棋盤內
                System.out.println("無效棋盤");
                System.out.println("請重新輸入:");
            } else {
                if (coverchess[arr[0]][arr[1]].equals("1")) { //判斷是不是空位置
                    System.out.println("選擇位置不是棋子");
                    System.out.println("請重新輸入:");
                } else {
                    System.out.println("以選擇(" + arr[0] + "," + arr[1] + ")");
                    break;
                }
            }
        }
        return arr;
    }

    /**
     * 返回無覆蓋的所有棋
     *
     * @return
     */
    public String[][] getChessboard() {
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
     * 列印棋面
     */
    public void printBoard() {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                System.out.print(coverChess[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 列印所有值
     */
    public void printAll() {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                System.out.print(chessBoard[i][j] + " ");
            }
            System.out.println();
        }
    }
}
