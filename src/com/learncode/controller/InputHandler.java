package com.learncode.controller;

import com.learncode.view.GUIView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Coen on 13-7-2017.
 */
public class InputHandler implements KeyListener {

    private Game controller;

    public InputHandler(GUIView canvas) {
        canvas.addKeyListener(this);
        this.controller = canvas.getController();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            controller.reset();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (!controller.gameOver()) {
            if (!controller.isPaused()) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    controller.rotate();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    controller.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    controller.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    controller.fall();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_F1) {
                controller.togglePause();
            }
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
