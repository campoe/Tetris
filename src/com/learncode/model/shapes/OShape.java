package com.learncode.model.shapes;

import com.learncode.model.Board;

/**
 * Created by Coen on 13-7-2017.
 */
public class OShape extends Shape {

    public OShape() {
        super(Tetromino.O, Board.getIndex(0, 4), Board.getIndex(0, 5), Board.getIndex(1, 4), Board.getIndex(1, 5));
        this.center = 3;
    }

    // Test
    public static void main(String[] args) {
        Shape o = new OShape();
        System.out.println(o);
        o.moveDown();
        System.out.println(o);
        o.rotate();
        System.out.println(o);
        o.rotate();
        System.out.println(o);
        o.rotate();
        System.out.println(o);
        o.rotate();
        System.out.println(o);
    }

    @Override
    public Shape rotate() {
        // don't do anything
        Shape shape = this.copy();
        return shape;
    }

}
