package esdia.sokoban.ui;

import esdia.sokoban.global.Configuration;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
    MainWindow window;

    public Keyboard(MainWindow window) {
        this.window = window;
    }

    private void log(int code) {
        Configuration.instance().get_logger().info(
                "Key pressed : " + code
        );
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.log(e.getKeyCode());

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER -> this.window.game.skipLevel();

            case KeyEvent.VK_F11 -> this.window.toggleFullscreen();

            case KeyEvent.VK_UP, KeyEvent.VK_Z -> this.window.game.moveUp();
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> this.window.game.moveDown();
            case KeyEvent.VK_LEFT, KeyEvent.VK_Q -> this.window.game.moveLeft();
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> this.window.game.moveRight();
        }
    }
}
