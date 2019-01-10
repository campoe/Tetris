package com.learncode.controller;

import com.learncode.model.Board;
import com.learncode.model.shapes.Shape;
import com.learncode.view.GUIView;

/**
 * Created by Coen on 13-7-2017.
 */
public class Game {

    private static final int LEVEL_CAP = 90;

    private Board board;

    private boolean paused;
    private boolean falling;
    private boolean gameOver;

    private int fallIterations;
    private int score;

    private GUIView view;
    private Shape nextShape;

    public Game() {
        this.view = new GUIView(this);
        start();
    }

    public void reset() {
        score = 0;
        fallIterations = 0;
        board = new Board();
        paused = false;
        falling = false;
        gameOver = false;
        nextShape = getRandomShape();
    }

    public void start() {
        reset();
        view.start();
    }

    public long getIterationDelay() {
        return (long) (((11 - getLevel()) * 0.05) * 1000);
    }

    public int getLevel() {
        if ((board.getFullRows() >= 1) && (board.getFullRows() <= LEVEL_CAP)) {
            return 1 + ((board.getFullRows() - 1) / 10);
        } else if (board.getFullRows() > LEVEL_CAP) {
            return 10;
        } else {
            return 1;
        }
    }

    public int getFallIterations() {
        return fallIterations;
    }

    public void incrementFallIterations() {
        fallIterations++;
    }

    public int getScore() {
        return score;
    }

    public Shape getRandomShape() {
        return Shape.getRandom();
    }

    public Shape getNextShape() {
        return nextShape;
    }

    public Board getBoard() {
        return board;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public void togglePause() {
        setPaused(!isPaused());
    }

    public void fall() {
        setFalling(true);
    }

    public void moveDown() {
        falling = false;
        if (!board.canMoveDown(board.getCurrentShape())) {
            for (int i = 0; i < Shape.CELLS; i++) {
                if (!board.isEmptyCell(nextShape.getCoordinate(i))) {
                    gameOver = true;
                }
            }
            board.addShape(nextShape);
            nextShape = getRandomShape();
            increaseScore(((21 + (3 * getLevel())) - fallIterations));
            fallIterations = 0;
        } else {
            board.moveDown();
            incrementFallIterations();
        }
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    public void moveLeft() {
        board.moveLeft();
    }

    public void moveRight() {
        board.moveRight();
    }

    public void rotate() {
        board.rotate();
    }

}
