package ooad.observer;

import ooad.game.Game;
import ooad.mock.ObserverMock;
import ooad.piece.PieceFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventBusTest {

    @Test
    void testEventBus() {
        Game game = new Game(new PieceFactory());
        ObserverMock observerMock = new ObserverMock();
        game.attach(observerMock, List.of(EventType.ALL));

        game.tileClicked(6, 0);
        game.tileClicked(4, 0);
        game.tileClicked(1, 1);
        game.tileClicked(3, 1);
        game.tileClicked(4, 0);
        game.tileClicked(3, 1);

        assertTrue(observerMock.events.stream().anyMatch(eventType -> eventType == EventType.PIECE_SELECTED));
        assertTrue(observerMock.events.stream().anyMatch(eventType -> eventType == EventType.PIECE_MOVED));
        assertTrue(observerMock.events.stream().anyMatch(eventType -> eventType == EventType.PIECE_TAKEN));

        game.detach(observerMock, List.of(EventType.ALL));
    }
}
