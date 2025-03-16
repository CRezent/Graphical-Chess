package ooad.observer;

import java.util.List;

import org.junit.jupiter.api.Test;

import ooad.game.Game;
import ooad.piece.PieceFactory;

public class AudibleObserverTest {

    @Test
    void testAudibleObserver() {
        Game game = new Game(new PieceFactory());
        AudibleObserver audibleObserver = new AudibleObserver(1);
        game.attach(audibleObserver, List.of(EventType.PIECE_MOVED, EventType.PIECE_TAKEN));

        game.tileClicked(6, 0);
        game.tileClicked(4, 0);
        game.tileClicked(1, 1);
        game.tileClicked(3, 1);
        game.tileClicked(4, 0);
        game.tileClicked(3, 1);

        game.detach(audibleObserver, List.of(EventType.PIECE_MOVED, EventType.PIECE_TAKEN));
    }
}
