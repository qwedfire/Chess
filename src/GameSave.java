import java.io.Serializable;

public class GameSave implements Serializable {
    static final long serialVersionUID = 42L;
    private ChessBoard chessBoard;
    private Panel panel;
    public GameSave(ChessBoard chessBoard,Panel panel){
        this.chessBoard=chessBoard;
        this.panel=panel;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

}
