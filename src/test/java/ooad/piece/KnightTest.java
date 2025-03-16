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

public class KnightTest {
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
        Piece knight = new Knight(false);
        assertNotNull(knight);

        assertEquals("black knight", knight.toString());
        assertEquals(false, knight.getIsWhite());
        assertEquals("knight", knight.getType());
    }

    @Test
    void testValidMovesEmptyBoard(){
        Board board = game.getBoard();
        Piece knight = new Knight(true);

        board.getTile(0,0).setPiece(knight);
        knight.setTile(board.getTile(0,0));
        ArrayList<Tile> validMoves = knight.getValidMoves(game);
        assertEquals(2, validMoves.size());

        board.getTile(0,0).removePiece();
        board.getTile(4,4).setPiece(knight);
        knight.setTile(board.getTile(4,4));
        ArrayList<Tile> validMoves2 = knight.getValidMoves(game);
        assertEquals(8, validMoves2.size());
    }

    @Test
    void testValidMovesWithOtherPieces(){
        Board board = game.getBoard();
        Piece knight = new Knight(false);
        Piece pawn = new Pawn(false);
        Piece bishop = new Bishop(true);

        board.getTile(0,0).setPiece(knight);
        knight.setTile(board.getTile(0,0));
        board.getTile(2,1).setPiece(pawn);
        pawn.setTile(board.getTile(2,1));
        board.getTile(1,2).setPiece(bishop);
        bishop.setTile(board.getTile(1,2));

        ArrayList<Tile> validMoves = knight.getValidMoves(game);

        assertEquals(1, validMoves.size());

        board.getTile(0,0).removePiece();
        board.getTile(2,1).removePiece();
        board.getTile(1,2).removePiece();
        board.getTile(4,4).setPiece(knight);
        knight.setTile(board.getTile(4,4));
        board.getTile(2,3).setPiece(pawn);
        pawn.setTile(board.getTile(2,3));
        board.getTile(3,2).setPiece(bishop);
        bishop.setTile(board.getTile(3,2));

        ArrayList<Tile> validMoves2 = knight.getValidMoves(game);

        assertEquals(7, validMoves2.size());
    }

    @Test
    void testValidMovesOppositeColor(){
        Board board = game.getBoard();
        Piece knight = new Knight(false);
        Piece pawn1 = new Pawn(true);
        Piece pawn2 = new Pawn(true);
        Piece pawn3 = new Pawn(true);
        Piece pawn4 = new Pawn(true);
        Piece pawn5 = new Pawn(true);
        Piece pawn6 = new Pawn(true);
        Piece pawn7 = new Pawn(true);
        Piece pawn8 = new Pawn(true);

        board.getTile(4,4).setPiece(knight);
        knight.setTile(board.getTile(4,4));
        board.getTile(6,5).setPiece(pawn1);
        pawn1.setTile(board.getTile(6,5));
        board.getTile(6,3).setPiece(pawn2);
        pawn2.setTile(board.getTile(6,3));
        board.getTile(2,5).setPiece(pawn3);
        pawn3.setTile(board.getTile(2,5));
        board.getTile(2,3).setPiece(pawn4);
        pawn4.setTile(board.getTile(2,3));
        board.getTile(5,6).setPiece(pawn5);
        pawn5.setTile(board.getTile(5,6));
        board.getTile(3,6).setPiece(pawn6);
        pawn6.setTile(board.getTile(3,6));
        board.getTile(5,2).setPiece(pawn7);
        pawn7.setTile(board.getTile(5,2));
        board.getTile(3,2).setPiece(pawn8);
        pawn8.setTile(board.getTile(3,2));

        ArrayList<Tile> validMoves = knight.getValidMoves(game);

        assertEquals(8, validMoves.size());
    }

    @Test
    void testValidMovesSameColor(){
        Board board = game.getBoard();
        Piece knight = new Knight(false);
        Piece pawn1 = new Pawn(false);
        Piece pawn2 = new Pawn(false);
        Piece pawn3 = new Pawn(false);
        Piece pawn4 = new Pawn(false);
        Piece pawn5 = new Pawn(false);
        Piece pawn6 = new Pawn(false);
        Piece pawn7 = new Pawn(false);
        Piece pawn8 = new Pawn(false);

        board.getTile(4,4).setPiece(knight);
        knight.setTile(board.getTile(4,4));
        board.getTile(6,5).setPiece(pawn1);
        pawn1.setTile(board.getTile(6,5));
        board.getTile(6,3).setPiece(pawn2);
        pawn2.setTile(board.getTile(6,3));
        board.getTile(2,5).setPiece(pawn3);
        pawn3.setTile(board.getTile(2,5));
        board.getTile(2,3).setPiece(pawn4);
        pawn4.setTile(board.getTile(2,3));
        board.getTile(5,6).setPiece(pawn5);
        pawn5.setTile(board.getTile(5,6));
        board.getTile(3,6).setPiece(pawn6);
        pawn6.setTile(board.getTile(3,6));
        board.getTile(5,2).setPiece(pawn7);
        pawn7.setTile(board.getTile(5,2));
        board.getTile(3,2).setPiece(pawn8);
        pawn8.setTile(board.getTile(3,2));

        ArrayList<Tile> validMoves = knight.getValidMoves(game);

        assertEquals(0, validMoves.size());
    }
}
