package ooad.piece;

import ooad.board.Board;
import ooad.board.Tile;
import ooad.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static ooad.board.Board.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BishopTest {
    Game game = new Game(new PieceFactory());

    @BeforeEach
    void resetBoard() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                Piece piece = game.getBoard().getTile(r, c).getPiece();
                if (piece != null) {
                    piece.getTile().removePiece();
                    piece.setTile(null);
                    piece.take();
                }
            }
        }
    }

    @Test
    void testConstructorAndToString() {
        Piece bishop = new Bishop(true);
        assertNotNull(bishop);

        assertEquals("white bishop", bishop.toString());
        assertEquals(true, bishop.getIsWhite());
        assertEquals("bishop", bishop.getType());
    }

    @Test
    void testValidMovesEmptyBoard(){
        Board board = game.getBoard();
        Piece bishop = new Bishop(true);

        board.getTile(0,0).setPiece(bishop);
        bishop.setTile(board.getTile(0,0));
        ArrayList<Tile> validMoves = bishop.getValidMoves(game);
        assertEquals(7, validMoves.size());

        board.getTile(0,0).removePiece();
        board.getTile(4,4).setPiece(bishop);
        bishop.setTile(board.getTile(4,4));
        ArrayList<Tile> validMoves2 = bishop.getValidMoves(game);
        assertEquals(13, validMoves2.size());
    }

    @Test
    void testValidMovesWithOtherPieces(){
        Board board = game.getBoard();
        Piece bishop = new Bishop(false);
        Piece pawn = new Pawn(false);
        Piece knight = new Knight(true);

        board.getTile(2,2).setPiece(bishop);
        bishop.setTile(board.getTile(2,2));
        board.getTile(1,1).setPiece(pawn);
        pawn.setTile(board.getTile(1,1));
        board.getTile(0,4).setPiece(knight);
        knight.setTile(board.getTile(0,4));

        ArrayList<Tile> validMoves = bishop.getValidMoves(game);

        assertEquals(9, validMoves.size());

        board.getTile(2,2).removePiece();
        board.getTile(0,4).removePiece();
        board.getTile(4,4).setPiece(bishop);
        bishop.setTile(board.getTile(4,4));
        board.getTile(2,6).setPiece(knight);
        knight.setTile(board.getTile(2,6));

        ArrayList<Tile> validMoves2 = bishop.getValidMoves(game);

        assertEquals(10, validMoves2.size());
    }

    @Test
    void testValidMovesOppositeColor(){
        Board board = game.getBoard();
        Piece bishop = new Bishop(false);
        Piece pawn5 = new Pawn(true);
        Piece pawn6 = new Pawn(true);
        Piece pawn7 = new Pawn(true);
        Piece pawn8 = new Pawn(true);

        board.getTile(4,4).setPiece(bishop);
        bishop.setTile(board.getTile(4,4));
        board.getTile(3,3).setPiece(pawn5);
        pawn5.setTile(board.getTile(3,3));
        board.getTile(5,5).setPiece(pawn6);
        pawn6.setTile(board.getTile(5,5));
        board.getTile(3,5).setPiece(pawn7);
        pawn7.setTile(board.getTile(3,5));
        board.getTile(5,3).setPiece(pawn8);
        pawn8.setTile(board.getTile(5,3));

        ArrayList<Tile> validMoves = bishop.getValidMoves(game);

        assertEquals(4, validMoves.size());
    }

    @Test
    void testValidMovesSameColor(){
        Board board = game.getBoard();
        Piece bishop = new Bishop(false);
        Piece pawn5 = new Pawn(false);
        Piece pawn6 = new Pawn(false);
        Piece pawn7 = new Pawn(false);
        Piece pawn8 = new Pawn(false);

        board.getTile(4,4).setPiece(bishop);
        bishop.setTile(board.getTile(4,4));
        board.getTile(3,3).setPiece(pawn5);
        pawn5.setTile(board.getTile(3,3));
        board.getTile(5,5).setPiece(pawn6);
        pawn6.setTile(board.getTile(5,5));
        board.getTile(3,5).setPiece(pawn7);
        pawn7.setTile(board.getTile(3,5));
        board.getTile(5,3).setPiece(pawn8);
        pawn8.setTile(board.getTile(5,3));

        ArrayList<Tile> validMoves = bishop.getValidMoves(game);

        assertEquals(0, validMoves.size());
    }
}
