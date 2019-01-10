package com.learncode.model.shapes;

import com.learncode.model.Board;

import java.awt.*;
import java.util.Random;

/**
 * Created by Coen on 13-7-2017.
 */
public abstract class Shape {

    public static final int CELLS = 4;
    protected Tetromino tetromino;
    protected int[] coords;
    protected int center;
    protected boolean initial;

    public Shape(Tetromino tetromino, int coord1, int coord2, int coord3, int coord4) {
        this.tetromino = tetromino;
        this.coords = new int[]{coord1, coord2, coord3, coord4};
        this.initial = true;
    }

    public Shape(Tetromino tetromino, int coord1, int coord2, int coord3, int coord4, int center, boolean initial) {
        this.tetromino = tetromino;
        this.coords = new int[]{coord1, coord2, coord3, coord4};
        this.center = center;
        this.initial = initial;
    }

    public static Shape getRandom() {
        Tetromino tetromino = Tetromino.getRandom();
        return getShape(tetromino);
    }

    public static Shape getShape(Tetromino tetromino) {
        if (tetromino == Tetromino.L) {
            return new LShape();
        } else if (tetromino == Tetromino.T) {
            return new TShape();
        } else if (tetromino == Tetromino.S) {
            return new SShape();
        } else if (tetromino == Tetromino.I) {
            return new IShape();
        } else if (tetromino == Tetromino.O) {
            return new OShape();
        } else if (tetromino == Tetromino.J) {
            return new JShape();
        } else {
            return new ZShape();
        }
    }

    public Tetromino getTetromino() {
        return tetromino;
    }

    public int[] getCoordinates() {
        return coords;
    }

    public int getCoordinate(int index) {
        return coords[index];
    }

    public int getCenter() {
        return coords[center];
    }

    public void setCoord(int index, int coord) {
        coords[index] = coord;
    }

    public void setTetromino(Tetromino tetromino) {
        this.tetromino = tetromino;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public void setCoords(int coord1, int coord2, int coord3, int coord4) {
        setCoord(0, coord1);
        setCoord(1, coord2);
        setCoord(2, coord3);
        setCoord(3, coord4);
    }

    public abstract Shape rotate();

    public Shape copy() {
        Shape copy = getShape(tetromino);
        copy.setTetromino(tetromino);
        copy.setCenter(center);
        copy.setInitial(initial);
        copy.setCoords(coords[0], coords[1], coords[2], coords[3]);
        return copy;
    }

    public void rotateLeft() {
        for (int i = 0; i < coords.length; i++) {
            if (i == center) {
                continue;
            }
            int row = Board.getRow(coords[i]);
            int column = Board.getColumn(coords[i]);
            int rowDiff = row - Board.getRow(getCenter());
            int columnDiff = column - Board.getColumn(getCenter());
            coords[i] = Board.getIndex(row - columnDiff - rowDiff, column - columnDiff + rowDiff);
        }
    }

    public void rotateRight() {
        for (int i = 0; i < coords.length; i++) {
            if (i == center) {
                continue;
            }
            int row = Board.getRow(coords[i]);
            int column = Board.getColumn(coords[i]);
            int rowDiff = row - Board.getRow(getCenter());
            int columnDiff = column - Board.getColumn(getCenter());
            coords[i] = Board.getIndex(row + columnDiff - rowDiff, column - columnDiff - rowDiff);
        }
    }

    public void moveLeft() {
        for (int i = 0; i < coords.length; i++) {
            coords[i]--;
        }
    }

    public void moveRight() {
        for (int i = 0; i < coords.length; i++) {
            coords[i]++;
        }
    }

    public void moveDown() {
        for (int i = 0; i < coords.length; i++) {
            coords[i] += Board.COLUMNS;
        }
    }

    public boolean hasCoordinate(int coord) {
        for (int i = 0; i < getCoordinates().length; i++) {
            if (getCoordinate(i) == coord) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < coords.length; i++) {
            res += coords[i];
            if (i < coords.length - 1) {
                res += ", ";
            }
        }
        return res;
    }

    public enum Tetromino {

        L() {
            public Color getColor() {
                return Color.CYAN;
            }
        },
        Z() {
            public Color getColor() {
                return Color.RED;
            }
        },
        S() {
            public Color getColor() {
                return Color.GREEN;
            }
        },
        T() {
            public Color getColor() {
                return Color.YELLOW;
            }
        },
        O() {
            public Color getColor() {
                return Color.MAGENTA;
            }
        },
        I() {
            public Color getColor() {
                return Color.BLUE;
            }
        },
        J() {
            public Color getColor() {
                return Color.ORANGE;
            }
        },
        NONE() {
            public Color getColor() {
                return Color.BLACK;
            }
        };

        private static Random random = new Random();

        Tetromino() {

        }

        public abstract Color getColor();

        public static Tetromino getRandom() {
            return Tetromino.values()[random.nextInt(Tetromino.values().length)];
        }

        @Override
        public String toString() {
            if (this == NONE) {
                return " ";
            } else {
                return super.toString();
            }
        }

    }

}
