package ooad.game;

import ooad.board.Board;
import ooad.board.Tile;
import ooad.piece.Piece;

public interface IChessGame {
    Board getBoard();
    boolean pieceMoveResultsInCheck(Piece piece, Tile move, boolean checkWhiteKing);
    void tileClicked(int row, int col);
}
