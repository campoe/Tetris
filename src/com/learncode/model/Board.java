package com.learncode.model;

import com.learncode.model.shapes.Shape;

/**
 * Created by Coen on 13-7-2017.
 */
public class Board {

    public static final int ROWS = 20;
    public static final int COLUMNS = 10;
    public static final int CELLS = ROWS * COLUMNS;

    private Cell[] cells;
    private Shape currentShape;
    private int fullRows;

    public Board() {
        this.currentShape = Shape.getRandom();
        this.fullRows = 0;
        this.cells = new Cell[CELLS];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(Shape.Tetromino.NONE, i);
        }
    }

    public static int getRow(int index) {
        return index / COLUMNS;
    }

    public static int getColumn(int index) {
        return index % COLUMNS;
    }

    public static int getIndex(int row, int column) {
        return column + row * COLUMNS;
    }

    public static boolean isCell(int index) {
        return 0 <= index && index < CELLS;
    }

    public static boolean isCell(int row, int column) {
        return isCell(getIndex(row, column));
    }

    public static void main(String[] args) {
        System.out.println(new Board());
    }

    public void addShape(Shape shape) {
        this.currentShape = shape;
        for (int i = 0; i < shape.getCoordinates().length; i++) {
            int coord = shape.getCoordinate(i);
            cells[coord].setTetromino(shape.getTetromino());
        }
    }

    public void removeShape(Shape shape) {
        for (int i = 0; i < shape.getCoordinates().length; i++) {
            int coord = shape.getCoordinate(i);
            cells[coord].setTetromino(Shape.Tetromino.NONE);
        }
    }

    public void clear() {
        for (int i = 0; i < cells.length; i++) {
            cells[i].setTetromino(Shape.Tetromino.NONE);
        }
    }

    public void reset() {
        clear();
        fullRows = 0;
        currentShape = null;
    }

    public void clearFullRows() {
        Cell[] newCells = createEmptyCells();
        int fullRows = 0;
        for (int row = ROWS - 1; row >= 0; row--) {
            if (isFullRow(row)) {
                fullRows++;
            } else {
                for (int column = 0; column < COLUMNS; column++) {
                    newCells[getIndex(row + fullRows, column)] = getCell(row, column);
                }
            }
        }
        this.fullRows += fullRows;
        this.cells = newCells;
    }

    private Cell[] createEmptyCells() {
        Cell[] cells = new Cell[CELLS];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(Shape.Tetromino.NONE, i);
        }
        return cells;
    }

    private void copyRow(Cell[] cells, int row, int toRow) {
        for (int column = 0; column < COLUMNS; column++) {
            cells[getIndex(toRow, column)] = this.cells[getIndex(row, column)];
        }
    }

    public void moveDown() {
        if (canMoveDown(currentShape)) {
            removeShape(currentShape);
            currentShape.moveDown();
            addShape(currentShape);
        }
        if (!canMoveDown(currentShape)) {
            clearFullRows();
        }
    }

    public void rotate() {
        Shape newShape = currentShape.rotate();
        if (fits(newShape)) {
            int prevCoord = -1;
            for (int i = 0; i < newShape.getCoordinates().length; i++) {
                int coord = newShape.getCoordinate(i);
                if (prevCoord != -1 && (Board.getColumn(coord) == Board.getColumn(prevCoord) + COLUMNS - 1 || Board.getColumn(coord) == Board.getColumn(prevCoord) - COLUMNS + 1)) {
                    return;
                }
                prevCoord = coord;
            }
            Shape oldShape = currentShape;
            removeShape(oldShape);
            addShape(newShape);
        }
    }

    public Cell[] getCells() {
        return cells;
    }

    public boolean fits(Shape shape) {
        for (int i = 0; i < shape.getCoordinates().length; i++) {
            int coord = shape.getCoordinate(i);
            if (!isCell(coord)) {
                return false;
            }
            if (!isEmptyCell(coord) && !currentShape.hasCoordinate(coord)) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveDown(Shape shape) {
        for (int i = 0; i < shape.getCoordinates().length; i++) {
            int coord = shape.getCoordinate(i);
            if (!isCell(coord)) {
                return false;
            }
            // bottom row
            if (Board.getRow(coord) == ROWS - 1) {
                return false;
            }
            if (!isEmptyCell(coord + COLUMNS) && !shape.hasCoordinate(coord + COLUMNS)) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft(Shape shape) {
        for (int i = 0; i < shape.getCoordinates().length; i++) {
            int coord = shape.getCoordinate(i);
            if (!isCell(coord)) {
                return false;
            }
            // most left column
            if (Board.getColumn(coord) == 0) {
                return false;
            }
            if (!isEmptyCell(coord - 1) && !shape.hasCoordinate(coord - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveRight(Shape shape) {
        for (int i = 0; i < shape.getCoordinates().length; i++) {
            int coord = shape.getCoordinate(i);
            if (!isCell(coord)) {
                return false;
            }
            // most right column
            if (Board.getColumn(coord) == COLUMNS - 1) {
                return false;
            }
            if (!isEmptyCell(coord + 1) && !shape.hasCoordinate(coord + 1)) {
                return false;
            }
        }
        return true;
    }

    public void moveLeft() {
        if (canMoveLeft(currentShape)) {
            removeShape(currentShape);
            currentShape.moveLeft();
            addShape(currentShape);
        }
    }

    public void moveRight() {
        if (canMoveRight(currentShape)) {
            removeShape(currentShape);
            currentShape.moveRight();
            addShape(currentShape);
        }
    }

    public void setCell(int index, Shape.Tetromino tetromino) {
        cells[index].setTetromino(tetromino);
    }

    public void setCell(int row, int column, Shape.Tetromino tetromino) {
        setCell(getIndex(row, column), tetromino);
    }

    public Cell getCell(int index) {
        return cells[index];
    }

    public Cell getCell(int row, int column) {
        return cells[getIndex(row, column)];
    }

    public boolean isEmptyCell(int index) {
        return getCell(index).isEmpty();
    }

    public boolean isEmptyCell(int row, int column) {
        return isEmptyCell(getIndex(row, column));
    }

    public boolean isFullRow(int row) {
        for (int column = 0; column < COLUMNS; column++) {
            if (isEmptyCell(row, column)) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull() {
        for (int column = 0; column < COLUMNS; column++) {
            boolean columnFilled = true;
            for (int row = 0; row < ROWS; row++) {
                if (isEmptyCell(row, column)) {
                    columnFilled = false;
                    break;
                }
            }
            if (columnFilled) {
                return true;
            }
        }
        return false;
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    @Override
    public String toString() {
        String res = "-----------------------------------------\n";
        for (int row = 0; row < ROWS; row++) {
            res += "| ";
            for (int column = 0; column < COLUMNS; column++) {
                res += getCell(row, column).toString() + " | ";
            }
            res += "\n-----------------------------------------\n";
        }
        return res;
    }

    public int getFullRows() {
        return fullRows;
    }

}
