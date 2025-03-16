package ooad.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ooad.board.Tile;
import ooad.game.Game;
import ooad.mock.ObserverMock;
import ooad.observer.EventType;
import ooad.piece.Piece;
import ooad.piece.PieceFactory;

import java.util.ArrayList;
import java.util.List;

import static ooad.board.Board.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CucumberTest {
    Game game;
    ObserverMock mockObserver;

    @Given("I have a new game of chess")
    public void iHaveANewGameOfChess() {
        game = new Game(new PieceFactory());
        mockObserver = new ObserverMock();
        game.attach(mockObserver, List.of(EventType.ALL));
    }

    @When("I play the game to a check")
    public void iPlayTheGameToACheck() {
        Piece blackPawn = game.getBoard().getTiles().get(1).get(4).getPiece();
        Piece whitePawn = game.getBoard().getTiles().get(6).get(4).getPiece();
        blackPawn.getTile().removePiece();
        blackPawn.setTile(null);
        blackPawn.take();
        whitePawn.getTile().removePiece();
        whitePawn.setTile(null);
        whitePawn.take();

        game.tileClicked(7, 3);
        game.tileClicked(6, 4);
    }

    @When("I play the game to a checkmate")
    public void iPlayTheGameToACheckmate() {
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
    }

    @When("I move a pawn")
    public void iMoveAPawn() {
        game.tileClicked(6, 0);
        game.tileClicked(4, 0);
    }

    @When("I move opposing pawns and capture the black one")
    public void iMoveOpposingPawnsAndCaptureTheBlackOne() {
        game.tileClicked(6, 0);
        game.tileClicked(4, 0);

        game.tileClicked(1, 1);
        game.tileClicked(3, 1);

        game.tileClicked(4, 0);
        game.tileClicked(3, 1);
    }

    @And("a white piece should threaten the black king")
    public void aWhitePieceShouldThreatenTheBlackKing() {
        Piece whiteQueen = game.getBoard().getTile(6, 4).getPiece();
        Piece blackKing = game.getBoard().getTile(0, 4).getPiece();

        boolean queenMoveOnKingTile = false;
        for (Tile move : whiteQueen.getValidMoves(game)){
            if (move == blackKing.getTile()) {
                queenMoveOnKingTile = true;
                break;
            }
        }
        assertTrue(queenMoveOnKingTile);
    }

    @And("the black king should have no valid moves")
    public void theBlackKingShouldHaveNoValidMoves() {
        Piece blackKing = game.getBoard().getTile(0, 4).getPiece();
//        TODO: currently fails because getValidMoves doesn't take check into account
        assertEquals(0, blackKing.getValidMoves(game).size());
    }

    @Then("I should be notified of a check")
    public void iShouldBeNotifiedOfACheck() {
        assertTrue(mockObserver.events.contains(EventType.CHECK));
    }

    @Then("I should be notified of a checkmate")
    public void iShouldBeNotifiedOfACheckmate() {
        assertTrue(mockObserver.events.contains(EventType.CHECKMATE));
    }

    @Then("I should be notified of a piece move")
    public void iShouldBeNotifiedOfAPieceMove() {
        assertTrue(mockObserver.events.contains(EventType.PIECE_MOVED));
    }

    @Then("I should be notified of a piece taken")
    public void iShouldBeNotifiedOfAPieceTaken() {
        assertTrue(mockObserver.events.contains(EventType.PIECE_TAKEN));
    }

    @After()
    public void cleanup() {
        game.detach(mockObserver, List.of(EventType.ALL));
    }
}
