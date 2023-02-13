import java.util.HashMap;

public class Rule {
    public final static HashMap<String, Integer> chessNum = new HashMap<>() {
        {
            put("兵", 0);
            put("卒", 0);
            put("炮", 1);
            put("包", 1);
            put("傌", 2);
            put("馬", 2);
            put("俥", 3);
            put("車", 3);
            put("相", 4);
            put("象", 4);
            put("仕", 5);
            put("士", 5);
            put("帥", 6);
            put("將", 6);
        }
    }; //判斷數字大小用
    public final static HashMap<String, Integer> chessRed = new HashMap<>() {
        {
            put("O", -1);
            put("兵", 0);
            put("炮", 1);
            put("傌", 2);
            put("俥", 3);
            put("相", 4);
            put("仕", 5);
            put("帥", 6);
        }
    }; //判斷紅棋
    public final static HashMap<String, Integer> chessBlack = new HashMap<>() {
        {
            put("O", -1);
            put("卒", 0);
            put("包", 1);
            put("馬", 2);
            put("車", 3);
            put("象", 4);
            put("士", 5);
            put("將", 6);
        }
    }; //判斷黑棋

    /**
     * 比較棋子大小
     *
     * @param o1
     * @param o2
     * @return
     */
    public static boolean compare(String o1, String o2) { //要移動的棋 將被吃掉的棋
        if (chessRed.containsKey(o1) && chessRed.containsKey(o2)) { //如果都是紅棋不能互吃
            System.out.println("同隊(o1" + o1 + "o2" + o2 + ")");
            return false;
        }
        if (chessBlack.containsKey(o1) && chessBlack.containsKey(o2)) { //如果都是黑棋不能互吃
            System.out.println("同隊(o1" + o1 + "o2" + o2 + ")");
            return false;
        }
        if (chessNum.get(o1) > chessNum.get(o2)) { //一般大小 將 兵
            if (chessNum.get(o1) == 6 && chessNum.get(o2) == 0) { //將 兵規則
                return false;
            } else if (chessNum.get(o1) == 1 && chessNum.get(o2) == 0) { //包 兵規則(普通移動)
                System.out.println("包不能直接吃");
                return false;
            } else {
                return true;
            }
        } else if (chessNum.get(o1) == chessNum.get(o2)) {
            return true;
        } else if (chessNum.get(o1) < chessNum.get(o2)) {
            if (chessNum.get(o1) == 0 && chessNum.get(o2) == 6) { //兵 將規則
                return true;
            }
        }
        return false;
    }

    /**
     * 包吃規則(同隊不吃)
     *
     * @param o1
     * @param o2
     * @return
     */
    public static boolean compareBao(String o1, String o2) {
        if (chessRed.containsKey(o1) && chessRed.containsKey(o2)) { //如果都是紅棋不能互吃
            System.out.println("同隊(o1" + o1 + "o2" + o2 + ")");
            return false;
        }
        if (chessBlack.containsKey(o1) && chessBlack.containsKey(o2)) { //如果都是黑棋不能互吃
            System.out.println("同隊(o1" + o1 + "o2" + o2 + ")");
            return false;
        }
        return true;
    }
}