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

public class RookTest {
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
        Piece rook = new Rook(true);
        assertNotNull(rook);

        assertEquals("white rook", rook.toString());
        assertEquals(true, rook.getIsWhite());
        assertEquals("rook", rook.getType());
    }

    @Test
    void testValidMovesEmptyBoard(){
        Board board = game.getBoard();
        Piece rook = new Rook(true);

        board.getTile(0,0).setPiece(rook);
        rook.setTile(board.getTile(0,0));
        ArrayList<Tile> validMoves = rook.getValidMoves(game);
        assertEquals(14, validMoves.size());

        board.getTile(0,0).removePiece();
        board.getTile(4,4).setPiece(rook);
        rook.setTile(board.getTile(4,4));
        ArrayList<Tile> validMoves2 = rook.getValidMoves(game);
        assertEquals(14, validMoves2.size());
    }

    @Test
    void testValidMovesWithOtherPieces(){
        Board board = game.getBoard();
        Piece rook = new Rook(false);
        Piece pawn = new Pawn(false);
        Piece bishop = new Bishop(true);

        board.getTile(0,0).setPiece(rook);
        rook.setTile(board.getTile(0,0));
        board.getTile(1,0).setPiece(pawn);
        pawn.setTile(board.getTile(1,0));
        board.getTile(0,7).setPiece(bishop);
        bishop.setTile(board.getTile(0,7));

        ArrayList<Tile> validMoves = rook.getValidMoves(game);

        assertEquals(7, validMoves.size());

        board.getTile(0,0).removePiece();
        board.getTile(1,0).removePiece();
        board.getTile(0,7).removePiece();
        board.getTile(4,4).setPiece(rook);
        rook.setTile(board.getTile(4,4));
        board.getTile(1,4).setPiece(pawn);
        pawn.setTile(board.getTile(1,4));
        board.getTile(4,2).setPiece(bishop);
        bishop.setTile(board.getTile(4,2));

        ArrayList<Tile> validMoves2 = rook.getValidMoves(game);

        assertEquals(10, validMoves2.size());
    }

    @Test
    void testValidMovesOppositeColor(){
        Board board = game.getBoard();
        Piece rook = new Rook(false);
        Piece pawn1 = new Pawn(true);
        Piece pawn2 = new Pawn(true);
        Piece pawn3 = new Pawn(true);
        Piece pawn4 = new Pawn(true);

        board.getTile(4,4).setPiece(rook);
        rook.setTile(board.getTile(4,4));
        board.getTile(3,4).setPiece(pawn1);
        pawn1.setTile(board.getTile(3,4));
        board.getTile(5,4).setPiece(pawn2);
        pawn2.setTile(board.getTile(5,4));
        board.getTile(4,3).setPiece(pawn3);
        pawn3.setTile(board.getTile(4,3));
        board.getTile(4,5).setPiece(pawn4);
        pawn4.setTile(board.getTile(4,5));

        ArrayList<Tile> validMoves = rook.getValidMoves(game);

        assertEquals(4, validMoves.size());
    }

    @Test
    void testValidMovesSameColor(){
        Board board = game.getBoard();
        Piece rook = new Rook(false);
        Piece pawn1 = new Pawn(false);
        Piece pawn2 = new Pawn(false);
        Piece pawn3 = new Pawn(false);
        Piece pawn4 = new Pawn(false);

        board.getTile(4,4).setPiece(rook);
        rook.setTile(board.getTile(4,4));
        board.getTile(3,4).setPiece(pawn1);
        pawn1.setTile(board.getTile(3,4));
        board.getTile(5,4).setPiece(pawn2);
        pawn2.setTile(board.getTile(5,4));
        board.getTile(4,3).setPiece(pawn3);
        pawn3.setTile(board.getTile(4,3));
        board.getTile(4,5).setPiece(pawn4);
        pawn4.setTile(board.getTile(4,5));

        ArrayList<Tile> validMoves = rook.getValidMoves(game);

        assertEquals(0, validMoves.size());
    }
}
