package ooad;

import ooad.game.Game;
import ooad.gui.GuiObserver;
import ooad.observer.AudibleObserver;
import ooad.observer.EventType;
import ooad.piece.PieceFactory;

import javax.swing.*;
import java.util.List;

public class Chess {
    public static void main(String[] args) {
        Game game = new Game(new PieceFactory());

        GuiObserver guiObserver = new GuiObserver(game);
        AudibleObserver audibleObserver = new AudibleObserver(0);

        game.attach(guiObserver, List.of(EventType.CHECKMATE, EventType.CHECK, EventType.STALEMATE));
        game.attach(audibleObserver, List.of(EventType.CHECKMATE, EventType.CHECK, EventType.STALEMATE, EventType.PAWN_REACH_END, EventType.PIECE_TAKEN));

        SwingUtilities.invokeLater(guiObserver::createAndShowGUI);
    }
}
