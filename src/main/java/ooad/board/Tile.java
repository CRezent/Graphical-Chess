package ooad.board;

import ooad.piece.Piece;

public class Tile {
    private final TileType tileType;
    private TileHighlightType tileHighlightType;
    private int row;
    private int col;
    private Piece piece;

    public Tile(TileType tileType, int row, int col) {
        this.tileType = tileType;
        this.tileHighlightType = TileHighlightType.NONE;
        this.piece = null;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public TileType getTileType() {
        return tileType;
    }
    public TileHighlightType getTileHighlightType() {
        return tileHighlightType;
    }
    public Piece getPiece() {
        return piece;
    }

    public void setCol(int col) {
        this.col = col;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setTileHighlightType(TileHighlightType tileHighlightType) {
        this.tileHighlightType = tileHighlightType;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece(){
        this.piece = null;
    }
}
