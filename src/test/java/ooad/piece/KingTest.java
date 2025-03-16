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

public class KingTest {
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
        Piece king = new King(true);
        assertNotNull(king);

        assertEquals("white king", king.toString());
        assertEquals(true, king.getIsWhite());
        assertEquals("king", king.getType());
    }

    @Test
    void testValidMovesEmptyBoard(){
        Board board = game.getBoard();
        Piece king = new King(true);

        board.getTile(7,4).setPiece(king);
        king.setTile(board.getTile(7,4));
        ArrayList<Tile> validMoves = king.getValidMoves(game);
        assertEquals(5, validMoves.size());

        board.getTile(7,4).removePiece();
        board.getTile(4,4).setPiece(king);
        king.setTile(board.getTile(4,4));
        ArrayList<Tile> validMoves2 = king.getValidMoves(game);
        assertEquals(8, validMoves2.size());
    }

    @Test
    void testValidMovesWithOtherPieces(){
        Board board = game.getBoard();
        Piece king = new King(false);
        Piece pawn = new Pawn(false);
        Piece bishop = new Bishop(true);

        board.getTile(0,4).setPiece(king);
        king.setTile(board.getTile(0,4));
        board.getTile(1,4).setPiece(pawn);
        pawn.setTile(board.getTile(1,4));
        board.getTile(0,3).setPiece(bishop);
        bishop.setTile(board.getTile(0,3));

        ArrayList<Tile> validMoves = king.getValidMoves(game);

        assertEquals(4, validMoves.size());

        board.getTile(0,4).removePiece();
        board.getTile(1,4).removePiece();
        board.getTile(0,3).removePiece();
        board.getTile(4,4).setPiece(king);
        king.setTile(board.getTile(4,4));
        board.getTile(3,4).setPiece(pawn);
        pawn.setTile(board.getTile(3,4));
        board.getTile(5,3).setPiece(bishop);
        bishop.setTile(board.getTile(5,3));

        ArrayList<Tile> validMoves2 = king.getValidMoves(game);

        assertEquals(7, validMoves2.size());
    }

    @Test
    void testValidMovesOppositeColor(){
        Board board = game.getBoard();
        Piece king = new King(false);
        Piece pawn1 = new Pawn(true);
        Piece pawn2 = new Pawn(true);
        Piece pawn3 = new Pawn(true);
        Piece pawn4 = new Pawn(true);
        Piece pawn5 = new Pawn(true);
        Piece pawn6 = new Pawn(true);
        Piece pawn7 = new Pawn(true);
        Piece pawn8 = new Pawn(true);

        board.getTile(4,4).setPiece(king);
        king.setTile(board.getTile(4,4));
        board.getTile(3,4).setPiece(pawn1);
        pawn1.setTile(board.getTile(3,4));
        board.getTile(5,4).setPiece(pawn2);
        pawn2.setTile(board.getTile(5,4));
        board.getTile(4,3).setPiece(pawn3);
        pawn3.setTile(board.getTile(4,3));
        board.getTile(4,5).setPiece(pawn4);
        pawn4.setTile(board.getTile(4,5));
        board.getTile(3,3).setPiece(pawn5);
        pawn5.setTile(board.getTile(3,3));
        board.getTile(5,5).setPiece(pawn6);
        pawn6.setTile(board.getTile(5,5));
        board.getTile(3,5).setPiece(pawn7);
        pawn7.setTile(board.getTile(3,5));
        board.getTile(5,3).setPiece(pawn8);
        pawn8.setTile(board.getTile(5,3));

        ArrayList<Tile> validMoves = king.getValidMoves(game);

        assertEquals(8, validMoves.size());
    }

    @Test
    void testValidMovesSameColor(){
        Board board = game.getBoard();
        Piece king = new King(false);
        Piece pawn1 = new Pawn(false);
        Piece pawn2 = new Pawn(false);
        Piece pawn3 = new Pawn(false);
        Piece pawn4 = new Pawn(false);
        Piece pawn5 = new Pawn(false);
        Piece pawn6 = new Pawn(false);
        Piece pawn7 = new Pawn(false);
        Piece pawn8 = new Pawn(false);

        board.getTile(4,4).setPiece(king);
        king.setTile(board.getTile(4,4));
        board.getTile(3,4).setPiece(pawn1);
        pawn1.setTile(board.getTile(3,4));
        board.getTile(5,4).setPiece(pawn2);
        pawn2.setTile(board.getTile(5,4));
        board.getTile(4,3).setPiece(pawn3);
        pawn3.setTile(board.getTile(4,3));
        board.getTile(4,5).setPiece(pawn4);
        pawn4.setTile(board.getTile(4,5));
        board.getTile(3,3).setPiece(pawn5);
        pawn5.setTile(board.getTile(3,3));
        board.getTile(5,5).setPiece(pawn6);
        pawn6.setTile(board.getTile(5,5));
        board.getTile(3,5).setPiece(pawn7);
        pawn7.setTile(board.getTile(3,5));
        board.getTile(5,3).setPiece(pawn8);
        pawn8.setTile(board.getTile(5,3));

        ArrayList<Tile> validMoves = king.getValidMoves(game);

        assertEquals(0, validMoves.size());
    }

    @Test
    void testKingSideCastle () {
        Board board = game.getBoard();
        Piece whiteKing = new King(true);
        Piece whiteRook = new Rook(true);
        Piece blackKing = new King(false);
        Piece blackRook = new Rook(false);

        board.getTile(7,4).setPiece(whiteKing);
        whiteKing.setTile(board.getTile(7,4));
        board.getTile(7,7).setPiece(whiteRook);
        whiteRook.setTile(board.getTile(7,7));
        board.getTile(0,4).setPiece(blackKing);
        blackKing.setTile(board.getTile(0,4));
        board.getTile(0,7).setPiece(blackRook);
        blackRook.setTile(board.getTile(0,7));

        assertEquals(1, whiteKing.getNonThreateningMoves(game).size());
        assertEquals(7, whiteKing.getNonThreateningMoves(game).get(0).getRow());
        assertEquals(6, whiteKing.getNonThreateningMoves(game).get(0).getCol());

        board.getTile(7,4).removePiece();
        board.getTile(7,7).removePiece();
        whiteKing.setTile(board.getTile(7,6));
        board.getTile(7,6).setPiece(whiteKing);
        whiteRook.setTile(board.getTile(7,5));
        board.getTile(7,5).setPiece(whiteRook);

        assertEquals(0, blackKing.getNonThreateningMoves(game).size());
    }

    @Test
    void testQueenSideCastle () {
        Board board = game.getBoard();
        Piece whiteKing = new King(true);
        Piece whiteRook = new Rook(true);
        Piece blackKing = new King(false);
        Piece blackRook = new Rook(false);

        board.getTile(7,4).setPiece(whiteKing);
        whiteKing.setTile(board.getTile(7,4));
        board.getTile(7,0).setPiece(whiteRook);
        whiteRook.setTile(board.getTile(7,0));
        board.getTile(0,4).setPiece(blackKing);
        blackKing.setTile(board.getTile(0,4));
        board.getTile(0,0).setPiece(blackRook);
        blackRook.setTile(board.getTile(0,0));

        assertEquals(1, whiteKing.getNonThreateningMoves(game).size());
        assertEquals(7, whiteKing.getNonThreateningMoves(game).get(0).getRow());
        assertEquals(2, whiteKing.getNonThreateningMoves(game).get(0).getCol());

        board.getTile(7,4).removePiece();
        board.getTile(7,0).removePiece();
        whiteKing.setTile(board.getTile(7,2));
        board.getTile(7,2).setPiece(whiteKing);
        whiteRook.setTile(board.getTile(7,3));
        board.getTile(7,3).setPiece(whiteRook);

        assertEquals(0, blackKing.getNonThreateningMoves(game).size());
    }
}
