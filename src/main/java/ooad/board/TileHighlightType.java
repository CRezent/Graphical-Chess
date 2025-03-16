package ooad.board;

public enum TileHighlightType {
    NONE,               // nothing special about this tile
    GREEN,              // signifies tiles the selected piece can move to
    YELLOW,             // signifies tile of selected piece
    RED                 // signifies tiles where selected piece can move to and take an enemy piece
}
