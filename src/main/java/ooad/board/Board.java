package ooad.board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int BOARD_SIZE = 8;

    private ArrayList<ArrayList<Tile>> tiles;

    public Board() {
        initTiles();
    }

    private void initTiles() {
        this.tiles = new ArrayList<>();
        boolean whiteTile = true;

        for (int r = 0; r < BOARD_SIZE; r++) {
            this.tiles.add(new ArrayList<>());

            for (int c = 0; c < BOARD_SIZE; c++) {
                this.tiles.get(r).add(new Tile(whiteTile ? TileType.WHITE : TileType.BLACK, r, c));
                if (c < BOARD_SIZE -1) {
                    whiteTile = !whiteTile;
                }
            }
        }
    }

    public Tile getTile(int row, int col) {
        return tiles.get(row).get(col);
    }

    public List<List<Tile>> getTiles() {
        return List.copyOf(tiles);
    }
}
