package ooad.gui;

import ooad.game.Game;
import ooad.observer.EventType;
import ooad.piece.PieceFactory;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GuiObserverTest {

    private static final int TILE_WIDTH = 100;
    private static final int TILE_HEIGHT = 100;

    @Test
    void testGuiObserver() {
        assertDoesNotThrow(() -> SwingUtilities.invokeLater(() -> {
            Game game = new Game(new PieceFactory());
            GuiObserver gui = new GuiObserver(game);
            game.attach(gui, List.of(EventType.CHECKMATE, EventType.CHECK, EventType.STALEMATE));
            gui.createAndShowGUI();
        }));
    }

    @Test
    void testUserInteraction() {
        assertDoesNotThrow(() -> SwingUtilities.invokeLater(() -> {
            Game game = new Game(new PieceFactory());
            GuiObserver gui = new GuiObserver(game);
            game.attach(gui, List.of(EventType.CHECKMATE, EventType.CHECK, EventType.STALEMATE));
            gui.createAndShowGUI();

            MouseEvent click = new MouseEvent(gui, MouseEvent.MOUSE_CLICKED,
                    System.currentTimeMillis(), 0,
                    3 * TILE_WIDTH, 4 * TILE_HEIGHT,
                    1, false);
            for (MouseListener listener : gui.getMouseListeners()) {
                listener.mouseClicked(click);
            }
        }));
    }
}
