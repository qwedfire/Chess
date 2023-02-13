import javax.swing.*;
//1.Rule.compareB 須改

public class Main extends JFrame {
    private static Panel myPanel;
    public Main() {
        super("象棋遊戲");
        myPanel = new Panel();
        add(myPanel);
        setSize(Resource.pictures.get("棋盤").getIconWidth(), Resource.pictures.get("棋盤").getIconHeight());
        setLocation(700, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

//    public static void main(String[] args) {
//        new Main();
//        while (true) {
//            測試
//            myPanel.getBoard().printAll();
//            System.out.println("---------------------");
//            int arr[] = myPanel.getBoard().selectChess(myPanel.getBoard().getCoverchess()); //選擇棋子 (滑鼠點擊動作)
//
//            if(myPanel.getBoard().getChessboard()[arr[0]][arr[1]].equals("包")||myPanel.getBoard().getChessboard()[arr[0]][arr[1]].equals("炮")) {
//                myPanel.getBoard().bao(myPanel.getBoard().getChessboard(),arr);
//            }else {
//                myPanel.getBoard().move(myPanel.getBoard().getChessboard(), arr); //一般移動 透明版
//            }
//
//            正式
//            myPanel.getBoard().printBoard();
//            System.out.println("---------------------");
//            int arrr[] = myPanel.getBoard().selectChess(myPanel.getBoard().getCoverchess()); //選擇棋子 (滑鼠點擊動作)
//            if(myPanel.getBoard().getCoverchess()[arr[0]][arr[1]].equals("O")){ //如果棋子覆蓋狀態
//                myPanel.getBoard().openChess(myPanel.getBoard().getCoverchess(),myPanel.getBoard().getChessboard(),arr); //翻開
//            }else if(myPanel.getBoard().getCoverchess()[arr[0]][arr[1]].equals("包")||myPanel.getBoard().getChessboard()[arr[0]][arr[1]].equals("炮")){
//                myPanel.getBoard().bao(myPanel.getBoard().getCoverchess(),arr); //包的移動
//            }else {
//                myPanel.getBoard().move(myPanel.getBoard().getCoverchess(),arr); //移動
//            }
//        }
//    }
}