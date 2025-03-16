package ooad.piece;

import ooad.board.Board;
import ooad.board.Tile;
import ooad.game.IChessGame;

import java.util.ArrayList;

import static ooad.board.Board.BOARD_SIZE;

public class Pawn extends Piece{
    private static final String DEFAULT_PAWN_TYPE = "pawn";

    public Pawn(Boolean isWhite) { this(DEFAULT_PAWN_TYPE, isWhite); }

    public Pawn(String type, Boolean isWhite) {
        super(type, isWhite);
    }

    @Override
    public ArrayList<Tile> getThreateningMoves(IChessGame game) {
        Board board = game.getBoard();
        ArrayList<Tile> threateningMoves = new ArrayList<>();
        int row = this.getTile().getRow();
        int col = this.getTile().getCol();

        if (this.isWhite){
            if (row - 1 >= 0 && col - 1 >= 0
                    && board.getTile(row - 1, col - 1).getPiece() != null
                    && board.getTile(row - 1, col - 1).getPiece().getIsWhite() != this.getIsWhite()) {

                threateningMoves.add(board.getTile(row - 1, col - 1));
            }
            if (row - 1 >= 0 && col + 1 < BOARD_SIZE
                    && board.getTile(row - 1, col + 1).getPiece() != null
                    && board.getTile(row - 1, col + 1).getPiece().getIsWhite() != this.getIsWhite()) {

                threateningMoves.add(board.getTile(row - 1, col + 1));
            }
        }
        else {
            if (row + 1 < BOARD_SIZE && col - 1 >= 0
                    && board.getTile(row + 1, col - 1).getPiece() != null
                    && board.getTile(row + 1, col - 1).getPiece().getIsWhite() != this.getIsWhite()) {

                threateningMoves.add(board.getTile(row + 1, col - 1));
            }
            if (row + 1 < BOARD_SIZE && col + 1 < BOARD_SIZE
                    && board.getTile(row + 1, col + 1).getPiece() != null
                    && board.getTile(row + 1, col + 1).getPiece().getIsWhite() != this.getIsWhite()) {

                threateningMoves.add(board.getTile(row + 1, col + 1));
            }
        }
        return threateningMoves;
    }

    @Override
    public ArrayList<Tile> getNonThreateningMoves(IChessGame game) {
        Board board = game.getBoard();
        ArrayList<Tile> nonThreateningMoves = new ArrayList<>();
        int row = this.getTile().getRow();
        int col = this.getTile().getCol();

        if (this.isWhite){
            if (row - 1 >= 0
                    && board.getTile(row - 1, col).getPiece() == null) {

                nonThreateningMoves.add(board.getTile(row-1, col));

                if (row - 2 >= 0
                        && board.getTile(row - 2, col).getPiece() == null
                        && this.getFirstMove()){

                    nonThreateningMoves.add(board.getTile(row - 2, col));
                }
            }
        }
        else {
            if (row + 1 < BOARD_SIZE
                    && board.getTile(row + 1, col).getPiece() == null) {

                nonThreateningMoves.add(board.getTile(row + 1, col));

                if (row + 2 < BOARD_SIZE
                        && board.getTile(row + 2, col).getPiece() == null
                        && this.getFirstMove()) {

                    nonThreateningMoves.add(board.getTile(row + 2, col));
                }
            }
        }
        return nonThreateningMoves;
    }
}
