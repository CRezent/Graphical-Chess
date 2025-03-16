package ooad.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceFactoryTest {
    private final PieceFactory pieceFactory = new PieceFactory();
    @Test
    void testCreatePawn() {
        Pawn pawn = pieceFactory.createPawn(true);

        assertEquals("white pawn", pawn.toString());
        assertEquals(true, pawn.getIsWhite());
        assertEquals("pawn", pawn.getType());
    }
    @Test
    void testCreateRook() {
        Rook rook = pieceFactory.createRook(true);

        assertEquals("white rook", rook.toString());
        assertEquals(true, rook.getIsWhite());
        assertEquals("rook", rook.getType());
    }
    @Test
    void testCreateBishop() {
        Bishop bishop = pieceFactory.createBishop(true);

        assertEquals("white bishop", bishop.toString());
        assertEquals(true, bishop.getIsWhite());
        assertEquals("bishop", bishop.getType());
    }
    @Test
    void testCreateKnight() {
        Knight knight = pieceFactory.createKnight(true);

        assertEquals("white knight", knight.toString());
        assertEquals(true, knight.getIsWhite());
        assertEquals("knight", knight.getType());
    }
    @Test
    void testCreateQueen() {
        Queen queen = pieceFactory.createQueen(true);

        assertEquals("white queen", queen.toString());
        assertEquals(true, queen.getIsWhite());
        assertEquals("queen", queen.getType());
    }
    @Test
    void testCreateKing() {
        King king = pieceFactory.createKing(true);

        assertEquals("white king", king.toString());
        assertEquals(true, king.getIsWhite());
        assertEquals("king", king.getType());
    }

}
