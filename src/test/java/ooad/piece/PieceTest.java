package ooad.piece;

import ooad.board.Tile;
import ooad.board.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
    @Test
    void testGetColor() {
        Piece piece = new Pawn(true);
        assertEquals(true, piece.getIsWhite());
    }
    @Test
    void testGetType() {
        Piece piece = new Pawn(true);
        assertEquals("pawn", piece.getType());
    }
    @Test
    void testSetAndGetTile() {
        Piece piece = new Pawn(true);
        Tile tile = new Tile(TileType.WHITE, 0, 0);

        piece.setTile(tile);
        assertEquals(tile, piece.getTile());
    }
    @Test
    void testFirstMove(){
        Piece piece = new Pawn(true);
        assertEquals(true, piece.getFirstMove());
        piece.setFirstMove(false);
        assertEquals(false, piece.getFirstMove());
    }
    @Test
    void testGetFileName(){
        Piece pawn = new Pawn(true);
        Piece king = new King(false);
        assertEquals("whitepawn.png", pawn.getFileName());
        assertEquals("blackking.png", king.getFileName());
    }

    @Test
    void testTakeAndGetIsTaken() {
        Piece pawn = new Pawn(true);
        assertFalse(pawn.getIsTaken());
        pawn.take();
        assertTrue(pawn.getIsTaken());
    }
}
