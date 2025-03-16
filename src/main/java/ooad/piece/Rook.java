package ooad.piece;

import ooad.board.Board;
import ooad.board.Tile;
import ooad.game.IChessGame;

import java.util.ArrayList;

import static ooad.board.Board.BOARD_SIZE;

public class Rook extends Piece {
    private static final String DEFAULT_ROOK_TYPE = "rook";

    public Rook(Boolean isWhite) {
        this(DEFAULT_ROOK_TYPE, isWhite);
    }

    public Rook(String type, Boolean isWhite) {
        super(type, isWhite);
    }

    @Override
    public ArrayList<Tile> getThreateningMoves(IChessGame game) {
        Board board = game.getBoard();
        ArrayList<Tile> threateningMoves = new ArrayList<>();
        int row = this.getTile().getRow();
        int col = this.getTile().getCol();

        for (int r = row + 1; r < BOARD_SIZE; r++) {
            if (board.getTile(r, col).getPiece() != null) {
                if (board.getTile(r, col).getPiece().getIsWhite() == this.getIsWhite()){
                    break;
                }
                else {
                    threateningMoves.add(board.getTile(r, col));
                    break;
                }
            }
            else {
                threateningMoves.add(board.getTile(r, col));
            }
        }

        for (int r = row - 1; r >= 0; r--) {
            if (board.getTile(r, col).getPiece() != null) {
                if (board.getTile(r, col).getPiece().getIsWhite() == this.getIsWhite()) {
                    break;
                } else {
                    threateningMoves.add(board.getTile(r, col));
                    break;
                }
            } else {
                threateningMoves.add(board.getTile(r, col));
            }
        }

        for (int c = col + 1; c < BOARD_SIZE; c++){
            if (board.getTile(row, c).getPiece() != null){
                if (board.getTile(row, c).getPiece().getIsWhite() == this.getIsWhite()){
                    break;
                }
                else {
                    threateningMoves.add(board.getTile(row, c));
                    break;
                }
            }
            else {
                threateningMoves.add(board.getTile(row, c));
            }
        }

        for (int c = col - 1; c >= 0; c--) {
            if (board.getTile(row, c).getPiece() != null) {
                if (board.getTile(row, c).getPiece().getIsWhite() == this.getIsWhite()) {
                    break;
                } else {
                    threateningMoves.add(board.getTile(row, c));
                    break;
                }
            }
            else {
                threateningMoves.add(board.getTile(row, c));
            }
        }
        return threateningMoves;
    }
}
