package com.learncode.model.shapes;

import com.learncode.model.Board;
import com.learncode.model.Cell;

/**
 * Created by Coen on 13-7-2017.
 */
public class ZShape extends Shape {

    public ZShape() {
        super(Tetromino.Z, Board.getIndex(0, 3), Board.getIndex(0, 4), Board.getIndex(1, 4), Board.getIndex(1, 5));
        this.center = 1;
    }

    // Test
    public static void main(String[] args) {
        Shape z = new ZShape();
        System.out.println(z);
        z.moveDown();
        System.out.println(z);
        z.rotate();
        System.out.println(z);
        z.rotate();
        System.out.println(z);
        z.rotate();
        System.out.println(z);
        z.rotate();
        System.out.println(z);
    }

    @Override
    public Shape rotate() {
        Shape shape = this.copy();
        if (shape.initial) {
            shape.rotateRight();
        } else {
            shape.rotateLeft();
        }
        shape.initial = !shape.initial;
        return shape;
    }

}
