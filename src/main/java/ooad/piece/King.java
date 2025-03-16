package ooad.piece;

import ooad.board.Board;
import ooad.board.Tile;
import ooad.game.IChessGame;

import java.util.ArrayList;
import java.util.Objects;

import static ooad.board.Board.BOARD_SIZE;

public class King extends Piece {
    private static final String DEFAULT_KING_TYPE = "king";
    private boolean kingSideCastle = false;
    private boolean queenSideCastle = false;

    public King(Boolean isWhite) {
        this(DEFAULT_KING_TYPE, isWhite);
    }

    public King(String type, Boolean isWhite) {
        super(type, isWhite);
    }

    private ArrayList<Tile> getPotentialMoves(IChessGame game) {
        Board board = game.getBoard();
        ArrayList<Tile> potentialMoves = new ArrayList<>();
        int row = this.getTile().getRow();
        int col = this.getTile().getCol();

        if (row - 1 >= 0 && col - 1 >= 0){
            if (board.getTile(row - 1, col - 1).getPiece() != null) {
                if (board.getTile(row - 1, col - 1).getPiece().getIsWhite() != this.getIsWhite()) {
                    potentialMoves.add(board.getTile(row - 1, col - 1));
                }
            } else {
                potentialMoves.add(board.getTile(row - 1, col - 1));
            }
        }

        if (row - 1 >= 0 && col + 1 < BOARD_SIZE){
            if (board.getTile(row - 1, col + 1).getPiece() != null) {
                if (board.getTile(row - 1, col + 1).getPiece().getIsWhite() != this.getIsWhite()) {
                    potentialMoves.add(board.getTile(row - 1, col + 1));
                }
            } else {
                potentialMoves.add(board.getTile(row - 1, col + 1));
            }
        }

        if (row + 1 < BOARD_SIZE && col - 1 >= 0){
            if (board.getTile(row + 1, col - 1).getPiece() != null) {
                if (board.getTile(row + 1, col - 1).getPiece().getIsWhite() != this.getIsWhite()) {
                    potentialMoves.add(board.getTile(row + 1, col - 1));
                }
            } else {
                potentialMoves.add(board.getTile(row + 1, col - 1));
            }
        }

        if (row + 1 < BOARD_SIZE && col + 1 < BOARD_SIZE){
            if (board.getTile(row + 1, col + 1).getPiece() != null) {
                if (board.getTile(row + 1, col + 1).getPiece().getIsWhite() != this.getIsWhite()) {
                    potentialMoves.add(board.getTile(row + 1, col + 1));
                }
            } else {
                potentialMoves.add(board.getTile(row + 1, col + 1));
            }
        }

        if (row - 1 >= 0){
            if (board.getTile(row - 1, col).getPiece() != null) {
                if (board.getTile(row - 1, col).getPiece().getIsWhite() != this.getIsWhite()) {
                    potentialMoves.add(board.getTile(row - 1, col));
                }
            } else {
                potentialMoves.add(board.getTile(row - 1, col));
            }
        }

        if (row + 1 < BOARD_SIZE){
            if (board.getTile(row + 1, col).getPiece() != null) {
                if (board.getTile(row + 1, col).getPiece().getIsWhite() != this.getIsWhite()) {
                    potentialMoves.add(board.getTile(row + 1, col));
                }
            } else {
                potentialMoves.add(board.getTile(row + 1, col));
            }
        }

        if (col - 1 >= 0){
            if (board.getTile(row, col - 1).getPiece() != null) {
                if (board.getTile(row, col - 1).getPiece().getIsWhite() != this.getIsWhite()) {
                    potentialMoves.add(board.getTile(row, col - 1));
                }
            } else {
                potentialMoves.add(board.getTile(row, col - 1));
            }
        }

        if (col + 1 < BOARD_SIZE){
            if (board.getTile(row, col + 1).getPiece() != null) {
                if (board.getTile(row, col + 1).getPiece().getIsWhite() != this.getIsWhite()) {
                    potentialMoves.add(board.getTile(row, col + 1));
                }
            } else {
                potentialMoves.add(board.getTile(row, col + 1));
            }
        }
        return potentialMoves;
    }

    @Override
    public ArrayList<Tile> getThreateningMoves(IChessGame game) {
        Board board = game.getBoard();
        ArrayList<Tile> threateningMoves = new ArrayList<>();
        ArrayList<Tile> potentialMoves = this.getPotentialMoves(game);
        ArrayList<Tile> threatenedTiles = getOpponentMoves(game);

        // Get opponent piece possible move set
        boolean addTile = true;
        for (Tile potential : potentialMoves){
            for (Tile threatened : threatenedTiles){
                if (potential == threatened){
                    addTile = false;
                    break;
                }
            }
            if (addTile){
                threateningMoves.add(potential);
            }
            addTile = true;
        }
        return threateningMoves;
    }

    private ArrayList<Tile> getOpponentMoves(IChessGame game) {
        Board board = game.getBoard();
        ArrayList<Tile> threatenedTiles = new ArrayList<>();
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (board.getTile(r, c).getPiece() != null) {
                    if (board.getTile(r, c).getPiece().getIsWhite() != this.isWhite) {
                        if (Objects.equals(board.getTile(r, c).getPiece().getType(), "king")) {
                            King opponentKing = (King) board.getTile(r, c).getPiece();
                            threatenedTiles.addAll(opponentKing.getPotentialMoves(game));
                        }
                        else{
                            threatenedTiles.addAll(board.getTile(r, c).getPiece().getThreateningMoves(game));
                        }
                    }
                }
            }
        }
        return threatenedTiles;
    }

    @Override
    public ArrayList<Tile> getNonThreateningMoves(IChessGame game) {
        Board board = game.getBoard();
        ArrayList<Tile> nonThreateningMoves = new ArrayList<>();

        if (this.getFirstMove()){
            Tile kingTile = this.getTile();
            Tile kingSideTile1 = board.getTile(this.getTile().getRow(), this.getTile().getCol() + 1);
            Tile kingSideTile2 = board.getTile(this.getTile().getRow(), this.getTile().getCol() + 2);
            Tile kingSideRook = board.getTile(this.getTile().getRow(), this.getTile().getCol() + 3);
            Tile queenSideTile1 = board.getTile(this.getTile().getRow(), this.getTile().getCol() - 1);
            Tile queenSideTile2 = board.getTile(this.getTile().getRow(), this.getTile().getCol() - 2);
            Tile queenSideTile3 = board.getTile(this.getTile().getRow(), this.getTile().getCol() - 3);
            Tile queenSideRook = board.getTile(this.getTile().getRow(), this.getTile().getCol() - 4);

            // Get opponent piece possible move set
            ArrayList<Tile> threatenedTiles = getOpponentMoves(game);

            boolean threatenedKingSide = false;
            for (Tile tile : threatenedTiles) {
                if (tile == kingTile
                        || tile == kingSideTile1
                        || tile == kingSideTile2) {
                    threatenedKingSide = true;
                    break;
                }
            }
            if (this.getFirstMove()
                    && kingSideTile1.getPiece() == null
                    && kingSideTile2.getPiece() == null
                    && kingSideRook.getPiece() != null
                    && Objects.equals(kingSideRook.getPiece().getType(), "rook")
                    && kingSideRook.getPiece().getFirstMove()
                    && !threatenedKingSide) {
                nonThreateningMoves.add(board.getTile(this.getTile().getRow(), this.getTile().getCol() + 2));
                this.setKingSideCastle(true);
            }

            boolean threatenedQueenSide = false;
            for (Tile tile : threatenedTiles) {
                if (tile == kingTile
                        || tile == queenSideTile1
                        || tile == queenSideTile2) {
                    threatenedQueenSide = true;
                    break;
                }
            }
            if (this.getFirstMove()
                    && queenSideTile1.getPiece() == null
                    && queenSideTile2.getPiece() == null
                    && queenSideTile3.getPiece() == null
                    && queenSideRook.getPiece() != null
                    && Objects.equals(queenSideRook.getPiece().getType(), "rook")
                    && queenSideRook.getPiece().getFirstMove()
                    && !threatenedQueenSide) {
                nonThreateningMoves.add(board.getTile(this.getTile().getRow(), this.getTile().getCol() - 2));
                this.setQueenSideCastle(true);
            }
        }
        return nonThreateningMoves;
    }

    public boolean getKingSideCastle(){ return this.kingSideCastle; }

    public boolean getQueenSideCastle(){ return this.queenSideCastle; }

    public void setKingSideCastle(boolean setTo){ this.kingSideCastle = setTo; }

    public void setQueenSideCastle(boolean setTo){ this.queenSideCastle = setTo; }
}
