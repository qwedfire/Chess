public class Compare implements Comparable<String>{
    private String[] chessvalue = { "兵", "卒", "傌", "馬",  "俥", "車",
            "炮", "包", "相",  "象", "士",  "帥", "將"};
    @Override
    public int compareTo(String o) {
        for(int i=0;i<chessvalue.length;i++){
//            if(chessvalue[i])
        }
        if(o.equals("卒"))
            return 0;
        else return 1;
    }
    public boolean canMove(){

        return false;
    }

}
