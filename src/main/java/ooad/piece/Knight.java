package ooad.piece;

import ooad.board.Board;
import ooad.board.Tile;
import ooad.game.IChessGame;

import java.util.ArrayList;

import static ooad.board.Board.BOARD_SIZE;

public class Knight extends Piece{
    private static final String DEFAULT_KNIGHT_TYPE = "knight";

    public Knight(Boolean isWhite) {
        this(DEFAULT_KNIGHT_TYPE, isWhite);
    }

    public Knight(String type, Boolean isWhite) {
        super(type, isWhite);
    }

    @Override
    public ArrayList<Tile> getThreateningMoves(IChessGame game) {
        Board board = game.getBoard();
        ArrayList<Tile> threateningMoves = new ArrayList<>();
        int row = this.getTile().getRow();
        int col = this.getTile().getCol();

        int r = row + 2;
        int c = col + 1;

        if (r < BOARD_SIZE && c < BOARD_SIZE){
            if (board.getTile(r, c).getPiece() != null) {
                if (board.getTile(r, c).getPiece().getIsWhite() != this.getIsWhite()) {
                    threateningMoves.add(board.getTile(r, c));
                }
            } else {
                threateningMoves.add(board.getTile(r, c));
            }
        }

        r = row + 2;
        c = col - 1;

        if (r < BOARD_SIZE && c >= 0){
            if (board.getTile(r, c).getPiece() != null) {
                if (board.getTile(r, c).getPiece().getIsWhite() != this.getIsWhite()) {
                    threateningMoves.add(board.getTile(r, c));
                }
            } else {
                threateningMoves.add(board.getTile(r, c));
            }
        }

        r = row - 2;
        c = col + 1;

        if (r >= 0 && c < BOARD_SIZE){
            if (board.getTile(r, c).getPiece() != null) {
                if (board.getTile(r, c).getPiece().getIsWhite() != this.getIsWhite()) {
                    threateningMoves.add(board.getTile(r, c));
                }
            } else {
                threateningMoves.add(board.getTile(r, c));
            }
        }

        r = row - 2;
        c = col - 1;

        if (r >= 0 && c >= 0){
            if (board.getTile(r, c).getPiece() != null) {
                if (board.getTile(r, c).getPiece().getIsWhite() != this.getIsWhite()) {
                    threateningMoves.add(board.getTile(r, c));
                }
            } else {
                threateningMoves.add(board.getTile(r, c));
            }
        }

        r = row + 1;
        c = col + 2;

        if (r < BOARD_SIZE && c < BOARD_SIZE){
            if (board.getTile(r, c).getPiece() != null) {
                if (board.getTile(r, c).getPiece().getIsWhite() != this.getIsWhite()) {
                    threateningMoves.add(board.getTile(r, c));
                }
            } else {
                threateningMoves.add(board.getTile(r, c));
            }
        }

        r = row - 1;
        c = col + 2;

        if (r >= 0 && c < BOARD_SIZE) {
            if (board.getTile(r, c).getPiece() != null) {
                if (board.getTile(r, c).getPiece().getIsWhite() != this.getIsWhite()) {
                    threateningMoves.add(board.getTile(r, c));
                }
            } else {
                threateningMoves.add(board.getTile(r, c));
            }
        }

        r = row + 1;
        c = col - 2;

        if (r < BOARD_SIZE && c >= 0) {
            if (board.getTile(r, c).getPiece() != null) {
                if (board.getTile(r, c).getPiece().getIsWhite() != this.getIsWhite()) {
                    threateningMoves.add(board.getTile(r, c));
                }
            } else {
                threateningMoves.add(board.getTile(r, c));
            }
        }

        r = row - 1;
        c = col - 2;

        if (r >= 0 && c >= 0) {
            if (board.getTile(r, c).getPiece() != null) {
                if (board.getTile(r, c).getPiece().getIsWhite() != this.getIsWhite()) {
                    threateningMoves.add(board.getTile(r, c));
                }
            } else {
                threateningMoves.add(board.getTile(r, c));
            }
        }
        return threateningMoves;
    }
}
