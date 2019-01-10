package com.learncode.model.shapes;

import com.learncode.model.Board;

/**
 * Created by Coen on 13-7-2017.
 */
public class TShape extends Shape {

    public TShape() {
        super(Tetromino.T, Board.getIndex(0, 3), Board.getIndex(0, 4), Board.getIndex(0, 5), Board.getIndex(1, 4));
        this.center = 1;
    }

    // Test
    public static void main(String[] args) {
        Shape t = new TShape();
        System.out.println(t);
        t.moveDown();
        System.out.println(t);
        t.rotate();
        System.out.println(t);
        t.rotate();
        System.out.println(t);
        t.rotate();
        System.out.println(t);
        t.rotate();
        System.out.println(t);
    }

    @Override
    public Shape rotate() {
        Shape shape = this.copy();
        shape.rotateRight();
        return shape;
    }

}
