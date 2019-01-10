package com.learncode.view;

import com.learncode.controller.Game;
import com.learncode.controller.InputHandler;
import com.learncode.model.Board;
import com.learncode.model.Cell;
import com.learncode.model.shapes.Shape;
import com.learncode.util.Reference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * Created by Coen on 13-7-2017.
 */
public class GUIView extends Canvas {

    private static final Color TEXT_COLOR = new Color(60, 190, 180);
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final int GRID_OFFSET = 20;
    private static final int CELL_SIZE = 20;
    private static final int GRID_WIDTH = Board.COLUMNS * CELL_SIZE;
    private static final int GRID_HEIGHT = Board.ROWS * CELL_SIZE;
    private static final int SIDE_BOX_WIDTH = 200;
    private static final int TEXT_OFFSET = SIDE_BOX_WIDTH / 10;
    private static final int CANVAS_WIDTH = SIDE_BOX_WIDTH + GRID_WIDTH + GRID_OFFSET * 2;
    private static final int CANVAS_HEIGHT = GRID_HEIGHT + GRID_OFFSET * 2;
    private static final int SIDE_BOX_OFFSET = CANVAS_WIDTH - SIDE_BOX_WIDTH;
    private static final int MAX_FONT_SIZE = (CANVAS_HEIGHT * CANVAS_WIDTH) / (CANVAS_HEIGHT * 10);
    private static final String FONT_NAME = Reference.getExistingFontName("Arial Black");

    private Game controller;
    private BufferStrategy bfStrategy;
    private InputHandler inputHandler;
    private long lastIteration;
    private JFrame frame;

    public GUIView(Game controller) {
        this.controller = controller;
        inputHandler = new InputHandler(this);
        lastIteration = System.currentTimeMillis();
        frame = new JFrame("Tetris");
        JPanel pane = (JPanel) frame.getContentPane();
        pane.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        pane.setLayout(null);
        setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        pane.add(this);
        setIgnoreRepaint(true);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        createBufferStrategy(2);
        bfStrategy = getBufferStrategy();
    }

    public static void main(String[] args) {
        new Game();
    }

    public Game getController() {
        return controller;
    }

    public void start() {
        frame.setVisible(true);
        requestFocus();
        play();
    }

    public void play() {
        while (true) {
            draw();
            if (!controller.gameOver()) {
                if (!controller.isPaused()) {
                    if (controller.isFalling()) {
                        controller.moveDown();
                    } else if (System.currentTimeMillis() - lastIteration >= controller.getIterationDelay()) {
                        controller.moveDown();
                        lastIteration = System.currentTimeMillis();
                    }
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw() {
        Graphics2D g2d = (Graphics2D) bfStrategy.getDrawGraphics();
        drawBackground(g2d);
        drawGrid(g2d);
        if (!controller.gameOver()) {
            drawBoard(g2d);
            drawNextPiece(g2d, controller.getNextShape());
            if (controller.isPaused()) {
                drawPaused(g2d);
            }
        }
        drawSideBox(g2d);
        if (controller.gameOver()) {
            drawBoard(g2d);
            drawGameOver(g2d);
        }
        g2d.dispose();
        bfStrategy.show();
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        Cell[] cells = controller.getBoard().getCells();
        for (int i = 0; i < cells.length; i++) {
            g2d.drawRect(GRID_OFFSET + Board.getColumn(i) * CELL_SIZE, GRID_OFFSET + Board.getRow(i) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private void drawSideBox(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.drawLine(SIDE_BOX_OFFSET, 0, SIDE_BOX_OFFSET, CANVAS_HEIGHT);
        g2d.setColor(TEXT_COLOR);
        Font font = new Font(FONT_NAME, Font.PLAIN, 16);
        g2d.setFont(font);
        g2d.drawString(formatScore(), SIDE_BOX_OFFSET + TEXT_OFFSET, 30);
        g2d.drawString(formatLevel(), SIDE_BOX_OFFSET + TEXT_OFFSET, 70);
        g2d.drawString(formatFullRows(), SIDE_BOX_OFFSET + TEXT_OFFSET, 90);
    }

    private String formatLevel() {
        return String.format("Level: %1s", controller.getLevel());
    }

    private String formatFullRows() {
        return String.format("Lines: %1s", controller.getBoard().getFullRows());
    }

    private String formatScore() {
        return String.format("Score: %1s", controller.getScore());
    }

    private void drawGameOver(Graphics2D g2d) {
        Font font = new Font(FONT_NAME, Font.PLAIN, MAX_FONT_SIZE);
        g2d.setFont(font);
        g2d.setColor(TEXT_COLOR);
        drawCenteredString(g2d, "GAME OVER!", font, 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    private void drawPaused(Graphics2D g2d) {
        Font font = new Font(FONT_NAME, Font.PLAIN, 32);
        g2d.setFont(font);
        g2d.setColor(TEXT_COLOR);
        drawCenteredString(g2d, "PAUSED", font, GRID_OFFSET, GRID_OFFSET, GRID_WIDTH, GRID_HEIGHT);
    }

    private void drawNextPiece(Graphics2D g2d, Shape shape) {
        for (int i = 0; i < shape.getCoordinates().length; i++) {
            int coord = shape.getCoordinate(i);
            drawCell(g2d, Board.getColumn(coord) * CELL_SIZE + SIDE_BOX_OFFSET + SIDE_BOX_WIDTH / Board.COLUMNS, Board.getRow(coord) * CELL_SIZE + 2 * CANVAS_HEIGHT / 3, shape.getTetromino().getColor());
        }
    }

    private void drawBoard(Graphics2D g2d) {
        Cell[] cells = controller.getBoard().getCells();
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].getTetromino() != Shape.Tetromino.NONE) {
                drawCell(g2d, GRID_OFFSET + Board.getColumn(i) * CELL_SIZE, GRID_OFFSET + Board.getRow(i) * CELL_SIZE, cells[i].getTetromino().getColor());
            }
        }
    }

    private void drawCell(Graphics2D g2d, int x, int y, Color color) {
        g2d.setColor(color);
        g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
    }

    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    private void drawCenteredString(Graphics2D g2d, String string, Font font, int rectX, int rectY, int rectWidth, int rectHeight) {
        FontMetrics metrics = g2d.getFontMetrics(font);
        int x = rectX + (rectWidth - metrics.stringWidth(string)) / 2;
        int y = rectY + ((rectHeight - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.setFont(font);
        g2d.drawString(string, x, y);
    }

}
