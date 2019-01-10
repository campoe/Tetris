package com.learncode.model.shapes;

import com.learncode.model.Board;

/**
 * Created by Coen on 13-7-2017.
 */
public class JShape extends Shape {

    public JShape() {
        super(Tetromino.J, Board.getIndex(0, 3), Board.getIndex(0, 4), Board.getIndex(0, 5), Board.getIndex(1, 5));
        this.center = 1;
    }

    // Test
    public static void main(String[] args) {
        Shape j = new JShape();
        System.out.println(j);
        j.moveDown();
        System.out.println(j);
        j.rotate();
        System.out.println(j);
        j.rotate();
        System.out.println(j);
        j.rotate();
        System.out.println(j);
        j.rotate();
        System.out.println(j);
    }

    @Override
    public Shape rotate() {
        Shape shape = this.copy();
        shape.rotateRight();
        return shape;
    }

}
