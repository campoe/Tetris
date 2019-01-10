package com.learncode.model;

import com.learncode.model.shapes.Shape;

/**
 * Created by Coen on 13-7-2017.
 */
public class Cell {

    private int row;
    private int column;
    private Shape.Tetromino tetromino;

    public Cell(Shape.Tetromino tetromino, int row, int column) {
        this.tetromino = tetromino;
        this.row = row;
        this.column = column;
    }

    public Cell(Shape.Tetromino tetromino, int index) {
        this(tetromino, Board.getRow(index), Board.getColumn(index));
    }

    public boolean isEmpty() {
        return tetromino == Shape.Tetromino.NONE;
    }

    public void clear() {
        this.tetromino = Shape.Tetromino.NONE;
    }

    public Shape.Tetromino getTetromino() {
        return tetromino;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setTetromino(Shape.Tetromino tetromino) {
        this.tetromino = tetromino;
    }

    @Override
    public String toString() {
        return tetromino.toString();
    }

}
