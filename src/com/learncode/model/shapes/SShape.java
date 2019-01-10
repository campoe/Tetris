package com.learncode.model.shapes;

import com.learncode.model.Board;
import com.learncode.model.Cell;

/**
 * Created by Coen on 13-7-2017.
 */
public class SShape extends Shape {

    public SShape() {
        super(Tetromino.S, Board.getIndex(0, 4), Board.getIndex(0, 5), Board.getIndex(1, 3), Board.getIndex(1, 4));
        this.center = 3;
    }

    // Test
    public static void main(String[] args) {
        Shape s = new SShape();
        System.out.println(s);
        s.moveDown();
        System.out.println(s);
        s.rotate();
        System.out.println(s);
        s.rotate();
        System.out.println(s);
        s.rotate();
        System.out.println(s);
        s.rotate();
        System.out.println(s);
    }

    @Override
    public Shape rotate() {
        Shape shape = this.copy();
        if (shape.initial) {
            shape.rotateLeft();
        } else {
            shape.rotateRight();
        }
        shape.initial = !shape.initial;
        return shape;
    }

}
