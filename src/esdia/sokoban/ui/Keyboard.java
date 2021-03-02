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
            case KeyEvent.VK_ENTER -> {
                Configuration.instance().get_logger().info(
                        "Skipping level"
                );
                if (this.window.game.nextLevel()) {
                    this.window.frame.setTitle("Sokoban - Level " + this.window.game.getCurrentLevel().name());
                    this.window.frame.repaint();
                } else {
                    Configuration.instance().get_logger().info(
                            "No next level : shutting down"
                    );
                    this.window.shutdown();
                }
            }

            case KeyEvent.VK_F11 -> this.window.toggleFullscreen();

            case KeyEvent.VK_UP -> this.window.game.moveUp();
            case KeyEvent.VK_DOWN -> this.window.game.moveDown();
            case KeyEvent.VK_LEFT -> this.window.game.moveLeft();
            case KeyEvent.VK_RIGHT -> this.window.game.moveRight();
        }
    }
}
