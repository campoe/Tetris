package com.learncode.model.shapes;

import com.learncode.model.Board;

/**
 * Created by Coen on 13-7-2017.
 */
public class IShape extends Shape {

    public IShape() {
        super(Tetromino.I, Board.getIndex(0, 3), Board.getIndex(0, 4), Board.getIndex(0, 5), Board.getIndex(0, 6));
        this.center = 1;
    }

    // Test
    public static void main(String[] args) {
        Shape i = new IShape();
        System.out.println(i);
        i.moveDown();
        System.out.println(i);
        i = i.rotate();
        System.out.println(i);
        i = i.rotate();
        System.out.println(i);
        i = i.rotate();
        System.out.println(i);
        i = i.rotate();
        System.out.println(i);
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
