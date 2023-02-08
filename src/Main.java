import javax.swing.*;

public class Main  extends JFrame {
    private Panel myPanel;
    public Main()   {
        myPanel=new Panel();
        add(myPanel);
        setSize(myPanel.getChessBoard().getIconWidth(),myPanel.getChessBoard().getIconWidth());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
        ChessBoard board = new ChessBoard();
        board.newGame();
        while (true) {
            //測試
//            board.printAll();
//            System.out.println("---------------------");
//            int arr[] = board.selectChess(board.getCoverchess()); //選擇棋子 (滑鼠點擊動作)
//
//            if(board.getChessboard()[arr[0]][arr[1]].equals("包")||board.getChessboard()[arr[0]][arr[1]].equals("炮")) {
//                board.bao(board.getChessboard(),arr);
//            }else {
//                board.move(board.getChessboard(), arr); //一般移動 透明版
//            }

            //正式
            board.printBoard();
            System.out.println("---------------------");
            int arr[] = board.selectChess(board.getCoverchess()); //選擇棋子 (滑鼠點擊動作)
            if(board.getCoverchess()[arr[0]][arr[1]].equals("O")){ //如果棋子覆蓋狀態
                board.openChess(board.getCoverchess(),board.getChessboard(),arr); //翻開
            }else if(board.getCoverchess()[arr[0]][arr[1]].equals("包")||board.getChessboard()[arr[0]][arr[1]].equals("炮")){
                board.bao(board.getCoverchess(),arr); //包的移動
            }else {
                board.move(board.getCoverchess(),arr); //移動
            }
        }
    }
}