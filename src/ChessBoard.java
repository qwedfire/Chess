import java.awt.*;
import java.util.LinkedList;
import java.util.Scanner;

public class ChessBoard {
    private boolean blackFirst;
    private int count;
    private final static int column = 4; //列
    private final static int row = 8; //行
    //所有象棋
    private static final String[] chessValue = {"兵", "兵", "兵", "兵", "兵", "卒", "卒", "卒", "卒", "卒",
            "傌", "傌", "馬", "馬", "俥", "俥", "車", "車", "炮", "炮", "包",
            "包", "相", "相", "象", "象", "仕", "仕", "士", "士", "帥", "將"};

    public enum Action {
        移動,
        吃,
        取消;

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
    private static String[][] coverChess = new String[column][row]; //目前棋盤狀態(有覆蓋)

    public ChessBoard() {
        count = 0;
        blackFirst = false;
    }

    private boolean canMoved(String o1, String o2) {
        //如果棋子移動過去是空的或比對方大或一樣大
        if (o2.equals("1") || Rule.compare(o1, o2) || Rule.compare(o1, o2)) {
            return true;
        }
        System.out.println("無法吃");
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
     * 移動
     *
     * @param coverchess
     */
    public void move(String[][] coverchess, int arr[]) {
        Scanner sc = new Scanner(System.in);
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
                                count++;
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
                                count++;
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
                                count++;
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
                                count++;
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

    /**
     * 選棋子 返回棋子
     *
     * @param coverchess
     * @return
     */
    public int[] selectChess(String[][] coverchess) {
        Scanner sc = new Scanner(System.in);
        if (count != 0) {
            if (blackFirst) {
                if (count % 2 == 0) {
                    System.out.print("Player" + (count % 2 + 1) + "(黑子) 選擇棋子(x,y):");
                } else {
                    System.out.print("Player" + (count % 2 + 1) + "(紅子) 選擇棋子(x,y):");
                }
            } else {
                if (count % 2 == 0) {
                    System.out.print("Player" + (count % 2 + 1) + "(紅子) 選擇棋子(x,y):");
                } else {
                    System.out.print("Player" + (count % 2 + 1) + "(黑子) 選擇棋子(x,y):");
                }
            }
        } else {
            System.out.print("Player1 選擇棋子(x,y):");
        }
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
                    if (count != 0) {
                        if (blackFirst) {
                            if (count % 2 == 0 && Rule.chessBlack.containsKey(coverchess[arr[0]][arr[1]])) { //判斷紅黑棋玩家
                                System.out.print("以選擇【" + coverchess[arr[0]][arr[1]] + "】(" + arr[0] + "," + arr[1] + ") ");
                                break;
                            } else if (count % 2 == 1 && Rule.chessRed.containsKey(coverchess[arr[0]][arr[1]])) {
                                System.out.print("以選擇【" + coverchess[arr[0]][arr[1]] + "】(" + arr[0] + "," + arr[1] + ") ");
                                break;
                            } else {
                                System.out.println("只能選擇自己的顏色");
                                System.out.println("請重新輸入:");
                            }
                        } else {
                            if (count % 2 == 0 && Rule.chessRed.containsKey(coverchess[arr[0]][arr[1]])) { //判斷紅黑棋玩家
                                System.out.print("以選擇【" + coverchess[arr[0]][arr[1]] + "】(" + arr[0] + "," + arr[1] + ") ");
                                break;
                            } else if (count % 2 == 1 && Rule.chessBlack.containsKey(coverchess[arr[0]][arr[1]])) {
                                System.out.print("以選擇【" + coverchess[arr[0]][arr[1]] + "】(" + arr[0] + "," + arr[1] + ") ");
                                break;
                            } else {
                                System.out.println("只能選擇自己的顏色");
                                System.out.println("請重新輸入:");
                            }
                        }
                    } else {
                        System.out.print("以選擇(" + arr[0] + "," + arr[1] + ") ");
                        break;
                    }
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

    /**
     * 包移動功能
     *
     * @param coverchess
     * @param arr
     */
    public void bao(String[][] coverchess, int arr[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("輸入動作 1:移動 2:飛 3:取消");
        switch (Action.getvalues(sc.nextInt())) {
            case 吃:
                System.out.println("選擇飛的方向:");
                while (true) {
                    System.out.println("1:上 2:下 3:左 4:右 5:取消");
                    int dir = sc.nextInt();
                    boolean stop = false;
                    switch (Direction.getvalues(dir)) {
                        case 上:
                            LinkedList<Integer> haveChess = new LinkedList<>();
                            for (int i = 1; i <= arr[0]; i++) {
                                int x;
                                //蒐集整排內所有元素 直到list內有2顆棋
                                if (!coverchess[arr[0] - i][arr[1]].equals("1") && haveChess.size() < 2) {
                                    x = arr[0] - i; //收集到的棋子座標
                                    haveChess.add(x);
                                }
                            }
                            //判斷list內是否有2個元素
                            if (haveChess.size() == 2) {
                                //rule包的規則判斷 如果不是同隊
                                if (Rule.compareB(coverchess[arr[0]][arr[1]], coverchess[haveChess.get(1)][arr[1]])) {
                                    coverchess[haveChess.get(1)][arr[1]] = coverchess[arr[0]][arr[1]];
                                    coverchess[arr[0]][arr[1]] = "1";
                                    System.out.println("以移動");
                                    count++;
                                    stop = true;
                                }
                            }
                            //同一排元素不到2直接跳出迴圈
                            System.out.println("請重新選擇移動方向:");
                            break;
                        case 下:
                            LinkedList<Integer> haveChess1 = new LinkedList<>();
                            for (int i = 1; i <= 3 - arr[0]; i++) {
                                int x;
                                //蒐集整排內所有元素 直到list內有2顆棋
                                if (!coverchess[arr[0] + i][arr[1]].equals("1") && haveChess1.size() < 2) {
                                    x = arr[0] + i; //收集到的棋子座標
                                    haveChess1.add(x);
                                }
                            }
                            //判斷list內是否有2個元素
                            if (haveChess1.size() == 2) {
                                //rule包的規則判斷 如果不是同隊
                                if (Rule.compareB(coverchess[arr[0]][arr[1]], coverchess[haveChess1.get(1)][arr[1]])) {
                                    coverchess[haveChess1.get(1)][arr[1]] = coverchess[arr[0]][arr[1]];
                                    coverchess[arr[0]][arr[1]] = "1";
                                    System.out.println("以移動");
                                    count++;
                                    stop = true;
                                }
                            }
                            //同一排元素不到2直接跳出迴圈
                            System.out.println("請重新選擇移動方向:");
                            break;
                        case 右:
                            LinkedList<Integer> haveChess2 = new LinkedList<>();
                            for (int i = 1; i <= 7 - arr[1]; i++) {
                                int y;
                                //蒐集整排內所有元素 直到list內有2顆棋
                                if (!coverchess[arr[0]][arr[1] + i].equals("1") && haveChess2.size() < 2) {
                                    y = arr[1] + i; //收集到的棋子座標
                                    haveChess2.add(y);
                                }
                            }
                            //判斷list內是否有2個元素
                            if (haveChess2.size() == 2) {
                                //rule包的規則判斷 如果不是同隊
                                if (Rule.compareB(coverchess[arr[0]][arr[1]], coverchess[arr[0]][haveChess2.get(1)])) {
                                    coverchess[arr[0]][haveChess2.get(1)] = coverchess[arr[0]][arr[1]];
                                    coverchess[arr[0]][arr[1]] = "1";
                                    System.out.println("以移動");
                                    count++;
                                    stop = true;
                                }
                            }
                            //同一排元素不到2直接跳出迴圈
                            System.out.println("請重新選擇移動方向:");
                            break;
                        case 左:
                            LinkedList<Integer> haveChess3 = new LinkedList<>();
                            for (int i = 1; i <= arr[1]; i++) {
                                int y;
                                //蒐集整排內所有元素 直到list內有2顆棋
                                if (!coverchess[arr[0]][arr[1] - i].equals("1") && haveChess3.size() < 2) {
                                    y = arr[1] - i; //收集到的棋子座標
                                    haveChess3.add(y);
                                }
                            }
                            //判斷list內是否有2個元素
                            if (haveChess3.size() == 2) {
                                //rule包的規則判斷 如果不是同隊
                                if (Rule.compareB(coverchess[arr[0]][arr[1]], coverchess[arr[0]][haveChess3.get(1)])) {
                                    coverchess[arr[0]][haveChess3.get(1)] = coverchess[arr[0]][arr[1]];
                                    coverchess[arr[0]][arr[1]] = "1";
                                    System.out.println("以移動");
                                    count++;
                                    stop = true;
                                }
                            }
                            //同一排元素不到2直接跳出迴圈
                            System.out.println("請重新選擇移動方向:");
                            break;
                        case 上一步:
                            stop = true;
                            break;
                        default:
                            break;
                    }
                    if (stop) {
                        break;
                    }
                }
                break;
            case 移動:
                move(coverchess, arr);
                break;
            case 取消:
                break;
            default:
                break;
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
}
