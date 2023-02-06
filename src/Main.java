public class Main {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.newGame();
        while (true) {
            board.printBoard();
//            board.printAll();
            System.out.println("---------------------");
            int arr[] = board.selectChess(board.getCoverchess()); //選擇棋子 (滑鼠點擊動作)
            board.move(board.getCoverchess(), arr); //一般移動 覆蓋版
//            board.move(board.getChessboard(), arr); //一般移動 透明版
//            board.eat(board.getChessboard(),arr); //包飛吃
            if(board.getCoverchess()[arr[0]][arr[1]].equals("O")){ //如果棋子覆蓋狀態
                board.openChess(board.getCoverchess(),board.getChessboard(),arr);
            }else {
                board.move(board.getCoverchess(),arr); //移動
            }
        }
    }
}