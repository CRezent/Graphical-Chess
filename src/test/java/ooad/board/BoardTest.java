package ooad.board;

import org.junit.jupiter.api.Test;

import java.util.List;

import static ooad.board.Board.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {

    @Test
    void testCreateBoard(){
        Board board = new Board();

        boolean whiteTile = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (whiteTile) {
                assertEquals(TileType.WHITE, board.getTile(0, i).getTileType());
                assertEquals(TileType.WHITE, board.getTile(i, 0).getTileType());
            } else {
                assertEquals(TileType.BLACK, board.getTile(0, i).getTileType());
                assertEquals(TileType.BLACK, board.getTile(i, 0).getTileType());
            }
            whiteTile = !whiteTile;
        }

        List<List<Tile>> tiles = board.getTiles();
        assertEquals(BOARD_SIZE, tiles.size());
        assertEquals(BOARD_SIZE, tiles.get(0).size());
    }
}
