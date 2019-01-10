package com.learncode.model.shapes;

import com.learncode.model.Board;

/**
 * Created by Coen on 13-7-2017.
 */
public class LShape extends Shape {

    public LShape() {
        super(Tetromino.L, Board.getIndex(0, 3), Board.getIndex(0, 4), Board.getIndex(0, 5), Board.getIndex(1, 3));
        this.center = 1;
    }

    // Test
    public static void main(String[] args) {
        Shape l = new LShape();
        System.out.println(l);
        l.moveDown();
        System.out.println(l);
        l.rotate();
        System.out.println(l);
        l.rotate();
        System.out.println(l);
        l.rotate();
        System.out.println(l);
        l.rotate();
        System.out.println(l);
    }

    @Override
    public Shape rotate() {
        Shape shape = this.copy();
        shape.rotateRight();
        return shape;
    }

}
