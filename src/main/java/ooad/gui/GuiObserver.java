package ooad.gui;

import ooad.board.Board;
import ooad.game.IChessGame;
import ooad.observer.EventType;
import ooad.observer.IObserver;
import ooad.piece.Piece;
import ooad.board.Tile;
import ooad.board.TileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static ooad.board.Board.BOARD_SIZE;

public class GuiObserver extends JPanel implements IObserver {
    private static final Logger logger = LoggerFactory.getLogger(GuiObserver.class);

    private static final int TILE_WIDTH = 100;
    private static final int TILE_HEIGHT = 100;
    private static final int JPANEL_Y_OFFSET = 30;
    private static final int JPANEL_X_OFFSET = 7;
    private static final int HEIGHT = (TILE_HEIGHT * BOARD_SIZE) + JPANEL_Y_OFFSET;
    private static final int WIDTH = (TILE_WIDTH * BOARD_SIZE) + JPANEL_X_OFFSET - 1;
    private static final int BOARD_OFFSET_X = 0;
    private static final int BOARD_OFFSET_Y = 0;

    private static final int CHECK_ON_SCREEN_TIMER = 2000;
    private static final int TOP_TEXT_PAD = 0;
    private static final int HORIZONTAL_TEXT_PAD = 40;
    private static final int BOTTOM_TEXT_PAD = 30;

    private static final String IMAGE_DIR = "/image_files/";

    private final IChessGame chessGame;
    private final Board board;
    private String displayMessage;

    public GuiObserver(IChessGame chessGame) {
        this.chessGame = chessGame;
        this.board = this.chessGame.getBoard();
    }

    @Override
    public void update(EventType eventType, String eventDescription) {
        switch(eventType) {
            case CHECK:
                displayMessage = eventDescription;
                final java.util.Timer t = new java.util.Timer();
                t.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                displayMessage = null;
                                repaint();
                                t.cancel();
                            }
                        },
                        CHECK_ON_SCREEN_TIMER
                );
                break;
            case CHECKMATE:
                displayMessage = eventDescription;
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Tile tile = this.board.getTile(col, row);

//                Draw black/white tile
                Color tileColor = tile.getTileType() == TileType.WHITE ? Color.WHITE : Color.BLACK;
                g.setColor(tileColor);
                g.fillRect(BOARD_OFFSET_X + (row * TILE_WIDTH), BOARD_OFFSET_Y + (col * TILE_HEIGHT), TILE_WIDTH, TILE_HEIGHT);

//                Highlight tile if needed
                Optional<Color> highlightColor = Optional.empty();
                switch (tile.getTileHighlightType()) {
                    case NONE:
                        break;
                    case GREEN:
                        highlightColor = Optional.of(new Color(0, 255, 0, 127));
                        break;
                    case YELLOW:
                        highlightColor = Optional.of(new Color(255, 255, 0, 127));
                        break;
                    case RED:
                        highlightColor = Optional.of(new Color(255, 0, 0, 127));
                        break;
                }
                if (highlightColor.isPresent()) {
                    g.setColor(highlightColor.get());
                    g.fillRect(BOARD_OFFSET_X + (row * TILE_WIDTH), BOARD_OFFSET_Y + (col * TILE_HEIGHT), TILE_WIDTH, TILE_HEIGHT);
                }

//                Draw piece if tile has one
                Optional<Piece> piece = Optional.ofNullable(tile.getPiece());
                if (piece.isPresent() && !piece.get().getIsTaken()) {
                    BufferedImage img = loadImg(piece.get().getFileName());
                    if (img != null) {
                        g.drawImage(img, BOARD_OFFSET_X + (row * TILE_WIDTH), BOARD_OFFSET_Y + (col * TILE_HEIGHT), TILE_WIDTH, TILE_HEIGHT, null);
                    }
                }
            }
        }


        Optional.ofNullable(displayMessage).ifPresent(s -> {
            Font font = new Font("TimesRoman", Font.BOLD, 45);
            FontRenderContext frc = new FontRenderContext(null, true, true);
            Rectangle2D r2D = font.getStringBounds(s, frc);

            int rWidth = (int) Math.round(r2D.getWidth());
            int rHeight = (int) Math.round(r2D.getHeight());

            g.setColor(Color.GRAY);
            g.fillRect(
                    ((WIDTH - rWidth) / 2) - HORIZONTAL_TEXT_PAD,
                    ((HEIGHT - rHeight) / 2) - rHeight - TOP_TEXT_PAD,
                    rWidth + (2 * HORIZONTAL_TEXT_PAD),
                    rHeight + BOTTOM_TEXT_PAD + TOP_TEXT_PAD);

            g.setColor(Color.BLUE);
            g.setFont(font);
            g.drawString(s, (WIDTH - rWidth) / 2, (HEIGHT - rHeight) / 2);
        });
    }

    private BufferedImage loadImg(String filename) {
        BufferedImage img;
        InputStream imgStream = getClass().getResourceAsStream(IMAGE_DIR + filename);
        try {
            if (imgStream != null) {
                img = ImageIO.read(imgStream);
            } else {
                throw new IOException("Failed to load stream");
            }
        } catch (IOException e) {
            logger.error("Failed to load image {}. {}", filename, e.getMessage());
            img = null;
        }
        return img;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Chess");
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int row = (me.getY() - BOARD_OFFSET_Y - JPANEL_Y_OFFSET) / TILE_HEIGHT;
                int col = (me.getX() - BOARD_OFFSET_X - JPANEL_X_OFFSET) / TILE_WIDTH;

                chessGame.tileClicked(row, col);

                repaint();
            }
        });
    }
}
