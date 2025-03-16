package ooad.piece;

public class PieceFactory {
    public Pawn createPawn(final Boolean isWhite) {
        return new Pawn(isWhite);
    }
    public Rook createRook(final Boolean isWhite) {
        return new Rook(isWhite);
    }
    public Bishop createBishop(final Boolean isWhite) {
        return new Bishop(isWhite);
    }
    public Knight createKnight(final Boolean isWhite) {
        return new Knight(isWhite);
    }
    public Queen createQueen(final Boolean isWhite) {
        return new Queen(isWhite);
    }
    public King createKing(final Boolean isWhite) {
        return new King(isWhite);
    }
}
