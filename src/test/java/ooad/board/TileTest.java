package ooad.board;

import ooad.piece.Pawn;
import ooad.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TileTest {

    @Test
    void testCreateTile() {
        Tile tile = new Tile(TileType.WHITE, 1, 1);
        Piece pawn = new Pawn(false);

        assertEquals(1, tile.getRow());
        assertEquals(1, tile.getCol());
        assertEquals(TileType.WHITE, tile.getTileType());
        assertEquals(TileHighlightType.NONE, tile.getTileHighlightType());
        assertNull(tile.getPiece());

        tile.setPiece(pawn);
        tile.setTileHighlightType(TileHighlightType.RED);
        tile.setRow(2);
        tile.setCol(2);

        assertEquals(2, tile.getRow());
        assertEquals(2, tile.getCol());
        assertEquals(pawn, tile.getPiece());
        assertEquals(TileHighlightType.RED, tile.getTileHighlightType());

        tile.removePiece();

        assertNull(tile.getPiece());
    }
}
