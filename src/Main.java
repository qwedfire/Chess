import java.util.Scanner;

public class Main {
    public enum Action {
        翻棋,
        移動;

        public static Action getvalue(int index) {
            Action[] actions = Action.values();
            return actions[index - 1];
        }
    }

    public enum Direction {
        上,
        下,
        左,
        右;

        public static Direction getvalues(int index) {
            Direction[] direction = Direction.values();
            return direction[index - 1];
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[][] chessboard = new String[4][8]; //棋盤
        String[][] coverchess = new String[4][8]; //覆蓋棋盤0000
        String[] chessvalue = {"兵", "兵", "兵", "兵", "兵", "卒", "卒", "卒", "卒", "卒",
                "傌", "傌", "馬", "馬", "俥", "俥", "車", "車", "炮", "炮", "包",
                "包", "相", "相", "象", "象", "仕", "仕", "士", "士", "帥", "將"};
        washchess(chessvalue.length, chessvalue); //打亂陣列
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                chessboard[i][j] = chessvalue[j + 8 * i];  //擺好棋子
                coverchess[i][j] = "O";
            }
        }

        while (true) {
            for (int i = 0; i < chessboard.length; i++) {
                for (int j = 0; j < chessboard[i].length; j++) {
                    System.out.print(coverchess[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println("---------------------");
            selectChess(coverchess, chessboard); //選位置
            System.out.println("案enter繼續");
            sc.nextLine();
        }
    }

    /**
     * 覆蓋的棋全部翻開
     *
     * @param coverchess
     * @param chessboard
     */
    public static void openall(String[][] coverchess, String[][] chessboard) {
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                if (coverchess[i][j].equals("O")) { //如果覆蓋打開
                    coverchess[i][j] = chessboard[i][j];
                }
            }
        }
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                System.out.print(coverchess[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 選擇棋子
     *
     * @param coverchess
     */
    public static void selectChess(String[][] coverchess, String[][] chessboard) {
        Scanner sc = new Scanner(System.in);
        System.out.println("選擇棋子(x,y):");
        int x, y;
        while (true) {
            x = sc.nextInt();
            y = sc.nextInt();
            if (x >= 0 && x < 4 && y >= 0 && y < 8) {
                if (coverchess[x][y].equals("1")) {
                    System.out.println("選擇位置不是棋子");
                    selectChess(coverchess, chessboard);
                }
                break;
            } else {
                System.out.println("選擇位置無棋子");
                selectChess(coverchess, chessboard);
            }
        }
        if (coverchess[x][y].equals("O")) {
            coverchess[x][y] = chessboard[x][y];
            System.out.println("以翻開" + "(" + x + "," + y + ")");
        } else if (!coverchess[x][y].equals("O") && !coverchess[x][y].equals("1")) {
            System.out.println("選擇移動方向:");
            while (true) {
                System.out.println("1:上 2:下 3:左 4:右");
                int dir = sc.nextInt();
                boolean stop = false;
                switch (Direction.getvalues(dir)) {
                    case 上:
                        if (x - 1 >= 0 && !coverchess[x - 1][y].equals("O")) {
                            coverchess[x - 1][y] = coverchess[x][y];
                            coverchess[x][y] = "1";
                            stop = true;
                            break;
                        }
                        System.out.println("請重新選擇移動方向:");
                        break;
                    case 下:
                        if (x + 1 < 4 && !coverchess[x + 1][y].equals("O")) {
                            coverchess[x + 1][y] = coverchess[x][y];
                            coverchess[x][y] = "1";
                            stop = true;
                            break;
                        }
                        System.out.println("請重新選擇移動方向:");
                        break;
                    case 右:
                        if (y + 1 < 8 && !coverchess[x][y + 1].equals("O")) {
                            coverchess[x][y + 1] = coverchess[x][y];
                            coverchess[x][y] = "1";
                            stop = true;
                            break;
                        }
                        System.out.println("請重新選擇移動方向:");
                        break;
                    case 左:
                        if (y - 1 >= 0 && !coverchess[x][y - 1].equals("O")) {
                            coverchess[x][y - 1] = coverchess[x][y];
                            coverchess[x][y] = "1";
                            stop = true;
                            break;
                        }
                        System.out.println("請重新選擇移動方向:");
                        break;
                }
                if (stop) {
                    break;
                }
            }
        }
    }

    /**
     * 移動
     *
     * @param coverchess
     * @param chessboard
     */
    public static void move(String[][] coverchess, String[][] chessboard) {
        Scanner sc = new Scanner(System.in);
        System.out.println("選擇移動的棋子(x,y):");
        int x, y;
        while (true) {
            x = sc.nextInt();
            y = sc.nextInt();
            if (x >= 0 && x < 4 && y >= 0 && y < 8) {
                if (coverchess[x][y].equals("1")) {
                    System.out.println("選擇位置不是棋子");
                    return;
                } else if (coverchess[x][y].equals("O")) {
                    System.out.println("選擇位置還沒翻開");
                    return;
                }
                break;
            } else {
                System.out.println("選擇位置無棋子");
                return;
            }
        }
        System.out.println("選擇移動方向:");
        while (true) {
            System.out.println("1:上 2:下 3:左 4:右");
            int dir = sc.nextInt();
            boolean stop = false;
            switch (Direction.getvalues(dir)) {
                case 上:
                    if (x - 1 >= 0) {
                        coverchess[x - 1][y] = chessboard[x][y];
                        coverchess[x][y] = "1";
                        stop = true;
                        break;
                    }
                    System.out.println("請重新選擇移動方向:");
                    break;
                case 下:
                    if (x + 1 < 4) {
                        coverchess[x + 1][y] = chessboard[x][y];
                        coverchess[x][y] = "1";
                        stop = true;
                        break;
                    }
                    System.out.println("請重新選擇移動方向:");
                    break;
                case 右:
                    if (y + 1 < 8) {
                        coverchess[x][y + 1] = chessboard[x][y];
                        coverchess[x][y] = "1";
                        stop = true;
                        break;
                    }
                    System.out.println("請重新選擇移動方向:");
                    break;
                case 左:
                    if (y - 1 >= 0) {
                        coverchess[x][y - 1] = chessboard[x][y];
                        coverchess[x][y] = "1";
                        stop = true;
                        break;
                    }
                    System.out.println("請重新選擇移動方向:");
                    break;
            }
            if (stop) {
                break;
            }
        }
    }

    /**
     * 翻牌
     *
     * @param coverchess
     * @param chessboard
     */
    public static void openChess(String[][] coverchess, String[][] chessboard) {
        Scanner sc = new Scanner(System.in);
        System.out.println("輸入要翻得牌(x,y):");
        int x = sc.nextInt();
        int y = sc.nextInt();
        coverchess[x][y] = chessboard[x][y];
    }

    public static boolean isOpen() {
        return false;
    }

    /**
     * 交換元素
     *
     * @param arr
     * @param i
     * @param j
     * @return
     */
    public static String[] swap(String[] arr, int i, int j) {
        String tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        return arr;
    }

    /**
     * 洗牌
     *
     * @param num
     * @param chess
     */
    public static void washchess(int num, String chess[]) {
        for (int i = 0; i < 10000; i++) {
            int rand1 = (int) (Math.random() * num);
            int rand2 = (int) (Math.random() * num);
            chess = swap(chess, rand1, rand2);
        }
    }
}