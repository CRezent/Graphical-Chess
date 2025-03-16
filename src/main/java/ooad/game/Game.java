package ooad.game;

import ooad.board.Board;
import ooad.board.Tile;
import ooad.board.TileHighlightType;
import ooad.observer.EventBus;
import ooad.observer.EventType;
import ooad.observer.IObservable;
import ooad.observer.IObserver;
import ooad.piece.King;
import ooad.piece.Pawn;
import ooad.piece.Piece;
import ooad.piece.PieceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ooad.board.Board.BOARD_SIZE;

public class Game implements IObservable, IChessGame {
    private static final EventBus eventBus = EventBus.getInstance();
    private static final int BLACK_PAWN_START_ROW = 1;
    private static final int BLACK_PAWN_END_ROW = BOARD_SIZE-1;
    private static final int WHITE_PAWN_START_ROW = BOARD_SIZE-2;
    private static final int WHITE_PAWN_END_ROW = 0;

    private final PieceFactory pieceFactory;

    private final Board board;
    private final ArrayList<Piece> whitePieces;
    private final ArrayList<Piece> blackPieces;
    private King whiteKing;
    private King blackKing;

    private Piece selectedPiece;
    private boolean gameIsOver;
    private boolean whiteTurn;

    public Game(PieceFactory pieceFactory) {
        this.pieceFactory = pieceFactory;
        this.board = new Board();
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        initPieces();
        this.selectedPiece = null;
        this.gameIsOver = false;
        this.whiteTurn = true;
    }

//        For this let us define board index (0,0) to be the upper left of the board
//        Such that this will be the start square for the black rook and the black queen
//        will be at index (0,3).
    private void initPieces() {
//        BLACK PIECES
        Piece blackRook1 = this.pieceFactory.createRook(false);
        placePieceOnTile(blackRook1, WHITE_PAWN_END_ROW, 0);
        this.blackPieces.add(blackRook1);

        Piece blackKnight1 = this.pieceFactory.createKnight(false);
        placePieceOnTile(blackKnight1, WHITE_PAWN_END_ROW, 1);
        this.blackPieces.add(blackKnight1);

        Piece blackBishop1 = this.pieceFactory.createBishop(false);
        placePieceOnTile(blackBishop1, WHITE_PAWN_END_ROW, 2);
        this.blackPieces.add(blackBishop1);

        Piece blackQueen = this.pieceFactory.createQueen(false);
        placePieceOnTile(blackQueen, WHITE_PAWN_END_ROW, 3);
        this.blackPieces.add(blackQueen);

        this.blackKing = this.pieceFactory.createKing(false);
        placePieceOnTile(this.blackKing, WHITE_PAWN_END_ROW, 4);
        this.blackPieces.add(this.blackKing);

        Piece blackBishop2 = this.pieceFactory.createBishop(false);
        placePieceOnTile(blackBishop2, WHITE_PAWN_END_ROW, 5);
        this.blackPieces.add(blackBishop2);

        Piece blackKnight2 = this.pieceFactory.createKnight(false);
        placePieceOnTile(blackKnight2, WHITE_PAWN_END_ROW, 6);
        this.blackPieces.add(blackKnight2);

        Piece blackRook2 = this.pieceFactory.createRook(false);
        placePieceOnTile(blackRook2, WHITE_PAWN_END_ROW, 7);
        this.blackPieces.add(blackRook2);

        for (int c = 0; c < BOARD_SIZE; c++) {
            Piece pawn = this.pieceFactory.createPawn(false);
            placePieceOnTile(pawn, BLACK_PAWN_START_ROW, c);
            blackPieces.add(pawn);
        }

//        WHITE PIECES
        Piece whiteRook1 = this.pieceFactory.createRook(true);
        placePieceOnTile(whiteRook1, BLACK_PAWN_END_ROW, 0);
        this.whitePieces.add(whiteRook1);

        Piece whiteKnight1 = this.pieceFactory.createKnight(true);
        placePieceOnTile(whiteKnight1, BLACK_PAWN_END_ROW, 1);
        this.whitePieces.add(whiteKnight1);

        Piece whiteBishop1 = this.pieceFactory.createBishop(true);
        placePieceOnTile(whiteBishop1, BLACK_PAWN_END_ROW, 2);
        this.whitePieces.add(whiteBishop1);

        Piece whiteQueen = this.pieceFactory.createQueen(true);
        placePieceOnTile(whiteQueen, BLACK_PAWN_END_ROW, 3);
        this.whitePieces.add(whiteQueen);

        this.whiteKing = this.pieceFactory.createKing(true);
        placePieceOnTile(this.whiteKing, BLACK_PAWN_END_ROW, 4);
        this.whitePieces.add(this.whiteKing);

        Piece whiteBishop2 = this.pieceFactory.createBishop(true);
        placePieceOnTile(whiteBishop2, BLACK_PAWN_END_ROW, 5);
        this.whitePieces.add(whiteBishop2);

        Piece whiteKnight2 = this.pieceFactory.createKnight(true);
        placePieceOnTile(whiteKnight2, BLACK_PAWN_END_ROW, 6);
        this.whitePieces.add(whiteKnight2);

        Piece whiteRook2 = this.pieceFactory.createRook(true);
        placePieceOnTile(whiteRook2, BLACK_PAWN_END_ROW, 7);
        this.whitePieces.add(whiteRook2);

        for (int c = 0; c < BOARD_SIZE; c++) {
            Piece pawn = this.pieceFactory.createPawn(true);
            placePieceOnTile(pawn, WHITE_PAWN_START_ROW, c);
            whitePieces.add(pawn);
        }
    }

    private void placePieceOnTile(Piece piece, int r, int c) {
        Tile tile = this.board.getTile(r, c);
        tile.setPiece(piece);
        piece.setTile(tile);
        piece.placeOnBoard();
    }

    public boolean getGameIsOver() {
        return this.gameIsOver;
    }

    public boolean getWhiteTurn() {
        return this.whiteTurn;
    }

    @Override
    public void attach(IObserver observer, List<EventType> eventTypes) {
        for (EventType eventType : eventTypes) {
            eventBus.attach(observer, eventType);
        }
    }

    @Override
    public void detach(IObserver observer, List<EventType> eventTypes) {
        for (EventType eventType : eventTypes) {
            eventBus.detach(observer, eventType);
        }
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public void tileClicked(int row, int col) {
        if (this.gameIsOver) {
            return;
        }

        Optional<Piece> movedPiece = Optional.empty();
        final Tile tileClicked = board.getTile(row, col);
        switch (tileClicked.getTileHighlightType()) {

            case NONE:
//              tile is not highlighted and has piece of current turn team
//                  deselect piece and un-highlight board, then select new piece and highlights its moves
                deselectPieceAndResetBoardHighlight();
                final Optional<Piece> pieceToSelect = Optional.ofNullable(tileClicked.getPiece());
                if (pieceToSelect.isPresent() && pieceToSelect.get().getIsWhite() == this.whiteTurn) {
                    selectPiece(pieceToSelect.get());
                    highlightSelectedPieceMoves();
                }
                break;

            case GREEN:
//              tile is highlighted in green
//                  move selected piece to that tile and deselect it and un-highlight board
                movePiece(this.selectedPiece, tileClicked, false);
                movedPiece = Optional.of(this.selectedPiece);
                deselectPieceAndResetBoardHighlight();
                break;

            case YELLOW:
//              tile is highlighted in yellow
//                  deselect piece and un-highlight board
                deselectPieceAndResetBoardHighlight();
                break;

            case RED:
//              tile is highlighted in red
//                  take piece in that tile, then move selected piece to that tile and deselect it and un-highlight board
                takePiece(tileClicked.getPiece(), true);
                movePiece(this.selectedPiece, tileClicked, false);
                movedPiece = Optional.of(this.selectedPiece);
                deselectPieceAndResetBoardHighlight();
                break;
        }

        if (movedPiece.isPresent()) {
            checkPawnReachEnd(movedPiece.get());
            checkForGameOver();
            this.whiteTurn = !this.whiteTurn;
        }
    }

    private void checkPawnReachEnd(Piece piece) {
        Tile tile = piece.getTile();
        int targetRow = piece.getIsWhite() ? WHITE_PAWN_END_ROW : BLACK_PAWN_END_ROW;

        if (piece instanceof Pawn && tile.getRow() == targetRow) {
            Piece newQueen = this.pieceFactory.createQueen(piece.getIsWhite());

            if (piece.getIsWhite()) {
                this.whitePieces.add(newQueen);
            } else {
                this.blackPieces.add(newQueen);
            }

            this.takePiece(piece, false);
            placePieceOnTile(newQueen, tile.getRow(), tile.getCol());

            eventBus.postMessage(EventType.PAWN_REACH_END, piece + " converted to a Queen");
        }
    }

    private void deselectPieceAndResetBoardHighlight() {
        this.selectedPiece = null;
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                this.board.getTile(r, c).setTileHighlightType(TileHighlightType.NONE);
            }
        }
    }

    private void selectPiece(Piece piece) {
        this.selectedPiece = piece;
        this.selectedPiece.getTile().setTileHighlightType(TileHighlightType.YELLOW);
        eventBus.postMessage(EventType.PIECE_SELECTED, piece + " selected");
    }

    private void highlightSelectedPieceMoves() {
        for (Tile tile : this.selectedPiece.getValidMoves(this)) {
            Optional<Piece> piece = Optional.ofNullable(tile.getPiece());
            if (piece.isPresent() && this.selectedPiece.getIsWhite() != piece.get().getIsWhite()) {
                tile.setTileHighlightType(TileHighlightType.RED);
            } else {
                tile.setTileHighlightType(TileHighlightType.GREEN);
            }
        }
    }

    private void movePiece(Piece piece, Tile tile, boolean testMove) {
//      Check if the move is a castling move
        if (testMove) {
            piece.getTile().setPiece(null);
            piece.setTile(tile);
            tile.setPiece(piece);
        } else if(!castling(piece,tile)) {
            piece.getTile().setPiece(null);
            piece.setTile(tile);
            tile.setPiece(piece);
            piece.setFirstMove(false);
            eventBus.postMessage(EventType.PIECE_MOVED, piece + " moved");
        }
    }

    private boolean castling(Piece piece, Tile tile){
        if (piece == this.whiteKing){
            if (whiteKing.getKingSideCastle()){
                if (tile.getRow() == piece.getTile().getRow()
                        && tile.getCol() == piece.getTile().getCol() + 2){
//                  king side castle -> both the king and rook need to be moved
                    piece.getTile().setPiece(null);
                    piece.setTile(tile);
                    tile.setPiece(piece);

                    Piece rook = this.board.getTile(7,7).getPiece();
                    this.board.getTile(7,7).setPiece(null);
                    this.board.getTile(7,5).setPiece(rook);
                    rook.setTile(this.board.getTile(7,5));

                    whiteKing.setKingSideCastle(false);
                    whiteKing.setFirstMove(false);
                    rook.setFirstMove(false);
                    return true;
                }
            } else if (whiteKing.getQueenSideCastle()) {
                if (tile.getRow() == piece.getTile().getRow()
                        && tile.getCol() == piece.getTile().getCol() - 2) {
//                  queen side castle -> both the king and rook need to be moved
                    piece.getTile().setPiece(null);
                    piece.setTile(tile);
                    tile.setPiece(piece);

                    Piece rook = this.board.getTile(7, 0).getPiece();
                    this.board.getTile(7, 0).setPiece(null);
                    this.board.getTile(7, 3).setPiece(rook);
                    rook.setTile(this.board.getTile(7, 3));

                    whiteKing.setQueenSideCastle(false);
                    whiteKing.setFirstMove(false);
                    rook.setFirstMove(false);
                    return true;
                }
            }
        } else if (piece == blackKing){
            if (blackKing.getKingSideCastle()){
                if (tile.getRow() == piece.getTile().getRow()
                        && tile.getCol() == piece.getTile().getCol() + 2){
//                  king side castle -> both the king and rook need to be moved
                    piece.getTile().setPiece(null);
                    piece.setTile(tile);
                    tile.setPiece(piece);

                    Piece rook = this.board.getTile(0,7).getPiece();
                    this.board.getTile(0,7).setPiece(null);
                    this.board.getTile(0,5).setPiece(rook);
                    rook.setTile(this.board.getTile(0,5));

                    blackKing.setKingSideCastle(false);
                    blackKing.setFirstMove(false);
                    rook.setFirstMove(false);
                    return true;
                }
            } else if (blackKing.getQueenSideCastle()) {
                if (tile.getRow() == piece.getTile().getRow()
                        && tile.getCol() == piece.getTile().getCol() - 2) {
//                  queen side castle -> both the king and rook need to be moved
                    piece.getTile().setPiece(null);
                    piece.setTile(tile);
                    tile.setPiece(piece);

                    Piece rook = this.board.getTile(0, 0).getPiece();
                    this.board.getTile(0, 0).setPiece(null);
                    this.board.getTile(0, 3).setPiece(rook);
                    rook.setTile(this.board.getTile(0, 3));

                    blackKing.setQueenSideCastle(false);
                    blackKing.setFirstMove(false);
                    rook.setFirstMove(false);
                    return true;
                }
            }
        }
        return false;
    }

    private void takePiece(Piece pieceToTake, boolean announce) {
        pieceToTake.getTile().removePiece();
        pieceToTake.setTile(null);
        pieceToTake.take();
        if (announce) {
            eventBus.postMessage(EventType.PIECE_TAKEN, pieceToTake + " taken");
        }
    }

    private boolean kingsSpotThreatened(boolean checkWhiteKing) {
        Tile kingToCheckTile = checkWhiteKing ? this.whiteKing.getTile() : this.blackKing.getTile();
        ArrayList<Piece> enemyPieces = checkWhiteKing ? this.blackPieces : this.whitePieces;

        for (Piece piece : enemyPieces) {
            if (piece.getIsTaken()) {
                continue;
            }
            for (Tile move : piece.getThreateningMoves(this)) {
                if (move == kingToCheckTile) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean pieceCanMoveOutOfCheck(Piece piece, boolean checkWhiteKing) {
        for (Tile move : piece.getValidMoves(this)) {
            if (!pieceMoveResultsInCheck(piece, move, checkWhiteKing)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pieceMoveResultsInCheck(Piece piece, Tile move, boolean checkWhiteKing) {
        boolean resultsInCheck = false;
        Tile originalTile = piece.getTile();

        Optional<Piece> pieceOnMove = Optional.ofNullable(move.getPiece());
        pieceOnMove.ifPresent(pieceMove -> takePiece(pieceMove, false));

        movePiece(piece, move, true);
        if (kingsSpotThreatened(checkWhiteKing)) {
            resultsInCheck = true;
        }
        movePiece(piece, originalTile, true);

        pieceOnMove.ifPresent(pieceMove -> placePieceOnTile(pieceMove, move.getRow(), move.getCol()));

        return resultsInCheck;
    }

    private void checkForGameOver() {

//        Check if the king's current spot is under threat
        boolean kingThreatened = kingsSpotThreatened(!this.whiteTurn);

//        Check if any of the king's moves can be done without threat
        King kingToCheck = this.whiteTurn ? this.blackKing : this.whiteKing;
        boolean kingCanMove = pieceCanMoveOutOfCheck(kingToCheck, !this.whiteTurn);

//        Check if any other pieces of the king can cover his current position
        boolean piecesCanCoverKing = false;
        ArrayList<Piece> kingsPieces = this.whiteTurn ? this.blackPieces : this.whitePieces;
        for (Piece kingsPiece : kingsPieces) {
            if (kingsPiece.getIsTaken()) {
                continue;
            }
            piecesCanCoverKing = pieceCanMoveOutOfCheck(kingsPiece, !this.whiteTurn);
            if (piecesCanCoverKing) {
                break;
            }
        }

//        Using the combination of the above conditions, determine the status of the king
        if (kingThreatened && (kingCanMove || piecesCanCoverKing)) {
            String colorChecked = this.whiteTurn ? "black" : "white";
            eventBus.postMessage(EventType.CHECK, "Check on " + colorChecked + " king");
        } else if (kingThreatened) {
            this.gameIsOver = true;
            String colorWins = this.whiteTurn ? "White" : "Black";
            eventBus.postMessage(EventType.CHECKMATE, "Checkmate. " + colorWins + " wins");
        } else if (kingsPieces.stream().filter(piece -> !piece.getIsTaken()).count() <= 1 && !kingCanMove) {
            this.gameIsOver = true;
            eventBus.postMessage(EventType.STALEMATE, "Stalemate. It's a draw!");
        }
    }
}
