package ooad.game;

import ooad.board.Board;
import ooad.board.Tile;
import ooad.board.TileHighlightType;
import ooad.mock.ObserverMock;
import ooad.observer.EventType;
import ooad.piece.Piece;
import ooad.piece.PieceFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static ooad.board.Board.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void testCreateGame() {
        Game game = new Game(new PieceFactory());

        assertFalse(game.getGameIsOver());
        assertTrue(game.getWhiteTurn());
    }

    @Test
    void testGetBoard() {
        Game game = new Game(new PieceFactory());
        Board board = game.getBoard();

        assertEquals(BOARD_SIZE, board.getTiles().size());
        assertEquals(BOARD_SIZE, board.getTiles().get(0).size());
    }

    @Test
    void testPawnReachEnd() {
        Game game = new Game(new PieceFactory());
        Piece whitePawn = game.getBoard().getTiles().get(6).get(0).getPiece();

        Piece blackPawn = game.getBoard().getTiles().get(1).get(0).getPiece();
        Piece blackRook = game.getBoard().getTiles().get(0).get(0).getPiece();
        blackPawn.getTile().removePiece();
        blackPawn.setTile(null);
        blackPawn.take();
        blackRook.getTile().removePiece();
        blackRook.setTile(null);
        blackRook.take();

        for (int i = 0; i < 6; i++) {
            game.tileClicked(whitePawn.getTile().getRow(), whitePawn.getTile().getCol());
            game.tileClicked(whitePawn.getTile().getRow()-1, whitePawn.getTile().getCol());

            game.tileClicked(1, 7-i);
            game.tileClicked(2, 7-i);
        }

        Piece newQueen = game.getBoard().getTiles().get(0).get(0).getPiece();

        assertNotNull(newQueen);
        assertTrue(whitePawn.getIsTaken());
        assertFalse(newQueen.getIsTaken());
        assertTrue(newQueen.getIsWhite());
    }

    @Test
    void testClickNotSelectedOwn() {
        Game game = new Game(new PieceFactory());
        Piece whitePawn = game.getBoard().getTiles().get(6).get(0).getPiece();

        game.tileClicked(6, 0);

        assertEquals(TileHighlightType.YELLOW, whitePawn.getTile().getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(5).get(0).getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(4).get(0).getTileHighlightType());

        game.tileClicked(4, 0);
        game.tileClicked(1, 7);
        game.tileClicked(2, 7);
        game.tileClicked(4, 0);

        assertEquals(TileHighlightType.YELLOW, whitePawn.getTile().getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(3).get(0).getTileHighlightType());
        assertEquals(TileHighlightType.NONE, game.getBoard().getTiles().get(2).get(0).getTileHighlightType());
    }

    @Test
    void testClickNotSelectOpposing() {
        Game game = new Game(new PieceFactory());
        Piece blackPawn = game.getBoard().getTiles().get(1).get(0).getPiece();

        game.tileClicked(1, 0);

        assertEquals(TileHighlightType.NONE, blackPawn.getTile().getTileHighlightType());
        assertEquals(TileHighlightType.NONE, game.getBoard().getTiles().get(2).get(0).getTileHighlightType());
        assertEquals(TileHighlightType.NONE, game.getBoard().getTiles().get(3).get(0).getTileHighlightType());
    }

    @Test
    void testClickGreenToMove() {
        Game game = new Game(new PieceFactory());
        Piece whitePawn = game.getBoard().getTiles().get(6).get(0).getPiece();

        game.tileClicked(6, 0);

        assertEquals(TileHighlightType.YELLOW, whitePawn.getTile().getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(5).get(0).getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(4).get(0).getTileHighlightType());

        game.tileClicked(4, 0);

        assertEquals(4, whitePawn.getTile().getRow());
        assertEquals(0, whitePawn.getTile().getCol());
        assertEquals(TileHighlightType.NONE, whitePawn.getTile().getTileHighlightType());
    }

    @Test
    void testClickYellowToDeselect() {
        Game game = new Game(new PieceFactory());
        Piece whitePawn = game.getBoard().getTiles().get(6).get(0).getPiece();

        game.tileClicked(6, 0);

        assertEquals(TileHighlightType.YELLOW, whitePawn.getTile().getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(5).get(0).getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(4).get(0).getTileHighlightType());

        game.tileClicked(6, 0);

        assertEquals(6, whitePawn.getTile().getRow());
        assertEquals(0, whitePawn.getTile().getCol());
        assertEquals(TileHighlightType.NONE, whitePawn.getTile().getTileHighlightType());
    }

    @Test
    void testClickRedToTake() {
        Game game = new Game(new PieceFactory());
        Piece whitePawn = game.getBoard().getTiles().get(6).get(0).getPiece();
        Piece blackPawn = game.getBoard().getTiles().get(1).get(1).getPiece();

        game.tileClicked(6, 0);
        game.tileClicked(4, 0);
        game.tileClicked(1, 1);
        game.tileClicked(3, 1);
        game.tileClicked(4, 0);

        assertEquals(TileHighlightType.YELLOW, whitePawn.getTile().getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(3).get(0).getTileHighlightType());
        assertEquals(TileHighlightType.RED, blackPawn.getTile().getTileHighlightType());

        game.tileClicked(3, 1);

        assertTrue(blackPawn.getIsTaken());
        assertEquals(3, whitePawn.getTile().getRow());
        assertEquals(1, whitePawn.getTile().getCol());
    }

    @Test
    void testPlayToCheck() {
        Game game = new Game(new PieceFactory());
        ObserverMock mockObserver = new ObserverMock();
        game.attach(mockObserver, List.of(EventType.CHECK));

        Piece blackPawn = game.getBoard().getTiles().get(1).get(4).getPiece();
        Piece whitePawn = game.getBoard().getTiles().get(6).get(4).getPiece();
        blackPawn.getTile().removePiece();
        blackPawn.setTile(null);
        blackPawn.take();
        whitePawn.getTile().removePiece();
        whitePawn.setTile(null);
        whitePawn.take();

        game.tileClicked(7, 3);

        assertEquals(TileHighlightType.YELLOW, game.getBoard().getTiles().get(7).get(3).getTileHighlightType());
        assertEquals(TileHighlightType.GREEN, game.getBoard().getTiles().get(6).get(4).getTileHighlightType());

        game.tileClicked(6, 4);

        assertTrue(mockObserver.events.contains(EventType.CHECK));
        game.detach(mockObserver, List.of(EventType.CHECK));
    }

    @Test
    void testPlayToCheckmate() {
        Game game = new Game(new PieceFactory());
        ObserverMock mockObserver = new ObserverMock();
        game.attach(mockObserver, List.of(EventType.CHECKMATE));

        ArrayList<Tile> piecesToKeep = new ArrayList<>();
        piecesToKeep.add(game.getBoard().getTiles().get(0).get(4));
        piecesToKeep.add(game.getBoard().getTiles().get(1).get(0));
        piecesToKeep.add(game.getBoard().getTiles().get(7).get(0));
        piecesToKeep.add(game.getBoard().getTiles().get(7).get(3));
        piecesToKeep.add(game.getBoard().getTiles().get(7).get(4));
        piecesToKeep.add(game.getBoard().getTiles().get(7).get(7));

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                Piece piece = game.getBoard().getTiles().get(r).get(c).getPiece();
                if (piece != null && !piecesToKeep.contains(piece.getTile())) {
                    piece.getTile().removePiece();
                    piece.setTile(null);
                    piece.take();
                }
            }
        }

        game.tileClicked(7, 7);
        game.tileClicked(7, 5);

        game.tileClicked(1, 0);
        game.tileClicked(2, 0);

        game.tileClicked(7, 3);
        game.tileClicked(5, 5);

        game.tileClicked(2, 0);
        game.tileClicked(3, 0);

        game.tileClicked(7, 0);
        game.tileClicked(7, 3);

        game.tileClicked(3, 0);
        game.tileClicked(4, 0);

        game.tileClicked(5, 5);
        game.tileClicked(6, 4);

        assertTrue(mockObserver.events.contains(EventType.CHECKMATE));
        game.detach(mockObserver, List.of(EventType.CHECKMATE));
    }

    @Test
    void testPlayToStalemate() {
        Game game = new Game(new PieceFactory());
        ObserverMock mockObserver = new ObserverMock();
        game.attach(mockObserver, List.of(EventType.STALEMATE));

        ArrayList<Tile> piecesToKeep = new ArrayList<>();
        piecesToKeep.add(game.getBoard().getTiles().get(0).get(4));
        piecesToKeep.add(game.getBoard().getTiles().get(1).get(0));
        piecesToKeep.add(game.getBoard().getTiles().get(7).get(0));
        piecesToKeep.add(game.getBoard().getTiles().get(7).get(3));
        piecesToKeep.add(game.getBoard().getTiles().get(7).get(4));
        piecesToKeep.add(game.getBoard().getTiles().get(7).get(7));

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                Piece piece = game.getBoard().getTiles().get(r).get(c).getPiece();
                if (piece != null && !piecesToKeep.contains(piece.getTile())) {
                    piece.getTile().removePiece();
                    piece.setTile(null);
                    piece.take();
                }
            }
        }

        Piece blackPawn = game.getBoard().getTiles().get(1).get(0).getPiece();

        game.tileClicked(7, 7);
        game.tileClicked(7, 5);

        game.tileClicked(1, 0);
        game.tileClicked(2, 0);

        game.tileClicked(7, 3);
        game.tileClicked(4, 6);

        game.tileClicked(2, 0);
        game.tileClicked(3, 0);

        game.tileClicked(7, 0);
        game.tileClicked(7, 3);

        game.tileClicked(3, 0);
        game.tileClicked(4, 0);

        blackPawn.getTile().removePiece();
        blackPawn.setTile(null);
        blackPawn.take();

        game.tileClicked(4, 6);
        game.tileClicked(1, 6);

        assertTrue(mockObserver.events.contains(EventType.STALEMATE));
        game.detach(mockObserver, List.of(EventType.STALEMATE));
    }
}
