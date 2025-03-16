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

public class PawnTest {
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
        Piece pawn = new Pawn("pawn",false);
        assertNotNull(pawn);

        assertEquals("black pawn", pawn.toString());
        assertEquals(false, pawn.getIsWhite());
        assertEquals("pawn", pawn.getType());
    }

    @Test
    void testValidMovesWithOtherPieces(){
        Board board = game.getBoard();
        // check white pieces
        Piece pawn1 = new Pawn(true);
        Piece knight1 = new Knight(true);
        Piece bishop1 = new Bishop(false);

        // check first moves
        board.getTile(6,3).setPiece(pawn1);
        pawn1.setTile(board.getTile(6,3));
        board.getTile(4,3).setPiece(knight1);
        knight1.setTile(board.getTile(4,3));
        board.getTile(5,2).setPiece(bishop1);
        bishop1.setTile(board.getTile(5,2));
        ArrayList<Tile> validMoves1 = pawn1.getValidMoves(game);
        assertEquals(2, validMoves1.size());

        // check second moves
        pawn1.setFirstMove(false);

        validMoves1 = pawn1.getValidMoves(game);
        assertEquals(2, validMoves1.size());

        // check black pieces
        Piece pawn2 = new Pawn(false);
        Piece knight2 = new Knight(true);
        Piece bishop2 = new Bishop(true);

        // check first moves
        board.getTile(1,4).setPiece(pawn2);
        pawn2.setTile(board.getTile(1,4));
        board.getTile(2,5).setPiece(bishop2);
        bishop2.setTile(board.getTile(2,5));
        board.getTile(2,3).setPiece(knight2);
        knight2.setTile(board.getTile(2,3));

        ArrayList<Tile> validMoves2 = pawn2.getValidMoves(game);
        assertEquals(4, validMoves2.size());

        // check second moves
        pawn2.setFirstMove(false);

        validMoves2 = pawn2.getValidMoves(game);
        assertEquals(3, validMoves2.size());
    }

    @Test
    void testValidMovesEmptyBoard(){
        Board board = game.getBoard();
        // check white pieces
        Piece pawn1 = new Pawn(true);

        // check first moves
        board.getTile(6,3).setPiece(pawn1);
        pawn1.setTile(board.getTile(6,3));

        ArrayList<Tile> validMoves1 = pawn1.getValidMoves(game);
        assertEquals(2, validMoves1.size());

        // check second moves
        pawn1.setFirstMove(false);

        validMoves1 = pawn1.getValidMoves(game);
        assertEquals(1, validMoves1.size());

        // check black pieces
        Piece pawn2 = new Pawn(false);

        // check first moves
        board.getTile(1,4).setPiece(pawn2);
        pawn2.setTile(board.getTile(1,4));

        ArrayList<Tile> validMoves2 = pawn2.getValidMoves(game);
        assertEquals(2, validMoves2.size());

        // check second moves
        pawn2.setFirstMove(false);

        validMoves2 = pawn2.getValidMoves(game);
        assertEquals(1, validMoves2.size());
    }

    @Test
    void testValidMovesOppositeColorBlack(){
        Board board = game.getBoard();
        Piece pawn = new Pawn(false);
        Piece pawn1 = new Pawn(true);
        Piece pawn2 = new Pawn(true);
        Piece pawn3 = new Pawn(true);

        board.getTile(1,4).setPiece(pawn);
        pawn.setTile(board.getTile(1,4));
        board.getTile(2,4).setPiece(pawn1);
        pawn1.setTile(board.getTile(2,4));
        board.getTile(2,5).setPiece(pawn2);
        pawn2.setTile(board.getTile(2,5));
        board.getTile(2,3).setPiece(pawn3);
        pawn3.setTile(board.getTile(2,3));

        ArrayList<Tile> validMoves = pawn.getValidMoves(game);

        assertEquals(2, validMoves.size());

        validMoves = pawn.getValidMoves(game);

        assertEquals(2, validMoves.size());
    }

    @Test
    void testValidMovesOppositeColorWhite(){
        Board board = game.getBoard();
        Piece pawn = new Pawn(true);
        Piece pawn1 = new Pawn(false);
        Piece pawn2 = new Pawn(false);
        Piece pawn3 = new Pawn(false);

        board.getTile(6,4).setPiece(pawn);
        pawn.setTile(board.getTile(6,4));
        board.getTile(5,4).setPiece(pawn1);
        pawn1.setTile(board.getTile(5,4));
        board.getTile(5,5).setPiece(pawn2);
        pawn2.setTile(board.getTile(5,5));
        board.getTile(5,3).setPiece(pawn3);
        pawn3.setTile(board.getTile(5,3));

        ArrayList<Tile> validMoves = pawn.getValidMoves(game);

        assertEquals(2, validMoves.size());

        validMoves = pawn.getValidMoves(game);

        assertEquals(2, validMoves.size());
    }

    @Test
    void testValidMovesSameColorBlack(){
        Board board = game.getBoard();
        Piece pawn = new Pawn(false);
        Piece pawn1 = new Pawn(false);
        Piece pawn2 = new Pawn(false);
        Piece pawn3 = new Pawn(false);

        board.getTile(1,4).setPiece(pawn);
        pawn.setTile(board.getTile(1,4));
        board.getTile(2,4).setPiece(pawn1);
        pawn1.setTile(board.getTile(2,4));
        board.getTile(2,5).setPiece(pawn2);
        pawn2.setTile(board.getTile(2,5));
        board.getTile(2,3).setPiece(pawn3);
        pawn3.setTile(board.getTile(2,3));

        ArrayList<Tile> validMoves = pawn.getValidMoves(game);

        assertEquals(0, validMoves.size());

        validMoves = pawn.getValidMoves(game);

        assertEquals(0, validMoves.size());
    }

    @Test
    void testValidMovesSameColorWhite(){
        Board board = game.getBoard();
        Piece pawn = new Pawn(true);
        Piece pawn1 = new Pawn(true);
        Piece pawn2 = new Pawn(true);
        Piece pawn3 = new Pawn(true);

        board.getTile(6,4).setPiece(pawn);
        pawn.setTile(board.getTile(6,4));
        board.getTile(5,4).setPiece(pawn1);
        pawn1.setTile(board.getTile(5,4));
        board.getTile(5,5).setPiece(pawn2);
        pawn2.setTile(board.getTile(5,5));
        board.getTile(5,3).setPiece(pawn3);
        pawn3.setTile(board.getTile(5,3));

        ArrayList<Tile> validMoves = pawn.getValidMoves(game);

        assertEquals(0, validMoves.size());

        validMoves = pawn.getValidMoves(game);

        assertEquals(0, validMoves.size());
    }

    @Test
    void testThreateningAndNonThreateningMoves(){
        Board board = game.getBoard();
        Piece pawn = new Pawn(false);
        Piece knight = new Knight(true);
        Piece bishop = new Bishop(true);

        board.getTile(1,4).setPiece(pawn);
        pawn.setTile(board.getTile(1,4));
        board.getTile(2,5).setPiece(bishop);
        bishop.setTile(board.getTile(2,5));
        board.getTile(2,3).setPiece(knight);
        knight.setTile(board.getTile(2,3));

        // Test non-threatening moves
        ArrayList<Tile> validMoves = pawn.getNonThreateningMoves(game);
        assertEquals(2, validMoves.size());

        pawn.setFirstMove(false);
        validMoves = pawn.getNonThreateningMoves(game);
        assertEquals(1, validMoves.size());

        // Test threatening moves
        validMoves = pawn.getThreateningMoves(game);
        assertEquals(2, validMoves.size());
    }
}
